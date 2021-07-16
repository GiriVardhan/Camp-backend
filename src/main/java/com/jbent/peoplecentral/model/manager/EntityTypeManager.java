/**
 * 
 */
package com.jbent.peoplecentral.model.manager;

import java.sql.Date;
import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.dao.EntityTypeDAO;
import com.jbent.peoplecentral.model.pojo.Attribute;
import com.jbent.peoplecentral.model.pojo.AttributeDataType;
import com.jbent.peoplecentral.model.pojo.AttributeFieldType;
import com.jbent.peoplecentral.model.pojo.AttributeFileStorage;
import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;
import com.jbent.peoplecentral.model.pojo.AuditSummary;
import com.jbent.peoplecentral.model.pojo.CompletePermissions;
import com.jbent.peoplecentral.model.pojo.EntityType;
import com.jbent.peoplecentral.model.pojo.Regex;

/**
 * @author Jason Tesser
 *
 */

public interface EntityTypeManager {
	
	
	/**
	 * Returns all entity types
	 * @param id
	 * @return
	 * @throws DataException
	 */
	@Transactional(readOnly=true)
	@PostFilter("hasPermission(filterObject, 'ADMINISTRATION') or hasPermission(filterObject, 'WRITE') or hasPermission(filterObject, 'READ')")
	public List<EntityType> load() throws DataException;
	
	/**
	 * Returns EntityTypes if the current user has 'CREATE' Permission on the list of Objects
	 * @param id
	 * @return
	 * @throws DataException
	 * */	
	@Transactional(readOnly=true)
	@PostFilter("hasPermission(filterObject, 'CREATE') and (hasPermission(filterObject, 'ADMINISTRATION') or hasPermission(filterObject, 'WRITE'))")
	public List<EntityType> loadEntiyTypeDropDownList() throws DataException;
		
	
	/**
	 * Returns all entity types based on the limit and the offset
	 * @param id
	 * @return
	 * @throws DataException
	 */
	@Transactional(readOnly=true)
	@PostFilter("hasPermission(filterObject, 'ADMINISTRATION') or hasPermission(filterObject, 'WRITE') or hasPermission(filterObject, 'READ')")
	public List<EntityType> entityTypeTableload(long limit,long offset) throws DataException;
	
	/**
	 * Loads a specific EntityType
	 * @return
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	@PostAuthorize("hasPermission(new com.jbent.peoplecentral.model.pojo.EntityType(#id), 'ADMINISTRATION') or hasPermission(new com.jbent.peoplecentral.model.pojo.EntityType(#id), 'WRITE') or hasPermission(new com.jbent.peoplecentral.model.pojo.EntityType(#id), 'READ')")
	public EntityType load(long id) throws DataException;
	
	/**
	 * Saves and returns an entity type
	 * @param entityType
	 * @return
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	public EntityType save(EntityType entityType) throws DataException;
	
	/**
	 * Will return all entity types
	 * @param entityTypes
	 * @return
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	public List<EntityType> save(List<EntityType> entityTypes) throws DataException;
	
	/**
	 * Will return all entity types
	 * @param attributes
	 * @return
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	public List<EntityType> saveAttributes(List<Attribute> attributes) throws DataException;
	
	/**
	 * Saves an attribute and will return the specific entity type for that attribute
	 * @param attribute
	 * @return
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	public EntityType saveAttribute(Attribute attribute) throws DataException;
	
	/**
	 * Will retrieve a specific attribute
	 * @param id
	 * @return
	 * @throws DataException
	 */
	@Transactional(readOnly=true)
	@PostAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	public Attribute loadAttribute(long id) throws DataException;
	

	/**
	 * Will prepare a specific attribute velocity name by attribute name
	 * @param attributeName
	 * @return
	 * @throws DataException
	 */
	public String prepareAttributeVelocityName(String attributeName) throws DataException;
	
	/**
	 * Will prepare a specific attribute velocity name list by attribute name
	 * @param attributeList
	 * @return
	 * @throws DataException
	 */
	
	public List <Attribute> prepareAttributeVelocityNameList(List<Attribute> attributeList) throws DataException;

	/**
	 * Can only be used to save attributes of a single entity type
	 * Will reorder to the order passed in
	 * @param attributeIds
	 * @return
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	public EntityType reorderAttribute(List<Long> attributeIds) throws DataException;
	
	/**
	 * Sets DAO for Injection
	 * @param entityTypeDAO
	 */
	public void setEntityTypeDAO(EntityTypeDAO entityTypeDAO);
	
