/**
 * 
 */
package com.jbent.peoplecentral.controller.validator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;
import com.jbent.peoplecentral.model.pojo.Attribute.DataType;



/**
 * @author jasontesser
 *
 */
public class AttributeValueStorageValidator extends ApplicationObjectSupport implements Validator {

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	private Pattern alphaNumericPattern;	
	private Pattern pattern;
	private Matcher matcher;

	private static final String DATE_PATTERN = 
        "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";

	public AttributeValueStorageValidator() {
		alphaNumericPattern = Pattern.compile("^[a-zA-Z0-9 ]*$");		
		pattern = Pattern.compile(DATE_PATTERN);
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return AttributeValueStorage.class.equals(clazz); 
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object obj, Errors e) {
		AttributeValueStorage avs = null;
		try{
			avs = (AttributeValueStorage)obj;
		}catch (Throwable t) {
			logger.debug("AttributeValueStorageValidator.validate : Object is not of AttributeValueStorage Type", t);
			e.reject("error.entitytype.save.object");
			return;
		}
		//NEEDS TO CHANGE JUST HERE OR TESTING
		if(avs.getDataTypeName().equals(DataType.VARCHAR.toString())){
			ValidationUtils.rejectIfEmptyOrWhitespace(e, "valueVarchar","error.attributeValue.value.empty");
			if(obj != null && !e.hasErrors() && !alphaNumericPattern.matcher(avs.getValueVarchar()).matches()){
				e.rejectValue("valueVarchar", "error.entitytype.name.invalid");
			}

		}
		if(avs.getDataTypeName().equals(DataType.LONG.toString())){
			ValidationUtils.rejectIfEmptyOrWhitespace(e, "valueLong","error.attributeValue.value.empty");
		}
		if(avs.getDataTypeName().equals(DataType.DATE.toString())){			
			if(avs.getValueDate() != null && !e.hasErrors()){
				if( validateDate(avs.getValueDate()))
					e.rejectValue("valueDate", "error.attributeValue.date.invalid");
			}
			else{
				e.rejectValue("valueDate", "error.attributeValue.date.invalid");
				ValidationUtils.rejectIfEmptyOrWhitespace(e, "valueDate","error.attributeValue.value.empty");
			}
		}
		if(avs.getDataTypeName().equals(DataType.TEXT.toString())){
			ValidationUtils.rejectIfEmptyOrWhitespace(e, "valueText","error.attributeValue.value.empty");
			if(obj != null && !e.hasErrors() && !alphaNumericPattern.matcher(avs.getValueText()).matches()){
				e.rejectValue("valueText", "error.entitytype.name.invalid");
			}

		}
	
	}
	
	public boolean validateDate(Date date){
		 
	    SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
	    String dt = sdf.format(date);
		matcher = pattern.matcher(dt);	 
	     
	     if(matcher.matches()){	 
	    	 matcher.reset();
	     	 	if(matcher.find()){
	 	             String day = matcher.group(1);
	 	             String month = matcher.group(2);
	 	             int year = Integer.parseInt(matcher.group(3));
	 
	 	             if (day.equals("31") && 
	 	            		 (month.equals("4") || month .equals("6") || month.equals("9") ||
	 	            				 month.equals("11") || month.equals("04") || month .equals("06") ||
	 	            				 month.equals("09"))) {
	 	            	 return false; // only 1,3,5,7,8,10,12 has 31 days
	 	             }
	 	             else if (month.equals("2") || month.equals("02")) {
	 	            	 //leap year
	 	            	 if(year % 4==0){
	 	            		 if(day.equals("30") || day.equals("31")){
	 	            			 return false;
	 	            		 }else{	 	            			 
	 	            			 return true;
	 	            		 }
	 	            	 }else{
	 	            		 if(day.equals("29")||day.equals("30")||day.equals("31")){
	 	            			 return false;
	 	            		 }else{
	 	            			 return true;
	 	            		 }
	 	            	 }
	 	             }else{				 
	 	            	 return true;				 
	 	             }
	     	 	}else{
	    	      return false;
	     	 	}		  
	     	}else{
	     		return false;
	     	}			    
	   }
}


