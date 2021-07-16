package com.jbent.peoplecentral.model.pojo;

import java.io.Serializable;

public class Users implements Serializable{

	private static final long serialVersionUID = 3828447528374967718L;
	
	
	protected String userName;
	protected String password;
	protected boolean enabled;
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	protected String schema;
	
	

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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @param password the password to set
	 */
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}
	
	
	


	


	
	
}
