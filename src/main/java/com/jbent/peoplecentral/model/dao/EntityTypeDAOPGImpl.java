/**
 * 
 */
package com.jbent.peoplecentral.model.dao;

import java.sql.Date;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.pojo.Attribute;
import com.jbent.peoplecentral.model.pojo.AttributeChoice;
import com.jbent.peoplecentral.model.pojo.AttributeDataType;
import com.jbent.peoplecentral.model.pojo.AttributeFieldType;
import com.jbent.peoplecentral.model.pojo.AuditSummary;
import com.jbent.peoplecentral.model.pojo.EntityType;
import com.jbent.peoplecentral.model.pojo.FieldTypeOption;
import com.jbent.peoplecentral.model.pojo.Regex;
import com.jbent.peoplecentral.model.pojo.RelatedEntityType;
import com.jbent.peoplecentral.model.rowmapper.AttributeDataTypeMapper;
import com.jbent.peoplecentral.model.rowmapper.AttributeFieldTypeMapper;
import com.jbent.peoplecentral.model.rowmapper.AttributeFieldTypeOptionsMapper;
import com.jbent.peoplecentral.model.rowmapper.AttributeMapper;
import com.jbent.peoplecentral.model.rowmapper.AuditSummaryMapper;
import com.jbent.peoplecentral.model.rowmapper.EntityTypeMapper;
import com.jbent.peoplecentral.model.rowmapper.EntityTypeTableMapper;
import com.jbent.peoplecentral.model.rowmapper.RegexMapper;
import com.jbent.peoplecentral.permission.PermissionManager;
import com.jbent.peoplecentral.postgres.PGArrayGeneric;
import com.jbent.peoplecentral.postgres.PGAttribute;
import com.jbent.peoplecentral.postgres.PGAttributeChoice;
import com.jbent.peoplecentral.postgres.PGAttributeFieldtypeOptionValue;
import com.jbent.peoplecentral.postgres.PGEntityType;
import com.jbent.peoplecentral.postgres.PGRegex;
import com.jbent.peoplecentral.postgres.PGRelatedEntityType;

/**
 * @author Jason Tesser
 *
 */
public class EntityTypeDAOPGImpl extends ApplicationObjectSupport implements EntityTypeDAO, InitializingBean {

	private JdbcTemplate simpleJdbcTemplate;
	private PermissionManager permissionManager;
	
