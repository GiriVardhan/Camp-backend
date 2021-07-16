/**
 * 
 */
package com.jbent.peoplecentral.model.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import com.jbent.peoplecentral.client.ClientManageUtil;
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
import com.jbent.peoplecentral.model.rowmapper.AttributeFileStorageMapper;
import com.jbent.peoplecentral.model.rowmapper.AttributeValueStorageMapper;
import com.jbent.peoplecentral.model.rowmapper.BoxEntityMapper;
import com.jbent.peoplecentral.model.rowmapper.EntityAuditMapper;
import com.jbent.peoplecentral.model.rowmapper.EntityMapper;
import com.jbent.peoplecentral.model.rowmapper.EntityStatusMapper;
import com.jbent.peoplecentral.model.rowmapper.EntityTypeMapper;
import com.jbent.peoplecentral.model.rowmapper.ImportEntitiesFailedRowMapper;
import com.jbent.peoplecentral.model.rowmapper.ImportEntityMapper;
import com.jbent.peoplecentral.permission.PermissionManager;
import com.jbent.peoplecentral.postgres.PGArrayGeneric;
import com.jbent.peoplecentral.postgres.PGAttributeFileStorage;
import com.jbent.peoplecentral.postgres.PGAttributeValueStorage;
import com.jbent.peoplecentral.postgres.PGBulkEntity;
import com.jbent.peoplecentral.postgres.PGEntity;

/**
 * @author RaviT
 *
 */
public class EntityDAOPGImpl extends ApplicationObjectSupport implements EntityDAO, InitializingBean {
	
    private JdbcTemplate simpleJdbcTemplate;
    
	
	private PermissionManager permissionManager;	
	//private final String ENTITIES_SAVE = "SELECT * FROM entity_values_save(?,?);";
	private final String ENTITIES_LOAD = "SELECT * FROM entities_load();";
	private final String ENTITY_LOAD = "SELECT * FROM entity_load(?);";
	private final String ENTITY_SEARCH_SIMPLE = "SELECT * FROM entity_search_simple(?,?,?,?);";
	private final String ENTITY_SEARCH = "SELECT * FROM entity_search(?,?,?,?,?,?);";
	private final String BULK_ENTITIES_SAVE = "SELECT * FROM entity_bulk_save(?,?,?,?);";
	private final String IMPORT_TIME_END_UPDATE = "select * from mark_import_time_ended(?)";
	private final String IMPORT_UPDATE = "select * from entity_import_status(?,?,?)";	
	private final String IMPORT_COMPLETED_UPDATE = "select * from mark_import_completed(?)";
	private final String ENTITIES_SAVE_TO_BOX = "SELECT * FROM entity_box_save(?,?);";	
	private final String ENTITIES_LOAD_BOX = "SELECT * FROM entity_box_load(?,?,?,?);";
	private final String ENTITIES_ALL_LOAD_BOX = "SELECT * FROM entity_box_loadall(?);";
	private final String ENTITY_BOX_TO_DELETE = "SELECT * FROM entity_box_delete(?,?);";
	private final String ENTITIES_BOX_COUNT = "SELECT * FROM entities_box_count(?);";
	private final String ENTITY_SEARCH_COMPLEX = "SELECT * FROM entity_search_complex(?,?,?,?,?);";
	private final String ROLES_LOAD = "SELECT * FROM all_roles_load()";
	private final String ASSIGNED_ROLES_LOAD = "SELECT * FROM assigned_roles_load(?); ";
	private final String ENTITIES_IMPORTED_LOAD = "SELECT * FROM entities_imported_load(?,?); ";
	private final String ENTITIES_QUICK_SAVE = "SELECT * FROM entity_values_auto_save(?,?);";
	private final String ENTITIES_SAVE = "SELECT * FROM entity_set_valid(?);";
	private final String AVS_SAVE = "SELECT * FROM attribute_value_save(?);";
	private final String FILES_SAVE = "SELECT * FROM attribute_file_value_save(?);";
	private final String SINGLE_FILES_SAVE = "SELECT * FROM attribute_single_file_value_save(?);";
	private final String FILE_DELETE = "SELECT * FROM attribute_file_to_delete(?,?);";
	private final String ENTITIES_LOAD_REPORTS = "SELECT * FROM entity_load_report(?,?);";
	private final String IMAGES_LOAD = "SELECT * FROM entity_file_storage_load(?);";
	private final String ENTITIES_SAVED_SEARCH_LOAD = "SELECT * FROM entities_saved_search_load(?);";
	private final String ENTITY_LOAD_BY_USERNAME = "SELECT * FROM user_profile_load(?);";
	private final String ENTITY_DELETE = "SELECT entity_delete(?,?);";
	private final String ENTITIES_INVALID_LOAD = "SELECT * FROM entities_invalid_load(?);";
	private final String SEARCH_RESULTS_ENTITYTYPE = "SELECT * FROM entity_search_rtn_enttype(?);";

	
	@Override
    public void afterPropertiesSet() throws Exception {
		Assert.notNull(simpleJdbcTemplate, "SimpleJDBCTemplate is null");
		Assert.notNull(permissionManager, "permissionManager is null");
	}
	
