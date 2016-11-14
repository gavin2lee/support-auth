package com.lachesis.support.auth.cache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/service-config.xml",
		"classpath:spring/ehcache-config.xml",
		"classpath:spring/repository-config.xml",
		"classpath:spring/datasource-config.xml"})
public class DefaultAuthCacheProviderTest {
	
	@Autowired
	AuthCacheProvider provider;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetAuthTokenCache() {
		Assert.assertNotNull("check auth token cache", provider.getAuthTokenCache());
	}

	@Test
	public void testGetUserDetailsCache() {
		Assert.assertNotNull("check user details cache", provider.getUserDetailsCache());
	}

}
