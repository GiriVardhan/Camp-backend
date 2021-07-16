package com.jbent.peoplecentral.model.pojo;

import java.io.Serializable;
import java.util.SortedMap;

public class RelatedEntityType implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9155972475256441573L;
	private long id;
	private long attributeId;
	private long childEntityTypeId;
	private boolean collapse;
	private String mod_user;
	private SortedMap<Integer, RelatedEntityType> relatedEntityTypeMap;
	public RelatedEntityType() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public SortedMap<Integer, RelatedEntityType> getRelatedEntityTypeMap() {
		return relatedEntityTypeMap;
	}

	public void setRelatedEntityTypeMap(
			SortedMap<Integer, RelatedEntityType> relatedEntityTypeMap) {
		this.relatedEntityTypeMap = relatedEntityTypeMap;
	}

	public long getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(long attributeId) {
		this.attributeId = attributeId;
	}

	public long getChildEntityTypeId() {
		return childEntityTypeId;
	}

	public void setChildEntityTypeId(long childEntityTypeId) {
		this.childEntityTypeId = childEntityTypeId;
	}

	public boolean isCollapse() {
		return collapse;
	}

	public void setCollapse(boolean collapse) {
		this.collapse = collapse;
	}

	public String getMod_user() {
		return mod_user;
	}

	public void setMod_user(String mod_user) {
		this.mod_user = mod_user;
	}
	
    
}
