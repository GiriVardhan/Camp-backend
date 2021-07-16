/**
 * 
 */
package com.jbent.peoplecentral.model.manager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.jbent.peoplecentral.exception.AttributeValueNotValidException;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.dao.EntityDAO;
import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;
import com.jbent.peoplecentral.model.pojo.BoxEntity;
import com.jbent.peoplecentral.model.pojo.CompletePermissions;
import com.jbent.peoplecentral.model.pojo.Entity;
import com.jbent.peoplecentral.model.pojo.EntityStatus;
import com.jbent.peoplecentral.model.pojo.EntityType;



/**
 * @author RaviT
 *
 */
public interface EntityManager {
	
	/**
	 * Saves an entity when User has permissions on Parent -> 'create' and either ('admin' or 'edit') AND Entity -> 'admin' or 'edit'   
	 * if entity == 0 Then Checking Permissions CREATE and (ADMIN or WRITE) on EntityType
	 * else Checking Permissions for (WRITE or ADMIN) on Entity	    
	 * @param entity
	 * @param attValue
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("((#entity.entityId >= 0  and hasPermission(#entity.entityType,'CREATE')) and (hasPermission(#entity.entityType,'ADMINISTRATION') or hasPermission(#entity.entityType,'WRITE'))) " +
				  "or (hasPermission(#entity,'WRITE') or hasPermission(#entity,'ADMINISTRATION'))")
	public Long quickSave(Entity entity, List<AttributeValueStorage> attValuelist) throws DataException;
	
	/**
	 * Saves an attributeValueStorage	 
	 * if entity == 0 Then Checking Permissions CREATE and (ADMIN or WRITE) on EntityType
	 * else Checking Permissions for (WRITE or ADMIN) on Entity	    
	 * @param attributeValueStorage
	 * @throws DataException 
	 * @throws DataException
	 */	
	 
	 @PreAuthorize("((#entity.entityId >= 0  and hasPermission(#entity.entityType,'CREATE')) and (hasPermission(#entity.entityType,'ADMINISTRATION') or hasPermission(#entity.entityType,'WRITE'))) " +
	 			   "or (hasPermission(#entity,'WRITE') or hasPermission(#entity,'ADMINISTRATION'))")
	 public EntityStatus avsSave(Entity entity,AttributeValueStorage attributeValueStorage) throws AttributeValueNotValidException, DataException;
	
	/**
	 * 
	 * @return
	 * @throws DataException
	 */
	@Transactional(readOnly=true)
	@PostFilter("hasPermission(filterObject, 'ADMINISTRATION') or hasPermission(filterObject, 'WRITE') or hasPermission(filterObject, 'READ')")
	public List<Entity> load()throws DataException; 
	
	/**
	 * Loads a specific EntityType
	 * @return
	 * @throws DataException
	 */
	@Transactional(readOnly=true)
	@PreAuthorize("hasPermission(new com.jbent.peoplecentral.model.pojo.Entity(#entityId),'ADMINISTRATION') or hasPermission(new com.jbent.peoplecentral.model.pojo.Entity(#entityId),'WRITE') or hasPermission(new com.jbent.peoplecentral.model.pojo.Entity(#entityId),'READ')")
	public Entity loadEntity(long entityId) throws DataException;
	
	/**
	 * Loads a specific EntityModDate
	 * @return
	 * @throws DataException
	 */
	@Transactional(readOnly=true)
	@PreAuthorize("hasPermission(new com.jbent.peoplecentral.model.pojo.Entity(#entityId),'ADMINISTRATION') or hasPermission(new com.jbent.peoplecentral.model.pojo.Entity(#entityId),'WRITE') or hasPermission(new com.jbent.peoplecentral.model.pojo.Entity(#entityId),'READ')")
	public Date loadEntityModDate(long entityId) throws DataException;
	
	/**
	* 
	* @param file
	* @param uid
	* @param entityTypeId
	* @return
	* @throws DataException
	*@throws Throwable 
	*/
	@Transactional(rollbackFor = Exception.class,propagation=Propagation.REQUIRED)
	@PreAuthorize("hasPermission(#entType,'CREATE') and (hasPermission(#entType,'ADMINISTRATION') or hasPermission(#entType,'WRITE'))")
	public String importEntity(EntityType entType,File file,String uid,Locale locale,MappingJackson2JsonView view)throws DataException;
	
