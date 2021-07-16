package com.jbent.peoplecentral.postgres;


import java.util.List;

import org.postgresql.util.PGobject;

import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.model.pojo.AttributeFileStorage;
import com.jbent.peoplecentral.util.sql.PGHelper;

public class PGAttributeFileStorage extends PGobject {
	
	private static final long serialVersionUID = -5459744779924954588L;

	private AttributeFileStorage attributeFileStorage;
	public PGAttributeFileStorage() {
		
	}

	public PGAttributeFileStorage(AttributeFileStorage attributeFileStorage){
		this();
		this.attributeFileStorage = attributeFileStorage;
		setType("attribute_value_file_storage_"+ ClientManageUtil.loadClientSchema());
	}
	
	@Override
	public String getValue() {
		return "(" + PGHelper.escapeObjectForPostgres(attributeFileStorage.getAttributeFileId()) + "," + PGHelper.escapeObjectForPostgres(attributeFileStorage.getId()) + "," +
		PGHelper.escapeObjectForPostgres(attributeFileStorage.getEntityId()) + "," + PGHelper.escapeObjectForPostgres(attributeFileStorage.getFileName()) + ","  + PGHelper.escapeObjectForPostgres(attributeFileStorage.getDescription())+","  
		+ PGHelper.escapeObjectForPostgres(attributeFileStorage.getSort()) + "," + PGHelper.escapeObjectForPostgres(attributeFileStorage.getModUser())  + ")";
	}
	
	public static PGArray getPGAttributeFileArray(List<AttributeFileStorage> attributeFileStorages){
		if(attributeFileStorages == null || attributeFileStorages.size() < 1){
			return null;
		}
		PGAttributeFileStorage[] pgats = null;
		if(attributeFileStorages != null && attributeFileStorages.size() > 0){
			pgats = new PGAttributeFileStorage[attributeFileStorages.size()];
			int counter = 0;
			for (AttributeFileStorage atv : attributeFileStorages) {
				pgats[counter] = new PGAttributeFileStorage(atv);
				counter++;
			}
		}
		//this.email = email;
		return new PGArray(pgats);
	}
}
