package com.lachesis.support.auth.authenticater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lachesis.support.auth.model.AuthToken;
import com.lachesis.support.auth.model.Credential;
import com.lachesis.support.auth.model.UserDetails;
import com.lachesis.support.auth.provider.AuthenticationProvider;

public class DefaultAuthenticater implements Authenticater{
	private static final Logger LOG = LoggerFactory.getLogger(DefaultAuthenticater.class);
	
	private AuthenticationProvider authenticationProvider;
	
	public UserDetails authenticateWithCredential(Credential credential){
		if(LOG.isDebugEnabled()){
			LOG.debug("using AuthenticationProvider ["+authenticationProvider.getClass()+"]");
		}
		
		UserDetails userDetails = null;
		try{
			//userDetails = authenticationProvider.authenticate(credential);
		}catch(Exception e){
			LOG.error("authenticating failed.", e);
			return null;
		}
		return userDetails;
	}

	public UserDetails authenticateWithAuthToken(AuthToken token) {
		//TODO
		return null;
	}
}
