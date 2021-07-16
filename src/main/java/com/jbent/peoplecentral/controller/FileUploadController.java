/**
 * 
 */
package com.jbent.peoplecentral.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.manager.EntityManager;
import com.jbent.peoplecentral.model.manager.EntityTypeManager;
import com.jbent.peoplecentral.model.manager.FileManager;
import com.jbent.peoplecentral.model.pojo.AttributeFileStorage;
import com.jbent.peoplecentral.model.pojo.CompletePermissions;
import com.jbent.peoplecentral.model.pojo.Entity;
import com.jbent.peoplecentral.util.FileUploadHelperUtil;
import com.jbent.peoplecentral.util.MimeTypeConstants;
import com.jbent.peoplecentral.web.SessionContext;

/**
 * @author jasontesser
 *
 */
@Controller
public class FileUploadController extends WebApplicationObjectSupport {

	private FileUploadHelperUtil fileUploadHelpUtil;
	@Autowired
	private FileManager fileManger;	
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private EntityTypeManager entityTypeManager;
	@RequestMapping(value = {"/file/submit"}, method = RequestMethod.POST)
	public MappingJackson2JsonView uploadFile(HttpServletRequest request) throws DataException{
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		Iterator<String> fileNames = ((DefaultMultipartHttpServletRequest)request).getFileNames();
		boolean first = true;
		boolean HTML = false;
		String fret = null;
		while(fileNames.hasNext()){
			try {
				String fn = fileNames.next();
				MultipartFile mpf = ((DefaultMultipartHttpServletRequest)request).getFile(fn);
				if(first == true && fn.startsWith("HTML")){
					HTML = true;
					Map <String, Object> m = new HashMap<String, Object>();
					m.put("name", mpf.getOriginalFilename());
					m.put("file", fileUploadHelpUtil.storeTempFile(mpf));
					m.put("id", mpf.getOriginalFilename());
					ret.add(m);
				}else{
					fret = "file="+fileUploadHelpUtil.storeTempFile(mpf)+",name="+mpf.getOriginalFilename();
				}
			} catch (IOException e) {
				logger.error(FileUploadController.class,e);
				throw new RuntimeException("Unable to upload temp file");
			}catch (Exception e) {
				logger.error(FileUploadController.class,e);
				throw new RuntimeException("Unable to upload temp file");
			}
		}
		if(HTML){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("identifier", "id");
			map.put("label", "file");
			map.put("items",ret);
			//model.addAttribute("output", "<textarea>"+new JSONObject(map).toString()+"</textarea>");
		}else{
			view.getAttributesMap().put("output", fret);
		}
		
		return view;
	}

	/**
	 * @param fileHelper the fileHelper to set
	 */
	@Autowired
	public void setFileUploadHelpUtil(FileUploadHelperUtil fileUploadHelpUtil) {
		this.fileUploadHelpUtil = fileUploadHelpUtil;
	}
	
