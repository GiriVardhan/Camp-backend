package com.jbent.peoplecentral.postgres;


import java.util.List;

import org.postgresql.util.PGobject;

import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.model.pojo.FieldTypeOption;
import com.jbent.peoplecentral.util.sql.PGHelper;

public class PGAttributeFieldtypeOptionValue extends PGobject {

	private static final long serialVersionUID = -5459744779924954588L;

	private FieldTypeOption option;
	public PGAttributeFieldtypeOptionValue() {
		
	}

	public PGAttributeFieldtypeOptionValue(FieldTypeOption option){
		this();
		this.option = option;
		setType("attribute_fieldtype_option_value_" + ClientManageUtil.loadClientSchema());
	}
	
	@Override
	public String getValue() {
		return "(" + PGHelper.escapeObjectForPostgres(option.getOptionValueId()) + "," + PGHelper.escapeObjectForPostgres(option.getAttributeId()) + "," + 
		PGHelper.escapeObjectForPostgres(option.getFieldtypeOptionId()) + "," + PGHelper.escapeObjectForPostgres(option.getOptionValue())+  ")";
	}
	
	public static PGArray getPGEntityTypeArray(List<FieldTypeOption> option){
		if(option == null || option.size() < 1){
			return null;
		}
		PGAttributeFieldtypeOptionValue[] pgats = null;
		if(option != null && option.size() > 0){
			pgats = new PGAttributeFieldtypeOptionValue[option.size()];
			int counter = 0;
			for (FieldTypeOption at : option) {
				pgats[counter] = new PGAttributeFieldtypeOptionValue(at);
				counter++;
			}
		}
		//this.email = email;
		return new PGArray(pgats);
	}
}
