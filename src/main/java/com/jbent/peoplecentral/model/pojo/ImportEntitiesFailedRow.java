package com.jbent.peoplecentral.model.pojo;

import java.io.Serializable;
import java.util.List;



public class ImportEntitiesFailedRow implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2037289940269273273L;
	private long id;
	private String importId;	
	private long rowNumberFailed;
	private String reason;
	private String  data;
	private List<ImportEntitiesFailedRow> importFailedEntites;
	
	
	/**
	 * @return the importFailedEntites
	 */
	public List<ImportEntitiesFailedRow> getImportFailedEntites() {
		return importFailedEntites;
	}

	/**
	 * @param importFailedEntites the importFailedEntites to set
	 */
	public void setImportFailedEntites(
			List<ImportEntitiesFailedRow> importFailedEntites) {
		this.importFailedEntites = importFailedEntites;
	}

	public ImportEntitiesFailedRow() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getImportId() {
		return importId;
	}

	public void setImportId(String importId) {
		this.importId = importId;
	}

	public long getRowNumberFailed() {
		return rowNumberFailed;
	}

	public void setRowNumberFailed(long rowNumberFailed) {
		this.rowNumberFailed = rowNumberFailed;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}


		
}

