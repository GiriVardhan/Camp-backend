/**
 * 
 */
package com.jbent.peoplecentral.model.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jbent.peoplecentral.model.pojo.AttributeDataType;
import com.jbent.peoplecentral.permission.Permissionable;

/**
 * @author PrasadBHVN
 *
 */
public class AttributeDataTypeMapper implements Permissionable, RowMapper<AttributeDataType> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2415289097960421961L;

	@Override
	public AttributeDataType mapRow(ResultSet rs, int rownum) throws SQLException {
		AttributeDataType attDataType = new AttributeDataType();
		if(rs.getLong("datatype_id") > 0){
			attDataType.setDatatypeId(rs.getLong("datatype_id"));
			attDataType.setDatatypeName(rs.getString("datatype_name"));
			
		}
		return attDataType;
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
