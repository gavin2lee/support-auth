package com.lachesis.support.auth.authentication.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lachesis.support.auth.authentication.Authenticator;
import com.lachesis.support.auth.cache.AuthCache;
import com.lachesis.support.auth.cache.AuthCacheProvider;
import com.lachesis.support.auth.data.AuthUserService;
import com.lachesis.support.auth.model.AuthUser;
import com.lachesis.support.auth.vo.AuthToken;
import com.lachesis.support.auth.vo.UsernamePasswordToken;
import com.lachesis.support.auth.vo.SimpleUserDetails;
import com.lachesis.support.auth.vo.UserDetails;

@Service("localDatabaseAuthenticator")
public class LocalDatabaseAuthenticator implements Authenticator {
	private static final Logger LOG = LoggerFactory.getLogger(LocalDatabaseAuthenticator.class);

	@Autowired
	@Qualifier("databaseBasedAuthUserService")
	private AuthUserService authUserService;

	@Autowired
	private AuthCacheProvider authCacheProvider;

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

	protected UserDetails doAuthenticateWithAuthToken(AuthToken token) {
		UserDetails userDetailsToReturn = null;
		String tokenValue = token.getTokenValue();
		if (StringUtils.isNotBlank(tokenValue)) {
			userDetailsToReturn = findFromUserDetailsCache(tokenValue);
		}

		if (userDetailsToReturn != null) {
			return userDetailsToReturn;
		}

		if (StringUtils.isBlank(token.getUserid()) || StringUtils.isBlank(token.getPassword())) {
			LOG.warn("at least the userid and password should be provided");
			return null;
		}

		return doAuthenticateWithCredential(token.getUserid(), token.getPassword());
	}

	private UserDetails findFromUserDetailsCache(String tokenValue) {
		AuthCache userDetailsCache = authCacheProvider.getUserDetailsCache();
		UserDetails userDetails = (UserDetails) userDetailsCache.get(tokenValue);
		return userDetails;
	}

	protected UserDetails doAuthenticateWithCredential(String userid, String password) {
		AuthUser user = authUserService.findAuthUserByUserid(userid);
		if (user == null) {
			LOG.debug("authenticating failed for " + userid);
			return null;
		}

		if (!password.equals(user.getPassword())) {
			LOG.debug("password comparing failed for " + userid);
			return null;
		}

		UserDetails userDetails = new SimpleUserDetails(String.valueOf(user.getId()), user.getUsername(),
				user.getPassword(), null);
		return userDetails;
	}

}
