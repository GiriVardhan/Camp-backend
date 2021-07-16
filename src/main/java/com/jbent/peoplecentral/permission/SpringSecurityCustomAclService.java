/**
 * 
 */
package com.jbent.peoplecentral.permission;

import javax.sql.DataSource;

import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.AlreadyExistsException;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

/**
 * @author jasontesser
 *
 */
public class SpringSecurityCustomAclService extends JdbcMutableAclService {

	public SpringSecurityCustomAclService(DataSource dataSource,
			LookupStrategy lookupStrategy, AclCache aclCache) {
		super(dataSource, lookupStrategy, aclCache);
		// TODO Auto-generated constructor stub
	}

	public ObjectIdentity createObjectIdentityFor(ObjectIdentity objectIdentity) throws AlreadyExistsException {
        Assert.notNull(objectIdentity, "Object Identity required");

        // Check this object identity hasn't already been persisted
        if (retrieveObjectIdentityPrimaryKey(objectIdentity) != null) {
            throw new AlreadyExistsException("Object identity '" + objectIdentity + "' already exists");
        }

        // Need to retrieve the current principal, in order to know who "owns" this ACL (can be changed later on)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalSid sid = new PrincipalSid(auth);

        // Create the acl_object_identity row
        createObjectIdentity(objectIdentity, sid);
        return objectIdentity;
    }
	
	 public void createEntries(final MutableAcl acl) {
	       super.createEntries(acl);
	       
	    }
	 
	 public void updateObjectIdentity(MutableAcl acl) {
		 super.updateObjectIdentity(acl);
	 }
	 
}

