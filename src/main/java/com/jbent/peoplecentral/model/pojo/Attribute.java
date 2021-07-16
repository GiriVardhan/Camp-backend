package com.jbent.peoplecentral.model.pojo;

import java.io.Serializable;
import java.util.List;

import com.jbent.peoplecentral.permission.Permissionable;

public class Attribute implements Serializable, Permissionable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4221794704388170971L;
	public enum FieldType {
		
		TEXT("text"),
		TEXT_AREA("text_area"),
		CHOICE("choice"),
		EntityType("entity_type"),
		PASSWORD("password"),
		SSN("ssn"),
		PHONE_US("phone_us"),
		ZIP_SHORT("zip_short"),
		ZIP_LONG("zip_long"),
		DATE("date"),
		TIME("time"),
		IMAGE("image"),
		FILE("file"),
		NUMERIC("numeric"),
		EMAIL("email"),
		CURRENCY("currency"),
		WYSIWYG("WYSIWYG");
	
		private String value;
		
		FieldType (String value) {
			this.value = value;
		}
		
		public String toString () {
			return value;
		}
		
		public static FieldType getFieldType (String value) {
			FieldType[] types = FieldType.values();
			for (FieldType type : types) {
				if (type.value.equals(value))
					return type;
			}
			return null;
		}
	}
	
public enum DataType {
		
		VARCHAR("varchar"),
		LONG("long"),
		DATE("date"),		
		TEXT("text"),
		TIME("time");
		
	
		private String value;
		
		DataType (String value) {
			this.value = value;
		}
		
		public String toString () {
			return value;
		}
		
		public static DataType getDataType (String value) {
			DataType[] types = DataType.values();
			for (DataType type : types) {
				if (type.value.equals(value))
					return type;
			}
			return null;
		}
	}

	public enum Attributes {
	
		USERNAME(125),
		PROFILEIMAGE(126),
		EMIALADDRESS(127),
		PASSWORD(128),
		ROLENAME(130),
		QUERYNAME(133),
		QUERYSYNTAX(134),
		SHARE(135),
		USER(136),
		SEARCHTYPE(137),
		SAVEDQUERY(138);
		
		private  final int id;
		Attributes(int id) {
			this.id = id;
		}
		public int getValue() {
			return id; 
		}
		
	}

	
	private long id;
	private int order;
	private String name;
	private String attributeVelocityName;
	private long entityTypeId;
	private long dataTypeId;
	private String dataTypeName;
	private long fieldTypeId;
	private String fieldTypeName;
	private boolean indexed = true;
	private boolean required;
	private String mod_user;
	private Regex regex;	
	private List<FieldTypeOption> options;
	private RelatedEntityType relatedEntityType;
	private AttributeChoice attributeChoice;
	private boolean display = true;
	private boolean aDeletable = true;
	
	
	/**
	 * 
	 */
	public Attribute() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param id
	 */
	public Attribute(long id) {
		super();
		this.id = id;
	}
	public List<FieldTypeOption> getOptions() {
		return options;
	}
	public void setOptions(List<FieldTypeOption> options) {
		this.options = options;
	}
		/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public void setIndexed(boolean indexed) {
		this.indexed = indexed;
	}
	public boolean isIndexed() {
		return indexed;
	}
	
	/**
	 * @return the dataTypeId
	 */
	public long getDataTypeId() {
		return dataTypeId;
	}
	/**
	 * @param dataTypeId the dataTypeId to set
	 */
	public void setDataTypeId(long dataTypeId) {
		this.dataTypeId = dataTypeId;
	}
	/**
	 * @return the dataTypeName
	 */
	public String getDataTypeName() {
		return dataTypeName;
	}
	
	/**
	 * @param dataTypeName the dataTypeName to set
	 */
	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}
	/**
	 * @param fieldTypeName the fieldTypeName to set
	 */
	public void setFieldTypeName(String fieldTypeName) {
		this.fieldTypeName = fieldTypeName;
	}
	/**
	 * @return the fieldTypeId
	 */
	public long getFieldTypeId() {
		return fieldTypeId;
	}
	/**
	 * @param fieldTypeId the fieldTypeId to set
	 */
	public void setFieldTypeId(long fieldTypeId) {
		this.fieldTypeId = fieldTypeId;
	}
	/**
	 * @return the fieldTypeName
	 */
	public String getFieldTypeName() {
		return fieldTypeName;
	}
	/**
	 * @return the entityTypeId
	 */
	public long getEntityTypeId() {
		return entityTypeId;
	}
	/**
	 * @param entityTypeId the entityTypeId to set
	 */
	public void setEntityTypeId(long entityTypeId) {
		this.entityTypeId = entityTypeId;
	}
	
	/**
	 * @return the required
	 */
	public boolean isRequired() {
		return required;
	}
	/**
	 * @param required the required to set
	 */
	public void setRequired(boolean required) {
		this.required = required;
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
	
	/**
	 * @return the regex
	 */
	
	public Regex getRegex() {
		return regex;
	}
	
	/**
	 * @param mod_user the mod_user to set
	 */
	
	public void setRegex(Regex regex) {
		this.regex = regex;
	}
	@Override
	public long getPermissionId() {
		return id;
	}
	@Override
	public String getPermissionType() {
		return "attribute_"+id;
	}
	/**
	 * @return the relatedEntityType
	 */
	public RelatedEntityType getRelatedEntityType() {
		return relatedEntityType;
	}
	/**
	 * @param relatedEntityType the relatedEntityType to set
	 */
	public void setRelatedEntityType(RelatedEntityType relatedEntityType) {
		this.relatedEntityType = relatedEntityType;
	}
	/**
	 * @return the attributeChoice
	 */
	public AttributeChoice getAttributeChoice() {
		return attributeChoice;
	}
	/**
	 * @param attributeChoice the attributeChoice to set
	 */
	public void setAttributeChoice(AttributeChoice attributeChoice) {
		this.attributeChoice = attributeChoice;
	}
	/**
	 * @return the display
	 */
	public boolean isDisplay() {
		return display;
	}
	/**
	 * @param display the display to set
	 */
	public void setDisplay(boolean display) {
		this.display = display;
	}
	/**
	 * @return the aDeletable
	 */
	public boolean isADeletable() {
		return aDeletable;
	}
	/**
	 * @param deletable the aDeletable to set
	 */
	public void setADeletable(boolean deletable) {
		aDeletable = deletable;
	}
	
	public String getAttributeVelocityName() {
		if(getName()!= null){
			attributeVelocityName = getName().replaceAll("[^a-zA-Z]+", "");
			attributeVelocityName = attributeVelocityName.trim().toLowerCase();
		}		
		return attributeVelocityName;
	}
	
	public void setAttributeVelocityName(String attributeVelocityName) {
		this.attributeVelocityName = attributeVelocityName;
	}
	
}
