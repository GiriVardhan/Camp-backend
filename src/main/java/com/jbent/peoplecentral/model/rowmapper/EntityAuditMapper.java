package com.jbent.peoplecentral.model.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.jdbc.core.RowMapper;

public class EntityAuditMapper extends ApplicationObjectSupport implements RowMapper<EntityAuditMapper> {
	
	private Date modDate;
	int count = 0;
	@Override
	public EntityAuditMapper mapRow(ResultSet rs, int rownum) throws SQLException  {		 
		this.modDate = rs.getTimestamp("date");	    
		return this;
	}
	
	

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}
	
	

	/**
	 * @return the entities
	 */
	public Date getModDate() {
		return this.modDate;
	}

	
}
