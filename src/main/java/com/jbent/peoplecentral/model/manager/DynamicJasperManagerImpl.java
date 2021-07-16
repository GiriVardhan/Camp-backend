/**
 * 
 */
package com.jbent.peoplecentral.model.manager;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.ui.Model;
import org.springframework.util.Assert;

import ar.com.fdvs.dj.core.DJServletHelper;

import com.jbent.peoplecentral.dynamic.jasper.ReportLayout;
import com.jbent.peoplecentral.exception.AttributeValueNotValidException;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.dao.EntityDAO;
import com.jbent.peoplecentral.model.dao.EntityTypeDAO;
import com.jbent.peoplecentral.model.pojo.Attribute;
import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;
import com.jbent.peoplecentral.model.pojo.CompletePermissions;
import com.jbent.peoplecentral.model.pojo.Entity;
import com.jbent.peoplecentral.model.pojo.EntityStatus;
import com.jbent.peoplecentral.model.pojo.EntityType;
import com.jbent.peoplecentral.model.pojo.ImportEntity;
import com.jbent.peoplecentral.model.pojo.Attribute.Attributes;
import com.jbent.peoplecentral.model.pojo.Attribute.DataType;
import com.jbent.peoplecentral.model.pojo.EntityType.EntityTypes;
import com.jbent.peoplecentral.permission.PermissionManager;
import com.jbent.peoplecentral.web.SessionContext;



