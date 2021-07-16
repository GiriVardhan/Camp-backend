package com.jbent.peoplecentral.model.pojo;

import java.io.Serializable;
import java.util.List;

public class CompletePermissions  implements  Serializable,Comparable<CompletePermissions> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8105183644187863498L;
	
	private String id;
	private long objId;	
	private String role;
	private String roleOn;
	private int permission;
	private int additionalPermission;
	private List <CompletePermissions> parentPermissions;
	private List <CompletePermissions> childrenPermissions;
	private List <CompletePermissions> completeRoles;
	

	
	/**
	 * @return the roleOn
	 */
	public String getRoleOn() {
		return roleOn;
	}
	/**
	 * @param roleOn the roleOn to set
	 */
	public void setRoleOn(String roleOn) {
		this.roleOn = roleOn;
	}
	/**
	 * @return the compleRoles
	 */
	public List<CompletePermissions> getCompleteRoles() {
		return completeRoles;
	}
	/**
	 * @param compleRoles the compleRoles to set
	 */
	public void setCompleteRoles(List<CompletePermissions> completeRoles) {
		this.completeRoles = completeRoles;
	}
	public int getPermission() {
		return permission;
	}
	public void setPermission(int permission) {
		this.permission = permission;
	}
	public List<CompletePermissions> getParentPermissions() {
		return parentPermissions;
	}
	public void setParentPermissions(List<CompletePermissions> parentPermissions) {
		this.parentPermissions = parentPermissions;
	}
	public List<CompletePermissions> getChildrenPermissions() {
		return childrenPermissions;
	}
	public void setChildrenPermissions(List<CompletePermissions> childrenPermissions) {
		this.childrenPermissions = childrenPermissions;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public long getObjId() {
		return objId;
	}
	public void setObjId(long objId) {
		this.objId = objId;
	}
	public int getAdditionalPermission() {
		return additionalPermission;
	}
	public void setAdditionalPermission(int additionalPermission) {
		this.additionalPermission = additionalPermission;
	}
	
	@Override
	public int compareTo(CompletePermissions o) {
		 return this.getRole().compareTo(o.getRole());
	}

}

