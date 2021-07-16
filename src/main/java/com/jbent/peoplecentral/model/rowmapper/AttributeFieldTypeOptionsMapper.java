/**
 * 
 */
package com.jbent.peoplecentral.model.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.jbent.peoplecentral.model.pojo.AttributeFieldType;
import com.jbent.peoplecentral.model.pojo.FieldTypeOption;
import com.jbent.peoplecentral.permission.Permissionable;

/**
 * @author PrasadBHVN
 *
 */
public class AttributeFieldTypeOptionsMapper implements Permissionable, RowMapper<AttributeFieldType> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2415289097960421961L;
	private List <FieldTypeOption> options= new ArrayList<FieldTypeOption>();
	AttributeFieldType fieldType = new AttributeFieldType();

	@Override
	public AttributeFieldType mapRow(ResultSet rs, int rownum) throws SQLException {
		FieldTypeOption option = null;
		if(rs.getLong("fieldtype_option_id") > 0){
			option = new FieldTypeOption();
			option.setFieldtypeOptionId(rs.getLong("fieldtype_option_id"));
			option.setFieldTypeId(rs.getLong("attribute_fieldtype_id"));
			option.setOption(rs.getString("option"));
		}
		options.add(option);
		fieldType.setOptions(options);
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
