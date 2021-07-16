package com.jbent.peoplecentral.filter;

import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class SwaggerLoginEntry extends LoginUrlAuthenticationEntryPoint {

	public SwaggerLoginEntry(String loginFormUrl) {
		super("/app/swagger/swaggerloginpage.html");
	}
	
	public SwaggerLoginEntry() {
		super("/app/swagger/swaggerloginpage.html");
	}

}
