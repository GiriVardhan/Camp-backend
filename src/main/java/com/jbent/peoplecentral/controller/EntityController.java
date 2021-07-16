/**
 * 
 */
package com.jbent.peoplecentral.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.http.ResponseEntity;

import com.csvreader.CsvReader;
import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.exception.AttributeValueNotValidException;
import com.jbent.peoplecentral.exception.ConfigException;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.exception.DataExceptionRT;
import com.jbent.peoplecentral.model.manager.EntityManager;
import com.jbent.peoplecentral.model.manager.EntityTypeManager;
import com.jbent.peoplecentral.model.manager.FileManager;
import com.jbent.peoplecentral.model.pojo.Attribute;
import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;
import com.jbent.peoplecentral.model.pojo.CompletePermissions;
import com.jbent.peoplecentral.model.pojo.Entity;
import com.jbent.peoplecentral.model.pojo.EntityStatus;
import com.jbent.peoplecentral.model.pojo.EntityType;
import com.jbent.peoplecentral.model.pojo.ExportMap;
import com.jbent.peoplecentral.model.pojo.Exportable;
import com.jbent.peoplecentral.model.pojo.Attribute.DataType;
import com.jbent.peoplecentral.model.pojo.Attribute.FieldType;
import com.jbent.peoplecentral.model.pojo.AttributeFileStorage;
import com.jbent.peoplecentral.model.pojo.EntityType.EntityTypes;
import com.jbent.peoplecentral.model.rowmapper.EntityAuditMapper;
import com.jbent.peoplecentral.model.rowmapper.EntityMapper;
import com.jbent.peoplecentral.permission.Permission.Permissions;
import com.jbent.peoplecentral.template.TemplateManager;
import com.jbent.peoplecentral.util.FileUploadHelperUtil;
import com.jbent.peoplecentral.util.MimeTypeConstants;
import com.jbent.peoplecentral.velocity.EntityVelocityUtil;
import com.jbent.peoplecentral.velocity.VelocityManager;
import com.jbent.peoplecentral.velocity.viewtool.EntityViewTool;
import com.jbent.peoplecentral.web.SessionContext;
import com.jbent.peoplecentral.model.pojo.Attribute.Attributes;

/**
 * @author RaviT
 *
 */
@SuppressWarnings("unchecked")
@Controller
public class EntityController extends WebApplicationObjectSupport {
	
	//private static final String XML_VIEW_NAME = null;
	private EntityManager entityManager;
	private EntityTypeManager entityTypeManager;
	//private Validator entityValidator;
	@Autowired
	private MessageSource messageSource;
	boolean isError = false;
	private VelocityManager velocityManager;
	private TemplateManager templateManager;
	@Autowired
	private Exportable exportable;
	
	@RequestMapping(value = {"/entity/add/{entitytypeid}","/*/entity/add/{entitytypeid}"}, method = RequestMethod.POST)
    public MappingJackson2JsonView loadAttributes(@PathVariable long entitytypeid,HttpServletRequest request,@ModelAttribute("entity")Entity entity,BindingResult result,Locale locale){
		MappingJackson2JsonView view = new MappingJackson2JsonView();	
		String baseEntityIdToRelateNew = "";
		if(request.getParameter("baseEntityIdToRelateNewTT") != null){
			baseEntityIdToRelateNew = request.getParameter("baseEntityIdToRelateNewTT");
			view.getAttributesMap().put("baseEntityIdToRelateNew", baseEntityIdToRelateNew);
	    }
		String newEntityRelationNameTT = "";
		if(request.getParameter("newEntityRelationNameTT") != null){
			newEntityRelationNameTT = request.getParameter("newEntityRelationNameTT");
			view.getAttributesMap().put("newEntityRelationName", newEntityRelationNameTT);
		}
		if(request.getParameter("parentFolderId") != null){
			Long parentFolderId = Long.parseLong(request.getParameter("parentFolderId"));
			try {
				view.getAttributesMap().put("parentFolderEntity", entityManager.loadEntity(parentFolderId));
				view.getAttributesMap().put("parentFolderId", parentFolderId);
			} catch (DataException e) {
				e.printStackTrace();
			}
	    }
		List<Attribute> attributeList = new ArrayList<Attribute>();
	    List<AttributeValueStorage> avsList = new ArrayList<AttributeValueStorage>();
	    AttributeValueStorage avs = null;
		EntityType et = null;
		try {
			if(entitytypeid >0 ){
				
			}
			et = entityTypeManager.load(entitytypeid);
			if(et != null){
				attributeList = et.getAttributes();
				if(attributeList != null && attributeList.size()>0){
					for(Attribute attribute:attributeList){
						avs = new AttributeValueStorage();
						try {
							BeanUtils.copyProperties(avs, attribute);
						} catch (IllegalAccessException e) {
							logger.error(e.getMessage(),e);
						} catch (InvocationTargetException e) {
							logger.error(e.getMessage(),e);
						}
						avs.setEntityTypeId(entitytypeid);
						avsList.add(avs);
					}
				}else{
					isError= true;
					logger.error("loadAttributes:- No Attributes found :"+attributeList);
					view.getAttributesMap().put("errorMsg",messageSource.getMessage("error.entitytype.attributes.empty", null, locale));
					throw new DataException("loadAttributes:- No Attributes found:"+attributeList);
				}
				entity.setEntityType(et);
				entity.setEntityTypeId(entitytypeid);
				//Filtering Attribute list
				List<AttributeValueStorage> attStorage = entityTypeManager.filterAttributesValues(avsList);				
				entity.setAttributeValueStorage(attStorage);    
			}	
			else{
				logger.error("EntityController.loadAttributes:- EntityType NOT found for entityTypeID:"+entitytypeid);
				throw new DataException("loadAttributes:- EntityType NOT found for entityTypeID:"+entitytypeid);
				
			}
		} catch (DataException e) {
			logger.error("loadAttributes:-Error loading entity types : " + e.getMessage(),e);
			// redirect the user safely to entity/add page
		}catch (Exception e) {
			isError= true;
			logger.error("loadAttributes:-Error loading entity types : " + e.getMessage(),e);
			view.getAttributesMap().put("errorMsg",messageSource.getMessage("error.entitytype.attributes.empty", null, locale));
			// redirect the user safely to entity/add page
		}
		
        view.getAttributesMap().put("entity", entity); 
        view.getAttributesMap().put("isEntityValid", "entity.notcreated");
        view.getAttributesMap().put("error", "noerror");
        view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
        view.getAttributesMap().put(TemplateManager.COCOAOWL_TEMPLATE_EDIT_MODE_CONSTANT, TemplateManager.COCOAOWL_TEMPLATE_EDIT_MODE_CONSTANT);
        if(entity != null){
        	String detailMarkup = null;
			try {
				detailMarkup = templateManager.mergeAsString("/" + ClientManageUtil.loadClientSchema() + "/type/" + entity.getEntityTypeId() + ".vtl", view.getAttributesMap());
			} catch (ConfigException e) {
				e.printStackTrace();
			}
        	
        	if(detailMarkup != null && detailMarkup.length() > 0)
			view.getAttributesMap().put("detailMarkup",detailMarkup);
        }
        return view;
    }
	
	@RequestMapping(value = {"/entity/front/end/view/{entityId}","/*/entity/front/end/view/{entityId}"}, method = RequestMethod.GET)
    public MappingJackson2JsonView frontEndViewEntity(@PathVariable long entityId){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		List<AttributeValueStorage> avs =null,assignedRoles = null,assignedUsers = null;;
		Entity entity = new Entity();
		try {
			entity = entityManager.loadEntity(entityId);
			if(entity != null){
				EntityVelocityUtil.populateEntity(entity, view.getAttributesMap());				
				view.getAttributesMap().put("entity", entity);
				view.getAttributesMap().put("assignedRoles", assignedRoles); 
				view.getAttributesMap().put("assignedUsers", assignedUsers); 
		        view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
				view.getAttributesMap().put("detailMarkup",templateManager.mergeAsString("/" + ClientManageUtil.loadClientSchema() + "/type/" + entity.getEntityTypeId() + ".vtl", view.getAttributesMap()));
			}
		} catch (DataException e) {
			logger.error("Error loading entity : " + e.getMessage(),e);
		}catch (Exception e) {
			logger.error("Error loading entity : " + e.getMessage(),e);
		}
  	    return view;
        
    }
	
