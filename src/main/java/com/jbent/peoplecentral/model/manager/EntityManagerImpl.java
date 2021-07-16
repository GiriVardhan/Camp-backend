/**
 *
 */
package com.jbent.peoplecentral.model.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.jbent.peoplecentral.exception.AttributeValueNotValidException;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.dao.AdminDAO;
import com.jbent.peoplecentral.model.dao.EntityDAO;
import com.jbent.peoplecentral.model.dao.EntityTypeDAO;
import com.jbent.peoplecentral.model.pojo.Attribute;
import com.jbent.peoplecentral.model.pojo.AttributeFileStorage;
import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;
import com.jbent.peoplecentral.model.pojo.BoxEntity;
import com.jbent.peoplecentral.model.pojo.CompletePermissions;
import com.jbent.peoplecentral.model.pojo.Entity;
import com.jbent.peoplecentral.model.pojo.EntityStatus;
import com.jbent.peoplecentral.model.pojo.EntityType;
import com.jbent.peoplecentral.model.pojo.Attribute.Attributes;
import com.jbent.peoplecentral.model.pojo.Attribute.DataType;
import com.jbent.peoplecentral.model.pojo.Attribute.FieldType;
import com.jbent.peoplecentral.model.pojo.EntityType.EntityTypes;
import com.jbent.peoplecentral.permission.PermissionManager;
import com.jbent.peoplecentral.util.FileUploadHelperUtil;
import com.jbent.peoplecentral.web.SessionContext;


/**
 * @author RaviT
 *
 */
@SuppressWarnings("unused")
public class EntityManagerImpl extends ApplicationObjectSupport implements EntityManager, InitializingBean {
	
	
    private EntityDAO entityDAO;    

    
    @Autowired
    private PermissionManager permissionManager;
    @Autowired
    private ImportManager importManager;
	
    @Autowired
    private EntityTypeManager entityTypeManager;
    @Autowired
    private EntityTypeDAO entityTypeDAO;
    @Autowired
    private AdminDAO adminDAO;
    @Autowired
	private MessageSource messageSource;
    @Autowired
	private FileManager fileManager;	
	@Autowired
	private FileUploadHelperUtil fileUploadHelpUtil;
    @Autowired
    public void setEntityDAO(EntityDAO entityDAO) {
    	this.entityDAO = entityDAO;
    }
    

    
    @Override
    public void afterPropertiesSet() throws Exception {
    	Assert.notNull(entityDAO, "entityDAO is null");
        Assert.notNull(entityTypeDAO, "entityTypeDAO is null");
        Assert.notNull(permissionManager, "permissionManager is null");
        Assert.notNull(entityTypeManager, "entityTypeManager is null");
      
    }
 
     
    @Override
    public Long quickSave(Entity entity,List<AttributeValueStorage> attValuelist) throws DataException  {
    	Long entityId;
		try {
			entityId = entityDAO.quickSave(entity,attValuelist);
		} catch (DataException e) {
			logger.error("quickSave:- Unable to save entity :-",e);
			throw new DataException("quickSave:- Unable to save entity :-",e);
		}
        return entityId;	 
    }  
    
    @Override
    public EntityStatus avsSave(Entity entity,AttributeValueStorage avs) throws AttributeValueNotValidException, DataException {    
    	EntityStatus  entStatus = null;
    	
    	if(avs != null){    		
    		try {  
        		if(avs.getEntityTypeId() == EntityTypes.PEOPLE.getValue() && avs.getId() == Attributes.PASSWORD.getValue()){
               		String password = permissionManager.encodePassword(avs.getValueVarchar());
               		avs.setValueVarchar(password);
               	}
        		if(avs.getValue_Long() != null)
        			avs.setValueLong(Long.valueOf(filterValue_Long(avs.getValue_Long().trim())));
        		
        		entStatus = entityDAO.avsSave(avs);
        		
        		if(entStatus.getEntityId() > 0){
        			logger.debug("avsSave:- Attribute Value saved success for the attribute :-"+avs.getName());
        			entity = entityDAO.loadEntity(entStatus.getEntityId());
        			MutableAcl acl = permissionManager.fetchAcl(entity);
        			if(acl != null){
        				// Granting the edit permissions to all roles the user creating the entity has 
        		        // that are also marked assign_at_new is true on the role entity
        				permissionManager.createPermissionOnEntity(entity,entity.getEntityType());
        				// create custom user if the entityType is People/Person 
        		        if(entity.getEntityTypeId() == EntityTypes.PEOPLE.getValue()){
        		        	if(entStatus.isEntityValid()){
        		        		String userName= null,password=null;
            			        List<AttributeValueStorage> personValues = entity.getAttributeValueStorage();
            			        for(AttributeValueStorage personValue:personValues){
            			        	if(personValue.getId() == Attributes.USERNAME.getValue())
            			           		userName = personValue.getValueVarchar();
            			           	if(personValue.getId() == Attributes.PASSWORD.getValue())
            			           		password = personValue.getValueVarchar();
            			       }
            			       permissionManager.createCustomUser(userName, password, true, true, true, true, null);
            				}
        			    }
        			}
        		}
           		}catch (DataException e) {
    				logger.error("avsSave:- Unable to save entity :-",e);
    				throw new DataException("avsSave:- Unable to save entity :-",e);
    			}
       	}else{
    		logger.error("avsSave:- Entity failed to save no value in the avs :-"+avs);
    		throw new DataException("avsSave:-Entity failed to save no value in the avs:-" + avs);
    	} 
        return entStatus;	 
    }  
    
    @Override
    public List<Entity> load() throws DataException {
    	List<Entity> attFilterEntities = null;
    	List<Entity> entities;
		try {
			entities = entityDAO.loadEntities();
			if(entities != null && entities.size() > 0){
				attFilterEntities = new ArrayList<Entity>();
	    		for(Entity entity:entities){
	    			// filter the attributes users can't see
	    			List<AttributeValueStorage> attStorage = entityTypeManager.filterAttributesValues(entity.getAttributeValueStorage());
	        		if(attStorage != null && attStorage.size() > 0){
	        			entity.setAttributeValueStorage(attStorage);
	            		attFilterEntities.add(entity);
	        		}	
	        	}
	    	}
		} catch (DataException e) {
			logger.error("load:- Unable to load entity :-"+e);
			throw new DataException("load:- Unable to load entity :-"+e);
		}
    	return attFilterEntities;
    }

    @Override
    public List<Entity> entitySearchSimple(String searchTerm,long entityTypeId,String startHighlight,String endHighlight) throws DataException {
    	List<Entity> searchResult;
		try {
			searchResult = entityDAO.entitySearchSimple(searchTerm,entityTypeId,startHighlight,endHighlight);
		} catch (DataException e) {
			logger.error("entitySearchSimple:- Unable to searchSimple Entity :-"+e);
			throw new DataException("entitySearchSimple:- Unable to searchSimple Entity :-"+e);
		}
    	if(searchResult == null || searchResult.size() == 0){
    		logger.debug("entitySearchSimple:- entitySearchSimple returns searchResult:-"+searchResult);
    		return null;
    	}
    	return searchResult;
    }
      
