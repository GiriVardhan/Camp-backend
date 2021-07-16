/**
 * 
 */
package com.jbent.peoplecentral.controller.validator;

import java.util.regex.Pattern;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jbent.peoplecentral.model.pojo.Attribute;



/**
 * @author jasontesser
 *
 */
public class AttributeValidator extends ApplicationObjectSupport implements Validator {

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	private Pattern alphaNumericPattern;
	public AttributeValidator() {
		alphaNumericPattern = Pattern.compile("^[a-zA-Z0-9 ]*$");
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Attribute.class.equals(clazz); 
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object obj, Errors e) {
		Attribute att = null;
		try{
			att = (Attribute)obj;
		}catch (Throwable t) {
			logger.debug("AttributeValidator.validate : Object is not of Attribute Type", t);
			e.reject("error.entitytype.save.object");
			return;
		}
		//NEEDS TO CHANGE JUST HERE OR TESTING
		if(att.getDataTypeId() == 0){
			att.setDataTypeId(1);
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "name","error.attribute.name.empty");
		if(obj != null && !e.hasErrors() && !alphaNumericPattern.matcher(att.getName()).matches()){
			e.rejectValue("name", "error.entitytype.name.invalid");
		}		
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "fieldTypeId","error.attribute.fieldtype.empty");
	
	}

}
