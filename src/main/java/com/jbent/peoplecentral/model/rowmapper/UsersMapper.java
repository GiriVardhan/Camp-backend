/**
 * 
 */
package com.jbent.peoplecentral.model.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.jbent.peoplecentral.model.pojo.Users;
import com.jbent.peoplecentral.permission.Permissionable;

/**
 * @author PrasadBHVN
 *
 */
public class UsersMapper implements Permissionable, RowMapper<UsersMapper> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2415289097960421961L;
	private List<Users> users = new ArrayList<Users>();
	@Override
	public UsersMapper mapRow(ResultSet rs, int rownum) throws SQLException {
		
		Users user = new Users();
		if((rs.getString("value_varchar")!= null) || (rs.getString("value_varchar")!= "" )){
			user.setUserName(rs.getString("value_varchar"));
		}
		if(user.getUserName()!= null)
			users.add(user);
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
	
	public void setUsers(List<Users> users) {
		this.users = users;
	}

	/**
	 * @return the entities
	 */
	public List<Users> getUsers() {
		return users;
	}

}
