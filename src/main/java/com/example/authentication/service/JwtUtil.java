package com.example.authentication.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

	private String secretKey="${jwt.secret}";
	private String tokenExpiryTime = "${token.expiry.time}";
	
	//Extract user_name from a JWT Token
	public String extractUsername(String token){
		return extractClaims(token,Claims::getSubject);
	}
	
	//Extract Expiration Date from a JWT Token
	public Date extractExpiryDate(String token) {
		return extractClaims(token, Claims::getExpiration);
	}
	
	//Get Status of a JWT Token
	public boolean isTokenExpired(String token) {
		return extractExpiryDate(token).before(new Date());
	}
	
	//Generate JWT Token for a userDeatils
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims,userDetails.getUsername());
	}
	
	//Create Token for a User_name
	private String createToken(Map<String, Object> claims, String username) {
		int tokenExpiry = Integer.parseInt(tokenExpiryTime);
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+tokenExpiry*60*1000))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}
	
	//Validate a token with userDetails
	public boolean validateToken(String token,UserDetails userDetails) {
		String tokenUserName = extractUsername(token);
		String userDetailsUserName = userDetails.getUsername();
		return (tokenUserName.equals(userDetailsUserName) && !isTokenExpired(token));
	}
	
	//Validate a token
	public boolean validateToken(String token) {
		return !isTokenExpired(token);
	}

	//get All Claims from a JWT token
	public Claims extractAllClaims(String token) {
		String token1 = token.trim().replaceAll("\\0xfffd", "");
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token1).getBody();
	}
	
	//get particular claims from token based of function provided
	public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
		Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
}
