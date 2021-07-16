package com.jbent.peoplecentral.model.pojo;

import java.io.Serializable;

public class EntityStatus implements Serializable{

	private static final long serialVersionUID = 3828447528374967718L;
	private long entityId;
	private boolean entityValid;
	/**
	 * @return the entityId
	 */
	public long getEntityId() {
		return entityId;
	}
	/**
	 * @param entityId the entityId to set
	 */
	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}
	/**
	 * @return the entityValid
	 */
	public boolean isEntityValid() {
		return entityValid;
	}
	/**
	 * @param entityValid the entityValid to set
	 */
	public void setEntityValid(boolean entityValid) {
		this.entityValid = entityValid;
	}
	
	
	
}
