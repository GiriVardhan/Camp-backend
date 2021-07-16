package com.jbent.peoplecentral.javaconfig;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;

public class CocoaOwlCorsConfigurationSource extends org.springframework.web.cors.UrlBasedCorsConfigurationSource{

	@Override
	public CorsConfiguration getCorsConfiguration(HttpServletRequest request){
		
		System.out.println(" - - - - inside getCorsConfiguration () . . . . ");;
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedMethod(HttpMethod.GET);
		corsConfiguration.addAllowedMethod(HttpMethod.POST);
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedHeader("Authorization");
		corsConfiguration.addAllowedHeader("Origin");
		corsConfiguration.addAllowedHeader("X-Requested-With");
		corsConfiguration.addAllowedHeader("Accept");
		corsConfiguration.addAllowedHeader("Content-Type");
		corsConfiguration.addAllowedHeader("Authorization");
		corsConfiguration.addAllowedHeader("JWT-AUTH-TOKEN");
		corsConfiguration.addAllowedHeader("*");
		return corsConfiguration;
		
	}
	
	@Override
	public Map<String,CorsConfiguration> getCorsConfigurations(){
		Map<String,CorsConfiguration> map = new HashMap<String,CorsConfiguration>();
		map.put("/**", getCorsConfiguration(null));
		map.put("/*", getCorsConfiguration(null));
		return map;
	}
}