    private String[] buildSearchArray(String sterm) {
    	String arr [] = null;

    	String pattern1 = "$";
    	String pattern2 = "[";
    	String SearchTerms = sterm.replace("\"",  pattern1);
        sterm = SearchTerms.replaceAll(" +", " ").trim();
        boolean isConsiderSpace = true;
        ArrayList<String> atname = new ArrayList<String>();
        ArrayList<String> operats = new ArrayList<String>();
        operats.add("and ");
        operats.add(" or ");
        operats.add(" not ");
        operats.add(" AND ");
        operats.add(" OR ");
        operats.add(" NOT ");
        
        if(sterm.contains(pattern2)){
        	if(!sterm.isEmpty()){
        		if(sterm.indexOf(" ")> -1){
        		String space = sterm.substring(0,sterm.indexOf(" "));
        			if(space.contains("[")){
        				isConsiderSpace = false;
        			}else{
        				atname.add(space);
        				sterm =sterm.replace(space, "");
        			}
        		}	
        	}
        	if(!sterm.isEmpty()){
        		if(sterm.startsWith("[")){
        			atname.add(sterm);
        			sterm= sterm.replace(sterm, "");
        		}
        	}
        	if(!sterm.isEmpty()){
        		for(int i=0; i<=sterm.length(); i++){
        			if(sterm.indexOf("]")> -1){
        				String ar = sterm.substring(0,sterm.indexOf("]")+1);
        				atname.add(ar);
        				sterm =sterm.replace(ar, "");
        				if((!sterm.contains(pattern2))&&(!sterm.isEmpty())){
        					atname.add(sterm);
        					sterm =sterm.replace(sterm, "");
        				}
        			}	
        			for(String oper:operats){
        				if((sterm.contains(oper))&&(!sterm.isEmpty())){
        					if(sterm.indexOf("[") > -1){
        						String op = sterm.substring(0, sterm.indexOf("["));
        						if(op.contains(oper)){
        							atname.add(oper);
        							sterm =sterm.replaceFirst(oper, "");
        						}
        					}		
        				}
        			}
        		}
        	}
        	arr = (String []) atname.toArray (new String [0]);
        }else if((sterm.contains("type"))&& (!sterm.contains("["))&&(!sterm.isEmpty()) ){
        	String space;
        	if(sterm.indexOf(" ")> -1){
        		 space = sterm.substring(0, sterm.indexOf(" "));
        		 atname.add(space);
        		 sterm = sterm.replace(space,"");
        	}
        	atname.add(sterm);
        	sterm = sterm.replace(sterm, "");
        	arr = (String []) atname.toArray (new String [0]);
        }else{
        	arr = new String[1];
        	arr[0]= sterm;
        }       
        return arr ;
    }
    
    @Override
    public List<EntityType> searchResultsEntityType(String searchTerm) throws DataException {
    	List<EntityType> ets = null;
    	String pattern1 = "$";
    	String SearchTerms = searchTerm.replace("\"",  pattern1);
    	String sterm = SearchTerms.replaceAll(" +", " ").trim();
    	String [] arr = buildSearchArray(searchTerm);
    	try{
    		if(arr == null){
    			return null;
    		}else if(arr.length>0){
    			ets = entityDAO.entityTypesSearchResults(arr);   
    		}        
    		if(ets == null || ets.size() == 0){
    			logger.debug("searchResultsEntityType:- searchResultsEntityType returns entity types:-"+ets);
    			return null;
    		}
    	}catch(Exception e){
    		logger.error("entitySearch:- Unable to search entity :-",e);
    		throw new DataException("entitySearch:- Unable to search entity :-",e);
    	}
    	return ets ;
    }
    
   @Override
   public List<Entity> entitySearch(String searchTerm,long entityTypeId,String startHighlight,String endHighlight,long limit, long offsetValue) throws DataException {
	   List<Entity> entites = null;
	   String pattern1 = "$";
       String SearchTerms = searchTerm.replace("\"",  pattern1);
       String sterm = SearchTerms.replaceAll(" +", " ").trim();
       boolean isComplexQuery = isComplexQuery(searchTerm);
	   String [] arr = buildSearchArray(searchTerm);
	   try{
		   if(arr == null){
			   return null;
		   }else if(isComplexQuery){
			   entites = entityDAO.entitySearchComplex(arr,entityTypeId,startHighlight,endHighlight,limit,offsetValue);   
		   }else{
			   entites = entityDAO.entitySearch(sterm,entityTypeId,startHighlight,endHighlight,limit,offsetValue);
		   }        
	       if(entites == null || entites.size() == 0){
	          	logger.debug("entitySearch:- entitySearch returns entites:-"+entites);
	           	return null;
	       }
        }catch(Exception e){
			logger.error("entitySearch:- Unable to search entity :-",e);
			throw new DataException("entitySearch:- Unable to search entity :-",e);
        }
        return entites ;
    }
   
   @Override
   public List<Entity> searchRelations(long entityId, boolean isFrom) throws DataException {
	   String searchTerm = "";
	   if(isFrom)
		   searchTerm = "type:Relation From:["+ entityId +"] or Name:[*] or To:[*]";
	   else
		   searchTerm = "type:Relation From:[*] or Name:[*] or To:["+ entityId +"]";
	   List<Entity> entites = null;
	   String pattern1 = "$";
       String SearchTerms = searchTerm.replace("\"",  pattern1);
       String sterm = SearchTerms.replaceAll(" +", " ").trim();
       boolean isComplexQuery = isComplexQuery(searchTerm);
	   String [] arr = buildSearchArray(searchTerm);
	   try{
		   if(arr == null){
			   return null;
		   }else if(isComplexQuery){
			   entites = entityDAO.entitySearchComplex(arr,0,"<b>","</b>",10,0);   
		   }else{
			   entites = entityDAO.entitySearch(sterm,0,"<b>","</b>",10,0);
		   }        
	       if(entites == null || entites.size() == 0){
	          	logger.debug("entitySearch:- entitySearch returns entites:-"+entites);
	           	return null;
	       }
        }catch(Exception e){
			logger.error("entitySearch:- Unable to search entity :-",e);
			throw new DataException("entitySearch:- Unable to search entity :-",e);
        }
        return entites ;
    }
   
