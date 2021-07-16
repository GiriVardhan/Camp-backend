package com.jbent.peoplecentral.model.pojo;

import java.io.Serializable;

import com.jbent.peoplecentral.permission.Permissionable;

public class AuditSummary  implements Permissionable, Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8105183644187863498L;
	
	private long auditId;
	private String date;
	private String action;
	private long entityTypeId;
	private long entityId;
	private String tableName;
	private String mod_user;
	private EntityType entityType;
	private String title;
	private long auditCount;
	
	
	
	
	
	/**
	 * @return the auditCount
	 */
	public long getAuditCount() {
		return auditCount;
	}
	/**
	 * @param auditCount the auditCount to set
	 */
	public void setAuditCount(long auditCount) {
		this.auditCount = auditCount;
	}
	/**
	 * @return the entityType
	 */
	public EntityType getEntityType() {
		return entityType;
	}
	/**
	 * @param entityType the entityType to set
	 */
	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the auditId
	 */
	public long getAuditId() {
		return auditId;
	}
	/**
	 * @param auditId the auditId to set
	 */
	public void setAuditId(long auditId) {
		this.auditId = auditId;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
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
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
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
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
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
	@Override
	public long getPermissionId() {
		// TODO Auto-generated method stub
		return this.auditId;
	}
	@Override
	public String getPermissionType() {
		// TODO Auto-generated method stub
		return "audit_"+auditId;
	}
	   
    
   	
}

