package com.lachesis.support.auth.cache;

public interface AuthCache {
	void put(String key,Object val);
	Object get(String key);
}
