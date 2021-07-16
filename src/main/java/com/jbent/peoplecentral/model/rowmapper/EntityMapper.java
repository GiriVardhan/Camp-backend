package com.jbent.peoplecentral.model.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.jdbc.core.RowMapper;

import com.jbent.peoplecentral.exception.SqlParseException;
import com.jbent.peoplecentral.model.pojo.AttributeFileStorage;
import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;
import com.jbent.peoplecentral.model.pojo.Entity;
import com.jbent.peoplecentral.model.pojo.EntityType;
import com.jbent.peoplecentral.model.pojo.FieldTypeOption;
import com.jbent.peoplecentral.model.pojo.Regex;
import com.jbent.peoplecentral.util.sql.PGHelper;

public class EntityMapper extends ApplicationObjectSupport implements RowMapper<EntityMapper> {
	
	private List<Entity> entities = new ArrayList<Entity>();
	private Map<Long, Integer> entititesToListOrder = new HashMap<Long, Integer>();
	private List <AttributeValueStorage> listAvs;
	private List <AttributeFileStorage> listAfs;
	int count = 0;
	@Override
	public EntityMapper mapRow(ResultSet rs, int rownum) throws SQLException  {		 
		Entity et ;
		EntityType ent = new EntityType();		
		if(entititesToListOrder.get(rs.getLong("entity_id")) != null){
			et = entities.get(entititesToListOrder.get(rs.getLong("entity_id")));
		}else{
			et = new Entity();
			listAvs = new ArrayList<AttributeValueStorage>();
			listAfs = new ArrayList<AttributeFileStorage>();
		}		
		et.setEntityId(rs.getLong("entity_id"));
		et.setEntityValid(rs.getBoolean("entity_valid"));
		et.setEntityTypeId(rs.getLong("entity_type_id"));
		et.setEntityValid(rs.getBoolean("entity_valid"));
		ent.setId(rs.getLong("entity_type_id"));
	    ent.setName(rs.getString("entity_type_name"));
	    ent.setTemplate(rs.getString("template"));
	    ent.setEditTemplate(rs.getString("edit_template"));
	    ent.setLock(rs.getBoolean("lock_mode"));
	    ent.setEtDeletable(rs.getBoolean("entitytype_deletable"));
	    ent.setTreeable(rs.getBoolean("entitytype_treeable"));
	    et.setMod_user(rs.getString("mod_user"));
	    et.setEntityType(ent);	   
	    if(rs.getMetaData().getColumnCount()>25){
	    	et.setSearchCount(rs.getLong("result_count"));
	    	et.setBoxEntityCount(rs.getLong("result_count"));
	    }
	    if(rs.getMetaData().getColumnCount()>26){
	    	et.setSearchCount(rs.getLong("box_id"));	    	
	    }
	    
	    if(rs.getArray("value_storage") != null && rs.getArray("value_storage").getArray() != null){			
	    	SortedMap<Integer, AttributeValueStorage> att_val_sto = new TreeMap<Integer, AttributeValueStorage>();	   	   
	   	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	   	    DateFormat formatter = new SimpleDateFormat("HH:mm a");
	   	    AttributeValueStorage attVals ; 
	   	    String[] att_value = (String[])rs.getArray("value_storage").getArray();
	   	    String[] regexArray = (String[])rs.getArray("regex_pattern").getArray();
	   	    String[] optionValueArray = (String[])rs.getArray("fieldtype_option_value").getArray();
	   	    for (String at_val : att_value) {
	   	    	attVals = new AttributeValueStorage();
				List<String> attCols;
				try {
					attCols = PGHelper.postgresROW2StringList(at_val);
				} catch (SqlParseException e) {
					logger.error("Unable to Map AttributeValues", e);
					throw new SQLException("Unable to Map AttributeValues",e);
				}				
				attVals.setAttributeValueId(new Long(attCols.get(0)));	
				if(rs.getLong("attribute_id") > 0){						
					attVals.setName(rs.getString("attribute_name"));
					attVals.setId(rs.getLong("attribute_id"));
					attVals.setDataTypeId(rs.getLong("attribute_datatype_id"));
					attVals.setDataTypeName(rs.getString("attribute_datatype_name"));
					attVals.setFieldTypeId(rs.getLong("attribute_fieldtype_id"));
					attVals.setFieldTypeName(rs.getString("attribute_fieldtype_name"));
					attVals.setIndexed(rs.getBoolean("indexed"));
					attVals.setOrder(rs.getInt("order"));
					attVals.setEntityTypeId(rs.getLong("entity_type_id"));
					attVals.setRequired(rs.getBoolean("required"));
					attVals.setMod_user(rs.getString("mod_user"));
					attVals.setRegex(regexValue(regexArray));
					attVals.setDisplay(rs.getBoolean("display"));
					attVals.setADeletable(rs.getBoolean("attribute_deletable"));
					attVals.setOptions(fieldTypeOptionValue(optionValueArray));
				}
								
				attVals.setEntityId(new Long(attCols.get(2)));
				if(!attCols.get(3).isEmpty()){
					attVals.setValueVarchar(new String(attCols.get(3)));
				}	
				if(!attCols.get(4).isEmpty()){
					attVals.setValueLong(new Long(attCols.get(4))) ;
					attVals.setValue_Long(String.valueOf(new Long(attCols.get(4))));
				}				
				try {
					if(!attCols.get(5).isEmpty()){
						attVals.setValueDate(df.parse(attCols.get(5)));
						attVals.setValue_Date(attCols.get(5));
					}
				} catch (ParseException e) {
					logger.error("Unable to parse", e);
				}
				if(!attCols.get(6).isEmpty()){
					attVals.setValueTime(java.sql.Time.valueOf(attCols.get(6)));
					attVals.setValue_Time(attCols.get(6));
				}
				if(!attCols.get(7).isEmpty()){
					attVals.setValueText(new String(attCols.get(7)));
				}
				if(!attCols.get(8).isEmpty()){	
					attVals.setMod_user(new String(attCols.get(8)));
				}
				if(!attCols.get(9).isEmpty()){
					attVals.setSearchIndex(new String(attCols.get(9)));	   
				}
				if(!attCols.get(10).isEmpty()){
					attVals.setAttributeValueFileId(new Long(attCols.get(10)));	   
				}	
				listAvs.add(attVals);
				attVals.setAttibuteValueStorageMap(att_val_sto);
				if(listAvs.size()>0){
					et.setAttributeValueStorage(listAvs);
				}
			}	
		}
	    if(rs.getArray("file_storage") != null && rs.getArray("file_storage").getArray() != null){			
	    	//SortedMap<Integer, AttributeFileStorage> att_file_sto = new TreeMap<Integer, AttributeFileStorage>();	   	   
	    	AttributeFileStorage attFiles ; 
	   	    String[] att_files = (String[])rs.getArray("file_storage").getArray();
	   	    String[] optionValueArray = (String[])rs.getArray("fieldtype_option_value").getArray();
	   	    for (String at_file : att_files) {
	   	    	attFiles = new AttributeFileStorage();
				List<String> attCols;
				try {
					attCols = PGHelper.postgresROW2StringList(at_file);
				} catch (SqlParseException e) {
					logger.error("Unable to Map AttributeFiles", e);
					throw new SQLException("Unable to Map AttributeFiles",e);
				}				
				attFiles.setAttributeFileId(new Long(attCols.get(0)));	
				if(rs.getLong("attribute_id") > 0){						
					attFiles.setName(rs.getString("attribute_name"));
					attFiles.setId(rs.getLong("attribute_id"));
					attFiles.setDataTypeId(rs.getLong("attribute_datatype_id"));
					attFiles.setDataTypeName(rs.getString("attribute_datatype_name"));
					attFiles.setFieldTypeId(rs.getLong("attribute_fieldtype_id"));
					attFiles.setFieldTypeName(rs.getString("attribute_fieldtype_name"));
					attFiles.setIndexed(rs.getBoolean("indexed"));
					attFiles.setOrder(rs.getInt("order"));
					attFiles.setEntityTypeId(rs.getLong("entity_type_id"));
					attFiles.setRequired(rs.getBoolean("required"));
					attFiles.setMod_user(rs.getString("mod_user"));	
					attFiles.setDisplay(rs.getBoolean("display"));
					attFiles.setOptions(fieldTypeOptionValue(optionValueArray));				
					
				}
								
				attFiles.setEntityId(new Long(attCols.get(2)));
				if(!attCols.get(3).isEmpty()){
					attFiles.setFileName(new String(attCols.get(3)));
				}	
				if(!attCols.get(4).isEmpty()){
					attFiles.setDescription(new String(attCols.get(4))) ;
				}			
				
				if(!attCols.get(5).isEmpty()){
					attFiles.setSort(new Long(attCols.get(5)));
				}
				if(!attCols.get(6).isEmpty()){	
					attFiles.setMod_user(new String(attCols.get(6)));
				}
				listAfs.add(attFiles);				
				if(listAfs.size()>0){
					et.setAttributeFileStorage(listAfs);
				}
			}	
		}
	    if(entititesToListOrder.get(et.getEntityId()) == null){
	    	entities.add(et);
			entititesToListOrder.put(et.getEntityId(), count);
			count++;
		}
		return this;
	}
	
