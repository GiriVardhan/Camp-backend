package com.jbent.peoplecentral.exception;

public class ConfigException extends Exception {

	private static final long serialVersionUID = -5455166406252351599L;
	private String message;

	public ConfigException(String message) {

		this.message = message;
	}

	public ConfigException(String message, Exception e) {
		
		this.message = message;
		super.initCause(e);
	}

	/**
	 * 
	 * @return the message
	 */

	public String getMessage() {

		return message;

	}

}