	@RequestMapping(value = {"/entity/view/{entityId}","/*/entity/view/{entityId}"}, method = RequestMethod.GET)
    public MappingJackson2JsonView loadEntity(@PathVariable long entityId, HttpSession session){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		List<CompletePermissions> combinedRolesForEntity = null;
		List<AttributeValueStorage> avs =null,assignedRoles = null,assignedUsers = null;;
		Entity entity = new Entity();
		try {
			entity = entityManager.loadEntity(entityId);
			if(entity != null){
				avs = entity.getAttributeValueStorage();
				if(avs != null && avs.size()>0){
					//entity.setAttributeValueStorage(entity.getAttributeValueStorage());
					// loadAssigned Roles only if EntityType is PEOPLE
					if(entity.getEntityTypeId() == EntityTypes.PEOPLE.getValue()){
						assignedRoles =entityManager.loadAssignedRoles(entity);
					}else if(entity.getEntityTypeId() == EntityTypes.ROLE.getValue()){
						assignedUsers =entityManager.loadAssignedUsers(entity);
					}	
					//sanitize Role Name
					for(AttributeValueStorage value:avs){
						if(value.getId() == Attributes.ROLENAME.getValue()){
							value.setValueVarchar(entityManager.sanitizeRole(value.getValueVarchar()));
						}						
						break;
					}
				}else{
					logger.error("EntityController.loadEntity:- avs should not be null:"+avs);
				}
				// retrieve the All roles permissions on the Entity
				combinedRolesForEntity = entityManager.retrieveCombinedRolesForEntity(entity);
				if(combinedRolesForEntity != null){
					view.getAttributesMap().put("combinedRolesForEntity",combinedRolesForEntity);
					view.getAttributesMap().put("rolesCount",combinedRolesForEntity.size());
				}	
				List<Entity> relationsFrom = entityManager.searchRelations(entityId, true);
				List<Entity> relationsTo = entityManager.searchRelations(entityId, false);
				
				if(relationsFrom != null && relationsFrom.size() > 0)
					view.getAttributesMap().put("relationsFrom", relationsFrom);
				else
					view.getAttributesMap().put("relationsFrom", new ArrayList<Entity>());
				
				if(relationsTo != null && relationsTo.size() > 0)
					view.getAttributesMap().put("relationsTo", relationsTo);
				else
					view.getAttributesMap().put("relationsTo", new ArrayList<Entity>());
			}
		} catch (DataException e) {
			logger.error("Error loading entity : " + e.getMessage(),e);
		}catch (Exception e) {
			logger.error("Error loading entity : " + e.getMessage(),e);
		}			
		String postStatus = (String) session.getAttribute("postStatus");
		if(postStatus != "" && postStatus != null){
			view.getAttributesMap().put("postStatus", postStatus);
			session.removeAttribute("postStatus");
		}
		view.getAttributesMap().put("entity", entity);
		view.getAttributesMap().put("assignedRoles", assignedRoles); 
		view.getAttributesMap().put("assignedUsers", assignedUsers); 
        view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
        
        try {
        	EntityVelocityUtil.populateEntity(entity, view.getAttributesMap());
        	String detailMarkup = templateManager.mergeAsString("/" + ClientManageUtil.loadClientSchema() + "/type/" + entity.getEntityTypeId() + ".vtl", view.getAttributesMap());
			if(detailMarkup != null && detailMarkup.length() > 0)
				view.getAttributesMap().put("detailMarkup",detailMarkup);
		} catch (Exception e) {
			logger.error("Unable to parse velocity : " + e.getMessage(),e);
		}
 	   return view;
        
    }
	@RequestMapping(value = {"/entity/edit/{entityId}","/*/entity/edit/{entityId}"}, method = RequestMethod.GET)
    public MappingJackson2JsonView editEntity(@PathVariable long entityId,Locale locale ){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		List<CompletePermissions> combinedRolesForEntity = null;
		boolean hasAttibuteValue =false;
		List<AttributeValueStorage> avs =null,assignedRoles = null;
		Entity entity = new Entity();
		try {
			entity = entityManager.loadEntity(entityId);
			if(entity != null){
				avs = entity.getAttributeValueStorage();
				List<Attribute> attributes = entity.getEntityType().getAttributes();
				if(attributes != null && attributes.size()>0){
					for(Attribute att:attributes){
						hasAttibuteValue = false;
						for(AttributeValueStorage value:avs){
							if(att.getId() == value.getId()){	
								if(att.getId() == Attributes.ROLENAME.getValue()){
									value.setValueVarchar(entityManager.sanitizeRole(value.getValueVarchar()));
								}
								hasAttibuteValue= true;
								break;
							}
						}
						if(!hasAttibuteValue){
							AttributeValueStorage attributeValue= new AttributeValueStorage();
							attributeValue.setId(att.getId());
							attributeValue.setName(att.getName());
							attributeValue.setName(att.getName());
							attributeValue.setFieldTypeId(att.getFieldTypeId());
							attributeValue.setFieldTypeName(att.getFieldTypeName());
							attributeValue.setDataTypeId(att.getFieldTypeId());
							attributeValue.setDataTypeName(att.getDataTypeName());
							avs.add(attributeValue);
						}
					}
				}
				if(avs != null && avs.size()>0){
					//entity.setAttributeValueStorage(entity.getAttributeValueStorage());
					// loadAssigned Roles only if EntityType is PEOPLE
					if(entity.getEntityTypeId() == EntityTypes.PEOPLE.getValue()){
						assignedRoles =entityManager.loadAssignedRoles(entity);
					}else if(entity.getEntityTypeId() == EntityTypes.ROLE.getValue()){
						assignedRoles =entityManager.loadAssignedUsers(entity);
					}	
				}else{
					logger.error("EntityController.loadEntity:- avs should not be null:"+avs);
				}
//				// retrieve the All roles permissions on the Entity
				combinedRolesForEntity = entityManager.retrieveCombinedRolesForEntity(entity);
				if(combinedRolesForEntity != null){
					view.getAttributesMap().put("combinedRolesForEntity",combinedRolesForEntity);
					view.getAttributesMap().put("rolesCount",combinedRolesForEntity.size());
				}
				List<Entity> relationsFrom = entityManager.searchRelations(entityId, true);
				List<Entity> relationsTo = entityManager.searchRelations(entityId, false);
				
				if(relationsFrom != null && relationsFrom.size() > 0)
					view.getAttributesMap().put("relationsFrom", relationsFrom);
				else
					view.getAttributesMap().put("relationsFrom", new ArrayList<Entity>());
				
				if(relationsTo != null && relationsTo.size() > 0)
					view.getAttributesMap().put("relationsTo", relationsTo);
				else
					view.getAttributesMap().put("relationsTo", new ArrayList<Entity>());
				
				view.getAttributesMap().put(TemplateManager.COCOAOWL_TEMPLATE_EDIT_MODE_CONSTANT, TemplateManager.COCOAOWL_TEMPLATE_EDIT_MODE_CONSTANT);
				//Filtering Attribute list
				List<AttributeValueStorage> attStorage = entityTypeManager.filterAttributesValues(entity.getAttributeValueStorage());
				entity.setAttributeValueStorage(attStorage);
				
			}else{
				logger.error("EntityController.loadEntity:- Entity should not be null:"+entity);
			}
		} catch (DataException e) {
			isError= true;
			logger.error("Error loading entity : " + e.getMessage(),e);
			view.getAttributesMap().put("errorMsg",messageSource.getMessage("error.entitytype.attributes.empty", null, locale));
		}catch (Exception e) {
			logger.error("Error loading entity : " + e.getMessage(),e);
		}			
		view.getAttributesMap().put("entity", entity);
		view.getAttributesMap().put("action", "edit");
		view.getAttributesMap().put("assignedRoles", assignedRoles); 
        view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
        if(entity != null){
        	String detailMarkup = null;
			try {
				detailMarkup = templateManager.mergeAsString("/" + ClientManageUtil.loadClientSchema() + "/type/" + entity.getEntityTypeId() + ".vtl", view.getAttributesMap());
			} catch (ConfigException e) {
				e.printStackTrace();
			}
        	
        	if(detailMarkup != null && detailMarkup.length() > 0)
			view.getAttributesMap().put("detailMarkup",detailMarkup);
        }
 	   return view;
        
    }
	
	@RequestMapping(value = {"/events/calendar/view","/*/events/calendar/view"}, method = RequestMethod.GET)
    public MappingJackson2JsonView viewEvents(Locale locale ){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return view;
	}
	
	@RequestMapping(value = {"/entity/remove/{entityId}","/*/entity/remove/{entityId}"}, method = RequestMethod.POST)
    public @ResponseBody void removeInvalidEntity(@PathVariable long entityId){
		try {
			 entityManager.removeEntity(entityId);
		} catch (DataException e) {
			logger.error("Error in removing entity : " + e.getMessage(),e);
		}         
    }
	
