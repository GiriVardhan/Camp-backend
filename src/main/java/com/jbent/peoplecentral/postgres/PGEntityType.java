package com.jbent.peoplecentral.postgres;


import java.util.List;

import org.postgresql.util.PGobject;
import org.springframework.stereotype.Component;

import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.model.pojo.EntityType;
import com.jbent.peoplecentral.util.sql.PGHelper;

public class PGEntityType extends PGobject {

	private static final long serialVersionUID = -63449337024748903L;
	
	private EntityType entityType;
	private PGEntityType() {
		
	}
		
	public PGEntityType(EntityType entityType){
		this();
		this.entityType = entityType;
		setType("entity_type_" + ClientManageUtil.loadClientSchema());
	}
	
	@Override
	public String getValue() {
		return "(" + PGHelper.escapeObjectForPostgres(entityType.getId()) + "," + PGHelper.escapeObjectForPostgres(entityType.getName())+ "," +
		PGHelper.escapeObjectForPostgres(entityType.getMod_user()) + ","+PGHelper.escapeObjectForPostgres(entityType.getTemplate()) +"," + 
		PGHelper.escapeObjectForPostgres(entityType.getEditTemplate()) + ","+PGHelper.escapeObjectForPostgres(entityType.isLock())+ ","+ 
		PGHelper.escapeObjectForPostgres(entityType.isEtDeletable()) + "," + PGHelper.escapeObjectForPostgres(entityType.isTreeable()) + ")";
	}
	
	public static PGArray getPGEntityTypeArray(List<EntityType> entityTypes){
		if(entityTypes == null || entityTypes.size() < 1){
			return null;
		}
		PGEntityType[] pgets = null;
		if(entityTypes != null && entityTypes.size() > 0){
			pgets = new PGEntityType[entityTypes.size()];
			int counter = 0;
			for (EntityType et : entityTypes) {
				pgets[counter] = new PGEntityType(et);
				counter++;
			}
		}
		//this.email = email;
		return new PGArray(pgets);
	}

}
