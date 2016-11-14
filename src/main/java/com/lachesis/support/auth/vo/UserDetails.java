package com.lachesis.support.auth.vo;

import java.io.Serializable;
import java.util.List;

public interface UserDetails extends Serializable{
	String getResourceId();
	String getUserid();
	List<Authority> getAuthorities();
}