	@RequestMapping(value = {"/files/images/upload/{attributeId}/{entityID}","/*/files/images/upload/{attributeId}/{entityID}"}, method = RequestMethod.GET)
	public MappingJackson2JsonView fileImagesUpload(@PathVariable("attributeId") long attributeId,@PathVariable("entityID") long entityId){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.getAttributesMap().put("attributeId",attributeId);
		view.getAttributesMap().put("eId",entityId);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());	
		view.getAttributesMap().put("fileAttributeJSP","YES");
		return view;
	}

	@RequestMapping(value = {"/files/images/upload","/*/files/images/upload"}, method = RequestMethod.POST)
	public View fileUpload( HttpServletRequest request, HttpServletResponse  response) {
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		long entId = 0;
		long entityId = Long.parseLong(request.getParameter("entityId"));
		long attributeId = Long.parseLong(request.getParameter("attributeId"));
		int filesCount = Integer.parseInt(request.getParameter("fileCount"));
		List<MultipartFile> files = new ArrayList<MultipartFile>();
		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
		}
		files = ((DefaultMultipartHttpServletRequest)request).getFiles("file");

		try {
			entityTypeManager.loadAttribute(attributeId);
			entId = fileManger.Save(files,entityId,attributeId,filesCount,view);
			//model.addAttribute("redirect", "/app/" + ClientManageUtil.loadClientSchema() + "/entity/add/" + att.getEntityTypeId());
			
		} catch (DataException e) {
			view.getAttributesMap().put("isError", "true");	
			view.getAttributesMap().put("attributeId", attributeId);	
			view.getAttributesMap().put("errorMsg", messageSource.getMessage("error.attribute.file.upload.failed", null, request.getLocale()));	
			logger.error("Unable to save file:"+e.getMessage());
		}catch (Exception e) {
			view.getAttributesMap().put("isError", "true");	
			view.getAttributesMap().put("attributeId", attributeId);	
			view.getAttributesMap().put("errorMsg", messageSource.getMessage("error.attribute.file.upload.failed", null, request.getLocale()));	
			logger.error("Unable to save file:"+e.getMessage());
		}

		view.getAttributesMap().put("entId", entId);				
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());		
		
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = {"/files/images/edit/{entityID}","/*/files/images/edit/{entityID}"}, method = RequestMethod.GET)
	public MappingJackson2JsonView loadImage(@PathVariable("entityID") long entityId) throws DataException{
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		List<AttributeFileStorage> afsList = null;
		try {
			afsList = fileManger.loadImages(entityId);
		} catch (DataException e) {			
			logger.error("Unable to upload temp file",e);
		}catch (Exception e) {			
			logger.error("Unable to upload temp file",e);
		}
		view.getAttributesMap().put("afsList",afsList);		
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return view;
	}
	
	

	@RequestMapping(value = {"/files/images/save","/*/files/images/save"}, method = RequestMethod.POST)
	public View saveImage(HttpServletRequest request,@RequestParam("imageHight") Integer imageHight,@RequestParam("imageWidth") Integer imageWidth,@RequestParam("canvasImagePath") String fileName,@RequestParam("entityId") long entityId,@RequestParam("attributeId") long attributeId,Locale locale) throws DataException, IOException{
		//AttributeFileStorage afs = new AttributeFileStorage();
		//int filesCount = 1;
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		 AttributeFileStorage afs = new AttributeFileStorage();
		 afs.setFileName(fileName);
		 afs.setId(attributeId);
		 afs.setEntityId(entityId);
		 String serverPath = fileUploadHelpUtil.getServerPath();
		 File file = new File(serverPath+File.separator+ fileManger.tempImageLocationToSave(afs)+File.separator+fileName);
		try{
			afs = fileManger.imageSave(file, entityId, attributeId, view);
		}catch(Exception e){
			logger.error("Error while performing IM Filter Opertations file... "+e.getStackTrace());
		}
		view.getAttributesMap().put("afs",afs);
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = {"/file/image/delete","/*/file/image/delete/{attributeFileId}"}, method = RequestMethod.GET)
	public MappingJackson2JsonView deleteImage(@PathVariable("attributeFileId") long attributeFileId,@RequestParam("entityid")long entityId){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		Entity entity = new Entity();		
		try {
			entity = entityManager.loadEntity(entityId);
			for(AttributeFileStorage afs : entity.getAttributeFileStorage()){
				if(afs.getAttributeFileId()== attributeFileId){
					fileManger.imageDelete(afs);
					break;
				}
			}
			view.getAttributesMap().put("redirect", "/app/" + ClientManageUtil.loadClientSchema() + "/entity/edit/"+entityId);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			view.getAttributesMap().put("error", "Unable to delete File:"+attributeFileId);
		}

		return view;
	}
	
	@RequestMapping(value = {"/files/images/filter/add","/*/files/images/filter/add"}, method = RequestMethod.POST)
	public View filterApply(HttpServletRequest request,@RequestParam("filterType") String filterType,@RequestParam("imageHight") String imageHight,@RequestParam("imageWidth") String imageWidth,@RequestParam("rotate") String rotate,@RequestParam("compress") String compress,@RequestParam("cropWidth") String cropWidth,@RequestParam("cropHieght") String cropHieght,@RequestParam("offsetx") String offsetx,@RequestParam("offsety") String offsety,@RequestParam("iName") String iPath,@RequestParam("entityId") long entityId,@RequestParam("attributeId") long attributeId,Locale locale) throws DataException, IOException{
		//AttributeFileStorage afs = new AttributeFileStorage();
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		int filesCount = 1;		
		List<String> arguments = new ArrayList<String>();
		AttributeFileStorage afs = null;
		String serverPath = fileUploadHelpUtil.getServerPath();
		File file = new File(serverPath+File.separator+ iPath);
		if(filterType.equalsIgnoreCase("rotate")){
				arguments.add(rotate);
		}else if(filterType.equalsIgnoreCase("compress")){
			arguments.add(compress);
		}else if(filterType.equalsIgnoreCase("resize")){
			arguments.add(imageHight);
			arguments.add(imageWidth);
		}else if(filterType.equalsIgnoreCase("crop")){
			arguments.add(cropWidth);
			arguments.add(cropHieght);
			arguments.add(offsetx);
			arguments.add(offsety);
		}else if(filterType.equalsIgnoreCase("hsb")){}
		else if(filterType.equalsIgnoreCase("grayscale")){}
		try{
			afs = fileManger.IMFilterOperations(file,arguments,filterType,entityId, attributeId, filesCount, view);
		}catch(Exception e){
			logger.error("Error while performing IM Filter Opertations file... "+e.getStackTrace());
		}
		view.getAttributesMap().put("afs",afs);
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = {"/entity/file/view/uploaded","/*/entity/file/view/uploaded"}, method = RequestMethod.GET)
	public String viewFile(Model model){		
		return "viewfile";
	}
	
	@RequestMapping(value = {"/files/images/manipulation/{attributeId}/{entityID}/{fileId}","/*/files/images/manipulation/{attributeId}/{entityID}/{fileId}"}, method = RequestMethod.GET)
	public MappingJackson2JsonView fileImagesManipulation (@PathVariable("attributeId") long attributeId,@PathVariable("entityID") long entityId,@PathVariable("fileId") long fileId){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.getAttributesMap().put("attributeId",attributeId);
		view.getAttributesMap().put("eId",entityId);
		view.getAttributesMap().put("fileId",fileId);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());	
		view.getAttributesMap().put("fileAttributeJSP","YES");
		return view;
	}
	
	@RequestMapping(value = {"/files/images/loadUserFile","/*/files/images/loadUserFile"}, method = RequestMethod.GET)
	public void loadUserFile(
			@RequestParam("usersFilePath") String usersFilePath,
			HttpServletRequest request,
			HttpServletResponse response,HttpSession session){
		try{
			String serverPath = "";
			if(usersFilePath.toString().length() > 0){
				serverPath = fileUploadHelpUtil.getServerPath();	
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
								
								if(request.getParameter("force-download") != null){
									response.setContentType("application/force-download");
									response.setHeader("Content-disposition", "attachment; filename="+ FilenameUtils.getName(usersFilePath));
								}else
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
			}
		}catch(Exception e){
			logger.error("Error in loadUserFile() of FileUploadController "+ e);
		}
	}
}
