package com.jbent.peoplecentral.velocity;

import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.parser.ParseException;

import com.jbent.peoplecentral.exception.ConfigException;

public interface VelocityManager {

	/**
	 * Returns a VelocityEngine with all available tools loaded
	 * @return
	 * @throws ConfigException 
	 */
	public VelocityEngine getPrimaryEngine() throws ConfigException;
	
	/**
	 * Will return a string of the merged template merging the model into the Velocity context
	 * @param templatePath
	 * @param model
	 * @return
	 * @throws ConfigException 
	 * @throws ResourceNotFoundException 
	 * @throws ParseException 
	 */
	public String mergeAsString(String templatePath, Map<String, Object> model) throws ConfigException, ParseException, ResourceNotFoundException;
	
	/**
	 * Comes with all available tools loaded
	 * @return
	 * @throws ConfigException 
	 */
	public Context getToolContext() throws ConfigException; 
}
