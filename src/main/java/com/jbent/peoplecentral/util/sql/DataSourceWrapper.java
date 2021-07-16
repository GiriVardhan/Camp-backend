/**
 * 
 */
package com.jbent.peoplecentral.util.sql;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.jdbc.datasource.DelegatingDataSource;

import com.jbent.peoplecentral.client.ClientManageUtil;

/**
 * @author Jason Tesser
 *
 */
public class DataSourceWrapper extends DelegatingDataSource {
		
	@Override
	public Connection getConnection() throws SQLException {
		Connection con = super.getConnection();
		try{
			con.createStatement().execute("SET search_path TO " + ClientManageUtil.loadClientSchema() + ",public");
		}catch (Exception e) {
			con.createStatement().execute("SET search_path TO public");
		}
		return con;
	}
	
	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
		Connection con = super.getConnection(username, password);
		try{
			con.createStatement().execute("SET search_path TO " + ClientManageUtil.loadClientSchema() + ",public");
		}catch (Exception e) {
			con.createStatement().execute("SET search_path TO public");
		}
		return con;
	}
	
}
