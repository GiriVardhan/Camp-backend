package com.jbent.peoplecentral.util;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.jbent.peoplecentral.model.pojo.AttributeFileStorage;

/**
 * This helper is needed because with the flash DOJO uploader
 * files get uploaded in separate HTTP sessions. So we allow
 * the files to be uploaded anonymously then retrived via a
 * unique String which the UTIL provides 
 * The files are stored in a temporary directory on the FS
 * @author jasontesser
 *
 */
public interface FileUploadHelperUtil {

	/**
	 * The full path to save the files to 
	 * @param path
	 */
	@Autowired
	public void setTemporyPath(String path);
	
	/**
	 * Returns a unique string 
	 * @param fileToStore
	 * @return
	 * @throws IOException 
	 */
	public String storeTempFile(MultipartFile fileToStore) throws IOException;
	
	/**
	 * Returns a file previously saved from the passed in ID
	 * Once retrieved the file will be deleted. 
	 * @param tempFileID
	 * @return
	 */
	public File retrieveTempFile(String tempFileID);
	
	/**
	 * 
	 * @param tempDirId
	 */
	public void deleteTempFile(String tempDirId);
	
	/**
	 * Returns a unique string 
	 * @param fileToStore
	 * @return
	 * @throws IOException 
	 */
	public boolean storeUploadFileImage(MultipartFile fileToStore,AttributeFileStorage afs ) throws IOException;
	
	/**
	 * Returns a File 
	 * @param String fileName, String ImageSavedPath
	 * @return 
	 * @throws IOException 
	 */
	public File retrieveUploadedFile(String fileName,String ImageSavedPath) throws IOException ;
	/**
	 * Returns a path 
	 * @param path
	 * @return	 
	 */
	public String getImagePath(String path);
	/**
	 * Returns a path 
	 * @param path
	 * @return	 
	 */
	public String getPath();
	
	/**
	 * Returns a Tomcat Installatin path 
	 * @param path
	 * @return	 
	 */
	public String getServerPath();
	
	/**
	 * Returns a Tomcat Webapp path 
	 * @param path
	 * @return	 
	 */
	public String getWebappPath();
}
