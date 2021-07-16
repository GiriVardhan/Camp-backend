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
import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;

/**
 * @author Prasad BHVN
 *
 */
public class AdminManagerImpl extends ApplicationObjectSupport implements AdminManager, InitializingBean {

	private AdminDAO adminDAO;
	@Autowired
	private EntityDAO entityDAO;
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(adminDAO, "adminDAO is null");
	}

	@Override
	public boolean createClient(String schemaName, String clientName)
			throws DataException {
		// TODO Auto-generated method stub
		return adminDAO.createClient(schemaName, clientName);
		
	}

	

	@Override
	public List<AttributeValueStorage> loadUsers() throws DataException {
		// TODO Auto-generated method stub
		return adminDAO.loadUsers();
	}

	@Autowired
	public void setAdminDAO(AdminDAO adminDAO) {
		this.adminDAO = adminDAO;
	}
	
	public List<AttributeValueStorage>  loadRoles() throws DataException{
		return entityDAO.loadRoles();
	}

	

	

}
