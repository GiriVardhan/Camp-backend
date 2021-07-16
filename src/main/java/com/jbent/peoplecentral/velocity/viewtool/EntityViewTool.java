package com.jbent.peoplecentral.velocity.viewtool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.manager.EntityManager;
import com.jbent.peoplecentral.model.manager.EntityTypeManager;
import com.jbent.peoplecentral.model.pojo.Entity;

public class EntityViewTool {
	
	public EntityViewTool(){
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	private EntityTypeManager entityTypeManager;
	private EntityManager entityManager;

	/**
	 * @param entityTypeManager the entityTypeManager to set
	 */
	@Autowired
	private void setEntityTypeManager(EntityTypeManager entityTypeManager) {
		this.entityTypeManager = entityTypeManager;
	}
	
	/**
	 * @param entityManager the entityManager to set
	 */
	@Autowired
	private void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Entity getEntity(long entityId) throws DataException{
		return entityManager.loadEntity(entityId);
	}
	
	public List<Entity> searchEntitiesByType(String entityTypeName){
		List<Entity> result = new ArrayList<Entity>();
		try {
			result = entityManager.entitySearch("type:["+ entityTypeName +"]", 0, "<b>", "</b>", 10000, 0);
		} catch (DataException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<Entity> searchEntities(String query){
		List<Entity> searchResult = new ArrayList<Entity>();
		List<Entity> relationEntities = new ArrayList<Entity>();
		try {
			searchResult = entityManager.entitySearch(query, 0, "<b>", "</b>", 10000, 0);
			if(searchResult != null){
				for(Entity e : searchResult){
					relationEntities.add(getEntity(e.getEntityId()));
				}
			}
		} catch (DataException e) {
			e.printStackTrace();
		}
		return relationEntities;
	}

	public List<Entity> pullRelations(long entityId){
		List<Entity> existingRelations = new ArrayList<Entity>();
		try {
			existingRelations = entityManager.entitySearch("type:Relation From:["+ entityId +"] or To:["+ entityId +"]", 0, "<b>", "</b>", 1000, 0);
		} catch (DataException e) {
			e.printStackTrace();
		}		
		return existingRelations;
	}
	
	public List<Entity> pullRelations(long entityId, String relName){
		List<Entity> existingRelations = new ArrayList<Entity>();
		List<Entity> result = new ArrayList<Entity>();
		String fromQry = "type:Relation From:["+ entityId +"] and Name:['"+ relName +"']";
		String toQry = "type:Relation To:["+ entityId +"] and Name:['"+ relName +"']";
		try {
			existingRelations = entityManager.entitySearch(toQry, 0, "<b>", "</b>", 1000000, 0);
		} catch (DataException e) {}
		try {
			existingRelations.addAll(entityManager.entitySearch(fromQry, 0, "<b>", "</b>", 1000000, 0));
		} catch (DataException e) {}		
		for(Entity e : existingRelations){
			try {
				result.add(entityManager.loadEntity(e.getEntityId()));
			} catch (DataException e1) {}
		}
		return result;
	}
	
	public List<Entity> pullRelations(long entityFromId, long entityToId){
		List<Entity> existingRelations = new ArrayList<Entity>();
		try {
			existingRelations = entityManager.entitySearch("type:Relation From:["+ entityFromId +"] and To:["+ entityToId +"]", 0, "<b>", "</b>", 1000, 0);
		} catch (DataException e) {
			e.printStackTrace();
		}		
		return existingRelations;
	}
	
	public List<Entity> pullRelations(long entityFromId, String relName, long entityToId){
		List<Entity> existingRelations = new ArrayList<Entity>();
		try {
			existingRelations = entityManager.entitySearch("type:Relation From:["+ entityFromId +"] and Name:["+ relName +"] and To:["+ entityToId +"]", 0, "<b>", "</b>", 1000, 0);
		} catch (DataException e) {
			e.printStackTrace();
		}		
		return existingRelations;
	}
	
	public List<Entity> pullRelatedEntities(long entityId){
		List<Entity> existingRelations = new ArrayList<Entity>();
		List<Entity> result = new ArrayList<Entity>();
		long eId = 0;
		try {
			existingRelations = entityManager.entitySearch("type:Relation From:["+ entityId +"] or To:["+ entityId +"]", 0, "<b>", "</b>", 1000, 0);
			if(existingRelations == null)
				return result;
			for(Entity e : existingRelations){
				Long from = (Long) e.getValueObject("From");
				Long to = (Long) e.getValueObject("To");
				if(from !=null && from.equals(entityId)){
					eId = to;
				}else if(to !=null && to.equals(entityId)){
					eId = from;
				}
				if(eId > 0){
					result.add(getEntity(eId));
				}
			}
		} catch (DataException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<Entity> pullRelatedEntities(long entityId, String relName){
		List<Entity> searchResult = new ArrayList<Entity>();
		List<Entity> relationEntities = new ArrayList<Entity>();
		List<Entity> result = new ArrayList<Entity>();
		String fromQry = "type:Relation From:["+ entityId +"] and Name:['"+ relName +"']";
		String toQry = "type:Relation To:["+ entityId +"] and Name:['"+ relName +"']";
		try {
			searchResult = entityManager.entitySearch(toQry, 0, "<b>", "</b>", 1000000, 0);
			if(searchResult != null){
				for(Entity e : searchResult){
					relationEntities.add(getEntity(e.getEntityId()));
				}
			}
		} catch (DataException e) {}
		try {
			searchResult = (entityManager.entitySearch(fromQry, 0, "<b>", "</b>", 1000000, 0));
			if(searchResult != null){
				for(Entity e : searchResult){
					relationEntities.add(getEntity(e.getEntityId()));
				}
			}
		} catch (DataException e) {}
		long eId = 0;
		for(Entity e : relationEntities){
			Long from = (Long) e.getValueObject("From");
			Long to = (Long) e.getValueObject("To");
			if(from !=null && from.equals(entityId)){
				eId = to;
			}else if(to !=null && to.equals(entityId)){
					eId = from;
			}
			if(eId > 0){
				try {
					result.add(getEntity(eId));
				} catch (DataException e1) {}
			}
		}
		return result;
	}

	public List<Entity> pullRelatedEntitiesByDirection(long entityId, String directionToOrFrom){
		List<Entity> searchResult = new ArrayList<Entity>();
		List<Entity> relationEntities = new ArrayList<Entity>();
		List<Entity> result = new ArrayList<Entity>();
		String fromQry = "type:Relation From:["+ entityId +"]";
		String toQry = "type:Relation To:["+ entityId +"]";
		try {
			if(directionToOrFrom.equalsIgnoreCase("From")){
				searchResult = entityManager.entitySearch(fromQry, 0, "<b>", "</b>", 1000000, 0);
				if(searchResult != null){
					for(Entity e : searchResult){
						relationEntities.add(getEntity(e.getEntityId()));
					}
				}
			}else if(directionToOrFrom.equalsIgnoreCase("To")){
				searchResult = entityManager.entitySearch(toQry, 0, "<b>", "</b>", 1000000, 0);
				if(searchResult != null){
					for(Entity e : searchResult){
						relationEntities.add(getEntity(e.getEntityId()));
					}
				}
			}
		} catch (DataException e) {}
		long eId = 0;
		for(Entity e : relationEntities){
			Long from = (Long) e.getValueObject("From");
			Long to = (Long) e.getValueObject("To");
			if(from !=null && from.equals(entityId)){
				eId = to;
			}else if(to !=null && to.equals(entityId)){
					eId = from;
			}
			if(eId > 0){
				try {
					result.add(getEntity(eId));
				} catch (DataException e1) {}
			}
		}
		return result;
	}
	
	public List<Entity> pullRelatedEntities(long entityId, String relName, String directionToOrFrom){
		List<Entity> searchResult = new ArrayList<Entity>();
		List<Entity> relationEntities = new ArrayList<Entity>();
		List<Entity> result = new ArrayList<Entity>();
		String fromQry = "type:Relation From:["+ entityId +"] and Name:['"+ relName +"']";
		String toQry = "type:Relation To:["+ entityId +"] and Name:['"+ relName +"']";
		try {
			if(directionToOrFrom.equalsIgnoreCase("From")){
				searchResult = entityManager.entitySearch(fromQry, 0, "<b>", "</b>", 1000000, 0);
				if(searchResult != null){
					for(Entity e : searchResult){
						relationEntities.add(getEntity(e.getEntityId()));
					}
				}
			}else if(directionToOrFrom.equalsIgnoreCase("To")){
				searchResult = entityManager.entitySearch(toQry, 0, "<b>", "</b>", 1000000, 0);
				if(searchResult != null){
					for(Entity e : searchResult){
						relationEntities.add(getEntity(e.getEntityId()));
					}
				}
			}
		} catch (DataException e) {}
		long eId = 0;
		for(Entity e : relationEntities){
			Long from = (Long) e.getValueObject("From");
			Long to = (Long) e.getValueObject("To");
			if(from !=null && from.equals(entityId)){
				eId = to;
			}else if(to !=null && to.equals(entityId)){
					eId = from;
			}
			if(eId > 0){
				try {
					result.add(getEntity(eId));
				} catch (DataException e1) {}
			}
		}
		return result;
	}

	public List<Entity> pullDateFilteredRelatedEntities(long entityId, String relName, String directionToOrFrom, String startDate, String endDate, String attributeName){
		List<Entity> searchResult = new ArrayList<Entity>();
		List<Entity> result = new ArrayList<Entity>();
		if(attributeName == null || attributeName == "")
			attributeName = "date";
		if(relName == null && directionToOrFrom == null)
			searchResult = pullRelatedEntities(entityId);
		else if(directionToOrFrom == null)
			searchResult = pullRelatedEntities(entityId,relName);
		else
			searchResult = pullRelatedEntities(entityId, relName, directionToOrFrom);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = null;
		Date eDate = null;
		try{
			sDate = sdf.parse(startDate);
		}catch(Exception e){}
		try{
			eDate = sdf.parse(endDate);
		}catch(Exception e){}
		SimpleDateFormat dataBaseSDF = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy"); 
		if(sDate != null && eDate != null){
			for(Entity e : searchResult){
				Date entityDate = null;
				try {
					entityDate = dataBaseSDF.parse(e.getValueString(attributeName));
				} catch (ParseException e1) {}
				if(entityDate != null && (entityDate.compareTo(sDate) >= 0 && entityDate.compareTo(eDate) <= 0)){
					result.add(e);
				}
			}
		}else if(sDate != null){
			for(Entity e : searchResult){
				Date entityDate = null;
				try {
					entityDate = dataBaseSDF.parse(e.getValueString(attributeName));
				} catch (ParseException e1) {}
				if(entityDate != null && entityDate.compareTo(sDate) == 0){
					result.add(e);
				}
			}
		}
		
		return result;
	}
	
	public List<Entity> pullDateFilteredRelatedEntities(long entityId, String startDate, String endDate){
		return pullDateFilteredRelatedEntities(entityId, null, null, startDate, endDate, null);
	}	
	
	public List<Entity> pullEvents(Integer fullYear, Integer month, Integer day){
		List<Entity> result = new ArrayList<Entity>();
		LocalDate startDate = LocalDate.of(fullYear, month, 1);
		LocalDate endDate = LocalDate.of(fullYear, month, YearMonth.of(fullYear, month).atEndOfMonth().getDayOfMonth());
		if(day != null && day > 0){
			startDate = LocalDate.of(fullYear, month, day);
			endDate = LocalDate.of(fullYear, month, day);
		}
		try {
			result = entityManager.pullEventsInRange(startDate,endDate);
		} catch (DataException e) {
			e.printStackTrace();
		}
		if(result != null){
			return result;
		}else
			return new ArrayList<Entity>();
	}
	
	public List<Entity> pullEvents(String startDateStr, String endDateStr){
		List<Entity> result1 = new ArrayList<Entity>();
		List<Entity> result = new ArrayList<Entity>();
		LocalDate startDate = LocalDate.parse(startDateStr);
		LocalDate endDate = LocalDate.parse(endDateStr);		
		try {
			result1 = entityManager.pullEventsInRange(startDate,endDate);
		} catch (DataException e) {
			e.printStackTrace();
		}
		if(result1 != null){
			return result1;
		}else
			return result;
	}
	
}
