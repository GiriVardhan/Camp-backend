package com.jbent.peoplecentral.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.manager.DynamicJasperManager;
import com.jbent.peoplecentral.model.pojo.Attribute;
import com.jbent.peoplecentral.model.pojo.CompletePermissions;
import com.jbent.peoplecentral.model.pojo.Report;
import com.jbent.peoplecentral.model.pojo.EntityType.EntityTypes;
import com.jbent.peoplecentral.permission.Permission.Permissions;
import com.jbent.peoplecentral.web.SessionContext;

/**
 * @author Prasad BHVN
 *
 */

@Controller  


public class DynamicJasperReportController extends WebApplicationObjectSupport {

	@Autowired
	private DynamicJasperManager jasperManager;

   	
	@RequestMapping(value={"/report","/*/report"},method=RequestMethod.GET)	
  	public MappingJackson2JsonView dynamicJasper(@ModelAttribute("dynamicreport")Report dynamicReport, BindingResult result) { 
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.getAttributesMap().put("client",ClientManageUtil.loadClientSchema());
		view.getAttributesMap().put("page","report");
		return view;
  	}
	
	
	@RequestMapping(value={"/report/simple/query/build/{entitytypeid}","/*/report/simple/query/build/{entitytypeid}"},method=RequestMethod.POST)	
  	public View buildReport(HttpServletRequest request, HttpServletResponse response,@PathVariable("entitytypeid") long entitytypeid, @RequestParam("eid")long entityId, @RequestParam("querySaved")String querySaved, @RequestParam("conditionType") String conditionType,@RequestParam("fileterAttArray") String fileterAttArray,@RequestParam("fileterConditionArray") String fileterConditionArray,@RequestParam("fileterConditionValueArray") String fileterConditionValueArray,@RequestParam("queryName") String queryName,@RequestParam("submitType") String submitType,Model model) { 
		model.addAttribute("client",ClientManageUtil.loadClientSchema());
		JasperPrint jp;
		try {
			jp = jasperManager.simpleQueryJSPrint(request,response,entitytypeid,entityId,querySaved,conditionType,fileterAttArray,fileterConditionArray,fileterConditionValueArray,queryName,submitType,model);
			SessionContext jasperPrintSC = SessionContext.getJasperPrintSessionContext();
			if(jasperPrintSC.getJasperPrint() != null){
				 jp = jasperPrintSC.getJasperPrint();
				if (jp != null){
					model.addAttribute("totalPages",jp.getPages().size());
				}
			}	
		} catch (DataException e) {
			logger.error("Unable to buildReport : " + e.getMessage(), e);
		}catch (Exception e) {
			logger.error("Unable to buildReport : " + e.getMessage(), e);
		}
		
		return new MappingJackson2JsonView();
  	}
	
	@RequestMapping(value={"/report/saved/search/build/{entityId}","/*/report/saved/search/build/{entityId}"},method=RequestMethod.POST)	
  	public View buildSavedSearchReport(HttpServletRequest request, HttpServletResponse response,@PathVariable("entityId") long entityId,Model model) { 
		model.addAttribute("client",ClientManageUtil.loadClientSchema());
		JasperPrint jp;
		try {
			jp = jasperManager.savedSearchJSPrint(request,response,entityId,model);
			SessionContext jasperPrintSC = SessionContext.getJasperPrintSessionContext();
			if(jasperPrintSC.getJasperPrint() != null){
				 jp = jasperPrintSC.getJasperPrint();
				if (jp != null){
					model.addAttribute("totalPages",jp.getPages().size());
				}
			}	
		} catch (DataException e) {
			logger.error("Unable to buildSavedSearchReport : " + e.getMessage(), e);
		}catch (Exception e) {
			logger.error("Unable to buildSavedSearchReport : " + e.getMessage(), e);
		}
		
		return new MappingJackson2JsonView();
  	}
	
	@RequestMapping(value={"/report/advanced/query/build","/*/report/advanced/query/build"},method=RequestMethod.POST)	
  	public View buildAdvancedQuery(HttpServletRequest request, HttpServletResponse response,@RequestParam("queryString") String queryString,@RequestParam("queryName") String queryName,@RequestParam("submitType") String submitType,@RequestParam("entityId")long entityId,@RequestParam("querySaved")String querySaved,Model model) { 
		model.addAttribute("client",ClientManageUtil.loadClientSchema());
		JasperPrint jp;
		try {
			jp = jasperManager.advancedQueryJSPrint(request, response, queryString, queryName,submitType,querySaved,entityId, model);
			SessionContext jasperPrintSC = SessionContext.getJasperPrintSessionContext();
			if(jasperPrintSC.getJasperPrint() != null){
				 jp = jasperPrintSC.getJasperPrint();
				if (jp != null){
					model.addAttribute("totalPages",jp.getPages().size());
				}
			}	
		} catch (DataException e) {
			logger.error("Unable to buildAdvancedQuery : " + e.getMessage(), e);
		}catch (Exception e) {
			logger.error("Unable to buildAdvancedQuery : " + e.getMessage(), e);
		}
		
		return new MappingJackson2JsonView();
  	}
	
