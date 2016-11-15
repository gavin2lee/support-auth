package com.lachesis.support.auth.authentication;

import com.lachesis.support.auth.vo.AuthToken;
import com.lachesis.support.auth.vo.AuthenticationToken;
import com.lachesis.support.auth.vo.UserDetails;

public interface Authenticator {
	UserDetails authenticateWithCredential(AuthenticationToken credential);
	UserDetails authenticateWithAuthToken(AuthToken token);
}
