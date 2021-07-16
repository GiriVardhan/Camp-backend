/**
 * 
 */
package com.jbent.peoplecentral.model.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.jdbc.core.RowMapper;

import com.jbent.peoplecentral.exception.SqlParseException;
import com.jbent.peoplecentral.model.pojo.Attribute;
import com.jbent.peoplecentral.model.pojo.FieldTypeOption;
import com.jbent.peoplecentral.model.pojo.Regex;
import com.jbent.peoplecentral.model.pojo.RelatedEntityType;
import com.jbent.peoplecentral.permission.Permissionable;
import com.jbent.peoplecentral.util.sql.PGHelper;

/**
 * @author PrasadBHVN
 *
 */
public class AttributeMapper extends ApplicationObjectSupport implements Permissionable, RowMapper<Attribute> {
	
	private static final long serialVersionUID = 2415289097960421961L;
	@Override
	public Attribute mapRow(ResultSet rs, int rownum) throws SQLException {
		Regex regex = null;	
		FieldTypeOption fieldTypeOption =null;
		RelatedEntityType relEntType = null;
		List<FieldTypeOption> fieldTypeOptions = new ArrayList<FieldTypeOption>();
		Attribute att = new Attribute();
		if(rs.getLong("attribute_id") > 0){	
			String[] regexArray = (String[])rs.getArray("regex_pattern").getArray();
			String[] optionValuesArray = (String[])rs.getArray("fieldtype_option_value").getArray();
			String[] relatedEntityTypeArray = (String[])rs.getArray("related_entity").getArray();
			for (String reg : regexArray) {
				regex = new Regex();
				List<String> regexCols;
				try {
					regexCols = PGHelper.postgresROW2StringList(reg);
				} catch (SqlParseException e) {
					logger.error("Unable to Map Regex", e);
					throw new SQLException("Unable to Map Regex",e);
				}
				regex.setRegexId(new Long(regexCols.get(0)));
				regex.setPattern(new String(regexCols.get(1)));
				regex.setDisplayName(new String(regexCols.get(2)));
				regex.setCustom(Boolean.parseBoolean(regexCols.get(3)));
					
			}
			for (String options : optionValuesArray) {
				fieldTypeOption = new FieldTypeOption();
				List<String> optionsCols;
				try {
					optionsCols = PGHelper.postgresROW2StringList(options);
				} catch (SqlParseException e) {
					logger.error("Unable to Map fieldTypeOptions", e);
					throw new SQLException("Unable to Map fieldTypeOptions",e);
				}
				fieldTypeOption.setFieldtypeOptionId(new Long(optionsCols.get(0)));
				fieldTypeOption.setFieldTypeId(new Long(optionsCols.get(1)));
				fieldTypeOption.setOption(new String(optionsCols.get(2)));
				fieldTypeOption.setOptionValueId(new Long(optionsCols.get(3)));
				fieldTypeOption.setAttributeId(new Long(optionsCols.get(4)));
				fieldTypeOption.setOptionValue(new String(optionsCols.get(5)));
				fieldTypeOptions.add(fieldTypeOption);
			}
			for (String relEnt : relatedEntityTypeArray) {
				relEntType =  new RelatedEntityType();
				List<String> relEntCols;
				try {
					relEntCols = PGHelper.postgresROW2StringList(relEnt);
				} catch (SqlParseException e) {
					logger.error("Unable to Map relatedEntityType", e);
					throw new SQLException("Unable to Map relatedEntityType",e);
				}
				relEntType.setId(new Long(relEntCols.get(0)));
				relEntType.setAttributeId(new Long(relEntCols.get(1)));
				relEntType.setChildEntityTypeId(new Long(relEntCols.get(2)));
				relEntType.setCollapse(Boolean.parseBoolean(relEntCols.get(3)));
			}
			att.setId(rs.getLong("attribute_id"));
			att.setDataTypeId(rs.getLong("attribute_datatype_id"));
			att.setDataTypeName(rs.getString("attribute_datatype_name"));
			att.setFieldTypeId(rs.getLong("attribute_fieldtype_id"));
			att.setFieldTypeName(rs.getString("attribute_fieldtype_name"));
			att.setIndexed(rs.getBoolean("indexed"));
			att.setName(rs.getString("attribute_name"));
			att.setOrder(rs.getInt("order"));
			att.setRequired(rs.getBoolean("required"));
			att.setEntityTypeId(rs.getLong("entity_type_id"));
			att.setOptions(fieldTypeOptions);
			att.setRegex(regex);
			att.setRelatedEntityType(relEntType);
			att.setDisplay(rs.getBoolean("display"));
			att.setADeletable(rs.getBoolean("attribute_deletable"));
		}
		return att;
	}

	@Override
	public long getPermissionId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getPermissionType() {
		// TODO Auto-generated method stub
		return null;
	}

}
