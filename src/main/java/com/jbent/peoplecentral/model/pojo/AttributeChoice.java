/**
 * 
 */
package com.jbent.peoplecentral.model.pojo;

import java.util.SortedMap;

/**
 * @author Jason Tesser
 *
 */
public class AttributeChoice extends Attribute {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7418567258194290258L;
	private long choiceAttributeId;
	private String displayType;
	private long choiceEntityTypeAttributeId;
	private long attributeId;
	private String mod_user;
	
	private SortedMap<Integer, AttributeChoice> attributeChoiceMap;
	
	public SortedMap<Integer, AttributeChoice> getAttributeChoiceMap() {
		return attributeChoiceMap;
	}
	public void setAttributeChoiceMap(
			SortedMap<Integer, AttributeChoice> attributeChoiceMap) {
		this.attributeChoiceMap = attributeChoiceMap;
	}
	public long getAttributeId() {
		return attributeId;
	}
	public void setAttributeId(long attributeId) {
		this.attributeId = attributeId;
	}
	
	/**
	 * @return the choiceAttributeId
	 */
	public long getChoiceAttributeId() {
		return choiceAttributeId;
	}
	/**
	 * @param choiceAttributeId the choiceAttributeId to set
	 */
	public void setChoiceAttributeId(long choiceAttributeId) {
		this.choiceAttributeId = choiceAttributeId;
	}
	
	/**
	 * @return the displayType
	 */
	public String getDisplayType() {
		return displayType;
	}
	/**
	 * @param displayType the displayType to set
	 */
	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}
	
	/**
	 * @return the choiceEntityTypeAttributeId
	 */
	public long getChoiceEntityTypeAttributeId() {
		return choiceEntityTypeAttributeId;
	}
	/**
	 * @param choiceEntityTypeAttributeId the choiceEntityTypeAttributeId to set
	 */
	public void setChoiceEntityTypeAttributeId(long choiceEntityTypeAttributeId) {
		this.choiceEntityTypeAttributeId = choiceEntityTypeAttributeId;
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
	
}
