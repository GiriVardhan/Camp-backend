/**
 * 
 */
package com.jbent.peoplecentral.model.pojo;

import java.io.Serializable;


/**
 * @author Prasad BHVN
 *
 */
public class FieldTypeOption implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7343668337318003685L;

	public enum FieldTypeOptions {
		
		IMAGELIMIT(1),
		IMAGETYPE(2),
		FILETYPE(3);
		
		private  final int id;
		
		FieldTypeOptions(int id) {
			 this.id = id;
		}
		public int getValue() {
			return id; 
		}
	}

	private long optionValueId;
	private long fieldtypeOptionId;
	private long fieldTypeId;
	private long attributeId;
	private String option;
	private String optionValue;
	
	
	
	
	public long getFieldTypeId() {
		return fieldTypeId;
	}

	public void setFieldTypeId(long fieldTypeId) {
		this.fieldTypeId = fieldTypeId;
	}
	
	
	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public long getAttributeId() {
		return attributeId;
	}
	public void setAttributeId(long attributeId) {
		this.attributeId = attributeId;
	}
	
	public long getOptionValueId() {
		return optionValueId;
	}
	public void setOptionValueId(long optionValueId) {
		this.optionValueId = optionValueId;
	}
	
	public String getOptionValue() {
		return optionValue;
	}
	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

	public long getFieldtypeOptionId() {
		return fieldtypeOptionId;
	}

	public void setFieldtypeOptionId(long fieldtypeOptionId) {
		this.fieldtypeOptionId = fieldtypeOptionId;
	}
	
	
		
}
