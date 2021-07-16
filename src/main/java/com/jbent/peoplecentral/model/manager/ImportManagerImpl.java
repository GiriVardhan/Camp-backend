/**
 *
 */
package com.jbent.peoplecentral.model.manager;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.util.Assert;

import com.csvreader.CsvReader;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.dao.EntityDAO;
import com.jbent.peoplecentral.model.dao.EntityTypeDAO;
import com.jbent.peoplecentral.model.pojo.Attribute;
import com.jbent.peoplecentral.model.pojo.BulkEntity;
import com.jbent.peoplecentral.model.pojo.Entity;
import com.jbent.peoplecentral.model.pojo.EntityType;
import com.jbent.peoplecentral.model.pojo.Import;
import com.jbent.peoplecentral.model.pojo.ImportEntitiesFailedRow;
import com.jbent.peoplecentral.model.pojo.ImportEntity;
import com.jbent.peoplecentral.model.pojo.Attribute.DataType;
import com.jbent.peoplecentral.model.pojo.Attribute.FieldType;
import com.jbent.peoplecentral.permission.PermissionManager;


/**
 * @author RaviT
 *
 */
public class ImportManagerImpl extends ApplicationObjectSupport implements ImportManager, InitializingBean {

	@Autowired
    private PermissionManager permissionManager;
	private long maxIntervals; 
	@Autowired
	private EntityDAO entityDAO;   
	@Autowired
	private EntityTypeDAO entityTypeDAO;   
	
	
	@Autowired
    public void setMaxIntervals(long maxIntervals) {
	   this.maxIntervals = maxIntervals;
    }
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(permissionManager, "permissionManager is null");
		Assert.notNull(entityDAO, "entityDAO is null");
		Assert.notNull(entityTypeDAO, "entityTypeDAO is null");
		
	}
	public void saveBulkEntities(CsvReader reader,EntityType entityType,String uid,List<EntityType> relatedEntityTypes) throws InterruptedException ,DataException{
	   	int startVal = 0;
	   	long entitieRecordNum = 0; 
	   	boolean hasRecord = false;
	   	List<BulkEntity> singleRowEntities =  new ArrayList<BulkEntity>();
	   	List<BulkEntity> bulkEntities =  new ArrayList<BulkEntity>();
        List <String> importedEntitiesIds = new ArrayList<String>();
        Import importResult = null;
        List <ImportEntity> importedEntities = new ArrayList<ImportEntity>();
        String modUser = permissionManager.getUsername();
	    try{
	    	while(startVal <= maxIntervals){	    	   
	    	 	   	startVal++;
	        	    if(startVal == 1){
	        	    	entitieRecordNum = reader.getCurrentRecord();
	        	    }
	        	    if(startVal <= maxIntervals){
	        	    	hasRecord = reader.readRecord();
	        		    if(hasRecord){
	        		    	singleRowEntities = prepareBulkEntities(reader,entityType,modUser,relatedEntityTypes);
	        		    	bulkEntities.addAll(singleRowEntities);
	        		    }
	        	    }
	        	    if(!hasRecord || startVal > maxIntervals){
	        		    if(bulkEntities != null && bulkEntities.size()>0){
	        		    	importResult = entityDAO.saveBulkEntities(bulkEntities,uid, entitieRecordNum,entityType);
	        		    	//update the import_base table with bellow values and time_end should be null.
	        		    	entityDAO.updateImport(uid,importResult.getNumberAttempted(),importResult.getNumberFailed());
	        		    	importedEntitiesIds = importResult.getImportedEntitiesIds();
	        		    	startVal = 0;
	        			    bulkEntities.clear();
	        		    }    
	        		    if(importedEntitiesIds != null && importedEntitiesIds.size()>0){
	        		    	for(String importedEntityId :importedEntitiesIds){
	        		    		ImportEntity imptEnt = constructWrapperEntity(importedEntityId,entityType);
	            		    	importedEntities.add(imptEnt);
	        		    	}	        		    			        		    	
	                    }
	        		    // Splitting the csv file based on the line numbers(75000) to avoid java Heap OutofMemory  and also reducing the permission time.  
	        		    if(!hasRecord || importedEntities.size() >= 75000) {
	        		    	// Update the TimeEnd column in the import table if no records in the file...
	        		    	entityDAO.updateImportTimeEnd(uid);	
	        		    	// starts the permissions after import has done...
	        		    	assignPermissionOnImportedEntites(importedEntities);
	        		    	importedEntities.clear();
	        		    	if(!hasRecord){
	        		    		// Update the TimeEnd column in the import table if no records in the file...
		        		    	entityDAO.updateImportCompleted(uid);	
		        		    	break;
	        		    	}
	       		    	
	        		    }
	        	   }
		      }
	    }catch (IOException e) {
	    	logger.error("saveBulkEntities"+e.getMessage(),e);
	    }catch (Exception e) {
	         logger.error("saveBulkEntities"+e.getMessage(),e);
	    } 
	    reader.close();  
	    logger.debug("logging Ended inside saveBulkEntities()");
	 }
	 private ImportEntity constructWrapperEntity(String importedEntityId ,EntityType entityType){
		 Entity entity = new Entity();
		 ImportEntity imptEnt = new ImportEntity();		 
		 entity.setEntityId(Long.parseLong(importedEntityId));
		 imptEnt.setEntity(entity);
		 imptEnt.setParent(entityType);
	    	
		 return imptEnt;
	 }
	 private List<BulkEntity> prepareBulkEntities(CsvReader reader,EntityType entityType,String modUser,List<EntityType> relatedEntityTypes) throws DataException{
		 logger.debug("prepareBulkEntities:-logging started inside saveBattchEntities()");
	     List <Attribute> attList=new ArrayList<Attribute>();
	     List <BulkEntity> bulkEntities = new ArrayList<BulkEntity>(),relatedEntities = null;
	     BulkEntity bulkEntity = null ;
	     attList = entityType.getAttributes(); 
	     if(attList != null && attList.size() > 0){	    	
	    	 // preparing BulkEntites for attributes and related EntityTypes
	    	 for(Attribute att1 : attList){
	    		 try{
	    			 // if attribute field type is related EntityType then prepare Related Bulk Entities
	    			 if(att1.getFieldTypeName().equals(FieldType.EntityType.toString())){
	    				 relatedEntities = prepareRelatedBulkEntites(reader, entityType,att1, modUser,relatedEntityTypes);
	    				 bulkEntities.addAll(relatedEntities);
	    			 }else{
	    				 bulkEntity =  prepareBulkEntity(reader, att1.getName(),att1, entityType, modUser);
	    				 // Avoid null bulk objects
	    				 if(bulkEntity != null)
	    					 bulkEntities.add(bulkEntity);
	    			 }
	       		}catch (Exception e) {
	       	       	logger.error("prepareBulkEntities"+e.getMessage(),e);        	        	
	       	    }
	         }
	     }else{
	     	logger.error("prepareBulkEntities:- No Attributes found to prepare bulkEntity :-"+attList);
	     }
	     if(bulkEntities == null || bulkEntities.size() == 0 ){
	    	logger.error("prepareBulkEntities:- bulkEntities is empty :-"+bulkEntities +"for the record num:"+reader.getCurrentRecord());
	        return null;
	     }
	     return bulkEntities;
	 }
  	private BulkEntity prepareBulkEntity(CsvReader reader,String colName,Attribute att1,EntityType entityType,String modUser) throws DataException{
  		String value="";
  		BulkEntity bulkEntity = null ;
  		try {
  			//read Attribute Value from CSV file
  			value = reader.get(reader.getIndex(colName.trim()));
  			logger.debug("prepareBulkEntity:-Attribute Value:-"+value);
            if(!value.isEmpty()){            	
            	bulkEntity = constructBulkEntity(reader,att1,entityType,modUser);
            	bulkEntity = addBulkEntityValue(bulkEntity,att1,value);
             }else logger.debug("prepareBulkEntity:-EntityManagerImpl.prepareBulkEntities:- No value is  found to attribute :-"+att1.getName()+" and value:"+value);
 		} catch (IOException e) {
        	logger.error("prepareBulkEntity"+e.getMessage(),e);
        } catch (Exception e) {
        	logger.error("prepareBulkEntity:"+e.getMessage(),e);
			throw new DataException("prepareBulkEntity:- Unable to prepareBulkEntity :-");
		}
 		return bulkEntity;
  	}
  	
  	private List<BulkEntity> prepareRelatedBulkEntites(CsvReader reader,EntityType primaryEntityType,Attribute relatedAtt1,String modUser,List<EntityType> relatedEntityTypes) throws DataException{
  		 List <Attribute> relAttList= null;
  		List <BulkEntity> bulkEntities = new ArrayList<BulkEntity>();
  		try{
  			if(relatedEntityTypes != null && relatedEntityTypes.size()>0){
  				for(EntityType relentityType:relatedEntityTypes){
  					if(relentityType.getId() == relatedAtt1.getRelatedEntityType().getChildEntityTypeId()){
  						relAttList = relentityType.getAttributes(); 
  					// preparing BulkEntites for related EntityType attributes 
  				    	 for(Attribute att1 : relAttList){
  				    			BulkEntity bulkEntity =  prepareBulkEntity(reader, relentityType.getName()+"."+att1.getName(),att1, relentityType, modUser);
  				    		// Avoid null bulk objects
  			    				 if(bulkEntity != null)
  			    					 bulkEntities.add(bulkEntity);
         	 	         }
  				    	break; 
  					}
  				}
  				BulkEntity relatedBulkEntity = constructBulkEntity(reader,relatedAtt1,primaryEntityType,modUser);
  				bulkEntities.add(relatedBulkEntity);
  			}
  		}catch(Exception e){
  			logger.error("prepareRelatedBulkEntites:"+e.getMessage(),e);
			throw new DataException("prepareRelatedBulkEntites:- Unable to prepareRelatedBulkEntites :-");
  		}
  		return bulkEntities;
  	}
  	private BulkEntity constructBulkEntity(CsvReader reader,Attribute att1,EntityType entityType,String modUser){
  		
  		BulkEntity bulkEntity = new BulkEntity(); 	                        	
        bulkEntity.setMod_user(modUser);
        bulkEntity.setCounter(reader.getCurrentRecord());
        bulkEntity.setAttribute(att1);
    	bulkEntity.setEntityTypeId(entityType.getId());
    	return bulkEntity;
  	}

    private BulkEntity addBulkEntityValue(BulkEntity bulkEntity,Attribute att1,String value)throws DataException {
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");   
    	try {
    		if(bulkEntity != null){
    			if(att1.getDataTypeName().equals(DataType.VARCHAR.toString())){
                	bulkEntity.setValueVarchar(new String(value));
                    logger.debug("setBulkValue:-set value into ValueVarchar where datatypeID:-"+att1.getDataTypeId());
                }
        		else if(att1.getDataTypeName().equals(DataType.LONG.toString())){
                	bulkEntity.setValueLong(new Long(value));
                    logger.debug("setBulkValue:-set value into ValueLong where datatypeID:-"+att1.getDataTypeId());
                }    
        		else if(att1.getDataTypeName().equals(DataType.DATE.toString())){
                	try {
                		bulkEntity.setValueDate(df.parse(value));
                        logger.debug("setBulkValue:-set value into ValueDate where datatypeID:-"+att1.getDataTypeId());
                    } catch (ParseException e) {
                    	logger.debug("setBulkValue:-Exception while parsing date:-",e);
                    }
                }      
        		else if(att1.getDataTypeName().equals(DataType.TEXT.toString())){
                	bulkEntity.setValueText(new String(value));
                    logger.debug("setBulkValue:-set value into ValueText where datatypeID:-"+att1.getDataTypeId());
                }   
    		}
  		} catch (Exception e) {
    		logger.error("setBulkValue:"+e.getMessage(),e);
    		throw new DataException("setBulkValue:- Unable to setBulkValue :-");
    	}
    	return bulkEntity;
    }
	
    private boolean assignPermissionOnImportedEntites(List <ImportEntity> allEntities ) throws DataException{
	  	try {
	  			permissionManager.createPermissionsOnNewEntities(allEntities);
	   			
		} catch (DataException e) {
			logger.error("assignPermissionOnImportedEntites:"+e.getMessage(),e);
			throw new DataException("assignPermissionOnImportedEntites:- Unable to assignPermissionOnImportedEntites :-");
		}
		return true;
	}
  	
	@Override
	public List<ImportEntitiesFailedRow> fetchImportFailed(String importID) throws DataException {
		try {
			return entityDAO.fetchImportFailed(importID);
		} catch (DataException e) {
			logger.error("fetchImportResults:Unable to fetchImportResults"+e.getMessage());
        	throw new DataException("fetchImportResults:Unable to fetchImportResults"+e.getMessage());
		}
	}
	
	@Override
	public Import fetchImportResults(String importID) throws DataException	{		
	   	try {
			return entityDAO.fetchImportResults(importID);
		} catch (DataException e) {
			logger.error("fetchImportResults:Unable to fetchImportResults"+e.getMessage());
	       	throw new DataException("fetchImportResults:Unable to fetchImportResults"+e.getMessage());
		}
	}

}