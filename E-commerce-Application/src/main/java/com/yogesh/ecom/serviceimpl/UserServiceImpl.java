package com.yogesh.ecom.serviceimpl;


import java.time.Duration;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yogesh.ecom.cache.CacheStore;
import com.yogesh.ecom.emailService.MailService;
import com.yogesh.ecom.emailService.MessageModel;
import com.yogesh.ecom.enums.UserRole;
import com.yogesh.ecom.exception.IllegalArgumentExceptions;
import com.yogesh.ecom.exception.IlligalAccesRequstException;
import com.yogesh.ecom.exception.InvalidEmailException;
import com.yogesh.ecom.exception.InvalidIUserRoleSpecifiedException;
import com.yogesh.ecom.exception.InvaliodCredentionalException;
import com.yogesh.ecom.exception.OptExpiredException;
import com.yogesh.ecom.exception.OtpInvalidException;
import com.yogesh.ecom.exception.RegistrationSessionExpiredException;
import com.yogesh.ecom.jwt.JwtService;
import com.yogesh.ecom.model.AccessToken;
import com.yogesh.ecom.model.Customer;
import com.yogesh.ecom.model.RefreshToken;
import com.yogesh.ecom.model.Seller;
import com.yogesh.ecom.model.User;
import com.yogesh.ecom.repository.AccesTokenRepository;
import com.yogesh.ecom.repository.CustomerRepository;
import com.yogesh.ecom.repository.RefreshTokenRepo;
import com.yogesh.ecom.repository.SellerRepository;
import com.yogesh.ecom.repository.UserRepository;
import com.yogesh.ecom.requestDto.AuthRequst;
import com.yogesh.ecom.requestDto.OtpRequest;
import com.yogesh.ecom.requestDto.UserRequst;
import com.yogesh.ecom.responseDto.AuthResponse;
import com.yogesh.ecom.responseDto.UserResponse;
import com.yogesh.ecom.responseDto.UserResponse.UserResponseBuilder;
import com.yogesh.ecom.service.UserService;
import com.yogesh.ecom.utility.ResponseStructure;
import com.yogesh.ecom.utility.SimpleResponseStructure;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private CacheStore<String> otpCache;
	private CacheStore<User> userCache;
	private ResponseStructure<UserResponse> responseStructure;
	private SimpleResponseStructure simpleResponseStructure;
	private MailService mailService;
	private AuthenticationManager authManeger;
	private JwtService jwtService;
	private RefreshTokenRepo refreshTokenRepo;
	private AccesTokenRepository accesTokenRepository;
	private PasswordEncoder encoder;




	@Value("${myapp.jwt.access.expiration}")
	private long accesTokenExpiretion;
	@Value("${myapp.jwt.refresh.expiration}")
	private long refreshTokenExpiretion;



	public UserServiceImpl(UserRepository userRepository, CacheStore<String> otpCache, CacheStore<User> userCache,
			ResponseStructure<UserResponse> responseStructure, SimpleResponseStructure simpleResponseStructure,
			MailService mailService, AuthenticationManager authManeger, JwtService jwtService,
			RefreshTokenRepo refreshTokenRepo, AccesTokenRepository accesTokenRepository, PasswordEncoder encoder) {
		super();
		this.userRepository = userRepository;
		this.otpCache = otpCache;
		this.userCache = userCache;
		this.responseStructure = responseStructure;
		this.simpleResponseStructure = simpleResponseStructure;
		this.mailService = mailService;
		this.authManeger = authManeger;
		this.jwtService = jwtService;
		this.refreshTokenRepo = refreshTokenRepo;
		this.accesTokenRepository = accesTokenRepository;
		this.encoder = encoder;
	}
	@Override
	public ResponseEntity<SimpleResponseStructure> userRegistration(UserRequst userRequst) {
		// TODO Auto-generated method stub
		if(userRepository.existsByEmail(userRequst.getEmail())) 
			throw new InvalidIUserRoleSpecifiedException("Invalid User Email");
		User user=mapToChildEntity(userRequst);

		String otp = generetOpt();

		otpCache.add(userRequst.getEmail(), otp);
		userCache.add(userRequst.getEmail(), user);

		System.err.println(otp);

		//send mail with otp
		try {
			sendOTP(user,otp);
		} catch (MessagingException e) {
			throw new InvalidEmailException("Plaese Enter valid Email-ID");
		}

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(simpleResponseStructure.setStatus(HttpStatus.ACCEPTED.value()).setMessage("Verify OTP sent through mail to complete registration | "
				+ "OTP expires in 1 minute"));
	}
	private void sendOTP(User user, String otp) throws MessagingException {
		// TODO Auto-generated method stub
		MessageModel model = MessageModel.builder()
				.to(user.getEmail())
				.subject("Verify Your OTP")
				.text(
						"Hi " + user.getEmail().split("@")[0] + ",<br>"
								+ "<h4> Nice to see you interested in Flipkart, your OTP for email verification is,</h4><br><br>"
								+ "<h3 style=\"color: #f2f2f2; font-size: 1rem; font-weight: 600; text-decoration: none; padding: 0.5em 1em;"
								+ "border-radius: 10px; width: max-content;\">" + otp + "</h3>" // add the OTP ID (UUID)
								+ "<br><br>"
								+ "With Best Regards,<br>"
								+ "Flipkart"
						)
				.build();
		mailService.sendMailMessage(model);
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> verifyOtp(OtpRequest optRequest) {
		// TODO Auto-generated method stub
		//System.out.println(otpCache.get("otp"));
		if (otpCache.get(optRequest.getEmail())==null) throw new OptExpiredException("opt expired");
		if(!otpCache.get(optRequest.getEmail()).equals(optRequest.getOtp())) throw new OtpInvalidException("Please Enter valid Otp");


		User user = userCache.get(optRequest.getEmail());
		if (user==null) throw new RegistrationSessionExpiredException("Registration Time is Expired");
		user.setEmailVerified(true);
		user.setPassword(encoder.encode(user.getPassword()));

		return ResponseEntity.ok(responseStructure.setStatuscode(HttpStatus.OK.value())
				.setMessage("otp verified")
				.setData(mapToUserResponse(userRepository.save(user))));
	}

	private UserResponse mapToUserResponse(User user) {
		// TODO Auto-generated method stub
		return UserResponse.builder()
				.userId(user.getUserId())
				.userName(user.getUserName())
				.displayName(user.getDisplayName())
				.email(user.getEmail())
				.isEmailVerified(user.isEmailVerified())
				.userRole(user.getUserRole())
				.build();
	}
	private <T extends User> T mapToChildEntity(UserRequst userRequst) {
		// TODO Auto-generated method stub
		UserRole userRole = userRequst.getUserRole();
		User user=null;
		switch (userRole) {
		case SELLER ->user=new Seller();
		case CUSTOMER -> user=new Customer();
		default -> throw new InvalidIUserRoleSpecifiedException("Invalid user Role");
		}
		user.setDisplayName(userRequst.getDisplayName());
		user.setUserName(userRequst.getEmail().split("@gmail.com") [0]);
		user.setEmail(userRequst.getEmail());
		user.setPassword(userRequst.getPassword());
		user.setUserRole(userRole);
		user.setEmailVerified(false);
		user.setDeleted(false);
		return (T) user;		
	}
	private String generetOpt() {
		// TODO Auto-generated method stub
		return String.valueOf(new Random().nextInt(100000, 999999));
	}

	@Override
	public ResponseEntity<ResponseStructure<AuthResponse>> logIn(AuthRequst authRequst) {
		String username = authRequst.getUsername().split("@gmail.com") [0];

		Authentication authenticate = authManeger.authenticate(new UsernamePasswordAuthenticationToken(username,authRequst.getPassword()));

		if(!authenticate.isAuthenticated()) throw new InvaliodCredentionalException("User is not Authenticated");

		SecurityContextHolder.getContext().setAuthentication(authenticate);

		HttpHeaders headers=new HttpHeaders();

		return userRepository.findByUserName(username).map(user -> {
			generateAccesToken(user,headers);
			generateRefreshToken(user,headers);
			return ResponseEntity.ok().headers(headers).body(new ResponseStructure<AuthResponse>()
					.setStatuscode(HttpStatus.OK.value())
					.setMessage("Log In seccusfully")
					.setData(mapTOAuthResponse(user)));
		}).get();
	}
	@Override
	public ResponseEntity<ResponseStructure<AuthResponse>> logOut(String accesToken, String refreshToken) {

		HttpHeaders headers=new HttpHeaders();
		headers.add(HttpHeaders.SET_COOKIE,invalidCooklie(accesToken));
		headers.add(HttpHeaders.SET_COOKIE, invalidCooklie(refreshToken));
		//block the tokens
		blockAccesToken(accesToken);
		blockRefreshToken(refreshToken);

		return null;
	}

	private void blockRefreshToken(String refreshToken) {
		refreshTokenRepo.findByToken(refreshToken).ifPresent(rt->{
			rt.setBlocked(true);
			refreshTokenRepo.save(rt);
		});

	}
	private void blockAccesToken(String accesToken) {
		accesTokenRepository.findByToken(accesToken).ifPresent(at->{
			at.setBlocked(true);
			accesTokenRepository.save(at);
		});

	}
	private String invalidCooklie(String name) {
		return ResponseCookie.from(name,"")
				.domain("localhost")
				.path("/")
				.httpOnly(true)
				.secure(false)
				.maxAge(0)
				.sameSite("lax")
				.build().toString();
	}
	@Override
	public ResponseEntity<ResponseStructure<AuthResponse>> refreshLogin(String accesToken, String refreshToken) {
		System.out.println(refreshToken);
		if(refreshToken==null) {
			throw new IllegalArgumentExceptions("User is not Logged In");
		}
		if(accesToken!=null)
		{
			accesTokenRepository.findByToken(refreshToken).ifPresent(token->
			{
				token.setBlocked(true);
				accesTokenRepository.save(token);
			});
		}
		Date date=jwtService.getIssueDate(refreshToken);
		String username=jwtService.getuserName(refreshToken);
		System.out.println("USERNAME : "+username);
		HttpHeaders headers=new HttpHeaders();

		return userRepository.findByUserName(username).map(user->{
			if(date.before(new Date()))
				generateAccesToken(user, headers);
			else 
				headers.add(HttpHeaders.SET_COOKIE,configureCookie("rt", refreshToken,refreshTokenExpiretion));
			generateAccesToken(user, headers);
			return ResponseEntity.ok().headers(headers).body(new ResponseStructure<AuthResponse>()
					.setStatuscode(HttpStatus.OK.value()).setMessage("Token is Refresh seccusfully")
					.setData(mapTOAuthResponse(user))
					);
		}).get();
	}

	private AuthResponse mapTOAuthResponse(User user)
	{
		return AuthResponse.builder()
				.userId(user.getUserId()).accesExpiretion(accesTokenExpiretion).refreshExpiretion(refreshTokenExpiretion)
				.username(user.getUserName())
				.userRole(user.getUserRole())
				.build();
	}

	private void generateAccesToken(User user, HttpHeaders headers) {

		String token = jwtService.generateAccessToken(user.getUserName(),user.getUserRole().name());
		headers.add(HttpHeaders.SET_COOKIE,configureCookie("at",token,accesTokenExpiretion));
		AccessToken accessToken=new AccessToken();
		accessToken.setToken(token);
		accessToken.setBlocked(false);
		accessToken.setAccesExpiretion(accesTokenExpiretion);
		accessToken.setUser(user);
		accesTokenRepository.save(accessToken);
	}

	private void generateRefreshToken(User user, HttpHeaders headers) {
		String token = jwtService.generateRefreshToken(user.getUserName(),user.getUserRole().name());
		headers.add(org.springframework.http.HttpHeaders.SET_COOKIE,configureCookie("rt",token,refreshTokenExpiretion));
		RefreshToken rfToken= new RefreshToken();
		rfToken.setToken(token);
		rfToken.setBlocked(false);
		rfToken.setRefreshExpiretion(refreshTokenExpiretion);
		rfToken.setUser(user);
		refreshTokenRepo.save(rfToken);


	}
	private String configureCookie(String name, String value, long maxAge) {

		return ResponseCookie.from(name, value)
				.domain("localhost")
				.path("/")
				.httpOnly(true)
				.secure(false)
				.maxAge(Duration.ofMillis(maxAge))
				.sameSite("Lax")
				.build().toString();
	}








}
