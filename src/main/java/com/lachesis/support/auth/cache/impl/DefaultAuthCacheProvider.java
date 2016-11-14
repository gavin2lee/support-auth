package com.lachesis.support.auth.cache.impl;

import com.lachesis.support.auth.cache.AuthCache;
import com.lachesis.support.auth.cache.AuthCacheProvider;

public class DefaultAuthCacheProvider implements AuthCacheProvider {

	@Override
	public AuthCache getAuthTokenCache() {
		return null;
	}

	@Override
	public AuthCache getUserDetailsCache() {
		return null;
	}

}
