package com.jbent.peoplecentral.template;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.cache.TTLCacheEntryValidity;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolution;
import org.thymeleaf.templateresource.StringTemplateResource;

import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.manager.EntityTypeManager;
import com.jbent.peoplecentral.model.pojo.EntityType;
import com.jbent.peoplecentral.velocity.VelocitySpringUtil;
import com.jbent.peoplecentral.web.SessionContext;

public class ThymeleafTemplateResolver implements ITemplateResolver{
	
	protected static final Log logger = LogFactory.getLog(ThymeleafTemplateResolver.class);
	private EntityTypeManager entityTypeManager;
	
	public ThymeleafTemplateResolver() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	public String getName() {
		return "CocoaOwlThymeleafTemplateResolver";
	}

	@Override
	public Integer getOrder() {
		return 1;
	}

	@Override
	public TemplateResolution resolveTemplate(IEngineConfiguration iEngineConfiguration, String ownerTemplate, String templatePathOrId,
			Map<String, Object> templateResolutionAttributes) {
		
		if(templatePathOrId ==null){
			logger.error("No path was passed to get Template");
		}
		
		long entityTypeID = 0;
		String template = null;
		EntityType et = null;
		try{
			entityTypeID = new Long(templatePathOrId.split("/")[3].substring(0,templatePathOrId.split("/")[3].indexOf(".")));
		}catch (Exception e) {
			logger.error("Unable to parse path for Template", e);
		}
		
		//try to load template from DB
		try {
			if(entityTypeManager == null)
				entityTypeManager = new VelocitySpringUtil().getEntityTypeManager();
			et = entityTypeManager.load(entityTypeID);
			if(templatePathOrId.startsWith(TemplateManager.COCOAOWL_TEMPLATE_EDIT_MODE_CONSTANT))
				template = et.getEditTemplate();
			else
				template = et.getTemplate();
		} catch (DataException e) {
			logger.error("Unable to load entity type : " + e.getMessage(),e);
		} catch (NullPointerException e) {
			logger.debug("Entity Type doesn't exist continuing on");
		}
		
		//if not in DB try to load cocoaowl provided template for entitytype
		if(template == null && et != null){
			try {
				if(templatePathOrId.startsWith(TemplateManager.COCOAOWL_TEMPLATE_EDIT_MODE_CONSTANT)){
					template = new String(Files.readAllBytes(Paths.get(SessionContext.getRequest().getSession().getServletContext().getRealPath("/WEB-INF/templates/" + TemplateManager.COCOAOWL_TEMPLATE_EDIT_MODE_CONSTANT + et.getName() + ".html"))), StandardCharsets.UTF_8);
				}else{
					template = new String(Files.readAllBytes(Paths.get(SessionContext.getRequest().getSession().getServletContext().getRealPath("/WEB-INF/templates/" + et.getName() + ".html"))), StandardCharsets.UTF_8);
				}
			} catch (Exception e) {
				logger.debug("Entity Type Template File Not Found" + e.getMessage(),e);
			}
		}
		
		//default implementation to be handled like JSP
		if(template == null || template.trim().length() == 0){
			try{
				if(templatePathOrId.startsWith(TemplateManager.COCOAOWL_TEMPLATE_EDIT_MODE_CONSTANT)){
					template = TemplateBuider.buildEditTemplateCode();
					if(!Files.exists(Paths.get(SessionContext.getRequest().getSession().getServletContext().getRealPath("/WEB-INF/templates/" + TemplateManager.COCOAOWL_TEMPLATE_EDIT_MODE_CONSTANT + et.getName() + ".html")))){
						Files.createFile(Paths.get(SessionContext.getRequest().getSession().getServletContext().getRealPath("/WEB-INF/templates/" + TemplateManager.COCOAOWL_TEMPLATE_EDIT_MODE_CONSTANT + et.getName() + ".html")));
						Files.write(Paths.get(SessionContext.getRequest().getSession().getServletContext().getRealPath("/WEB-INF/templates/" + TemplateManager.COCOAOWL_TEMPLATE_EDIT_MODE_CONSTANT + et.getName() + ".html")), template.getBytes());
					}
				}else{
					template = TemplateBuider.buildViewTemplateCode();
					if(!Files.exists(Paths.get(SessionContext.getRequest().getSession().getServletContext().getRealPath("/WEB-INF/templates/" + et.getName() + ".html")))){
						Files.createFile(Paths.get(SessionContext.getRequest().getSession().getServletContext().getRealPath("/WEB-INF/templates/" + et.getName() + ".html")));
						Files.write(Paths.get(SessionContext.getRequest().getSession().getServletContext().getRealPath("/WEB-INF/templates/" + et.getName() + ".html")), template.getBytes());
					}
				}
			} catch (Exception e) {
				logger.debug("Can not create Entity Type Template File" + e.getMessage(),e);
			}
		}
		
		template = "";

		TemplateResolution tr = new TemplateResolution(new StringTemplateResource(template), TemplateMode.HTML, new TTLCacheEntryValidity(0));
		return tr;
	}

	/**
	 * @param entityTypeManager the entityTypeManager to set
	 */
	@Autowired
	@Required
	public void setEntityTypeManager(EntityTypeManager entityTypeManager) {
		this.entityTypeManager = entityTypeManager;
	}

}
