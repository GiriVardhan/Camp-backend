package com.jbent.peoplecentral.model.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jbent.peoplecentral.permission.Permissionable;

public class EntityType implements Serializable, Permissionable{

	public enum EntityTypes {
		
		PEOPLE(38),
		RELATION(39),
		ROLE(40),
		SAVEDSEARCHES(41),
		EVENT(42),
		FOLDER(43);

		private  final int id;
		
		EntityTypes(int id) {
			this.id = id;
		}
		public int getValue() {
			return id; 
		}

	}
	
	private static final long serialVersionUID = -8378894744728163367L;
	
	private long id;
	private String name;
	private String mod_user;
	private long count;
	private String template;
	private String editTemplate;
	private boolean lock;
	private boolean etDeletable = true;
	private boolean treeable = false;

	private List<Attribute> attributes = new ArrayList<Attribute>();
	
	
	/**
	 * @param id
	 */
	public EntityType(long id) {
		super();
		this.id = id;
	}

	/**
	 * @return the editTemplate
	 */
	public String getEditTemplate() {
		return editTemplate;
	}

	/**
	 * @param editTemplate the editTemplate to set
	 */
	public void setEditTemplate(String editTemplate) {
		this.editTemplate = editTemplate;
	}

	
	/**
	 * @return the lock
	 */
	public boolean isLock() {
		return lock;
	}

	/**
	 * @param lock the lock to set
	 */
	public void setLock(boolean lock) {
		this.lock = lock;
	}

	/**
	 * The map should use the attribute order as the key so it will Sort properly
	 */
	
	
	public EntityType() {}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}
	
	public int getAttributeCount(){
		return attributes.size();
	}

	public String getMod_user() {
		return mod_user;
	}

	public void setMod_user(String mod_user) {
		this.mod_user = mod_user;
	}

	@Override
	public long getPermissionId() {
		return id;
	}

	@Override
	public String getPermissionType() {
		return "entityType_"+id;
	}
	
	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public boolean isEtDeletable() {
		return etDeletable;
	}

	public void setEtDeletable(boolean etDeletable) {
		this.etDeletable = etDeletable;
	}

	public boolean isTreeable() {
		return treeable;
	}

	public void setTreeable(boolean treeable) {
		this.treeable = treeable;
	}
}
