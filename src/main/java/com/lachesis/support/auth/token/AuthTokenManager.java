package com.lachesis.support.auth.token;

import com.lachesis.support.auth.model.AuthToken;

public interface AuthTokenManager {
	void store(AuthToken authToken);
	void updateLastModifiedTime(AuthToken authToken);
	AuthToken retrieve(String tokenValue);
	AuthToken dismiss(String tokenValue);
}