	public long attributeQuickSave(long attributeId, long entityId, String valueToSave) throws Exception{
		long newEntityId = 0;
		if(attributeId == 0)
			return 0;
		Attribute attributeToSave = entityTypeManager.loadAttribute(attributeId);
		EntityType entityType = entityTypeManager.load(attributeToSave.getEntityTypeId());
		List<Attribute> attributesList = entityType.getAttributes();
		List<AttributeValueStorage> attributeValueStorageList = new ArrayList<AttributeValueStorage>();
		
		if(entityId > 0){
			
			boolean isEntityAttributePopulated;
			Entity entityToUpdate = entityManager.loadEntity(entityId);
			List<AttributeValueStorage> entityToUpdateAVSList = entityToUpdate.getAttributeValueStorage();
			List<Attribute> entityTypeAttributes = entityToUpdate.getEntityType().getAttributes();
			
			if(entityTypeAttributes != null && entityTypeAttributes.size()>0){
				
				for(Attribute entityTypeAttribute:entityTypeAttributes){
					
					isEntityAttributePopulated = false;
					for(AttributeValueStorage entityToUpdateAVS : entityToUpdateAVSList){
						if(entityTypeAttribute.getId() == entityToUpdateAVS.getId()){
							isEntityAttributePopulated= true;
							break;
						}
					}
					if(!isEntityAttributePopulated){
						AttributeValueStorage newAVSToPopulate = new AttributeValueStorage();
						newAVSToPopulate.setId(entityTypeAttribute.getId());
						newAVSToPopulate.setName(entityTypeAttribute.getName());
						newAVSToPopulate.setFieldTypeId(entityTypeAttribute.getFieldTypeId());
						newAVSToPopulate.setFieldTypeName(entityTypeAttribute.getFieldTypeName());
						newAVSToPopulate.setDataTypeId(entityTypeAttribute.getFieldTypeId());
						newAVSToPopulate.setDataTypeName(entityTypeAttribute.getDataTypeName());
						entityToUpdateAVSList.add(newAVSToPopulate);
					}
				}
			}

			for(AttributeValueStorage avs : entityToUpdateAVSList){
				
				if(avs.getId() == attributeId){
					
					attributeValueStorageList.add(avs);
					avs.setEntityId(entityId);
					switch (avs.getDataTypeName()) {
					case  "date" :
						avs.setValueDate(new SimpleDateFormat("dd-MMM-yyyy").parse(valueToSave));
						break;
					case "long" :
						avs.setValueLong(Long.parseLong(valueToSave));
						avs.setValue_Long(valueToSave);
						break;
					case "text" :
						avs.setValueText(valueToSave);
						break;
					case "time" :
						avs.setValueTime(null);// TODO
						break;
					case "varchar" :
						avs.setValueVarchar(valueToSave);
						break;
					default:
						break;
					}
					
					newEntityId = entityManager.avsSave(entityToUpdate, avs).getEntityId();//quickSave(entityToUpdate, avsList);
					
				}
			}
		}else{
			Entity newEntity = new Entity();
			for(Attribute attr : attributesList){
				AttributeValueStorage avs = new AttributeValueStorage();
				BeanUtils.copyProperties(avs, attr);
				attributeValueStorageList.add(avs);
			}
			newEntity.setEntityType(entityType);
			newEntity.setEntityTypeId(entityType.getId());
			newEntity.setAttributeValueStorage(attributeValueStorageList);
			
			for(AttributeValueStorage avs : newEntity.getAttributeValueStorage()){
				if(avs.getId() == attributeId){
					switch (avs.getDataTypeName()) {
					case  "date" :
						avs.setValueDate(new SimpleDateFormat("dd-MMM-yyyy").parse(valueToSave));
						break;
					case "long" :
						avs.setValueLong(Long.parseLong(valueToSave));
						break;
					case "text" :
						avs.setValueText(valueToSave);
						break;
					case "time" :
						avs.setValueTime(null);// TODO
						break;
					case "varchar" :
						avs.setValueVarchar(valueToSave);
						break;
					default:
						break;
					}
					newEntityId = entityManager.avsSave(newEntity, avs).getEntityId();
				}
			}
		}
	return newEntityId;

	}
//entityStatus = entityManager.avsSave(entity,avs);
	@RequestMapping(value = {"/entity/quick/save/","/*/entity/quick/save/"}, method = RequestMethod.GET)
	public View quickSave(HttpServletRequest request, HttpServletResponse response) throws Exception{
		long entityId = 0;
		long attributeId = 0;
		long newEntityId = 0;
		String valueToSave = request.getParameter("value");
		try{
			if(request.getParameter("entityId") != null)
				entityId = Long.parseLong(request.getParameter("entityId"));
		attributeId = Long.parseLong(request.getParameter("attributeId"));
		
		}catch(NumberFormatException e){
			
		}
		newEntityId = attributeQuickSave(attributeId, entityId, valueToSave);
		MappingJackson2JsonView result = new MappingJackson2JsonView();
		result.getAttributesMap().put("newEntityId", newEntityId);
	return result;

	}
	@RequestMapping(value = {"/entity/save/auto","/*/entity/save/auto"}, method = RequestMethod.POST)
	public View autoSave(
			@RequestParam("date")String date,
			@RequestParam("time")String time,
			@RequestParam("aId")long attributeId,
			@RequestParam("entityId")long entityId,
			@RequestParam("entityValid")String entityValid,
			@RequestParam("wyswyg")String wyswyg, 
			HttpServletRequest request, 
			@ModelAttribute("entity")Entity entity, 
			BindingResult result, 
			Locale locale) {
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		boolean isError = false;
		DateFormat formatter ;
		String attributeName = "";
		EntityStatus entityStatus = new EntityStatus();
		try {
			//Explicitly setting only (DOJO.Date and DOJO.Time) values to the form object.
			if(entity != null && attributeId > 0){
				List<AttributeValueStorage> avsList = entity.getAttributeValueStorage();
				if(avsList != null && avsList.size() > 0){
					for(AttributeValueStorage avs : avsList){						
						if(avs.getId()== attributeId ){
							attributeName = avs.getName();
			    			if(entityId > 0 && entityValid.equalsIgnoreCase("true")){
			    				avs.setEntityId(entityId);
			    				entityStatus.setEntityId(entityId);
			    				entityStatus.setEntityValid(true);
			    			}else{
			    				avs.setEntityId(entityId);
			    				entityStatus.setEntityId(entityId);
			    				entityStatus.setEntityValid(false);
			    			}
			    			if(avs.getValueVarchar() != null){
								avs.setValueVarchar((avs.getValueVarchar().trim()));
							}
			    			if(wyswyg != ""  && avs.getFieldTypeName().equalsIgnoreCase(FieldType.WYSIWYG.toString())){
								avs.setValueText((wyswyg.trim()));
							}
				    		if(avs.getFieldTypeName().equalsIgnoreCase(FieldType.NUMERIC.toString())){
			    				avs.setValueLong(new Long(avs.getValue_Long().trim()));
			    			}
			    			else if(avs.getFieldTypeName().equalsIgnoreCase(FieldType.DATE.toString())){
			    				if(date != ""){
			    					formatter = new SimpleDateFormat("MM/dd/yyyy");
			    					avs.setValueDate(formatter.parse(date));
			    				}
			    			}else if(avs.getFieldTypeName().equalsIgnoreCase(FieldType.TIME.toString())){
			    				if(time != ""){
			    					formatter = new SimpleDateFormat("hh:mm a");
			    					java.sql.Time timeValue = new java.sql.Time(formatter.parse(time).getTime());
			    					avs.setValueTime(timeValue);
			    				}
			    			}else if(avs.getFieldTypeName().equalsIgnoreCase(FieldType.FILE.toString()) || avs.getFieldTypeName().equalsIgnoreCase(FieldType.IMAGE.toString())){
			    				entityStatus.setEntityId(entity.getEntityId());	
			    				break;
			    			}
				    		
				    		entity.setEntityType(entityTypeManager.load(entity.getEntityTypeId()));
				    		
			    			avs.setEntityTypeId(entity.getEntityTypeId());
			    			//avs.setValue_Time(value_Time);
							entityStatus = entityManager.avsSave(entity,avs);
							
							if(entityStatus.getEntityId()> 0){
								view.getAttributesMap().put("valueSaved", messageSource.getMessage("entity.attribute.value.saved", null, locale));
							}else{
								view.getAttributesMap().put("valueSaved", messageSource.getMessage("error.entity.attribute.value.notsaved", null, locale));
							}
							break;
			    		}
			    	}
				}else{
					isError= true;
					logger.error("EntityController.quickSave:- avsList contains atleast 1 AttributeValueStorage Object:"+avsList);
					throw new DataExceptionRT(attributeName+":"+messageSource.getMessage("error.entity.attribute.value.notsaved", null, locale));
				}
			}else{
				isError= true;
				logger.error("EntityController.quickSave:- Couldn't process this request if entity is:"+entity+", and attributeId is :"+attributeId);
				throw new DataExceptionRT(attributeName+":"+messageSource.getMessage("error.entity.attribute.value.notsaved", null, locale));
			}
		} catch (AttributeValueNotValidException e) {
			isError= true;
			view.getAttributesMap().put("errorMsg", attributeName+":"+messageSource.getMessage("error.attribute.value.notvalid", null, locale));
			logger.error("Error saving Value : " + e.getMessage());
			e.printStackTrace();
		} catch (ParseException e) {
			isError= true;
			view.getAttributesMap().put("errorMsg", attributeName+":"+messageSource.getMessage("error.attribute.value.notvalid", null, locale));
			logger.error("Error saving date value  : " + e.getMessage());
			e.printStackTrace();
		}catch(Exception e){
			isError= true;
			view.getAttributesMap().put("errorMsg",attributeName+":"+messageSource.getMessage("error.attribute.value.notvalid", null, locale));
			logger.error("Error saving Entity  : " + e.getMessage());
			e.printStackTrace();
		}finally{
			if(isError)	view.getAttributesMap().put("isError", "true");
			else view.getAttributesMap().put("isError", "false");
		}
		if(!entityStatus.isEntityValid()){
			view.getAttributesMap().put("entityValid", messageSource.getMessage("error.entity.notvalid", null, locale)+":"+entityStatus.getEntityId());
		}else{
			String newEntityRelationName = "";
			view.getAttributesMap().put("entityValid", messageSource.getMessage("entity.valid", null, locale)+":"+entityStatus.getEntityId());
			String baseEntityIdToRelateNew = "0";
			if(request.getParameter("baseEntityIdToRelateNew") != null && !request.getParameter("baseEntityIdToRelateNew").equals("")){
				baseEntityIdToRelateNew = request.getParameter("baseEntityIdToRelateNew");
		    }else if(request.getParameter("parentFolderId") != null && request.getParameter("parentFolderId") != ""){
		    	newEntityRelationName = "SubFolder";
		    	baseEntityIdToRelateNew = request.getParameter("parentFolderId");
		    }
			if(request.getParameter("newEntityRelationName") != null && !request.getParameter("newEntityRelationName").equals("")){
				newEntityRelationName = request.getParameter("newEntityRelationName");
				view.getAttributesMap().put("newEntityRelationName", newEntityRelationName);
			}
			if(Long.parseLong(baseEntityIdToRelateNew) > 0)
				saveRelation(Long.parseLong(baseEntityIdToRelateNew), entityStatus.getEntityId(), newEntityRelationName, view);
		}
		view.getAttributesMap().put("entityId", entityStatus.getEntityId());
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());		
		view.getAttributesMap().put("entityStatus", entityStatus);
		return new MappingJackson2JsonView();
	}
	
	
	@RequestMapping(value = {"/entity/quickSave/auto","/*/entity/quickSave/auto"},
			method = RequestMethod.POST)
	public ResponseEntity<?>  quickSave(
			@RequestParam("date")String dateOrTime,
			@RequestParam("aId")long attributeId,
			@RequestParam("entityId")long entityId,
			@RequestParam("entityValid")String entityValid,
			@RequestParam("wyswyg")String wyswyg, 
			@ModelAttribute("entity")Entity entity, 
			BindingResult result, 
			Locale locale,Model model) {
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		boolean isError = false;
		DateFormat formatter ;
		String attributeName = "";
		EntityStatus entityStatus = new EntityStatus();
		try {
			//Explicitly setting only (DOJO.Date and DOJO.Time) values to the form object.
			if(entity != null && attributeId > 0){
				List<AttributeValueStorage> avsList = entity.getAttributeValueStorage();
				if(avsList != null && avsList.size() > 0){
					for(AttributeValueStorage avs : avsList){
						if(avs.getId()== attributeId ){
							attributeName = avs.getName();
			    			if(entityId > 0 && entityValid.equalsIgnoreCase("true")){
			    				avs.setEntityId(entityId);
			    				entityStatus.setEntityId(entityId);
			    				entityStatus.setEntityValid(true);
			    			}else{
			    				avs.setEntityId(entityId);
			    				entityStatus.setEntityId(entityId);
			    				entityStatus.setEntityValid(false);
			    			}
			    			if(avs.getValueVarchar() != null){
								avs.setValueVarchar((avs.getValueVarchar().trim()));
							}
			    			if(wyswyg != ""){
								avs.setValueText((wyswyg.trim()));
							}
				    		if(avs.getFieldTypeName().equalsIgnoreCase(FieldType.NUMERIC.toString())){
			    				avs.setValueLong(new Long(avs.getValue_Long().trim()));
			    			}
			    			else if(avs.getFieldTypeName().equalsIgnoreCase(FieldType.DATE.toString())){
			    				if(dateOrTime != ""){
			    					formatter = new SimpleDateFormat("MM/dd/yyyy");
			    					avs.setValueDate(formatter.parse(dateOrTime));
			    				}
			    			}else if(avs.getFieldTypeName().equalsIgnoreCase(FieldType.TIME.toString())){
			    				if(dateOrTime != ""){
			    					formatter = new SimpleDateFormat("HH:mm a");
			    					avs.setValueDate(formatter.parse(dateOrTime));
			    				}
			    			}else if(avs.getFieldTypeName().equalsIgnoreCase(FieldType.FILE.toString()) || avs.getFieldTypeName().equalsIgnoreCase(FieldType.IMAGE.toString())){
			    				entityStatus.setEntityId(entity.getEntityId());	
			    				break;
			    			}
			    			avs.setEntityTypeId(entity.getEntityTypeId());
							entityStatus = entityManager.avsSave(entity,avs);
							
							if(entityStatus.getEntityId()> 0){
								model.addAttribute("valueSaved", messageSource.getMessage("entity.attribute.value.saved", null, locale));
							}else{
								model.addAttribute("valueSaved", messageSource.getMessage("error.entity.attribute.value.notsaved", null, locale));
							}
							break;
			    		}
			    	}
				}else{
					isError= true;
					logger.error("EntityController.quickSave:- avsList contains atleast 1 AttributeValueStorage Object:"+avsList);
					throw new DataExceptionRT(attributeName+":"+messageSource.getMessage("error.entity.attribute.value.notsaved", null, locale));
				}
			}else{
				isError= true;
				logger.error("EntityController.quickSave:- Couldn't process this request if entity is:"+entity+", and attributeId is :"+attributeId);
				throw new DataExceptionRT(attributeName+":"+messageSource.getMessage("error.entity.attribute.value.notsaved", null, locale));
			}
		} catch (AttributeValueNotValidException e) {
			isError= true;
			model.addAttribute("errorMsg", attributeName+":"+messageSource.getMessage("error.attribute.value.notvalid", null, locale));
			logger.error("Error saving Value : " + e.getMessage());
			e.printStackTrace();
		} catch (ParseException e) {
			isError= true;
			model.addAttribute("errorMsg", attributeName+":"+messageSource.getMessage("error.attribute.value.notvalid", null, locale));
			logger.error("Error saving date value  : " + e.getMessage());
			e.printStackTrace();
		}catch(Exception e){
			isError= true;
			model.addAttribute("errorMsg",attributeName+":"+messageSource.getMessage("error.attribute.value.notvalid", null, locale));
			logger.error("Error saving Entity  : " + e.getMessage());
			e.printStackTrace();
		}finally{
			if(isError)	model.addAttribute("isError", "true");
			else model.addAttribute("isError", "false");
		}
		if(!entityStatus.isEntityValid())model.addAttribute("entityValid", messageSource.getMessage("error.entity.notvalid", null, locale)+":"+entityStatus.getEntityId());
		else model.addAttribute("entityValid", messageSource.getMessage("entity.valid", null, locale)+":"+entityStatus.getEntityId());
		model.addAttribute("entityId", entityStatus.getEntityId());
		model.addAttribute("client", ClientManageUtil.loadClientSchema());		
		model.addAttribute("entityStatus", entityStatus);
		return ResponseEntity.ok(entityStatus);
	}
	
	@RequestMapping(value = {"/entity/foldersync/update/time","/*/entity/foldersync/update/time"}, method = RequestMethod.GET)
	public View updateFolderSyncTime(HttpServletRequest request) throws AttributeValueNotValidException, DataException {
		String username = request.getParameter("username");
		//**Query String: type:People Username:[jbent]
		EntityViewTool ev = new EntityViewTool();
		List<Entity> entities = ev.searchEntities("type:FolderSyncDetails Username:["+ username +"]");// entityManager.entitySearch(, EntityType.EntityTypes.PEOPLE.getValue(), "<p>", "</p>", 1000,0);
		Entity userEntity = null;
		for(Entity e : entities){
			if(e.getValueString("Username").equals(username)){
				userEntity = e;
				break;
			}
		}
		List<Attribute> attributeList = new ArrayList<Attribute>();
	    AttributeValueStorage avs = null;
	    EntityType entityType = userEntity.getEntityType();
		attributeList = entityType.getAttributes();
		for(Attribute attribute:attributeList){
			if(attribute.getName().equals("Syncedon")){
				avs = new AttributeValueStorage();
				try {
					BeanUtils.copyProperties(avs, attribute);
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage(),e);
				} catch (InvocationTargetException e) {
					logger.error(e.getMessage(),e);
				}
				avs.setEntityId(userEntity.getEntityId());
				avs.setEntityTypeId(entityType.getId());
				SimpleDateFormat sdf = new SimpleDateFormat("dd_MMM_yyyy_HH_mm_ss");
				avs.setValueVarchar(sdf.format(new Date()));
				entityManager.avsSave(userEntity,avs);
			}
		}
		return new MappingJackson2JsonView();
	}

	@RequestMapping(value = {"/entity/pull/events","/*/entity/pull/events"}, method = RequestMethod.GET)
	public View pullEventsOfMonth(@RequestParam("startDate") String startDateStr,@RequestParam("endDate") String endDateStr,Locale locale) {
		MappingJackson2JsonView view = new MappingJackson2JsonView();	
		List<Entity> result1 = new ArrayList<Entity>();
		List<Entity> result = new ArrayList<Entity>();
		List<Map<String, String>> eventMapList = new ArrayList<Map<String, String>>();
		LocalDate startDate = LocalDate.parse(startDateStr);
		LocalDate endDate = LocalDate.parse(endDateStr);
		try {
			result1 = entityManager.pullEventsInRange(startDate,endDate);
		} catch (DataException e) {
			e.printStackTrace();
		}
		if(result1 != null){
			for(Entity e : result1){
				Map<String, String> eventMap = new HashMap<String,String>();
				for(AttributeValueStorage avs : e.getAttributeValueStorage()){
					eventMap.put(avs.getName(), avs.getValue().toString());
					if(avs.getName().equalsIgnoreCase("Date")){
						LocalDate eventDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(avs.getValueDate()));
						eventMap.put("day", eventDate.getDayOfMonth()+"");
						eventMap.put("month", eventDate.getMonth()+"");
						eventMap.put("year", eventDate.getYear()+"");
					}
				}
				eventMap.put("entityId", e.getEntityId()+"");
				eventMapList.add(eventMap);
			}
			view.getAttributesMap().put("result",result1);
		}
		else
			view.getAttributesMap().put("result",result);
		view.getAttributesMap().put("eventMapList", eventMapList);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return new MappingJackson2JsonView();
	}

	@RequestMapping(value = {"/entity/list","/*/entity/list"}, method = RequestMethod.GET)
    public void listEntities(){
		MappingJackson2JsonView view = new MappingJackson2JsonView();	
		List<Entity> entities = null;
		try {
			entities = entityManager.load();
		} catch (DataException e) {
			logger.error("EntityController.listEntities:-Unable to load listEntitites : "+e.getMessage(),e);
		}catch (Exception e) {
			logger.error("EntityController.listEntities:-Unable to load listEntitites : "+e.getMessage(),e);
		}
		view.getAttributesMap().put("entities", entities);
	}
	
		
	@RequestMapping(value = {"/test/submit","/*/test/submit"}, method = RequestMethod.POST)
	public View test(@RequestParam("importFile0") MultipartFile file,HttpServletRequest request) {
		MappingJackson2JsonView view = new MappingJackson2JsonView();	
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return new MappingJackson2JsonView();
	}
	
