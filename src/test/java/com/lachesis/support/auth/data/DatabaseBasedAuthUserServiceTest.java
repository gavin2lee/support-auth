package com.lachesis.support.auth.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lachesis.support.auth.model.AuthUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/service-config.xml",
		"classpath:spring/repository-config.xml",
		"classpath:spring/datasource-config.xml"})
public class DatabaseBasedAuthUserServiceTest {

	@Autowired
	@Qualifier("databaseBasedAuthUserService")
	private AuthUserService service;
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testFindAuthUserByUserid() {
		String userid = "283";
		AuthUser authUser = service.findAuthUserByUserid(userid);
		
		Assert.assertNotNull("check authUser", authUser);
	}

}
