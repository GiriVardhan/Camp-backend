package com.jbent.peoplecentral.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.manager.LoginUserManager;
import com.jbent.peoplecentral.model.pojo.AttributeFileStorage;
import com.jbent.peoplecentral.web.SessionContext;



public class FileUploadHelpImpl implements FileUploadHelperUtil{

	@Autowired
	private LoginUserManager loginUserManager;
	
	private String path;
	private String filePath;
	/**
	 * @return the Tomcat Installation Path
	 */
	public String getServerPath(){
		return  System.getProperty("catalina.base");
	}
	/**
	 * @return the filePath
	 */
	public String getDefaultFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Autowired
	@Override
	public void setTemporyPath(String tempPath) {
		if(!tempPath.endsWith(File.separator)){
			path += File.separator;
		}
		this.path = tempPath;
		new File(path).mkdirs();
	}

	@Override
	public String storeTempFile(MultipartFile fileToStore) throws IOException {
		String uuid = UUID.randomUUID().toString();
		File f = new File(path + File.separator + uuid);
		f.mkdir();
		FileCopyUtils.copy(fileToStore.getInputStream(), new FileOutputStream(new File(f + File.separator + fileToStore.getOriginalFilename())));
		return uuid;
	}

	@Override
	public File retrieveTempFile(String tempFileID) {
		File d = new File(path + File.separator + tempFileID);
		try{
			File f = d.listFiles()[0];
			return f;
		}catch (Exception e) {
			return null;
		}
	}

	public void deleteTempFile(String tempDirId){
		File d = new File(path + File.separator + tempDirId);
		for (File f : d.listFiles()) {
			f.delete();
		}
		new File(path + File.separator + tempDirId).delete();
	}
	
	public boolean  storeUploadFileImage(MultipartFile fileToStore,AttributeFileStorage afs) throws IOException {
		//Long attId = attributeId;
		//String att = attId.toString();
		//SessionContext.getRequest().getSession().getServletContext().getRealPath("/");

		String serverPath =  System.getProperty("catalina.base");
		
		File f = new File(serverPath+File.separator+afs.getImageLocation());
		f.mkdirs();
		FileCopyUtils.copy(fileToStore.getInputStream(), new FileOutputStream(new File(f + File.separator + afs.getFileName())));
		
		//For profile Picture
		if(afs.getId() == 126){		
			try {
				loginUserManager.setProfileImagePath();
			} catch (DataException e) {
				e.printStackTrace();
			}
		}
		
		//Check file is saved in the FileSystem.

		File fileRetrived = retrieveUploadedFile(afs.getFileName(),afs.getImageLocation());
		if(fileRetrived.isFile())
			return true;
		
		return false;
	}
	
	public File retrieveUploadedFile(String fileName,String ImageSavedPath) throws IOException {
		//Long attId = attributeId;
		//String att = attId.toString();
		File savedFile = null;
		//String serverPath = SessionContext.getRequest().getSession().getServletContext().getRealPath("/");
		String serverPath =  System.getProperty("catalina.base");
		File f = new File(serverPath+File.separator+ImageSavedPath);

		try{
			File[] savedFiles = f.listFiles();
			for(File sFile:savedFiles){
				if(sFile.getName().equalsIgnoreCase(fileName))
					return sFile;
			}
		}catch (Exception e) {
			return null;
		}
		
		return savedFile;
	}
	

	public String getImagePath(String Imagepath){
        return getDefaultFilePath()+File.separator+Imagepath;
	}
	public String getPath( ){
        return this.path;
	}

	/**
	 * @return the Tomcat Webapp Path
	 */
	public String getWebappPath(){
		return SessionContext.getRequest().getSession().getServletContext().getRealPath("/");
	}

	
}
