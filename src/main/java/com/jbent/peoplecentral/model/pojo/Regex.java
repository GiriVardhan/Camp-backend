package com.jbent.peoplecentral.model.pojo;

import java.io.Serializable;



public class Regex implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6130632053339325596L;
	private long regexId;
	private String pattern;
	private String displayName;
	private boolean custom;
		
	
	public Regex() {}


	/**
	 * @return the regexId
	 */
	public long getRegexId() {
		return regexId;
	}


	/**
	 * @param regexId the regexId to set
	 */

	public void setRegexId(long regexId) {
		this.regexId = regexId;
	}

	/**
	 * @return the pattern
	 */

	public String getPattern() {
		return pattern;
	}

	/**
	 * @param pattern the pattern to set
	 */

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	/**
	 * @return the displayName
	 */

	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the custom
	 */

	public Boolean getCustom() {
		return custom;
	}

	/**
	 * @param custom the custom to set
	 */

	public void setCustom(boolean custom) {
		this.custom = custom;
	}

		
}

