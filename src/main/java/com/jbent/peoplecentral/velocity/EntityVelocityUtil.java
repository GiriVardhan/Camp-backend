package com.jbent.peoplecentral.velocity;

import java.util.Map;

import com.jbent.peoplecentral.model.pojo.AttributeFileStorage;
import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;
import com.jbent.peoplecentral.model.pojo.Entity;

public class EntityVelocityUtil {

	/**
	 * Will put in the passed in map the entity.  Will take all the attributes and add directly to 
	 * the map with the key being the attribute name. Will also add a value for the key attributes which 
	 * is the list of AttributeValueStorage objects.
	 * @param entity
	 * @param context
	 */
	public static void populateEntity(Entity entity, Map<String, Object> context){
		if(entity !=null && entity.getAttributeFileStorage() != null){
			context.put("attributes", entity.getAttributeValueStorage());
			for (AttributeValueStorage av : entity.getAttributeValueStorage()) {
				context.put(av.getName(), av.getValue());
				context.put(av.getAttributeVelocityName(), av.getValue());
			}
			if(entity.getAttributeFileStorage().size() > 0){
				for (AttributeFileStorage av : entity.getAttributeFileStorage()) {
					context.put(av.getName()+"_imagePath", av.getImagePath());
					context.put(av.getName()+"_fileName", av.getFileName());
				}
			}
		}
		
	}
	
}
