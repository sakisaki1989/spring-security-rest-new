package com.springsecurity.model;

import java.io.Serializable;

import org.springframework.security.core.userdetails.UserDetails;

public class JwtUserResponse implements Serializable {

	private static final long serialVersionUID = -1515454964949494L;
	private Users user;
	
	
	public JwtUserResponse(Users user) {
		this.user = user;
	}


	/**
	 * @return the user
	 */
	public Users getUser() {
		return user;
	}
	
	
	
}