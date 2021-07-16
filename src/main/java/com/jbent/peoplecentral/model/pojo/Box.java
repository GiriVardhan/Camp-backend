package com.jbent.peoplecentral.model.pojo;

import java.io.Serializable;



public class Box implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3839529992652535701L;
	private long boxId;
	private long userEntityId;	
	
	public Box() {}

	
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
	
	/**
	 * @return the userEntityId
	 */

	public long getUserEntityId() {
		return userEntityId;
	}
	
	/**
	 * @param userEntityId the userEntityId to set
	 */

	public void setUserEntityId(long userEntityId) {
		this.userEntityId = userEntityId;
	}
	
}

