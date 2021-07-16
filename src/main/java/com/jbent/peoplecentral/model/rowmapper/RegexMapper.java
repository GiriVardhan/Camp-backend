/**
 * 
 */
package com.jbent.peoplecentral.model.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jbent.peoplecentral.model.pojo.Regex;
import com.jbent.peoplecentral.permission.Permissionable;

/**
 * @author RaviT
 *
 */
public class RegexMapper implements Permissionable, RowMapper<Regex> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2415289097960421961L;

	@Override
	public Regex mapRow(ResultSet rs, int rownum) throws SQLException {
		Regex reg = new Regex();
		if(rs.getLong("regex_id") > 0){
			reg.setRegexId(rs.getLong("regex_id"));
			reg.setPattern(rs.getString("pattern"));
			reg.setDisplayName(rs.getString("display_name"));
			reg.setCustom(rs.getBoolean("custom"));
		}
		return reg;
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
