package com.yogesh.ecom.jwt;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.yogesh.ecom.exception.UserTokenBlockedStateException;
import com.yogesh.ecom.repository.AccesTokenRepository;
import com.yogesh.ecom.repository.RefreshTokenRepo;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RefreshFilter extends OncePerRequestFilter 
{
	private RefreshTokenRepo refreshTokenRepo;
	private JwtService jwtService;
	
	public RefreshFilter( RefreshTokenRepo refreshTokenRepo,
			JwtService jwtService) {
		this.refreshTokenRepo = refreshTokenRepo;
		this.jwtService = jwtService;
	}


	// servelet filter chain 
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String rt=null;
		Cookie[] cookies = request.getCookies();
		if(cookies!=null)
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("rt")) rt=cookie.getValue();
			}

		if(rt!=null)
		{
			if( refreshTokenRepo.existsByTokenAndBlocked(rt,true)) {
				throw new UserTokenBlockedStateException("User is in blocked State");
			}
			String UserName = jwtService.getuserName(rt);
			String userRole = jwtService.getUserRole(rt);
			
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
