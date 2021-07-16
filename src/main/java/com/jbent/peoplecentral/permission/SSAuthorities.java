package com.jbent.peoplecentral.permission;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

public class SSAuthorities extends JdbcDaoImpl {

	
	public SSAuthorities() {
		// TODO Auto-generated constructor stub		
		
	}
	
	@Override
	public UserDetails createUserDetails(String username,
			UserDetails userFromUserQuery,
			List<GrantedAuthority> combinedAuthorities) {
		// TODO Auto-generated method stub
		return super
				.createUserDetails(username, userFromUserQuery, combinedAuthorities);
	}

	@Override
	protected void addCustomAuthorities(String username,
			List<GrantedAuthority> authorities) {
		// TODO Auto-generated method stub
		super.addCustomAuthorities(username, authorities);
	}

	@Override
	protected List<UserDetails> loadUsersByUsername(String username) {
		// TODO Auto-generated method stub
		return super.loadUsersByUsername(username);
	}
	
		
	
}