package com.jbent.peoplecentral.postgres;


import java.util.List;

import org.postgresql.util.PGobject;

import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.model.pojo.Entity;
import com.jbent.peoplecentral.util.sql.PGHelper;

public class PGEntity extends PGobject {

	private static final long serialVersionUID = -63449337024748903L;
		
	private Entity entity;
	private PGEntity() {
		
	}
	
	public PGEntity(Entity entity){
		this();
		this.entity = entity;
		setType("entity_" + ClientManageUtil.loadClientSchema());
	}
	
	@Override
	public String getValue() {
		return "(" + PGHelper.escapeObjectForPostgres(entity.getEntityId()) + "," + PGHelper.escapeObjectForPostgres(entity.getEntityTypeId())+ "," + PGHelper.escapeObjectForPostgres(entity.getMod_user())+ "," + PGHelper.escapeObjectForPostgres(entity.getSearchIndex())+ "," + PGHelper.escapeObjectForPostgres(entity.isEntityValid()) + ")";
	}
	
	public static PGArray getPGEntityArray(List<Entity> entity){
		if(entity == null || entity.size() < 1){
			return null;
		}
		PGEntity[] pgets = null;
		if(entity != null && entity.size() > 0){
			pgets = new PGEntity[entity.size()];
			int counter = 0;
			for (Entity et : entity) {
				pgets[counter] = new PGEntity(et);
				counter++;
			}
		}
		//this.email = email;
		return new PGArray(pgets);
	}

}
