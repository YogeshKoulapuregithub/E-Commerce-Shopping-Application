package com.yogesh.ecom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yogesh.ecom.jwt.JwtService;
import com.yogesh.ecom.requestDto.AuthRequst;
import com.yogesh.ecom.requestDto.OtpRequest;
import com.yogesh.ecom.requestDto.UserRequst;
import com.yogesh.ecom.responseDto.AuthResponse;
import com.yogesh.ecom.responseDto.UserResponse;
import com.yogesh.ecom.service.UserService;
import com.yogesh.ecom.utility.ResponseStructure;
import com.yogesh.ecom.utility.SimpleResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin(allowCredentials = "true",origins = "http://localhost:5173/")
@RequestMapping("/api/v1")
public class UserController {
	
	private UserService userService;
	private JwtService jwtService;

	@PostMapping("/register")
	public ResponseEntity<SimpleResponseStructure> userRegistration(@RequestBody UserRequst userRequst) {
		return userService.userRegistration(userRequst);
	}

	@PostMapping("/verify-email")
	public ResponseEntity<ResponseStructure<UserResponse>> verifyOpt(@RequestBody OtpRequest otpRequest) {
		return userService.verifyOtp(otpRequest);
	}
	//	@GetMapping("/generate")
	//	public ResponseEntity<String> generateToken()
	//	{
	//		return new ResponseEntity<String>(jwtService.generateAccessToken("Yogesh"),HttpStatus.OK);
	//	}
	@PostMapping("/login")
	public ResponseEntity<ResponseStructure<AuthResponse>> logIn(@RequestBody AuthRequst authRequst)
	{
		return userService.logIn(authRequst);
	}
	@PostMapping("/logout")
	public ResponseEntity<ResponseStructure<AuthResponse>> logOut(@CookieValue(name = "at",required = false)String accesToken,
			@CookieValue(name = "rt",required = false)String refreshToken)
	{
		return userService.logOut(accesToken,refreshToken);
	}
	@PostMapping("/login/refresh")
	public ResponseEntity<ResponseStructure<AuthResponse>> refreshLogin(@CookieValue(name = "at",required = false)String accesToken,
			@CookieValue(name = "rt",required = false)String refreshToken)
	{
		return userService.refreshLogin(accesToken,refreshToken);
	}










}
