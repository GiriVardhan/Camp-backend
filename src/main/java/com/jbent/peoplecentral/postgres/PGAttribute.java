package com.jbent.peoplecentral.postgres;

import java.util.List;

import org.postgresql.util.PGobject;

import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.model.pojo.Attribute;
import com.jbent.peoplecentral.util.sql.PGHelper;

public class PGAttribute extends PGobject{

	private static final long serialVersionUID = -5459744779924954588L;

	private Attribute attribute;
	public PGAttribute() {
		
	}

	public PGAttribute(Attribute attribute){
		this();
		this.attribute = attribute;
		setType("attribute_" + ClientManageUtil.loadClientSchema());
	}
	
	@Override
	public String getValue() {
		return "(" + PGHelper.escapeObjectForPostgres(attribute.getId()) + "," + PGHelper.escapeObjectForPostgres(attribute.getOrder()) + "," +
		PGHelper.escapeObjectForPostgres(attribute.getEntityTypeId()) + "," + PGHelper.escapeObjectForPostgres(attribute.getName()) + "," + PGHelper.escapeObjectForPostgres(attribute.getDataTypeId()) + "," + PGHelper.escapeObjectForPostgres(attribute.isIndexed())+","  
		+ PGHelper.escapeObjectForPostgres(attribute.getFieldTypeId()) + "," + PGHelper.escapeObjectForPostgres(attribute.isRequired()) + "," + PGHelper.escapeObjectForPostgres(attribute.getMod_user()) + ","
		+ PGHelper.escapeObjectForPostgres(attribute.getRegex().getRegexId() ==0 ? "":attribute.getRegex().getRegexId()) +","+ PGHelper.escapeObjectForPostgres(attribute.isDisplay())+ ","+ PGHelper.escapeObjectForPostgres(attribute.isADeletable())+ ")";
	}
	
	public static PGArray getPGEntityTypeArray(List<Attribute> attributes){
		if(attributes == null || attributes.size() < 1){
			return null;
		}
		PGAttribute[] pgats = null;
		if(attributes != null && attributes.size() > 0){
			pgats = new PGAttribute[attributes.size()];
			int counter = 0;
			for (Attribute at : attributes) {
				pgats[counter] = new PGAttribute(at);
				counter++;
			}
		}
		//this.email = email;
		return new PGArray(pgats);
	}
}
