package com.jbent.peoplecentral.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.exception.DataExceptionRT;
import com.jbent.peoplecentral.model.manager.EntityTypeManager;
import com.jbent.peoplecentral.model.pojo.Attribute;
import com.jbent.peoplecentral.model.pojo.AttributeDataType;
import com.jbent.peoplecentral.model.pojo.AttributeFieldType;
import com.jbent.peoplecentral.model.pojo.CompletePermissions;
import com.jbent.peoplecentral.model.pojo.EntityType;
import com.jbent.peoplecentral.model.pojo.FieldTypeOption;
import com.jbent.peoplecentral.model.pojo.Regex;
import com.jbent.peoplecentral.model.pojo.Attribute.DataType;
import com.jbent.peoplecentral.model.pojo.Attribute.FieldType;
import com.jbent.peoplecentral.permission.Permission.Permissions;
import com.jbent.peoplecentral.web.SessionContext;

@Controller
@SuppressWarnings("unchecked")
public class ManageEntityTypeController extends WebApplicationObjectSupport {
	
	private EntityTypeManager entityTypeManager;
	private Validator entityTypeValidator;
	private Validator attributeValidator;
	private List<Long> etIdsList = null;
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value = {"/entitytype/add","/*/entitytype/add"}, method = RequestMethod.GET)
	public MappingJackson2JsonView manage(){	
		MappingJackson2JsonView view = new MappingJackson2JsonView();	
		EntityType et = new EntityType();
		view.getAttributesMap().put("entityType", et);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return view;
	}
	
	@RequestMapping(value = {"/attribute/delete/{attId}","/*/attribute/delete/{attId}"}, method = RequestMethod.POST)
	public View deleteAttribute(@PathVariable long attId){
			Attribute att = null; 
			MappingJackson2JsonView view = new MappingJackson2JsonView();
		try {
			att = entityTypeManager.loadAttribute(attId);
			entityTypeManager.deleteAttribute(attId);
			view.getAttributesMap().put("redirect", "/app/" + ClientManageUtil.loadClientSchema() + "/entitytype/" + att.getEntityTypeId());
		} catch (DataException e) {
			logger.error(e.getMessage(),e);
			view.getAttributesMap().put("error", "Unable to save entity type");
//			throw new HttpMessageNotWritableException(e.getMessage(), e);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			view.getAttributesMap().put("error", "Unable to delete Attribute type");
		}
		
//		return "blank";
//		RedirectView rv = new RedirectView("/app/" + ClientManageUtil.loadClientSchema() + "/manage/entitytype/" + att.getEntityTypeId());
//		rv.setExposeModelAttributes(false);
		
		return view;
	}
	
	@RequestMapping(value = {"/entitytype/delete/{entityTypeId}","/*/entitytype/delete/{entityTypeId}"}, method = RequestMethod.POST)
	public View deleteEntityType(@PathVariable long entityTypeId){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		try {
			entityTypeManager.delete(entityTypeId);
			view.getAttributesMap().put("redirect", "/app/" + ClientManageUtil.loadClientSchema() + "/entitytype/add");
		} catch (DataException e) {
			logger.error(e.getMessage(),e);
			view.getAttributesMap().put("error", "Unlock before delete the entity type");
//			throw new HttpMessageNotWritableException(e.getMessage(), e);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			view.getAttributesMap().put("error", "Unable to delete entity type:"+entityTypeId);
		}

		return view;
	}
	
	@RequestMapping(value = {"/entitytype/lock/{entityTypeId}","/*/entitytype/lock/{entityTypeId}"}, method = RequestMethod.GET)
	public View lockEntityType(HttpServletRequest request,@PathVariable long entityTypeId,@RequestParam("lock")String toggle, HttpSession session){
		boolean hasEtId = false;
		boolean lockMode = false;
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		SessionContext unLockedEntityTypeSC = SessionContext.getUnlockedEnittyTypesSessionContext();
		try {
			if(unLockedEntityTypeSC.getEtIdsList() != null){			
				// to avoid duplicate entity type ids in the session
				 for(Long etId:etIdsList){
			       	if(etId==entityTypeId){
			       		hasEtId = true;
			       	}
			    }
				if(!hasEtId){
					etIdsList.add(entityTypeId);
				}
			}else {
				etIdsList = new ArrayList<Long>();
				etIdsList.add(entityTypeId);
			}
			lockMode = entityTypeManager.lock(entityTypeId,toggle);
			SessionContext.setUnlockedEnittyTypesSessionContext(etIdsList);
			view.getAttributesMap().put("lockMode", lockMode);
		} catch (DataException e) {
			logger.error(e.getMessage(),e);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			view.getAttributesMap().put("error", "Unable to load entity type"+entityTypeId);
		}
		return view;
	}
	
	@RequestMapping(value = {"/entitytype","/*/entitytype"}, method = RequestMethod.POST)
	public View saveEntityType(@ModelAttribute("entityType")EntityType entityType,BindingResult result,Locale locale,SessionStatus status){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		entityTypeValidator.validate(entityType, result);
		if (result.hasErrors()) { 
			view.getAttributesMap().put("errors", true);
			view.getAttributesMap().put("entityType", entityType);
			view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
			view.getAttributesMap().put("errorMsg", messageSource.getMessage("error.entitytype.create", null, locale));
			RedirectView rv = new RedirectView("/app/" + ClientManageUtil.loadClientSchema() + "/entitytype/add");
			rv.setExposeModelAttributes(false);
			return rv;
		}
				
		EntityType et = new EntityType();
		try {
			et = entityTypeManager.save(entityType);
			status.setComplete();
		} catch (DataException e) {
			logger.error("Unable to save : " + e.getMessage(), e);
			result.reject("error.entitytype.save.failed");
			return new JstlView("manage/entitytype/manage");
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			view.getAttributesMap().put("error", "Unable to save entity type");
			return new JstlView("manage/entitytype/manage");
		}
		
		view.getAttributesMap().put("entityType", et);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		RedirectView rv = new RedirectView("/app/" + ClientManageUtil.loadClientSchema() + "/entitytype/" + et.getId());
		rv.setExposeModelAttributes(false);
		return rv;
	}
	
	@RequestMapping(value = {"/entitytype/{entityTypeId}","/*/entitytype/{entityTypeId}"}, method = RequestMethod.GET)
	public MappingJackson2JsonView editEntityType(@PathVariable long entityTypeId){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		EntityType et = null;
		String searchTerm=null;
		boolean lockMode = false;
		List<CompletePermissions> permissionsList = null;
		try {
			et = entityTypeManager.load(entityTypeId);
			et.setAttributes(entityTypeManager.prepareAttributeVelocityNameList(et.getAttributes()));	
			lockMode = entityTypeManager.lock(et.getId(), null);
			permissionsList = entityTypeManager.retrieveEntityTypePermissions(et);
			if(permissionsList!= null){
				if(permissionsList.size()>0)
				view.getAttributesMap().put("permissionsList",permissionsList);
				else
					view.getAttributesMap().put("permissionsList",null);
		    }
		} catch (DataException e) {
			logger.error("Error loading entity types : " + e.getMessage(),e);
			view.getAttributesMap().put("error", "Unlock before delete the entity type");
		} catch (Exception e) {
			logger.error("Error loading entity types : " + e.getMessage(),e);
			view.getAttributesMap().put("error", "Unlock before delete the entity type");
		}
		searchTerm = "type:";
		//view.getAttributesMap().put("rolesList",entityManager.sanitizeRoles(allRoles));
		view.getAttributesMap().put("searchTermEntity", searchTerm+et.getName());
		view.getAttributesMap().put("entityType", et);
		view.getAttributesMap().put("lockMode", lockMode);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return view;
	}
	
	@RequestMapping(value = {"/attribute/save/entitytype/{entityTypeId}","/*/attribute/save/entitytype/{entityTypeId}"}, method = RequestMethod.GET)
	public MappingJackson2JsonView saveNewAttribute(@PathVariable long entityTypeId,@ModelAttribute("attribute")Attribute att, BindingResult result){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		att = new Attribute();
		att.setEntityTypeId(entityTypeId);
		List<AttributeDataType> dataTypes = null;
		List<AttributeFieldType> fieldTypes = null;
		AttributeFieldType attFiedlTypeOptions = new AttributeFieldType();
		try{
			dataTypes = entityTypeManager.loadAttributeDataTypes();
		}catch (Exception e) {
			logger.error("Unable to load datatypes : " + e.getMessage(), e);
		}
		try{
			fieldTypes = entityTypeManager.loadAttributeFieldTypes();
		}catch (Exception e) {
			logger.error("Unable to load fieldtypes : " + e.getMessage(), e);
		}
		view.getAttributesMap().put("dataTypes", dataTypes);
		view.getAttributesMap().put("fieldTypes", fieldTypes);
		view.getAttributesMap().put("attribute", att);		
		view.getAttributesMap().put("attFiedlTypeOptions", attFiedlTypeOptions);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		view.getAttributesMap().put("display", "display: none");
		view.getAttributesMap().put("displayCustom", "display: none");
		return view;
	}
	
	@RequestMapping(value = {"/attribute/save/{attributeId}","/*/attribute/save/{attributeId}"}, method = RequestMethod.GET)
	public MappingJackson2JsonView saveAttribute(@PathVariable long attributeId,@ModelAttribute("attribute")Attribute att, BindingResult result){		
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		List<AttributeDataType> dataTypes = null;
		List<AttributeFieldType> fieldTypes = null;
		List<Regex> regexList = new ArrayList<Regex>();
		AttributeFieldType attFiedlTypeOptions = new AttributeFieldType();
		EntityType relEntityType = null;
		
		if(attributeId > 0){
			try {
				att = entityTypeManager.loadAttribute(attributeId);
			} catch (DataException e) {
				logger.error(e.getMessage(),e);
				throw new DataExceptionRT("Unable to load attribute : " + e.getMessage(), e);
			}
		}	
		try{
			dataTypes = entityTypeManager.loadAttributeDataTypes();
			fieldTypes = entityTypeManager.loadAttributeFieldTypes();
			regexList = entityTypeManager.loadRegex(att.getFieldTypeId(),att.getId());	
			if(att.getRelatedEntityType() != null){
				relEntityType = entityTypeManager.load(att.getRelatedEntityType().getChildEntityTypeId());
				if(relEntityType != null)
				view.getAttributesMap().put("relEntityTypeName", relEntityType.getName());
			}
			if(att.getRegex()!= null){
				if(att.getRegex().getDisplayName().equalsIgnoreCase("custom")){
					view.getAttributesMap().put("displayCustom", "");
				}else view.getAttributesMap().put("displayCustom", "display: none");
			}else {
				view.getAttributesMap().put("displayCustom", "display: none");
				view.getAttributesMap().put("display", "display: none");
			}
		}catch (DataException e) {
			logger.error("Unable to load datatypes : " + e.getMessage(), e);
		}catch (Exception e) {
			logger.error("Unable to load datatypes : " + e.getMessage(), e);
		}
		view.getAttributesMap().put("dataTypes", dataTypes);
		view.getAttributesMap().put("fieldTypes", fieldTypes);
		//view.getAttributesMap().put("regexList", regexList);
		view.getAttributesMap().put("attribute", att);		
		view.getAttributesMap().put("attFiedlTypeOptions", attFiedlTypeOptions);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return view;
	}
	
	@RequestMapping(value = {"/attribute/save","/*/attribute/save"}, method = RequestMethod.POST)
	public View saveAttribute(@ModelAttribute("attribute")Attribute att,BindingResult result,Locale locale,SessionStatus status,HttpServletRequest request ){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		attributeValidator.validate(att, result);
		RedirectView rv = new RedirectView();
		rv.setExposeModelAttributes(false);
	
		SessionContext fieldTypeSC = SessionContext.getFieldTypeSessionContext();
		if(fieldTypeSC.getAttributeFieldType() != null){
			AttributeFieldType fieldType = fieldTypeSC.getAttributeFieldType();
			List<FieldTypeOption> options = fieldType.getOptions();
			for(FieldTypeOption option:options){
				option.setOptionValue(request.getParameter(option.getOption()));
			}
			att.setOptions(options);			
		}			
		EntityType et = new EntityType();		
		try {
			et = entityTypeManager.saveAttribute(att);
			status.setComplete();
		} catch (Exception e) {
			logger.error("Unable to save : " + e.getMessage(), e);
			result.reject("error.attribute.save.failed");
			view.getAttributesMap().put("errorMsg", messageSource.getMessage("error.attribute.save.failed", null, locale));
			//rv.setUrl("/app/" + ClientManageUtil.loadClientSchema() + "/entitytype/" + att.getEntityTypeId());
			return new MappingJackson2JsonView();
		}
		SessionContext.getFieldTypeSessionContext().destroyFieldTypeSessionContext();
		view.getAttributesMap().put("entityType", et);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		view.getAttributesMap().put("redirect", "/app/" + ClientManageUtil.loadClientSchema() + "/entitytype/" + et.getId());
		//rv.setUrl("/app/" + ClientManageUtil.loadClientSchema() + "/entitytype/" + et.getId());
		return view;
	}
	
	@RequestMapping(value = {"/attribute/reorder","/*/attribute/reorder"}, method = RequestMethod.POST)
	public MappingJackson2JsonView reorderAttributes(@RequestParam("attribute_ids")String attIds){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		EntityType et = null;
		attIds = attIds.trim();
		try{
			List<Long> ids = new ArrayList<Long>(); 
			if(attIds.contains(",")){
				for(String id : attIds.split(",")){
					id = id.trim();
					ids.add(new Long(id));
				}
			}
			et = entityTypeManager.reorderAttribute(ids);
		}catch (Exception e) {
			logger.error("ManageEntityTypeController.reorderAttributes : " + e.getMessage(), e);
			et = new EntityType();
		}
		view.getAttributesMap().put("entityType", et);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return view;
	}

	
	/*
	 * Add or modify ROLE Permissions from the EntityType Page.
	 * */
	@RequestMapping(value = {"/entitytype/permission/add","/*/entitytype/permission/add"}, method = RequestMethod.POST)
	public View addPermissions(@RequestParam("ObjId")long id,@RequestParam("role")String role,@RequestParam("permission")String permission){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		EntityType et = null;
		List<CompletePermissions> permissionsList = null;
		try {
			et =entityTypeManager.load(id);
		} catch (DataException e) {
			logger.error(e.getMessage(),e);
		}
		try {
			permissionsList =	entityTypeManager.addOrRemovePermissionOnEntityType(et, role, permission);
		} catch (DataException e) {
			logger.error("ManageEntityTypeController.addPermissions:-ManageError while creating Permissions on EntityType"+et.getName());
		}catch (Exception e) {
			logger.error("ManageEntityTypeController.addPermissions:-ManageError while creating Permissions on EntityType"+et.getName());
		}
		view.getAttributesMap().put("ManageEntityTypeController.addPermissions:-permissionsList", permissionsList);
		return view;
	}	
	
	/*
	 * Add or modify ROLE Permissions for Attribute.
	 * */

	@RequestMapping(value = {"/attribute/permission/show/{attributeId}","/*/attribute/permission/show/{attributeId}"}, method = RequestMethod.GET)
	public MappingJackson2JsonView showAttributePermissions(@PathVariable long attributeId){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		List<CompletePermissions> permissionsList = null;
		Attribute attribute = new Attribute();;
		try {
			attribute = entityTypeManager.loadAttribute(attributeId);
		} catch (DataException e) {
			logger.error(e.getMessage(),e);
		}
		try {
			permissionsList = entityTypeManager.retrieveAttributePermissions(attribute);
		} catch (DataException e) {
			logger.error("ManageEntityTypeController.showAttributePermissions:-Error while retreving Attribute Permissions "+attribute.getName());
		}catch (Exception e) {
			logger.error("ManageEntityTypeController.showAttributePermissions:-Error while retreving Attribute Permissions "+attribute.getName());
		}
		view.getAttributesMap().put("permissionsList",permissionsList);
		view.getAttributesMap().put("attribute",attribute);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return view;
	}
	
	@RequestMapping(value = {"/attribute/permission/add","/*/attribute/permission/add"}, method = RequestMethod.POST)
	public @ResponseBody String addAttributePermissions(@RequestParam("ObjId")long id,@RequestParam("role")String role,@RequestParam("permission")String permission){
		Attribute attribute;
			try {
			if(id > 0){
				attribute = entityTypeManager.loadAttribute(id);
				entityTypeManager.addPermissionOnAttribute(attribute, role, permission);
			}
		} catch (DataException e) {
			logger.error(e.getMessage(),e);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return "added";
	}
	
	

	/**
	 * @param entityTypeManager the entityTypeManager to set
	 */
	@Autowired
	public void setEntityTypeManager(EntityTypeManager entityTypeManager) {
		this.entityTypeManager = entityTypeManager;
	}

	/**
	 * @param entityTypeValidator the entityTypeValidator to set
	 */
	@Autowired
	public void setEntityTypeValidator(Validator entityTypeValidator) {
		this.entityTypeValidator = entityTypeValidator;
	}
	
	@Autowired
	public void setAttributeValidator(Validator attributeValidator) {
		this.attributeValidator = attributeValidator;
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
	
	

}

