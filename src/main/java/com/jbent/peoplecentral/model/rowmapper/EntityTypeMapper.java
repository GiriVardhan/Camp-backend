/**
 * 
 */
package com.jbent.peoplecentral.model.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.jdbc.core.RowMapper;

import com.jbent.peoplecentral.exception.SqlParseException;
import com.jbent.peoplecentral.model.pojo.Attribute;
import com.jbent.peoplecentral.model.pojo.AttributeChoice;
import com.jbent.peoplecentral.model.pojo.EntityType;
import com.jbent.peoplecentral.model.pojo.FieldTypeOption;
import com.jbent.peoplecentral.model.pojo.Regex;
import com.jbent.peoplecentral.model.pojo.RelatedEntityType;
import com.jbent.peoplecentral.util.sql.PGHelper;

/**
 * @author Jason Tesser
 *
 */
public class EntityTypeMapper extends ApplicationObjectSupport implements RowMapper<EntityTypeMapper> {

	private List<EntityType> entities = new ArrayList<EntityType>();
	private Map<Long, Integer> entititesToListOrder = new HashMap<Long, Integer>();
	int count = 0;	
	@Override
	public EntityTypeMapper mapRow(ResultSet rs, int rownum) throws SQLException {
		EntityType et;
		RelatedEntityType relEnt = new RelatedEntityType();
		if(entititesToListOrder.get(rs.getLong("entity_type_id")) != null){
			et = entities.get(entititesToListOrder.get(rs.getLong("entity_type_id")));
		}else{
			et = new EntityType();
		}
		et.setId(rs.getLong("entity_type_id"));
		et.setName(rs.getString("entity_type_name"));
		et.setTemplate(rs.getString("template"));
		et.setEditTemplate(rs.getString("edit_template"));
		et.setLock(rs.getBoolean("lock_mode"));
		et.setEtDeletable(rs.getBoolean("entitytype_deletable"));
		et.setTreeable(rs.getBoolean("entitytype_treeable"));
		List<Attribute> entity_att;
		if(et.getAttributes() == null){
			entity_att = new ArrayList<Attribute>();
		}else{
			entity_att = et.getAttributes();
		}
		String[] regexArray = (String[])rs.getArray("regex_pattern").getArray();
		String[] optionValueArray = (String[])rs.getArray("fieldtype_option_value").getArray();
		if(rs.getLong("attribute_id") > 0){
			Attribute att = new Attribute();
			att.setId(rs.getLong("attribute_id"));
			att.setEntityTypeId(rs.getLong("entity_type_id"));
			att.setDataTypeId(rs.getLong("attribute_datatype_id"));
			att.setDataTypeName(rs.getString("attribute_datatype_name"));
			att.setFieldTypeId(rs.getLong("attribute_fieldtype_id"));
			att.setFieldTypeName(rs.getString("attribute_fieldtype_name"));
			att.setIndexed(rs.getBoolean("indexed"));
			att.setName(rs.getString("attribute_name"));
			att.setOrder(rs.getInt("order"));
			att.setRequired(rs.getBoolean("required"));
			att.setMod_user(rs.getString("mod_user"));
			att.setRegex(regexValue(regexArray));
			att.setDisplay(rs.getBoolean("display"));
			att.setADeletable(rs.getBoolean("attribute_deletable"));
			att.setOptions(fieldTypeOptionValue(optionValueArray));
			if(rs.getArray("related_entity") != null && rs.getArray("related_entity").getArray() != null){
				SortedMap<Integer, RelatedEntityType> rel_entity_type = new TreeMap<Integer, RelatedEntityType>();
				String[] relate_ent = (String[])rs.getArray("related_entity").getArray();
				for (String rel_ent : relate_ent) {
					List<String> attCols;
					try {
						attCols = PGHelper.postgresROW2StringList(rel_ent);
					} catch (SqlParseException e) {
						logger.error("Unable to Map Related entity", e);
						throw new SQLException("Unable to Map Related entity",e);
					}
					relEnt.setId(new Long(attCols.get(0)));
					relEnt.setAttributeId(new Long(attCols.get(1)));
					relEnt.setChildEntityTypeId(new Long(attCols.get(2)));
					relEnt.setCollapse(new Boolean(attCols.get(3)));
				}			
				relEnt.setRelatedEntityTypeMap(rel_entity_type);
				att.setRelatedEntityType(relEnt);
			}
			if(rs.getArray("choice_attribute") != null && rs.getArray("choice_attribute").getArray() != null){
				 SortedMap<Integer, AttributeChoice> attribute_choice = new TreeMap<Integer, AttributeChoice>();
				 AttributeChoice atch = new AttributeChoice();
				 String[] choice_det = (String[])rs.getArray("choice_attribute").getArray();
				 for (String cho_det : choice_det) {
					 List<String> attCols;
					 try {
						attCols = PGHelper.postgresROW2StringList(cho_det);
					 } catch (SqlParseException e) {
						logger.error("Unable to Map choices", e);
						throw new SQLException("Unable to Map choice details",e);
					 }
					 atch.setChoiceAttributeId(new Long(attCols.get(0)));
					 atch.setAttributeId(new Long(attCols.get(1)));
					 atch.setDisplayType(new String(attCols.get(2)));
					 atch.setChoiceEntityTypeAttributeId(new Long(attCols.get(3)));
				 }			
				 atch.setAttributeChoiceMap(attribute_choice);
				 att.setAttributeChoice(atch);
			}
			entity_att.add(att);
		}
		if(entity_att.size()>0){
			et.setAttributes(entity_att);
		}
		if(entititesToListOrder.get(et.getId()) == null){
			entities.add(et);
			entititesToListOrder.put(et.getId(), count);
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
			 } catch (SqlParseException e) {
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
				logger.error("Unable to Map Regex", e);
				
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


	/**
	 * @param entities the entities to set
	 */
	public void setEntities(List<EntityType> entities) {
		this.entities = entities;
	}

	/**
	 * @return the entities
	 */
	public List<EntityType> getEntities() {
		return entities;
	}

}
