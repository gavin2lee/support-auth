package com.lachesis.support.auth.authentication;

import com.lachesis.support.auth.vo.AuthToken;
import com.lachesis.support.auth.vo.Credential;
import com.lachesis.support.auth.vo.UserDetails;

public interface Authenticator {
	UserDetails authenticateWithCredential(Credential credential);
	UserDetails authenticateWithAuthToken(AuthToken token);
}
