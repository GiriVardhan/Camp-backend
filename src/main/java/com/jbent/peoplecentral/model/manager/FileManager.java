/**
 * 
 */
package com.jbent.peoplecentral.model.manager;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.pojo.AttributeFileStorage;



/**
 * @author RaviT
 *
 */
public interface FileManager {
	
	@Transactional(rollbackFor = Exception.class)
	public long Save(List<MultipartFile> files,long entityId,long attributeId,int filesCount,MappingJackson2JsonView view) throws DataException;
	
	@Transactional(rollbackFor = Exception.class)
	public AttributeFileStorage IMFilterOperations(File file,List<String> arguments,String filterType,long entityId,long attributeId,int filesCount,MappingJackson2JsonView view) throws DataException;
	
	@Transactional(rollbackFor = Exception.class)
	public List<AttributeFileStorage> loadImages(long entityId) throws DataException;
	
	@Transactional(rollbackFor = Exception.class)
	public String imageLocationToSave(AttributeFileStorage afs) throws DataException;
	
	@Transactional(rollbackFor = Exception.class)
	public AttributeFileStorage imageSave(File file,long entityId, long attributeId,MappingJackson2JsonView view) throws DataException;
	
	@Transactional(rollbackFor = Exception.class)
	public AttributeFileStorage imageDelete(AttributeFileStorage afs) throws DataException;
	
	@Transactional(rollbackFor = Exception.class)
	public String getSavedImagePath(AttributeFileStorage afs) throws DataException;
	
	@Transactional(rollbackFor = Exception.class)
	public String tempImageLocationToSave(AttributeFileStorage afs) throws DataException;

	public String getFileText(AttributeFileStorage attributeFileStorage) throws IOException;

	void setFileText(AttributeFileStorage attributeFileStorage, String text) throws IOException;
	
	
}
