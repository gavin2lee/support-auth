package com.lachesis.support.auth.token;

public class AuthTokenValueParser {
	public static final String TOKEN_PARTS_DELIMITER = "|";
	private String plainTokenValue;
	private String parts[];

	public AuthTokenValueParser(String plainTokenValue) {
		super();
		this.plainTokenValue = plainTokenValue;
		parts = plainTokenValue.split(TOKEN_PARTS_DELIMITER);
		
		if(parts.length != 3){
			throw new RuntimeException("token should consist of 3 parts but "+parts.length);
		}
	}
	
	public String getTerminalIpAddress(){
		return parts[1];
	}
	
	public String getUserid(){
		return parts[0];
	}
	
	public String getTokenValue(){
		return this.plainTokenValue;
	}
}
