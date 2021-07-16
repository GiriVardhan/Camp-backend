package com.jbent.peoplecentral.model.pojo;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.SortedMap;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class AttributeValueStorage extends Attribute implements  Serializable,Comparable<AttributeValueStorage>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -237059775378932732L;
	private long attributeValueId;
	private long entityId;
	private String valueVarchar;	
	private Long valueLong;
	private Date valueDate;
	private Time valueTime;
	private String valueText;
	private String mod_user;
	private String searchIndex;
	private long entityTypeId;
	private Object value;
	private String value_Long;
	private String value_Date;
	private String value_Time;
	private Long attributeValueFileId;
	
	/**
	 * 
	 * @return the searchIndex
	 */
	
	public String getSearchIndex() {
		return searchIndex;
	}
      /**
       * 
       * @param searchIndex
       */

	public void setSearchIndex(String searchIndex) {
		this.searchIndex = searchIndex;
	}

	private SortedMap<Integer, AttributeValueStorage> attibuteValueStorageMap;
	
	
	
	public SortedMap<Integer, AttributeValueStorage> getAttibuteValueStorageMap() {
		return attibuteValueStorageMap;
	}


	public void setAttibuteValueStorageMap(
			SortedMap<Integer, AttributeValueStorage> attibuteValueStorageMap) {
		this.attibuteValueStorageMap = attibuteValueStorageMap;
	}


	public AttributeValueStorage() {}


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
	 * @return the attributeValueId
	 */

	public long getAttributeValueId() {
		return attributeValueId;
	}

	/**
	 * @param attributeValueId the attributeValueId to set
	 */

	public void setAttributeValueId(long attributeValueId) {
		this.attributeValueId = attributeValueId;
	}

	/**
	 * @return the attribute
	 */


//	public Attribute getAttribute() {
//		return attribute;
//	}
//	/**
//	 * @param attribute the attribute to set
//	 */
//
//
//	public void setAttribute(Attribute attribute) {
//		this.attribute = attribute;
//	}


	/**
	 * @return the valueVarchar
	 */

	public String getValueVarchar() {
		return valueVarchar;
	}

	/**
	 * @param valueVarchar the valueVarchar to set
	 */

	public void setValueVarchar(String valueVarchar) {
		this.valueVarchar = valueVarchar;
	}

	

	/**
	 * @return the valueLong
	 */

	public Long getValueLong() {
		return valueLong;
	}


	/**
	 * @param valueLong the valueLong to set
	 */
	public void setValueLong(Long valueLong) {
		this.valueLong = valueLong;
	}

	/**
	 * @return the valueDate
	 */

	public Date getValueDate() {
		return valueDate;
	}

	/**
	 * @param valueDate the valueDate to set
	 */

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}
	
	/**
	 * @return the valueTime
	 */

	public Time getValueTime() {
		return valueTime;
	}
	
	/**
	 * @param valueTime the valueTime to set
	 */
	
	public void setValueTime(Time valueTime) {
		this.valueTime = valueTime;
	}
	/**
	 * @return the valueText
	 */

	public String getValueText() {
		return valueText;
	}

	/**
	 * @param valueText the valueText to set
	 */

	public void setValueText(String valueText) {
		this.valueText = valueText;
	}

	/**
	 * @return the mod_user
	 */

	public String getMod_user() {
		return mod_user;
	}

	/**
	 * @param mod_user the mod_user to set
	 */

	public void setMod_user(String mod_user) {
		this.mod_user = mod_user;
	}
	public long getEntityTypeId() {
		return entityTypeId;
	}
	public void setEntityTypeId(long entityTypeId) {
		this.entityTypeId = entityTypeId;
	}

	/**
	 * @return the value_Long
	 */
	public String getValue_Long() {
		return value_Long;
	}
	/**
	 * @param value_Long the value_Long to set
	 */
	public void setValue_Long(String value_Long) {
		this.value_Long = value_Long;
	}
	
	
	/**
	 * @return the value_Date
	 */
	public String getValue_Date() {
		return value_Date;
	}
	/**
	 * @param value_Date the value_Date to set
	 */
	public void setValue_Date(String value_Date) {
		this.value_Date = value_Date;
	}
	/**
	 * @return the value_Time
	 */
	public String getValue_Time() {
		return value_Time;
	}
	/**
	 * @param value_Time the value_Time to set
	 */
	public void setValue_Time(String value_Time) {
		this.value_Time = value_Time;
	}
	public Object getValue() {
		if(getDataTypeId()>0){
			if(getDataTypeId() == 1){
				value = valueVarchar;
				return value;
			}
			if(getDataTypeName().equals(Attribute.DataType.DATE.toString())){
				value = valueDate;
			}else if(getDataTypeName().equals(Attribute.DataType.TIME.toString())){
				value = valueTime;
			}else if(getDataTypeName().equals(Attribute.DataType.TEXT.toString())){
				value = valueText;
			}else if(getDataTypeName().equals(Attribute.DataType.LONG.toString())){
				value = valueLong;
			}else{
				value = valueVarchar;
			}
		}
		
		return value;
	}
	
	@Override
	public String toString() {
		value = getValue();
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	@Override
	public int compareTo(AttributeValueStorage o) {
		return sanitizeRole(this.getValueVarchar()).compareTo(sanitizeRole(o.getValueVarchar()));
	}
	private String sanitizeRole(String role){
		return role.trim().substring(5);
	}
	
	
	/**
	 * @return the attributeFileId
	 */
	
	public Long getAttributeValueFileId() {
		return attributeValueFileId;
	}
	
	/**
	 * @param attributeFileId the attributeFileId to set
	 */
	
	public void setAttributeValueFileId(Long attributeValueFileId) {
		this.attributeValueFileId = attributeValueFileId;
	}
	
}

