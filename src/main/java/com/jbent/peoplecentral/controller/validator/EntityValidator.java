/**
 * 
 */
package com.jbent.peoplecentral.controller.validator;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ListIterator;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;
import com.jbent.peoplecentral.model.pojo.Entity;
import com.jbent.peoplecentral.model.pojo.Attribute.DataType;
import com.jbent.peoplecentral.model.pojo.Attribute.FieldType;

/**
 * @author jasontesser
 *
 */
public class EntityValidator extends ApplicationObjectSupport implements Validator {
   List <AttributeValueStorage> avsList;  
   @Override
	public boolean supports(Class<?> clazz) {
		return Entity.class.isAssignableFrom(clazz);
	}
	@Override
	public void validate(Object obj, Errors e) {
		Entity et = null;
		try{
			et = (Entity)obj;
		}catch (Throwable t) {
			logger.debug("EntityValidator.validate : Object is not of Entity", t);
			e.reject("error.entitytype.save.object");
			return;
		}
		avsList= et.getAttributeValueStorage();
		if(avsList != null){
			ListIterator<AttributeValueStorage> it = avsList.listIterator();
			if(it.hasNext()){
				for(AttributeValueStorage avs:avsList){           
					if(avs.getDataTypeName().equals(DataType.VARCHAR.toString())){
						if(avs.getFieldTypeName().equals(FieldType.FILE.toString()) || avs.getFieldTypeName().equals(FieldType.IMAGE.toString())){
							// 	not validating for the field types image and file
							return ;
						}	
						else if(avs.isRequired()){
							ValidationUtils.rejectIfEmptyOrWhitespace(e, "attributeValueStorage["+avsList.indexOf(avs)+"].valueVarchar", "error.attributeValue.value.empty");
						}                    
						if(avs.getRegex().getRegexId()> 0 && !avs.getValueVarchar().isEmpty()){
							boolean matches=avs.getValueVarchar().matches(avs.getRegex().getPattern().trim());
							if(!matches){
								e.rejectValue("attributeValueStorage["+avsList.indexOf(avs)+"].valueVarchar", "error.entitytype.name.invalid");
							}                   
						} 
					}
					if(avs.getDataTypeName().equals(DataType.LONG.toString())){
						if(avs.isRequired()){
							ValidationUtils.rejectIfEmptyOrWhitespace(e, "attributeValueStorage["+avsList.indexOf(avs)+"].valueLong", "error.attributeValue.value.empty");
						}                    
						if(avs.getRegex().getRegexId()> 0 && avs.getValueLong()!= null){
							boolean matches=String.valueOf(avs.getValueLong()).matches(avs.getRegex().getPattern().trim());             	
							if(!matches){
								e.rejectValue("attributeValueStorage["+avsList.indexOf(avs)+"].valueLong", "error.entity.number.invalid");
							}
						}                  
					}
					if(avs.getDataTypeName().equals(DataType.DATE.toString())){
						String dateString ="";
						if(avs.isRequired()){
							ValidationUtils.rejectIfEmptyOrWhitespace(e, "attributeValueStorage["+avsList.indexOf(avs)+"].valueDate", "error.attributeValue.value.empty");
						}         
						if(avs.getRegex().getRegexId()> 0 && avs.getValueDate() != null){
							SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
							dateString = dateformat.format(avs.getValueDate());
							boolean matches=dateString.matches(avs.getRegex().getPattern().trim());
							if(!matches){
								e.rejectValue("attributeValueStorage["+avsList.indexOf(avs)+"].valueDate", "error.attributeValue.date.invalid");
							}
						}else if(dateString == ""){
							e.rejectValue("attributeValueStorage["+avsList.indexOf(avs)+"].valueDate", "error.attributeValue.date.invalid");
						}                 
					}
					if(avs.getDataTypeName().equals(DataType.TEXT.toString())){
						if(avs.isRequired()){
							ValidationUtils.rejectIfEmptyOrWhitespace(e, "attributeValueStorage["+avsList.indexOf(avs)+"].valueText", "error.attributeValue.value.empty");
						}                    
						if(avs.getRegex().getRegexId()> 0 && !avs.getValueText().isEmpty()){
							boolean matches=avs.getValueText().matches(avs.getRegex().getPattern().trim());
							if(!matches){
								e.rejectValue("attributeValueStorage["+avsList.indexOf(avs)+"].valueText", "error.entitytype.name.invalid");
							}
						}                  
					}
				}
			}
		}else{
			logger.error("EntityValidator.validate: avsList is NULL");
		}
	}		
}
	



