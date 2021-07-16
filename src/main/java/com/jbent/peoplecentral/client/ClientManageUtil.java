package com.jbent.peoplecentral.client;

import com.jbent.peoplecentral.web.SessionContext;

public class ClientManageUtil {

	public static String loadClientSchema() {
		try{
			return SessionContext.getSessionContext().getSchemaContext();
		}catch (IllegalStateException e) {
			return "testclient";
		}
	}
	
}
