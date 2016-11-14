package com.lachesis.support.auth.authentication.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lachesis.support.auth.authentication.AuthenticationProvider;
import com.lachesis.support.auth.authentication.Authenticator;

@Service("defaultAuthenticationProvider")
public class DefaultAuthenticationProvider implements AuthenticationProvider {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultAuthenticationProvider.class);

	@Autowired
	private Authenticator authenticator;
	
	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}

	@Override
	public Authenticator getAuthenticator() {
		if(this.authenticator == null){
			throw new RuntimeException();
		}
		
		if(LOG.isDebugEnabled()){
			LOG.debug(String.format("current authenticator is [%s]", authenticator.getClass().getName()));
		}
		return authenticator;
	}

}
