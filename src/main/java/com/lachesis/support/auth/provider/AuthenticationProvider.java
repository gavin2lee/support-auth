package com.lachesis.support.auth.provider;

import com.lachesis.support.auth.authenticater.Authenticater;

public interface AuthenticationProvider {
	Authenticater getAuthenticater();
}