	private Regex regexValue(String[] regexArray){
		Regex regex = null;		
		for (String reg : regexArray) {
			regex = new Regex();
			List<String> regexCols;				
			try {
				regexCols = PGHelper.postgresROW2StringList(reg);
				regex.setRegexId(new Long(regexCols.get(0)));
				regex.setPattern(new String(regexCols.get(1)));
				regex.setDisplayName(new String(regexCols.get(2)));
				regex.setCustom(Boolean.parseBoolean(regexCols.get(3)));
			}catch (SqlParseException e) {
				logger.error("Unable to Map Regex", e);
			}
		}
		return regex;
	}
	

	private List<FieldTypeOption> fieldTypeOptionValue(String[] optionValueArray){
		List<FieldTypeOption> fieldTypeOptions = new ArrayList<FieldTypeOption>();
		FieldTypeOption fieldTypeOption = null;		
		for (String options : optionValueArray) {
			fieldTypeOption = new FieldTypeOption();
			List<String> optionsCols = null;
			try {
				optionsCols = PGHelper.postgresROW2StringList(options);
			} catch (SqlParseException e) {
				logger.error("Unable to Map fieldTypeOptionValue", e);
				
			}
			fieldTypeOption.setFieldtypeOptionId(new Long(optionsCols.get(0)));
			fieldTypeOption.setFieldTypeId(new Long(optionsCols.get(1)));
			fieldTypeOption.setOption(new String(optionsCols.get(2)));
			fieldTypeOption.setOptionValueId(new Long(optionsCols.get(3)));
			fieldTypeOption.setAttributeId(new Long(optionsCols.get(4)));
			fieldTypeOption.setOptionValue(new String(optionsCols.get(5)));
			fieldTypeOptions.add(fieldTypeOption);
		}
		return fieldTypeOptions;
	}
	
	

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}
	
	

	/**
	 * @return the entities
	 */
	public List<Entity> getEntities() {
		return entities;
	}

	
}
