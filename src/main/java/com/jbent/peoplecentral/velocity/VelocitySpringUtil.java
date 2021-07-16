package com.jbent.peoplecentral.velocity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.jbent.peoplecentral.model.manager.EntityTypeManager;

public class VelocitySpringUtil {

	public VelocitySpringUtil(){
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
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
