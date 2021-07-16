/**
 * 
 */
package com.jbent.peoplecentral.permission;

import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.ObjectIdentityRetrievalStrategyImpl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;

/**
 * @author jasontesser
 *
 */
public class SpringSecurityACLStrategy implements
		ObjectIdentityRetrievalStrategy {

	/* (non-Javadoc)
	 * @see org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy#getObjectIdentity(java.lang.Object)
	 */
	@Override
	public ObjectIdentity getObjectIdentity(Object o) {
		if(!(o instanceof Permissionable)){
			return new ObjectIdentityRetrievalStrategyImpl().getObjectIdentity(o);
		}
		return new ObjectIdentityImpl(((Permissionable)o).getPermissionType(), ((Permissionable)o).getPermissionId());
	}

}
