package com.lachesis.support.auth.cache;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.lachesis.support.auth.vo.AuthToken;
import com.lachesis.support.auth.vo.Authority;
import com.lachesis.support.auth.vo.SimpleUserDetails;
import com.lachesis.support.auth.vo.UserDetails;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Ignore
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

		authTokenCache.put(new Element(t.getTokenValue(), t));

		AuthToken ret = (AuthToken) authTokenCache.get(t.getTokenValue()).getObjectValue();
		Assert.assertNotNull("check if the element got from cache is not null.", ret);
		Assert.assertEquals("check token value", t.getTokenValue(), ret.getTokenValue());

		authTokenCache.remove(t.getTokenValue());
		Element elementAfterRemove = authTokenCache.get(t.getTokenValue());
		Assert.assertNull("should be null after remove", elementAfterRemove);
	}

	@Test
	public void testCacheTokenInBatch() {
		AuthToken tokenToRemove = null;
		AuthToken tokenToUpdate = null;
		int maxSizeToCache = 2000;
		for (int i = 0; i < 2000; i++) {
			AuthToken t = mockAuthToken();
			authTokenCache.put(new Element(t.getTokenValue(), t));

			if (i == 10) {
				tokenToRemove = t;
			}

			if (i == 11) {
				tokenToUpdate = t;
			}
		}

		authTokenCache.remove(tokenToRemove.getTokenValue());
		
		tokenToUpdate.setTerminalIpAddress("255.255.255.255");
		try {
			authTokenCache.tryWriteLockOnKey(tokenToUpdate.getTokenValue(), 1000);
			authTokenCache.replace(new Element(tokenToUpdate.getTokenValue(), tokenToUpdate));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			authTokenCache.releaseWriteLockOnKey(tokenToUpdate.getTokenValue());
		}
		
		List<?> keys = authTokenCache.getKeys();
		
		AuthToken retTokenToUpdate = (AuthToken) authTokenCache.get(tokenToUpdate.getTokenValue()).getObjectValue();
		Assert.assertNotNull("check token to update", retTokenToUpdate);
		Assert.assertEquals("check ip of token to update", "255.255.255.255", retTokenToUpdate.getTerminalIpAddress());

		Assert.assertEquals("check cache size", (maxSizeToCache - 1), authTokenCache.getSize());
		Assert.assertEquals("check size of one element removed cache", (maxSizeToCache - 1), keys.size());
	}

	@Test
	public void testCacheUserDetails() {
		UserDetails userDetails = mockUserDetails();
		AuthToken token = mockAuthToken();

		userDetailsCache.put(new Element(token.getTokenValue(), userDetails));

		UserDetails userDetailsFromCache = (UserDetails) userDetailsCache.get(token.getTokenValue()).getObjectValue();

		Assert.assertNotNull("check if user details from cahce is null", userDetailsFromCache);
		Assert.assertEquals("check the password of user details", "123456", ((SimpleUserDetails)userDetailsFromCache).getPassword());
	}

	private UserDetails mockUserDetails() {
		long seq = userDetailsSequence.getAndIncrement();
		String userid = "test-" + seq;
		String password = "123456";
		List<Authority> authorities = null;

		return new SimpleUserDetails(String.valueOf(seq), userid, password, authorities);
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