	/**
	 * Deletes and Entity Type
	 * @param id
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	public void delete(long id) throws DataException;
	
	/**
	 * lock  or unlock and Entity Type
	 * @param id
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	public boolean lock(long id, String lockMode) throws DataException;
	
	/**
	 * lock  and Entity Type
	 * @param id
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	public void setToLock(List<Long> etIdsList) throws DataException;
	
	/**
	 * Deletes an attribute
	 * @param id
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	public void deleteAttribute(long id) throws DataException;
	
	/**
	 * Loads the possible data/storage types of an attribute
	 * @return
	 * @throws DataException
	 */
	@Transactional(readOnly=true)
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	public List<AttributeDataType> loadAttributeDataTypes() throws DataException;
	
	/**
	 * Loads the possible field types of an attribute
	 * @return
	 * @throws DataException
	 */
	@Transactional(readOnly=true)
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	public List<AttributeFieldType> loadAttributeFieldTypes() throws DataException;
	
	/**
	 * Will retrieve set of values in regex_base for specific fieldtype_id
	 * @param fieldtype_id
	 * @return 
	 * @throws DataException
	 */
	@Transactional(readOnly=true)
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	public List<Regex> loadRegex(long fieldtypeId) throws DataException;

	
	/**
	 * Will retrieve set of values in regex_base for specific fieldtype_id
	 * @param fieldtype_id
	 * @return 
	 * @throws DataException
	 */
	@Transactional(readOnly=true)
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	public List<Regex> loadRegex(long fieldtypeId, long attributeId) throws DataException;

	/** returns true on successful  added permissions to Attribute.   
	 * @param Attribute attribute,String role,String permission
	 * @throws DataException 
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	public boolean addPermissionOnAttribute(Attribute attribute,String role,String permission) throws DataException;
	
	/**  returns true on successful  added permissions to EntityType.   
	 * @param EntityType entityType,String role,String permission
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	public  List<CompletePermissions>  addOrRemovePermissionOnEntityType(EntityType entityType,String role,String permission) throws DataException;
	
	/**  returns Complete list of permissions of specified EntityType.   
	 * @param Attribute attribute
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	public List<CompletePermissions> retrieveEntityTypePermissions(EntityType entityType) throws DataException;
	
	/**  returns  Complete list of permissions of specified Attribute.   
	 * @param Attribute attribute
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	public List<CompletePermissions> retrieveAttributePermissions(Attribute attribute) throws DataException;
	
	/**  returns  Complete list of Options of specified FieldType.   
	 * @param long fieldTypeId
	 * @throws DataException
	 */
	@Transactional(readOnly=true)
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	public AttributeFieldType loadAttributeFieldTypeOptions(long fieldTypeId)throws DataException;;
	
	/**
	 * filters the Attributes which user cann't see from the Entity
	 * @return
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	@PostFilter("hasPermission(filterObject, 'ADMINISTRATION') or hasPermission(filterObject, 'WRITE') or hasPermission(filterObject, 'READ')")
	public List<AttributeValueStorage> filterAttributesValues( List<AttributeValueStorage>  filterAttributes) throws DataException; 

	/**
	 * filters the Attributes which user cann't see from the Entity
	 * @return
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	@PostFilter("hasPermission(filterObject, 'ADMINISTRATION') or hasPermission(filterObject, 'WRITE') or hasPermission(filterObject, 'READ')")
	public List<AttributeFileStorage> filterFileAttributesValues( List<AttributeFileStorage>  filterFileAttributesValues) throws DataException; 

	/**
	 * filters the Attributes which user cann't see from the Entity
	 * @return
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	@PostFilter("hasPermission(filterObject, 'ADMINISTRATION') or hasPermission(filterObject, 'WRITE') or hasPermission(filterObject, 'READ')")
	public List<Attribute> filterAttributes(List<Attribute>  filterAttributes) throws DataException; 

	/**
	 * filters the Attributes which user cann't see from the Entity
	 * @return
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class)
	public List<AuditSummary> loadWorkStreamAudit(Date startDate,Date endDate,Long limit,long pageNum,long totalRecords,String action) throws DataException;

	EntityType findByName(String entityTypeName) throws DataException;
	
	

	
}