   private boolean isComplexQuery(String searchTerm){
	   String pattern1 = "[";
	   String pattern2 = "]";
	   String pattern3 = ":";
	   	if(searchTerm.contains(pattern1) || searchTerm.contains(pattern2) || searchTerm.contains(pattern3))
	   		return true;
	   return false;
   }
   @Override
    public String importEntity(EntityType entType,File uploadedEntities,String uId,Locale locale,MappingJackson2JsonView view)
    	throws DataException {  
	    CsvReader reader;
		EntityType entityType;
		BufferedReader buf,buf1 = null;
        boolean attributeMatched=false;
        List <Attribute> attList=new ArrayList<Attribute>();         
        long headerCount = 0,attCount=0,totalCsvLines=0;
        List<EntityType> relatedEntityTypes = new ArrayList<EntityType>();
        entityType = entType;
	    long entityTypeId=entityType.getId();
	    view.getAttributesMap().put("entityTypeId",entityTypeId);
        try{
        	//create a buffer to parse the file once to find out the total number of lines  
            buf =  new BufferedReader(new FileReader(uploadedEntities));
            //create a new one to actually read the values
            buf1 =  new BufferedReader(new FileReader(uploadedEntities));
            if(buf == null || buf1 == null){
        	    logger.error("importEntity:-BufferReader is Null"+buf);
        	    throw new DataException("EntityManagerImpl.importEntity:- BufferReader is Null"+buf);
            }
            totalCsvLines = linesInCsvFile(buf);            
            view.getAttributesMap().put("totalRecords",totalCsvLines);
            reader = new CsvReader(buf1);
            reader.readHeaders();
            String []headerColumns = reader.getHeaders();
            attList = entityType.getAttributes();
            headerCount = reader.getHeaderCount();
            if(attList !=null && attList.size()>0){ 
                for(Attribute att: attList){
        	 	     if(att.getFieldTypeName().equals(FieldType.EntityType.toString())){
        	 	    	 // Load related EntityType first if we have
        	 	    	 EntityType relatedEntityType = loadRelatedEntityType(att.getRelatedEntityType().getChildEntityTypeId());
        	 	    	 List<Attribute> relAttributes = relatedEntityType.getAttributes();
        	 	    	 for(Attribute relAtt:relAttributes){
        	 	    		isAttributeMatched(relatedEntityType.getName()+"."+relAtt.getName(),headerColumns,view,locale);
        	 	    		
        	 	    	 }
        	 	    	 int relAttCount = relAttributes.size();
        	 	    	 relatedEntityTypes.add(relatedEntityType);
        	 	    	 attCount = attCount+relAttCount;
        	 	     }else{
        	 	    	 attributeMatched = isAttributeMatched(att.getName(),headerColumns,view,locale);
        	 	    	 if(attributeMatched){
        	 	    		 attCount++;
        	 	    	 }
        	 	     }
                }	
                if(headerCount != attCount){
                	view.getAttributesMap().put("msg", messageSource.getMessage("error.entity.import.attributes.conunt.not.match", null, locale));
                	logger.error("importEntity:- Sorry! Attribute Count doesn't match while Parsing your csv file....Attributes Count:"+headerCount+" But and Expected:"+attCount);
                	throw new DataException("EntityManagerImpl.importEntity:- Sorry! Attribute Count doesn't match while Parsing your csv file....Attributes Count:"+headerCount+" But and Expected:"+attCount);
                }
                //	Checks lines in Csv  if (lines > 1) then it creates a new thread. 
                if(totalCsvLines >= 1){
                	//	Passing Authentication Object to Child Thread.
                	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                	SecurityContextHolder.setStrategyName("MODE_INHERITABLETHREADLOCAL");
                	SecurityContextHolder.getContext().setAuthentication(auth);              	
                	
                	ImportEntityThread entityThread = new ImportEntityThread(reader,entityType,uId,relatedEntityTypes);
                	Thread thread = new Thread(entityThread);
                	thread.start();
                	//	I need to tell the main thread to wait and continue to the next step only after new thread completed.Here key point is to use thread.join() method
                	//	thread.join();
                 	
                }else{
                	view.getAttributesMap().put("msg", messageSource.getMessage("error.entity.import.file.no.entites", null, locale));
               	 	logger.error("importEntity:-File should contain at least one entity to parse");
               	 	throw new DataException("EntityManagerImpl.importEntity:- File should contain at least one entity to parse");
                }
            }else{
            	view.getAttributesMap().put("msg", messageSource.getMessage("error.entity.import.attributes.not.found", null, locale));
            	logger.error("importEntity:- couldn't import the file No attributes found:-"+attList);
            	throw new DataException("EntityManagerImpl.importEntity:- couldn't import the file No attributes found");
            }            
        } catch (IOException e) {
        	logger.error("importEntity:- Unable to Parse the Imported File"+e.getMessage(),e);
        	throw new DataException("EntityManagerImpl.importEntity:Unable to Parse the Imported File"+e);
        }catch (Exception e) {
        	logger.error(e.getMessage(),e);
        	throw new DataException("EntityManagerImpl.importEntity:Unable to Parse the Imported File"+e);
        }
        return uId;
    }
   
