package com.amaresh.projects.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.amaresh.projects.userservice.dao.OAuthDaoService;
import com.amaresh.projects.userservice.model.CustomUser;
import com.amaresh.projects.userservice.model.UserEntity;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	OAuthDaoService oauthDaoService;

	@Override
	public CustomUser loadUserByUsername(final String username) throws UsernameNotFoundException {
		UserEntity userEntity = null;
		try {
			userEntity = oauthDaoService.getUserDetails(username);
			if (userEntity != null && userEntity.getUsername() != null
					&& !"".equalsIgnoreCase(userEntity.getUsername())) {
				CustomUser user = new CustomUser(userEntity);
				return user;
			} else {
				throw new UsernameNotFoundException("User " + username + " was not found in the database");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}
	}
}