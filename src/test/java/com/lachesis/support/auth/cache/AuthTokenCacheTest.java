package com.lachesis.support.auth.cache;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Before;
import org.junit.Test;

import com.lachesis.support.auth.model.AuthToken;
import com.lachesis.support.auth.model.Authority;
import com.lachesis.support.auth.model.SimpleUserDetails;
import com.lachesis.support.auth.model.UserDetails;

import junit.framework.Assert;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class AuthTokenCacheTest {
	CacheManager manager;
	Cache authTokenCache;
	Cache userDetailsCache;
	String ehcacheConfigFilename = "ehcache.xml";
	String authTokenCacheName = "authTokenCache";
	String userDetailsCacheName = "userDetailsCache";
	
	private AtomicLong tokenSequence = new AtomicLong();
	private AtomicLong userDetailsSequence = new AtomicLong();
	

	@Before
	public void setUp() throws Exception {
		manager = CacheManager
				.create(AuthTokenCacheTest.class.getClassLoader().getResourceAsStream(ehcacheConfigFilename));
		authTokenCache = manager.getCache(authTokenCacheName);
		userDetailsCache = manager.getCache(userDetailsCacheName);
		Assert.assertNotNull("make sure cache manager is not null", manager);
		Assert.assertNotNull("make sure auth token cache is not null", authTokenCache);
		Assert.assertNotNull("make sure user details cahce is not null", userDetailsCache);
	}

	@Test
	public void testPutOneToken() {
		AuthToken t = mockAuthToken();
		
		authTokenCache.put(new Element(t.getTokenValue(),t));
		
		AuthToken ret = (AuthToken) authTokenCache.get(t.getTokenValue()).getObjectValue();
		Assert.assertNotNull("check if the element got from cache is not null.", ret);
		Assert.assertEquals("check token value", t.getTokenValue(), ret.getTokenValue());
	}
	
	@Test
	public void testPutTokenInBatch(){
		AuthToken tokenToRemove = null;
		for(int i=0; i<2000; i++){
			AuthToken t = mockAuthToken();
			authTokenCache.put(new Element(t.getTokenValue(),t));
			
			if(i == 10){
				tokenToRemove = t;
			}
		}
		
		System.out.println("TO REMOVE :"+tokenToRemove.getTerminalIpAddress());
		authTokenCache.remove(tokenToRemove.getTokenValue());
		
		List<?> keys = authTokenCache.getKeys();
		
		for(Object key:keys){
			Element entry = authTokenCache.get(key);
			AuthToken at = (AuthToken) entry.getObjectValue();
			System.out.println(at.getTerminalIpAddress());
		}
	}
	
	@Test
	public void testPutUserDetails(){
		UserDetails userDetails = mockUserDetails();
		AuthToken token = mockAuthToken();
		
		userDetailsCache.put(new Element(token.getTokenValue(), userDetails));
		
		UserDetails userDetailsFromCache = (UserDetails) userDetailsCache.get(token.getTokenValue()).getObjectValue();
		
		Assert.assertNotNull("check if user details from cahce is null", userDetailsFromCache);
		Assert.assertEquals("check the password of user details", "123456", userDetailsFromCache.getPassword());
	}
	
	private UserDetails mockUserDetails(){
		long seq = userDetailsSequence.getAndIncrement();
		String userid = "test-"+seq;
		String password = "123456";
		List<Authority> authorities = null;
		
		return new SimpleUserDetails(userid, password, authorities);
	}
	
	private AuthToken mockAuthToken() {
		AuthToken t = new AuthToken();
		long oid = tokenSequence.incrementAndGet();
		t.setOid(oid);
		t.setActive(true);
		t.setLastModified(new Date());
		t.setTerminalIpAddress("10.10.10." + oid);
		t.setPassword("123456");
		t.setTokenValue(UUID.randomUUID().toString());
		return t;
	}

}
