package com.lachesis.support.auth.token;

import com.lachesis.support.auth.model.AuthToken;

public interface TokenHolder {
	void store(AuthToken authToken);
	AuthToken retrieve(String tokenValue);
}
