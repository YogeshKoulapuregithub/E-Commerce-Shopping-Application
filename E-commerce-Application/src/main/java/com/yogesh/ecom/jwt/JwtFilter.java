package com.yogesh.ecom.jwt;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.yogesh.ecom.exception.UserTokenBlockedStateException;
import com.yogesh.ecom.model.AccessToken;
import com.yogesh.ecom.repository.AccesTokenRepository;
import com.yogesh.ecom.repository.RefreshTokenRepo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter 
{
	
	private AccesTokenRepository accesTokenRepository;
	private RefreshTokenRepo refreshTokenRepo;
	private JwtService jwtService;
	
	
	public JwtFilter(AccesTokenRepository accesTokenRepository, RefreshTokenRepo refreshTokenRepo,
			JwtService jwtService) {
		super();
		this.accesTokenRepository = accesTokenRepository;
		this.refreshTokenRepo = refreshTokenRepo;
		this.jwtService = jwtService;
	}


	// servelet filter chain 
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String at=null;
		String rt=null;
		Cookie[] cookies = request.getCookies();
		if(cookies!=null)
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("at")) at=cookie.getValue();
				if(cookie.getName().equals("rt")) rt=cookie.getValue();
			}

		if(at !=null && rt!=null)
		{
			if(accesTokenRepository.existsByTokenAndBlocked(at,true)&& refreshTokenRepo.existsByTokenAndBlocked(rt,true)) {
				throw new UserTokenBlockedStateException("User is in blocked State");
			}
			String UserName = jwtService.getuserName(at);
			String userRole = jwtService.getUserRole(at);
			
			//System.err.println(UserName+" "+userRole);
			
			if(UserName!= null && 
				userRole!=null &&	SecurityContextHolder.getContext().getAuthentication()==null) {
				UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken
						(UserName, null,
						Collections.singleton(new SimpleGrantedAuthority(userRole)));
				authenticationToken.setDetails(new WebAuthenticationDetails(request));
				 SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
