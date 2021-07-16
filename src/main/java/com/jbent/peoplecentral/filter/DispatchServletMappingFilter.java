/**
 * 
 */
package com.jbent.peoplecentral.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.web.SessionContext;

/**
 * @author jasontesser
 *
 */
public class DispatchServletMappingFilter implements Filter {
	
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req; 
		
		 HttpServletResponse response = (HttpServletResponse) res;
		    response.setHeader("Access-Control-Allow-Origin", "*");
		    response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		    response.setHeader("Access-Control-Max-Age", "3600");
		    response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, JWT-AUTH-TOKEN, Authorization, ");
		    
		    
		SessionContext sc = SessionContext.getSessionContext();
		if(sc != null && sc.getSchemaContext() != null){
			chain.doFilter(request, res);
			return;
		}
		try{
			String uri = request.getRequestURI().replace(request.getContextPath(), "");
			String uriParts[] = uri.split("/");
			if(!uriParts[1].equals("app")){
				chain.doFilter(request, res);
				return;
			}
			String schemaContext = "testclient";
			if(uriParts[1].equals("app")){
				schemaContext = uriParts[2];
			}
			if(schemaContext != null && schemaContext.length() != 0)
				sc.setSchemaContext(schemaContext);
			if(uriParts[1].equals("app")){				
				request = new DispatchServletMappingRequestWrapper((HttpServletRequest)req, ((HttpServletRequest)req).getRequestURI().split("/")[3]);
			}else{
				request = new DispatchServletMappingRequestWrapper((HttpServletRequest)req, ((HttpServletRequest)req).getRequestURI().split("/")[1]);
			}
		}catch (Exception e) {
//			SchemaContext.setClientScehma(((HttpServletRequest)req).getServerName());
		}
		chain.doFilter(request, res);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {}

	public void setSessionContext() throws DataException{
		
	}
	
}
