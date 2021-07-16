/**
 * 
 */
package com.jbent.peoplecentral.model.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.jbent.peoplecentral.model.pojo.ImportEntitiesFailedRow;
import com.jbent.peoplecentral.permission.Permissionable;

/**
 * @author RaviT
 *
 */
public class ImportEntitiesFailedRowMapper implements Permissionable, RowMapper<ImportEntitiesFailedRow> {
	
	private static final long serialVersionUID = 2415289097960421961L;
	private List<ImportEntitiesFailedRow> importEntiesFaild = new ArrayList<ImportEntitiesFailedRow>();
	
	
	@Override
	public ImportEntitiesFailedRow mapRow(ResultSet rs, int rownum) throws SQLException {
		ImportEntitiesFailedRow impEntities = new ImportEntitiesFailedRow();
		if(rs.getLong("id") > 0){
			impEntities.setId(rs.getLong("id"));
			impEntities.setImportId(rs.getString("import_id"));
			impEntities.setRowNumberFailed(rs.getLong("row_number_failed"));
			impEntities.setReason(rs.getString("reason"));
			impEntities.setData(rs.getString("data"));
			importEntiesFaild.add(impEntities);
		}
		return impEntities;
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

	/**
	 * @return the importEntiesFaild
	 */
	public List<ImportEntitiesFailedRow> getImportEntiesFaild() {
		return importEntiesFaild;
	}

	/**
	 * @param importEntiesFaild the importEntiesFaild to set
	 */
	public void setImportEntiesFaild(List<ImportEntitiesFailedRow> importEntiesFaild) {
		this.importEntiesFaild = importEntiesFaild;
	}


}
