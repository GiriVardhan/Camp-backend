/**
 * 
 */
package com.jbent.peoplecentral.model.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jbent.peoplecentral.model.pojo.AttributeFieldType;
import com.jbent.peoplecentral.permission.Permissionable;

/**
 * @author PrasadBHVN
 *
 */
public class AttributeFieldTypeMapper implements Permissionable, RowMapper<AttributeFieldType> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2415289097960421961L;

	@Override
	public AttributeFieldType mapRow(ResultSet rs, int rownum) throws SQLException {
		AttributeFieldType fieldType = new AttributeFieldType();
		if(rs.getLong("fieldtype_id") > 0){
			fieldType.setFieldTypeId(rs.getLong("fieldtype_id"));
			fieldType.setName(rs.getString("fieldtype_name"));
			fieldType.setDisplayOrder(rs.getInt("display_order"));
			fieldType.setDisplayLabel(rs.getString("display_label"));			
		}
		return fieldType;
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