	@Autowired
	public void setDataSource(DataSource dataSource) { 
		this.simpleJdbcTemplate = new JdbcTemplate(dataSource);
		
		
	} 
	
	
	@Override
	public Entity save(Entity entity) throws DataException {
		// TODO Auto-generated method stub
		List<Entity> etlist = null;
		entity.setMod_user(permissionManager.getUsername());
		try {
			List<EntityMapper> em = simpleJdbcTemplate.query(ENTITIES_SAVE, new EntityMapper(), new PGEntity(entity));
			if(em != null && em.size() > 0){
				etlist = em.get(0).getEntities();
			}
		} catch (Exception e) {
			logger.error("EntityDAOImpl : save failed : ");
			throw new DataException("Unable to save Entity ",e);
		}
		if(etlist == null){
			logger.error("EntityDAOImpl : No entities found to return:"+etlist);
			return null;
		}
		return etlist.get(etlist.size()-1);
    }
	
	@Override
	public Long quickSave(Entity entity, List<AttributeValueStorage> attValuelist) throws DataException {
		// TODO Auto-generated method stub
		Long entityId;
		try {
			entity.setMod_user("jbent");
			for(AttributeValueStorage storage:attValuelist ){
				storage.setMod_user("jbent");			
			}
			entityId= simpleJdbcTemplate.queryForObject(ENTITIES_QUICK_SAVE,new Object[] {new PGEntity(entity),PGAttributeValueStorage.getPGAttributeValueArray(attValuelist)}, Long.class);
		} catch (Exception e) {
			logger.error("EntityDAOImpl : save failed : ");
			throw new DataException("Unable to auto save Entity ",e);
		}
		return ((entityId == null) ? 0 : entityId.longValue());
    }
	
	@Override
	public EntityStatus avsSave(AttributeValueStorage attributeValueStorage) throws AttributeValueNotValidException, DataException {
		
		EntityStatus entityStatus = null;	
		
		try {
			attributeValueStorage.setMod_user("jbent");
			List<EntityStatus> esm = simpleJdbcTemplate.query(AVS_SAVE, new EntityStatusMapper(),  new PGAttributeValueStorage(attributeValueStorage));
			if(esm != null && esm.size() > 0){
				entityStatus = esm.get(0);
			   }
		}catch (Exception  e) {
			logger.error("EntityStatus : avsSave failed : ");
			throw new DataException("Unable to auto save Entity ",e);
		}
		return entityStatus;
    }
	
    @Override	     
    public Entity  loadEntity(long entityId)throws DataException{
	   List<Entity> entities = null;
	   try{
		   List<EntityMapper> em = simpleJdbcTemplate.query(ENTITY_LOAD,new EntityMapper(),entityId);
		   if(em != null && em.size() > 0){
			   entities = em.get(0).getEntities();
		   }
	   }catch (Exception e) {
		   logger.error("EntityDAOImpl : loadEntity failed : "+e);
		   throw new DataException("Unable to load Entity",e);
	   }
	   if(entities == null){
		   logger.debug("No Entity Found in Mapper");
		   return null;
	   }
	   if(entities.size() > 1){
		   logger.warn("More than one Entity Found in Mapper with id of " + entityId);
	   }
	   return entities.get(0);
    }
    
