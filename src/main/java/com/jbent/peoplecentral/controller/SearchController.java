package com.jbent.peoplecentral.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.manager.EntityManager;
import com.jbent.peoplecentral.model.manager.EntityTypeManager;
import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;
import com.jbent.peoplecentral.model.pojo.BoxEntity;
import com.jbent.peoplecentral.model.pojo.Entity;
import com.jbent.peoplecentral.model.pojo.EntityType;
import com.jbent.peoplecentral.model.pojo.Attribute.Attributes;
import com.jbent.peoplecentral.model.pojo.EntityType.EntityTypes;
import com.jbent.peoplecentral.permission.Permission.Permissions;
import com.jbent.peoplecentral.web.SessionContext;

@Controller
public class SearchController extends WebApplicationObjectSupport{	
	
	private EntityManager entityManager;
	@Autowired
	private EntityTypeManager entityTypeManager;
	@Autowired
	private MessageSource messageSource;
	boolean isError = false;
    private int i=0;
	
	@RequestMapping(value = {"/entity/search","/*/entity/search"}, method = RequestMethod.GET)
	public MappingJackson2JsonView manage(){
		MappingJackson2JsonView view = new MappingJackson2JsonView();		   
		try {
			long page = 1;
			long begin = 0;
			long end = 0;
			long limit=10;
			view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
			view.getAttributesMap().put("page", page);
			view.getAttributesMap().put("begin", begin);
			view.getAttributesMap().put("end", end);
			view.getAttributesMap().put("limit", limit);
		} catch (Exception e) {
			logger.error("error in entitysearch :" +e.getMessage(),e);
			e.printStackTrace();
			}
		return view;
	} 

