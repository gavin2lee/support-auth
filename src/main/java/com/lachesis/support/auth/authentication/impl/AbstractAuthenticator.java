package com.lachesis.support.auth.authentication.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lachesis.support.auth.authentication.Authenticator;
import com.lachesis.support.auth.vo.AuthToken;
import com.lachesis.support.auth.vo.UserDetails;
import com.lachesis.support.auth.vo.UsernamePasswordToken;

public abstract class AbstractAuthenticator implements Authenticator {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractAuthenticator.class);
	@Override
	public UserDetails authenticateWithCredential(UsernamePasswordToken credential) {
		if (credential == null) {
			LOG.error("credential should be specified.");
			throw new IllegalArgumentException();
		}

		String userid = credential.getUsername();
		String password = credential.getPassword();

		if (StringUtils.isBlank(userid) || StringUtils.isBlank(password)) {
			LOG.error("invalid credential");
			throw new IllegalArgumentException();
		}

		return doAuthenticateWithCredential(userid, password);
	}

	@Override
	public UserDetails authenticateWithAuthToken(AuthToken token) {
		if (token == null) {
			return null;
		}

		return doAuthenticateWithAuthToken(token);
	}

	protected abstract UserDetails doAuthenticateWithCredential(String userid, String password);
	protected abstract UserDetails doAuthenticateWithAuthToken(AuthToken token);
}
