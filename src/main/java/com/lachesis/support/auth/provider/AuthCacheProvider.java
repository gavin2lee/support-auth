package com.lachesis.support.auth.provider;

import com.lachesis.support.auth.cache.AuthCache;

public interface AuthCacheProvider {
	AuthCache getAuthCacheInstance(String cacheName);
}
