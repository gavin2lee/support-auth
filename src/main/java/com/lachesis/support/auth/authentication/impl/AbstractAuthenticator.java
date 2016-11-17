package com.lachesis.support.auth.authentication.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lachesis.support.auth.authentication.Authenticator;
import com.lachesis.support.auth.vo.UserDetails;
import com.lachesis.support.auth.vo.UsernamePasswordToken;

public abstract class AbstractAuthenticator implements Authenticator {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractAuthenticator.class);
	@Override
	public UserDetails authenticate(UsernamePasswordToken credential) {
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

		return doAuthenticate(userid, password);
	}

	protected abstract UserDetails doAuthenticate(String userid, String password);
}
