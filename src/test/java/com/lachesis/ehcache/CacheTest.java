package com.lachesis.ehcache;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

//import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.lachesis.support.auth.model.AuthToken;

import junit.framework.Assert;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.RegisteredEventListeners;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

public class CacheTest {
	private AtomicLong oidSequence = new AtomicLong();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCache() {
		String cacheName = "AuthTokenCache";
		int maxElementsInMemory = 100;
		MemoryStoreEvictionPolicy memoryStoreEvictionPolicy = MemoryStoreEvictionPolicy.LRU;
		boolean overflowToDisk = false;
        String diskStorePath = null;
        boolean eternal = false;
        long timeToLiveSeconds = 0;
        long timeToIdleSeconds = 5;
        boolean diskPersistent = false;
        long diskExpiryThreadIntervalSeconds = 10;
        RegisteredEventListeners registeredEventListeners = null;
        
        int maxSizeToStore = 110;
		
		Cache authTokenCache = new Cache(cacheName,
                maxElementsInMemory,
                memoryStoreEvictionPolicy,
                overflowToDisk,
                diskStorePath,
                eternal,
                timeToLiveSeconds,
                timeToIdleSeconds,
                diskPersistent,
                diskExpiryThreadIntervalSeconds,
                registeredEventListeners);
		
		
		Assert.assertEquals("check cache name.", cacheName, authTokenCache.getName());
		
		
		for(int i = 0; i < maxSizeToStore; i++){
			AuthToken authToken = generateAuthToken();
			authTokenCache.put(new Element(authToken.getTokenValue(), authToken));
		}
		
		System.out.println("SIZE:" + authTokenCache.getSize());
		
		Assert.assertEquals("check size", maxElementsInMemory, authTokenCache.getSize());
	}
	
	private AuthToken generateAuthToken(){
		AuthToken t = new AuthToken();
		long oid = oidSequence.incrementAndGet();
		t.setOid(oid);
		t.setMaxActiveSeconds(16);
		t.setActive(true);
		t.setLastModified(new Date());
		t.setTerminalIpAddress("10.2.3."+oid);
		t.setPassword("123456");
		t.setTokenValue(UUID.randomUUID().toString());
		return t;
	}

}
