package com.lachesis.support.auth.token;

import com.lachesis.support.auth.model.AuthToken;

public interface TokenStorage {
	void store(AuthToken authToken);
	void updateLastModifiedTime(AuthToken authToken);
	AuthToken retrieve(String tokenValue);
}
