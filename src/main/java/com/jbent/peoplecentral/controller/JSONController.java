package com.jbent.peoplecentral.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.manager.EntityManager;
import com.jbent.peoplecentral.model.manager.EntityTypeManager;
import com.jbent.peoplecentral.model.pojo.Attribute;
import com.jbent.peoplecentral.model.pojo.AttributeFieldType;
import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;
import com.jbent.peoplecentral.model.pojo.Entity;
import com.jbent.peoplecentral.model.pojo.EntityType;
import com.jbent.peoplecentral.model.pojo.Regex;
import com.jbent.peoplecentral.model.pojo.Attribute.Attributes;
import com.jbent.peoplecentral.model.pojo.EntityType.EntityTypes;
import com.jbent.peoplecentral.web.SessionContext;
/**
 * 
 * @author Jason Tesser
 *
 */
@Controller
public class JSONController extends WebApplicationObjectSupport {

	private EntityTypeManager entityTypeManager;
	@Autowired
	private EntityManager entityManager;
	
	@RequestMapping(value = {"/app/json/entity/search/entitytypes/{query}","/*/json/entity/search/entitytypes/{query}"}, method = RequestMethod.GET)
	public MappingJackson2JsonView entityTypesSearchResults(@PathVariable String query,Model model){
		List<EntityType> ets = null;
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		Map <String, Object> m1 = new HashMap<String, Object>();
		m1.put("etname", "All");
		m1.put("id", "all");
		ret.add(m1);
		try {
			ets = entityManager.searchResultsEntityType(query);
			if(ets != null && ets.size()>0){
//				SortedMap<String, Object> ret = new TreeMap<String, Object>();
				for (EntityType entityType : ets) {
					Map <String, Object> m = new HashMap<String, Object>();
					m.put("etname", entityType.getName());
					m.put("id", entityType.getId());
					ret.add(m);
				}
//				ret.put("entityTypes", m);
			}
		} catch (DataException e) {
			logger.error(e.getMessage(),e);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		Map <String, Object> m2 = new HashMap<String, Object>();
		m2.put("etname", "");
		m2.put("id", "blank");
		ret.add(m2);
		model.addAttribute("identifier", "id");
		model.addAttribute("label", "etname");
		model.addAttribute("items",ret);
		model.addAttribute("client", ClientManageUtil.loadClientSchema());
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = {"/app/json/simplesearch/entitytype/list","/*/json/simplesearch/entitytype/list"}, method = RequestMethod.GET)
	public MappingJackson2JsonView loadEntityTypesForSearch(Model model){
		List<EntityType> ets = null;
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		Map <String, Object> m1 = new HashMap<String, Object>();
		m1.put("etname", "All");
		m1.put("id", "all");
		ret.add(m1);
		try {
			ets = entityTypeManager.loadEntiyTypeDropDownList();
			if(ets != null && ets.size()>0){
//				SortedMap<String, Object> ret = new TreeMap<String, Object>();
				for (EntityType entityType : ets) {
					if(entityType.getId() != EntityTypes.SAVEDSEARCHES.getValue()){
						Map <String, Object> m = new HashMap<String, Object>();
						m.put("etname", entityType.getName());
						m.put("id", entityType.getId());
						ret.add(m);
					}
				}
//				ret.put("entityTypes", m);
			}
		} catch (DataException e) {
			logger.error(e.getMessage(),e);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		Map <String, Object> m2 = new HashMap<String, Object>();
		m2.put("etname", "");
		m2.put("id", "blank");
		ret.add(m2);
		model.addAttribute("identifier", "id");
		model.addAttribute("label", "etname");
		model.addAttribute("items",ret);
		model.addAttribute("client", ClientManageUtil.loadClientSchema());
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = {"/app/json/entitytype/list","/*/json/entitytype/list"}, method = RequestMethod.GET)
	public MappingJackson2JsonView loadEntityTypes(HttpServletRequest request, Model model){
		List<EntityType> ets = null;
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		try {
			ets = entityTypeManager.loadEntiyTypeDropDownList();
			if(ets != null && ets.size()>0){
//				SortedMap<String, Object> ret = new TreeMap<String, Object>();
				for (EntityType entityType : ets) {
					if(isListedInCreateEntityDropdown(entityType.getId())){
						Map <String, Object> m = new HashMap<String, Object>();
						m.put("etname", entityType.getName());
						m.put("id", entityType.getId());
						ret.add(m);
					}
				}
//				ret.put("entityTypes", m);
			}
		} catch (DataException e) {
			logger.error(e.getMessage(),e);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		model.addAttribute("identifier", "id");
		model.addAttribute("label", "etname");
		model.addAttribute("items",ret);
		model.addAttribute("client", ClientManageUtil.loadClientSchema());
		return new MappingJackson2JsonView();
	}
	
	private boolean isListedInCreateEntityDropdown(long entityTypeId){
		if(entityTypeId == EntityTypes.SAVEDSEARCHES.getValue()
				|| entityTypeId == EntityTypes.FOLDER.getValue()){
			return false;
		}
		return true;
	}
	@RequestMapping(value = {"/app/json/attribute/{entityType}","/*/json/attribute/{entityType}"}, method = RequestMethod.GET)
	public MappingJackson2JsonView loadAttributes(@PathVariable long entityType, Model model){
		EntityType et = null;
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		
		try {
			et = entityTypeManager.load(entityType);
			if(et != null){
//				SortedMap<String, Object> ret = new TreeMap<String, Object>();
				List<Attribute> attributes = entityTypeManager.filterAttributes(et.getAttributes());
				
				for (Attribute att : attributes) {
					Map <String, Object> m = new HashMap<String, Object>();
					m.put("attname", att.getName());
					m.put("id", att.getId());
					ret.add(m);
				}
//				ret.put("entityTypes", m);
				
			}
		} catch (DataException e) {
			logger.error(e.getMessage(),e);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		model.addAttribute("identifier", "id");
		model.addAttribute("label", "attname");
		model.addAttribute("items",ret);
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = {"/app/json/attribute/validation/{fieldTypeId}","/*/json/attribute/validation/{fieldTypeId}"}, method = RequestMethod.POST)
	public MappingJackson2JsonView loadValidation(@PathVariable("fieldTypeId")long fieldTypeId,Model model){
		List<Regex> regexes = null;
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		try {
			regexes = entityTypeManager.loadRegex(fieldTypeId);
			if(regexes != null && regexes.size()>0){
//				SortedMap<String, Object> ret = new TreeMap<String, Object>();
				for (Regex rx : regexes) {
					Map <String, Object> m = new HashMap<String, Object>();
					m.put("name", rx.getDisplayName());
					m.put("id", rx.getRegexId());
					m.put("pattern", rx.getPattern());
					m.put("custom", rx.getCustom());
					ret.add(m);
				}
//				ret.put("entityTypes", m);
			}
		} catch (DataException e) {
			logger.error(e.getMessage(),e);
			regexes = new ArrayList<Regex>();
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			regexes = new ArrayList<Regex>();
		}
		model.addAttribute("identifier", "id");
		model.addAttribute("label", "name");
		model.addAttribute("items",ret);
		model.addAttribute("regexes",regexes);
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = {"/app/json/fieldtype/list","/*/json/fieldtype/list"}, method = RequestMethod.GET)
	public MappingJackson2JsonView loadFieldTypes(Model model){
		List<AttributeFieldType> fields = null;
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		try {
			fields = entityTypeManager.loadAttributeFieldTypes();
			if(fields != null && fields.size()>0){
//				SortedMap<String, Object> ret = new TreeMap<String, Object>();
				for (AttributeFieldType f : fields) {
					Map <String, Object> m = new HashMap<String, Object>();
					m.put("name", f.getName());
					m.put("id", f.getFieldTypeId());
					m.put("displayName", f.getDisplayLabel());
					m.put("order", f.getDisplayOrder());
					ret.add(m);
				}
//				ret.put("entityTypes", m);
			}

		} catch (DataException e) {
			logger.error(e.getMessage(),e);
			fields = new ArrayList<AttributeFieldType>();
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			fields = new ArrayList<AttributeFieldType>();
		}
		model.addAttribute("identifier", "id");
		model.addAttribute("label", "name");
		model.addAttribute("items",ret);
		return new MappingJackson2JsonView();
	}
	

	@RequestMapping(value = {"/json/fieldtype/options/list","/*/json/fieldtype/options/list"}, method = RequestMethod.POST)
	public View fieldTypeOptions(@RequestParam("fieldTypeId")long fieldTypeId,Model model) throws DataException{
		AttributeFieldType fieldType = null;
		fieldType = entityTypeManager.loadAttributeFieldTypeOptions(fieldTypeId);
		model.addAttribute("client", ClientManageUtil.loadClientSchema());
		model.addAttribute("fieldType", fieldType);
		model.addAttribute("fieldTypeId", fieldTypeId);
		SessionContext.setFiedlTypeSessionContext(fieldType);
		return new MappingJackson2JsonView();
	}
	
	
	@RequestMapping(value = {"/app/json/roles/available/{entityId}","/*/json/roles/available/{entityId}"}, method = RequestMethod.GET)
	public MappingJackson2JsonView loadAvailableRoles(@PathVariable("entityId")long entityId,Model model){
		List<AttributeValueStorage> avs = null;
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		List<String> availableRoles = new ArrayList<String>();
		try {
			avs = entityManager.loadAvailableRoles(entityId);
			if(avs != null && avs.size()>0){
				for (AttributeValueStorage f : avs) {
					Map <String, Object> m = new HashMap<String, Object>();
					m.put("rolName", f.getValueVarchar());
					m.put("id", f.getEntityId());
					ret.add(m);
					availableRoles.add(f.getValueVarchar());
				}
			}
		} catch (DataException e) {
			logger.error(e.getMessage(),e);
			avs = new ArrayList<AttributeValueStorage>();
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			avs = new ArrayList<AttributeValueStorage>();
		}
		model.addAttribute("identifier", "id");
		model.addAttribute("label", "rolName");
		model.addAttribute("items",ret);
		model.addAttribute("availableRoles", availableRoles);
		model.addAttribute("client", ClientManageUtil.loadClientSchema());
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = {"/app/json/users/available/{entityId}","/*/json/users/available/{entityId}"}, method = RequestMethod.GET)
	public MappingJackson2JsonView loadAvailableUsers(@PathVariable("entityId")long entityId,Model model){
		List<AttributeValueStorage> avs = null;
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		List<String> availableUsers = new ArrayList<String>();
		try {
			Entity entity = entityManager.loadEntity(entityId);
			avs = entityManager.loadAvailableUsers(entity);
			if(avs != null && avs.size()>0){
				for (AttributeValueStorage f : avs) {
					Map <String, Object> m = new HashMap<String, Object>();
					m.put("userName", f.getValueVarchar());
					m.put("id", f.getEntityId());
					ret.add(m);
					availableUsers.add(f.getValueVarchar());
				}
			}
		} catch (DataException e) {
			logger.error(e.getMessage(),e);
			avs = new ArrayList<AttributeValueStorage>();
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			avs = new ArrayList<AttributeValueStorage>();
		}
		model.addAttribute("identifier", "id");
		model.addAttribute("label", "userName");
		model.addAttribute("items",ret);
		model.addAttribute("availableUsers", availableUsers);
		model.addAttribute("client", ClientManageUtil.loadClientSchema());
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = {"/app/json/users/assigned/load/{entityId}","/*/json/users/assigned/load/{entityId}"}, method = RequestMethod.GET)
	public MappingJackson2JsonView loadAssignedUsers(@PathVariable("entityId")long entityId,Model model){
		List<AttributeValueStorage> avs = null;
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		List<AttributeValueStorage> assignedUsers = new ArrayList<AttributeValueStorage>();
		try {
			Entity entity = entityManager.loadEntity(entityId);
			avs = entityManager.loadAssignedUsers(entity);
			if(avs != null && avs.size()>0){
				for (AttributeValueStorage f : avs) {
					Map <String, Object> m = new HashMap<String, Object>();
					m.put("userName", f.getValueVarchar());
					m.put("id", f.getEntityId());
					ret.add(m);
					assignedUsers.add(f);
				}
			}
		} catch (DataException e) {
			logger.error(e.getMessage(),e);
			avs = new ArrayList<AttributeValueStorage>();
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			avs = new ArrayList<AttributeValueStorage>();
		}
		model.addAttribute("identifier", "id");
		model.addAttribute("label", "userName");
		model.addAttribute("items",ret);
		model.addAttribute("assignedUsers", assignedUsers);
		model.addAttribute("client", ClientManageUtil.loadClientSchema());
		return new MappingJackson2JsonView();
	}

	@RequestMapping(value = {"/app/json/roles/assigned/load/{entityId}","/*/json/roles/assigned/load/{entityId}"}, method = RequestMethod.GET)
	public MappingJackson2JsonView loadAssignedRoles(@PathVariable("entityId")long entityId,Model model){
		List<AttributeValueStorage> avs = null;
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		List<AttributeValueStorage> assinedRoles = new ArrayList<AttributeValueStorage>();
		try {
			Entity entity = entityManager.loadEntity(entityId);
			avs = entityManager.loadAssignedRoles(entity);
			if(avs != null && avs.size()>0){
				for (AttributeValueStorage f : avs) {
					Map <String, Object> m = new HashMap<String, Object>();
					m.put("rolName", f.getValueVarchar());
					m.put("id", f.getEntityId());
					assinedRoles.add(f);
				}
			}
		} catch (DataException e) {
			logger.error(e.getMessage(),e);
			avs = new ArrayList<AttributeValueStorage>();
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			avs = new ArrayList<AttributeValueStorage>();
		}
		model.addAttribute("identifier", "id");
		model.addAttribute("label", "rolName");
		model.addAttribute("items",ret);
		model.addAttribute("assinedRoles", assinedRoles);
		model.addAttribute("client", ClientManageUtil.loadClientSchema());
		return new MappingJackson2JsonView();
	}

	
	@RequestMapping(value = {"/app/json/saved/query/{page}","/*/json/saved/query/{page}"}, method = RequestMethod.GET)
	public MappingJackson2JsonView savedSearches(@PathVariable String page,  Model model){
		List<Entity> ets = null;
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		Map <String, Object> m = null;
		try {
			ets = entityManager.loadEntities(EntityTypes.SAVEDSEARCHES.getValue());
			if(ets != null && ets.size()>0){
//				SortedMap<String, Object> ret = new TreeMap<String, Object>();
				for (Entity entity : ets) {
					List<AttributeValueStorage> avs = entity.getAttributeValueStorage();
					if(avs != null && avs.size()>0){
						for (AttributeValueStorage f : avs) {
							if(f.getId() == Attributes.QUERYNAME.getValue()){
								m = new HashMap<String, Object>();
								m.put("queryName", f.getValueVarchar());
								m.put("id", entity.getEntityId());
							}
							if(f.getId() == Attributes.SAVEDQUERY.getValue() && f.getValueVarchar().equalsIgnoreCase(page))
								ret.add(m);
						}
					}
				}
//				ret.put("entityTypes", m);
			}
		} catch (DataException e) {
			logger.error(e.getMessage(),e);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		model.addAttribute("identifier", "id");
		model.addAttribute("label", "queryName");
		model.addAttribute("items",ret);
		model.addAttribute("client", ClientManageUtil.loadClientSchema());
		return new MappingJackson2JsonView();
	}

	@RequestMapping(value = {"/app/json/report/filter/condition/list","/*/json/report/filter/condition//list"}, method = RequestMethod.GET)
	public MappingJackson2JsonView reportFilterConditions(Model model){
		
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		Map <String, Object> m = new HashMap<String, Object>();
		m.put("condtionName", "equalTo(=)");
		m.put("id", "=");
		ret.add(m);
		model.addAttribute("identifier", "id");
		model.addAttribute("label", "condtionName");
		model.addAttribute("items",ret);
		model.addAttribute("client", ClientManageUtil.loadClientSchema());
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = {"/app/json/search/types","/*/json/search/types"}, method = RequestMethod.GET)
	public MappingJackson2JsonView selectSearchTypes(Model model){
		
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		Map <String, Object> m1 = new HashMap<String, Object>(),m2 = new HashMap<String, Object>();
		m1.put("searchType", "Yes - I'll choose a saved search");
		m1.put("id", "yes");
		ret.add(m1);
		m2.put("searchType", "No - I'll build a new search query");
		m2.put("id", "no");
		ret.add(m2);
		model.addAttribute("identifier", "id");
		model.addAttribute("label", "searchType");
		model.addAttribute("items",ret);
		model.addAttribute("client", ClientManageUtil.loadClientSchema());
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = {"/app/json/report/types","/*/json/report/types"}, method = RequestMethod.GET)
	public MappingJackson2JsonView selectReportTypes(Model model){
		
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		Map <String, Object> m1 = new HashMap<String, Object>(),m2 = new HashMap<String, Object>();
		m1.put("reportType", "Yes - I'll choose a saved Report");
		m1.put("id", "yes");
		ret.add(m1);
		m2.put("reportType", "No - I'll build a new Report query");
		m2.put("id", "no");
		ret.add(m2);
		model.addAttribute("identifier", "id");
		model.addAttribute("label", "reportType");
		model.addAttribute("items",ret);
		model.addAttribute("client", ClientManageUtil.loadClientSchema());
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = {"/app/json/search/methods","/*/json/search/methods"}, method = RequestMethod.GET)
	public MappingJackson2JsonView selectSearchMethods(Model model){
		
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		Map <String, Object> m1 = new HashMap<String, Object>(),m2 = new HashMap<String, Object>();
		m1.put("searchMethod", "Simple Method - Use the Query Builder Wizard");
		m1.put("id", "simple");
		ret.add(m1);
		m2.put("searchMethod", "Advanced Method - Type your query into a text area");
		m2.put("id", "advanced");
		ret.add(m2);
		model.addAttribute("identifier", "id");
		model.addAttribute("label", "searchMethod");
		model.addAttribute("items",ret);
		model.addAttribute("client", ClientManageUtil.loadClientSchema());
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = {"/app/json/search/condition/type","/*/json/search/condition/type"}, method = RequestMethod.GET)
	public MappingJackson2JsonView searchConditionTypes(Model model){
		
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		Map <String, Object> m1 = new HashMap<String, Object>(),m2 = new HashMap<String, Object>();
		m1.put("qryConditionType", "all");
		m1.put("id", "all");
		ret.add(m1);
		m2.put("qryConditionType", "any");
		m2.put("id", "any");
		ret.add(m2);
		model.addAttribute("identifier", "id");
		model.addAttribute("label", "qryConditionType");
		model.addAttribute("items",ret);
		model.addAttribute("client", ClientManageUtil.loadClientSchema());
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = {"/app/json/attribute/displayTypeFilter","/*/json/attribute/displayTypeFilter"}, method = RequestMethod.GET)
	public MappingJackson2JsonView displayTypeMethod(Model model){
		
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		Map <String, Object> m1 = new HashMap<String, Object>(),m2 = new HashMap<String, Object>(),m3 = new HashMap<String, Object>(),m4 = new HashMap<String, Object>();
		m1.put("displayType", "Radio");
		m1.put("id", "Radio");
		ret.add(m1);
		m2.put("displayType", "Multi-Select");
		m2.put("id", "Multi-Select");
		ret.add(m2);
		m3.put("displayType", "Checkbox");
		m3.put("id", "Checkbox");
		ret.add(m3);
		m4.put("displayType", "ComboBox");
		m4.put("id", "ComboBox");
		ret.add(m4);
		model.addAttribute("identifier", "id");
		model.addAttribute("label", "displayType");
		model.addAttribute("items",ret);
		model.addAttribute("client", ClientManageUtil.loadClientSchema());
		return new MappingJackson2JsonView();
	}


	@RequestMapping(value = {"/app/json/attribute/choiceSelectType","/*/json/attribute/choiceSelectType"}, method = RequestMethod.GET)
	public MappingJackson2JsonView choiceSelectType(Model model){
		
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		Map <String, Object> m1 = new HashMap<String, Object>(),m2 = new HashMap<String, Object>();
		m1.put("choiceSelectType", "Yes - I'll select a Exist Type");
		m1.put("id", "yes");
		ret.add(m1);
		m2.put("choiceSelectType", "No - I'll build a New Type");
		m2.put("id", "no");
		ret.add(m2);
		
		model.addAttribute("identifier", "id");
		model.addAttribute("label", "choiceSelectType");
		model.addAttribute("items",ret);
		model.addAttribute("client", ClientManageUtil.loadClientSchema());
		return new MappingJackson2JsonView();
	}

	@RequestMapping(value = {"/app/json/attribute/entities/load/{attributeId}","/*/json/attribute/entities/load/{attributeId}"}, method = RequestMethod.GET)
	public MappingJackson2JsonView loadAttributeValues(@PathVariable long attributeId, Model model){
		Attribute att = null;List<Entity> entites = null;
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		
		try {
			att = entityTypeManager.loadAttribute(attributeId);
			if(att != null){
				entites = entityManager.loadEntities(att.getEntityTypeId());
				if(entites != null){
					for(Entity entity: entites){
						List<AttributeValueStorage> attValues = entity.getAttributeValueStorage();
						if(attValues != null && attValues.size()>0){
							for(AttributeValueStorage attValue:attValues){
								if(attValue.getId() == attributeId){
									Map <String, Object> m = new HashMap<String, Object>();
									m.put("attValue", attValue.getValueVarchar());
									m.put("id", attValue.getAttributeValueId());
									ret.add(m);
								}
							}
						}	
					}
				}
			}
		} catch (DataException e) {
			logger.error(e.getMessage(),e);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		model.addAttribute("identifier", "id");
		model.addAttribute("label", "attValue");
		model.addAttribute("items",ret);
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = {"/app/json/useremail/load","/*/json/useremail/load"}, method = RequestMethod.GET)
	public MappingJackson2JsonView userEmailsLoad( Model model){
		 long limit = 100;
		 long offset = 0;		
		 long entityTypeId = 38;
		 String startHighlight = "<b>";
		 String endHighlight = "</b>";
		 List<Entity> entites = null;
		 List<AttributeValueStorage> avs = null;	 
		 List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		 try {
			 entites =  entityManager.entitySearch("type:people",entityTypeId,startHighlight,endHighlight, limit, offset);
			 for(Entity entity: entites){
				 avs = entity.getAttributeValueStorage();
				 if(avs != null && avs.size()>0){
					 Map <String, Object> m = new HashMap<String, Object>();
					 boolean isEmailId = false;
					 for (AttributeValueStorage f : avs) {
							if(f.getId() == 125)
								m.put("username", f.getValueVarchar());
							if(f.getId() == 127){
								m.put("email", f.getValueVarchar());
							    isEmailId = true;
							}	
						}
					 if(isEmailId)
					  		ret.add(m);
					}
			 }
			 
		} catch (DataException e) {
			logger.error("Error simple searching entities : " + e.getMessage(),e);
		}catch (Exception e) {
			logger.error("Error simple searching entities : " + e.getMessage(),e);
		}
		 model.addAttribute("items",ret);	 
		 model.addAttribute("client", ClientManageUtil.loadClientSchema());
		
		return new MappingJackson2JsonView();
	}

	
	/**
	 * @param entityTypeManager the entityTypeManager to set
	 */
	@Autowired
	public void setEntityTypeManager(EntityTypeManager entityTypeManager) {
		this.entityTypeManager = entityTypeManager;
	}
	
}
