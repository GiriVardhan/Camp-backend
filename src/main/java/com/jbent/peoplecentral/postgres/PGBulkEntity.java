package com.jbent.peoplecentral.postgres;


import java.util.List;

import org.postgresql.util.PGobject;
import com.jbent.peoplecentral.postgres.PGArray;
import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.model.pojo.BulkEntity;
import com.jbent.peoplecentral.util.sql.PGHelper;

public class PGBulkEntity extends PGobject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8489778358873572963L;

	private BulkEntity bulkentity;
	//private PGArray values;
	private PGBulkEntity() {}
	
	public PGBulkEntity(BulkEntity bulkentity) {
		this();
		this.bulkentity = bulkentity;
		setType("bulk_entity_" + ClientManageUtil.loadClientSchema());
		//values = PGAttributeValueStorage.getPGAttributeValueArray(bulkentity.getAttributeValueStorage());
	}
	
	
	@Override
	public String getValue() {
		return "(" + PGHelper.escapeObjectForPostgres(bulkentity.getEntityId()) + "," 
		+ PGHelper.escapeObjectForPostgres(bulkentity.getEntityTypeId())+ "," 
		+ PGHelper.escapeObjectForPostgres(bulkentity.getMod_user())+ "," 
		+ PGHelper.escapeObjectForPostgres(bulkentity.getSearchIndex()) + "," 
		+ PGHelper.escapeObjectForPostgres(bulkentity.isEntityValid()) + ","
		+ PGHelper.escapeObjectForPostgres(bulkentity.getAttributeValueId()) + ","
		+ PGHelper.escapeObjectForPostgres(bulkentity.getAttribute().getId()) + ","
		+ PGHelper.escapeObjectForPostgres(bulkentity.getValueVarchar()) + ","
		+ PGHelper.escapeObjectForPostgres(bulkentity.getValueLong())+","
		+ PGHelper.escapeObjectForPostgres(bulkentity.getValueDate()) + ","
		+ PGHelper.escapeObjectForPostgres(bulkentity.getValue_Time()) + ","
		+ PGHelper.escapeObjectForPostgres(bulkentity.getValueText()) + ","
		+ PGHelper.escapeObjectForPostgres(bulkentity.getAttributeValueFileId()) + ","
		+ PGHelper.escapeObjectForPostgres(bulkentity.getCounter()) + ")";
		
	}
	
	/*@Override
	public String getArraySafeValue() {
		StringBuffer buffy = new StringBuffer();
		AbstractJdbc2Array.escapeArrayElement(buffy,"(" + PGHelper.escapeObjectForPostgres(bulkentity.getEntityId()) + "," 
				+ PGHelper.escapeObjectForPostgres(bulkentity.getEntityTypeId())+ "," 
				+ PGHelper.escapeObjectForPostgres(bulkentity.getMod_user())+ "," 
				+ PGHelper.escapeObjectForPostgres(bulkentity.getSearchIndex()) + ",");
		
		//buffy.append(values.toString());
		AbstractJdbc2Array.escapeArrayElement(buffy,")");
		return buffy.toString();
	}
*/	
	public static PGArray getPGBulkEntityArray(List<BulkEntity> bulkEntries){
		if(bulkEntries == null || bulkEntries.size() < 1){
			return null;
		}
		PGBulkEntity[] pgats = null;
		if(bulkEntries != null && bulkEntries.size() > 0){
			pgats = new PGBulkEntity[bulkEntries.size()];
			int counter = 0;
			for (BulkEntity atv : bulkEntries) {
				pgats[counter] = new PGBulkEntity(atv);
				counter++;
			}
		}
		//this.email = email;
		return new PGArray(pgats);
	}
	
}
