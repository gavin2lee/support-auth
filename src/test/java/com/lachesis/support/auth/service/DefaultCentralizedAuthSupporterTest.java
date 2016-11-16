package com.lachesis.support.auth.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lachesis.support.auth.annotation.SupportTestContext;
import com.lachesis.support.auth.authentication.Authenticator;
import com.lachesis.support.auth.data.AuthUserService;
import com.lachesis.support.auth.model.AuthUser;
import com.lachesis.support.auth.vo.UserDetails;

@RunWith(SpringJUnit4ClassRunner.class)
@SupportTestContext
public class DefaultCentralizedAuthSupporterTest {

	@InjectMocks
	@Autowired
	CentralizedAuthSupporter supporter;

	@Mock
	AuthUserService authUserService;

	@InjectMocks
	@Autowired
	Authenticator authenticator;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(authUserService.findAuthUserByUserid(any(String.class))).thenReturn(mockAuthUser());
	}

	@Test
	public void testGenerateToken() {
		String userid = "283";
		String password = "123";
		String ip = "10.10.10.10";

		String token = supporter.generateToken(userid, password, ip);

		Assert.assertNotNull("token should not be null", token);
	}

	@Test
	public void testAuthenticate() {
		String userid = "283";
		String password = "123";
		String ip = "10.10.10.10";

		String token = supporter.generateToken(userid, password, ip);
		
		Assert.assertThat(token, Matchers.notNullValue());

		UserDetails userDetails = supporter.authenticate(token, ip);

		Assert.assertNotNull("user details should not be null", userDetails);
	}

	@Test
	public void testDismissToken() {
		String userid = "283";
		String password = "123";
		String ip = "10.10.10.10";
		String ip2 = "100.100.10.10";

		String token = supporter.generateToken(userid, password, ip);

		Assert.assertThat(token, Matchers.notNullValue());

		UserDetails userDetails = supporter.authenticate(token, ip2);

		Assert.assertNotNull("user details should not be null", userDetails);

		supporter.dismissToken(token);
		UserDetails userDetailsAfterDismiss = supporter.authenticate(token, ip2);

		Assert.assertNull("user details should be null after dismission", userDetailsAfterDismiss);
	}

	private AuthUser mockAuthUser() {
		AuthUser u = new AuthUser();
		u.setId(3L);
		u.setUsername("283");
		u.setUserCode("000283");
		u.setPassword("123");

		return u;
	}
}
