package com.lachesis.support.auth.vo;

public class AuthenticationToken {
	private String username;
	private String password;
	
	public AuthenticationToken(String username,String password){
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
}
