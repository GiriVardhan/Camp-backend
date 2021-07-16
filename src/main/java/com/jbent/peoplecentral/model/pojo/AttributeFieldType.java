package com.jbent.peoplecentral.model.pojo;

import java.io.Serializable;
import java.util.List;

public class AttributeFieldType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3669392778667768132L;
	private long fieldTypeId;
	private String name;
	private int displayOrder;
	private String displayLabel;
	private List<FieldTypeOption> options;
	
	
	public AttributeFieldType() {}



	public long getFieldTypeId() {
		return fieldTypeId;
	}



	public void setFieldTypeId(long fieldTypeId) {
		this.fieldTypeId = fieldTypeId;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}






	public int getDisplayOrder() {
		return displayOrder;
	}



	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}



	public String getDisplayLabel() {
		return displayLabel;
	}



	public void setDisplayLabel(String displayLabel) {
		this.displayLabel = displayLabel;
	}



	public List<FieldTypeOption> getOptions() {
		return options;
	}



	public void setOptions(List<FieldTypeOption> options) {
		this.options = options;
	}


	

	
	
	
}

