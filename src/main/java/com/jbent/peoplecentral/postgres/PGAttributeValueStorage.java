package com.jbent.peoplecentral.postgres;


import java.io.Externalizable;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

import org.postgresql.util.PGobject;

import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;
import com.jbent.peoplecentral.util.sql.PGHelper;

public class PGAttributeValueStorage extends PGobject implements Externalizable {
	
	private static final long serialVersionUID = -5459744779924954588L;

	private AttributeValueStorage attributeValueStorage;
	public PGAttributeValueStorage() {
		
	}

	public PGAttributeValueStorage(AttributeValueStorage attributeValueStorage){
		this();
		this.attributeValueStorage = attributeValueStorage;
		setType("attribute_value_storage_"+ ClientManageUtil.loadClientSchema());
	}
	
	@Override
	public String getValue() {
		return "(" + PGHelper.escapeObjectForPostgres(attributeValueStorage.getAttributeValueId()) + "," + PGHelper.escapeObjectForPostgres(attributeValueStorage.getId()) + "," +
		PGHelper.escapeObjectForPostgres(attributeValueStorage.getEntityId()) + "," + PGHelper.escapeObjectForPostgres(attributeValueStorage.getValueVarchar()) + ","  + PGHelper.escapeObjectForPostgres(attributeValueStorage.getValueLong())+","  
		+ PGHelper.escapeObjectForPostgres(attributeValueStorage.getValueDate())+ "," + PGHelper.escapeObjectForPostgres(attributeValueStorage.getValueTime()) + "," + PGHelper.escapeObjectForPostgres(attributeValueStorage.getValueText())+ "," 
		+ PGHelper.escapeObjectForPostgres(attributeValueStorage.getMod_user()) + "," + PGHelper.escapeObjectForPostgres(attributeValueStorage.getSearchIndex())+"," + PGHelper.escapeObjectForPostgres(attributeValueStorage.getAttributeValueFileId()) + ")";
	}
	
	public static PGArray getPGAttributeValueArray(List<AttributeValueStorage> attributeValueStorages){
		if(attributeValueStorages == null || attributeValueStorages.size() < 1){
			return null;
		}
		PGAttributeValueStorage[] pgats = null;
		if(attributeValueStorages != null && attributeValueStorages.size() > 0){
			pgats = new PGAttributeValueStorage[attributeValueStorages.size()];
			int counter = 0;
			for (AttributeValueStorage atv : attributeValueStorages) {
				pgats[counter] = new PGAttributeValueStorage(atv);
				counter++;
			}
		}
		//this.email = email;
		return new PGArray(pgats);
	}

	@Override
	public void readExternal(ObjectInput arg0) throws IOException,
			ClassNotFoundException {
		throw new NotSerializableException("Not today!");
		
	}

	@Override
	public void writeExternal(ObjectOutput arg0) throws IOException {
		throw new NotSerializableException("Not today!");
		
	}
}
