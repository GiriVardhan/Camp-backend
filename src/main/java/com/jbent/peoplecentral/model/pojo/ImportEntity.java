package com.jbent.peoplecentral.model.pojo;

import java.io.Serializable;





public class ImportEntity implements Serializable{

 /**
	 * 
	 */
	private static final long serialVersionUID = 340356408340419818L;
private Entity entity;
 private EntityType parent;
/**
 * @return the entity
 */
public Entity getEntity() {
	return entity;
}
/**
 * @param entity the entity to set
 */
public void setEntity(Entity entity) {
	this.entity = entity;
}
/**
 * @return the parent
 */
public EntityType getParent() {
	return parent;
}
/**
 * @param parent the parent to set
 */
public void setParent(EntityType parent) {
	this.parent = parent;
}
 
		
}

