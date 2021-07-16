package com.jbent.peoplecentral.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

/**
 * This helper is needed because with Tika we are going parse the uploaded docuements 
 * like pdf, ms-office and other docs.
 *
 */
public class TikaHelperUtil {
	
	static final Logger logger = Logger.getLogger(TikaHelperUtil.class);
	
		public String getMetadataAndFileContent(MultipartFile mFile) {
			
			String metaStr = "";
			String fileContentStr="";
			try {
				File file = convertFile(mFile);
				Metadata metadata = new Metadata();
				Tika t = new Tika();				
                t.setMaxStringLength(-1);                
                String contentType = mFile.getContentType();
                
                metaStr = file.getName();
                metaStr = metaStr+ " " + FilenameUtils.getExtension(file.getName());
                
                //if file is image then get metadata
                if(contentType.startsWith("image/")){
                	t.parse(new FileInputStream(file),metadata);
                	metaStr = metaStr +" "+getMetadata(metadata,"image");
                }else{
                	// if file then parse file to get file content
                	fileContentStr= t.parseToString(new FileInputStream(file),metadata);
    				if(fileContentStr !=null){
    					fileContentStr.trim();
    				}
    				metaStr = metaStr +" "+getMetadata(metadata,"file");
                }  
                
			    if(metaStr !=null)
			    	fileContentStr = metaStr+" "+fileContentStr;
			      
			} catch (FileNotFoundException e) {
				logger.error("getMetadata:"+e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("getMetadata:"+e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				logger.error("getMetadata:"+e.getMessage());
				e.printStackTrace();
			} 
			
			return fileContentStr;			
		}
		
		
		private String getMetadata(Metadata metadata, String type){
				
			StringBuffer metaString = new StringBuffer();
			String metaDataStr = "";
			try {
				//getting the list of all meta data elements 
			    String[] metadataNames = metadata.names();
			    if(type != "image"){
			    	for(String name : metadataNames) {		        
			    		metaString.append(metadata.get(name)+"  ");
			    	}			    	
			    }else{
			    	metaString.append(metadata.get("File Modified Date")+"  ");
			    }
			    
			    if(metaString != null && metaString.toString() != null){
			    	metaDataStr = metaString.toString();
			    }
			} catch (Exception e) {
				logger.error("getMetadata:"+e.getMessage());
				e.printStackTrace();
			} 
			
			return metaDataStr;
		}
		
		
		private File convertFile(MultipartFile file)
		{    
		    File convFile = new File(file.getOriginalFilename());
		    try {
				convFile.createNewFile();
				FileOutputStream fos = new FileOutputStream(convFile); 
			    fos.write(file.getBytes());
			    fos.close(); 
			} catch (IOException e) {
				logger.error("TikaHeperUtil:convertFile:"+e.getMessage());
				
			} 
		    
		    return convFile;
		}
	
	}