		/**
	 * 
	  * @param entityId
	  * @return
	  * @throws DataException
	  */
	@Transactional(rollbackFor = Exception.class)
	public List<BoxEntity>  saveEntitiesToBox(Long[] entityIds) throws DataException;	
	
	/**
	 * 
	 * @return
	 * @throws DataException
	 */
	@Transactional(readOnly=true)
	@PostFilter("hasPermission(filterObject, 'ADMINISTRATION') or hasPermission(filterObject, 'WRITE') or hasPermission(filterObject, 'READ')")
	public List<Entity> loadBoxEntities(String order, long limit, long offset)throws DataException; 
	
	/**
	 * 
	 * @return
	 * @throws DataException
	 */
	@Transactional(readOnly=true)
	@PostFilter("hasPermission(filterObject, 'ADMINISTRATION') or hasPermission(filterObject, 'WRITE') or hasPermission(filterObject, 'READ')")
	public List<Entity> loadAllBoxEntities()throws DataException; 
	
	/**
	 * Removes an Entity from Box
	 * @param entityId
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasPermission(new com.jbent.peoplecentral.model.pojo.Entity(#entityId),'ADMINISTRATION')")
	public Long removeBoxEntity(long entityId) throws DataException;
	
	/** load the roles from the Role EntityType
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	public void exportEntities(HttpServletResponse response,List<Entity> entity) throws DataException, IOException;

	/**
	 * Sets DAO for Injection
	 * @param entityDAO
	 */
	public void setEntityDAO(EntityDAO entityDAO);
	/**
	 * 
	 * @param searchTerm
	 * @param entityId
	 * @throws DataException
	 */
	@Transactional(readOnly=true)
	@PostFilter("hasPermission(filterObject, 'ADMINISTRATION') or hasPermission(filterObject, 'WRITE') or hasPermission(filterObject, 'READ')")
	public List<Entity> entitySearchSimple(String searchTerm,long entityTypeId,String startHighlight,String endHighlight)throws DataException;
	 /**
	  * 
	  * @param searchTerm
	  * @return
	  * @throws DataException
	  */
	@Transactional(readOnly=true)
	@PostFilter("hasPermission(filterObject, 'ADMINISTRATION') or hasPermission(filterObject, 'WRITE') or hasPermission(filterObject, 'READ')")
	public List<Entity> entitySearch(String searchTerm,long entityTypeId,String startHighlight,String endHighlight,long limit, long offsetValue)throws DataException;

	/**
	 * 
	 * @param search
	 * @return
	 * @throws DataException
	 */
	@Transactional(readOnly=true)
	@PostFilter("hasPermission(filterObject, 'ADMINISTRATION') or hasPermission(filterObject, 'WRITE') or hasPermission(filterObject, 'READ')")
	public List<EntityType> searchResultsEntityType(String search) throws DataException;
		
	/**
	 * get entity count from Box_entity
	 *
	 * @throws DataException
	 */
	@Transactional(readOnly=true)
	public Long getCountBoxEntity() throws DataException;
	
	
	
	/** returns true on successful of permissions added to Entity.   
	 * @param Entity entity,String role,String permission
	 * @throws DataException 
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasPermission(#entity,'ADMINISTRATION')")
	public boolean modifyPermissionsOnEntity(Entity entity,String role,String permission) throws DataException;

	
	/** returns the complete list of entity permissions.
	 * @param String role
	 * @throws DataException
	 */
	@Transactional(readOnly=true)
	@PreAuthorize("hasPermission(#entity,'ADMINISTRATION') or hasPermission(#entity,'WRITE') or hasPermission(#entity,'READ')")
	public List<CompletePermissions> retrieveCombinedRolesForEntity(Entity entity) throws DataException;
	
		
	/** returns the All Roles
	 * @throws DataException
	 */
	@Transactional(readOnly=true)
	@PostFilter("hasPermission(filterObject, 'ADMINISTRATION') or hasPermission(filterObject, 'WRITE') or hasPermission(filterObject, 'READ')")
	public List<AttributeValueStorage>  loadRoles() throws DataException;
	
