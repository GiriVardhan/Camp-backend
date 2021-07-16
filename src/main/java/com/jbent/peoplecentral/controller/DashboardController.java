package com.jbent.peoplecentral.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.manager.EntityManager;
import com.jbent.peoplecentral.model.manager.EntityTypeManager;
import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;
import com.jbent.peoplecentral.model.pojo.AuditSummary;
import com.jbent.peoplecentral.model.pojo.Entity;
import com.jbent.peoplecentral.model.pojo.Attribute.Attributes;
import com.jbent.peoplecentral.model.pojo.EntityType.EntityTypes;
import com.jbent.peoplecentral.permission.PermissionManager;
import com.jbent.peoplecentral.web.SessionContext;

@Controller
@SuppressWarnings("unchecked")
public class DashboardController extends WebApplicationObjectSupport{
	
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private EntityTypeManager entityTypeManager;
	@Autowired
	private PermissionManager permissionManager;
	
	@RequestMapping(value = {"/dashboard","/*/dashboard"}, method = RequestMethod.GET)
	public RedirectView dashboard(Model model){
		String client = SessionContext.getSessionContext().getSchemaContext();
		model.addAttribute("client", ClientManageUtil.loadClientSchema());
		RedirectView rv = new RedirectView("/app/" + client);
		rv.setExposeModelAttributes(false);
		return rv;
	}

	//@RequestMapping(value = {"","/","/*","/*/"}, method = RequestMethod.GET)
	public MappingJackson2JsonView manage(){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		long limit = 20;long totalPages = 0;
		double totalRecords = 0;double totalPage;double tpages;
		List<Entity> ets = null,savedSearches =  new ArrayList<Entity>(),savedReports =  new ArrayList<Entity>();
		List<AuditSummary> wsa = null;
		Entity profile = null;
		
		try {
			ets = entityManager.loadEntities(EntityTypes.SAVEDSEARCHES.getValue());
			if(ets != null && ets.size()>0){
				for (Entity entity : ets) {
					List<AttributeValueStorage> avs = entity.getAttributeValueStorage();
					if(avs != null && avs.size()>0){
						for (AttributeValueStorage f : avs) {
							// filter the report entities
							if(f.getId() == Attributes.SAVEDQUERY.getValue() && f.getValueVarchar().equalsIgnoreCase("search")){
								savedSearches.add(entity);
							}else if(f.getId() == Attributes.SAVEDQUERY.getValue() && f.getValueVarchar().equalsIgnoreCase("report")){
								savedReports.add(entity);
							}
						}
					}
				}
			}
			 wsa = entityTypeManager.loadWorkStreamAudit(null,null,limit,0,0,"");
			 profile = entityManager.loadEntityByUsername(permissionManager.getUsername());
			 if(wsa != null && wsa.size() >=0){
				 	totalRecords= wsa.get(0).getAuditCount();  
					totalPage=totalRecords/limit;
					tpages = Math.ceil(totalPage);
					totalPages = (long) Math.abs(tpages);
			}
		} catch (DataException e) {
			logger.error("Error while loading dashboard:"+e.getMessage(),e);
		}catch (Exception e) {
			logger.error("Error while loading dashboard:"+e.getMessage(),e);
		}
		if(savedSearches.size() == 0)
			savedSearches= null;
		if(savedReports.size() == 0)
			savedReports= null;
		
		view.getAttributesMap().put("profile",profile);
		view.getAttributesMap().put("workStreamAudit",wsa);
		view.getAttributesMap().put("totalRecords",(long)totalRecords);
		view.getAttributesMap().put("totalPages",totalPages);
		view.getAttributesMap().put("limit",limit);
		view.getAttributesMap().put("savedSearches",savedSearches);
		view.getAttributesMap().put("savedReports",savedReports);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return view;
	}
	
	@RequestMapping(value = {"/dashboard/audit/summary/{pageNum}","/*/dashboard/audit/summary/{pageNum}"}, method = RequestMethod.POST)
	public MappingJackson2JsonView addPermissions(@PathVariable long pageNum,@RequestParam("action") String action,@RequestParam("limit") long limit,@RequestParam("totalRecords") long totalRecords){
		List<AuditSummary> wsa = null;
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		try {
			 wsa = entityTypeManager.loadWorkStreamAudit(null, null, limit, pageNum,totalRecords,action);
		} catch (DataException e) {
			logger.error("error in audit summary :"+ e.getMessage(),e);
		}
		view.getAttributesMap().put("workStreamAudit",wsa);
		return view;
	}	
	
	@RequestMapping(value = {"/dashboard/invalid/entities/view","/*/dashboard/invalid/entities/view"}, method = RequestMethod.GET)
	public MappingJackson2JsonView invalidEntitiesLoad(){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		List<Entity> invalidEntities = null;
		try {
			invalidEntities = entityManager.loadInvalidEntities();
		} catch (DataException e) {
			logger.error("error in invalid entities"+ e.getMessage(),e);
		}
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		view.getAttributesMap().put("invalidEntities",invalidEntities);
		return view;
	}
	
	@RequestMapping(value = {"/dashboard/password/edit/{entityId}","/*/dashboard/password/edit/{entityId}"}, method = RequestMethod.GET)
	public MappingJackson2JsonView passwordEditForm(@PathVariable("entityId") long entityId){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.getAttributesMap().put("entityId", entityId);	
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());		
		return view;
	}
	
	@RequestMapping(value = {"/dashboard/password/edit","/*/dashboard/password/edit"}, method = RequestMethod.POST)
	public MappingJackson2JsonView passwordEdit(@RequestParam("entityId") long entityId,@RequestParam("newpassword") String newPassword, Locale locale){
		boolean status = false;
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		try {
			status = entityManager.changePassword(entityId,newPassword);
		} catch (DataException e) {
			logger.error("error in change password :"+ e.getMessage(),e);
		}
		view.getAttributesMap().put("status", status);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());		
		return view;
	}
	
	@ModelAttribute("ENTITYPE")
	public Map EntityTypes(){
		final EntityTypes[] enums = EntityTypes.class.getEnumConstants();  
		Map<String, Integer> map = new HashMap<String, Integer>(enums.length);	
		for (EntityTypes anEnum : enums)
			map.put(anEnum.toString(), anEnum.getValue());
	
		return map;
	}

	@ModelAttribute("ATTRIBUTE")
	public Map Attributes(){
		final Attributes[] enums = Attributes.class.getEnumConstants();  
		Map<String, Integer> map = new HashMap<String, Integer>(enums.length);	
		for (Attributes anEnum : enums)
			map.put(anEnum.toString(), anEnum.getValue());
	
		return map;
	}

}
