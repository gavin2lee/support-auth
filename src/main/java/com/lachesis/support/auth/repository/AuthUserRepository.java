package com.lachesis.support.auth.repository;

import com.lachesis.support.auth.model.AuthUser;

public interface AuthUserRepository {
	AuthUser findByUserid(String userid);
}
