package com.lachesis.support.auth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lachesis.support.auth.authentication.AuthenticationProvider;
import com.lachesis.support.auth.cache.AuthCacheProvider;
import com.lachesis.support.auth.encryption.EncryptionProvider;
import com.lachesis.support.auth.token.AuthTokenManager;
import com.lachesis.support.auth.token.AuthTokenValueAssembler;
import com.lachesis.support.auth.verifier.TokenVerifier;
import com.lachesis.support.auth.vo.AuthToken;
import com.lachesis.support.auth.vo.AuthenticationToken;
import com.lachesis.support.auth.vo.UserDetails;

@Service("defaultCentralizedAuthSupporter")
public class DefaultCentralizedAuthSupporter extends AbstractCentralizedAuthSupporter {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultCentralizedAuthSupporter.class);
	@Autowired
	private AuthenticationProvider authenticationProvider;
	@Autowired
	private EncryptionProvider encryptionProvider;
	@Autowired
	private AuthTokenManager tokenManager;
	@Autowired
	private TokenVerifier tokenVerifier;
	@Autowired
	private AuthCacheProvider authCacheProvider;

	protected String doGenerateToken(String userid, String password, String terminalIpAddress) {
		AuthenticationToken credential = new AuthenticationToken(userid, password);
		UserDetails userDetails = authenticationProvider.getAuthenticator().authenticateWithCredential(credential);
		if (userDetails == null) {
			LOG.debug(String.format("failed to authenticate [userid:%s]", userid));
			return null;
		}

		String plainTokenValue = assemblePlainTokenValue(userDetails, terminalIpAddress);
		String tokenValue = encryptionProvider.getEncrypter().encrypt(plainTokenValue);

		AuthToken token = assembleAuthToken(userid, password, terminalIpAddress, tokenValue);
		tokenManager.store(token);
		
		cacheUserDetails(tokenValue, userDetails);

		return tokenValue;
	}
	
	private void cacheUserDetails(String tokenValue, UserDetails userDetails){
		authCacheProvider.getUserDetailsCache().put(tokenValue, userDetails);
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

	@Override
	protected void doDismissToken(String token) {
		tokenManager.dismiss(token);
	}
}