//	@RequestMapping(value = {"/test/submit","/*/test/submit"}, method = RequestMethod.POST)
//	public View test(Model model, HttpServletRequest request) throws DataException{
//		return new MappingJackson2JsonView();
//	}
	
	@RequestMapping(value = {"/entity/search/export","/*/entity/search/export"}, method = RequestMethod.POST)
	public void searchExport(@RequestParam("page")long page,@RequestParam("searchTerm") String searchTerm,@RequestParam("entityTypeId") Long entTypeId,@RequestParam("limit") long limit,HttpServletResponse response) {
		List<Entity> entities = null;	
		long entityTypeId = 0;
	    long offset = 0;
	    String startHighlight = "<b>";
		String endHighlight = "</b>";
	    
	    if(page>1){
            offset= limit*(page-1);
         }
	    if(entTypeId != null){	        	
        	entityTypeId = entTypeId;
        }
	    try {
			entities = entityManager.entitySearch(searchTerm,entityTypeId,startHighlight,endHighlight,limit,offset);
			if(entities != null){
					entityManager.exportEntities(response, entities);
			}
		} catch (DataException e1) {
			logger.error("searchExport:Error searchExport:"+e1.getMessage());
		} catch (IOException e) {
			logger.error("searchExport:Error searchExport:"+e.getMessage());
		}catch (Exception e) {
			logger.error("searchExport:Error searchExport:"+e.getMessage());
		}
		
	}
	
	@RequestMapping(value = {"/entity/box/export","/*/entity/box/export"}, method = RequestMethod.POST)
	public void boxExport(@RequestParam("pageBox")long page,@RequestParam("limitBox") long limit,HttpServletResponse response) {
		 long offset = 0;
		 String order = "date_added";
		    if(page>1){
	            offset= limit*(page-1);
	         }
		List<Entity> entities = null;	  
		try {
			entities = entityManager.loadBoxEntities(order,limit,offset);
			if(entities != null){
				entityManager.exportEntities(response, entities);
			}
		} catch (DataException e1) {
			logger.error("boxExport:Error boxExport:"+e1.getMessage());
		} catch (IOException e) {
			logger.error("boxExport:Error boxExport:"+e.getMessage());
		} catch (Exception e) {
			logger.error("boxExport:Error boxExport:"+e.getMessage());
		}
		
	}
	
	/*
	 * Modify ROLE Permissions from EntityDetail Page
	 * */
	@RequestMapping(value = {"/entity/permission/add","/*/entity/permission/add"}, method = RequestMethod.POST)
	public @ResponseBody boolean addPermissions(@RequestParam("ObjId")long entityId,@RequestParam("role")String role,@RequestParam("permission")String permission){
		MappingJackson2JsonView view = new MappingJackson2JsonView();	
		boolean status = false;
		Entity et = null;
		try {
			et =entityManager.loadEntity(entityId);
			if(et != null)
				status = entityManager.modifyPermissionsOnEntity(et, role, permission);
		} catch (DataException e) {
			logger.error("Error in add permission : " + e.getMessage(),e);
		}catch (Exception e) {
			logger.error("Error in add permission : " + e.getMessage(),e);
		}
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return status;
	}	
	
	@RequestMapping(value = {"/entity/exportable","/*/entity/exportable"},headers="Accept=application/xml,application/json", method = RequestMethod.GET)
	public @ResponseBody ExportMap getBoxEntities() { 
	//public ModelAndView getBoxEntities() throws DataException {
		long page = 1;
		long limit=10;
		long offset = 0;		
		String order = "date_added";
	
		if(page>1){
	       offset= limit*(page-1);
	    }
		List<Entity> entities = null;	
		ExportMap exportMap = null;
		try {
			entities = entityManager.loadBoxEntities(order,limit,offset);
			exportMap = exportable.getExportMap(entities);
		} catch (DataException e) {
			logger.error("ExportMap:Error ExportMap : " + e.getMessage(),e);
		}catch (Exception e) {
			logger.error("ExportMap:Error ExportMap : " + e.getMessage(),e);
		}			
		return exportMap;

	}
	
	@RequestMapping(value = {"/entity/user/role/add","/*/entity/user/role/add"}, method = RequestMethod.POST)
	public View assignUserRole(@RequestParam("entityId") long entityId,@RequestParam("roleOrUser") String roleOrUser) {
		MappingJackson2JsonView view = new MappingJackson2JsonView();	
		if(entityId == 0 || roleOrUser=="" ){
			return new MappingJackson2JsonView();
		}
		try {
			boolean status = entityManager.assignUserRole(entityId,  roleOrUser);
			view.getAttributesMap().put("status", status);
		} catch (DataException e) {
			logger.error("assignUserRole:Error assignUserRole : " + e.getMessage(),e);
		}catch (Exception e) {
			logger.error("assignUserRole:Error assignUserRole : " + e.getMessage(),e);
		}
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return new MappingJackson2JsonView();
	} 

	@RequestMapping(value = {"/entity/user/role/remove","/*/entity/user/role/remove"}, method = RequestMethod.POST)
	public View removeUserRole(@RequestParam("entityId") long entityId,@RequestParam("roleOrUser") String roleOrUser){
		MappingJackson2JsonView view = new MappingJackson2JsonView();	
		if(entityId == 0 || roleOrUser=="" ){
			return new MappingJackson2JsonView();
		}
		try {
			boolean status = entityManager.removeUserRole(entityId, roleOrUser,view);
			view.getAttributesMap().put("status", status);
		} catch (DataException e) {
			logger.error("removeUserRole:Error removeUserRole : " + e.getMessage(),e);
		} catch (Exception e) {
			logger.error("removeUserRole:Error removeUserRole : " + e.getMessage(),e);
		}
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return new MappingJackson2JsonView();
	} 

	@RequestMapping(value = {"/entity/rolesAll/load/{entityId}","/*/entity/rolesAll/load/{entityId}"}, method = RequestMethod.GET)
	public MappingJackson2JsonView showAllRoles(@PathVariable("entityId") long entityId){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		List<CompletePermissions> combinedRolesForEntity = null;
		try {
			Entity entity = entityManager.loadEntity(entityId);
			combinedRolesForEntity = entityManager.retrieveCombinedRolesForEntity(entity);
			if(combinedRolesForEntity != null){
				view.getAttributesMap().put("combinedRolesForEntity",combinedRolesForEntity);
				view.getAttributesMap().put("title",entity.getTitle());
				view.getAttributesMap().put("rolesCount",combinedRolesForEntity.size());
			}	
		} catch (DataException e) {
			logger.error("showAllRoles:Error showAllRoles : " + e.getMessage(),e);
		} catch (Exception e) {
			logger.error("showAllRoles:Error showAllRoles : " + e.getMessage(),e);
		}
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		SessionContext.setShowAllRolesSessionContext("showAllRoles");
		return view;
	}
	
	@RequestMapping(value = {"/entity/reload/{entityId}","/*/entity/reload/{entityId}"}, method = RequestMethod.GET)
	public View reloadEntity(@PathVariable("entityId") long entityId,@RequestParam("page") String page){
		MappingJackson2JsonView view = new MappingJackson2JsonView();	
		SessionContext.getShowAllRolesSessionContext().destroyShowAllRolesSessionContext();
		if(page.equalsIgnoreCase("entityDetail"))
			view.getAttributesMap().put("redirect", "/app/"+ClientManageUtil.loadClientSchema()+"/entity/view/"+entityId);
		else if( page.equalsIgnoreCase("addent"))
			view.getAttributesMap().put("redirect", "/app/"+ClientManageUtil.loadClientSchema()+"/entity/edit/"+entityId);
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = {"/entity/roles/load/{entityId}","/*/entity/roles/load/{entityId}"}, method = RequestMethod.GET)
	public View entityRolesLoad(@PathVariable("entityId") long entityId,@RequestParam("page") String page, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MappingJackson2JsonView view = new MappingJackson2JsonView();	
		List<CompletePermissions> combinedRolesForEntity = null;	
		SessionContext combinedRolesSC = SessionContext.getCombinedRolesSessionContext();
		PagedListHolder combinedRolesList = null;
			if(combinedRolesSC.getCombinedRoles() == null){
				// retrieve the All roles permissions on the Entity
				Entity entity = entityManager.loadEntity(entityId);
				combinedRolesForEntity = entityManager.retrieveCombinedRolesForEntity(entity);
				combinedRolesList = new PagedListHolder(combinedRolesForEntity);
				combinedRolesList.setPageSize(10);
				combinedRolesForEntity = combinedRolesList.getPageList();
				SessionContext.setCombinedRolesSessionContext(combinedRolesList);
			}
			else {
				combinedRolesList = combinedRolesSC.getCombinedRoles();
				if (combinedRolesList != null){
					if ("next".equals(page)) {
						combinedRolesList.nextPage();
						combinedRolesForEntity = combinedRolesList.getPageList();
					}
					else if ("previous".equals(page)) {
						combinedRolesList.previousPage();
						combinedRolesForEntity = combinedRolesList.getPageList();
					}else  if ("last".equals(page)){
						while(true){
							if(combinedRolesList.isLastPage()){
								combinedRolesForEntity = combinedRolesList.getPageList();
								break;
							}else{
								combinedRolesList.nextPage();
							}
						}
					}else{
						while(true){
							if(combinedRolesList.isFirstPage()){
								combinedRolesForEntity = combinedRolesList.getPageList();
								break;
							}else{
								combinedRolesList.previousPage();
							}	
						}
					}
				}
				
				
			}
			view.getAttributesMap().put("combinedRolesForEntity",combinedRolesForEntity);
			view.getAttributesMap().put("pageNum",combinedRolesList.getPage());
			view.getAttributesMap().put("totalPages",combinedRolesList.getPageCount()-1);
			return new MappingJackson2JsonView();	
	}

	@RequestMapping(value = {"/entity/roles/assigned/{entityId}","/*/entity/roles/assigned/{entityId}"}, method = RequestMethod.GET)
	public MappingJackson2JsonView assignedRolesLoad(@PathVariable("entityId") long entityId) throws Exception {
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		view.getAttributesMap().put("entityId",entityId);
		return view;	
	}

	@RequestMapping(value = {"/entity/users/assigned/{entityId}","/*/entity/users/assigned/{entityId}"}, method = RequestMethod.GET)
	public MappingJackson2JsonView assignedUsersLoad(@PathVariable("entityId") long entityId) throws Exception {
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		view.getAttributesMap().put("entityId",entityId);
		return view;	
	}

	/**
	 * @param entityeManager the entityManager to set
	 */
	@Autowired
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Autowired
	public void setEntityTypeManager(EntityTypeManager entityTypeManager) {
		this.entityTypeManager = entityTypeManager;
	}
	
	/**
	* @param entityeValidator the entityValidator to set
	*/
