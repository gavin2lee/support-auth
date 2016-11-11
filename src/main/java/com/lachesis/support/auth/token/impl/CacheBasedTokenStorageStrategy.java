package com.lachesis.support.auth.token.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lachesis.support.auth.cache.AuthCacheProvider;
import com.lachesis.support.auth.token.TokenStorageStrategy;
import com.lachesis.support.auth.vo.AuthToken;

public class CacheBasedTokenStorageStrategy implements TokenStorageStrategy {
	private static final Logger LOG = LoggerFactory.getLogger(CacheBasedTokenStorageStrategy.class);
	
	private AuthCacheProvider authCacheProvider;

	@Override
	public void save(AuthToken authToken) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(AuthToken authToken) {
		// TODO Auto-generated method stub

	}

	@Override
	public AuthToken find(String tokenValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuthToken remove(String tokenValue) {
		// TODO Auto-generated method stub
		return null;
	}

}
