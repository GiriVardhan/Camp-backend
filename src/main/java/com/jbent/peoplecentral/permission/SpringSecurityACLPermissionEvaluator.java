/**
 * 
 */
package com.jbent.peoplecentral.permission;

import java.util.List;

import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author jasontesser
 *
 */
public class SpringSecurityACLPermissionEvaluator extends AclPermissionEvaluator {

	public SpringSecurityACLPermissionEvaluator(AclService aclService) {
		super(aclService);
	}

	public boolean hasPermission(Authentication authentication, Object domainObject, Object permission) {
		if (domainObject == null) {
	           return false;
	       }
	    List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
	    for(GrantedAuthority auth : authorities){
	    	if(auth.getAuthority().equalsIgnoreCase("ROLE_ADMINISTRATOR"))
	        	return true;
	        }
	        
	    return super.hasPermission(authentication, domainObject, permission);
	}
}
