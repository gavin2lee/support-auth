package com.lachesis.support.auth.vo;

import java.io.Serializable;
import java.util.List;

public interface UserDetails extends Serializable{
	String getUserid();
	String getPassword();
	List<Authority> getAuthorities();
}
