package com.jbent.peoplecentral.jmx.mbean;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Log4jMBean {
	
	/**
	 * Returns the level of the root logger
	 * @return
	 */
	public String getLogRootLevel(){
		return Logger.getRootLogger().getLevel().toString();
	}
	
	/**
	 * Sets the root level
	 * @param level
	 */
	public void setLogRootLevel(String level){
		if ("debug".equalsIgnoreCase(level)) {	    	  
			Logger.getRootLogger().setLevel(Level.DEBUG);	        
	      } else if ("info".equalsIgnoreCase(level)) {
	    	  Logger.getRootLogger().setLevel(Level.INFO);
	      } else if ("error".equalsIgnoreCase(level)) {
	    	  Logger.getRootLogger().setLevel(Level.ERROR);
	      } else if ("fatal".equalsIgnoreCase(level)) {
	    	  Logger.getRootLogger().setLevel(Level.FATAL);
	      } else if ("warn".equalsIgnoreCase(level)) {
	    	  Logger.getRootLogger().setLevel(Level.WARN);
	      }
	}
	
	/**
	 * Returns the level for a specific package name
	 * @param packageName
	 * @return
	 */
	public String getLogLevel(String packageName){
		return Logger.getLogger(packageName).getLevel().toString();
	}
	
	/**
	 * Set the logger for a specific package
	 * @param packageName
	 * @param level
	 */
	 public void setLogLevel(String packageName, String level) {
	      if ("debug".equalsIgnoreCase(level)) {	    	  
	         Logger.getLogger(packageName).setLevel(Level.DEBUG);	        
	      } else if ("info".equalsIgnoreCase(level)) {
	         Logger.getLogger(packageName).setLevel(Level.INFO);
	      } else if ("error".equalsIgnoreCase(level)) {
	         Logger.getLogger(packageName).setLevel(Level.ERROR);
	      } else if ("fatal".equalsIgnoreCase(level)) {
	         Logger.getLogger(packageName).setLevel(Level.FATAL);
	      } else if ("warn".equalsIgnoreCase(level)) {
	         Logger.getLogger(packageName).setLevel(Level.WARN);
	      }
	  }


}
