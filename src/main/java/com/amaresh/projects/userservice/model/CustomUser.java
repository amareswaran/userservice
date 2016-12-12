package com.amaresh.projects.userservice.model;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {

	private static final long serialVersionUID = 1L;
	private String emailid;
	private String id;
	private List<GrantedAuthority> gn_authorities_list;

	public CustomUser(UserEntity user) {
		super(user.getUsername(), user.getPassword(), user.grantedAuthoritiesList);
		this.emailid = user.getEmailid();
		this.id = user.getId();
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<GrantedAuthority> getGn_authorities_list() {
		return gn_authorities_list;
	}

	public void setGn_authorities_list(List<GrantedAuthority> gn_authorities_list) {
		this.gn_authorities_list = gn_authorities_list;
	}

}