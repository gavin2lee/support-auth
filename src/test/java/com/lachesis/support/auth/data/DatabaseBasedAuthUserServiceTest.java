package com.lachesis.support.auth.data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class DatabaseBasedAuthUserServiceTest {

	@Autowired
	@Qualifier("databaseBasedAuthUserService")
	AuthUserService service;
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testFindAuthUserByUserid() {
		fail("Not yet implemented");
	}

}
