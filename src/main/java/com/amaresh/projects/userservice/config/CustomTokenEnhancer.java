package com.amaresh.projects.userservice.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.amaresh.projects.userservice.model.AccessTokenMapper;
import com.amaresh.projects.userservice.model.CustomUser;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;

public class CustomTokenEnhancer extends JwtAccessTokenConverter {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		CustomUser user = (CustomUser) authentication.getPrincipal();
		Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());
		if(user.getId()!=null)
			info.put("id",user.getId());
		if (user.getUsername() != null)
			info.put("user_name", user.getUsername());
		if (user.getEmailid() != null)
			info.put("emailid", user.getEmailid());
		DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
		customAccessToken.setAdditionalInformation(info);
		return super.enhance(customAccessToken, authentication);
	}

	@Override
	public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
		OAuth2Authentication auth = super.extractAuthentication(map);
		AccessTokenMapper details = new AccessTokenMapper();
		if(map.get("id")!=null)
			details.setId((String) map.get("id"));
		if (map.get("user_name") != null)
			details.setUser_name((String) map.get("user_name"));
		if (map.get("emailid") != null)
			details.setEmailid((String) map.get("emailid"));
		
		if (auth.getAuthorities() != null && !auth.getAuthorities().isEmpty()) {
			List<String> authorities = new ArrayList<>();
			for (GrantedAuthority gn : auth.getAuthorities()) {
				authorities.add(gn.getAuthority());
			}
			details.setAuthorities(authorities);
		}
		auth.setDetails(details);
		return auth;
	}
}