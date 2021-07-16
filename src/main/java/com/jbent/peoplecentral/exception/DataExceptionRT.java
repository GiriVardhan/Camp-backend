package com.jbent.peoplecentral.exception;

public class DataExceptionRT extends RuntimeException{

	private static final long serialVersionUID = -3080604396817990424L;

	private String message;
	
	public DataExceptionRT(String message) {

		this.message = message;
	}

	public DataExceptionRT(String message, Exception e) {
		
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