	private final String ENTITY_TYPES_LOAD = "SELECT * FROM entitytype_load();";
	private final String ENTITY_TYPES_TABLE_LOAD = "SELECT * FROM entitytype_tab_load(?,?,?);";
	private final String REORDER_ATTRIBUTES = "SELECT * FROM attribute_reorder(?,?);";
	private final String ENTITY_TYPE_LOAD = "SELECT * FROM entitytype_load() WHERE entity_type_id = ?;";
	private final String ENTITY_TYPE_LOAD_BY_NAME = "SELECT * FROM entitytype_load() WHERE entity_type_name = ?;";
	private final String ENTITY_TYPES_SAVE = "SELECT * FROM entity_types_save(?);";
	private final String SAVE_ATTRIBUTES = "SELECT * FROM entitytype_attribute_add(?,?,?,?);";
	private final String SAVE_ATTRIBUTES_RETURN_SINGLE_ET = "SELECT * FROM entitytype_attribute_add(?,?,?,?,?) where entity_type_id = ?;";
	private final String ATTRIBUTE_LOAD = "SELECT * FROM entitytype_load() WHERE attribute_id = ?;";
	private final String ATTRIBUTE_TO_DELETE = "SELECT * FROM attribute_to_delete(?,?);";
	private final String ENTITY_TYPE_DELETE = "SELECT entity_type_delete(?,?);";
	private final String ATTRIBUTE_DATA_TYPES_LOAD = "SELECT * from attribute_datatypes_load()";
	private final String ATTRIBUTE_FIELD_TYPES_LOAD = "SELECT * from attribute_fieldtypes_load();";
	private final String REGEX_LOAD = "SELECT * from regex_load_old(?);";
	private final String REGEX_LOAD_EDIT = "SELECT * from regex_load(?,?);";
	private final String ENTITY_TYPE_TOGGLE = "SELECT entity_type_set_lockmode(?,?);";
	private final String ENTITY_TYPE_LOCK = "SELECT entity_type_set_defaultlock(?);";
	private final String ATTRIBUTE_FIELD_TYPE_OPTIONS_LOAD = "SELECT * from attribute_fieldtype_options_load(?);";
	private final String WORK_STREAM_LOAD = "SELECT * from audit_summary_detail(?,?,?,?,?);";

	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(simpleJdbcTemplate, "SimpleJDBCTemplate is null");
		Assert.notNull(permissionManager, "permissionManager is null");
	}
	
	@Autowired
	public void setDataSource(DataSource dataSource) { 
		this.simpleJdbcTemplate = new JdbcTemplate(dataSource);
	} 
	
	/* (non-Javadoc)
	 * @see com.jbent.peoplecentral.model.dao.EntityTypeDAO#loadEntities()
	 */
	
	@Override
	public List<EntityType> load()  throws DataException{
		List<EntityType> ets = null;
		try{
			List<EntityType> em = simpleJdbcTemplate.query(ENTITY_TYPES_LOAD,new EntityTypeMapper()).get(0).getEntities();
			if(em != null && em.size() > 0){
				ets = em;
			}
		}catch (Exception e) {
			logger.error("EntityTypeDAOImpl.load : loadEntityTypes failed : ", e);
			throw new DataException("Unable to load EntityType",e);
		}
		if(ets == null){
			logger.debug("EntityTypeDAOImpl.load :No Entity Type Found in Mapper");
			return null;
		}
		return ets;
	}
	
	@Override
	public List<EntityType> entityTypeTableload(long limit,long offset)  throws DataException{
		List<EntityType> ets = null;
		try{
			List<EntityType> em = simpleJdbcTemplate.query(ENTITY_TYPES_TABLE_LOAD,new EntityTypeTableMapper(),permissionManager.getUsername(),limit,offset);
			if(em != null && em.size() > 0){
				ets = em;
			}
		}catch (Exception e) {
			logger.error("EntityTypeDAOImpl.entityTypeTableload : loadEntityType failed : ", e);
			throw new DataException("EntityTypeDAOImpl.entityTypeTableload:Unable to load EntityType",e);
		}
		if(ets == null){
			logger.debug("EntityTypeDAOImpl.entityTypeTableload:No Entity Type Found in Mapper");
			return null;
		}
		return ets;
	}

	@Override
	public EntityType load(long id)  throws DataException{
		List<EntityType> ets = null;
		try{
			List<EntityTypeMapper> em = simpleJdbcTemplate.query(ENTITY_TYPE_LOAD,new EntityTypeMapper(),id);
			if(em != null && em.size() > 0){
				ets = em.get(0).getEntities();
			}
		}catch (Exception e) {
			logger.error("EntityTypeDAOImpl : loadEntity failed : ", e);
			throw new DataException("Unable to load Entity",e);
		}
		if(ets == null){
			logger.debug("No Entity Type Found in Mapper");
			return null;
		}
		if(ets.size() > 1){
			logger.warn("More then one Entity Type Found in Mapper with id of " + id);
		}
		return ets.get(0);
	}

	@Override
	public EntityType save(EntityType entityType) throws DataException {
		List<EntityType> ets = new ArrayList<EntityType>();
		ets.add(entityType);
		ets =  save(ets);
		for (EntityType et : ets) {
			if(et.getName().equals(entityType.getName())){
				return et;
			}
		}
		return null;
	}
	
	@Override
	public List<EntityType> save(List<EntityType> entityTypes) throws DataException {
		for(EntityType entityType:entityTypes ){
			entityType.setMod_user(permissionManager.getUsername());			
		}
		try{
			return simpleJdbcTemplate.query(ENTITY_TYPES_SAVE,new EntityTypeMapper(),PGEntityType.getPGEntityTypeArray(entityTypes)).get(0).getEntities();
		}catch (Exception e) {
			logger.error("EntityTypeDAOImpl : save failed : ", e);
			throw new DataException("Unable to save Entity Type",e);
		}
	}
	
	@Override
	public EntityType saveAttribute(Attribute attribute) throws DataException {
		List<Attribute> atts = new ArrayList<Attribute>();
		atts.add(attribute);
		return saveAttributes(atts, true).get(0);
	}
	
	@Override
	public List<EntityType> saveAttributes(List<Attribute> attributes) throws DataException {
		return saveAttributes(attributes, false);
	}
	
	private List<EntityType> saveAttributes(List<Attribute> attributes, boolean returnSingle) throws DataException {
		List<Attribute> atts = new ArrayList<Attribute>();
		List<AttributeChoice> catts = new ArrayList<AttributeChoice>();
		List<FieldTypeOption> optionValues = new ArrayList<FieldTypeOption>();
		Regex regex = new Regex();		
		RelatedEntityType relatedEntityType = new RelatedEntityType();
		AttributeChoice attributeChoice = new AttributeChoice();
		try{
			for (Attribute a : attributes) {
				a.setMod_user(permissionManager.getUsername());
				if(a.getAttributeChoice()!= null ){	
					if(a.getAttributeChoice().getChoiceEntityTypeAttributeId()>0){
						attributeChoice = a.getAttributeChoice();
						attributeChoice.setMod_user(permissionManager.getUsername());
						catts.add(attributeChoice);
					}
					}if(a.getRelatedEntityType()!= null){
						if(a.getRelatedEntityType().getChildEntityTypeId()>0){
							relatedEntityType = a.getRelatedEntityType();
							relatedEntityType.setMod_user(permissionManager.getUsername());
						}				
					}			
					if(a.getRegex().getRegexId() < 0 ){				
						regex.setPattern(a.getRegex().getPattern());
						regex.setDisplayName("custom");
						regex.setCustom(true);
					}else regex.setRegexId(a.getRegex().getRegexId());				
					if(a.getOptions() !=null)
						optionValues.addAll( a.getOptions());
					if(a instanceof AttributeChoice){
						catts.add((AttributeChoice)a);
					}else{
						atts.add(a);
					}
				}
			if(!returnSingle){
				return simpleJdbcTemplate.query(SAVE_ATTRIBUTES,new EntityTypeMapper(),PGAttribute.getPGEntityTypeArray(atts),PGAttributeChoice.getPGEntityTypeArray(catts),PGAttributeFieldtypeOptionValue.getPGEntityTypeArray(optionValues)).get(0).getEntities();
			}else{
				return simpleJdbcTemplate.query(SAVE_ATTRIBUTES_RETURN_SINGLE_ET,new EntityTypeMapper(),PGAttribute.getPGEntityTypeArray(atts),PGAttributeChoice.getPGEntityTypeArray(catts),PGAttributeFieldtypeOptionValue.getPGEntityTypeArray(optionValues),new PGRegex(regex),new PGRelatedEntityType(relatedEntityType), attributes.get(0).getEntityTypeId()).get(0).getEntities();
			}
		}catch (Exception e) {			
			logger.error("EntityTypeDAOImpl : saveAttributes failed : "+ e.getMessage());
			throw new DataException("Unable to save Attributes"+e.getMessage());
		}
	}
	
	@Override
	public Attribute loadAttribute(long id) throws DataException{
		List<Attribute> ets = null;
		try{
			ets = simpleJdbcTemplate.query(ATTRIBUTE_LOAD,new AttributeMapper(),id);
		}catch (Exception e) {
			logger.error("EntityTypeDAOImpl : loadAttribute failed : ", e);
			throw new DataException("Unable to save load the attribute",e);
		}
		if(ets == null || ets.size() < 1){
			logger.debug("No Attribute Type Found in Mapper");
			return null;
		}
		if(ets.size() > 1){
			logger.warn("More then one Attribute Found in Mapper with id of " + id);
		}
		return ets.get(0);
	}

	
	public EntityType reorderAttribute(List<Long> attributeIds) throws DataException {
		PGArrayGeneric pa = new PGArrayGeneric();
		pa.setArray(Types.BIGINT, attributeIds.toArray());
		List<EntityType> ets;
		try{
			ets = simpleJdbcTemplate.query(REORDER_ATTRIBUTES,new EntityTypeMapper(),pa,permissionManager.getUsername()).get(0).getEntities();
		}catch (Exception e) {
			logger.error("EntityTypeDAOImpl : loadEntity failed : ", e);
			throw new DataException("Unable to load Entity",e);
		}
		if(ets == null){
			logger.debug("No Entity Type Found in Mapper");
			return null;
		}
		if(ets.size() > 1){
			logger.warn("More then one Entity Type Found in Mapper");
		}
		return ets.get(0);
	}
	
	@Override
	public List<AttributeDataType> loadAttributeDataTypes() throws DataException {
		try{
			return simpleJdbcTemplate.query(ATTRIBUTE_DATA_TYPES_LOAD,new AttributeDataTypeMapper());
		}catch(Exception e){
			logger.error("EntityTypeDAOImpl : loadAttributeDataTypes failed : ", e);
			throw new DataException("Unable to loadAttributeDataTypes",e);
		}
	}

	@Override
	public List<AttributeFieldType> loadAttributeFieldTypes()
			throws DataException {
		try{
			return simpleJdbcTemplate.query(ATTRIBUTE_FIELD_TYPES_LOAD,new AttributeFieldTypeMapper());
		}catch(Exception e){
			logger.error("EntityTypeDAOImpl : loadAttributeFieldTypes failed : ", e);
			throw new DataException("Unable to loadAttributeFieldTypes",e);
		}
	}
	
	
	public AttributeFieldType loadAttributeFieldTypeOptions(long fieldTypeId)
			throws DataException {
		List<AttributeFieldType> fieldTypes;
		try{
			fieldTypes = simpleJdbcTemplate.query(ATTRIBUTE_FIELD_TYPE_OPTIONS_LOAD,new AttributeFieldTypeOptionsMapper(),fieldTypeId);
		}catch(Exception e){
			logger.error("EntityTypeDAOImpl : loadAttributeFieldTypeOptions failed : ", e);
			throw new DataException("Unable to loadAttributeFieldTypeOptions",e);
		}
		if(fieldTypes == null || fieldTypes.size() < 1){
			logger.debug("No Field Type Found in Mapper");
			return null;
		}else
		return fieldTypes.get(0);
	}
	
	/**
	 * @param permissionManager the permissionManager to set
	 */
	@Autowired
	public void setPermissionManager(PermissionManager permissionManager) {
		this.permissionManager = permissionManager;
	}

	@Override
	public void delete(long id) throws DataException {
		try {
			simpleJdbcTemplate.queryForMap(ENTITY_TYPE_DELETE,id,permissionManager.getUsername());
		} catch (DataAccessException e) {
			logger.error("EntityTypeDAOImpl : delete failed : ", e);
			throw new DataException("Unable to Delete Entity Type",e);
		}
	}	
	
	@Override
	public boolean lock(long id, String lockMode) throws DataException {
		boolean mode = false;
		Integer lckMode=0;
		try{
			if(lockMode != null){
				if(lockMode.equalsIgnoreCase("true")){
					lckMode = simpleJdbcTemplate.queryForObject(ENTITY_TYPE_TOGGLE,new Object[]{id,"true"},Integer.class);
				}else if(lockMode.equalsIgnoreCase("false")){
					lckMode = simpleJdbcTemplate.queryForObject(ENTITY_TYPE_TOGGLE,new Object[]{id,"false"},Integer.class);
				}
			}else{
				lckMode = simpleJdbcTemplate.queryForObject(ENTITY_TYPE_TOGGLE,new Object[]{id,null},Integer.class);
			}	
		}catch(Exception e){
			logger.error("EntityTypeDAOImpl : failed to lock : ", e);
			throw new DataException("EntityTypeDAOImpl : failed to lock : ", e);
		}
		lckMode = (lckMode == null) ? 0 : lckMode.intValue();
		if(lckMode==1){
			mode = true;
		}else if(lckMode==0){
		mode = false;
		} 
		return mode;
	}
	
	@Override
	public void setToLock(List<Long> etIdsList) throws DataException {
		PGArrayGeneric etIdsArr = new PGArrayGeneric();
		etIdsArr.setArray(Types.BIGINT, etIdsList.toArray());
		try{
			simpleJdbcTemplate.queryForMap(ENTITY_TYPE_LOCK,etIdsArr);
		}catch(Exception e){
			logger.error("EntityTypeDAOImpl.setToLock : failed to setToLock : ", e);
			throw new DataException("EntityTypeDAOImpl.setToLock : failed to setToLock : ", e);
		}
		
	}	
	
	@Override
	public void deleteAttribute(long id) throws DataException {
		try{
			simpleJdbcTemplate.queryForMap(ATTRIBUTE_TO_DELETE,id,permissionManager.getUsername());
		}catch(Exception e){
			logger.error("EntityTypeDAOImpl.deleteAttribute : failed to deleteAttribute : ", e);
			throw new DataException("EntityTypeDAOImpl.deleteAttribute : failed to deleteAttribute : ", e);
		}
		
	}
	
	@Override
	public List<Regex> loadRegex(long fieldtypeId) throws DataException {
		List<Regex> ret = null;
		try{
			ret = simpleJdbcTemplate.query(REGEX_LOAD,new RegexMapper(),fieldtypeId);
		}catch (Exception e) {
			logger.error("EntityTypeDAOImpl : loadRegex failed : ", e);
			throw new DataException("Unable to get the values",e);
		}
		if(ret == null){
			logger.debug("No Regex Type Found in Mapper");
			return null;
		}
		return ret;
	}
	
	@Override
	public List<Regex> loadRegex(long fieldtypeId, long attributeId) throws DataException {
		List<Regex> ret = null;
		try{
			ret = simpleJdbcTemplate.query(REGEX_LOAD_EDIT,new RegexMapper(),fieldtypeId,attributeId);
		}catch (Exception e) {
			logger.error("EntityTypeDAOImpl : loadRegex failed : ", e);
			throw new DataException("Unable to get the values",e);
		}
		if(ret == null){
			logger.debug("No Regex Type Found in Mapper");
			return null;
		}
		return ret;
	}

	@Override
	public List<AuditSummary> loadWorkStreamAudit(Date startDate,Date endDate,Long limit,Long offset) throws DataException {
		List<AuditSummaryMapper> auditMapper = new ArrayList<AuditSummaryMapper>();
		List<AuditSummary> auditSummary = null;
		try {
			auditMapper = simpleJdbcTemplate.query(WORK_STREAM_LOAD,new AuditSummaryMapper(),startDate,endDate,permissionManager.getUsername(),limit,offset);
		} catch (DataAccessException e) {
			logger.debug("DataAccessError while loading auditSummary"+e.getMessage());
			throw new DataException("DataAccessError while loading auditSummary"+e.getMessage());
		} catch (DataException e) {
			logger.debug("Data Error while loading auditSummary"+e.getMessage());
			throw new DataException("Data Error while loading auditSummary"+e.getMessage());
			
		} 
		if(auditMapper == null || auditMapper.size() == 0){
			logger.debug("No Entity Type Found in Audit Mapper");
			return null;
		}
		if(auditMapper != null && auditMapper.size() > 0){
			auditSummary = auditMapper.get(0).getAuditSummary();
		}
		
		return auditSummary;
	}

	@Override
	public EntityType findByName(String entityTypeName) throws DataException{

		List<EntityType> ets = null;
		try{
			List<EntityTypeMapper> em = simpleJdbcTemplate.query(ENTITY_TYPE_LOAD_BY_NAME,new EntityTypeMapper(),entityTypeName);
			if(em != null && em.size() > 0){
				ets = em.get(0).getEntities();
			}
		}catch (Exception e) {
			logger.error("EntityTypeDAOImpl : loadEntity failed : ", e);
			throw new DataException("Unable to load Entity",e);
		}
		if(ets == null){
			logger.debug("No Entity Type Found in Mapper");
			return null;
		}
		if(ets.size() > 1){
			logger.warn("More then one Entity Type Found in Mapper with id of " + entityTypeName);
		}
		return ets.get(0);
	}	
}
