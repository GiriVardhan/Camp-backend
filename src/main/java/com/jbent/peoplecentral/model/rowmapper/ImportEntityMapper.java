/**
 * 
 */
package com.jbent.peoplecentral.model.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.jbent.peoplecentral.model.pojo.Import;
import com.jbent.peoplecentral.permission.Permissionable;

/**
 * @author RaviT
 *
 */
public class ImportEntityMapper extends Import implements Permissionable, RowMapper<Import> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2415289097960421961L;
	private List<Import> importEntities = new ArrayList<Import>();
	
	@Override
	public Import mapRow(ResultSet rs, int rownum) throws SQLException {
		Import impEntity = new Import();
		if(rs.getString("import_id") != null){
			impEntity.setImportId(rs.getString("import_id"));
			impEntity.setNumberAttempted(rs.getLong("number_attempted"));
			impEntity.setNumberFailed(rs.getLong("number_failed"));
			impEntity.setTimeStart(rs.getTimestamp("time_start"));
			impEntity.setTimeEnd(rs.getTimestamp("time_ended"));
			impEntity.setCompleted(rs.getBoolean("completed"));
		}
		if(impEntity.getImportId() != null)
			importEntities.add(impEntity);
		return impEntity;
	}
	public List<Import> getImport() {
		return importEntities;
	}

	public void setImportEntities(List<Import> importEntities) {
		this.importEntities = importEntities;
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