	/** returns the User Available Roles .(Only users with Admin on the Entity should be able to see the Roles div) 
	 * @throws DataException
	 */
	@Transactional(readOnly=true)
	@PreAuthorize("hasPermission(new com.jbent.peoplecentral.model.pojo.Entity(#entityId),'ADMINISTRATION')")
	public List<AttributeValueStorage>  loadAvailableRoles(long entityId) throws DataException;
	
		
	/** returns the Assign Role To User
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasPermission(new com.jbent.peoplecentral.model.pojo.Entity(#entityId),'ADMINISTRATION')")
	public boolean assignUserRole(long entityId,String role) throws DataException;
	
	/** returns the removes the User Role
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasPermission(new com.jbent.peoplecentral.model.pojo.Entity(#entityId),'ADMINISTRATION')")
	public boolean removeUserRole(long entityId,String role,MappingJackson2JsonView view) throws DataException;
	
	/** returns the Assigned Roles of the current User using spring api.(Only users with Admin on the Entity should be able to see the Roles div)
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasPermission(#entity,'ADMINISTRATION') or hasPermission(#entity,'WRITE') or hasPermission(#entity,'READ')")
	public List<AttributeValueStorage> loadAssignedRoles(Entity entity)throws DataException;
	
	/** returns the Assigned Users of the current role using spring api.(Only users with ADMIN permission on the Entity are able be able to see the users DIV)
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasPermission(#entity,'ADMINISTRATION') or hasPermission(#entity,'WRITE') or hasPermission(#entity,'READ')")
	public List<AttributeValueStorage> loadAssignedUsers(Entity entity)throws DataException;
	
	/** returns the Assigned Users of the current role using spring api.(Only users with ADMIN permission on the Entity are able be able to see the users DIV)
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasPermission(#entity,'ADMINISTRATION') or hasPermission(#entity,'WRITE') or hasPermission(#entity,'READ')")
	public List<AttributeValueStorage> loadAvailableUsers(Entity entity)throws DataException;
	
	/**
	 * Loads a SavedSearch entities 
	 * @return
	 * @throws DataException
	 */
	@Transactional(readOnly=true)
	@PostFilter("hasPermission(filterObject, 'ADMINISTRATION') or hasPermission(filterObject, 'WRITE') or hasPermission(filterObject, 'READ')")
	public List<Entity> loadEntities(long entityTypeId) throws DataException;
	
	/**
	 * Loads a SavedSearch entities 
	 * @return
	 * @throws DataException
	 */
	@Transactional(readOnly=true)
	public Entity loadEntityByUsername(String userName) throws DataException;
	/**
	 * Loads a returns the  entity title 
	 * @return
	 * @throws DataException
	 */
	@Transactional(readOnly=true)
	public String entityTitle(Entity entity) throws DataException;
	
	/**
	 * Loads a returns the  entity title 
	 * @return
	 * @throws DataException
	 */
	@Transactional(readOnly=true)
	public List<Entity> loadInvalidEntities() throws DataException;
	/**
	 * Remove a Invalid entity  
	 * @return
	 * @throws DataException
	 */
	@Transactional(rollbackFor=Exception.class)
	public void removeEntity(long entityId) throws DataException;
	
	/**
	 * Remove a Invalid entity  
	 * @return
	 * @throws DataException
	 */
	@Transactional(rollbackFor=Exception.class)
	public boolean changePassword(long entityId,String newPassword) throws DataException;

	List<Entity> searchRelations(long entityId, boolean isFrom)	throws DataException;

	public List<Entity> pullEventsInRange(LocalDate startDate, LocalDate endDate) throws DataException;
		
	public String getAttributeValue(List<AttributeValueStorage> avsList, String attributeName)
			throws DataException ;

	File getCSVFileInputStreamForEntity(long entityId) throws DataException, IOException;
	
	public String sanitizeRole(String role);
}
