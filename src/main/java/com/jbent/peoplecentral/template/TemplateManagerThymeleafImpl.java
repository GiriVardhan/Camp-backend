package com.jbent.peoplecentral.template;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.TemplateSpec;
import org.thymeleaf.context.Context;

import com.jbent.peoplecentral.exception.ConfigException;
import com.jbent.peoplecentral.model.manager.EntityTypeManager;

public class TemplateManagerThymeleafImpl implements TemplateManager {
	
	private static TemplateEngine templateEngine = null;
	private static ThymeleafTemplateResolver templateResolver = new ThymeleafTemplateResolver();
	protected static final Log logger = LogFactory.getLog(TemplateManagerThymeleafImpl.class);
	
	public TemplateManagerThymeleafImpl() {
		synchronized(this) {
			if(templateEngine != null)
				return;
			
			templateEngine = new TemplateEngine();
			templateEngine.setTemplateResolver(templateResolver);
		}
	
	}

	@Override
	public TemplateEngine getTemplateEngine() throws ConfigException {
		if(templateEngine == null){
			logger.error("Template Engine unable to init : THIS SHOULD NEVER HAPPEN!!!");
			throw new ConfigException("Template Engine not avialible");
		}
		return templateEngine;
	}
	
	public TemplateEngine getEntityManagerInstance() throws ConfigException {
		if(templateEngine == null){
			logger.error("Template Engine unable to init : THIS SHOULD NEVER HAPPEN!!!");
			throw new ConfigException("Template Engine not avialible");
		}
		return templateEngine;
	}

	@Override
	public String mergeAsString(String templatePath, Map<String, Object> model) throws ConfigException {
		Context context = new Context();
		context.setVariables(model);
		if(model.get(TemplateManager.COCOAOWL_TEMPLATE_EDIT_MODE_CONSTANT) != null)
			templatePath = TemplateManager.COCOAOWL_TEMPLATE_EDIT_MODE_CONSTANT + templatePath;
		
		return templateEngine.process(templatePath, context);
	}
	
	private EntityTypeManager entityTypeManager;

	
	/**
	 * @param entityTypeManager the entityTypeManager to set
	 */
	@Autowired
	public void setEntityTypeManager(EntityTypeManager entityTypeManager) {
		this.entityTypeManager = entityTypeManager;
	}

	public EntityTypeManager getEntityTypeManager(){
		return this.entityTypeManager;
	}

}
