package com.jbent.peoplecentral.model.pojo;

import java.io.Serializable;

public class EmailType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5036031829578437096L;
	private long typeId;
	private String typeName;
	
	public EmailType() {}
	
	public long getTypeId() {
		return typeId;
	}
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}
