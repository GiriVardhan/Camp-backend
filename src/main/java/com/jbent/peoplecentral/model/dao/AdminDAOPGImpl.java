package com.jbent.peoplecentral.model.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;
import com.jbent.peoplecentral.model.pojo.Attribute.Attributes;
import com.jbent.peoplecentral.model.rowmapper.AttributeValueStorageMapper;
import com.jbent.peoplecentral.permission.PermissionManager;

public class AdminDAOPGImpl extends ApplicationObjectSupport implements AdminDAO, InitializingBean {

	private JdbcTemplate simpleJdbcTemplate;
	private PermissionManager permissionManager;	
	private SimpleJdbcCall call;
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(simpleJdbcTemplate, "SimpleJDBCTemplate is null");
		Assert.notNull(permissionManager, "permissionManager is null");
	}

	@Autowired
	public void setDataSource(DataSource dataSource) { 
		this.simpleJdbcTemplate = new JdbcTemplate(dataSource);
		this.call = new SimpleJdbcCall(dataSource);

	} 

	public boolean createClient(String schemaName, String clientName) throws DataException {					
		Map<String, Object> result;
		boolean flag = false;
		try{
			call.withProcedureName("setup_client");
			result = call.execute(clientName,schemaName);
		}catch (Exception e) {
			logger.error("AdminDAOImpl : create Client failed : ", e);
			throw new DataException("Unable to Create Client",e);
		}
		if(!result.isEmpty())
			flag = true;
		
		return flag;
	}

	
	public List<AttributeValueStorage>  loadUsers() throws DataException{
		List<AttributeValueStorageMapper> em=null;
		List<AttributeValueStorage> users=null;
		try{
			em = simpleJdbcTemplate.query("SELECT * FROM ONLY "+ ClientManageUtil.loadClientSchema()+".attribute_value_storage_"+ClientManageUtil.loadClientSchema()+" WHERE attribute_id ="+Attributes.USERNAME.getValue(),new AttributeValueStorageMapper());
		}catch (Exception e) {
			logger.error("AdminDAOImpl : failed to get Users List : ", e);
			throw new DataException("Unable to get Users List",e);
		}
		if(em != null && em.size() > 0){
			users = em.get(0).getAttValues();
		}
		return users;
	}
	
		
	@Autowired
	public void setPermissionManager(PermissionManager permissionManager) {
		this.permissionManager = permissionManager;
	}

}
