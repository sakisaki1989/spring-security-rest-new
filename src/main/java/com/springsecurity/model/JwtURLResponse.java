package com.springsecurity.model;

import java.io.Serializable;

public class JwtURLResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String url;

	public JwtURLResponse(String url) {
		this.url = url;
	}

	public String getURL() {
		return this.url;
	}
}