package com.lachesis.support.auth.model;

import java.util.List;

public interface UserDetails {
	String getUserid();
	List<Authority> getAuthorizes();
}
