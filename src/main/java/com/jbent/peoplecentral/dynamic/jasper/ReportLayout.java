package com.jbent.peoplecentral.dynamic.jasper;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.WordUtils;

import com.jbent.peoplecentral.model.pojo.Attribute;

import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
 

public class ReportLayout extends CustomBaseDjReport {
	

	
@Override
public DynamicReport buildReport() throws Exception {
	// 	The column fields must match the name of the  properties in your datasource
	// 	For example, if in you're datasource, you have a field name firstName, then the column
	// 	field must have a fieldName as well
	// Create an instance of FastReportBuilder
	
	//Create columns for Report from a ListBox
	List<Attribute> attributes = getAttributes();
	FastReportBuilder drb = new FastReportBuilder();	
	for(Attribute attribute:attributes){
		if(attribute.getDataTypeId() == 2)
			drb.addColumn(attribute.getName(),attribute.getName(), Long.class.getName(),10);
		else if(attribute.getDataTypeId() == 3)
			drb.addColumn(attribute.getName(),attribute.getName(), Date.class.getName(), 10);
		else
			drb.addColumn(attribute.getName(),attribute.getName(), String.class.getName(), 10);	
	}
	
	// Format when adding columns:
	// Friendly column name,  Field name (case-sensitive), Type, Width of the column
	
	//drb.addGroups(2)
	drb.setPrintColumnNames(true)
 
	// Disables pagination
	.setIgnorePagination(false)
 
	// 	Experiment with this numbers to see the effect
	.setMargins(0, 0, 0, 0) 
	
	// Set the title shown inside the Excel file
	.setTitle(WordUtils.capitalize(getTitle())+"") 
 
	// Set the subtitle shown inside the Excel file
	.setSubtitle("This report was generated at " + new Date()) 
 
	// Set to true if you want to constrain your report within the page boundaries
	// For longer reports, set it to false
	.setUseFullPageWidth(true);
 
	// Set the name of the file
	drb.setReportName(WordUtils.capitalize(getTitle()));
 
	// Build the report layout
	// Note this just the layout. It doesn't have any data yet!
	DynamicReport dr = drb.build();
 
	// Return the layout
	return dr;

}







}