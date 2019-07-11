package com.springsecurity.controller;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.model.JwtRequest;
import com.springsecurity.model.JwtUserResponse;
import com.springsecurity.model.Users;
import com.springsecurity.security.JwtTokenUtil;
import com.springsecurity.validator.UserValidator;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class JwtRegistrationController {
	
    @Autowired
    private UserValidator userValidator;
    
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	JdbcUserDetailsManager  jdbcUserDetailsManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PostMapping("/registration")
	public ResponseEntity<?> addUser(@RequestBody JwtRequest registrationRequest, BindingResult bindingResult) {
		Users user = new Users(registrationRequest.getUsername(), passwordEncoder.encode(registrationRequest.getPassword()) , 1 ,  registrationRequest.getRole());
		userValidator.validate(user, bindingResult);
		
		 if (bindingResult.hasErrors()) {
	            return ResponseEntity
	                    .status(HttpStatus.FORBIDDEN)
	                    .body(bindingResult.getFieldError());
	    }
		
		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
		
		UserDetails userDetails = (UserDetails)new User(user.getUsername(), 
				user.getPassword() , Arrays.asList(authority));
		final String token = jwtTokenUtil.generateToken(userDetails);
		user.setToken(token);
		jdbcUserDetailsManager.createUser(userDetails);
		return ResponseEntity.ok(new JwtUserResponse(user));
	}












}
