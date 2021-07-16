package com.jbent.peoplecentral.model.pojo;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;

public class Authorities implements Serializable,GrantedAuthority{

	private static final long serialVersionUID = 3828447528374967718L;
	
	
	protected String userName;
	protected String authority;
	
	
	

	/**
	 * @return the userName
	 */
	
	public String getUserName() {
		return userName;
	}
	
	/**
	 * @param userName the userName to set
	 */
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * @return the authority
	 */
	
	public String getAuthority() {
		return authority;
	}

	/**
	 * @param authority the authority to set
	 */
	
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	
	


	


	
	
}
