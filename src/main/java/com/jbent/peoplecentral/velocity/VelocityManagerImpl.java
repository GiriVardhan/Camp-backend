package com.jbent.peoplecentral.velocity;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.parser.ParseException;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.ToolManager;
import org.springframework.context.support.ApplicationObjectSupport;

import com.jbent.peoplecentral.exception.ConfigException;


public class VelocityManagerImpl extends ApplicationObjectSupport implements VelocityManager{

	private static VelocityEngine ve = null;
	
	public VelocityManagerImpl(){
		synchronized(this) {
			if(ve != null)
				return;
			ve = new VelocityEngine();
//			try{
//				ve.init(this.getClass().getResource("/velocity.properties").getPath());
//			}catch (Exception e) {
//				logger.error(e.getMessage(),e);
//			}
		}
	}
	
	@Override
	public VelocityEngine getPrimaryEngine() throws ConfigException {
		if(ve == null){
			logger.error("Velocity Engine unable to init : THIS SHOULD NEVER HAPPEN!!!");
			throw new ConfigException("Velocity Engine not avialible");
		}
		return ve;
	}

	@Override
	public String mergeAsString(String templatePath, Map<String, Object> model) throws ConfigException, ParseException, ResourceNotFoundException {
		Template t = getPrimaryEngine().getTemplate(templatePath);
		StringWriter sw = new StringWriter();
		ToolContext tc = getToolContext();
		tc.putAll(model);
		tc.put("velocityContext", tc);
		t.merge(tc, sw);
		return sw.toString();
	}

	@Override
	public ToolContext getToolContext() throws ConfigException {
		ToolManager manager = new ToolManager();
		manager.configure(this.getClass().getResource("/tools.xml").getPath());
		return manager.createContext();
	}
	
}
