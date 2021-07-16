/**
 * 
 */
package com.jbent.peoplecentral.model.manager;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.util.Assert;

import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.dao.AdminDAO;
import com.jbent.peoplecentral.model.dao.EntityDAO;
import com.jbent.peoplecentral.model.pojo.AttributeFileStorage;
import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;
import com.jbent.peoplecentral.model.pojo.Entity;
import com.jbent.peoplecentral.permission.PermissionManager;
import com.jbent.peoplecentral.web.SessionContext;

/**
 * @author Prasad BHVN
 *
 */
public class LoginUserManagerImpl extends ApplicationObjectSupport implements LoginUserManager, InitializingBean{

	@Autowired
	private PermissionManager permissionManager;
	

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setProfileImagePath() throws DataException {
		
		Entity ent = permissionManager.loadLoginUserEntity();
		List<AttributeFileStorage> afs = ent.getAttributeFileStorage();
		String path = null;
		if(afs != null){
			for(AttributeFileStorage a : afs){
				if(a.getId() == 126){
					path = a.getImagePath();
				}
			}
		}
		if(path == null){
			List<AttributeValueStorage> avs = ent.getAttributeValueStorage();
			if(avs != null){
				for(AttributeValueStorage av : avs){
					if(av.getId() == 125 && av.getValueVarchar() != null){
						path = String.valueOf(av.getValueVarchar().charAt(0)).toUpperCase();
					}
				}
			}
		}
		SessionContext.setProfileImageSessionContext(path);
		
		
	}

	


	@Override
	public void setUserInSessionContext() throws DataException {
		// TODO Auto-generated method stub
		Entity ent = permissionManager.loadLoginUserEntity();
		SessionContext.setUserEntitySessionContext(ent);
		
		//Set User Profile Image path in Session Context
		setProfileImagePath();
	} 
	
	
}
