/**
 * 
 */
package com.jbent.peoplecentral.model.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jbent.peoplecentral.model.pojo.EntityType;
import com.jbent.peoplecentral.permission.Permissionable;

/**
 * @author PrasadBHVN
 *
 */
public class EntityTypeTableMapper implements Permissionable, RowMapper<EntityType> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2415289097960421961L;

	@Override
	public EntityType mapRow(ResultSet rs, int rownum) throws SQLException {
		EntityType entityType = new EntityType();
		if(rs.getLong("entity_type_id") > 0){
			entityType.setId(rs.getLong("entity_type_id"));
			entityType.setName(rs.getString("entity_type_name"));
			entityType.setCount(rs.getLong(3));
		}
		return entityType;
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
