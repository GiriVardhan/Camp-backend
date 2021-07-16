/**
 * 
 */
package com.jbent.peoplecentral.model.manager;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;

/**
 * @author jasontesser
 *
 */
public interface AdminManager {

	/**
	 * Administrator  only creates the Client 
	 * @param schemaName
	 * @param clientName
	 * @throws DataException
	 * return true on successful client creation
	 */
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	public boolean createClient(String schemaName, String clientName)throws DataException;

		
	/**
	 * Administrator  only load the Users  
	 * returns the list of Users
	 */
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	public List<AttributeValueStorage>  loadUsers() throws DataException;


	/**
	 * Administrator  only load the Roles   
	 * returns the list of Roles
	 */
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	public List<AttributeValueStorage>  loadRoles() throws DataException;
	
	
}
