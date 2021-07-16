/**
 * 
 */
package com.jbent.peoplecentral.model.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.pojo.Attribute;
import com.jbent.peoplecentral.model.pojo.CompletePermissions;




public interface DynamicJasperManager {
	
	@Transactional(rollbackFor = Exception.class)
	public JasperPrint  simpleQueryJSPrint(HttpServletRequest request, HttpServletResponse response,long entityTypeId, long entityId ,String savedQuery,String  conditionType,String fileterAttArray,String fileterConditionArray,String fileterConditionValueArray,String queryName, String submitType,Model model) throws DataException;
	
	@Transactional(rollbackFor = Exception.class)
	public JasperPrint  advancedQueryJSPrint(HttpServletRequest request, HttpServletResponse response,String queryString,String queryName, String submitType, String savedQuery,long entityId, Model model) throws DataException;
	
	@Transactional(rollbackFor = Exception.class)
	public JasperPrint  savedSearchJSPrint(HttpServletRequest request, HttpServletResponse response,long entityId,Model model) throws DataException;
	
	@Transactional(readOnly=true)
	public StringBuffer  loadReportPage(HttpServletRequest request, HttpServletResponse response,int pageNum,Model model) throws DataException;
	
	@Transactional(readOnly=true)
	public List<Attribute>  loadReportAttributes(long enitytTypeId,Model model) throws DataException;
	
	public void export(HttpServletResponse response, String exportingType,String entityTypeName) throws DataException ;
	
	@Transactional(rollbackFor = Exception.class)
	public List<CompletePermissions> queryPemissions(long entityId) throws DataException ;
}
