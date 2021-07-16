package com.jbent.peoplecentral.model.pojo;

import java.io.Serializable;


public class AttributeDataType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5707313295377345580L;
	private long datatypeId;
	private String datatypeName;
	
	
	
	
	public AttributeDataType() {}

	public long getDatatypeId() {
		return datatypeId;
	}

	public void setDatatypeId(long datatypeId) {
		this.datatypeId = datatypeId;
	}

	public String getDatatypeName() {
		return datatypeName;
	}

	public void setDatatypeName(String datatypeName) {
		this.datatypeName = datatypeName;
	}

	
}