    @Override   
    public List<Entity>  loadEntities()throws DataException{
    	List<Entity> entities = null;
    	try{
    		List<EntityMapper>  em = simpleJdbcTemplate.query(ENTITIES_LOAD, new EntityMapper());
    		if(em != null && em.size() > 0){
    			entities = em.get(0).getEntities();
    		}
    	}catch(Exception e){
    		logger.error("EntityDAOImpl : loadEntities failed : "+e);
 		   throw new DataException("Unable to loadEntities",e);
    	}
    	return entities;
	}
   
    @Override
    public List <Entity> entitySearchSimple(String searchTerm,long EntityTypeId,String startHighlight,String endHighlight) throws DataException {
	   List<Entity> entities = null;
	   searchTerm=searchTerm.replaceAll(" +", " ").trim(); 
	   String[] arr=searchTerm.split(" ");
       PGArrayGeneric st = new PGArrayGeneric();	
  	   st.setArray(Types.VARCHAR,arr);
       try {
    	   List<EntityMapper> em = simpleJdbcTemplate.query(ENTITY_SEARCH_SIMPLE, new EntityMapper(),st,EntityTypeId,startHighlight,endHighlight);
    	   if(em != null && em.size() > 0){
    		   entities = em.get(0).getEntities();
		   }
       } catch (Exception e) {
    	   logger.error("EntityDAOImpl : search failed : ", e);
    	   throw new DataException("Unable to search ",e);
	   }
       if(entities == null){
    	   logger.debug("No Entity Found ");
    	   return null;
       }
	   return entities;
    }
     
    @Override
    public List <Entity> entitySearch(String searchTerm,long EntityTypeId,String startHighlight,String endHighlight,long limit, long offsetValue) throws DataException{ 
    	List<Entity> entities = null;
    	List<String> searchTerm1 = new ArrayList<String>();
    	PGArrayGeneric st = new PGArrayGeneric();
    	if(searchTerm.startsWith("$")){
   	   		searchTerm1.add(searchTerm);
   	   		st.setArray(Types.VARCHAR, searchTerm1.toArray());
   	   	}
   	   	else {
   	   		String[] arr=searchTerm.split(" ");
   	   		st.setArray(Types.VARCHAR,arr);
   	   	}
   	   	try {
   	   		List<EntityMapper> em = simpleJdbcTemplate.query(ENTITY_SEARCH, new EntityMapper(),st,EntityTypeId,startHighlight,endHighlight,limit,offsetValue);
    		if(em != null && em.size() > 0){
    			entities = em.get(0).getEntities();
    		}
    	} catch (Exception e) {
    		logger.error("EntityDAOImpl : search failed : ", e);
    		throw new DataException("Please check the syntax/specify corret attribute or entitytype ",e);
    	}
    	if(entities == null){
    		logger.debug("No Entity Found ");
    		return entities;
    	}
	    return entities;
    }
    
    @Override
    public List<EntityType> entityTypesSearchResults(String[] searchTerm) throws DataException {
    	List<EntityType> ets = null;
    	PGArrayGeneric st = new PGArrayGeneric();
    	st.setArray(Types.VARCHAR,searchTerm);
        try {
   	   		List<EntityTypeMapper> em = simpleJdbcTemplate.query(SEARCH_RESULTS_ENTITYTYPE, new EntityTypeMapper(),st);
    		if(em != null && em.size() > 0){
    			ets = em.get(0).getEntities();
    		}
    	} catch (Exception e) {
    		logger.error("EntityDAOImpl : search failed : ", e);
    		throw new DataException("Please check the syntax/specify corret attribute or entitytype ",e);
    	}
    	if(ets == null){
    		logger.debug("No EntityTypes Found ");
    		return ets;
    	}
	    return ets;
    }
    
