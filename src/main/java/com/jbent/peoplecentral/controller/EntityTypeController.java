/**
 * 
 */
package com.jbent.peoplecentral.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.manager.EntityTypeManager;
import com.jbent.peoplecentral.model.pojo.Attribute;
import com.jbent.peoplecentral.model.pojo.AttributeDataType;
import com.jbent.peoplecentral.model.pojo.AttributeFieldType;
import com.jbent.peoplecentral.model.pojo.EntityType;
import com.jbent.peoplecentral.model.pojo.Regex;
import com.jbent.peoplecentral.model.pojo.Attribute.DataType;
import com.jbent.peoplecentral.model.pojo.Attribute.FieldType;
import com.jbent.peoplecentral.permission.Permission.Permissions;

/**
 * @author Jason Tesser
 *
 */
@Controller
@SuppressWarnings("unchecked")
public class EntityTypeController extends WebApplicationObjectSupport {

	private EntityTypeManager entityTypeManager;
	
	@RequestMapping(value = {"/save/entitytype/saveattribute","/*/save/entitytype/saveattribute"}, method = RequestMethod.POST)
	public MappingJackson2JsonView saveAttribute(@ModelAttribute("attribute")Attribute att){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		List<EntityType> entityTypeList = new ArrayList<EntityType>();
		try {
			List<Attribute> atts = new ArrayList<Attribute>();
			atts.add(att);
			entityTypeList = entityTypeManager.saveAttributes(atts);
		} catch (DataException e) {
			logger.error("saveAttribute:- Error saving Attribute :"+e);
		}
		view.getAttributesMap().put("entityTypes", entityTypeList);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return view;
	}
	

	@RequestMapping(value = {"/edit/attribute/{attributeId}","/*/edit/attribute/{attributeId}"}, method = RequestMethod.GET)
	public MappingJackson2JsonView loadAttribute(@PathVariable long attributeId){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		Attribute attribute = new Attribute();
		try {
			attribute = entityTypeManager.loadAttribute(attributeId);
			} catch (DataException e) {
				logger.error("saveAttribute:- Error loading Attribute :"+e);
			}		
		view.getAttributesMap().put("attribute", attribute);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return view;
	}
	

	@RequestMapping(value = {"app/entitytype/saveattribute/{attributeId}","/*/entitytype/saveattribute/{attributeId}"}, method = RequestMethod.GET)
	public MappingJackson2JsonView addAttribute(@PathVariable long attributeId){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		Attribute attribute;
		if(attributeId == 0){
			attribute = new Attribute();
		}else{
			attribute = new Attribute();
		}
		view.getAttributesMap().put("attribute", attribute);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return view;

	}
	
