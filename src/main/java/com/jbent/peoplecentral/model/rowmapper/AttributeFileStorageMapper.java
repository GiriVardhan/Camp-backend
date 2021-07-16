/**
 * 
 */
package com.jbent.peoplecentral.model.rowmapper;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.model.pojo.AttributeFileStorage;
import com.jbent.peoplecentral.permission.Permissionable;
import com.jbent.peoplecentral.util.FileUploadHelperUtil;

/**
 * @author RaviT
 *
 */
public class AttributeFileStorageMapper implements Permissionable, RowMapper<AttributeFileStorageMapper> {
	
	private static final long serialVersionUID = 2415289097960421961L;
	private List<AttributeFileStorage> fileStorage = new ArrayList<AttributeFileStorage>();
	
	public List<AttributeFileStorage> getAttFiles() {
		return fileStorage;
	}

	public void setAttFiles(List<AttributeFileStorage> fileStorage) {
		this.fileStorage = fileStorage;
	}

	@Override
	public AttributeFileStorageMapper mapRow(ResultSet rs, int rownum) throws SQLException {
		AttributeFileStorage afs = new AttributeFileStorage();
		if(rs.getLong("attribute_id") > 0){
			afs.setAttributeFileId(rs.getLong("attribute_value_file_storage_id"));			
			afs.setEntityId(rs.getLong("entity_id"));
			afs.setId(rs.getLong("attribute_id"));			
			afs.setFileName(rs.getString("name"));
			afs.setDescription(rs.getString("description"));
			afs.setSort(rs.getLong("sort"));
			afs.setModUser(rs.getString("mod_user"));
			String imagePath = getImagePath(afs);
			afs.setImagePath(imagePath);
			}
		if(rs.getLong("attribute_id") > 0)
			fileStorage.add(afs);
		
		return this;
	}

	@Override
	public long getPermissionId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getPermissionType() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public String getImagePath(AttributeFileStorage afs){
		Long attributeID = afs.getId();
		String attId = attributeID.toString();
		String imageSave = ClientManageUtil.loadClientSchema()+File.separator+
		afs.getEntityId()+File.separator+attId.charAt(0)+File.separator+
	 	attId.charAt(1)+File.separator+attId.charAt(2)+File.separator+attId+File.separator+afs.getFileName();
		
	return imageSave;
	}
	
	
}
