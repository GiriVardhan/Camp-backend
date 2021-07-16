/**
 * 
 */
package com.jbent.peoplecentral.permission;

import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;

import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.web.SessionContext;

/**
 * @author RaviT
 *
 */
public class CustomSwitchUserFilter extends SwitchUserFilter {
	
	public CustomSwitchUserFilter() {
		super();
	}	
	@Override
	public void setTargetUrl(String targetUrl) {
		// TODO Auto-generated method stub		
		String client = ClientManageUtil.loadClientSchema();
		super.setTargetUrl("/app/"+client+"/admin/switchUser");
	}
	
}
