/**
 * 
 */
package com.jbent.peoplecentral.model.dao;

import java.sql.Date;
import java.util.List;

import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.pojo.Attribute;
import com.jbent.peoplecentral.model.pojo.AttributeDataType;
import com.jbent.peoplecentral.model.pojo.AttributeFieldType;
import com.jbent.peoplecentral.model.pojo.AuditSummary;
import com.jbent.peoplecentral.model.pojo.EntityType;
import com.jbent.peoplecentral.model.pojo.Regex;

/**
 * @author Jason Tesser
 *
 */
public interface EntityTypeDAO {

	/**
	 * Loads a specific EntityType
	 * @param id
	 * @return
	 * @throws DataException
	 */
	public EntityType load(long id) throws DataException;
	
	/**
	 * Returns all entity types
	 * @return
	 * @throws DataException
	 */
	public List<EntityType> load() throws DataException;
	
	/**
	 * Returns all entity types
	 * @return
	 * @throws DataException
	 */
	public List<EntityType> entityTypeTableload(long limit,long offset) throws DataException;
	
	/**
	 * Saves and returns an entity type 
	 * @param entityType
	 * @return
	 * @throws DataException
	 */
	public EntityType save(EntityType entityType) throws DataException;
	
	/**
	 * Will return all entity types
	 * @param entityTypes
	 * @return
	 * @throws DataException
	 */
	public List<EntityType> save(List<EntityType> entityTypes) throws DataException;
	
	/**
	 * Can be used to save any attribute type including choice attributes.  
	 * It can even be mixed as well different types of attributes from different entitytypes
	 * @param attributes
	 * @return
	 * @throws DataException
	 */
	public List<EntityType> saveAttributes(List<Attribute> attributes) throws DataException;
	
	/**
	 * Saves an attribute and will return the specific entity type for that attribute
	 * @param attribute
	 * @return
	 * @throws DataException
	 */
	public EntityType saveAttribute(Attribute attribute) throws DataException;
	
	/**
	 * Will retrieve a specific attribute
	 * @param id
	 * @return
	 * @throws DataException
	 */
	public Attribute loadAttribute(long id)throws DataException;

	/**
	 * Can only be used to save attributes of a single entity type
	 * Will reorder to the order passed in
	 * @param attributeIds
	 * @return
	 * @throws DataException
	 */
	public EntityType reorderAttribute(List<Long> attributeIds) throws DataException;
	
	/**
	 * Lock and Entity Type
	 * @param id
	 * @throws DataException
	 */
	public boolean lock(long id, String lockMode) throws DataException;
	
	/**
	 * Lock and Entity Type
	 * @param id
	 * @throws DataException
	 */
	public void setToLock(List<Long> etIdsList) throws DataException;
	
	/**
	 * Deletes and Entity Type
	 * @param id
	 * @throws DataException
	 */
	public void delete(long id) throws DataException;
	
	/**
	 * Deletes an attribute
	 * @param id
	 * @throws DataException
	 */
	public void deleteAttribute(long id) throws DataException;
	
	/**
	 * Loads the possible data/storage types of an attribute
	 * @return
	 * @throws DataException
	 */
	public List<AttributeDataType> loadAttributeDataTypes() throws DataException;
	
	/**
	 * Loads the possible field types of an attribute
	 * @return
	 * @throws DataException
	 */
	public List<AttributeFieldType> loadAttributeFieldTypes() throws DataException;
	
	 /**
	 * Will retrieve set of values in regex_base for given fieldtype_id
	 * @param fieldtypeId
	 * @return
	 * @throws DataException
	 */
	public List<Regex> loadRegex(long fieldtypeId)throws DataException;
	
	 /**
	 * Will retrieve set of values in regex_base for given fieldtype_id
	 * @param fieldtypeId
	 * @return
	 * @throws DataException
	 */
	public List<Regex> loadRegex(long fieldtypeId, long attributeId)throws DataException;
	 /**
	 * Will retrieve set of options for given fieldtype_id
	 * @param fieldtypeId
	 * @return
	 * @throws DataException
	 */
	public AttributeFieldType loadAttributeFieldTypeOptions(long fieldTypeId)
	throws DataException ;
	 /**
	 * Will retrieve work stream from audit tables
	 * 
	 * @return
	 * @throws DataException
	 */
	public List<AuditSummary> loadWorkStreamAudit(Date startDate,Date endDate,Long limit,Long offset) throws DataException;

	public EntityType findByName(String entityTypeName) throws DataException;

	
}
