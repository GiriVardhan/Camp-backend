/**
 * 
 */
package com.jbent.peoplecentral.model.dao;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jbent.peoplecentral.exception.AttributeValueNotValidException;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.pojo.AttributeFileStorage;
import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;
import com.jbent.peoplecentral.model.pojo.BoxEntity;
import com.jbent.peoplecentral.model.pojo.BulkEntity;
import com.jbent.peoplecentral.model.pojo.Entity;
import com.jbent.peoplecentral.model.pojo.EntityStatus;
import com.jbent.peoplecentral.model.pojo.EntityType;
import com.jbent.peoplecentral.model.pojo.Import;
import com.jbent.peoplecentral.model.pojo.ImportEntitiesFailedRow;


/**
 * @author RaviT
 *
 */
public interface EntityDAO {

	
	
	/**
	 * Saves an entity
	 * @param entity
	 * @param attValuelist
	 * @throws DataException
	 */
	public Entity save(Entity entity) throws DataException;
	
	/**
	 * Saves an entity
	 * @param entity
	 * @param attValue
	 * @throws DataException
	 */
	public Long quickSave(Entity entity, List<AttributeValueStorage> attValuelist) throws DataException;
	
	
	/**
	 * Saves an Attribute Value	 
	 * @param attributeValueStorage
	 * @throws DataException 
	 * @throws DataException
	 */
	public EntityStatus avsSave(AttributeValueStorage attributeValueStorage) throws AttributeValueNotValidException, DataException;
	
	/**
	 * Loads a specific Entity
	 * @param id
	 * @return
	 * @throws DataException
	 */
	public Entity loadEntity(long entityId) throws DataException;
	
	
	/**
	 * Returns all entity
	 * @return
	 * @throws DataException
	 */
	public List<Entity> loadEntities() throws DataException;
     /**
      *  	
      * @param searchTerm
      * @param entityTypeId
      * @param startHighlight
      * @param endHighlight
      * @return
      * @throws DataException
      */

	public List<Entity> entitySearchSimple(String searchTerm,long entityTypeId,String startHighlight,String endHighlight)throws DataException;
	/**
	 * 
	 * @param searchTerm
	 * @param entityTypeId
	 * @param startHighlight
	 * @param endHighlight
	 * @param limit
	 * @param offsetValue
	 * @return
	 * @throws DataException
	 */
	public List<Entity> entitySearch(String searchTerm,long entityTypeId,String startHighlight,String endHighlight,long limit, long offsetValue)throws DataException;
	/**
	 * 
	 * @param searchTerm
	 * @param entityTypeId
	 * @param startHighlight
	 * @param endHighlight
	 * @param limit
	 * @param offsetValue
	 * @return
	 * @throws DataException
	 */
	public List<Entity> entitySearchComplex(String[] searchTerm,long entityTypeId,String startHighlight,String endHighlight,long limit, long offsetValue)throws DataException;
	
	/**
	 * 
	 * @param searchTerm
	 * @return
	 * @throws DataException
	 */
	public List<EntityType> entityTypesSearchResults(String[] searchTerm) throws DataException;
	
	/**
	 * 
	 * @param bulkEntities
	 * @param id
	 * @param startNum
	 * @return
	 * @throws DataException
	 */
	public Import saveBulkEntities(List<BulkEntity> bulkEntities, String id,long startNum,EntityType entityType)throws DataException;

	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
	public void updateImportTimeEnd(String uid)	throws DataException;
	
	/**
	 * 
	 * updates the import table with 
	 * param UID
	 * @return
	 * @throws DataException
	 */
	public void updateImportCompleted( String uid) throws DataException;
	/**
	 * 
	 * updates the import table with 
	 * param numberAttempted,numberFailed
	 * @return
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
	public void updateImport(String uId, long numberAttempted, long numberFailded)throws DataException;
	
	/**
	 * 
	 * @param entityId
	 * @return
	 * @throws DataException
	 */
	public List<BoxEntity>  saveEntitiesToBox(Long[] entityIds) throws DataException;	
	
	/**
	 * 
	 * Loads a box Entities
	 * @return
	 * @throws DataException
	 */
	public List<Entity> loadBoxEntities(String order, long limit, long offset)throws DataException;
	
	/**
	 * 
	 * Loads all box Entities
	 * @return
	 * @throws DataException
	 */
	public List<Entity> loadAllBoxEntities()throws DataException;
	
	/**
	 * 
	 * Removes box Entity and return box entities
	 * @return
	 * @throws DataException
	 */
	public Long removeBoxEntity(long entityId)throws DataException;	
	/**
	 * 
	 * get entity count 
	 * @return
	 * @throws DataException
	 */
	public Long getCountBoxEntity()throws DataException;	
	
	/**
	 * 
	 * get ImportResults 
	 * @param importID
	 * @return
	 * @throws DataException
	 */
	public Import  fetchImportResults(String importID) throws DataException;
	/**
	 * 
	 * get ImportResults 
	 * @param importID
	 * @return
	 * @throws DataException
	 */
	public List<ImportEntitiesFailedRow>  fetchImportFailed(String importID) throws DataException;

	/**
	 * get All Roles 
	 * @return
	 * @throws DataException
	 */
	public List<AttributeValueStorage> loadRoles() throws DataException;
	/**
	 * 
	 * Returns the Assigned Roles list
	 * @return
	 * @throws DataException
	 */
	public List<AttributeValueStorage>  loadAssignNewRoles(String[] authorities) throws DataException;
	/**
	 * 
	 * Returns the Imported Entities list
	 * @return
	 * @throws DataException
	 */
	public List<Entity>  loadImportedEntities(long entityTypeId,String username)throws DataException;
	
	/**
	 * 
	 * saves the list of files uploaded and returns the entityId
	 * @return
	 * @throws DataException
	 */

	public AttributeFileStorage fileSave(List<AttributeFileStorage> fileValues) throws DataException ;
	/**
	 * Returns all entities of Specific EntityType
	 * @return
	 * @throws DataException
	 */
	public List<Entity> loadEntities(long entityTypeId,List<Long> attributeIds) throws DataException;
	/**
	 * Returns all Images of Specific Entity
	 * @return
	 * @throws DataException
	 */
	public List<AttributeFileStorage> loadImages(long entityId) throws DataException;

	/**
	 * Returns EntityModDate of the Specified Entity
	 * @return
	 * @throws DataException
	 */
	
	public Date loadEntityModDate(long entityTypeId)throws DataException;
	
	/**
	 * Returns all Entities of the Specified EntityType
	 * @return
	 * @throws DataException
	 */
	
	public List<Entity>  loadEntities(long entityTypeId)throws DataException;
	
	/**
	 * Returns all Entities of the Specified EntityType
	 * @return
	 * @throws DataException
	 */

	public Entity loadEntityByUsername(String userName)throws DataException;
	
	/**
	 * Returns all Invalid Entities
	 * @return
	 * @throws DataException
	 */
	public List<Entity> loadInvalidEntities() throws DataException;
	
	/**
	 * Remove a Invalid entity  
	 * @return
	 * @throws DataException
	 */
	public void removeEntity(long entityId) throws DataException;
	
	/**
	 *Saves single file
	 * @return
	 * @throws DataException
	 */
	
	public AttributeFileStorage fileSaveSingle (AttributeFileStorage attributeFileStorage) throws DataException;
	
	/**
	 *delete a file
	 * @return
	 * @throws DataException
	 */
	public void deleteFile(long attributeFileId, String userName) throws DataException;
	
	
}
