package com.lachesis.support.auth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lachesis.support.auth.authentication.AuthenticationProvider;
import com.lachesis.support.auth.encryption.EncryptionProvider;
import com.lachesis.support.auth.model.AuthToken;
import com.lachesis.support.auth.model.Credential;
import com.lachesis.support.auth.model.UserDetails;
import com.lachesis.support.auth.token.AuthTokenValueAssembler;
import com.lachesis.support.auth.token.AuthTokenManager;
import com.lachesis.support.auth.verifier.TokenVerifier;

public class DefaultCentralizedAuthSupporter extends AbstractCentralizedAuthSupporter {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultCentralizedAuthSupporter.class);
	private AuthenticationProvider authenticationProvider;
	private EncryptionProvider encryptionProvider;
	
	private AuthTokenManager tokenHolder;
	
	private TokenVerifier tokenVerifier;

	protected String doGenerateToken(String userid, String password, String terminalIpAddress) {
		Credential credential = new Credential(userid, password);
		UserDetails userDetails = authenticationProvider.getAuthenticator().authenticateWithCredential(credential);
		if (userDetails == null) {
			LOG.debug(String.format("failed to authenticate [userid:%s]", userid));
			return null;
		}

		String plainTokenValue = assemblePlainTokenValue(userDetails, terminalIpAddress);
		String tokenValue = encryptionProvider.getEncrypter().encrypt(plainTokenValue);

		AuthToken token = assembleAuthToken(userid, password, terminalIpAddress, tokenValue);
		tokenHolder.store(token);
		
		//TODO cache the userDetails

		return tokenValue;
	}

	private AuthToken assembleAuthToken(String userid, String password, String terminalIpAddress, String tokenValue) {
		return (new AuthTokenGenerator(userid, password, terminalIpAddress, tokenValue).generate());
	}

	private String assemblePlainTokenValue(UserDetails userDetails, String terminalIpAddress) {
		return (new AuthTokenValueAssembler(userDetails.getUserid(), terminalIpAddress).buildTokenValue());
	}
	
	protected UserDetails doAuthenticate(String token, String terminalIpAddress){
		AuthToken authToken = tokenVerifier.verify(token, terminalIpAddress);
		if(authToken == null){
			LOG.warn(String.format("token [%s] is invalid", token));
			return null;
		}
		
		UserDetails userDetails = authenticationProvider.getAuthenticator().authenticateWithAuthToken(authToken);
		if(userDetails == null){
			LOG.warn(String.format("authenticating failed for [token:%s,ip:%s]", token,terminalIpAddress));
			return null;
		}
		return userDetails;
	}
}
