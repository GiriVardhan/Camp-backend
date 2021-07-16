package com.jbent.peoplecentral.template;

import java.util.Map;

import org.thymeleaf.TemplateEngine;

import com.jbent.peoplecentral.exception.ConfigException;

public interface TemplateManager {
	
	public String COCOAOWL_TEMPLATE_EDIT_MODE_CONSTANT = "COCOAOWL_TEMPLATE_EDIT_MODE_CONSTANT_";
	public TemplateEngine getTemplateEngine() throws ConfigException;
	
	public String mergeAsString(String templatePath, Map<String, Object> model) throws ConfigException;

}
