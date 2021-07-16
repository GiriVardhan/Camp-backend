package com.jbent.peoplecentral.model.dao;

import java.util.List;

import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;
import com.jbent.peoplecentral.model.pojo.Users;



@SuppressWarnings("unused")
public interface AdminDAO {	
	public boolean createClient(String schemaName, String clientName) throws DataException;
	public List<AttributeValueStorage>  loadUsers() throws DataException;
}
