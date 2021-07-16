/**
 * 
 */
package com.jbent.peoplecentral.model.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.jdbc.core.RowMapper;

import com.jbent.peoplecentral.model.pojo.Box;
import com.jbent.peoplecentral.permission.Permissionable;

/**
 * @author RaviT
 *
 */
public class BoxMapper extends ApplicationObjectSupport implements Permissionable, RowMapper<Box> {
	
	private static final long serialVersionUID = 2415289097960421961L;
	@Override
	public Box mapRow(ResultSet rs, int rownum) throws SQLException {
		
		Box box = new Box();
		if(rs.getLong("box_id") > 0){	
			
			box.setBoxId(rs.getLong("box_id"));
			box.setUserEntityId(rs.getLong("user_entity_id"));			
			
		}
		return box;
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
