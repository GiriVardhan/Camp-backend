package com.jbent.peoplecentral.model.pojo;

import java.io.Serializable;



public class BoxEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7072988559621156525L;
	private long boxId;	
	private long entityId;
	
	public BoxEntity() {}

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
	 * @return the boxId
	 */

	public long getBoxId() {
		return boxId;
		
		/**
		 * @param boxId the boxId to set
		 */
	}

	public void setBoxId(long boxId) {
		this.boxId = boxId;
	}	

	
}