    class ImportEntityThread implements Runnable {   
    	CsvReader reader;
		EntityType entityType;
		String uId;
		List<EntityType> relatedEntityTypes;
		public ImportEntityThread(CsvReader reader,EntityType entityType,String uId,List<EntityType> relatedEntityTypes){
			this.reader = reader;
			this.entityType = entityType;
			this.uId = uId;
			this.relatedEntityTypes = relatedEntityTypes;
		}
    	// This run method invoking only with in the Thread flow  
    	@Override
    	public void run() {
    	   // Do work...
 				try {
 					// Starts the Importing 					
 					startImport(reader,entityType,uId,relatedEntityTypes);
					return;
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (DataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
    	}
   }
   
    public void startImport(CsvReader reader,EntityType entityType,String uId,List<EntityType> relatedEntityTypes) throws InterruptedException, DataException{    	    
    	importManager.saveBulkEntities(reader,entityType,uId,relatedEntityTypes);    	
    }
     
   
    
    private boolean isAttributeMatched(String attributeName, String[] headers,MappingJackson2JsonView view,Locale locale) throws DataException{
        if(headers.length>0){
        	for (String header: headers){
        		// check header list contains the attribute
                if(header.trim().equalsIgnoreCase(attributeName.trim()))
                  return true;
            }
        	view.getAttributesMap().put("msg", messageSource.getMessage("error.entity.import.attributes.not.match", null, locale));
            logger.error("importEntity:-Sorry! Attibute Name doesn't match while Parsing your csv file for the attribute :"+attributeName );
            throw new DataException("importEntity:-Sorry! Attibute Name doesn't match while Parsing your csv file for the attribute :"+attributeName );
      
        }
    	 return false;
    }
    
    private long linesInCsvFile(BufferedReader buf) throws DataException{
    	long Csvlines = 0;
    	CsvReader csvReader;
    	try {
    		csvReader = new CsvReader(buf);
    		csvReader.readHeaders();
			while (csvReader.readRecord()){
				if(csvReader.get(0)!=null && !csvReader.get(0).isEmpty())
					Csvlines = Csvlines+1;
			}
			csvReader.close();
		} catch (IOException e) {
			logger.error("linesInCsvFile:Exception in getLinesInCsvFile method:-",e);
			throw new DataException ("linesInCsvFile:Exception in getLinesInCsvFile method:-"+e);
		}
		return Csvlines;
    }
    
    @Override
    public File getCSVFileInputStreamForEntity(long entityId) throws DataException, IOException{
    	
		Entity entity = loadEntity(entityId);
		String entityTitle = entity.getTitle();
		File entityCSVFile = new File(entityTitle);
		if(entityCSVFile.exists())
			entityCSVFile.delete();
		entityCSVFile.createNewFile();
		
	    CsvWriter csvWriter = new CsvWriter(new FileWriter(entityCSVFile, true), ',');
	    csvWriter.setDelimiter(',');
	    for(AttributeValueStorage avs : entity.getAttributeValueStorage()){
	    	csvWriter.write(avs.getName());
	    }
	    csvWriter.endRecord();
	    for(AttributeValueStorage avs : entity.getAttributeValueStorage()){
	    	if(avs.getDataTypeName().equals("date")){
	    		csvWriter.write((new SimpleDateFormat("dd-MMM-yyyy").format(entity.getValueObject(avs.getName()))));
	    	}else{
	    		csvWriter.write(entity.getValueString(avs.getName()));
	    	}
	    }
	    csvWriter.endRecord();
	    csvWriter.close();
	    return entityCSVFile;
    }
    
    @Override
    public void exportEntities(HttpServletResponse response,List<Entity> entities) throws DataException, IOException{		
	    CsvWriter csvWriter = null;
	    String filename = ""; 
	    String[] sourceFiles = null;
	    FileInputStream fin = null;
	    FileInputStream fin1 = null;	  
	    int fileCount = 0;
	    int entitypeCount = 0;
	    long entypeId=0;
	    SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
	    try {			
	    	entitypeCount = getEntityTypeCount(entities);
			sourceFiles = new String[entitypeCount];
			if(entities != null && entities.size() > 0){
				for(Entity ent:entities){				
					if(entypeId!=ent.getEntityTypeId()){
						if(csvWriter != null){
							csvWriter.flush();
							csvWriter.close();
						}
						filename = ent.getEntityType().getName()+".csv";
						csvWriter = new CsvWriter(filename);
						csvWriter.setDelimiter(',');					
						sourceFiles[fileCount]=ent.getEntityType().getName()+".csv";
						fileCount++;					
						entypeId = ent.getEntityTypeId();
						csvWriter = getHeader(ent.getAttributeValueStorage(),csvWriter);
					}
	        		for(AttributeValueStorage avs:ent.getAttributeValueStorage()){
	        			       			
	        			if(avs.getDataTypeName().equals(DataType.VARCHAR.toString())){
	        				csvWriter.write(avs.getValueVarchar().replaceAll("\\<[^>]*>","").trim());
	        		    }
	        			if(avs.getDataTypeName().equals(DataType.LONG.toString())){
	        				csvWriter.write(avs.getValueLong().toString());
	            		}
	        			if(avs.getDataTypeName().equals(DataType.DATE.toString())){        				
	        				csvWriter.write(dateformat.format(avs.getValueDate()));
	            		}
	        			if(avs.getDataTypeName().equals(DataType.TEXT.toString())){
	        				csvWriter.write(avs.getValueText());
	            		} 
	        		}
	        		csvWriter.endRecord();
				}	
			}else{
				logger.error("exportEntities:- Passed parameter entities is empty :-"+entities);
			}
		} catch (IOException e) {			
			logger.error("Error loading file : " + e.getMessage(),e);
		} finally {			
			csvWriter.flush();
			csvWriter.close();
			if(entitypeCount == 1){
				response.setContentType("application/octet-stream");
		        response.setHeader("Content-Disposition","attachment;filename="+filename);					
				fin1 = new FileInputStream(filename);
				OutputStream responseStream1 = response.getOutputStream();
				int len =0;
				while((len = fin1.read()) > 0){
					responseStream1.write(len);
				}
				fin1.close();
				responseStream1.close();
				
			}
			else if(entitypeCount>1){
				response.setContentType("application/zip");
				 response.setHeader("Content-Disposition","attachment;filename=Export");
				OutputStream responseStream = response.getOutputStream();  
			    ZipOutputStream zout = new ZipOutputStream(responseStream);
				for(int i=0; i < sourceFiles.length; i++) {
					fin = new FileInputStream(sourceFiles[i]);
					zout.putNextEntry(new ZipEntry(sourceFiles[i]));					
					int length; 
					while((length = fin.read()) > 0){
						zout.write(length);
					}
					zout.closeEntry();
					fin.close();
				 }
				 zout.close();				
			}
		}
	}
    
    private CsvWriter getHeader(List<AttributeValueStorage> avs,CsvWriter csvWriter) throws DataException, IOException{			
    	
    	try {
	    	if(avs != null && avs.size() > 0){
	    		for(AttributeValueStorage avs1:avs){      			
	      			csvWriter.write(avs1.getName());      			
	      		}
	      		csvWriter.endRecord();
	    	}else{
	    		logger.error("getHeader:- input parameter avs is empty :-"+avs);
	    	}
    	} catch (IOException e) {			
			logger.error("Error loading file writer : " + e.getMessage(),e);
			throw new DataException ("linesInCsvFile:Exception in getLinesInCsvFile method:-"+e);
		}		
		return csvWriter;
	}
	
	private int getEntityTypeCount(List<Entity> entities) throws DataException{
		int count = 0;
		long eTypeId = 0;
		if(entities != null && entities.size() >0 ){
			for(Entity ent:entities){
				if(eTypeId!=ent.getEntityTypeId()){
					eTypeId=ent.getEntityTypeId();
					count++;
				}				 
			}
		}else{
			logger.error("getEntityTypeCount:- Passed parameter entities is empty :-"+entities);
		}
		return count;
	}
  
    @Override
    public Entity loadEntity(long entityId) throws DataException  {        
        Entity entity = null;
        List<AttributeFileStorage> afsList=null;
		List<String> savedLt = new ArrayList<String>();
		boolean saved = false;
        String serverPath = fileUploadHelpUtil.getServerPath();
		try {
			entity = entityDAO.loadEntity(entityId);
			if(entity != null){
				EntityType entType = entityTypeDAO.load(entity.getEntityTypeId());
				entity.setEntityType(entType);
	        	List<AttributeValueStorage> avs = entity.getAttributeValueStorage();
	        	List<AttributeFileStorage> fvs = entity.getAttributeFileStorage();
	        	List<AttributeFileStorage> finalfvs = new ArrayList<AttributeFileStorage>();
	        	if(avs != null){
	        		// filter the Attributes User Can't See...
	            	List<AttributeValueStorage> filterAtt = entityTypeManager.filterAttributesValues(avs);
	            	if(filterAtt !=null && filterAtt.size()>0){
	            		entity.setAttributeValueStorage(filterAtt);
	            		entity.setTitile(WordUtils.capitalize(entityTitle(entity)));
	            	}
	            }
	        	entity.setAttributeFileStorage(null);
	        	if(fvs != null && fvs.size()>0){
	        		List<AttributeFileStorage> filterFileAtt = entityTypeManager.filterFileAttributesValues(fvs);
	        		for(AttributeFileStorage fstorage:filterFileAtt){
	        			for (int i = 0; i < savedLt.size(); i++) {
							if (savedLt.get(i).equalsIgnoreCase(fstorage.getFileName())) {
								saved = true;
							}else{
								saved = false;
							}
						}
	        			String fileSavedPath = fileManager.getSavedImagePath(fstorage);
		        			File f = new File(serverPath+fileSavedPath);
		        			if(f.exists() && saved == false){
		        				fstorage.setImagePath(fileSavedPath);  
		        				saved = true;
								savedLt.add( fstorage.getFileName());
		        				finalfvs.add(fstorage);
		        			}
	        		}
	        		if(finalfvs != null && finalfvs.size()>0){
	        		entity.setAttributeFileStorage(finalfvs);
	        		}
	        	}
	        }else{
				logger.error("loadEntity:- your requested entity is not found:"+entityId);
				throw new DataException("loadEntity:- your requested entity is not found:"+entityId);
			}
		} catch (DataException e) {
	    	logger.error("loadEntity:Unable to loadEntity:"+e.getMessage(),e);
        	throw new DataException("loadEntity:Unable to loadEntity of Id:"+entityId);
		}
        return entity;
    }
    
    @Override
    public Date loadEntityModDate(long entityId) throws DataException  {
    	return entityDAO.loadEntityModDate(entityId);
    }
    
    public boolean modifyPermissionsOnEntity(Entity entity,String role,String permission) throws DataException{
    	boolean status = false;
    	try {
    		status = permissionManager.permissionHandler(entity, role, permission);
		} catch (DataException e) {
			logger.error("modifyPermissionsOnEntity:Unable to modifyPermissionsOnEntity- entity:"+entity.getTitle()+" ,role:"+role+", permission"+permission,e);
        	throw new DataException("modifyPermissionsOnEntity:Unable to modifyPermissionsOnEntity- entity:"+entity.getTitle()+" ,role:"+role+", permission"+permission,e);
		}
		return status;
    }
    
   	@Override
	public List<BoxEntity> saveEntitiesToBox(Long[] entityIds) throws DataException {		
   		List<BoxEntity> boxEntities;
		try {
			boxEntities = entityDAO.saveEntitiesToBox(entityIds);
		} catch (DataException e) {
			logger.error("saveEntitiesToBox:Unable to saveEntitiesToBox"+e);
        	throw new DataException("saveEntitiesToBox:Unable to saveEntitiesToBox"+e);
		}
   		if(boxEntities == null || boxEntities.size() == 0){
   			logger.debug("saveEntitiesToBox:- saveEntitiesToBox return boxEntities is empty :-"+boxEntities);
   			return null;
   		}
   		return boxEntities;
	}
	@Override
	public List<Entity> loadBoxEntities(String order, long limit, long offset) throws DataException  {		
		List<Entity> boxEntities;
		try {
			boxEntities = entityDAO.loadBoxEntities(order,limit,offset);
		} catch (DataException e) {
			logger.error("loadBoxEntities:Unable to loadBoxEntities"+e);
        	throw new DataException("loadBoxEntities:Unable to loadBoxEntities"+e);
		}
		if(boxEntities == null || boxEntities.size() ==0){
			logger.debug("loadBoxEntities:- loadBoxEntities return boxEntities is empty :-"+boxEntities);
   			return null;
		}
		return boxEntities;
	}
	
	@Override
	public List<Entity> loadAllBoxEntities() throws DataException  {		
		List<Entity> allBoxEntities;
		try {
			allBoxEntities = entityDAO.loadAllBoxEntities();
		} catch (DataException e) {
			logger.error("loadAllBoxEntities:Unable to loadAllBoxEntities"+e);
        	throw new DataException("loadAllBoxEntities:Unable to loadAllBoxEntities"+e);
		}
		if(allBoxEntities == null || allBoxEntities.size() == 0){
			logger.debug("loadBoxEntities:- loadAllBoxEntities return boxEntities is empty :-"+allBoxEntities);
   			return null;
		}
		return allBoxEntities;
	}
	
	@Override
	public Long removeBoxEntity(long entityId) throws DataException {		
		try {
			return entityDAO.removeBoxEntity(entityId);
		} catch (DataException e) {
			logger.error("removeBoxEntity:Unable to removeBoxEntity"+e);
        	throw new DataException("removeBoxEntity:Unable to removeBoxEntity"+e);
		}
	}

	@Override
	public Long getCountBoxEntity() throws DataException {		
		try {
			return entityDAO.getCountBoxEntity();
		} catch (DataException e) {
			logger.error("getCountBoxEntity:Unable to getCountBoxEntity"+e);
        	throw new DataException("getCountBoxEntity:Unable to getCountBoxEntity"+e);
		}
	}
	
	
	
	
	public List<CompletePermissions> retrieveCombinedRolesForEntity(Entity entity) throws DataException {
		MutableAcl acl;
		
		List<CompletePermissions> combinedEntityRoles = null,combinedParentAndEntityRoles = new ArrayList<CompletePermissions>();
		List<CompletePermissions> parentPermissionRoles = null,entityPermissionRoles = null;
		try {
			acl = permissionManager.fetchAcl(entity);
			if(acl != null){
				
				if(acl.getParentAcl()!= null)
					parentPermissionRoles =  permissionManager.fetchPermissionRoles(acl.getParentAcl().getEntries(),entity.getEntityId(),"parent");
				
				if(parentPermissionRoles!=null && parentPermissionRoles.size()>0){
//					parentPermissions = new ArrayList<CompletePermissions>();
//					for(CompletePermissions parentPer:parentPermissionRoles){
//						if(parentPer.getPermission() > 0  )
//							parentPermissions.add(parentPer);
//					}
					// Sorts the specified list into ascending order, according to the natural ordering of its elements.
					Collections.sort(parentPermissionRoles);
					combinedParentAndEntityRoles.addAll(parentPermissionRoles);
				}else{
					logger.debug("retrieveEntityPermissions:- No Parents Permissions found for the acl :-"+acl);
				}
				
				if(acl.getEntries().size() > 0){
					entityPermissionRoles =  permissionManager.fetchPermissionRoles(acl.getEntries(),entity.getEntityId(),"entity");
					
				}else{
					logger.debug("retrieveEntityPermissions:- No Entity Permissions found for the acl :-"+acl);
				}
				combinedEntityRoles = permissionManager.combinedPermissionRolesAndNoPermissionRoles(entityPermissionRoles,entity.getEntityId(),"entity");
				if(combinedEntityRoles != null)
					combinedParentAndEntityRoles.addAll(combinedEntityRoles);
				
				
				
				
				
			}

		} catch (DataException e) {
			logger.error("retrieveEntityPermissions:Unable to retrieveEntityPermissions :"+e);
        	throw new DataException("retrieveEntityPermissions:Unable to retrieveEntityPermissions :"+e);
		}
		return combinedParentAndEntityRoles;
	}
	
	
	@Override
	public List<AttributeValueStorage> loadAvailableRoles(long entityId) throws DataException  {
		String userName ="";
		List<AttributeValueStorage> availableRoles = new ArrayList<AttributeValueStorage>();
		Entity entity = null;
		try {
			entity = entityDAO.loadEntity(entityId);
			if(entity != null){
				// if the entity type is people or person
				if(entity.getEntityTypeId() == EntityTypes.PEOPLE.getValue()){
					List<AttributeValueStorage> users = entity.getAttributeValueStorage();
					if(users != null && users.size() > 0){
						for(AttributeValueStorage user:users){
							if(user.getId() == Attributes.USERNAME.getValue())
								userName = user.getValueVarchar();
							 	break;
						 } 
					 }
				 }
				 if(userName != null && userName != ""){
					// load All the Roles from the Role EntityType
					List<AttributeValueStorage> allRoles =  entityDAO.loadRoles();
					if(allRoles != null && allRoles.size() > 0 ){
						for(AttributeValueStorage avs:allRoles){
							// filtered the Roles already assigned to this User to get available Roles of this User
							if(!permissionManager.isRoleAssigned(userName,avs.getValueVarchar())){
								avs.setValueVarchar(sanitizeRole(avs.getValueVarchar()));
								availableRoles.add(avs);
							}
						}
					}
				 }
			}

		} catch (DataException e) {
			logger.error("loadAvailableRoles:Unable to loadAvailableRoles :"+e);
        	throw new DataException("loadAvailableRoles:Unable to loadAvailableRoles :"+e);
		}
		if(availableRoles == null || availableRoles.size() == 0){
			logger.debug("loadAvailableRoles:- availableRoles list is  empty:-"+entity+" of entityID:-"+availableRoles);
			return null;
		}
		return availableRoles;
	}
		
	@Override
	public List<AttributeValueStorage> loadAssignedRoles(Entity entity) throws DataException
			 {
		List<AttributeValueStorage> assignedRoles = new ArrayList<AttributeValueStorage>(),
									allRoles= new ArrayList<AttributeValueStorage>();
		String userName =null;
		// if the entity type is people or person
		try{
			if(entity.getEntityTypeId() == EntityTypes.PEOPLE.getValue()){
				List<AttributeValueStorage> userAttValues = entity.getAttributeValueStorage();
					if(userAttValues != null){
						for(AttributeValueStorage userAttValue:userAttValues){
							if(userAttValue.getId() == Attributes.USERNAME.getValue())
								userName = userAttValue.getValueVarchar();
							 	break;
						 }
						 // load All the Roles from the Role EntityType
						 allRoles =  entityDAO.loadRoles();
						 ListIterator<AttributeValueStorage> it = allRoles.listIterator();
						 if(it.hasNext()){
							 for(AttributeValueStorage avs:allRoles){
								 //filter  Roles those NOT assigned to this User to get unassigned Roles 
								 if(permissionManager.isRoleAssigned(userName,avs.getValueVarchar())){
									 avs.setValueVarchar(sanitizeRole(avs.getValueVarchar()));
									 assignedRoles.add(avs);
								 }
							 }
						}else{
							logger.debug("loadAssignedRoles:- No roles found on the Role EntityType");
						}
					}else{
						logger.debug("loadAssignedRoles:- No Users found for this EntityType"+EntityTypes.PEOPLE);
					}
			}		 

		}catch(Exception e){
			logger.error("loadAssignedRoles:Unable to loadAssignedRoles :"+e);
        	throw new DataException("loadAssignedRoles:Unable to loadAssignedRoles :"+e);
	
		}
		if(assignedRoles == null || assignedRoles.size() == 0){
			logger.debug("loadAssignedRoles:- No Assigned Roles found to load"+assignedRoles);
			return null;
		}
		return assignedRoles;
	}

	@Override
	public List<AttributeValueStorage> loadAssignedUsers(Entity entity) throws DataException
			 {
		List<AttributeValueStorage> assignedUsers = new ArrayList<AttributeValueStorage>(),
									allUsers= new ArrayList<AttributeValueStorage>();
		String roleName =null;
		// if the entity type is people or person
		try{
			if(entity.getEntityTypeId() == EntityTypes.ROLE.getValue()){
				List<AttributeValueStorage> userAttValues = entity.getAttributeValueStorage();
					if(userAttValues != null){
						for(AttributeValueStorage userAttValue:userAttValues){
							if(userAttValue.getId() == Attributes.ROLENAME.getValue())
								roleName = userAttValue.getValueVarchar();
							 	break;
						 }
						 // load All the Users 
						allUsers =  adminDAO.loadUsers();
						 ListIterator<AttributeValueStorage> it = allUsers.listIterator();
						 if(it.hasNext()){
							 for(AttributeValueStorage avs:allUsers){
								 //filter the Users those NOT assigned to this ROLE  
								 if(permissionManager.isRoleAssigned(avs.getValueVarchar(),roleName)){
									 assignedUsers.add(avs);
								 }
							 }
						}else{
							logger.debug("loadAssignedUsers:- No Users found on the Role:"+roleName);
						}
					}else{
						logger.debug("loadAssignedUsers:- No Roles found for this EntityType"+EntityTypes.ROLE);
					}
			}		 

		}catch(Exception e){
			logger.error("loadAssignedRoles:Unable to loadAssignedRoles :"+e);
        	throw new DataException("loadAssignedRoles:Unable to loadAssignedRoles :"+e);
	
		}
		if(assignedUsers == null || assignedUsers.size() == 0){
			logger.debug("loadAssignedRoles:- No Assigned Roles found to load"+assignedUsers);
			return null;
		}
		return assignedUsers;
	}
	
	@Override
	public List<AttributeValueStorage> loadAvailableUsers(Entity entity) throws DataException
			 {
		List<AttributeValueStorage> availableUsers = new ArrayList<AttributeValueStorage>(),
									allUsers= new ArrayList<AttributeValueStorage>();
		String roleName =null;
		// if the entity type is people or person
		try{
			if(entity.getEntityTypeId() == EntityTypes.ROLE.getValue()){
				List<AttributeValueStorage> userAttValues = entity.getAttributeValueStorage();
					if(userAttValues != null){
						for(AttributeValueStorage userAttValue:userAttValues){
							if(userAttValue.getId() == Attributes.ROLENAME.getValue())
								roleName = userAttValue.getValueVarchar();
							 	break;
						 }
						 // load All the Users 
						allUsers =  adminDAO.loadUsers();
						 ListIterator<AttributeValueStorage> it = allUsers.listIterator();
						 if(it.hasNext()){
							 for(AttributeValueStorage avs:allUsers){
								 //Add the Users those NOT assigned to this ROLE  
								 if(!permissionManager.isRoleAssigned(avs.getValueVarchar(),roleName)){
									 availableUsers.add(avs);
								 }
							 }
						}else{
							logger.debug("loadavailableUsers:- No Users found on the Role:"+roleName);
						}
					}else{
						logger.debug("loadavailableUsers:- No Roles found for this EntityType"+EntityTypes.ROLE);
					}
			}		 

		}catch(Exception e){
			logger.error("loadavailableUsers:Unable to loadavailableUsers :"+e);
        	throw new DataException("loadavailableUsers:Unable to loadavailableUsers :"+e);
	
		}
		if(availableUsers == null || availableUsers.size() == 0){
			logger.debug("loadavailableUsers:- No availableUsers  found to load"+availableUsers);
			return null;
		}
		return availableUsers;
	}


	
//	private List<Entity> loadImportedEntities(long entityTypeId,String username) throws DataException
//			 {
//		List<Entity> importedEt,filterImorts= null;
//		 try {
//			importedEt =  entityDAO.loadImportedEntities(entityTypeId,username);
//			// filter the entities which has permissions
//			if(importedEt != null && importedEt.size() > 0){
//				filterImorts = new ArrayList<Entity>();
//				for(Entity entity : importedEt){
//					MutableAcl acl = permissionManager.fetchAcl(entity);
//					// filter entities if entity has acl and not valid
//					if(acl == null && entity.isEntityValid() == true){
//						filterImorts.add(entity);
//					}
//				}
//			}else{
//				logger.info("loadImportedEntities:- importedEt list is empty "+importedEt);
//			}
//		} catch (DataException e) {
//			logger.error("loadImportedEntities.Unable to loadImportedEntities:"+e);
//			throw new DataException("loadImportedEntities.Unable to loadImportedEntities:"+e);
//		}
//		return filterImorts; 
//	}

	public String entityTitle(Entity entity) throws DataException{
		String entityTitle = null;EntityType etType = null;
		try {
			 etType = entityTypeDAO.load(entity.getEntityTypeId());
			 if(etType != null){
				 List<Attribute> attributes = etType.getAttributes();
				 if(attributes != null && attributes.size() > 0){
					 for(Attribute attribute:attributes){
						// get first text attribute value
						if(attribute.getFieldTypeName().equals(FieldType.TEXT.toString())){
							entityTitle = attributeValue(attribute.getId(),entity);
							if(entityTitle != null)
								break;
						}
						//get first attribute if a text one doesn't exist.
						else if (attribute.getOrder() == 1){
							if(attribute.getFieldTypeName().equals(FieldType.FILE.toString()) || attribute.getFieldTypeName().equals(FieldType.IMAGE.toString()) )
								entityTitle = attributeFileValue(attribute.getId(),entity);
							else	
							entityTitle = attributeValue(attribute.getId(),entity);
						}
					}  
				 }
				 
			 }else{
				 logger.debug("entityTitle:- load entityType returns etType is null "+etType); 
			 }
		} catch (DataException e) {
			logger.error("Unable to load entityTitle",e);
			e.printStackTrace();
		}
		return entityTitle;
	}

	private String attributeValue(long attributeId,Entity entity) throws DataException{
		String textValue = null;
		List<AttributeValueStorage> attValues = entity.getAttributeValueStorage();
		if(attValues != null && attValues.size() > 0){
			for(AttributeValueStorage attValue: attValues){
				if(attValue.getId() == attributeId){	
					if(attValue.getDataTypeName().equals(DataType.VARCHAR.toString())){
						if(attValue.getId() == Attributes.ROLENAME.getValue())
							textValue = attValue.getValueVarchar();//sanitizeRole(attValue.getValueVarchar());
						else
							textValue = attValue.getValueVarchar();
						break;
					}
					else if(attValue.getDataTypeName().equals(DataType.LONG.toString())){
						if(attValue.getValueLong() != null){
							long entTilte = attValue.getValueLong();
							textValue = new Long(entTilte).toString();						
							break;
						}
					}
					else if(attValue.getDataTypeName().equals(DataType.DATE.toString())){
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						Date entTitle = attValue.getValueDate();
						textValue = df.format(entTitle);
						break;
					}
					else if(attValue.getDataTypeName().equals(DataType.TEXT.toString())){
						textValue = attValue.getValueText();
						break;
					}	
				}
			}
		}
		return textValue;
	}		
	
	private String attributeFileValue(long attributeId,Entity entity) throws DataException{
		String fileValue = null;
		List<AttributeFileStorage> fileValues = entity.getAttributeFileStorage();
		if(fileValues != null && fileValues.size() > 0){
			for(AttributeFileStorage fileVal:fileValues){
				if(fileVal.getId() == attributeId){
					fileValue = fileVal.getFileName();
				}
			}
		}
		return fileValue;
	}
	
	@Override
	public boolean assignUserRole(long entityId, String roleOrUser) throws DataException{
		Entity entity;boolean status = false;
		try {
			entity = entityDAO.loadEntity(entityId);
			String userName ="",roleName ="";
			if(entity != null){
				// if the entity type is people or person
				if(entity.getEntityTypeId() == EntityTypes.PEOPLE.getValue()){
					List<AttributeValueStorage> users = entity.getAttributeValueStorage();
					if(users != null && users.size() > 0){
						for(AttributeValueStorage user:users){
							if(user.getId() == Attributes.USERNAME.getValue()){
								userName = user.getValueVarchar();
								break;
							}	
						 }
						status = permissionManager.assignRoleToUser(userName,roleOrUser);
					}
				 }else if(entity.getEntityTypeId() == EntityTypes.ROLE.getValue()){
					 List<AttributeValueStorage> roles = entity.getAttributeValueStorage();
						if(roles != null && roles.size() > 0){
							for(AttributeValueStorage role:roles){
								if(role.getId() == Attributes.ROLENAME.getValue()){
									roleName = role.getValueVarchar();
									break;
								}	
							 }
							status = permissionManager.assignRoleToUser(roleOrUser,sanitizeRole(roleName));
						}
				 }
				
			 }
		} catch (DataException e) {
			logger.error("assignUserRole:-Unable to assignUserRole:"+e);
			throw new DataException("assignUserRole:-Unable to assignUserRole:"+e);
		}
		return status;
	}
	@Override
	public boolean removeUserRole(long entityId, String roleOrUser,MappingJackson2JsonView view) throws DataException
			 {
		String userName ="",roleName ="";;boolean status = false;
		Entity entity;
		try {
			entity = entityDAO.loadEntity(entityId);
			if(entity != null){
				if(entity.getEntityTypeId() == EntityTypes.PEOPLE.getValue()){
					 List<AttributeValueStorage> users = entity.getAttributeValueStorage();
					 if(users != null && users.size() > 0){
						 for(AttributeValueStorage user:users){
							 if(user.getId() == Attributes.USERNAME.getValue()){
								userName = user.getValueVarchar();
							 	break;
							 }	
						 }
						 status = permissionManager.removeUserRole(userName,roleOrUser,view); 
					 }
				 }else if(entity.getEntityTypeId() == EntityTypes.ROLE.getValue()){
					 List<AttributeValueStorage> roles = entity.getAttributeValueStorage();
					 if(roles != null && roles.size() > 0){
						 for(AttributeValueStorage role:roles){
							 if(role.getId() == Attributes.ROLENAME.getValue()){
								 roleName = role.getValueVarchar();
							 	break;
							 }	
						 }
						 status = permissionManager.removeUserRole(roleOrUser,sanitizeRole(roleName),view); 
					 }
				 }
			}
		} catch (DataException e) {
			logger.error("removeUserRole:-Unable to removeUserRole:"+e);
			throw new DataException("removeUserRole:-Unable to removeUserRole:"+e);
		}
		return status;
	}
	@Override
	public List<AttributeValueStorage> loadRoles() throws DataException {
		try {
			return entityDAO.loadRoles();
		} catch (DataException e) {
			logger.error("loadRoles:-Unable to loadRoles:"+e);
			throw new DataException("loadRoles:-Unable to loadRoles:"+e);
		}
	}
	public String sanitizeRole(String role){
		if(role != null){
			if(role.startsWith("<b>ROLE<")){
				return role.trim().substring(12);
			}else{
				return role.trim().substring(5);
			}			
		}
		return "";
	}
	
	
	private String filterValue_Long(String value_Long) throws DataException {		
		StringBuffer strbuf = new StringBuffer();		
		for (int i=0; i<= value_Long.length()-1; i++ ) {
			char c = value_Long.charAt(i);
			if(c =='(' || c == ')' || c == '-' || c == '.' || c == ' '){
				continue;
			}else strbuf.append(c);			
         }		
		return strbuf.toString();
	}
	@Override
	public List<Entity> loadEntities(long entityTypeId) throws DataException  {
		try {
			return entityDAO.loadEntities(entityTypeId);
		} catch (DataException e) {
			logger.error("loadEntities:-Unable to loadEntities:"+e);
			throw new DataException("loadEntities:-Unable to loadEntities:"+e);
		}
	}
	@Override
	public Entity loadEntityByUsername(String userName) throws DataException {
		Entity entity = null;
		try {
			 entity =  entityDAO.loadEntityByUsername(userName);
			if(entity != null){
				List<AttributeFileStorage> fvs = entity.getAttributeFileStorage();
				if(fvs != null && fvs.size()>0){
	        		for(AttributeFileStorage fstorage:fvs){
	        			fstorage.setImagePath(fileManager.getSavedImagePath(fstorage));
	        		}
	        	}
			}
			
		} catch (DataException e) {
			logger.error("loadEntities:-Unable to loadEntities:"+e);
			throw new DataException("loadEntities:-Unable to loadEntities:"+e);
		}
		return entity;
	}
	private EntityType loadRelatedEntityType(long entityTypeId) throws DataException{  		
  		return entityTypeDAO.load(entityTypeId);
  	}



	@Override
	public List<Entity> loadInvalidEntities() throws DataException {
		List<Entity> invalidEntities = null;
		invalidEntities =entityDAO.loadInvalidEntities();
		if(invalidEntities !=null){
			for(Entity entity:invalidEntities){
				entity.setTitile(WordUtils.capitalize(entityTitle(entity)));
			}
		}
		return invalidEntities;
	}



	@Override
	public void removeEntity(long entityId) throws DataException {
		entityDAO.removeEntity(entityId);
		List<Entity> relationsToDelete = entitySearch("type:Relation From:["+ entityId +"] or To:["+ entityId +"]", 0, "<b>", "</b>", 10000, 0);
		if(relationsToDelete == null)
			return;
		for(Entity e : relationsToDelete){
			entityDAO.removeEntity(e.getEntityId());
		}
	}


	@Override
	public boolean changePassword(long entityId, String newPassword)
			throws DataException {
		EntityStatus entStaus = null;
		try{
			Entity entity = entityDAO.loadEntity(entityId);
			if(entity != null){
				String password = permissionManager.encodePassword(newPassword);
				List<AttributeValueStorage> personAttVals = entity.getAttributeValueStorage();
		        for(AttributeValueStorage person:personAttVals){
		        	if(person.getId() == Attributes.PASSWORD.getValue()){
		        		person.setValueVarchar(password);
		        		entStaus = entityDAO.avsSave(person);
		        		break;
		        	}
		        }
			}
			
	        if(entStaus == null)
	        	throw new DataException("changePassword:-Sorry Failed to Save Password:");
	 	}catch(Exception e){
			logger.error("changePassword:-Unable to changePassword:"+e);
			throw new DataException("changePassword:-Unable to changePassword:"+e);
		}
		return true;
	}



	@Override
	public List<Entity> pullEventsInRange(LocalDate intervalStartDate, LocalDate intervalEndDate) throws DataException {

		String searchTerm = "type:Event";
		List<Entity> entites = null;
		List<Entity> result = new ArrayList<Entity>();
		String pattern1 = "$";
		String SearchTerms = searchTerm.replace("\"", pattern1);
		String sterm = SearchTerms.replaceAll(" +", " ").trim();
		boolean isComplexQuery = isComplexQuery(searchTerm);
		String[] arr = buildSearchArray(searchTerm);
		try {
			if (arr == null) {
				return null;
			} else if (isComplexQuery) {
				entites = entityDAO.entitySearchComplex(arr, 0, "<b>", "</b>", 10000, 0);
			} else {
				entites = entityDAO.entitySearch(sterm, 0, "<b>", "</b>", 10000, 0);
			}
			if (entites == null || entites.size() == 0) {
				logger.debug("entitySearch:- entitySearch returns entites:-" + entites);
				return null;
			}
		} catch (Exception e) {
			logger.error("entitySearch:- Unable to search entity :-", e);
			throw new DataException("entitySearch:- Unable to search entity :-", e);
		}
		// filter events based on date range.
		boolean eventAdded = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (Entity eventEntity : entites) {
			eventAdded = false;
			LocalDate eventDate = LocalDate.parse(sdf.format(((Date) eventEntity.getValueObject("Date"))));

			if ((eventEntity.getValueString("Repeats") != null)) {
				buildRepeatedEvents(eventEntity, result, intervalStartDate, intervalEndDate);
				eventAdded = true;
			}
			if (!eventAdded && eventDate.compareTo(intervalStartDate) >= 0 && eventDate.compareTo(intervalEndDate) <= 0) {
				result.add(eventEntity);
				eventAdded = true;
			}
		}

		return result;
	}



	private void buildRepeatedEvents(Entity eventEntity, List<Entity> result, LocalDate intervalStartDate, LocalDate intervalEndDate) throws DataException {
		
		String repeats = eventEntity.getValueString("Repeats");
		LocalDate eventDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(((Date) eventEntity.getValueObject("Date"))));
		LocalDate eventRepetitionEndDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(((Date) eventEntity.getValueObject("RepetitionEndsOn"))));
		if(eventRepetitionEndDate == null)
			eventRepetitionEndDate = intervalEndDate;
		if(repeats.equalsIgnoreCase("daily")
				|| repeats.equalsIgnoreCase("weekly")
				|| repeats.equalsIgnoreCase("monthly")
				|| repeats.equalsIgnoreCase("yearly")){
			
			switch (repeats) {
			case "Daily":
				while(eventDate.compareTo(intervalEndDate) <= 0){
					if(eventDate.compareTo(intervalStartDate) >= 0 
							&& eventRepetitionEndDate.compareTo(intervalStartDate) >= 0
							&& eventDate.compareTo(eventRepetitionEndDate) <= 0){
						Entity recurringEvent = loadEntity(eventEntity.getEntityId());
						for(AttributeValueStorage attributeValueStorage : recurringEvent.getAttributeValueStorage()){
							if(attributeValueStorage.getName().equalsIgnoreCase("Date")){
								attributeValueStorage.setValueDate(Date.from(eventDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
							}
						}
						result.add(recurringEvent);
					}
					eventDate = eventDate.plusDays(1);
				}
				break;
			case "Weekly":
				while(eventDate.compareTo(intervalEndDate) <= 0){
						if(eventDate.compareTo(intervalStartDate) >= 0 
								&& eventRepetitionEndDate.compareTo(intervalStartDate) >= 0
								&& eventDate.compareTo(eventRepetitionEndDate) <= 0){
							Entity recurringEvent = loadEntity(eventEntity.getEntityId());
							for(AttributeValueStorage attributeValueStorage : recurringEvent.getAttributeValueStorage()){
								if(attributeValueStorage.getName().equalsIgnoreCase("Date")){
									attributeValueStorage.setValueDate(Date.from(eventDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
								}
							}
							result.add(recurringEvent);
						}
						eventDate = eventDate.plusWeeks(1);
				}
				break;
			case "Monthly":
				while(eventDate.compareTo(intervalEndDate) <= 0){
						if(eventDate.compareTo(intervalStartDate) >= 0 
								&& eventRepetitionEndDate.compareTo(intervalStartDate) >= 0
								&& eventDate.compareTo(eventRepetitionEndDate) <= 0){
							Entity recurringEvent = loadEntity(eventEntity.getEntityId());
							for(AttributeValueStorage attributeValueStorage : recurringEvent.getAttributeValueStorage()){
								if(attributeValueStorage.getName().equalsIgnoreCase("Date")){
									attributeValueStorage.setValueDate(Date.from(eventDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
								}
							}
							result.add(recurringEvent);
						}
						eventDate = eventDate.plusMonths(1);
				}
				break;
			case "Yearly":
				while(eventDate.compareTo(intervalEndDate) <= 0){
						if(eventDate.compareTo(intervalStartDate) >= 0
								&& eventRepetitionEndDate.compareTo(intervalStartDate) >= 0
								&& eventDate.compareTo(eventRepetitionEndDate) <= 0){
							Entity recurringEvent = loadEntity(eventEntity.getEntityId());
							for(AttributeValueStorage attributeValueStorage : recurringEvent.getAttributeValueStorage()){
								if(attributeValueStorage.getName().equalsIgnoreCase("Date")){
									attributeValueStorage.setValueDate(Date.from(eventDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
								}
							}
							result.add(recurringEvent);
						}
						eventDate = eventDate.plusYears(1);
				}
				break;
			default:
				break;
			}
		}
	}
	
	@Override
	public String getAttributeValue(List<AttributeValueStorage> avsList, String attributeName)
			throws DataException {
			if(avsList !=null){
				for(AttributeValueStorage avs:avsList){
					if(avs.getName().equalsIgnoreCase(attributeName)){
						if(avs.getFieldTypeId() == 1)
							return avs.getValueVarchar();
					}
				}
			}
		return "";
	}
	
}