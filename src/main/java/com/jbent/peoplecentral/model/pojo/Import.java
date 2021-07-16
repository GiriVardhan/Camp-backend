package com.jbent.peoplecentral.model.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


public class Import implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5019705425969429660L;
	private String importId;	
	private long numberAttempted=0;
	private long numberFailed;
	private Timestamp  timeStart;
	private Timestamp  timeEnd=null;
	private boolean completed;
	
	/**
	 * @return the completed
	 */
	public boolean isCompleted() {
		return completed;
	}



	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}


	private List<String> importedEntitiesIds;
	
	public Import() {}

	
	
	/**
	 * @return the importedEntitiesIds
	 */
	public List<String> getImportedEntitiesIds() {
		return importedEntitiesIds;
	}



	/**
	 * @param importedEntitiesIds the importedEntitiesIds to set
	 */
	public void setImportedEntitiesIds(List<String> importedEntitiesIds) {
		this.importedEntitiesIds = importedEntitiesIds;
	}



	public String getImportId() {
		return importId;
	}


	public void setImportId(String importId) {
		this.importId = importId;
	}


	public long getNumberAttempted() {
		return numberAttempted;
	}


	public void setNumberAttempted(long numberAttempted) {
		this.numberAttempted = numberAttempted;
	}


	public long getNumberFailed() {
		return numberFailed;
	}


	public void setNumberFailed(long numberFailed) {
		this.numberFailed = numberFailed;
	}


	public Timestamp getTimeStart() {
		return timeStart;
	}


	public void setTimeStart(Timestamp timeStart) {
		this.timeStart = timeStart;
	}


	public Timestamp getTimeEnd() {
		return timeEnd;
	}


	public void setTimeEnd(Timestamp timeEnd) {
		this.timeEnd = timeEnd;
	}


		
}

