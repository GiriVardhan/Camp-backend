/**
 * 
 */
package com.jbent.peoplecentral.model.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;
import com.jbent.peoplecentral.permission.Permissionable;

/**
 * @author RaviT
 *
 */
public class AttributeValueStorageMapper implements Permissionable, RowMapper<AttributeValueStorageMapper> {
	
	private static final long serialVersionUID = 2415289097960421961L;
	private List<AttributeValueStorage> attValues = new ArrayList<AttributeValueStorage>();
	
	public List<AttributeValueStorage> getAttValues() {
		return attValues;
	}

	public void setAttValues(List<AttributeValueStorage> attValues) {
		this.attValues = attValues;
	}

	@Override
	public AttributeValueStorageMapper mapRow(ResultSet rs, int rownum) throws SQLException {
		AttributeValueStorage atv = new AttributeValueStorage();
		if(rs.getLong("attribute_id") > 0){
			atv.setAttributeValueId(rs.getLong("attribute_value_id"));
			atv.setEntityId(rs.getLong("entity_id"));
			atv.setValueVarchar(rs.getString("value_varchar"));			
			atv.setValueLong(rs.getLong("value_long"));
			atv.setValueDate(rs.getDate("value_date"));
			atv.setValueTime(rs.getTime("value_time"));
			atv.setValueText(rs.getString("value_text"));
			atv.setValueText(rs.getString("mod_user"));
			atv.setSearchIndex(rs.getString("search_index"));
			atv.setAttributeValueFileId(rs.getLong("attribute_value_file_storage_id"));
			}
		if(rs.getLong("attribute_id") > 0)
			attValues.add(atv);
		
		return this;
	}

	@Override
	public long getPermissionId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getPermissionType() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
