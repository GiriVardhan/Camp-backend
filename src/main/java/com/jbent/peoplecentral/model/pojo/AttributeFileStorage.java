package com.jbent.peoplecentral.model.pojo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.jbent.peoplecentral.model.manager.FileManager;
import com.jbent.peoplecentral.util.MimeTypeConstants;

public class AttributeFileStorage extends Attribute{

	/**
	 * 
	 */
	private static final long serialVersionUID = -542019159126400775L;
	private long attributeFileId;	
	private long entityId;
	private String fileName;
	private String description;
	private long sort;
	private String modUser;
	private String imagePath;
	private String imageLocation;
	
	/**
	 * @return the imageLoctation
	 */
	public String getImageLocation() {
		return imageLocation;
	}
	/**
	 * @param imageLoctation the imageLoctation to set
	 */
	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}
	/**
	 * @return the modUser
	 */
	public String getModUser() {
		return modUser;
	}
	/**
	 * @param modUser the modUser to set
	 */
	public void setModUser(String modUser) {
		this.modUser = modUser;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

		
	/**
	 * @return the attributeFileId
	 */
	public long getAttributeFileId() {
		return attributeFileId;
	}
	/**
	 * @param attributeFileId the attributeFileId to set
	 */
	public void setAttributeFileId(long attributeFileId) {
		this.attributeFileId = attributeFileId;
	}
	/**
	 * @return the entityId
	 */
	public long getEntityId() {
		return entityId;
	}
	/**
	 * @param entityId the entityId to set
	 */
	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the sort
	 */
	public long getSort() {
		return sort;
	}
	/**
	 * @param sort the sort to set
	 */
	public void setSort(long sort) {
		this.sort = sort;
	}
	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}
	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public String getMimeType(){
		return MimeTypeConstants.getMimeType(fileName.substring(fileName.lastIndexOf(".")+1));
	}
			
	public boolean hasEditableText(){
		String mimeType = getMimeType();
		if (mimeType.indexOf("officedocument")==-1 
				&& (mimeType.indexOf("text")!=-1 
					|| mimeType.indexOf("javascript")!=-1 
					|| mimeType.indexOf("xml")!=-1 
					|| mimeType.indexOf("php")!=-1)) {
			return true;
		}
		return false;
	}
}

