package com.lachesis.support.auth.service;

import com.lachesis.support.auth.model.UserDetails;

public interface CentralizedAuthSupporter {
	String generateToken(String userid, String password, String terminalIpAddress);
	UserDetails authenticate(String token, String terminalIpAddress);
	void dismissToken(String token);
}
