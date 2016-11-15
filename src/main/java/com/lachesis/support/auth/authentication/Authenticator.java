package com.lachesis.support.auth.authentication;

import com.lachesis.support.auth.vo.AuthToken;
import com.lachesis.support.auth.vo.UsernamePasswordToken;
import com.lachesis.support.auth.vo.UserDetails;

public interface Authenticator {
	UserDetails authenticateWithCredential(UsernamePasswordToken credential);
	UserDetails authenticateWithAuthToken(AuthToken token);
}
