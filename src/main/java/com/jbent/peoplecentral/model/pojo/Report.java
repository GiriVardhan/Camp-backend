package com.jbent.peoplecentral.model.pojo;

import java.io.Serializable;

import com.jbent.peoplecentral.permission.Permissionable;

public class Report implements Serializable, Permissionable{

	private static final long serialVersionUID = 3828447528374967718L;
	
	protected long entityTypeId;
	protected String conditionValue;
	protected String[] attributesMultiSelect;
	protected String attributeFilter;
	protected String filterCondition;
	
	public Report() {}
	
	
	public long getEntityTypeId() {
		return entityTypeId;
	}


	public void setEntityTypeId(long entityTypeId) {
		this.entityTypeId = entityTypeId;
	}


	public String getConditionValue() {
		return conditionValue;
	}


	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}


	

	public String[] getAttributesMultiSelect() {
		return attributesMultiSelect;
	}


	public void setAttributesMultiSelect(String[] attributesMultiSelect) {
		this.attributesMultiSelect = attributesMultiSelect;
	}


	public String getAttributeFilter() {
		return attributeFilter;
	}


	public void setAttributeFilter(String attributeFilter) {
		this.attributeFilter = attributeFilter;
	}


	public String getFilterCondition() {
		return filterCondition;
	}


	public void setFilterCondition(String filterCondition) {
		this.filterCondition = filterCondition;
	}


	@Override
	public long getPermissionId() {
		return entityTypeId;
	}

	@Override
	public String getPermissionType() {
		return "Person";
	}
	
}
