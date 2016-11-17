package com.lachesis.support.auth.cache;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/service-config-test.xml"})
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
	public void testGetAuthorizationResultCache() {
		Assert.assertNotNull("check user details cache", provider.getAuthorizationResultCache());
	}

}
