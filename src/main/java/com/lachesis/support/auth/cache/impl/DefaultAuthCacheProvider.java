package com.lachesis.support.auth.cache.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lachesis.support.auth.cache.AuthCache;
import com.lachesis.support.auth.cache.AuthCacheProvider;

import net.sf.ehcache.Cache;

@Service("defaultAuthCacheProvider")
public class DefaultAuthCacheProvider implements AuthCacheProvider {
	
	@Autowired
	@Qualifier("authTokenCache")
	private Cache authTokenEhcache;
	
	@Autowired
	@Qualifier("userDetailsCache")
	private Cache userDetailsEhcache;
	
	private EhcacheBasedAuthCache authTokenCache;
	
	private EhcacheBasedAuthCache userDetailsCache;
	
	@PostConstruct
	public void PostConstruct(){
		this.init();
	}

	@Override
	public AuthCache getAuthTokenCache() {
		return authTokenCache;
	}

	@Override
	public AuthCache getUserDetailsCache() {
		return userDetailsCache;
	}
	
	private void init(){
		if(authTokenEhcache == null){
			throw new RuntimeException();
		}
		
		if(userDetailsEhcache == null){
			throw new RuntimeException();
		}
		
		authTokenCache = new EhcacheBasedAuthCache(authTokenEhcache);
		userDetailsCache = new EhcacheBasedAuthCache(userDetailsEhcache);
	}

}
