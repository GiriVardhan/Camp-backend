package com.jbent.peoplecentral.postgres;

import java.util.List;

import org.postgresql.util.PGobject;

import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.model.pojo.AttributeChoice;
import com.jbent.peoplecentral.util.sql.PGHelper;

public class PGAttributeChoice extends PGobject {

	private static final long serialVersionUID = -5459744779924954588L;

	private AttributeChoice choice;
	public PGAttributeChoice() {
		
	}

	public PGAttributeChoice(AttributeChoice choice){
		this();
		this.choice = choice;
		setType("choice_attribute_detail_" + ClientManageUtil.loadClientSchema());
	}
	
	@Override
	public String getValue() {
		return "(" + PGHelper.escapeObjectForPostgres(choice.getChoiceAttributeId()) + "," + PGHelper.escapeObjectForPostgres(choice.getId()) + "," + 
		PGHelper.escapeObjectForPostgres(choice.getDisplayType()) + "," + PGHelper.escapeObjectForPostgres(choice.getChoiceEntityTypeAttributeId())+ "," + PGHelper.escapeObjectForPostgres(choice.getMod_user()) + ")";
	}
	
	public static PGArray getPGEntityTypeArray(List<AttributeChoice> choices){
		if(choices == null || choices.size() < 1){
			return null;
		}
		PGAttributeChoice[] pgats = null;
		if(choices != null && choices.size() > 0){
			pgats = new PGAttributeChoice[choices.size()];
			int counter = 0;
			for (AttributeChoice at : choices) {
				pgats[counter] = new PGAttributeChoice(at);
				counter++;
			}
		}
		//this.email = email;
		return new PGArray(pgats);
	}
}
