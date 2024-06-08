package com.yogesh.ecom.security;

import java.beans.Customizer;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.yogesh.ecom.jwt.JwtFilter;
import com.yogesh.ecom.jwt.JwtService;
import com.yogesh.ecom.jwt.RefreshFilter;
import com.yogesh.ecom.repository.AccesTokenRepository;
import com.yogesh.ecom.repository.RefreshTokenRepo;

import lombok.AllArgsConstructor;
@EnableMethodSecurity
@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private CustumUserDetailService custumUserDetailService;
	private AccesTokenRepository accesTokenRepository;
	private RefreshTokenRepo refreshTokenRepo;
	private JwtService jwtService;

	@Bean
	PasswordEncoder passwordEncoder()//its interface create object by the subclass BCryptPasswordEncoder(more secure and popular)
	{
		return new BCryptPasswordEncoder(12);
	}

	@Bean
	AuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		authenticationProvider.setUserDetailsService(custumUserDetailService);
		return authenticationProvider;
	}

	@Bean
	@Order(1)
	SecurityFilterChain publicFilterChain(HttpSecurity httpSecurity) throws Exception
	{

		//cross side 
		return httpSecurity.csrf(csrf->csrf.disable())
				.securityMatchers(matchers->matchers.requestMatchers("/api/v1/login/**","/api/v1/register","/api/v1/verify-email","/api/v1/products","/api/v1/image/{imageId}"))
				.authorizeHttpRequests(authorize->authorize.requestMatchers("/api/v1/login/**","/api/v1/register","/api/v1/verify-email","/api/v1/products","/api/v1/image/{imageId}")
						.permitAll())
				.authenticationProvider(authenticationProvider())
				.build();
	}
	@Bean
	@Order(2)
	SecurityFilterChain refreshFilterChain(HttpSecurity httpSecurity) throws Exception
	{
		return httpSecurity.csrf(csrf->csrf.disable())
				.securityMatchers(matchers->matchers.requestMatchers("/api/v1/login/refresh/**"))
				.authorizeHttpRequests(authorize->authorize.anyRequest().authenticated())
				.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(new RefreshFilter(refreshTokenRepo,jwtService),UsernamePasswordAuthenticationFilter.class).build();

	}

	@Bean
	@Order(3)
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
	{

		//cross side 
		return httpSecurity.csrf(csrf->csrf.disable())
				.securityMatchers(matchers->matchers.requestMatchers("/api/v1/**"))
				.authorizeHttpRequests(authorize->authorize.anyRequest().authenticated())
				.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(new JwtFilter(accesTokenRepository, refreshTokenRepo, jwtService), UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();

	}

}
