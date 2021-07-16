package com.jbent.peoplecentral.postgres;


import java.util.List;

import org.postgresql.util.PGobject;
import org.springframework.stereotype.Component;

import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.model.pojo.RelatedEntityType;
import com.jbent.peoplecentral.util.sql.PGHelper;

public class PGRelatedEntityType extends PGobject {

	private static final long serialVersionUID = -63449337024748903L;
	
	private RelatedEntityType relatedEntityType;
	private PGRelatedEntityType() {
		
	}
		
	public PGRelatedEntityType(RelatedEntityType relatedEntityType){
		this();
		this.relatedEntityType = relatedEntityType;
		setType("related_entity_type_" + ClientManageUtil.loadClientSchema());
	}
	
	@Override
	public String getValue() {
		return "(" + PGHelper.escapeObjectForPostgres(relatedEntityType.getId()) + "," + PGHelper.escapeObjectForPostgres(relatedEntityType.getAttributeId())+ "," +
		PGHelper.escapeObjectForPostgres(relatedEntityType.getChildEntityTypeId()) + ","+ PGHelper.escapeObjectForPostgres(relatedEntityType.isCollapse()) + ","+
		PGHelper.escapeObjectForPostgres(relatedEntityType.getMod_user()) +")";

	}
	
	public static PGArray getPGRealtedEntityTypeArray(List<RelatedEntityType> relatedEntityTypes){
		if(relatedEntityTypes == null || relatedEntityTypes.size() < 1){
			return null;
		}
		PGRelatedEntityType[] pgets = null;
		if(relatedEntityTypes != null && relatedEntityTypes.size() > 0){
			pgets = new PGRelatedEntityType[relatedEntityTypes.size()];
			int counter = 0;
			for (RelatedEntityType ret : relatedEntityTypes) {
				pgets[counter] = new PGRelatedEntityType(ret);
				counter++;
			}
		}
		//this.email = email;
		return new PGArray(pgets);
	}

}
