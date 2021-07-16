/**
 * 
 */
package com.jbent.peoplecentral.controller.validator;

import java.util.regex.Pattern;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jbent.peoplecentral.model.pojo.EntityType;

/**
 * @author jasontesser
 *
 */
public class EntityTypeValidator extends ApplicationObjectSupport implements Validator {

	private Pattern alphaNumericPattern;
	
	public EntityTypeValidator() {
		alphaNumericPattern = Pattern.compile("^[a-zA-Z0-9 ]*$");
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return EntityType.class.equals(clazz); 
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object obj, Errors e) {
		EntityType et = null;
		try{
			et = (EntityType)obj;
		}catch (Throwable t) {
			logger.debug("EntityTypeValidator.validate : Object is not of EntityType", t);
			e.reject("error.entitytype.save.object");
			return;
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "name", "error.entitytype.name.empty");
		if(obj != null && !e.hasErrors() &&!alphaNumericPattern.matcher(et.getName()).matches()){
			e.rejectValue("name", "error.entitytype.name.invalid");
		}		
	}

}