	@RequestMapping(value = {"/search/build/query","/*/search/build/query"}, method = RequestMethod.POST)
	public MappingJackson2JsonView searchWizard(){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		view.getAttributesMap().put("page","search");
		return view;
	}

	
	@RequestMapping(value = {"/entity/search/box/list","/*/entity/search/box/list"}, method = RequestMethod.GET)
	public MappingJackson2JsonView box(){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		long page = 1;
		long totalPages = 0;
		long begin = 0;
		long end = 0;
		long limit=10;
		long offset = 0;
		String order = "date_added";
		double totalRecords = 0;
		List<Entity> entities = null;
		
			try {
				entities = entityManager.loadBoxEntities(order,limit,offset);
				if(entities!=null && entities.size()>0){
					totalRecords=entities.get(entities.size()-1).getBoxEntityCount(); 
					double totalPage=totalRecords/limit;
	                double tpages = Math.ceil(totalPage);
	                totalPages = (long) Math.abs(tpages);
	                if(totalPages <20 || totalPages == 20){
	                    begin =1;
	                    end = totalPages;
	                }else if(totalPages >20){
	                	begin = 1;
	                    end = 20;
	                }              
	                if(page > 9 && page+14 < totalPages){
	                     begin=page-5;
	                     end=page+14;
	                }else if(page > 9 && page+14 >= totalPages){
	                     begin=totalPages-20;
	                     end=totalPages;
	                }
					
				}
			} catch (DataException e) {
				logger.error("Error loading box entities : " +e.getMessage(),e);
			}catch (Exception e) {
				logger.error("Error loading box entities : " +e.getMessage(),e);
			}
			view.getAttributesMap().put("entities", entities);
			view.getAttributesMap().put("offBox", offset);
			view.getAttributesMap().put("totalRecordsBox", (long)totalRecords);
			view.getAttributesMap().put("totalPages", totalPages);
			view.getAttributesMap().put("beginBox", begin);
			view.getAttributesMap().put("endBox", end);
			view.getAttributesMap().put("limitBox", limit);
			view.getAttributesMap().put("pageBox", page);
			view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());		
		return view;
	}
	@RequestMapping(value = {"/entity/search/simple","/*/entity/search/simple"}, method = RequestMethod.POST)
    public MappingJackson2JsonView entitySearchSimple(@RequestParam("searchTerm") String searchTerm,@RequestParam("entityTypeId")  Long entTypeId,Locale locale){
		MappingJackson2JsonView view = new MappingJackson2JsonView(); 
		long entityTypeId = 0;
		 String startHighlight = "<b>";
		 String endHighlight = "</b>";
		 List<Entity> simpleSearch = null;
		if(entTypeId != null){
	       entityTypeId = entTypeId;
	        }
		 try {
			 simpleSearch =  entityManager.entitySearchSimple(searchTerm,entityTypeId,startHighlight,endHighlight);
			 
		} catch (DataException e) {
			isError= true;
			view.getAttributesMap().put("errorMsg",messageSource.getMessage("error.entitySearch.invalid", null, locale));
			logger.error("Error simple searching entities : " + e.getMessage(),e);
		}catch (Exception e) {
			logger.error("Error simple searching entities : " + e.getMessage(),e);
		}
		 view.getAttributesMap().put("simpleSearch",simpleSearch);		 
		 view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return view;
	 }
	

	@RequestMapping(value = {"/entity/search","/*/entity/search"}, method = RequestMethod.POST)
    public MappingJackson2JsonView entitySearch(@RequestParam("page")long page,@RequestParam("searchTerm") String searchTerm,@RequestParam("entityTypeId") Long entTypeId,@RequestParam("limit") long limit,@RequestParam("begin") long begin,@RequestParam("end") long end,HttpServletRequest request,Locale locale ){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
	    long entityTypeId = 0;
	    long offset = 0;
	    String startHighlight = "<b>";
		String endHighlight = "</b>";		
		long totalPages = 0;
		long entitiesBoxCount = 0;
		double totalRecords = 0;
		List<Entity> entsearch = new ArrayList<Entity>(); 
	    List<Long>eIds = new ArrayList<Long>();	    
	    Entity fnlEnt = new Entity();
		List<Entity> fnlEntList = new ArrayList<Entity>();	
		List<AttributeValueStorage> avsSearchLst = new ArrayList<AttributeValueStorage>();
		boolean flag=false;
	    if(page>1){
            offset= limit*(page-1);
         }
        
	       try {
			if(entTypeId != null){	        	
			    	entityTypeId = entTypeId;
			    }
			    entsearch = entityManager.entitySearch(searchTerm,entityTypeId,startHighlight,endHighlight,limit,offset);
			    String[] parts = searchTerm.split("\\[");
			    String searchVal = "";
			    if(parts.length>1){
				    String[] subParts = parts[1].split("\\]"); 
				    searchVal = subParts[0];
			    }
			    entitiesBoxCount = entityManager.getCountBoxEntity();			    
			    if(searchVal != ""){
			    	if(entsearch != null && entsearch.size()>0){				    	
				    	for(Entity ent:entsearch){
				    		for(AttributeValueStorage avs:ent.getAttributeValueStorage()){				    			
				    				AttributeValueStorage avsSearch = new AttributeValueStorage();
									if(avs.getValueVarchar() != null && searchVal.equalsIgnoreCase(avs.getValueVarchar())){						 
										eIds.add(avs.getEntityId());
										avsSearch = avs;	
										avsSearchLst.add(avsSearch);
										fnlEnt.setAttributeValueStorage(avsSearchLst);
										fnlEntList.add(fnlEnt);
										flag=true;
									}else if (avs.getValue_Long() != null && searchVal.equalsIgnoreCase(avs.getValue_Long())) {
										eIds.add(avs.getEntityId());
										avsSearch = avs;	
										avsSearchLst.add(avsSearch);
										fnlEnt.setAttributeValueStorage(avsSearchLst);
										fnlEntList.add(fnlEnt);
										flag=true;
									}else if(avs.getValue_Date() != null && searchVal.equalsIgnoreCase(avs.getValue_Date())){
										eIds.add(avs.getEntityId());
										avsSearch = avs;	
										avsSearchLst.add(avsSearch);
										fnlEnt.setAttributeValueStorage(avsSearchLst);
										fnlEntList.add(fnlEnt);
										flag=true;
									}else if(avs.getValueText() != null && searchVal.equalsIgnoreCase(avs.getValueText())){
										eIds.add(avs.getEntityId());
										avsSearch = avs;	
										avsSearchLst.add(avsSearch);
										fnlEnt.setAttributeValueStorage(avsSearchLst);
										fnlEntList.add(fnlEnt);
										flag=true;
									}else if(!flag){
										fnlEntList.add(fnlEnt);
										flag = false;
									}
									//sanitize Role Name
									if(avs.getId() == Attributes.ROLENAME.getValue()){
			            				avs.setValueVarchar(entityManager.sanitizeRole(avs.getValueVarchar()));
									}	
				    					
								//eIds.add(avs.getEntityId());
				    		}										
						}
				    	if(fnlEntList != null && fnlEntList.size()>0){
					    	totalRecords=fnlEntList.get(0).getSearchCount();	        	
					    	double totalPage=totalRecords/limit;
					        double tpages = Math.ceil(totalPage);
					        totalPages = (long) Math.abs(tpages);
				    	    if(totalPages <20 || totalPages == 20){
				                begin =1;
				                end = totalPages;
				            }else if(totalPages >20){
				                begin = 1;
				                end = 20;
				            }
				            if(page > 9 && page+14 < totalPages){
				                 begin=page-5;
				                 end=page+14;
				            }else if(page > 9 && page+14 >= totalPages){
				                 begin=totalPages-20;
				                 end=totalPages;
				            }
				    	}
				    }
			    	fnlEntList = filterEntityAttributes(fnlEntList);
			    	view.getAttributesMap().put("entsearch",fnlEntList);//entsearch);
			    }else{
			    	if(entsearch != null && entsearch.size()>0){
				    	totalRecords=entsearch.get(0).getSearchCount();	        	
				    	double totalPage=totalRecords/limit;
				        double tpages = Math.ceil(totalPage);
				        totalPages = (long) Math.abs(tpages);
				    	for(Entity ent:entsearch){
				    		for(AttributeValueStorage avs:ent.getAttributeValueStorage()){
				    			eIds.add(avs.getEntityId());				    			
				    			//sanitize Role Name
								if(avs.getId() == Attributes.ROLENAME.getValue()){
		            				avs.setValueVarchar(entityManager.sanitizeRole(avs.getValueVarchar()));
								}	
				    		}										
						}
				    	   if(totalPages <20 || totalPages == 20){
				                begin =1;
				                end = totalPages;
				           }else if(totalPages >20){
				                begin = 1;
				                end = 20;
				            }
				            if(page > 9 && page+14 < totalPages){
				                 begin=page-5;
				                 end=page+14;
				             }else if(page > 9 && page+14 >= totalPages){
				                 begin=totalPages-20;
				                 end=totalPages;
				             }
				    }
			    	entsearch = filterEntityAttributes(entsearch);
			    	view.getAttributesMap().put("entsearch",entsearch);
			    }			    
			    if((entsearch == null || entsearch.size() == 0) && (searchTerm != "")){
			    	view.getAttributesMap().put("errorMsg",messageSource.getMessage("entity.entitySearch.isNull", null, locale));
	            	logger.error("entitySearch:- entitySearch returns entites:-"+entsearch);
	            }
		} catch (DataException e) {
			isError= true;
			view.getAttributesMap().put("errorMsg",messageSource.getMessage("error.entitySearch.invalid", null, locale));
			logger.error("Error saving Value : " + e.getMessage());
			e.printStackTrace();
			
		}
		
		//model.addAttribute("entsearch",fnlEntList);//entsearch);
		view.getAttributesMap().put("off", offset);
		view.getAttributesMap().put("totalRecords", (long)totalRecords);
		view.getAttributesMap().put("totalPages", totalPages);
		view.getAttributesMap().put("begin", begin);
		view.getAttributesMap().put("end", end);
		view.getAttributesMap().put("limit", limit);
		view.getAttributesMap().put("page", page);
		view.getAttributesMap().put("searchTerm", searchTerm);
		view.getAttributesMap().put("eIdss", eIds);
		view.getAttributesMap().put("totalRecordsBox",entitiesBoxCount );
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return view;//return "search";
	 }
		

	@RequestMapping(value = {"entity/addtobox","/*/entity/addtobox"}, method = RequestMethod.GET)
	public @ResponseBody long addEntitiesToBox(@RequestParam("entityId")long entityId,@RequestParam("eIds")List<String> eIds) {
		MappingJackson2JsonView view = new MappingJackson2JsonView(); 
		String illegals = "[]\n\r";
	    String pattern = "[" + Pattern.quote(illegals) + "]";
		long entityCount=0;
		//long entityId=0;
		int arrCount =0;		
		Long[] la = null;
		  	
    	
		List<BoxEntity> boxEntities = new ArrayList<BoxEntity>();
		try {
			//checking entityId if user click on AddToBox button otherwise list of entityIds    
			
				if(entityId>0){
					la=new Long[1];
					la[0]=entityId;			
				}else if(eIds != null && eIds.size()>0){
					la = new Long[eIds.size()];			
					for(String ent:eIds){
						la[arrCount]=Long.parseLong((String) ent.toString().replaceAll(pattern, "").trim());
						arrCount++;
					}			
				}  
				boxEntities = entityManager.saveEntitiesToBox(la);
				if(boxEntities != null && boxEntities.size()>0){
					entityCount = boxEntities.size();
					view.getAttributesMap().put("box", boxEntities);
				}
	
			} catch (DataException e) {
			logger.error("Error adding entities : " + e.getMessage(),e);
	    	}catch (Exception e) {
			logger.error("Error adding entities : " + e.getMessage(),e);
		    }
		   return entityCount;
	}
	
	@RequestMapping(value = {"/entity/search/box/list","/*/entity/search/box/list"}, method = RequestMethod.POST)
    public MappingJackson2JsonView boxEntities(@RequestParam("pageBox")long page,@RequestParam("beginBox") long begin,@RequestParam("endBox") long end,@RequestParam("limitBox") long limit){
		MappingJackson2JsonView view = new MappingJackson2JsonView(); 
		long offset = 0;		
		 long totalPages = 0;
		 String order = "date_added";
		 //long limit =10;
		 double totalRecords = 0;
		 if(page>1){
	         offset= limit*(page-1);
	     }
		 List<Entity> entities = null;
		 try {
			entities = entityManager.loadBoxEntities(order,limit,offset);
			if(entities!=null && entities.size()>0){
				totalRecords=entities.get(entities.size()-1).getBoxEntityCount(); 
				double totalPage=totalRecords/limit;
                double tpages = Math.ceil(totalPage);
                totalPages = (long) Math.abs(tpages);
                if(totalPages <20 || totalPages == 20){
                    begin =1;
                    end = totalPages;
                }else if(totalPages >20){
                	begin = 1;
                    end = 20;
                }              
                if(page > 9 && page+14 < totalPages){
                     begin=page-5;
                     end=page+14;
                }else if(page > 9 && page+14 >= totalPages){
                     begin=totalPages-20;
                     end=totalPages;
                }
				
			}
			entities = filterEntityAttributes(entities);
			//sanitize Role Name
			for(Entity ent:entities){
				for(AttributeValueStorage avs:ent.getAttributeValueStorage()){	
					if(avs.getId() == Attributes.ROLENAME.getValue()){
	    				avs.setValueVarchar(entityManager.sanitizeRole(avs.getValueVarchar()));
					}	
	    		}	
			}
		} catch (DataException e) {
			logger.error("error in box entities : "+ e.getMessage(),e);
		}catch (Exception e) {
			logger.error("error in box entities : "+ e.getMessage(),e);
		}
		view.getAttributesMap().put("entities", entities);
		view.getAttributesMap().put("offBox", offset);
		view.getAttributesMap().put("totalRecordsBox", (long)totalRecords);
		view.getAttributesMap().put("totalPages", totalPages);
		view.getAttributesMap().put("beginBox", begin);
		view.getAttributesMap().put("endBox", end);
		view.getAttributesMap().put("limitBox", limit);
		view.getAttributesMap().put("pageBox", page);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());		
		i=i+2;
		view.getAttributesMap().put("pagereload", "Yes Reloaded..."+ i);
		return view;
	}
	
	
	@RequestMapping(value = {"/entity/search/box/delete/{entityId}","/*/entity/search/box/delete/{entityId}"}, method = RequestMethod.POST)
    public @ResponseBody Long removeBoxEntity(@PathVariable long entityId){
		Long count = null;
		try {
			count = entityManager.removeBoxEntity(entityId);
			
		} catch (DataException e) {
			logger.error("Error removing  entity : " + e.getMessage(),e);
		}         
      
		return count;
    }
	
	@RequestMapping(value={"/entity/search/build","/*/entity/search/build"},method=RequestMethod.POST)	
  	public MappingJackson2JsonView searchQueryBuilder() throws Exception{ 
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.getAttributesMap().put("client",ClientManageUtil.loadClientSchema());
		view.getAttributesMap().put("ConditionQueryRowNum",0);
		return view;
  	}
	
	
	@RequestMapping(value={"/entity/savedsearch/result","/*/entity/savedsearch/result"},method=RequestMethod.POST)	
  	public MappingJackson2JsonView savedSearchResult(HttpServletRequest request, HttpServletResponse response,@RequestParam("entityId") long entityId, @RequestParam("searchType") String searchType) throws Exception{ 
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		String searchTerm = null;
		long entityTypeId =0;
		long begin = 0,end = 0,page = 0;
		long limit = 10;
		long offset = 0;		
		long entitiesBoxCount = 0;	
		String startHighlight = "<b>";
		String endHighlight = "</b>";
		List<Entity> entsearch = null;
		if(searchType.equalsIgnoreCase("savedsearch")){
			if(entityId >0){
				Entity entity = entityManager.loadEntity(entityId);
				List<AttributeValueStorage> avs = entity.getAttributeValueStorage();
				for(AttributeValueStorage attValue: avs){
					if(attValue.getId() == Attributes.QUERYSYNTAX.getValue())
						searchTerm = attValue.getValueText().trim();
				}
			}
		}
		try {
			entsearch =  entityManager.entitySearch(searchTerm,entityTypeId,startHighlight,endHighlight, limit, offset);
			entsearch = filterEntityAttributes(entsearch);
		} catch (DataException e) {
			logger.error("Error simple searching entities : " + e.getMessage(),e);
		} catch (Exception e) {
			logger.error("Error simple searching entities : " + e.getMessage(),e);
		}
		entitiesBoxCount = entityManager.getCountBoxEntity();
		displaySearch(entsearch,begin,end,page,limit,offset,view);
		view.getAttributesMap().put("searchTerm", searchTerm);
		view.getAttributesMap().put("totalRecordsBox",entitiesBoxCount );
		view.getAttributesMap().put("client",ClientManageUtil.loadClientSchema());
		return view;
  	}

	@RequestMapping(value={"/entity/search/result","/*/entity/search/result"},method=RequestMethod.POST)	
  	public MappingJackson2JsonView displaySearchResult(HttpServletRequest request, HttpServletResponse response,@RequestParam("entityId") long entityId,@RequestParam("fileterConditionValueArray") String searchVal,@RequestParam("entityTypeId") long entityTypeId,@RequestParam("conditionType") String conditionType,@RequestParam("fileterAttArray") String fileterAttArray,@RequestParam("fileterConditionArray") String fileterConditionArray,@RequestParam("fileterConditionValueArray") String fileterConditionValueArray,@RequestParam("queryName") String queryName,@RequestParam("queryString") String queryString,@RequestParam("searchType") String searchType) throws Exception{ 
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		String searchTerm = null;
		long begin = 0,end = 0,page = 0;
		long limit = 10;
		long offset = 0;		
		long entitiesBoxCount = 0;	
		String startHighlight = "<b>";
		String endHighlight = "</b>";
		Entity fnlEnt = new Entity();
		List<Entity> entsearch = null;
		List<Entity> fnlEntList = new ArrayList<Entity>();
		List<AttributeValueStorage> avsSearchLst = new ArrayList<AttributeValueStorage>();
		boolean flag = false;
		if(searchType.equalsIgnoreCase("simple") || searchType.equalsIgnoreCase("savedsearch")){
			if(entityId >0){
				Entity entity = entityManager.loadEntity(entityId);
				List<AttributeValueStorage> avs = entity.getAttributeValueStorage();
				for(AttributeValueStorage attValue: avs){
					if(attValue.getId() == Attributes.QUERYSYNTAX.getValue())
						searchTerm = attValue.getValueText().trim();
				}
			}else{
				EntityType et = entityTypeManager.load(entityTypeId);
				searchTerm = prepareQuery(et,conditionType,fileterAttArray,fileterConditionArray,fileterConditionValueArray,queryName);
			}
			
		}else if (searchType.equalsIgnoreCase("advanced")){
			if(entityId >0){
				Entity entity = entityManager.loadEntity(entityId);
				List<AttributeValueStorage> avs = entity.getAttributeValueStorage();
				for(AttributeValueStorage attValue: avs){
					if(attValue.getId() == Attributes.QUERYSYNTAX.getValue())
						searchTerm = attValue.getValueText().trim();
				}
			}else{
				searchTerm = queryString.trim();
			}
		} 
		
		
		try {
			entsearch =  entityManager.entitySearch(searchTerm,entityTypeId,startHighlight,endHighlight, limit, offset);
			String[] parts = searchTerm.split("\\[");
			if(parts.length>1){
			    String[] subParts = parts[1].split("\\]"); 
			    searchVal = subParts[0];
		    }
			if(searchVal != ""){
			    if(entsearch != null){
				for(Entity ent:entsearch){				
					for(AttributeValueStorage avs : ent.getAttributeValueStorage()){
						AttributeValueStorage avsSearch = new AttributeValueStorage();
						if(avs.getValueVarchar() != null && searchVal.equalsIgnoreCase(avs.getValueVarchar())){						 
							avsSearch = avs;	
							avsSearchLst.add(avsSearch);
							fnlEnt.setAttributeValueStorage(avsSearchLst);
							fnlEntList.add(fnlEnt);
							flag=true;
						}else if (avs.getValue_Long() != null && searchVal.equalsIgnoreCase(avs.getValue_Long())) {
							avsSearch = avs;
							avsSearchLst.add(avsSearch);
							fnlEnt.setAttributeValueStorage(avsSearchLst);
							fnlEntList.add(fnlEnt);
							flag=true;
						}else if(avs.getValue_Date() != null && searchVal.equalsIgnoreCase(avs.getValue_Date())){
							avsSearch = avs;
							avsSearchLst.add(avsSearch);
							fnlEnt.setAttributeValueStorage(avsSearchLst);
							fnlEntList.add(fnlEnt);
							flag=true;
						}else if(avs.getValueText() != null && searchVal.equalsIgnoreCase(avs.getValueText())){
							avsSearch = avs;
							avsSearchLst.add(avsSearch);
							fnlEnt.setAttributeValueStorage(avsSearchLst);
							fnlEntList.add(fnlEnt);
							flag=true;
						}else if(!flag){
							fnlEntList.add(ent);
							flag = false;
						}										
					}				 
				}
			}
			}
		} catch (DataException e) {
			logger.error("Error simple searching entities : " + e.getMessage(),e);
		}catch (Exception e) {
			logger.error("Error simple searching entities : " + e.getMessage(),e);
		}
		entitiesBoxCount = entityManager.getCountBoxEntity();
		if(searchVal!=""){
			fnlEntList = filterEntityAttributes(fnlEntList);
			displaySearch(fnlEntList,begin,end,page,limit,offset,view);
		}else{
			entsearch = filterEntityAttributes(entsearch);
			displaySearch(entsearch,begin,end,page,limit,offset,view);
		}
		//displaySearch(fnlEntList,begin,end,page,limit,offset,model);
		view.getAttributesMap().put("searchTerm", searchTerm);
		view.getAttributesMap().put("totalRecordsBox",entitiesBoxCount );
		view.getAttributesMap().put("client",ClientManageUtil.loadClientSchema());
		return view;
  	}
	
	@RequestMapping(value = {"/entity/search/addrelation","/*/entity/search/addrelation"}, method=RequestMethod.GET)
  	public MappingJackson2JsonView displaySearchResultJson(
  			HttpServletRequest request, 
  			HttpServletResponse response,
  			@RequestParam("entityTypeId") long entityTypeId,
			@RequestParam("conditionType") String conditionType,
			@RequestParam("fileterAttArray") String fileterAttArray,
			@RequestParam("fileterConditionArray") String fileterConditionArray,
			@RequestParam("fileterConditionValueArray") String fileterConditionValueArray) throws Exception{

		MappingJackson2JsonView view = new MappingJackson2JsonView();
		String searchTerm = null;
		long begin = 0,end = 0,page = 0;
		long limit = 10;
		long offset = 0;
		String startHighlight = "<b>";
		String endHighlight = "</b>";
		List<Entity> entsearch = null;
		
		if(entityTypeId >0){
			EntityType et = entityTypeManager.load(entityTypeId);
			searchTerm = prepareQuery(et,conditionType,fileterAttArray,fileterConditionArray,fileterConditionValueArray,"");
		}
		try {
			entsearch =  entityManager.entitySearch(searchTerm,entityTypeId,startHighlight,endHighlight, limit, offset);
		} catch (DataException e) {
			logger.error("Error simple searching entities : " + e.getMessage(),e);
		}catch (Exception e) {
			logger.error("Error simple searching entities : " + e.getMessage(),e);
		}
		displaySearch(entsearch,begin,end,page,limit,offset,view);
		List<Map<String, String>> entityList = new ArrayList<Map<String,String>>();
		if(entsearch != null && entsearch.size()>0){
			for(Entity e : entsearch){
				Map<String, String> map = new HashMap<String, String>();
				map.put("entityId", e.getEntityId()+"");
				map.put("title", e.getAttributeValueStorage().get(0).getValue()+"");			
				entityList.add(map);
			}
		}
		view.getAttributesMap().put("entityList", entityList);
		return new MappingJackson2JsonView();
  	}
	
	private void displaySearch(List<Entity>  entsearch,long begin,long end,long page,long limit,long offset,MappingJackson2JsonView view){
		long totalPages = 0;
		List<Long>eIds = new ArrayList<Long>();	    
		double totalRecords = 0;
		try{
			if(entsearch != null && entsearch.size()>0){
	        	totalRecords=entsearch.get(0).getSearchCount();	        	
	        	double totalPage=totalRecords/limit;
	            double tpages = Math.ceil(totalPage);
	            totalPages = (long) Math.abs(tpages);
	        	for(Entity ent:entsearch){
	        		if(ent.getAttributeValueStorage() != null && ent.getAttributeValueStorage().size()>0){
	        			for(AttributeValueStorage avs:ent.getAttributeValueStorage()){
	            			eIds.add(avs.getEntityId());
	            			if(avs.getId() == Attributes.ROLENAME.getValue()){
	            				avs.setValueVarchar(entityManager.sanitizeRole(avs.getValueVarchar()));
							}	
	            		}	
	        		}
	        	}
	        	if(totalPages <20 || totalPages == 20){
	        		begin =1;
	                end = totalPages;
	            }else if(totalPages >20){
	            	begin = 1;
	                end = 20;
	            }
	            if(page > 9 && page+14 < totalPages){
	            	begin=page-5;
	                end=page+14;
	            }else if(page > 9 && page+14 >= totalPages){
	            	begin=totalPages-20;
	                end=totalPages;
	           }
	        }
		}catch(Exception e){
			logger.error("Error while displaying search result:"+e);
		}
		
		view.getAttributesMap().put("entsearch",entsearch);
		view.getAttributesMap().put("off", offset);
		view.getAttributesMap().put("totalRecords", (long)totalRecords);
		view.getAttributesMap().put("totalPages", totalPages);
		view.getAttributesMap().put("begin", begin);
		view.getAttributesMap().put("end", end);
		view.getAttributesMap().put("limit", limit);
		view.getAttributesMap().put("page", page);		
		view.getAttributesMap().put("eIdss", eIds);		
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
	}
	
	private String prepareQuery(EntityType entityType,String conditionType, String fileterAttArray,
			String fileterConditionArray, String fileterConditionValueArray,
			String queryName){
			
			StringBuffer searchTerm;
			searchTerm = new StringBuffer();
			try {
				String[] selectedAttributes = fileterAttArray.split(",");
				String[] conditionValues= fileterConditionValueArray.split(",");
				if(selectedAttributes.length > 0 && conditionValues.length > 0){
					if(selectedAttributes[0] != "" && conditionValues[0] !=""){
						for(int i=0;i<selectedAttributes.length;i++){
							if(conditionType.equalsIgnoreCase("any")){
								if(searchTerm.length()<=0){
									searchTerm.append("type:"+entityType.getName()+" ");
									searchTerm.append(selectedAttributes[i]+":["+conditionValues[i]+"]");
								}else{
									searchTerm.append(" or "+selectedAttributes[i]+":["+conditionValues[i]+"]");
								}	
							}else if(conditionType.equalsIgnoreCase("all")) {
								if(searchTerm.length()<=0){
									searchTerm.append("type:"+entityType.getName()+" ");
									searchTerm.append(selectedAttributes[i]+":["+conditionValues[i]+"]");
								}else{
									searchTerm.append(" and "+selectedAttributes[i]+":["+conditionValues[i]+"]");
								}
							}
						}
					}else searchTerm.append("type:"+entityType.getName());
				}else{
					searchTerm.append("type:"+entityType.getName());
				}
				
				System.out.println("**Query String: "+searchTerm.toString());
			} catch (Exception e) {
				logger.error("Error while preparing query for search" +e);
				e.printStackTrace();
			}
			return searchTerm.toString();
	}

	@RequestMapping(value={"/search/build","/*/search/build"},method=RequestMethod.GET)	
  	public MappingJackson2JsonView dynamicJasper() { 
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.getAttributesMap().put("client",ClientManageUtil.loadClientSchema());
		view.getAttributesMap().put("page","search");	
		return view;
  	}
	
	@ModelAttribute("ENTITYPE")
	public Map<String, Integer> EntityTypes(){
		final EntityTypes[] enums = EntityTypes.class.getEnumConstants();  
		Map<String, Integer> map = new HashMap<String, Integer>(enums.length);	
		for (EntityTypes anEnum : enums)
			map.put(anEnum.toString(), anEnum.getValue());
	
		return map;
	}
	@ModelAttribute("PERMISSION")
	public Map<String, Integer> Permission(Model model){
		final Permissions[] enums = Permissions.class.getEnumConstants();  
		Map<String, Integer> map = new HashMap<String, Integer>(enums.length);	
		for (Permissions anEnum : enums){
			map.put(anEnum.toString(), anEnum.getValue());
			model.addAttribute(anEnum.toString(),anEnum.getValue());
		}	
	
		return map;
	}

	/**;
	 * @param entityeManager the entityManager to set
	 */
	@Autowired
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public List<Entity> filterEntityAttributes(List<Entity> entList){
		List<Entity> attFilterEntities = new ArrayList<Entity>();
		try{
			if(entList != null){
				for(Entity entity:entList){
					// filter the attributes users can't see
					List<AttributeValueStorage> attStorage = entityTypeManager.filterAttributesValues(entity.getAttributeValueStorage());
		    		if(attStorage != null && attStorage.size() > 0){
		    			entity.setAttributeValueStorage(attStorage);
		        		attFilterEntities.add(entity);
		    		}	
		    	}
			}
		}catch(Exception e){
			logger.error("Error while filtering attributes in Entity" +e);
			e.printStackTrace();
		}
		return attFilterEntities;
	}
}
