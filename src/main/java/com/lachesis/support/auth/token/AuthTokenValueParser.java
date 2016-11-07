package com.lachesis.support.auth.token;

import com.lachesis.support.auth.common.AuthConstants;

public class AuthTokenValueParser {
	private String plainTokenValue;
	private String terminalIpAdress;
	private String userid;

	public AuthTokenValueParser(String plainTokenValue) {
		super();
		this.plainTokenValue = plainTokenValue;
		String [] parts = plainTokenValue.split(AuthConstants.TOKEN_PARTS_DELIMITER);
		
		if(parts.length != 3){
			throw new RuntimeException("token should consist of 3 parts but "+parts.length);
		}
		
		userid = parts[0];
		terminalIpAdress = parts[1];
	}
	
	public String getTerminalIpAddress(){
		return terminalIpAdress;
	}
	
	public String getUserid(){
		return userid;
	}
	
	public String getTokenValue(){
		return this.plainTokenValue;
	}
}
