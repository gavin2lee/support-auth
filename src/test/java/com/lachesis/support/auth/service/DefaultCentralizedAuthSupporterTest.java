package com.lachesis.support.auth.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lachesis.support.auth.vo.UserDetails;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-config-test.xml"})
public class DefaultCentralizedAuthSupporterTest {
	
	@Autowired
	CentralizedAuthSupporter supporter;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGenerateToken() {
		String userid = "283";
		String password = "123";
		String ip = "10.10.10.10";
		
		String token = supporter.generateToken(userid, password, ip);
		
		System.out.println("TOKEN:"+token);
		
		Assert.assertNotNull("token should not be null", token);
	}

	@Test
	public void testAuthenticate() {
		String userid = "283";
		String password = "123";
		String ip = "10.10.10.10";
		
		String token = supporter.generateToken(userid, password, ip);
		
		UserDetails userDetails = supporter.authenticate(token, ip);
		
		Assert.assertNotNull("user details should not be null", userDetails);
	}

	@Test
	public void testDismissToken() {
		String userid = "283";
		String password = "123";
		String ip = "10.10.10.10";
		
		String token = supporter.generateToken(userid, password, ip);
		
		UserDetails userDetails = supporter.authenticate(token, ip);
		
		Assert.assertNotNull("user details should not be null", userDetails);
		
		supporter.dismissToken(token);
		UserDetails userDetailsAfterDismiss = supporter.authenticate(token, ip);
		
		Assert.assertNull("user details should be null after dismission", userDetailsAfterDismiss);
	}

}
