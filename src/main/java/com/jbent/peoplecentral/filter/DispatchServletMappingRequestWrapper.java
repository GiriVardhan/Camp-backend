/**
 * 
 */
package com.jbent.peoplecentral.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author jasontesser
 *
 */
public class DispatchServletMappingRequestWrapper extends
		HttpServletRequestWrapper {

	String mapping = "";
	
	public DispatchServletMappingRequestWrapper(HttpServletRequest request, String uriMaping) {
		super(request);
		mapping = uriMaping;
	}

	@Override
	public String getParameter(String name) {
		if(name.equals("_uri_mapping")){
			return mapping;
		}
		return super.getParameter(name);
	}
	
	@Override
	public String[] getParameterValues(String name) {
		if(name.equals("_uri_mapping")){
			String[] r =  new String[1];
			r[0] = mapping;
			return r;
		}
		return super.getParameterValues(name);
	}
	
}
