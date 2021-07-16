/**
 * 
 */
package com.jbent.peoplecentral.model.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.jdbc.core.RowMapper;

import com.jbent.peoplecentral.model.pojo.AuditSummary;
import com.jbent.peoplecentral.permission.Permissionable;

/**
 * @author RaviT
 *
 */
public class AuditSummaryMapper extends ApplicationObjectSupport implements Permissionable, RowMapper<AuditSummaryMapper> {
	
	private static final long serialVersionUID = 2415289097960421961L;
	private List<AuditSummary> auditSummary = new ArrayList<AuditSummary>();
	public List<AuditSummary> getAuditSummary() {
		return auditSummary;
	}

	public void setAuditSummary(List<AuditSummary> auditSummary) {
		this.auditSummary = auditSummary;
	}

	@Override
	public AuditSummaryMapper mapRow(ResultSet rs, int rownum) throws SQLException {
		AuditSummary atv = new AuditSummary();
		
		if(rs.getLong("audit_id") > 0){
			atv.setAuditId(rs.getLong("audit_id"));
			atv.setAction(rs.getString("action"));
			atv.setEntityTypeId(rs.getLong("entity_type_id"));
			atv.setEntityId(rs.getLong("entity"));
			atv.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(rs.getTimestamp("date")));
			atv.setTableName(rs.getString("table_name"));
			atv.setMod_user(rs.getString("user_name"));
			atv.setAuditCount(rs.getLong("count"));
			}
		if(rs.getLong("audit_id") > 0)
			auditSummary.add(atv);
		
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
	
	
}
