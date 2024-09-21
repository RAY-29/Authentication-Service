package com.example.authentication.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private ProjectManagerService projectManagerService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		String username = null;
		String jwt = null;
		
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			jwt = authHeader.substring(7);
			try {
				username = jwtUtil.extractUsername(jwt);
			}
			catch(IllegalArgumentException e) {
				logger.error("en exception occured during getting username from token");
			}
			catch(ExpiredJwtException e) {
				logger.error("token is expired and not valid anymore");
			}
			catch(SignatureException e) {
				logger.error("username or password incorrect");
			}
		}
		else {
			logger.warn("couldn't find bearer string, will ignore the header");
		}
		
		
	}
	
	
}