    public List <Entity> entitySearchComplex(String[] searchTerm,long entityTypeId,String startHighlight,String endHighlight,long limit, long offsetValue) throws DataException{ 
    	List<Entity> entities = null;
    	PGArrayGeneric st = new PGArrayGeneric();
    	st.setArray(Types.VARCHAR,searchTerm);
        try {
   	   		List<EntityMapper> em = simpleJdbcTemplate.query(ENTITY_SEARCH_COMPLEX, new EntityMapper(),st,startHighlight,endHighlight,limit,offsetValue);
    		if(em != null && em.size() > 0){
    			entities = em.get(0).getEntities();
    		}
    	} catch (Exception e) {
    		logger.error("EntityDAOImpl : search failed : ", e);
    		throw new DataException("Please check the syntax/specify corret attribute or entitytype ",e);
    	}
    	if(entities == null){
    		logger.debug("No Entity Found ");
    		return entities;
    	}
	    return entities;
    }
    

    
 	@Override
	public Import saveBulkEntities(List<BulkEntity> bulkEntities, String uid,long startNum,EntityType entityType) throws DataException  {
  		
 		List<String> entityIds = null; 		
 		Import importStatus =null;
 		try{
 			List<Map<String, Object>> etIdsMapList = simpleJdbcTemplate.queryForList(BULK_ENTITIES_SAVE,PGBulkEntity.getPGBulkEntityArray(bulkEntities),uid,entityType.getId(),startNum);
 			if(etIdsMapList != null && etIdsMapList.size()> 0){
 				Map<String, Object> etIdsMap = etIdsMapList.get(0);
 				importStatus = new Import();
 				String etIdsValue = etIdsMap.get("ent_ids").toString();
 				Long numberAttempted = Long.parseLong(etIdsMap.get("num_attempted").toString());
 				Long numberFailed = Long.parseLong(etIdsMap.get("num_failed").toString());
 	 			String[] etIds= etIdsValue.split(",");
 	 			entityIds = Arrays.asList(etIds); 	 			
 	 			importStatus.setImportedEntitiesIds(entityIds);
 	 			importStatus.setNumberAttempted(numberAttempted);
 	 			importStatus.setNumberFailed(numberFailed);
 			}
 		}catch(Exception e){
 			logger.error("EntityDAOImpl.saveBulkEntities : Unable to saveBulkEntities : ", e);
    		throw new DataException("EntityDAOImpl.saveBulkEntities :Unable to save entities ",e);
 		}
 		return importStatus;
	}
 	
 	@Override
	public void updateImportTimeEnd( String uid) throws DataException {
 		try{
 			simpleJdbcTemplate.queryForMap(IMPORT_TIME_END_UPDATE, uid);
 		}catch(Exception e){
 			logger.error("EntityDAOImpl.updateImportTimeEnd : Unable to updateImportTimeEnd : ", e);
    		throw new DataException("EntityDAOImpl.updateImportTimeEnd :Unable to updateImportTimeEnd ",e);
 		}
  	}
	
 	@Override
	public void updateImportCompleted( String uid) throws DataException {
 		try{
 			simpleJdbcTemplate.queryForMap(IMPORT_COMPLETED_UPDATE, uid);
 		}catch(Exception e){
 			logger.error("EntityDAOImpl.updateImportTimeEnd : Unable to updateImportTimeEnd : ", e);
    		throw new DataException("EntityDAOImpl.updateImportTimeEnd :Unable to updateImportTimeEnd ",e);
 		}
  	}
	/**
	* @param permissionManager the permissionManager to set
	*/

    
    public List<BoxEntity> saveEntitiesToBox(Long[] entityIds) throws DataException{
    	List<BoxEntity> boxEntity = null;    	   	
    	PGArrayGeneric st = new PGArrayGeneric();
    	st.setArray(Types.BIGINT, entityIds);
    	try{
    		boxEntity=simpleJdbcTemplate.query(ENTITIES_SAVE_TO_BOX,new BoxEntityMapper(),st,permissionManager.getUsername());
    	}catch(Exception e){
    		logger.error("EntityDAOImpl.saveEntitiesToBox : Unable to saveEntitiesToBox : ", e);
    		throw new DataException("EntityDAOImpl.saveEntitiesToBox :Unable to saveEntitiesToBox ",e);
    	}
    	return boxEntity;		
	}
    
    @Override
    public List <Entity> loadBoxEntities(String order, long limit, long offset) throws DataException{ 
    	List<Entity> entities = null; 	
    	try {
   	   		List<EntityMapper> em = simpleJdbcTemplate.query(ENTITIES_LOAD_BOX, new EntityMapper(),permissionManager.getUsername(),order,limit,offset);
   	   		if(em != null && em.size() > 0){
    			entities = em.get(0).getEntities();
    		}
    	} catch (Exception e) {
    		logger.error("EntityDAOImpl : Box entity load failed : ", e);
    		throw new DataException("Unable to search ",e);
    	}
    	if(entities == null){
    		logger.debug("No Entity Found ");
    		return entities;
    	}
	    return entities;
    }
    
