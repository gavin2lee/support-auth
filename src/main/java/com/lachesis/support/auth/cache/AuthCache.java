package com.lachesis.support.auth.cache;

public interface AuthCache {
	void put(String key,Object value);
	Object get(String key);
	void remove(String key);
}
