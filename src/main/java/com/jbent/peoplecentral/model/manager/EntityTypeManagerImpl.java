/**
 * 
 */
package com.jbent.peoplecentral.model.manager;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.util.Assert;

import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.exception.ConfigException;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.dao.EntityDAO;
import com.jbent.peoplecentral.model.dao.EntityTypeDAO;
import com.jbent.peoplecentral.model.pojo.Attribute;
import com.jbent.peoplecentral.model.pojo.AttributeFileStorage;
import com.jbent.peoplecentral.model.pojo.Entity;
import com.jbent.peoplecentral.model.pojo.AttributeDataType;
import com.jbent.peoplecentral.model.pojo.AttributeFieldType;
import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;
import com.jbent.peoplecentral.model.pojo.AuditSummary;
import com.jbent.peoplecentral.model.pojo.CompletePermissions;
import com.jbent.peoplecentral.model.pojo.EntityType;
import com.jbent.peoplecentral.model.pojo.Regex;
import com.jbent.peoplecentral.permission.PermissionManager;
import com.jbent.peoplecentral.template.TemplateManager;

/**
 * @author Jason Tesser
 * 
 */
public class EntityTypeManagerImpl extends ApplicationObjectSupport implements
		EntityTypeManager, InitializingBean {

	private EntityTypeDAO entityTypeDAO;
	@Autowired
	private EntityDAO entityDAO;
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private PermissionManager permissionManager = null;
	@Autowired
	private TemplateManager templateManager;
	private long offset;
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(entityTypeDAO, "entityTypeDAO is null");
		Assert.notNull(entityTypeDAO, "entityDAO is null");
		Assert.notNull(entityManager, "entitymanager is null");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jbent.peoplecentral.model.manager.EntityManager#loadEntities()
	 */
	@Override
	public List<EntityType> load() throws DataException  {
		List<EntityType> etTypes = null;
		try {
			etTypes = entityTypeDAO.load();
		} catch (DataException e) {
			logger.error("load: Error load EntityFypes failed:"+e);
			throw new DataException("load: Error load EntityFypes failed:"+e);
		}
		return etTypes;
	}
	
	@Override
	public List<EntityType> entityTypeTableload(long limit,long offset) throws DataException  {
		List<EntityType> etTypes = null;
		try {
			etTypes = entityTypeDAO.entityTypeTableload(limit,offset);
		} catch (DataException e) {
			logger.error("entityTypeTableload: Error entityTypeTableload failed:"+e);
			throw new DataException("entityTypeTableload: Error entityTypeTableload failed:"+e);
		}
		return etTypes;
	}

	@Override
	public EntityType load(long id) throws DataException  {
		EntityType entityType = null;
		try {
			entityType = entityTypeDAO.load(id);
		} catch (DataException e) {
			logger.error("load: Error load EntityFype failed:"+e);
			throw new DataException("load: Error load EntityFype failed:"+e);
		}
		return entityType;
	}
	
	@Override
	public EntityType findByName(String entityTypeName) throws DataException  {
		EntityType entityType = null;
		try {
			entityType = entityTypeDAO.findByName(entityTypeName);
		} catch (DataException e) {
			logger.error("load: Error load EntityFype failed:"+e);
			throw new DataException("load: Error load EntityFype failed:"+e);
		}
		return entityType;
	}

	@Override
	public List<EntityType> save(List<EntityType> entityTypes) throws DataException  {
		List<EntityType> etTypes = null;
		try {
			etTypes = entityTypeDAO.save(entityTypes);
			if(etTypes != null && etTypes.size() > 0){
				for(EntityType et : etTypes){
					permissionManager.setAsParent(et);
				}
			}
		} catch (DataException e) {
			logger.error("save: saving entityType failed:"+e);
			throw new DataException("save: saving entityType failed:"+e);
		}
		return etTypes;
	}

	@Override
	public EntityType save(EntityType entityType) throws DataException {
		boolean isParentSet = false;
		EntityType etType = null;
		try {
			etType = entityTypeDAO.save(entityType);
			if(etType != null){
				logger.debug("save: saving entityType success with Id:"+etType.getId()+" name:"+etType.getName());
				isParentSet = permissionManager.setAsParent(etType);
				if(!isParentSet){
					logger.debug("save: "+etType.getName()+" is falied to setAsParent  :"+isParentSet);
				}else logger.debug("save: "+etType.getName()+" is Parent  :"+isParentSet);
				
				templateManager.getTemplateEngine().clearTemplateCacheFor("/" + ClientManageUtil.loadClientSchema() + "/type/" + etType.getId() + ".vtl");
				templateManager.getTemplateEngine().clearTemplateCacheFor(TemplateManager.COCOAOWL_TEMPLATE_EDIT_MODE_CONSTANT + "/" + ClientManageUtil.loadClientSchema() + "/type/" + etType.getId() + ".vtl");
			}
		} catch (DataException e) {
			logger.error("save: saving entityType failed:"+e);
			throw new DataException("save : saving entityType failed"+e);
		} catch (ConfigException e) {
			logger.error("save: saving template of entityType failed:"+e);
			e.printStackTrace();
		}
		
		return etType;
	}

	@Override
	public List<EntityType> saveAttributes(List<Attribute> attributes) throws DataException {
		boolean isDefalutVisible = false;
		List<EntityType> etTypes = null;
		try {
			etTypes = entityTypeDAO.saveAttributes(attributes);
			if(etTypes != null && etTypes.size() > 0){
				for(Attribute att:attributes){
					for(EntityType et:etTypes){
						if(att.getEntityTypeId()== et.getId()){
							List<Attribute> attList = et.getAttributes();
							if(attList != null && attList.size() > 0){
								Attribute attribute = fetchMatchedAttribute(attList,att);
								if(attribute != null){
									// 	Grant Read Permission to all Roles because By default All attributes are visible. 
									isDefalutVisible = addDefaultVisiblePermissionOnAttribute(attribute);
									if(!isDefalutVisible)logger.error("EntityTypeManagerImpl.saveAttributes: save attribute "+attribute.getName()+ "isDefalutVisible :"+isDefalutVisible);
								}
							}
						}
					}
				}
			}
		} catch (DataException e) {
			logger.error("saveAttributes: Error saveAttributes failed:"+e);
			throw new DataException("saveAttributes: Error saveAttributes failed:"+e);
		}
		return etTypes;
	}

	@Override
	public Attribute loadAttribute(long id) throws DataException {	
		Attribute attribute= null;
		try {
			attribute = entityTypeDAO.loadAttribute(id);
		} catch (DataException e) {
			logger.error("loadAttribute: Error loadAttribute failed:"+e);
			throw new DataException("loadAttribute: Error loadAttribute failed:"+e);
		}
		return attribute;
	}
	@Override
	public String prepareAttributeVelocityName(String attributeName) throws DataException {	
		String  attributeVelocityName= null;		
		attributeVelocityName = attributeName.replaceAll("[^a-zA-Z]+", "");
		attributeVelocityName = attributeVelocityName.trim().toLowerCase();
		return attributeVelocityName;
	}
	
	@Override
	public List <Attribute> prepareAttributeVelocityNameList(List<Attribute> attributeList) throws DataException {
		if(attributeList != null && attributeList.size()>0){
			for(Attribute attribute:attributeList){
				attribute.setAttributeVelocityName(attribute.getName().replaceAll("[^a-zA-Z]+", "").trim().toLowerCase());
			}
		}		
		return attributeList;
	}

	@Override
	public EntityType saveAttribute(Attribute attribute) throws DataException  {
		EntityType ent = new EntityType();
		if(attribute != null){
			if (attribute.getEntityTypeId() < 1) {
				throw new DataException(
						"EntityTypeManagerImpl.saveAttribute : Cannot save attribute without a valid entity type");
			}
			try {
				ent = entityTypeDAO.saveAttribute(attribute);
				if(ent != null){
					logger.debug("saveAttribute: Attribute saved success:"+attribute.getName()+",and id:"+attribute.getId()+", fieldType:"+attribute.getFieldTypeName()+", regex:"+attribute.getRegex().getDisplayName());
					List <Attribute> attributes = ent.getAttributes();
					if(attributes != null && attributes.size() > 0){
						Attribute matchedAttribute = fetchMatchedAttribute(attributes,attribute);
						if(matchedAttribute != null){
							
							// Grant Read Permission to all Roles because By default All attributes are visible. 
							addDefaultVisiblePermissionOnAttribute(matchedAttribute);
						}
					}else{
						logger.debug("saveAttribute: save Attribute get Attributes from return entity type:"+attributes);
					}
				}else{
					logger.error("saveAttribute: save Attribute  return entity type:"+ent);
					throw new DataException("saveAttribute:  saveAttribute failed:");
				}
			} catch (DataException e) {
				logger.error("saveAttribute: Error saveAttribute failed:"+e);
				throw new DataException("saveAttribute: Error saveAttribute failed:"+e);
			}
		}
		return ent;
	}
	@Override
	public EntityType reorderAttribute(List<Long> attributeIds) throws DataException{
		try {
			return entityTypeDAO.reorderAttribute(attributeIds);
		} catch (DataException e) {
			logger.error("reorderAttribute: Error reorderAttribute failed:"+e);
			throw new DataException("reorderAttribute: Error reorderAttribute failed:"+e);
		}
	}

	@Override
	@Autowired
	public void setEntityTypeDAO(EntityTypeDAO entityTypeDAO) {
		this.entityTypeDAO = entityTypeDAO;
	}

	@Override
	public void delete(long id) throws DataException  {
		EntityType ent = null;
		try {
			ent = entityTypeDAO.load(id);
			if(ent != null){
				entityTypeDAO.delete(id);
				logger.debug("delete: deleted EntityType success:"+id);
				// Delete the ACL information as well
				List<Attribute> attList = ent.getAttributes();
				if(attList != null && attList.size() > 0){
					for(Attribute att:attList){
						permissionManager.deletePermission(att);
					}
					permissionManager.deletePermission(ent);
				}
			}
		} catch (DataException e) {
			logger.error("delete: Error delete EntityType failed:"+e);
			throw new DataException("delete: Error delete EntityType failed:"+e);
		}
		
	}
	
	@Override
	public boolean lock(long id, String lockMode) throws DataException {
		try{
			return entityTypeDAO.lock(id, lockMode);
		}catch(Exception e){
			logger.error("lock: Error lock EntityType failed:"+e);
			throw new DataException("lock: Error lock EntityType failed:"+e);
		}
		
	}
	
	@Override
	public void setToLock(List<Long> etIdsList) throws DataException {	
		try{
			entityTypeDAO.setToLock(etIdsList);
		}catch(Exception e){
			logger.error("setToLock: Error setToLock EntityType failed:"+e);
			throw new DataException("setToLock: Error setToLock EntityType failed:"+e);
		}
		
	}

	@Override
	public void deleteAttribute(long id) throws DataException  {
		Attribute att = null;
		try {
			att = entityTypeDAO.loadAttribute(id);
			if(att != null){
				entityTypeDAO.deleteAttribute(id);
				logger.debug("deleteAttribute: Attribute deleted Success for attribute :"+att.getName()+"id:"+att.getId());
				// Delete the ACL information as well
				permissionManager.deletePermission(att);
			}else{
				logger.error("deleteAttribute: Attribute Not Found to delete for attribute Id:"+id);
				throw new DataException("deleteAttribute: Attribute Not Found to delete for attribute Id:"+id);
			}
		} catch (DataException e) {
			logger.error("deleteAttribute: Error delete deleteAttribute failed:"+e);
			throw new DataException("deleteAttribute: Error deleteAttribute failed:"+e);
		}
		
	}

	public AttributeFieldType loadAttributeFieldTypeOptions(long fieldTypeId) throws DataException{
		AttributeFieldType attFieldType = null;
		try {
			attFieldType = entityTypeDAO.loadAttributeFieldTypeOptions(fieldTypeId);
		} catch (DataException e) {
			logger.error("loadAttributeFieldTypeOptions: Error loadAttributeFieldTypeOptions failed:"+e);
			throw new DataException("loadAttributeFieldTypeOptions: Error loadAttributeFieldTypeOptions failed:"+e);
		}
		return attFieldType;
	}
	
	@Override
	public List<AttributeDataType> loadAttributeDataTypes() throws DataException  {
		List<AttributeDataType> attDataType = null;
		try {
			attDataType = entityTypeDAO.loadAttributeDataTypes();
		} catch (DataException e) {
			logger.error("loadAttributeDataTypes: Error loadAttributeDataTypes failed:"+e);
			throw new DataException("loadAttributeDataTypes: Error loadAttributeDataTypes failed:"+e);
		}
		return attDataType;
	}

	@Override
	public List<AttributeFieldType> loadAttributeFieldTypes() throws DataException {
		List<AttributeFieldType> attFieldType = null;
		try {
			attFieldType = entityTypeDAO.loadAttributeFieldTypes();
		} catch (DataException e) {
			logger.error("loadAttributeDataTypes: Error loadAttributeDataTypes failed:"+e);
			throw new DataException("loadAttributeDataTypes: Error loadAttributeDataTypes failed:"+e);
		}
		return attFieldType;	
	}
	
	@Override
	public List<Regex> loadRegex(long fieldtypeId) throws DataException  {
		List<Regex> regex = null;
		try {
			regex = entityTypeDAO.loadRegex(fieldtypeId);
		} catch (DataException e) {
			logger.error("loadRegex: Error loadRegex failed:"+e);
			throw new DataException("loadRegex: Error loadRegex failed:"+e);
		}
		return regex;
	}
	
	@Override
	public List<Regex> loadRegex(long fieldtypeId, long attributeId) throws DataException {
		 List<Regex> regex;
		try {
			regex = entityTypeDAO.loadRegex(fieldtypeId, attributeId);
		} catch (DataException e) {
			logger.error("loadRegex: Error loadRegex failed:"+e);
			throw new DataException("loadRegex: Error loadRegex failed:"+e);
		}
		return regex;
	}

	public boolean addPermissionOnAttribute(Attribute attribute,String role,String permission) throws DataException{
	   boolean status = false;
		try {
			status = permissionManager.permissionHandler(attribute, role, permission);
		} catch (DataException e) {
			logger.error("addPermissionOnAttribute: Error addPermissionOnAttribute failed:"+e);
			throw new DataException("addPermissionOnAttribute: Error addPermissionOnAttribute failed:"+e);
		}
		return status;
	}
	
	public List<CompletePermissions> retrieveEntityTypePermissions(EntityType entityType) throws DataException {
		MutableAcl acl;
		List<CompletePermissions> completeRolesAndPermissions=new ArrayList<CompletePermissions>();
		try {
			acl = permissionManager.fetchAcl(entityType);
			if(acl != null){
				List<CompletePermissions> permissionsList =  permissionManager.fetchPermissionRoles(acl.getEntries(),entityType.getId(),"parent");
				completeRolesAndPermissions = permissionManager.combinedPermissionRolesAndNoPermissionRoles(permissionsList,entityType.getId(),"parent");
				
			}else{
				logger.error("EntityTypeManagerImpl.retrieveEntityTypePermissions:  fetchAcl returns acl:"+acl+" for the entity type:"+entityType.getName());
			}
		} catch (DataException e) {
			logger.error("retrieveEntityTypePermissions: Error retrieveEntityTypePermissions failed:"+e);
			throw new DataException("retrieveEntityTypePermissions: Error retrieveEntityTypePermissions failed:"+e);
		}
		if(completeRolesAndPermissions == null || completeRolesAndPermissions.size() == 0){
			logger.error("EntityTypeManagerImpl.retrieveEntityTypePermissions:  No Roles found on this entityType:"+entityType.getName()+" result :"+completeRolesAndPermissions); 
			return null;
		}
		return completeRolesAndPermissions;
	}
	
	public List<CompletePermissions> retrieveAttributePermissions(Attribute attribute) throws DataException {
		MutableAcl acl;
		List<CompletePermissions> completeRolesAndPermissions = null;		
		try {
			acl = permissionManager.fetchAcl(attribute);
			if(acl != null){
				//retrieve the roles list those permissions assigned 
				List<CompletePermissions> permissionsList =  permissionManager.fetchPermissionRoles(acl.getEntries(),attribute.getId(),"attribute");
				//get the Complete roles meaning role has permission and has NO Permission
				completeRolesAndPermissions = permissionManager.combinedPermissionRolesAndNoPermissionRoles(permissionsList,attribute.getId(),"attribute");
			}else{
				logger.error("EntityTypeManagerImpl.retrieveAttributePermissions:  No acl is found while load fetchAcl:"+acl+" for the attribtue:"+attribute.getName());
			}
		} catch (DataException e) {
			logger.error("retrieveAttributePermissions: Error retrieveAttributePermissions failed:"+e);
			throw new DataException("retrieveAttributePermissions: Error retrieveAttributePermissions failed:"+e);
		}
		if(completeRolesAndPermissions == null || completeRolesAndPermissions.size() == 0){
			logger.error("EntityTypeManagerImpl.retrieveAttributePermissions:  No Roles found on this attirbute:"+attribute.getName()+" result :"+completeRolesAndPermissions);
			return null;
		}
		return completeRolesAndPermissions;
	}
	
	public List<CompletePermissions> addOrRemovePermissionOnEntityType(EntityType entityType,String role,String permission) throws DataException {
		MutableAcl acl;
		boolean status = false;
		List<CompletePermissions> permissionsList = null;
		try {
			status = permissionManager.permissionHandler(entityType, role, permission);
			if(status){
				logger.debug("EntityTypeManagerImpl.addOrRemovePermissionOnEntityType:  retrievePermisssions returns permissionsList:"+permissionsList+" for the enity type:"+entityType.getName()+" role:"+role+" permission:"+permission);
				acl = permissionManager.fetchAcl(entityType);
				permissionsList = permissionManager.fetchPermissionRoles(acl.getEntries(),entityType.getId(),"parent");
			}
		} catch (DataException e) {
			logger.error("addOrRemovePermissionOnEntityType:- Data Exception parsing string :-",e);
			throw new DataException("EntityTypeManagerImpl.addOrRemovePermissionOnEntityType:- Data Exception while paring stirng:-" + e.getMessage(), e);
		}
		return permissionsList;
	}
	
	public boolean addDefaultVisiblePermissionOnAttribute(Attribute attribute) throws DataException {
	   	try {
			return permissionManager.addDefaultVisiblePermissionOnAttribute(attribute);
		} catch (DataException e) {
			logger.error("addDefaultVisiblePermissionOnAttribute:- Error  addDefaultVisiblePermissionOnAttribute failed :-",e);
			throw new DataException("addDefaultVisiblePermissionOnAttribute:- Error  addDefaultVisiblePermissionOnAttribute failed :-",e);
		}
	}
	
	private Attribute fetchMatchedAttribute(List<Attribute> attributes,Attribute att) throws DataException{
		if(attributes != null && attributes.size() > 0){
			for(Attribute attribute:attributes){
				if(attribute.getName().equalsIgnoreCase(att.getName()))
					return attribute;
			}	
		}
		return null;
	}

	@Override
	public List<EntityType> loadEntiyTypeDropDownList() throws DataException  {
		List<EntityType> etDropDowns = null;
		try {
			etDropDowns = entityTypeDAO.load();
		} catch (DataException e) {
			logger.error("loadEntiyTypeDropDownList:- Error  loadEntiyTypeDropDownList failed :-",e);
			throw new DataException("loadEntiyTypeDropDownList:- Error  loadEntiyTypeDropDownList failed :-",e);
		}
		
		return etDropDowns;
		
	}
	

	@Override
	public List<AuditSummary> loadWorkStreamAudit(Date startDate,Date endDate,Long limit,long pageNum,long totalRecords,String action) throws DataException {
		Entity entity;EntityType entType = null;
		if(pageNum == 0){
			offset = 0;
		}else{
			if(action.equalsIgnoreCase("next")){
				offset = limit + offset;
			}else if(action.equalsIgnoreCase("previouse")){
				offset = offset - limit;
			}else if(action.equalsIgnoreCase("last")){
				offset = totalRecords - limit;
			}
		}
		List<AuditSummary> auditSummary = entityTypeDAO.loadWorkStreamAudit(startDate, endDate, limit, offset);
		if(auditSummary != null && auditSummary.size() != 0 ){
			for(AuditSummary summary:auditSummary){
				if(summary.getEntityId()>0){
					entity = entityDAO.loadEntity(summary.getEntityId());
					String title =entityManager.entityTitle(entity);
					summary.setTitle(title);
					summary.setEntityType(entity.getEntityType());
				}else{
					entType =entityTypeDAO.load(summary.getEntityTypeId());
					summary.setEntityType(entType);
				}
			}
		}
		return auditSummary;
	}

	@Override
	public List<Attribute> filterAttributes(List<Attribute> filterAttributes)
			throws DataException {
		return filterAttributes;
	}

	@Override
	public List<AttributeValueStorage> filterAttributesValues(
			List<AttributeValueStorage> filterAttributes) throws DataException {
		return filterAttributes;
	}

	@Override
	public List<AttributeFileStorage> filterFileAttributesValues(
			List<AttributeFileStorage> filterFileAttributeValues)
			throws DataException {
		
		return filterFileAttributeValues;
	}
	
	

}