    @Override
    public List <Entity> loadAllBoxEntities() throws DataException{ 
    	List<Entity> entities = null; 	
    	
   	   	try {
   	   		
   	   	List<EntityMapper> em = simpleJdbcTemplate.query(ENTITIES_ALL_LOAD_BOX, new EntityMapper(),permissionManager.getUsername());
    		if(em != null && em.size() > 0){
    			entities = em.get(0).getEntities();
    		}
    	} catch (Exception e) {
    		logger.error("EntityDAOImpl : Box entity load failed : ", e);
    		throw new DataException("Unable to search ",e);
    	}
    	if(entities == null){
    		logger.debug("No Entity Found ");
    		return entities;
    	}
	    return entities;
    }
    
    
    @Override
	public Long  removeBoxEntity(long entityId) throws DataException {    	
    	Long count = null;
    	try{
    		count =simpleJdbcTemplate.queryForObject(ENTITY_BOX_TO_DELETE,new Object[]{entityId,permissionManager.getUsername()},Long.class);
    	}catch(Exception e){
    		logger.error("EntityDAOImpl : failed to removeBoxEntity : ", e);
    		throw new DataException("Unable to removeBoxEntity ",e);
    	}
    	//entities= loadBoxEntities();
    	return ((count == null) ? 0 : count.longValue());
	}
    

    @Override
	public Long  getCountBoxEntity() throws DataException {    	
    	Long count = null;
    	try{
    		count =simpleJdbcTemplate.queryForObject(ENTITIES_BOX_COUNT,new Object[] {permissionManager.getUsername()},Long.class);
    	}catch(Exception e){
    		logger.error("EntityDAOImpl : failed to getCountBoxEntity : ", e);
    		throw new DataException("Unable to getCountBoxEntity ",e);
    	}
    	return ((count == null) ? 0 : count.longValue());
	}

	public Import   fetchImportResults(String importID) throws DataException{
		List<Import> importEntities = null;
		try{
			importEntities = simpleJdbcTemplate.query("select * from import_fetch(?)", new ImportEntityMapper(),importID);
		}catch (Exception e) {
			logger.error("EntitiyDAOPGImpl : failed to fetch ImportEntity Results : ", e);
			throw new DataException("Unable to get Roles List",e);
		}
		if(importEntities == null || importEntities.size() == 0){
			logger.debug("No Entity found while fetching the ImportEntity");
			return null;
		}
		return importEntities.get(0);
	}
	
	
	@Override
    public List<AttributeValueStorage>  loadRoles() throws DataException{	   
		List<AttributeValueStorage>  availableRoles=null;List<AttributeValueStorageMapper> em=null;
		try{
			em = simpleJdbcTemplate.query(ROLES_LOAD, new AttributeValueStorageMapper());
		}catch(Exception e){
			logger.error("loadRoles : failed to fetch load Roles : ", e);
			throw new DataException("loadRoles : failed to fetch load Roles : ", e);
		}
		if(em != null && em.size() > 0){
			availableRoles = em.get(0).getAttValues();
		}
		return availableRoles;
	}

	
	public List<AttributeValueStorage>  loadAssignNewRoles(String[] authorities) throws DataException{
		List<AttributeValueStorage> assingedRoles=null; List<AttributeValueStorageMapper> em=null;
		PGArrayGeneric st = new PGArrayGeneric();
    	st.setArray(Types.VARCHAR,authorities);
    	try{
    		 em = simpleJdbcTemplate.query(ASSIGNED_ROLES_LOAD, new AttributeValueStorageMapper(),st);
    	}catch(Exception e){
    		logger.error("loadAssignNewRoles : failed to fetch loadAssignNewRoles : ", e);
			throw new DataException("loadAssignNewRoles : failed to fetch loadAssignNewRoles : ", e);
    	}
    	
    	if(em != null && em.size() > 0){
    		assingedRoles = em.get(0).getAttValues();
		}
    	return assingedRoles;
	}

	 
	public List<Entity>  loadImportedEntities(long entityTypeId,String username)throws DataException{	   
		List<Entity> entities = null;List<EntityMapper> em= null;
		try{
			 em =simpleJdbcTemplate.query(ENTITIES_IMPORTED_LOAD, new EntityMapper(),entityTypeId,username);
		}catch(Exception e){
			logger.error("loadImportedEntities : failed to fetch loadImportedEntities : ", e);
			throw new DataException("loadImportedEntities : failed to fetch loadImportedEntities : ", e);
		}		
		if(em != null && em.size() > 0){
			   entities = em.get(0).getEntities();
		}
		return entities;
	}
	  
	

