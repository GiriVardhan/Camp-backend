package com.jbent.peoplecentral.model.pojo;


import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;



public interface Exportable {

	//public List getExportMap();	
	public ExportMap getExportMap(List<Entity> entities);
	
	//public  ExportMap getBoxEntities();
	//public String[] getRoles();
	
}
