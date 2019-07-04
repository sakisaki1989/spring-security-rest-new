package com.luv2code.springsecurity.demo.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.luv2code.springsecurity.demo.DAO.UserDAO;
import com.luv2code.springsecurity.demo.model.Users;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDAO userDAO;
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Users userInfo = userDAO.getUserInfo(username);
		GrantedAuthority authority = new SimpleGrantedAuthority(userInfo.getRole());
		UserDetails userDetails = (UserDetails)new User(userInfo.getUsername(), 
				"$2a$10$slYQmyNdGzTn7ZLB.XBChFOC9f6kFjAqPhccn.P6DxlWXx2lPk1C3G6", Arrays.asList(authority));
		return userDetails;
	}

}