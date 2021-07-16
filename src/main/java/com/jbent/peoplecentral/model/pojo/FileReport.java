package com.jbent.peoplecentral.model.pojo;

import java.io.Serializable;
import java.util.List;

public class FileReport implements Serializable{

	private static final long serialVersionUID = 3828447528374967718L;
	private String name;
	private boolean saved;
	private String description;
	private List<FileReport> fileReports;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @return the saved
	 */
	public boolean isSaved() {
		return saved;
	}
	/**
	 * @param saved the saved to set
	 */
	public void setSaved(boolean saved) {
		this.saved = saved;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the fileReport
	 */
	public List<FileReport> getFileReports() {
		return fileReports;
	}
	/**
	 * @param fileReport the fileReport to set
	 */
	public void setFileReport(List<FileReport> fileReports) {
		this.fileReports= fileReports;
	}
	
}
