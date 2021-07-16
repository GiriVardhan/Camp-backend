package com.jbent.peoplecentral.postgres;


import java.util.List;

import org.postgresql.util.PGobject;
import org.springframework.stereotype.Component;

import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.model.pojo.Regex;
import com.jbent.peoplecentral.util.sql.PGHelper;

public class PGRegex extends PGobject {

	private static final long serialVersionUID = -63449337024748903L;
	
	private Regex regex;
	private PGRegex() {
		
	}
		
	public PGRegex(Regex regex){
		this();
		this.regex = regex;
		setType("regex_" + ClientManageUtil.loadClientSchema());
	}
	
	@Override
	public String getValue() {
		return "(" + PGHelper.escapeObjectForPostgres(regex.getRegexId()) + "," + PGHelper.escapeObjectForPostgres(regex.getPattern())+ "," +
		PGHelper.escapeObjectForPostgres(regex.getDisplayName()) + ","+PGHelper.escapeObjectForPostgres(regex.getCustom()) +")";

	}
	
	public static PGArray getPGRegexArray(List<Regex> regexs){
		if(regexs == null || regexs.size() < 1){
			return null;
		}
		PGRegex[] pgregxes = null;
		if(regexs != null && regexs.size() > 0){
			pgregxes = new PGRegex[regexs.size()];
			int counter = 0;
			for (Regex rgx : regexs) {
				pgregxes[counter] = new PGRegex(rgx);
				counter++;
			}
		}
		//this.email = email;
		return new PGArray(pgregxes);
	}

}
