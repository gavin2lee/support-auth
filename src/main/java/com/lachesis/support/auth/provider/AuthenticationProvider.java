package com.lachesis.support.auth.provider;

import com.lachesis.support.auth.model.Credential;
import com.lachesis.support.auth.model.UserDetails;

public interface AuthenticationProvider {
	UserDetails authenticate(Credential credential);
}