//	@Autowired
//	public void setEntityValidator(EntityValidator entityValidator) {
//	this.entityValidator = entityValidator;
//	}
	
	@Autowired
	public void setExportable(Exportable exportable) {
	this.exportable = exportable;
	}

	/**
	 * @param velocityManager the velocityManager to set
	 */
	@Autowired
	public void setVelocityManager(VelocityManager velocityManager) {
		this.velocityManager = velocityManager;
	}
	
	/**
	 * @param templateManager the templateManager to set
	 */
	@Autowired
	public void setTemplateManager(TemplateManager templateManager) {
		this.templateManager = templateManager;
	}
	

	
	@ModelAttribute("DATATYPE")
	public Map DataType(){
		final DataType[] enums = DataType.class.getEnumConstants();  
		Map<String, String> map = new HashMap<String, String>(enums.length);	
		for (DataType anEnum : enums)
			map.put(anEnum.toString(), anEnum.toString());
	
		return map;
	}	
	@ModelAttribute("FIELDTYPE")
	public Map FieldType(){
		final FieldType[] enums = FieldType.class.getEnumConstants();  
		Map<String, String> map = new HashMap<String, String>(enums.length);	
		for (FieldType anEnum : enums)
			map.put(anEnum.toString(), anEnum.toString());
	
		return map;
	}
	@ModelAttribute("PERMISSION")
	public Map Permission(){
		final Permissions[] enums = Permissions.class.getEnumConstants();  
		Map<String, Integer> map = new HashMap<String, Integer>(enums.length);	
		for (Permissions anEnum : enums)
			map.put(anEnum.toString(), anEnum.getValue());
	
		return map;
	}
	@ModelAttribute("ENTITYPE")
	public Map EntityTypes(){
		final EntityTypes[] enums = EntityTypes.class.getEnumConstants();  
		Map<String, Integer> map = new HashMap<String, Integer>(enums.length);	
		for (EntityTypes anEnum : enums)
			map.put(anEnum.toString(), anEnum.getValue());
	
		return map;
	}
	
	
	@RequestMapping(value = {"/entity/save/relation","/*/entity/save/relation"}, method = RequestMethod.GET)
	public View saveRelation(@RequestParam("baseEntityId")long baseEntityId,
			@RequestParam("entityToRelate")long entityToRelate,
			@RequestParam("relationName")String relationName,MappingJackson2JsonView view) {	
		try{
			
			if(baseEntityId == entityToRelate){
				view.getAttributesMap().put("error", "Relation already exists.");
				return new MappingJackson2JsonView();
			}
			List<Entity> existingRelations = entityManager.entitySearch("type:Relation From:["+ baseEntityId +"] or Name:[*] or To:["+ entityToRelate +"]", 0, "<b>", "</b>", 10, 0);
			List<Entity> existingRelationsInReverseOrder = entityManager.entitySearch("type:Relation From:["+ entityToRelate +"] or Name[*] or To:["+ baseEntityId +"]", 0, "<b>", "</b>", 10, 0);
			boolean relationExists = false;
			if(existingRelations != null && existingRelations.size() > 0){
				for(Entity e : existingRelations){
					if(relationExists)
						break;
					List<AttributeValueStorage> attributeValueStorageList = e.getAttributeValueStorage();
					for(AttributeValueStorage avs : attributeValueStorageList){
						if(relationExists)
							break;
						if(avs.getName().equalsIgnoreCase("From")){
							if(((Long)avs.getValue()) == baseEntityId){
								for(AttributeValueStorage avs1 : attributeValueStorageList){
									if(avs1.getName().equalsIgnoreCase("To")){
										if(((Long)avs1.getValue()) == entityToRelate){
											relationExists = true;
											break;
										}
									}
								}
							}
						}
					}
				}
			}

			if(existingRelationsInReverseOrder != null && existingRelationsInReverseOrder.size() > 0){
				for(Entity e : existingRelationsInReverseOrder){
					if(relationExists)
						break;
					List<AttributeValueStorage> attributeValueStorageList = e.getAttributeValueStorage();
					for(AttributeValueStorage avs : attributeValueStorageList){
						if(relationExists)
							break;
						if(avs.getName().equalsIgnoreCase("To")){
							if(((Long)avs.getValue()) == baseEntityId){
								for(AttributeValueStorage avs1 : attributeValueStorageList){
									if(avs1.getName().equalsIgnoreCase("From")){
										if(((Long)avs1.getValue()) == entityToRelate){
											relationExists = true;
											break;
										}
									}
								}
							}
						}
					}
				}
			}			
			
			if(relationExists){
				view.getAttributesMap().put("error", "Relation already exists.");
				return new MappingJackson2JsonView();
			}
			
			
			Entity newRelation = new Entity();
			EntityType entityType = entityTypeManager.load(EntityType.EntityTypes.RELATION.getValue());
			List<Attribute> attributeList = new ArrayList<Attribute>();
		    List<AttributeValueStorage> avsList = new ArrayList<AttributeValueStorage>();
		    AttributeValueStorage avs = null;
			if(entityType != null){
				attributeList = entityType.getAttributes();
				if(attributeList != null && attributeList.size()>0){
					for(Attribute attribute:attributeList){
						avs = new AttributeValueStorage();
						try {
							BeanUtils.copyProperties(avs, attribute);
						} catch (IllegalAccessException e) {
							logger.error(e.getMessage(),e);
						} catch (InvocationTargetException e) {
							logger.error(e.getMessage(),e);
						}
						avs.setEntityTypeId(entityType.getId());
						avsList.add(avs);
					}
				}else{
					isError= true;
					logger.error("loadAttributes:- No Attributes found :"+attributeList);
					view.getAttributesMap().put("errorMsg",messageSource.getMessage("error.entitytype.attributes.empty", null, null));
					throw new DataException("loadAttributes:- No Attributes found:"+attributeList);
				}
				newRelation.setEntityType(entityType);
				newRelation.setEntityTypeId(entityType.getId());
				newRelation.setAttributeValueStorage(avsList);
				EntityStatus entityStatus = new EntityStatus();
				entityStatus.setEntityId(newRelation.getEntityId());
				entityStatus.setEntityValid(false);
				
				if(avsList != null && avsList.size() > 0){
					for(AttributeValueStorage avs1 : avsList){
						if(avs1.getName().equalsIgnoreCase("From")){
		    				avs1.setEntityId(newRelation.getEntityId());
		    				avs1.setValueLong(baseEntityId);
			    			avs1.setEntityTypeId(newRelation.getEntityTypeId());
			    		}else if(avs1.getName().equalsIgnoreCase("To")){
		    				avs1.setEntityId(newRelation.getEntityId());
		    				avs1.setValueLong(entityToRelate);
			    			avs1.setEntityTypeId(newRelation.getEntityTypeId());
			    		}else if(avs1.getName().equalsIgnoreCase("Name")){
		    				avs1.setEntityId(newRelation.getEntityId());
		    				avs1.setValueVarchar(relationName);
			    			avs1.setEntityTypeId(newRelation.getEntityTypeId());
			    		}
			    	}
				}
				view.getAttributesMap().put("eId", entityManager.quickSave(newRelation,avsList));
				view.getAttributesMap().put("From", baseEntityId);
				view.getAttributesMap().put("To", entityToRelate);
				view.getAttributesMap().put("Name", relationName);
			}
		}catch(DataException de){
			
		}

		return new MappingJackson2JsonView();
	}

	@RequestMapping(value = {"/entity/delete/{entityId}","/*/entity/delete/{entityId}"}, method = RequestMethod.GET)
    public ModelAndView deleteEntity(@PathVariable long entityId,HttpServletRequest request,Locale locale ){
		try {
		entityManager.removeEntity(entityId);
	} catch (DataException e) {
		e.printStackTrace();
	}
		if(request.getParameter("isFolder") != null){
			return new ModelAndView("redirect:/app/"+ClientManageUtil.loadClientSchema()+"/folderbrowser");
		}
	return new ModelAndView("redirect:/app/"+ClientManageUtil.loadClientSchema()+"/entitytype/list");
	}

	@RequestMapping(value = {"/folderbrowser","/*/folderbrowser"}, method = RequestMethod.GET)
    public MappingJackson2JsonView viewFolderBrowser(){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		List<EntityType> treeableEntitTypes = new ArrayList<EntityType>();
		try {
			List<EntityType> entityTypes = entityTypeManager.loadEntiyTypeDropDownList();
			for(EntityType entityType : entityTypes){
				if(entityType.isTreeable()){
					treeableEntitTypes.add(entityType);
				}
			}
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		view.getAttributesMap().put("treeableEntitTypes", treeableEntitTypes);
		return view;
    }
	
	@RequestMapping(value = {"/folder/treeable/children/","/*/folder/treeable/children/{parentId}"}, method = RequestMethod.GET)
	public View getTreeableChildren(@PathVariable long parentId) throws DataException, IOException{
		MappingJackson2JsonView view = new MappingJackson2JsonView();	
		ArrayList<Map<String, String>> children = new ArrayList<Map<String, String>>();
		if(parentId > 0){
			EntityViewTool entityViewTool = new EntityViewTool();// TODO integrate with Spring IOC for singleton.
			List<Entity> entities = entityViewTool.pullRelatedEntitiesByDirection(parentId, "From");
			Collections.sort(entities);
			for(Entity entity : entities){
				if(entity.getEntityType().isTreeable()){
					
					Map<String, String> map = new HashMap<String, String>();
					
					map.put("id", entity.getEntityId()+"");
					map.put("name", entity.getTitle());
					map.put("parent", parentId+"");
					map.put("entityTypeName", entity.getEntityType().getName());
					if(entity.hasFile())
						map.put("type", "file");
					else
						map.put("type", "non-file");
					
					if(entity.getEntityType().getName().equals("Document")){
						map.put("name", entity.getValueString("Name"));
						for(AttributeFileStorage afs : entity.getAttributeFileStorage()){
							String filePath = afs.getImagePath();
							if(filePath != null && !filePath.equals("")){
								map.put("filePath", filePath);
								break;
							}
						}
					}
					map.put("modDate",new SimpleDateFormat("dd_MMM_yyyy_HH_mm_ss").format(entityManager.loadEntityModDate(entity.getEntityId())));
					children.add(map);
				}
			}
		}
		view.getAttributesMap().put("children", children);
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value={"/folder/subFolders/{parentId}","/*/folder/subFolders/{parentId}"})
	public View getSubFolders(@PathVariable long parentId) throws DataException{
		MappingJackson2JsonView view = new MappingJackson2JsonView();	
		ArrayList<Map<String, String>> children = new ArrayList<Map<String, String>>();
		if(parentId > 0){
			EntityViewTool entityViewTool = new EntityViewTool();// TODO integrate with Spring IOC for singleton.
			List<Entity> entities = entityViewTool.pullRelatedEntitiesByDirection(parentId, "From");
			Collections.sort(entities);
			for(Entity entity : entities){
				if(entity.getEntityTypeId() == EntityType.EntityTypes.FOLDER.getValue()){
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", entity.getEntityId()+"");
					map.put("name", entity.getValueString("Name"));
					map.put("type", "file");
					map.put("parent", parentId+"");
					map.put("modDate",new SimpleDateFormat("dd_MMM_yyyy_HH_mm_ss").format(entityManager.loadEntityModDate(entity.getEntityId())));
					children.add(map);
				}
			}
		}
		view.getAttributesMap().put("children", children);
		return new MappingJackson2JsonView();
	}

	@RequestMapping(value={"/entity/folderbrowser/lastsyncedon/","/*/entity/folderbrowser/lastsyncedon/"})
	public View getLastSyncedOn(HttpServletRequest request,HttpServletResponse response) throws Exception{
		MappingJackson2JsonView view = new MappingJackson2JsonView();	
		String username = request.getParameter("username");
		EntityViewTool ev = new EntityViewTool();
		List<Entity> entities = ev.searchEntities("type:FolderSyncDetails Username:["+ username +"]");
		Entity userEntity = null;
		for(Entity e : entities){
			if(e.getValueString("username").equals(username)){
				userEntity = e;
				break;
			}
		}
		if(userEntity == null){
			long newEntityId = attributeQuickSave((long)157,0,username);
			attributeQuickSave((long)158,newEntityId ,"01_Jan_2015_01_01_01") ;
			userEntity = entityManager.loadEntity(newEntityId);
		}
		String lastSyncedOn = null;
		if(userEntity != null)
			lastSyncedOn = userEntity.getValueString("Syncedon");
		if(lastSyncedOn == null || lastSyncedOn.equals(""))
			lastSyncedOn = "01_Jan_2015_01_01_01";
		view.getAttributesMap().put("lastSyncedOn", lastSyncedOn);
		view.getAttributesMap().put("userEntity", userEntity);
		return new MappingJackson2JsonView();
	}

	
	@RequestMapping(value = {"/entity/new/folder","/*/entity/new/folder"}, method = RequestMethod.GET)
	public View saveNewFolder(HttpServletRequest request) {
		MappingJackson2JsonView view = new MappingJackson2JsonView();	
		try{
		String newFolderName = request.getParameter("newFolderName");
		Long parentFolderId = Long.parseLong(request.getParameter("parentFolderId"));	
		Long newFolderId = (long) 0;
		Entity newFolder = new Entity();
		EntityType folderEntityType = entityTypeManager.load(EntityType.EntityTypes.FOLDER.getValue());
		List<Attribute> attributeList = new ArrayList<Attribute>();
	    List<AttributeValueStorage> avsList = new ArrayList<AttributeValueStorage>();
	    AttributeValueStorage avs = null;
		if(folderEntityType != null){
			attributeList = folderEntityType.getAttributes();
			if(attributeList != null && attributeList.size()>0){
				for(Attribute attribute:attributeList){
					avs = new AttributeValueStorage();
					try {
						BeanUtils.copyProperties(avs, attribute);
					} catch (IllegalAccessException e) {
						logger.error(e.getMessage(),e);
					} catch (InvocationTargetException e) {
						logger.error(e.getMessage(),e);
					}
					avs.setEntityTypeId(folderEntityType.getId());
					avsList.add(avs);
				}
			}else{
				isError= true;
				logger.error("loadAttributes:- No Attributes found :"+attributeList);
				view.getAttributesMap().put("errorMsg",messageSource.getMessage("error.entitytype.attributes.empty", null, null));
				throw new DataException("loadAttributes:- No Attributes found:"+attributeList);
			}
			newFolder.setEntityType(folderEntityType);
			newFolder.setEntityTypeId(folderEntityType.getId());
			newFolder.setAttributeValueStorage(avsList);
			EntityStatus entityStatus = new EntityStatus();
			entityStatus.setEntityId(newFolder.getEntityId());
			entityStatus.setEntityValid(false);
			
			if(avsList != null && avsList.size() > 0){
				for(AttributeValueStorage avs1 : avsList){
					if(avs1.getName().equalsIgnoreCase("Name")){
	    				avs1.setEntityId(newFolder.getEntityId());
	    				avs1.setValueVarchar(newFolderName);
		    			avs1.setEntityTypeId(newFolder.getEntityTypeId());
		    		}
		    	}
			}
			newFolderId = entityManager.quickSave(newFolder,avsList);
			view.getAttributesMap().put("entityId", newFolderId);
		}
		saveRelation(parentFolderId, newFolderId, "subfolder", view);
		}catch(Exception e){e.printStackTrace();}

		return view;
	}
	
	@Autowired
	FileManager fileManager;
	
	@RequestMapping(value={"/entity/attribute/get/filetext/","/*/entity/attribute/get/filetext/"})
	public View getFileText(HttpServletRequest request,HttpServletResponse response) throws Exception{
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		long entityId = Long.parseLong(request.getParameter("entityId"));
		long attributeFileId = Long.parseLong(request.getParameter("attributeFileId"));
		for(AttributeFileStorage attributeFileStorage : entityManager.loadEntity(entityId).getAttributeFileStorage()){
			if(attributeFileStorage.getAttributeFileId() == attributeFileId){
				view.getAttributesMap().put("fileText", fileManager.getFileText(attributeFileStorage));
			}
		}		
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value={"/entity/attribute/set/filetext/","/*/entity/attribute/set/filetext/"})
	public View setFileText(HttpServletRequest request,HttpServletResponse response) throws Exception{
		long entityId = Long.parseLong(request.getParameter("entityId"));
		long attributeFileId = Long.parseLong(request.getParameter("attributeFileId"));
		String newText = request.getParameter("newText");
		for(AttributeFileStorage attributeFileStorage : entityManager.loadEntity(entityId).getAttributeFileStorage()){
			if(attributeFileStorage.getAttributeFileId() == attributeFileId){
				fileManager.setFileText(attributeFileStorage,newText);
			}
		}		
		return new MappingJackson2JsonView();
	}
	
	private FileUploadHelperUtil fileUploadHelpUtil;
	/**
	 * @param fileHelper the fileHelper to set
	 */
	@Autowired
	public void setFileUploadHelpUtil(FileUploadHelperUtil fileUploadHelpUtil) {
		this.fileUploadHelpUtil = fileUploadHelpUtil;
	}
	
	@RequestMapping(value={"/entity/get/document/csv/file/","/*/entity/get/document/csv/file/"})
	public void getEntityDocumentFileOrCSVFile(HttpServletRequest request, HttpServletResponse response) throws DataException, IOException{
		
		String entityIdString = request.getParameter("entityId");
		if(entityIdString == null || entityIdString == "")
			return;
		
		long entityId = Long.parseLong(entityIdString);
		Entity entity = entityManager.loadEntity(entityId);
		
		String usersFilePath = "";
		if(entity.getEntityType().getName().equals("Document")){
			for(AttributeFileStorage afs : entity.getAttributeFileStorage()){
				usersFilePath = afs.getImagePath();
			}
			String serverPath = fileUploadHelpUtil.getServerPath();	
			String fullUsersFilePath = serverPath+ File.separator + usersFilePath;
			File file = new File(fullUsersFilePath);
			byte[] buffer=null;
			if(file.exists()) {
			    InputStream inputStream;
				try {
					inputStream = new FileInputStream(new File(fullUsersFilePath));
					buffer= new byte[inputStream.available()];
					String extension = FilenameUtils.getExtension(usersFilePath);
						if(extension != null){
							String contentType =  MimeTypeConstants.getMimeType(extension.toLowerCase());
							
							response.setHeader("Content-disposition", "attachment; filename="+ FilenameUtils.getName(usersFilePath));
							
							if(request.getParameter("force-download") != null)
								response.setContentType("application/force-download");
							else
								response.setContentType(contentType);
							
							response.setContentLength(buffer.length);
							IOUtils.copy(inputStream, response.getOutputStream());
					            response.flushBuffer();
						}
				} catch (IOException ie) {
					logger.error("Error in loadUserFile() of FileUploadController "+ ie);
					ie.printStackTrace();
				}
			}
		}else{
			List<Entity> entityList = new ArrayList<Entity>();
			entityList.add(entity);
			File csvFileForEntity = entityManager.getCSVFileInputStreamForEntity(entityId);
			FileInputStream csvFileInputStreamForEntity = new FileInputStream(csvFileForEntity);
			String contentType =  MimeTypeConstants.getMimeType("csv");
			response.setHeader("Content-disposition", "attachment; filename="+ entity.getTitle() + ".csv");
			
			if(request.getParameter("force-download") != null)
				response.setContentType("application/force-download");
			else
				response.setContentType(contentType);
			
			response.setContentLength(csvFileInputStreamForEntity.available());
			IOUtils.copy(csvFileInputStreamForEntity, response.getOutputStream());
	            response.flushBuffer();
		}
	}
	
	@RequestMapping(value={"/entity/update/using/csv/file/","/*/entity/update/using/csv/file/"})
	public View updateEntityUsingCSVFile(HttpServletRequest request, HttpServletResponse  response) throws Exception{
		
		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
		}
		MultipartFile csvFileToUpdateEntity = ((DefaultMultipartHttpServletRequest)request).getFile("file");
		long entityId = Long.parseLong(request.getParameter("entityId"));
		Entity entityToUpdate = entityManager.loadEntity(entityId);
		CsvReader csvReader = new CsvReader(csvFileToUpdateEntity.getInputStream(), Charset.forName("UTF-8"));
		csvReader.readHeaders();
		while(csvReader.readRecord()){
			for(AttributeValueStorage avs : entityToUpdate.getAttributeValueStorage()){
				String newValueToSave = csvReader.get(avs.getName());
				attributeQuickSave(avs.getId(), entityId, newValueToSave);
			}
		}
		return new MappingJackson2JsonView();
	}
}
