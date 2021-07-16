package com.jbent.peoplecentral.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.manager.EntityManager;
import com.jbent.peoplecentral.model.manager.EntityTypeManager;
import com.jbent.peoplecentral.model.manager.ImportManager;
import com.jbent.peoplecentral.model.pojo.EntityType;
import com.jbent.peoplecentral.model.pojo.Import;
import com.jbent.peoplecentral.model.pojo.ImportEntitiesFailedRow;
import com.jbent.peoplecentral.util.FileUploadHelperUtil;

@Controller
public class ImportController extends WebApplicationObjectSupport{
	@Autowired
	private ImportManager importManager;
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private EntityTypeManager entityTypeManager;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private FileUploadHelperUtil fileUploadHelpUtil;
		
	@RequestMapping(value = {"/import","/*/import"}, method = RequestMethod.GET)
	public MappingJackson2JsonView importEntities() {
		MappingJackson2JsonView view = new MappingJackson2JsonView();	
		List <EntityType> entList = null;	
		try {
			entList = entityTypeManager.loadEntiyTypeDropDownList();
		} catch (DataException e) {
			logger.error("EntityController.uploadEntities:-Unable to load EntityTypes for DropDown : "+e.getMessage(),e);
		}catch (Exception e) {
			logger.error("EntityController.uploadEntities:-Unable to load EntityTypes for DropDown : "+e.getMessage(),e);
		}
		view.getAttributesMap().put("entList", entList);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return view;
   }
	
	@RequestMapping(value = {"/import","/*/import"}, method = RequestMethod.POST)
	public View importEntities(@RequestParam("entityTypeId") long entityTypeId,@RequestParam("file") MultipartFile file,Locale locale) {
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		String importedUid = null;
		List <EntityType> entList = null;
		UUID uid = null;
		Import importEntity = new Import();
		EntityType entityType = new EntityType();		
		if(!file.isEmpty() && entityTypeId > 0 ){
			try {
				uid = UUID.randomUUID();
				String filePath = fileUploadHelpUtil.getServerPath()+"/clientimport"+"/"+ClientManageUtil.loadClientSchema()+"/"+uid;
				boolean  status  = new File(filePath,file.getOriginalFilename()).mkdirs();
				File csvFile = new File(filePath,file.getOriginalFilename());
				if(status && csvFile != null){
					//	check file type
					int mid = file.getOriginalFilename().lastIndexOf(".");
					String ext = file.getOriginalFilename().substring(mid,file.getOriginalFilename().length()); 
					if(!ext.equalsIgnoreCase(".csv")){
						view.getAttributesMap().put("isError","true");
						view.getAttributesMap().put("msg",file.getOriginalFilename()+":"+messageSource.getMessage("entity.import.file.not.csv", null, locale));
						return new MappingJackson2JsonView();
					}				
					file.transferTo(csvFile);
					entityType = entityTypeManager.load(entityTypeId);
					if(entityType != null){
						importedUid = entityManager.importEntity(entityType,csvFile, uid.toString(),locale,view);
						
					}
					if(importedUid != null){			
						view.getAttributesMap().put("importedUid", importedUid);
						view.getAttributesMap().put("msg",messageSource.getMessage("entity.import.started", null, locale));
				    }
					entList = entityTypeManager.load();
				}
			} catch (IOException e) {
				logger.error("EntityController.importEntities:-Unable to Import file:"+e.getMessage(),e);
				//view.getAttributesMap().put("isError","true");
				view.getAttributesMap().put("msg",file.getOriginalFilename()+":"+messageSource.getMessage("error.entity.import.failed", null, locale));
			} catch (DataException e) {
				//view.getAttributesMap().put("isError", "true");
				view.getAttributesMap().put("genaralMsg",file.getOriginalFilename()+":"+messageSource.getMessage("error.entity.import.failed", null, locale));
				logger.error("EntityController.importEntities:-Unable to Import Entities:"+e.getMessage(),e);
			}catch (Exception e) {
				logger.error("EntityController.importEntities:-Unable to Import Entities:"+e.getMessage(),e);
			}		
		}
		view.getAttributesMap().put("entList", entList);
		view.getAttributesMap().put("entityTypeId",entityTypeId );
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		view.getAttributesMap().put("name", file.getOriginalFilename());
		view.getAttributesMap().put("importEntity", importEntity);
		view.getAttributesMap().put("permissionDone", null);
		view.getAttributesMap().put("flag", false);
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = {"/import/result/display","/*/import/result/display"}, method = RequestMethod.POST)
	public MappingJackson2JsonView displayImportResult(@RequestParam("importUID") String importUID,@RequestParam("entityTypeId") long entityTypeId,@RequestParam("importDone") boolean importDone,Locale locale) {
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		String permissionDone = null;
		Import importEntity = null;
		view.getAttributesMap().put("flag", false);
		try {
			importEntity = importManager.fetchImportResults(importUID);
			if(!importDone){				
				if(importEntity != null && importEntity.getTimeEnd() != null){
					view.getAttributesMap().put("msg", messageSource.getMessage("entity.import.permissions", null, locale));
					view.getAttributesMap().put("flag", true);
				}else{
					view.getAttributesMap().put("msg",messageSource.getMessage("entity.import.started", null, locale));
				}
				
			}else if(importEntity != null && importEntity.isCompleted()){
				permissionDone = "yes";
				view.getAttributesMap().put("msg", messageSource.getMessage("entity.import.completed", null, locale));
			}
		} catch (DataException e) {
			logger.error("fetchImportResult:Unable to fectch Imported Results :"+e.getMessage());
			view.getAttributesMap().put("msg", messageSource.getMessage("error.entity.import.failed", null, locale));
		}catch (Exception e) {
			logger.error("fetchImportResult:Error while fetching Imported Results:"+e.getMessage());
			view.getAttributesMap().put("msg", messageSource.getMessage("error.entity.import.failed", null, locale));
		}
		view.getAttributesMap().put("permissionDone", permissionDone);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		view.getAttributesMap().put("importEntity", importEntity);
		view.getAttributesMap().put("entityTypeId",entityTypeId );
		view.getAttributesMap().put("importedUid", importUID);
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = {"/import/failed/display","/*/import/failed/display"}, method = RequestMethod.POST)
	public MappingJackson2JsonView displayImportFailedResult(@RequestParam("importUID") String importUID,Locale locale) {
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		List<ImportEntitiesFailedRow> importFailedEntites;
		ImportEntitiesFailedRow importFailed = new ImportEntitiesFailedRow();
 		try {
 			 importFailedEntites = importManager.fetchImportFailed(importUID);
			 importFailed.setImportFailedEntites(importFailedEntites);
			 
		} catch (DataException e) {
			logger.error("displayImportFailedResult:Unable to fectch Import failed Results :"+e.getMessage());
		}catch (Exception e) {
			logger.error("displayImportFailedResult:Error while fetching Import failed Results:"+e.getMessage());
		}
		
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		view.getAttributesMap().put("importedUid", importUID);
		view.getAttributesMap().put("importFailedEntites", importFailed);
		return new MappingJackson2JsonView();
	}
}