public class DynamicJasperManagerImpl extends ApplicationObjectSupport implements
		DynamicJasperManager, InitializingBean {
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private EntityTypeManager entityTypeManager;
	@Autowired
	private EntityDAO entityDAO;   
	@Autowired
	private EntityTypeDAO entityTypeDAO;
	@Autowired
	private PermissionManager permissionManager;
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(entityDAO, "entityDAO is null");
		Assert.notNull(entityManager, "entityManager is null");
		Assert.notNull(entityTypeManager, "entityTypeManager is null");
	}
	
	@Override
	public JasperPrint simpleQueryJSPrint(HttpServletRequest request,
	    HttpServletResponse response, long entityTypeId,long entityId,String savedQuery,
		String conditionType, String fileterAttArray,
		String fileterConditionArray, String fileterConditionValueArray,
		String queryName,String submitType, Model model) throws DataException {
	
		EntityType entityType = null;
		EntityStatus entityStatus = null;
		JasperPrint jp = null;
		String share = "yes";
		try {
			entityType =entityTypeDAO.load(entityTypeId);
			String queryString = prepareQuery(entityType, conditionType, fileterAttArray, fileterConditionArray, fileterConditionValueArray, queryName);
			
			//If the user provides a "Query name" we serve the interactive permissions exactly similar to the one we serve in the Entity Editor
			if(queryName != ""){
				entityStatus = saveQuery(queryName,queryString,"simpleQuery",share,savedQuery,entityId);
				model.addAttribute("entityStatus",entityStatus);
			}	
			if(submitType.equalsIgnoreCase("reportSubmit"))
				jp = prepareJasperPrint(queryString, entityTypeId, "simpleQuery", request, response, model);

		} catch (DataException e) {
			logger.error("simpleQueryJSPrint.Error simpleQueryJSPrint:"+e);
			throw new DataException("simpleQueryJSPrint.Error simpleQueryJSPrint:"+e);
		} catch (Exception e) {
			logger.error("simpleQueryJSPrint.Error simpleQueryJSPrint:"+e);
			throw new DataException("simpleQueryJSPrint.Error simpleQueryJSPrint:"+e);
		}
				
		return jp;
	}

	@Override
	public JasperPrint savedSearchJSPrint(HttpServletRequest request,
		HttpServletResponse response, long entityId, Model model) throws DataException
		 {
	
		JasperPrint jp = null;
		Entity entity;
		try {
			entity = entityDAO.loadEntity(entityId);
			List<AttributeValueStorage> avs = entity.getAttributeValueStorage();
			String queryString ="";
		
			for(AttributeValueStorage value:avs){
				if(value.getId() == Attributes.QUERYSYNTAX.getValue()){
					queryString = value.getValueText();
				}
			}
			jp = prepareJasperPrint(queryString, 0, "savedSearch", request, response, model);
		} catch (DataException e) {
			logger.error("savedSearchJSPrint.Error savedSearchJSPrint:"+e);
			throw new DataException("savedSearchJSPrint.Error savedSearchJSPrint:"+e);
		} catch (Exception e) {
			logger.error("savedSearchJSPrint.Error savedSearchJSPrint:"+e);
			throw new DataException("savedSearchJSPrint.Error savedSearchJSPrint:"+e);
		}
		
		return jp;
	}
	
	@Override
	public JasperPrint advancedQueryJSPrint(HttpServletRequest request,
	    HttpServletResponse response,String queryString, String queryName,String submitType,String savedQuery,long entityId, Model model) throws DataException  {
		JasperPrint jp = null;
		EntityStatus entityStatus = null;
		String share = "yes";
		//If the user provides a "Query name" we serve the interactive permissions exactly similar to the one we serve in the Entity Editor
		try {
			if(queryName != ""){
				entityStatus = saveQuery(queryName,queryString,"advancedQuery",share,savedQuery, entityId);
			}
			if(submitType.equalsIgnoreCase("reportSubmit"))
				jp = prepareJasperPrint(queryString, 0, "advancedQuery", request, response, model);
		} catch (DataException e) {
			logger.error("advancedQueryJSPrint.Error advancedQueryJSPrint:"+e);
			throw new DataException("advancedQueryJSPrint.Error advancedQueryJSPrint:"+e);
		} catch (Exception e) {
			logger.error("advancedQueryJSPrint.Error advancedQueryJSPrint:"+e);
			throw new DataException("advancedQueryJSPrint.Error advancedQueryJSPrint:"+e);
		}
		model.addAttribute("entityStatus",entityStatus);
		return jp;
	}

	public List<Entity> load(long entityTypeId,List<Long> attributeIds) throws DataException {
		try {
			return entityDAO.loadEntities(entityTypeId , attributeIds);
		} catch (DataException e) {
			logger.error("load.Error load Entities for Report/Search:"+e);
			throw new DataException("load.Error load Entities for Report/Search:"+e);
		}
	}
	
		
	@Override
	public StringBuffer loadReportPage(HttpServletRequest request,HttpServletResponse response,int pageNum, Model model) throws DataException {
		String imageServletUrl = "reports/image";
		StringBuffer result = new StringBuffer();
		SessionContext jasperPrintSC = SessionContext.getJasperPrintSessionContext();
		if(jasperPrintSC.getJasperPrint() != null){
			JasperPrint jp = jasperPrintSC.getJasperPrint();
			if (jp != null){
				model.addAttribute("totalPages",jp.getPages().size());
				model.addAttribute("pageNum",pageNum);
				model.addAttribute("etName",null);
				JRHtmlExporter exporter = new JRHtmlExporter();				
			try
			{
				int pageIndex = pageNum;
				int lastPageIndex = 0;
				if (jp.getPages() != null){
					lastPageIndex = jp.getPages().size() - 1;
				}
				if (pageIndex < 0){
					pageIndex = 0;
				}
				if (pageIndex > lastPageIndex){
					pageIndex = lastPageIndex;
				}
				exporter.setParameter(JRHtmlExporterParameter.PAGE_INDEX, Integer.valueOf(pageIndex));
				exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML, "");
				DJServletHelper.setPageTreshold(jp.getPages().size());
				DJServletHelper.exportToHtml(request, response, imageServletUrl, jp, exporter.getExportParameters());
			}
			catch (JRException e) {
				logger.error("loadReportPage.Error loadReportPage:"+e);
				throw new DataException("loadReportPage.Error loadReportPage:"+e);
			} catch (IOException e) {
				logger.error("loadReportPage.Error loadReportPage:"+e);
				throw new DataException("loadReportPage.Error loadReportPage:"+e);
			}
		}else{
			logger.error("JasperPrint should not be null to export HTML "+jp);
		}
	}else{
		logger.error("No JasperPrint found on jasperPrintSC"+jasperPrintSC.getJasperPrint());
	}
	return result;
	}
	@Override
	public List<Attribute> loadReportAttributes(long enitytTypeId, Model model) throws DataException	 {
		EntityType entityType = null;
		try {
			entityType = entityTypeDAO.load(enitytTypeId);
			if(entityType != null){
				if(entityType.getAttributes().size()>0){
					return entityType.getAttributes();
				}
			}	
		} catch (DataException e) {
			logger.error("loadReportAttributes.Error loadReportAttributes:"+e);
			throw new DataException("loadReportAttributes.Error loadReportAttributes:"+e);
		}
		return null;
	}
	
	
	public void export(HttpServletResponse response, String exportingType,String entityTypeName) throws DataException  {
		// Create a JRXlsExporter instance
		JRExporter exporter;
		// Create our output byte stream
		// This is the stream where the data will be written
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		SessionContext jasperPrintSC = SessionContext.getJasperPrintSessionContext();
		if(jasperPrintSC.getJasperPrint() != null){
			JasperPrint jp = jasperPrintSC.getJasperPrint();
			try{
				if (jp != null){
					String fileName = entityTypeName+"."+exportingType;
					response.setContentType("application/octet-stream");
				    response.setHeader("Content-Disposition","attachment;filename="+fileName);
				
					if (exportingType.equalsIgnoreCase("pdf")){
						exporter = new JRPdfExporter();
						exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
						exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
						exporter.exportReport();
						// Set our response properties
						writeReportToResponseStream(response, baos);
					}else if (exportingType.equalsIgnoreCase("xls")){
						exporter =new JRXlsExporter();
						exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
						exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
						exporter.exportReport();
						// Set our response properties
						writeReportToResponseStream(response, baos);
					}else  if (exportingType.equalsIgnoreCase("xml")){
						exporter =new JRXmlExporter();
						exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
						exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
						exporter.exportReport();
						// Set our response properties
						writeReportToResponseStream(response, baos);
					}
					else  if (exportingType.equalsIgnoreCase("text")){
						exporter =new JRTextExporter();
						exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
						exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
						exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Integer(6));//6.55 //6
						exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Integer(10)); //11//10
						exporter.exportReport();
						// Set our response properties
						writeReportToResponseStream(response, baos);
					}
						
				}else{
					logger.error("JasperPrint should not be null to export HTML "+jp);
				}
			}catch (JRException e) {
				logger.error("export.Error exportReport:"+e);
				throw new DataException("export.Error exportReport:"+e);
			}
			
		}else{
			logger.error("No JasperPrint found on jasperPrintSC"+jasperPrintSC.getJasperPrint());
		}
	 }
	
	
	public String simpleSearchBuild(long entityTypeId,String conditionType, String fileterAttArray,
		String fileterConditionArray, String fileterConditionValueArray,
		String queryName,String savedQuery, long entityId, Model model) throws DataException  {
		EntityType entityType;	
		EntityStatus entityStatus = null;
		String share = "yes";
		String queryString = null;
		try {
			entityType =entityTypeDAO.load(entityTypeId);
			queryString = prepareQuery(entityType, conditionType, fileterAttArray, fileterConditionArray, fileterConditionValueArray, queryName);
			
			//If the user provides a "Query name" we serve the interactive permissions exactly similar to the one we serve in the Entity Editor
			if(queryName != ""){
				entityStatus = saveQuery(queryName,queryString,"simpleQuery",share,savedQuery,entityId);
				model.addAttribute("entityStatus",entityStatus);
			}
		} catch (DataException e) {
			logger.error("simpleSearchBuild.Error simpleSearchBuild:"+e);
			throw new DataException("simpleSearchBuild.Error simpleSearchBuild:"+e);
		}
		return queryString;
	}

	private EntityStatus saveQuery(String queryName,String queryString,String searchType,String share,String savedQuery, long entityId) throws DataException {
		EntityType entityType;
		EntityStatus entityStatus = null;
		try {
			entityType = entityTypeDAO.load(EntityTypes.SAVEDSEARCHES.getValue());
			List<Attribute> attributes = entityType.getAttributes();
			entityStatus = new EntityStatus();
			for(Attribute att: attributes){
					AttributeValueStorage value = new AttributeValueStorage();
					value.setRegex(att.getRegex());	
					if(entityStatus.getEntityId() > 0){
						value.setEntityId(entityStatus.getEntityId());
					}else{
						value.setEntityId(entityId);
					}
					
					if(att.getId() == Attributes.QUERYNAME.getValue()){
						value.setValueVarchar(queryName.trim());
						value.setId(att.getId());
					}
					else if(att.getId() == (Attributes.QUERYSYNTAX.getValue()) ){
						value.setValueText(queryString.trim());	
						value.setId(att.getId());									
					}
					else if(att.getId() == (Attributes.SHARE.getValue()) ){
						value.setValueVarchar(share);
						value.setId(att.getId());
					}
					else if(att.getId() == (Attributes.USER.getValue()) ){
						value.setValueVarchar(permissionManager.getUsername());	
						value.setId(att.getId());
					}
					else if(att.getId() == (Attributes.SEARCHTYPE.getValue()) ){
						value.setValueVarchar(searchType);	
						value.setId(att.getId());
					}
					else if(att.getId() == (Attributes.SAVEDQUERY.getValue()) ){
						value.setValueVarchar(savedQuery);	
						value.setId(att.getId());
					}
					entityStatus = entityDAO.avsSave(value);
				}

		} catch (DataException e) {
			logger.error("saveQuery.Error saveQuery:"+e);
			throw new DataException("saveQuery.Error saveQuery:"+e);
		} catch (AttributeValueNotValidException e) {
			logger.error("saveQuery.Error saveQuery AttributeValueNotValidException:"+e);
			throw new DataException("saveQuery.Error saveQuery AttributeValueNotValidException:"+e);
		}
		
		return entityStatus;
	}
		
	private String prepareQuery(EntityType entityType,String conditionType, String fileterAttArray,
		String fileterConditionArray, String fileterConditionValueArray,
		String queryName) throws DataException{
		
		StringBuffer searchTerm= new StringBuffer();
		String[] selectedAttributes = fileterAttArray.split(",");
		String[] conditionValues= fileterConditionValueArray.split(",");
		if(selectedAttributes.length > 0 && conditionValues.length > 0){
			if(selectedAttributes[0] != "" && conditionValues[0] !=""){
				for(int i=0;i<selectedAttributes.length;i++){
					if(conditionType.equalsIgnoreCase("any")){
						if(searchTerm.length()<=0){
							searchTerm.append("type:"+entityType.getName()+" ");
							searchTerm.append(selectedAttributes[i]+":["+conditionValues[i]+"]");
						}else{
							searchTerm.append(" or "+selectedAttributes[i]+":["+conditionValues[i]+"]");
						}	
					}else if(conditionType.equalsIgnoreCase("all")) {
						if(searchTerm.length()<=0){
							searchTerm.append("type:"+entityType.getName()+" ");
							searchTerm.append(selectedAttributes[i]+":["+conditionValues[i]+"]");
						}else{
							searchTerm.append(" and "+selectedAttributes[i]+":["+conditionValues[i]+"]");
						}
					}
				}
			}else searchTerm.append("type:"+entityType.getName());
		}else{
			searchTerm.append("type:"+entityType.getName());
		}
		logger.info("**Query String: "+searchTerm.toString());
		return searchTerm.toString();
	}
	
	
		private JasperPrint prepareJasperPrint(String queryString,long entityTypeId,String searchType,HttpServletRequest request,
			HttpServletResponse response,Model model) throws DataException {
		List<Entity> entities = new ArrayList<Entity>();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		EntityType entityType = null;
		List<Attribute> attributes = null ;
		JasperPrint jp = null;
		String start = "<b>";
		String end = "</b>";
		long limit = 1000000;
		long offset = 0;
		try {
			entities = entityManager.entitySearch(queryString,entityTypeId, start, end, limit, offset);
			if(searchType.equalsIgnoreCase("savedSearch")|| searchType.equalsIgnoreCase("advancedQuery")){
				if(entities != null && entities.size()>0){
					Entity ent = entities.listIterator().next();
					entityType = entityTypeDAO.load(ent.getEntityTypeId());
					model.addAttribute("etName",entityType.getName());
					attributes = entityType.getAttributes();
					dataList = prepareDataList(entities);
				}
			}else if(searchType.equalsIgnoreCase("simpleQuery") ){
				if(entities != null && entities.size()>0){
					entityType = entityTypeDAO.load(entityTypeId);
					attributes = entityType.getAttributes();
					dataList = prepareDataList(entities);
				}
			}
			// Create our report layout
			// We delegate the reporting layout to a custom ReportLayout instance
			if(dataList.size()>0){
				model.addAttribute("totalRecords",entities.size());
				ReportLayout layout = new ReportLayout();
				layout.setCollection(dataList);
				layout.setAttributes(attributes);
				layout.setTitle(entityType.getName());
				jp = layout.testReport(request,response,model);
			}

		} catch (DataException e) {
			logger.error("prepareJasperPrint.Error prepareJasperPrint:"+e);
			throw new DataException("prepareJasperPrint.Error prepareJasperPrint:"+e);
		} catch (Exception e) {
			logger.error("prepareJasperPrint.Error prepareJasperPrint:"+e);
			throw new DataException("prepareJasperPrint.Error prepareJasperPrint:"+e);
		}
		// set Jasper Print in session for further reporting
		SessionContext.setJasperPrintSessionContext(jp);
		return jp;
	}
	
	private List<Map<String, Object>>  prepareDataList (List<Entity> entities) throws DataException{
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map1=null;
		if(entities!=null && entities.size() > 0){
			for(Entity entity : entities){
				map1 = new HashMap<String, Object>();
				List<AttributeValueStorage> values = entity.getAttributeValueStorage();
				try {
					values = entityTypeManager.filterAttributesValues(values);
				} catch (DataException e) {
					logger.error("Error in DynamicJasperManagerImpl.prepareDataList:"+e);
				}
				if(values !=null && values.size() > 0){
					for(AttributeValueStorage avs: values ){
						//Attribute att = pickAttribute(avs.getId(),attributes);
						if(avs.getDataTypeName().equals(DataType.VARCHAR.toString())){
							map1.put(avs.getName(), avs.getValueVarchar());
						}	
						else if(avs.getDataTypeName().equals(DataType.LONG.toString())){
							map1.put(avs.getName(), avs.getValueLong());
						}	
						else if(avs.getDataTypeName().equals(DataType.DATE.toString())){
							map1.put(avs.getName(), avs.getValueDate());
						}	
						else if(avs.getDataTypeName().equals(DataType.TEXT.toString())){
							map1.put(avs.getName(), avs.getValueText());
						}	
					}
					dataList.add(map1);
				}
			}
		}else{
			logger.error("No Entites found to prepare dataList for Report"+entities);
		}
		return dataList;
	}


	/**
	* Writes the report to the output stream
	*/
	private void writeReportToResponseStream(HttpServletResponse response,ByteArrayOutputStream baos) {	 
		logger.debug("Writing report to the stream");
		try {
			// 	Retrieve the output stream
			ServletOutputStream outputStream = response.getOutputStream();
			// 	Write to the output stream
			baos.writeTo(outputStream);
			// 	Flush the stream
			outputStream.close();
		} catch (Exception e) {
			logger.error("Unable to write report to the output stream");
		}
	}

	@Override
	public List<CompletePermissions> queryPemissions(long entityId) throws DataException  {
		boolean entityStats = false;
		List<CompletePermissions> combinedRolesForEntity = null;
		List<ImportEntity> imprtEntities = new ArrayList<ImportEntity>();
		
		Entity entity;
		try {
			
	  	    entity = entityDAO.loadEntity(entityId);
			ImportEntity imprtEntity = new ImportEntity();
			imprtEntity.setEntity(entity);
			imprtEntity.setParent(entity.getEntityType());
			imprtEntities.add(imprtEntity);
			
			MutableAcl acl = permissionManager.fetchAcl(entity);
			if(acl == null){			
				entityStats = permissionManager.createPermissionsOnNewEntities(imprtEntities);			
			}else{
				entityStats = permissionManager.createPermissionOnEntity(entity, entity.getEntityType());
			}
			
			if(entityStats){
				// retrieve the All roles permissions on the Entity
				combinedRolesForEntity = entityManager.retrieveCombinedRolesForEntity(entity);
				
			}
		} catch (DataException e) {
			logger.error("queryPemissions.Error queryPemissions:"+e);
			throw new DataException("queryPemissions.Error queryPemissions:"+e);
		}
		return combinedRolesForEntity;
		
	}


}
