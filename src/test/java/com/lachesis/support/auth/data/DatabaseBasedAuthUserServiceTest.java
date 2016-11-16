package com.lachesis.support.auth.data;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lachesis.support.auth.data.impl.DatabaseBasedAuthUserService;
import com.lachesis.support.auth.model.AuthUser;
import com.lachesis.support.auth.repository.AuthUserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/service-config-test.xml"})
public class DatabaseBasedAuthUserServiceTest {

	@Mock
	AuthUserRepository authUserRepo;
	
	@InjectMocks
	private AuthUserService service = new DatabaseBasedAuthUserService();
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
	}

	@Test
	public void testFindAuthUserByUserid() {
		initFindAuthUserByUserid();
		
		String userid = "283";
		AuthUser authUser = service.findAuthUserByUserid(userid);
		
		Assert.assertNotNull("check authUser", authUser);
	}
	
	private void initFindAuthUserByUserid(){
		when(authUserRepo.findByUserid(any(String.class))).thenReturn(mockAuthUser());
	}
	
	private AuthUser mockAuthUser(){
		AuthUser u = new AuthUser();
		u.setId(3L);
		u.setUsername("283");
		u.setPassword("123");
		u.setUserCode("000283");
		
		return u;
	}

}
