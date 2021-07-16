package com.jbent.peoplecentral.postgres;



import org.postgresql.util.PGobject;

import com.jbent.peoplecentral.model.pojo.Authorities;
import com.jbent.peoplecentral.util.sql.PGHelper;

public class PGAuthorities extends PGobject implements  Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2347713583412585369L;
	private Authorities authorities;
	public PGAuthorities() {
		
	}
	
	public PGAuthorities(Authorities authorities){
		this();
		this.authorities = authorities;
		setType("authorities");
	}
	
	@Override
	public String getValue() {
		return  "(" + PGHelper.escapeObjectForPostgres(authorities.getUserName()) + "," + PGHelper.escapeObjectForPostgres(authorities.getAuthority())+")";
	}
	
}
