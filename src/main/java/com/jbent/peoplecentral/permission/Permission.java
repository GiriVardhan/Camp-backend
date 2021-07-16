/**
 * 
 */
package com.jbent.peoplecentral.permission;

import org.springframework.security.acls.domain.BasePermission;

/**
 * @author jasontesser
 *
 */
public class Permission extends BasePermission {

	public enum Permissions {
		
		ADMIN(16),
		WRITE(2),
		READ(1),
		CREATE(4);
		
		private  final int id;
		
		Permissions(int id) {
			 this.id = id;
		}
		public int getValue() {
			return id; 
		}
	}
	
		protected Permission(int mask) {
			super(mask);
		}

		protected Permission(int mask, char code) {
			super(mask, code);
		}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6520557528365571535L;
	
}
