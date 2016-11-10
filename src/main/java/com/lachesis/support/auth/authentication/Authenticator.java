package com.lachesis.support.auth.authentication;

import com.lachesis.support.auth.model.AuthToken;
import com.lachesis.support.auth.model.Credential;
import com.lachesis.support.auth.model.UserDetails;

public interface Authenticator {
	UserDetails authenticateWithCredential(Credential credential);
	UserDetails authenticateWithAuthToken(AuthToken token);
}
