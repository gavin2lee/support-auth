package com.lachesis.support.auth.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4072057897019711567L;
	private String userid;
	private String password;
	private List<Authority> authorities;
	
	public SimpleUserDetails(String userid, String password, List<Authority> authorities) {
		super();
		this.userid = userid;
		this.password = password;
		this.authorities = authorities;
	}

	@Override
	public String getUserid() {
		return userid;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public List<Authority> getAuthorities() {
		if(authorities==null){
			return Collections.unmodifiableList(new ArrayList<Authority>(0));
		}
		return Collections.unmodifiableList(authorities);
	}

}
