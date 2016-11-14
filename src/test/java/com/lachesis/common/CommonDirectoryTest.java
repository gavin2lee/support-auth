package com.lachesis.common;

import org.junit.Before;
import org.junit.Test;

public class CommonDirectoryTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testUserHome() {
		System.out.println("user.home:"+System.getProperty("user.home"));
		System.out.println("java.home:"+System.getProperty("java.home"));
	}

}
