package com.jbent.peoplecentral.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.jbent.peoplecentral.security.jwt.JwtTokenUtil;

import io.jsonwebtoken.Claims;

public class ApplicationAuthFilter implements Filter{


    @Value("${jwt.header}")
    private String tokenHeader;
    
	private JwtTokenUtil jwtTokenUtil;
	public ApplicationAuthFilter(JwtTokenUtil jwtTokenUtil){
		this.jwtTokenUtil = jwtTokenUtil;
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		boolean isJwtSet = false;
		String jwt = "";
		Cookie cookies[] = httpRequest.getCookies();
		if(cookies != null){
			for(Cookie c : cookies){
				if(c.getName().equals("COCOAOWL_J_W_T")){
					isJwtSet = true;
					jwt = c.getValue();
				}
			}
		}
		if(jwt == null || jwt.length() == 0){
	        String authToken = httpRequest.getHeader(this.tokenHeader);
	        if(authToken != null && authToken.length() != 0){
	        	if(authToken.startsWith("token")){
	        		JSONObject jObj = new JSONObject(authToken);
	                jwt = jObj.getString("token");
	        	}else{
	        		jwt = authToken;
	        	}        	
	        }
		}
		
		if(isJwtSet){
			Claims claimns = this.jwtTokenUtil.getClaimsFromToken(jwt);
			String username = claimns.get(JwtTokenUtil.CLAIM_KEY_USERNAME).toString();
			String commaSeparatedRoles = claimns.get(JwtTokenUtil.CLAIM_KEY_ROLES).toString();
			if(commaSeparatedRoles != null && commaSeparatedRoles != ""){
				String roles[] = commaSeparatedRoles.split(",");
				List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
				for(String role : roles){
					grantedAuthorities.add(new SimpleGrantedAuthority(role));
				}
				Authentication postAuthenticationToken = new UsernamePasswordAuthenticationToken(username, "", grantedAuthorities);
				SecurityContextHolder.getContext().setAuthentication(postAuthenticationToken);
			}else{
				throw new AccessDeniedException("No Roles found for user");
			}
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
