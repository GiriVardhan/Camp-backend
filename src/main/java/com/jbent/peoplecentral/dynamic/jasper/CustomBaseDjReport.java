
package com.jbent.peoplecentral.dynamic.jasper;


import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;

import ar.com.fdvs.dj.core.DJServletHelper;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.core.layout.ListLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;

import com.jbent.peoplecentral.model.pojo.Attribute;
@SuppressWarnings("unchecked")
public abstract class CustomBaseDjReport {


	protected static final Log log = LogFactory.getLog(CustomBaseDjReport.class);

	protected JasperPrint jp;
	protected JasperReport jr;
	protected Map params = new HashMap();
	protected DynamicReport dr;
	protected Collection collection = null;
	protected String title;
	protected String imageServletUrl = "reports/image";
	protected Map exporterParams = new HashMap();
	protected List<Attribute> attributes;

	public abstract DynamicReport buildReport() throws Exception;

	public JasperPrint testReport(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
			
			dr = buildReport();

			/**
			 * Get a JRDataSource implementation
			 */
			JRDataSource ds = getDataSource();

			/**
			 * Creates the JasperReport object, we pass as a Parameter
			 * the DynamicReport, a new ClassicLayoutManager instance (this
			 * one does the magic) and the JRDataSource
			 */
			jr = DynamicJasperHelper.generateJasperReport(dr, getLayoutManager(), params);
			
			/**
			 * Creates the JasperPrint object, we pass as a Parameter
			 * the JasperReport object, and the JRDataSource
			 */
			log.debug("Filling the report");
			if (ds != null)
				jp = JasperFillManager.fillReport(jr, params, ds);
			else
				jp = JasperFillManager.fillReport(jr, params);

			/** DJServletHelper class which handles the most common scenario for HTML 
			 **  exporting in a HTTP web server. DJServletHelper will directly write the HTML in the response.
			 */
//			if (jp != null){
//				JRHtmlExporter exporter = new JRHtmlExporter();
//				
//				int pageIndex = 0;
//				int lastPageIndex = 0;
//				if (jp.getPages() != null){
//					lastPageIndex = jp.getPages().size() - 1;
//					log.error("########### total pages"+lastPageIndex);
//				}
//				String pageStr = request.getParameter("page");
//				try
//				{
//					pageIndex = Integer.parseInt(pageStr);
//				}
//				catch(Exception e)
//				{
//				}
//				
//				if (pageIndex < 0)
//				{
//					pageIndex = 0;
//				}
//
//				if (pageIndex > lastPageIndex)
//				{
//					pageIndex = lastPageIndex;
//				}
//				model.addAttribute("pageIndex",pageIndex);
//				model.addAttribute("lastPageIndex",lastPageIndex);
//				log.error("########### pageIndex"+pageIndex);
//				exporter.setParameter(JRHtmlExporterParameter.PAGE_INDEX, Integer.valueOf(pageIndex));
//				exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML, "");
//				DJServletHelper.exportToHtml(request, response, imageServletUrl, jp, exporter.getExportParameters());
//			}
//			log.debug("Filling done!");
			log.debug("test finished");
			model.addAttribute("totalPages",jp.getPages().size());
            return jp;
	}

	protected LayoutManager getLayoutManager() {
		return new ListLayoutManager();
	}

	/**
	 * @return
	 */
	protected JRDataSource getDataSource() {
		JRDataSource ds = new JRBeanCollectionDataSource(this.collection);		//Create a JRDataSource, the Collection used
		return ds;
	}


	public DynamicReport getDynamicReport() {
		return dr;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	
	public Collection getCollection() {
		return collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public Map getParams() {
		return params;
	}

}
