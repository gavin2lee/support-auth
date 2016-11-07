package com.lachesis.support.auth.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lachesis.support.auth.model.AuthToken;
import com.lachesis.support.auth.model.Credential;
import com.lachesis.support.auth.model.UserDetails;
import com.lachesis.support.auth.provider.AuthenticationProvider;
import com.lachesis.support.auth.provider.EncryptionProvider;
import com.lachesis.support.auth.service.CentralizedAuthSupporter;
import com.lachesis.support.auth.token.AuthTokenValueAssembler;
import com.lachesis.support.auth.token.TokenStorage;
import com.lachesis.support.auth.verifier.TokenVerifier;

public class CentralizedAuthSupporterImpl implements CentralizedAuthSupporter {
	private static final Logger LOG = LoggerFactory.getLogger(CentralizedAuthSupporterImpl.class);
	private AuthenticationProvider authenticationProvider;
	private EncryptionProvider encryptionProvider;
	
	private TokenStorage tokenHolder;
	
	private TokenVerifier tokenVerifier;

	public String generateToken(String userid, String password, String terminalIpAddress) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("generating token for [userid:%s,ip:%s]", userid, terminalIpAddress));
		}

		String passwordMask = (StringUtils.isBlank(password) ? null : "***");

		if (StringUtils.isBlank(userid) || StringUtils.isBlank(password) || StringUtils.isBlank(terminalIpAddress)) {
			LOG.error(String.format("generating token failed for [userid:%s,password:%s,ip:%s]", userid, passwordMask,
					terminalIpAddress));
			throw new RuntimeException("generating token failed");
		}

		return doGenerateToken(userid, password, terminalIpAddress);
	}

	public UserDetails authenticate(String token, String terminalIpAddress) {
		if(LOG.isDebugEnabled()){
			LOG.debug(String.format("authenticating token for [token:%s,ip:%s]", token,terminalIpAddress));
		}
		
		if(StringUtils.isBlank(token) || StringUtils.isBlank(terminalIpAddress)){
			LOG.error(String.format("authenticating failed for [token:%s,ip:%s]", token,terminalIpAddress));
			return null;
		}
		
		return doAuthenticate(token, terminalIpAddress);
	}

	private String doGenerateToken(String userid, String password, String terminalIpAddress) {
		Credential credential = new Credential(userid, password);
		UserDetails userDetails = authenticationProvider.getAuthenticater().authenticateWithCredential(credential);
		if (userDetails == null) {
			LOG.debug(String.format("failed to authenticate [userid:%s]", userid));
			return null;
		}

		String plainTokenValue = assemblePlainTokenValue(userDetails, terminalIpAddress);
		String tokenValue = encryptionProvider.getEncrypter().encrypt(plainTokenValue);

		AuthToken token = assembleAuthToken(userid, password, terminalIpAddress, tokenValue);
		tokenHolder.store(token);

		return tokenValue;
	}

	private AuthToken assembleAuthToken(String userid, String password, String terminalIpAddress, String tokenValue) {
		return new AuthTokenGenerator(userid, password, terminalIpAddress, tokenValue).generate();
	}

	private String assemblePlainTokenValue(UserDetails userDetails, String terminalIpAddress) {
		return new AuthTokenValueAssembler(userDetails.getUserid(), terminalIpAddress).buildTokenValue();
	}
	
	private UserDetails doAuthenticate(String token, String terminalIpAddress){
		AuthToken authToken = tokenVerifier.verify(token, terminalIpAddress);
		if(authToken == null){
			LOG.warn(String.format("token [%s] is invalid", token));
			return null;
		}
		
		UserDetails userDetails = authenticationProvider.getAuthenticater().authenticateWithAuthToken(authToken);
		if(userDetails == null){
			LOG.warn(String.format("authenticating failed for [token:%s,ip:%s]", token,terminalIpAddress));
			return null;
		}
		return userDetails;
	}

}
