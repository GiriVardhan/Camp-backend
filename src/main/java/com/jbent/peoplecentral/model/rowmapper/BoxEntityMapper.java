/**
 * 
 */
package com.jbent.peoplecentral.model.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.jdbc.core.RowMapper;

import com.jbent.peoplecentral.model.pojo.BoxEntity;
import com.jbent.peoplecentral.permission.Permissionable;

/**
 * @author RaviT
 *
 */
public class BoxEntityMapper extends ApplicationObjectSupport implements Permissionable, RowMapper<BoxEntity> {
	
	private static final long serialVersionUID = 2415289097960421961L;
	@Override
	public BoxEntity mapRow(ResultSet rs, int rownum) throws SQLException {
		
		BoxEntity boxEntity = new BoxEntity();
		if(rs.getLong("box_id") > 0){	
			
			boxEntity.setBoxId(rs.getLong("box_id"));			
			boxEntity.setEntityId(rs.getLong("entity_id"));
			
		}
		return boxEntity;
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
