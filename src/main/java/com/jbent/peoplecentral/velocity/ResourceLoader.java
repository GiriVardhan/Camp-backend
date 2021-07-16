package com.jbent.peoplecentral.velocity;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.manager.EntityTypeManager;
import com.jbent.peoplecentral.model.pojo.EntityType;
import com.jbent.peoplecentral.web.SessionContext;

@Service
public class ResourceLoader extends
		org.apache.velocity.runtime.resource.loader.ResourceLoader {

	protected static final Log logger = LogFactory.getLog(ResourceLoader.class);
	private EntityTypeManager entityTypeManager;
	
	@Override
	public long getLastModified(Resource arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @param path should be /{client}/type/{entitytypeID}.vtl
	 */
	@Override
	public InputStream getResourceStream(String path) throws ResourceNotFoundException {
		if(path ==null){
			logger.error("No path was passed to get VTL");
			throw new ResourceNotFoundException("No path was passed to get VTL");
		}
		long entityTypeID = 0;
		String template = null;
		InputStream is = null;
		EntityType et = null;
		try{
			entityTypeID = new Long(path.split("/")[3].substring(0,path.split("/")[3].indexOf(".")));
		}catch (Exception e) {
			if(path.equals("VM_global_library.vm")){
				try {
					org.springframework.core.io.Resource resource = new ClassPathResource("velocity/VM_global_library.vm");
					is = resource.getInputStream();
				} catch (Exception e1) {
					logger.error(e1.getMessage(),e1);
				}
			}
			if(is==null){
				logger.error("Unable to parse path for VTL", e);
				throw new ResourceNotFoundException("Unable to parse path for VTL", e);
			}
		}
		try{
			//try to load template from DB
			try {
				if(this.entityTypeManager == null){
					this.entityTypeManager = new VelocitySpringUtil().getEntityTypeManager();
				}
				et = entityTypeManager.load(entityTypeID);
				template = et.getTemplate();
			} catch (DataException e) {
				logger.error("Unable to load entity type : " + e.getMessage(),e);
				throw new ResourceNotFoundException("Unable to load entity type", e);
			} catch (NullPointerException e) {
				logger.debug("Entity Type doesn't exist continuing on");
			}
			
			if(template != null && template.trim().length() > 0){
				try {
					is = new ByteArrayInputStream(template.getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					logger.error("Unable to convert template to UTF-8 : " + e.getMessage(),e);
				}
			}
			
			//if not in DB try to load cocoaowl provided template for entitytype
			if(is == null && et != null){
				try {
					is = new FileInputStream(SessionContext.getRequest().getSession().getServletContext().getRealPath("/WEB-INF/templates/" + et.getName() + ".vtl"));
				} catch (FileNotFoundException e) {
					logger.debug("Entity Type File Not Found" + e.getMessage(),e);
					is = null;
				}
			}
			
			//if not in DB or provided template for entitytype then load default template for all entity types 
			if(is == null){
				try {
					is = new FileInputStream(SessionContext.getRequest().getSession().getServletContext().getRealPath("/WEB-INF/templates/default.vtl"));
				} catch (FileNotFoundException e) {
					logger.debug("Default File Not Found" + e.getMessage(),e);
					is = null;
				}
			}
			return is;
		}finally{
			template = null;
			et = null;
		}
	}

	@Override
	public void init(ExtendedProperties arg0) {
		// TODO Auto-generated method stub
		if(this.entityTypeManager == null){
			this.entityTypeManager = new VelocitySpringUtil().getEntityTypeManager();
		}
	}

	@Override
	public boolean isSourceModified(Resource arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @param entityTypeManager the entityTypeManager to set
	 */
	@Autowired
	public void setEntityTypeManager(EntityTypeManager entityTypeManager) {
		this.entityTypeManager = entityTypeManager;
	}
	
}
