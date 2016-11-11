package com.lachesis.support.auth.cache;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Before;
import org.junit.Test;

import com.lachesis.support.auth.model.AuthToken;

import junit.framework.Assert;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class AuthTokenCacheTest {
	CacheManager manager;
	Cache authTokenCache;
	String ehcacheConfigFilename = "ehcache.xml";
	String authTokenCacheName = "authTokenCache";
	private AtomicLong oidSequence = new AtomicLong();
	

	@Before
	public void setUp() throws Exception {
		manager = CacheManager
				.create(AuthTokenCacheTest.class.getClassLoader().getResourceAsStream(ehcacheConfigFilename));
		authTokenCache = manager.getCache(authTokenCacheName);
		Assert.assertNotNull("make sure cache manager is not null", manager);
		Assert.assertNotNull("make sure auth token cache is not null", authTokenCache);
	}

	@Test
	public void testPutOneToken() {
		AuthToken t = generateAuthToken();
		
		authTokenCache.put(new Element(t.getTokenValue(),t));
		
		AuthToken ret = (AuthToken) authTokenCache.get(t.getTokenValue()).getObjectValue();
		Assert.assertNotNull("check if the element got from cache is not null.", ret);
		Assert.assertEquals("check token value", t.getTokenValue(), ret.getTokenValue());
	}
	
	@Test
	public void testPutTokenInBatch(){
		AuthToken tokenToRemove = null;
		for(int i=0; i<2000; i++){
			AuthToken t = generateAuthToken();
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
	
	private AuthToken generateAuthToken() {
		AuthToken t = new AuthToken();
		long oid = oidSequence.incrementAndGet();
		t.setOid(oid);
		t.setActive(true);
		t.setLastModified(new Date());
		t.setTerminalIpAddress("10.10.10." + oid);
		t.setPassword("123456");
		t.setTokenValue(UUID.randomUUID().toString());
		return t;
	}

}
