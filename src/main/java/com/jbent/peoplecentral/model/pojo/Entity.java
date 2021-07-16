package com.jbent.peoplecentral.model.pojo;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.manager.EntityManager;
import com.jbent.peoplecentral.permission.Permissionable;

@XmlRootElement(name="entities")
public class Entity implements Serializable, Permissionable, Exportable, Comparable<Entity> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8105183644187863498L;
	
	private long entityId;
	private String title;
	private long entityTypeId;
	private String mod_user;
	private String searchIndex;
	private boolean entityValid;
	protected List<AttributeValueStorage> attributeValueStorage;
    private EntityType entityType; 
    private long searchCount;
    private long boxEntityCount;
    private long boxId;
    protected List<Entity> entities; 
    private List<Entity> entity;   
    private List<AttributeFileStorage> attributeFileStorage;
    private EntityManager entityManager;
    
    /**
	 * @param entityId
	 */
	public Entity(long entityId) {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		this.entityId = entityId;
	}

	
	public String getTitle() {
		if(title == null || title == ""){
			try {
				title = entityManager.entityTitle(this);
			} catch (DataException e) {
				e.printStackTrace();
			}
		}
		if(title == null  || title.equals("")){
			if(this.getEntityType() != null)
				title = this.getEntityType().getName()+"_"+getEntityId();
		}
		if(title.startsWith("ROLE_")){
			return entityManager.sanitizeRole(title);
		}
		return title;
	}


	public void setTitile(String title) {
		this.title = title;
	}


	//  @XmlElement(name="entity")
	public List<Entity> getEntities() {
		return entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public Entity() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * @return the entityId
	 */

	public long getEntityId() {
		return entityId;
	}

	/**
	 * @param entityId the entityId to set
	 */

	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}

	/**
	 * @return the entityTypeId
	 */

	public long getEntityTypeId() {
		return entityTypeId;
	}

	/**
	 * @param entityTypeId the entityTypeId to set
	 */

	public void setEntityTypeId(long entityTypeId) {
		this.entityTypeId = entityTypeId;
	}

	/**
	 * @return the mod_user
	 */

	public String getMod_user() {
		return mod_user;
	}

	/**
	 * @param mod_user the mod_user to set
	 */

	public void setMod_user(String mod_user) {
		this.mod_user = mod_user;
	}

	
	/**
	 * @return the searchIndex
	 */
	
	public String getSearchIndex() {
		return searchIndex;
	}
	
	/**
	 * @param mod_user the searchIndex to set
	 */

	public void setSearchIndex(String searchIndex) {
		this.searchIndex = searchIndex;
	}
	
	public boolean isEntityValid() {
		return entityValid;
	}

	public void setEntityValid(boolean entityValid) {
		this.entityValid = entityValid;
	}

	/**
	 * @return the attributeValueStorage
	 */

	public List<AttributeValueStorage> getAttributeValueStorage() {
		return attributeValueStorage;
	}

    
	/**
	 * @param attributeValueStorage the attributeValueStorage to set
	 */

	public void setAttributeValueStorage(
			List<AttributeValueStorage> attributeValueStorage) {
		this.attributeValueStorage = attributeValueStorage;
	}

	/**
	 * @return the entityType
	 */

	public EntityType getEntityType() {
		return entityType;
	}
    
	/**
	 * @param entityType the entityType to set
	 */

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}
	
	/**
	 * @return the searchCount
	 */

	
	public long getSearchCount() {
		return searchCount;
	}
	
	/**
	 * @param searchCount the searchCount to set
	 */

	public void setSearchCount(long searchCount) {
		this.searchCount = searchCount;
	}
	
	/**
	 * @return the boxEntityCount
	 */


	public long getBoxEntityCount() {
		return boxEntityCount;
	}
	/**
	 * @param boxEntityCount the boxEntityCount to set
	 */

	public void setBoxEntityCount(long boxEntityCount) {
		this.boxEntityCount = boxEntityCount;
	}

	@Override
	public long getPermissionId() {
		return entityId;
	}

	@Override
	public String getPermissionType() {
		return "entity_"+entityId;
	}


	
	/**
	 * @return the attributeFileStorage
	 */
	public List<AttributeFileStorage> getAttributeFileStorage() {
		return attributeFileStorage;
	}


	/**
	 * @param attributeFileStorage the attributeFileStorage to set
	 */
	public void setAttributeFileStorage(
			List<AttributeFileStorage> attributeFileStorage) {
		this.attributeFileStorage = attributeFileStorage;
	}


	/**
	 * @return the boxId
	 */

	public long getBoxId() {
		return boxId;
	}
	/**
	 * @param boxId the boxId to set
	 */

	public void setBoxId(long boxId) {
		this.boxId = boxId;
	}

	
	
	 @Override
		public ExportMap getExportMap(List<Entity> entities) {
			ExportMap exportMap = new ExportMap(entities);
			return exportMap;
			
		}
	
	/**
	 * @return the getExportMap
	 */
	
	
	
	/**
	 * @param getExportMap the getExportMap to set
	 */

	
	
	/*public List getExportMap() {
		// TODO Auto-generated method stub
		return  this.attributeValueStorage;
	}*/
	
	 @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	 
	/**
	 * @param entityManager the entityManager to set
	 */
	@Autowired
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public String getValueString(String attributeName){
		//List<AttributeValueStorage> attributeValueStorage = getAttributeValueStorage();
		String result = "";
		for(AttributeValueStorage attributeValueStorage : getAttributeValueStorage()){
			if(attributeValueStorage.getName().equalsIgnoreCase(attributeName)){
				return attributeValueStorage.getValue().toString();
			}
		}
		
		if(getAttributeFileStorage() != null && getAttributeFileStorage().size() > 0){
			for(AttributeFileStorage attributeFileStorage : getAttributeFileStorage()){
				if(attributeFileStorage.getName().equalsIgnoreCase(attributeName)){
					return attributeFileStorage.getImagePath();
				}
			}
		}
		
		return result;
	}
	
	public Object getValueObject(String attributeName){
		//List<AttributeValueStorage> attributeValueStorage = getAttributeValueStorage();
		String result = "";
		for(AttributeValueStorage attributeValueStorage : getAttributeValueStorage()){
			if(attributeValueStorage.getName().equalsIgnoreCase(attributeName)){
				return attributeValueStorage.getValue();
			}
		}
		
		if(getAttributeFileStorage() != null && getAttributeFileStorage().size() > 0){
			for(AttributeFileStorage attributeFileStorage : getAttributeFileStorage()){
				if(attributeFileStorage.getName().equalsIgnoreCase(attributeName)){
					return attributeFileStorage.getImagePath();
				}
			}
		}
		
		return result;
	}


	@Override
	public int compareTo(Entity entity) {
		if(this.getTitle() != null && entity.getTitle() != null)
			return this.getTitle().toLowerCase().compareTo(entity.getTitle().toLowerCase());
		else if(this.getTitle() != null && entity.getTitle() == null)
			return 1;
		else if(this.getTitle() == null && entity.getTitle() != null)
			return -1;
		return 0;
	}
	
	public boolean hasFile(){
		if(getAttributeFileStorage() != null){
			for(AttributeFileStorage afs : getAttributeFileStorage()){
				if(afs.getImagePath() != null  && !afs.getImagePath().equals(""))
					return true;
			}
		}
		return false;
	}
	
}

