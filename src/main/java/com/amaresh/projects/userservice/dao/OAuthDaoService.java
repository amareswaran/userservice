package com.amaresh.projects.userservice.dao;

import com.amaresh.projects.userservice.model.UserEntity;

public interface OAuthDaoService {
	public abstract UserEntity getUserDetails(String username);
}