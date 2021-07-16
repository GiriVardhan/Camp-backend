package com.jbent.peoplecentral.model.pojo;

import java.io.Serializable;
import java.util.Date;


public class BulkEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3354870494334755634L;
	private long entityId;	
	private long entityTypeId;
	private String mod_user;
	private String searchIndex;
	private boolean entityValid;
	private long attributeValueId;
	private Attribute attribute;
	private String valueVarchar;	
	private Long valueLong;
	private Date valueDate;
	private String value_Time;
	private String valueText;
	private Long attributeValueFileId;
	
	
	private long counter;
	
	

	public long getCounter() {
		return counter;
	}


	public void setCounter(long counter) {
		this.counter = counter;
	}


	public BulkEntity() {}


	public long getEntityId() {
		return entityId;
	}


	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}


	public long getEntityTypeId() {
		return entityTypeId;
	}


	public void setEntityTypeId(long entityTypeId) {
		this.entityTypeId = entityTypeId;
	}


	public String getMod_user() {
		return mod_user;
	}


	public void setMod_user(String mod_user) {
		this.mod_user = mod_user;
	}


	public String getSearchIndex() {
		return searchIndex;
	}


	public void setSearchIndex(String searchIndex) {
		this.searchIndex = searchIndex;
	}
	
	public boolean isEntityValid() {
		return entityValid;
	}


	public void setEntityValid(boolean entityValid) {
		this.entityValid = entityValid;
	}


	public long getAttributeValueId() {
		return attributeValueId;
	}


	public void setAttributeValueId(long attributeValueId) {
		this.attributeValueId = attributeValueId;
	}


	public Attribute getAttribute() {
		return attribute;
	}


	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}


	public String getValueVarchar() {
		return valueVarchar;
	}


	public void setValueVarchar(String valueVarchar) {
		this.valueVarchar = valueVarchar;
	}


	public Long getValueLong() {
		return valueLong;
	}


	public void setValueLong(Long valueLong) {
		this.valueLong = valueLong;
	}


	public Date getValueDate() {
		return valueDate;
	}


	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}


	public String getValueText() {
		return valueText;
	}


	public void setValueText(String valueText) {
		this.valueText = valueText;
	}

	public String getValue_Time() {
		return value_Time;
	}


	public void setValue_Time(String value_Time) {
		this.value_Time = value_Time;
	}


	public Long getAttributeValueFileId() {
		return attributeValueFileId;
	}


	public void setAttributeValueFileId(Long attributeValueFileId) {
		this.attributeValueFileId = attributeValueFileId;
	}

	
}

