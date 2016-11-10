package com.lachesis.support.auth.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lachesis.support.auth.model.UserDetails;
import com.lachesis.support.auth.service.CentralizedAuthSupporter;

public abstract class AbstractCentralizedAuthSupporter implements CentralizedAuthSupporter {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractCentralizedAuthSupporter.class);

	public String generateToken(String userid, String password, String terminalIpAddress) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("generating token for [userid:%s,ip:%s]", userid, terminalIpAddress));
		}

		String passwordMask = (isBlank(password) ? null : "***");

		if (isBlank(userid) || isBlank(password) || isBlank(terminalIpAddress)) {
			LOG.error(String.format("generating token failed for [userid:%s,password:%s,ip:%s]", userid, passwordMask,
					terminalIpAddress));
			throw new RuntimeException("generating token failed");
		}

		return doGenerateToken(userid, password, terminalIpAddress);
	}

	public UserDetails authenticate(String token, String terminalIpAddress) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("authenticating token for [token:%s,ip:%s]", token, terminalIpAddress));
		}

		if (isBlank(token) || isBlank(terminalIpAddress)) {
			LOG.error(String.format("authenticating failed for [token:%s,ip:%s]", token, terminalIpAddress));
			return null;
		}

		return doAuthenticate(token, terminalIpAddress);
	}

	@Override
	public void dismissToken(String token) {
		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("dismissing token:%s", token));
		}
		
		if(isBlank(token)){
			return;
		}

		// TODO

	}
	
	protected boolean isBlank(String s){
		return StringUtils.isBlank(s);
	}

	protected abstract String doGenerateToken(String userid, String password, String terminalIpAddress);

	protected abstract UserDetails doAuthenticate(String token, String terminalIpAddress);
}
