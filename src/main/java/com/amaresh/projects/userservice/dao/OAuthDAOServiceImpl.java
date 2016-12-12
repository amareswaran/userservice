package com.amaresh.projects.userservice.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import com.amaresh.projects.userservice.model.UserEntity;

@Repository
public class OAuthDAOServiceImpl implements OAuthDaoService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public UserEntity getUserDetails(String username) {
		Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
		List<UserEntity> list = new ArrayList<>();
		String userSQLQuery = "SELECT * FROM USERS WHERE USERNAME=?";
		list = jdbcTemplate.query(userSQLQuery, new String[] { username }, (ResultSet rs, int rowNum) -> {
			UserEntity user = new UserEntity();
			user.setId(rs.getString("ID"));
			user.setUsername(username);
			user.setPassword(rs.getString("PASSWORD"));
			user.setEmailid(rs.getString("EMAILID"));
			return user;
		});

		if (!list.isEmpty()) {
			String permissionQuery = "SELECT PER.PERMISSION_NAME FROM PERMISSION PER "
					+ "INNER JOIN GROUP_PERMISSION GROUP_PER ON GROUP_PER.PERMISSION_ID=PER.ID "
					+ "INNER JOIN USERGROUP G ON G.ID=GROUP_PER.GROUP_ID  "
					+ "INNER JOIN GROUP_USERS GROUP_U ON GROUP_U.GROUP_ID=G.ID "
					+ "INNER JOIN USERS U ON U.ID=GROUP_U.USER_ID WHERE U.USERNAME=?";
			List<String> permissionList = new ArrayList<>();
			permissionList = jdbcTemplate.query(permissionQuery.toString(), new String[] { username },
					(ResultSet rs, int rowNum) -> {
						return "ROLE_" + rs.getString("PERMISSION_NAME");
					});
			if (permissionList != null && !permissionList.isEmpty()) {
				for (String permission : permissionList) {
					GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission);
					grantedAuthoritiesList.add(grantedAuthority);
				}
				list.get(0).setGrantedAuthoritiesList(grantedAuthoritiesList);
			}
			return list.get(0);
		}
		return null;
	}
}