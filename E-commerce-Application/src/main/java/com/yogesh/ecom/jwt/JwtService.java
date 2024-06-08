package com.yogesh.ecom.jwt;

import java.security.Key;
import java.util.Base64.Decoder;
import java.util.Date;

import java.util.Stack;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.lang.Maps;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService

{   
	@Value("${myapp.jwt.secret}")
	private String secret;

	@Value("${myapp.jwt.access.expiration}")
	private long accessExpiry;

	@Value("${myapp.jwt.refresh.expiration}")
	private long refreshExpiry;


	public String generateAccessToken(String username,String role)
	{
		return generateToken(username, accessExpiry,role);
	}

	public String generateRefreshToken(String username,String role)
	{
		return generateToken(username, refreshExpiry,role);
	}

	//jwts is class is user to create Tokens
	private String generateToken(String username,long expiretion,String role) {
		return	Jwts.builder()
				.setClaims(Maps.of("role",role).build())
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))  //any other region it will works like out of Country also
				.setExpiration(new Date(System.currentTimeMillis() + expiretion))
				.signWith(getSignetureKey(),SignatureAlgorithm.HS256)
				.compact();// converts into String formate 
	}
	public String getuserName(String token)
	{
		return parseJwtToken(token).getSubject();
	}
	public String getUserRole(String token)
	{
		return parseJwtToken(token).get("role",String.class);
	}

	private Key getSignetureKey()
	{

		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
	}

	private Claims parseJwtToken(String token)
	{
		return Jwts.parserBuilder()
				.setSigningKey(getSignetureKey()).build()
				.parseClaimsJws(token)
				.getBody(); 
	}
	public Date getIssueDate(String token)
	{
		return   parseJwtToken(token).getIssuedAt();
	}
	
}