	public AttributeFileStorage fileSave(List<AttributeFileStorage> fileValues) throws DataException {
		
		List<AttributeFileStorageMapper> filesMapper = null;
		AttributeFileStorage afs = null;

		for(AttributeFileStorage file:fileValues ){
			file.setModUser(permissionManager.getUsername());			
		}
		try {

			filesMapper= simpleJdbcTemplate.query(FILES_SAVE,new AttributeFileStorageMapper(), PGAttributeFileStorage.getPGAttributeFileArray(fileValues));
			if(filesMapper == null){
				logger.error("EntityDAOImpl : No file entities found in the File mapper : ");
				return null;
			}
		} catch (Exception e) {
			logger.error("EntityDAOImpl.fileSave : save failed : ", e);
			throw new DataException("Unable to auto save Entity ",e);
		}
		if(filesMapper != null && filesMapper.size() > 0){
			afs = filesMapper.get(0).getAttFiles().get(0);
		 }
		return afs;

    }
	
public AttributeFileStorage fileSaveSingle (AttributeFileStorage attributeFileStorage) throws DataException {
		
		List<AttributeFileStorageMapper> filesMapper = null;
		AttributeFileStorage afs = null;
		
		attributeFileStorage.setModUser(permissionManager.getUsername());			
		
		try {

			filesMapper= simpleJdbcTemplate.query(SINGLE_FILES_SAVE,new AttributeFileStorageMapper(),  new PGAttributeFileStorage(attributeFileStorage));
			if(filesMapper == null){
				logger.error("EntityDAOImpl : No file entities found in the File mapper : ");
				return null;
			}
		} catch (Exception e) {
			logger.error("EntityDAOImpl.fileSave : save failed : ", e);
			throw new DataException("Unable to auto save Entity ",e);
		}
		if(filesMapper != null && filesMapper.size() > 0){
			afs = filesMapper.get(0).getAttFiles().get(0);
		 }
		return afs;

    }

	@Override
	public void deleteFile(long attributeFileId, String userName) throws DataException {

		try{
			simpleJdbcTemplate.queryForMap(FILE_DELETE,attributeFileId, userName);
		}catch(Exception e){
			logger.error("EntityDAOImpl : failed to delete file : ", e);
			throw new DataException("Unable to delete file ",e);
		}
	}
	
	@Override
	public List<Entity> loadEntities(long entityTypeId,List<Long> attributeIds) throws DataException {
		List<Entity> entities = null;
		PGArrayGeneric st = new PGArrayGeneric();
		st.setArray(Types.BIGINT, attributeIds.toArray());
		try{
			List<EntityMapper> em = simpleJdbcTemplate.query(ENTITIES_LOAD_REPORTS, new EntityMapper(),entityTypeId,st);
			if(em != null && em.size() > 0){
				   entities = em.get(0).getEntities();
			}
		}catch(Exception e){
			logger.error("loadImportedEntities : failed to fetch loadImportedEntities : ", e);
			throw new DataException("loadImportedEntities : failed to fetch loadImportedEntities : ", e);
		}
		return entities;	
	}
	
	@Override   
	public List<AttributeFileStorage>  loadImages(long entityId)throws DataException{	   
		 List<AttributeFileStorage> fileStorages = null; List<AttributeFileStorageMapper>  fileMapper= null;
		 try{
			  fileMapper= simpleJdbcTemplate.query(IMAGES_LOAD, new AttributeFileStorageMapper(), entityId); 
		 }catch(Exception e){
			 logger.error("loadImages : failed to fetch loadImages : ", e);
			 throw new DataException("loadImages : failed to fetch loadImages : ", e);
		 }
		 if(fileMapper != null && fileMapper.size() > 0){
			 fileStorages = fileMapper.get(0).getAttFiles();
		 }
		 return fileStorages;		
	}
	
