/**
 * 
 */
package com.jbent.peoplecentral.model.pojo;

/**
 * @author jasontesser
 *
 */
public class Email extends EmailType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4463995806225294605L;

	private long id;
	private String email;
	
	public Email() {}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
}
