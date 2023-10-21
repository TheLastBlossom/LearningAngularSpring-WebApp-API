package com.ervinaldo.springboot.backend.apirest.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ervinaldo.springboot.backend.apirest.springsecurity.AuthenticationReq;
import com.ervinaldo.springboot.backend.apirest.springsecurity.TokenInfo;
import com.ervinaldo.springboot.backend.apirest.springsecurity.JwtUtilService;   
@RestController
public class AuthenticationRestController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService usuarioDetailsService;
	@Autowired
	private JwtUtilService jwtUtilService;
	
	@PostMapping("/api/authenticate")
	public ResponseEntity<TokenInfo> authenticate(@RequestBody AuthenticationReq authenticationReq) {		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationReq.getUser(),
					authenticationReq.getPassword()));

			final UserDetails userDetails = usuarioDetailsService.loadUserByUsername(authenticationReq.getUser());
			System.out.println(userDetails.getUsername());
			final String jwt = jwtUtilService.generateToken(userDetails);

			return ResponseEntity.ok(new TokenInfo(jwt));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TokenInfo("INCORRECT CREDENTIALS"));
		}
	}
}