	@Override	     
	public List<Entity>  loadEntities(long entityTypeId)throws DataException{
		   List<Entity> entities = null;
		   try{
			   List<EntityMapper> em = simpleJdbcTemplate.query(ENTITIES_SAVED_SEARCH_LOAD,new EntityMapper(),entityTypeId);
			   if(em != null && em.size() > 0){
				   entities = em.get(0).getEntities();
			   }
		   }catch (Exception e) {
			   logger.error("EntityDAOImpl : loadEntity failed : ");
			   throw new DataException("Unable to load Entity",e);
		   }
		   if(entities == null){
			   logger.debug(" Entities NOT Found in Mapper");
			   return null;
		   }
		   return entities;
	}
	
	@Override
	public Entity loadEntityByUsername(String userName) throws DataException {
		 List<Entity> entities = null;
		   try{
			   List<EntityMapper> em = simpleJdbcTemplate.query(ENTITY_LOAD_BY_USERNAME,new EntityMapper(),userName);
			   if(em != null && em.size() > 0){
				   entities = em.get(0).getEntities();
			   }
		   }catch (Exception e) {
			   logger.error("EntityDAOImpl : loadEntity failed : "+e);
			   throw new DataException("Unable to load Entity",e);
		   }
		   if(entities == null){
			   logger.debug("No Entity Found in Mapper");
			   return null;
		   }
		   return entities.get(0);
	}
    
    /**
	 * @param permissionManager the permissionManager to set
	 */

	@Autowired
	public void setPermissionManager(PermissionManager permissionManager) {
		this.permissionManager = permissionManager;
	}

	@Override
	public List<ImportEntitiesFailedRow> fetchImportFailed(String importID) throws DataException {
		List<ImportEntitiesFailedRow> importFailedEntities = null;
		try{
			importFailedEntities = simpleJdbcTemplate.query("SELECT * FROM ONLY "+ ClientManageUtil.loadClientSchema()+".import_failed_row_"+ ClientManageUtil.loadClientSchema()+" WHERE import_id = ? ", new ImportEntitiesFailedRowMapper(),importID);
		}catch (Exception e) {
			logger.error("EntitiyDAOPGImpl : failed to fetch ImportFailed Results : ", e);
			throw new DataException("Unable to get Roles List",e);
		}
		if(importFailedEntities == null || importFailedEntities.size() == 0){
			logger.info("No Entity found while fetching the ImportFailed");
			return null;
		}
		return importFailedEntities;
	}

	@Override
	public void updateImport(String uId, long numberAttempted, long numberFailded)
			throws DataException {
		try{
 			simpleJdbcTemplate.queryForMap(IMPORT_UPDATE, uId,numberAttempted,numberFailded);
 		}catch(Exception e){
 			logger.error("EntityDAOImpl.updateImportTimeEnd : Unable to updateImportTimeEnd : ", e);
    		throw new DataException("EntityDAOImpl.updateImportTimeEnd :Unable to updateImportTimeEnd ",e);
 		}
		
	}

	@Override
	public List<Entity> loadInvalidEntities() throws DataException {
		List<Entity> entities = null;
		try{
			List<EntityMapper> em =  simpleJdbcTemplate.query(ENTITIES_INVALID_LOAD, new EntityMapper(),permissionManager.getUsername());	
			if(em != null && em.size() > 0){
				entities = em.get(0).getEntities();
			}
		}catch(Exception e){
    		logger.error("EntityDAOImpl : load Invalid Entities failed : "+e);
 		   throw new DataException("Unable to load Invalid Entities",e);
    	}
		return entities;
	}

	@Override
	public void removeEntity(long entityId) throws DataException {
		
    	try{
    		simpleJdbcTemplate.queryForMap(ENTITY_DELETE,entityId, permissionManager.getUsername());
    	}catch(Exception e){
    		logger.error("EntityDAOImpl : failed to removeInvalidEntity : ", e);
    		throw new DataException("Unable to removeInvalidEntity ",e);
    	}
 	}

	@Override
	public Date loadEntityModDate(long entityId) throws DataException {
		   try{
			   List<EntityAuditMapper> em = simpleJdbcTemplate.query("SELECT * FROM audit_entity(?,?,?,?,?)",new EntityAuditMapper(),entityId,null,null, 1,0);
			   if(em != null && em.size() > 0){
				   return em.get(0).getModDate();
			   }
		   }catch (Exception e) {
			   logger.error("EntityDAOImpl : loadEntity failed : "+e);
			   throw new DataException("Unable to load Entity",e);
		   }
			return null;
	    }
	}
