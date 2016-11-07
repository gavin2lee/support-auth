package com.lachesis.support.auth.verifier.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lachesis.support.auth.encryption.EncryptionProvider;
import com.lachesis.support.auth.model.AuthToken;
import com.lachesis.support.auth.token.AuthTokenValueParser;
import com.lachesis.support.auth.token.TokenStorage;

public class IpAndMaxIdleTimeTokenVerifyingStrategy extends AbstractTokenVerifyingStrategy {
	public static final int TOKEN_MAX_IDLE_SECONDS = 300;
	private static final Logger LOG = LoggerFactory.getLogger(IpAndMaxIdleTimeTokenVerifyingStrategy.class);
	
	
	private TokenStorage tokenHolder;
	
	private EncryptionProvider encryptionProvider;

	
	protected AuthToken doVerify(String token, String terminalIpAddress){
		String plainTokenValue = encryptionProvider.getEncrypter().decrypt(token);
		String parsedIp = new AuthTokenValueParser(plainTokenValue).getTerminalIpAddress();
		
		if(!parsedIp.equals(terminalIpAddress)){
			LOG.debug(String.format("[parsedIP:%s and IP:%s] mismatched", parsedIp, terminalIpAddress));
			return null;
		}
		
		AuthToken authToken = tokenHolder.retrieve(token);
		
		if(authToken == null){
			LOG.debug(String.format("cannot retrieve [token:%s]", token));
			return null;
		}
		
		if(isExpired(authToken)){
			LOG.debug(String.format("[token:%s] expired", token));
			return null;
		}
		
		tokenHolder.updateLastModifiedTime(authToken);
		return authToken;
	}
	
	protected boolean isExpired(AuthToken token){
		long lastModifiedTime = token.getLastModified().getTime();
		long currentTime = System.currentTimeMillis();
		
		long idleTime = (currentTime - lastModifiedTime);
		return (idleTime > (TOKEN_MAX_IDLE_SECONDS * 1000) );
	}

}