	@RequestMapping(value = {"/app/entitytype/list","/*/entitytype/list"}, method = RequestMethod.GET)
	public MappingJackson2JsonView getEntityTypes(){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		List<EntityType> ets = null;
		long offset = 0;		 
		long totalPages = 0;		
		long limit =100;
		long page = 1;		
		long begin = 0;
		long end = 0;		
		double totalRecordsEt = 0;		
		try {
			ets = entityTypeManager.entityTypeTableload(limit,offset);
			if(ets!=null && ets.size() > 0 ){
				totalRecordsEt=ets.get(0).getCount(); 
				double totalPage=totalRecordsEt/limit;
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
			logger.error("error in entityTypelist"+ e.getMessage(),e);
		}
		view.getAttributesMap().put("entityTypes", ets);
		view.getAttributesMap().put("offEt", offset);
		view.getAttributesMap().put("totalRecordsEt", (long)totalRecordsEt);
		view.getAttributesMap().put("totalPages", totalPages);
		view.getAttributesMap().put("beginEt", begin);
		view.getAttributesMap().put("endEt", end);
		view.getAttributesMap().put("limitEt", limit);
		view.getAttributesMap().put("pageEt", page);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return view;
	}
	
	@RequestMapping(value = {"/app/entitytype/list","/*/entitytype/list"}, method = RequestMethod.POST)
	public MappingJackson2JsonView listEntityTypes(@RequestParam("pageEt")long page,@RequestParam("beginEt") long begin,@RequestParam("endEt") long end,@RequestParam("limitEt") long limit){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		List<EntityType> ets = null;
		long offset = 0;		 
		long totalPages = 0;		
		double totalRecordsEt = 0;
		if(page>1){
			offset= limit*(page-1);
	    }		
		try {
			ets = entityTypeManager.entityTypeTableload(limit,offset);
			if(ets!=null && ets.size() > 0 ){
				totalRecordsEt=ets.get(0).getCount();  
				double totalPage=totalRecordsEt/limit;
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
			logger.error("listEntityTypes:Unable to listEntityTypes error:"+e.getMessage(),e);
		}
		view.getAttributesMap().put("entityTypes", ets);
		view.getAttributesMap().put("offEt", offset);
		view.getAttributesMap().put("totalRecordsEt", (long)totalRecordsEt);
		view.getAttributesMap().put("totalPages", totalPages);
		view.getAttributesMap().put("beginEt", begin);
		view.getAttributesMap().put("endEt", end);
		view.getAttributesMap().put("limitEt", limit);
		view.getAttributesMap().put("pageEt", page);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return view;
	}
	
	@RequestMapping(value = {"/edit/entitytype/{entityTypeId}","/*/edit/entitytype/{entityTypeId}"}, method = RequestMethod.GET)
	public MappingJackson2JsonView editEntityType(@PathVariable long entityTypeId){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		EntityType et = null;
		if(entityTypeId < 1){
			et = new EntityType();
		}else{
			try {
				et = entityTypeManager.load(entityTypeId);
			} catch (DataException e) {
				logger.error("editEntityType.Error editEntityType"+e.getMessage(),e);
			}
		}
		view.getAttributesMap().put("entityType", et);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return view;
	}
	
	@RequestMapping(value = {"/save/entitytype","/*/save/entitytype"}, method = RequestMethod.POST)
	public MappingJackson2JsonView saveEntityType(@ModelAttribute("entityType")EntityType entityType,BindingResult result){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
//		new PersonValidator().validate(person, result);
		try {
			entityTypeManager.save(entityType);
		} catch (DataException e) {
			logger.error("Unable to save : " + e.getMessage(), e);
		}
		List<EntityType> ets = null;
		/*try {
			ets = entityTypeManager.load();
		} catch (DataException e) {
			logger.error(e.getMessage(),e);
		}*/
		view.getAttributesMap().put("entityTypes", ets);
		return view;
	}
	
	@RequestMapping(value = {"/app/attribute/datatypes/list","/*/app/attribute/datatypes/list"}, method = RequestMethod.GET)
	public void loadAttributeDatatypes(){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		List<AttributeDataType>  attributeDataType = null;
	
		try {
			attributeDataType = entityTypeManager.loadAttributeDataTypes();
			} catch (DataException e) {
				logger.error("Unable to loadAttributeDatatypes : " + e.getMessage(), e);
			}		
		view.getAttributesMap().put("attributeDataType", attributeDataType);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		
	}
	
	@RequestMapping(value = {"/app/attribute/fieldtypes/list","/*/app/attribute/fieldtypes/list"}, method = RequestMethod.GET)
	public void loadAttributeFieldtypes(){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		List<AttributeFieldType>  attributeFieldType = null;
	
		try {
			attributeFieldType = entityTypeManager.loadAttributeFieldTypes();
			} catch (DataException e) {
				logger.error("Unable to loadAttributeFieldtypes : " + e.getMessage(), e);
			}		
		view.getAttributesMap().put("attributeFieldType", attributeFieldType);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		
	}
	
	@RequestMapping(value = {"/app/regex/{fieldtypeId}","/*/app/regex/{fieldtypeId}"}, method = RequestMethod.GET)
	public void loadRegex(@PathVariable long fieldtypeId){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		List<Regex>  regexList = null;
	
		try {
			regexList = entityTypeManager.loadRegex(fieldtypeId);
			} catch (DataException e) {
				logger.error("Unable to loadRegex : " + e.getMessage(), e);
			}		
		view.getAttributesMap().put("regexList", regexList);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
	}
	
	/**
	 * @param entityTypeManager the entityTypeManager to set
	 */
	@Autowired
	public void setEntityTypeManager(EntityTypeManager entityTypeManager) {
		this.entityTypeManager = entityTypeManager;
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
