package com.jbent.peoplecentral.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.google.gson.JsonObject;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.permission.PermissionManager;

public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private PermissionManager permissionManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	String token = "";
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authToken = httpRequest.getHeader(this.tokenHeader);
        if(authToken != "" && authToken != null){
        	if(authToken.startsWith("token")){
        		JSONObject jObj = new JSONObject(authToken);
                token = jObj.getString("token");
        	}else{
        		token = authToken;
        	}        	
        }
        
        Cookie cookies[] = httpRequest.getCookies();
        if(cookies != null){
        	for(Cookie c: cookies){
        		if(c.getName().equals("COCOAOWL_J_W_T")){
        			token = c.getValue();
        		}
        	}
        }
        
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails;
			try {
				userDetails = permissionManager.loadUserByUsername(username);
				if (jwtTokenUtil.validateToken(token, userDetails)) {
	                UsernamePasswordAuthenticationToken authentication = permissionManager.getJWTAuthentication(userDetails);
	                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
	                SecurityContextHolder.getContext().setAuthentication(authentication);
	            }
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }

        chain.doFilter(request, response);
     
    }
}