package com.lachesis.support.auth.cache;

public interface AuthCacheProvider {
	AuthCache getAuthCacheInstance(String cacheName);
}