	@RequestMapping(value={"/report/display","/*/report/display"},method=RequestMethod.POST)	
  	public MappingJackson2JsonView displayReportResult(HttpServletRequest request, HttpServletResponse response,@RequestParam("entityTypeName") String entityTypeName) { 
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.getAttributesMap().put("client",ClientManageUtil.loadClientSchema());
		SessionContext jasperPrintSC = SessionContext.getJasperPrintSessionContext();
		if(jasperPrintSC.getJasperPrint() != null){
			JasperPrint  jp = jasperPrintSC.getJasperPrint();
			if (jp != null){
				view.getAttributesMap().put("totalPages",jp.getPages().size());
				view.getAttributesMap().put("etName",entityTypeName);
			}
		}	
		//model.addAttribute("totalRecords",totalRecords);
		return view;
  	}
	
	@RequestMapping(value={"/report/display/{pageNum}","/*/report/display/{pageNum}"},method=RequestMethod.POST)	
  	public View loadPage(HttpServletRequest request, HttpServletResponse response,@PathVariable int pageNum,Model model) { 
		try {
			jasperManager.loadReportPage(request, response, pageNum, model);
		} catch (DataException e) {
			logger.error("Unable to loadPage : " + e.getMessage(), e);
		}catch (Exception e) {
			logger.error("Unable to loadPage : " + e.getMessage(), e);
		}
		model.addAttribute("client",ClientManageUtil.loadClientSchema());
		return new MappingJackson2JsonView();
  	}
	
	@RequestMapping(value={"/report/attributes/load/{entityTypeId}","/*/report/attributes/load/{entityTypeId}"},method=RequestMethod.POST)	
  	public View attributesLoad(@PathVariable long entityTypeId,Model model) { 
		List<Attribute> attirbutes = null;
		try {
			attirbutes = jasperManager.loadReportAttributes(entityTypeId, model);
		} catch (DataException e) {
			logger.error("Unable to attributesLoad : " + e.getMessage(), e);
		}catch (Exception e) {
			logger.error("Unable to attributesLoad : " + e.getMessage(), e);
		}
		model.addAttribute("client",ClientManageUtil.loadClientSchema());
		model.addAttribute("attirbutes",attirbutes);
		return new MappingJackson2JsonView();
  	}


	@RequestMapping(value={"/report/build/query","/*/report/build/query"},method=RequestMethod.POST)	
  	public MappingJackson2JsonView reportQueryBuilder() { 
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.getAttributesMap().put("client",ClientManageUtil.loadClientSchema());
		view.getAttributesMap().put("ConditionQueryRowNum",0);
		view.getAttributesMap().put("page","report");
		return view;
  	}
	
	
	
	@RequestMapping(value={"/report/simple/query/permissions/{entityId}","/*/report/simple/query/permissions/{entityId}"},method=RequestMethod.POST)	
  	public View simpleQueryPermission(@PathVariable long entityId,Model model) { 
		model.addAttribute("client",ClientManageUtil.loadClientSchema());
		List<CompletePermissions> combinedRolesForEntity = null;	
		try {
			combinedRolesForEntity = jasperManager.queryPemissions(entityId);
			if(combinedRolesForEntity != null){
				model.addAttribute("combinedRolesForEntity",combinedRolesForEntity);
			}
		} catch (DataException e) {
			logger.error("Unable to perform simpleQueryPermission : " + e.getMessage(), e);
		}catch (Exception e) {
			logger.error("Unable to perform simpleQueryPermission : " + e.getMessage(), e);
		}
		return new MappingJackson2JsonView();
  	}
	
	@RequestMapping(value={"/report/entitytype/export","/*/report/entitytype/export"},method=RequestMethod.POST)
  	public View simpleQueryPermission(Model model,HttpServletResponse response,@RequestParam("exportingType") String exportingType,@RequestParam("entityTypeName") String entityTypeName){ 
		try {
			jasperManager.export(response, exportingType, entityTypeName);
		} catch (DataException e) {
			logger.error("Unable to perform simpleQueryPermission : " + e.getMessage(), e);
		} catch (Exception e) {
			logger.error("Unable to perform simpleQueryPermission : " + e.getMessage(), e);
		}
		model.addAttribute("client",ClientManageUtil.loadClientSchema());
		return new MappingJackson2JsonView();
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

	
   	
  }