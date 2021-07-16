/**
 * 
 */
package com.jbent.peoplecentral.model.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jbent.peoplecentral.model.pojo.EntityStatus;
import com.jbent.peoplecentral.permission.Permissionable;

/**
 * @author PrasadBHVN
 *
 */
public class EntityStatusMapper implements Permissionable, RowMapper<EntityStatus> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2415289097960421961L;
	
	@Override
	public EntityStatus mapRow(ResultSet rs, int rownum) throws SQLException {
		
		EntityStatus entStatus = new EntityStatus();
		if(rs.getLong("id") > 0){
			entStatus.setEntityId(rs.getLong("id"));
			entStatus.setEntityValid(rs.getBoolean("entity_valid"));
			
		}
		return entStatus;
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
