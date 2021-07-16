package com.jbent.peoplecentral.security.jwt.auth;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.exception.AttributeValueNotValidException;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.exception.DataExceptionRT;
import com.jbent.peoplecentral.model.manager.EntityManager;
import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;
import com.jbent.peoplecentral.model.pojo.Entity;
import com.jbent.peoplecentral.model.pojo.EntityStatus;
import com.jbent.peoplecentral.model.pojo.EntityType;
import com.jbent.peoplecentral.model.pojo.SignUp;
import com.jbent.peoplecentral.model.pojo.Attribute.FieldType;
import com.jbent.peoplecentral.permission.PermissionManager;
import com.jbent.peoplecentral.security.jwt.JwtTokenUtil;
import com.jbent.peoplecentral.security.jwt.JwtUser;
import com.jbent.peoplecentral.web.SessionContext;
import org.springframework.context.MessageSource;
import com.jbent.peoplecentral.model.manager.EntityTypeManager;



@RestController
public class JwtAuthenticationProvider {

    @Value("${jwt.header}")
    private String jwtUserToken;
    @Autowired
    private AuthenticationManager authManager;
    
    @Autowired
    private PermissionManager permissionManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EntityTypeManager entityTypeManager;

   // @Autowired
   // private UserDetailsService userDetailsService;

    @RequestMapping(value={"/${jwt.route.authentication}","/*/${jwt.route.authentication}"}, method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,ServletRequest request, ServletResponse response) throws AuthenticationException, DataException {
    	
    	Authentication preAuthenticationToken = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		Authentication postAuthenticationToken;
		try{
			postAuthenticationToken = this.authManager.authenticate(preAuthenticationToken);
		}catch(AuthenticationException authExp){
			throw authExp;
		}
		SecurityContextHolder.getContext().setAuthentication(postAuthenticationToken);		
    	Map<String, Object> claims = new HashMap<String, Object>();
		claims.put(JwtTokenUtil.CLAIM_KEY_USERNAME, authenticationRequest.getUsername());
        claims.put(JwtTokenUtil.CLAIM_KEY_CREATED, new Date());
        String commaSeparatedRoles = "";
		for(GrantedAuthority grantedAuthority : postAuthenticationToken.getAuthorities()){
			commaSeparatedRoles += grantedAuthority.getAuthority() + ",";
		}
		claims.put(JwtTokenUtil.CLAIM_KEY_ROLES, commaSeparatedRoles);
		String token = this.jwtTokenUtil.generateToken(claims);
		Cookie jwtCookie = new Cookie("COCOAOWL_J_W_T",token);
		jwtCookie.setPath("/");
		((HttpServletResponse)response).addCookie(jwtCookie);
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }
    
    @RequestMapping(value={"/${signup}","/*/${signup}"}, method = RequestMethod.POST)
    	public ResponseEntity<?>  createSignupUser(@RequestBody SignUp signup) {
    	
    	AttributeValueStorage userNameAttribute = new AttributeValueStorage();
    	userNameAttribute.setValueVarchar(signup.getUserName());
    	userNameAttribute.setId(125);
    	
    	AttributeValueStorage emailIdAttribute = new AttributeValueStorage();
    	emailIdAttribute.setValueVarchar(signup.getEmailId());
    	emailIdAttribute.setId(127);
    	
    	AttributeValueStorage passwordAttribute = new AttributeValueStorage();
    	passwordAttribute.setValueVarchar(signup.getPassword());
    	passwordAttribute.setId(128);
    	
    	List<AttributeValueStorage> avsList = new ArrayList<>();
    	avsList.add(userNameAttribute);
    	avsList.add(emailIdAttribute);
    	avsList.add(passwordAttribute);   	
    	
    	boolean status = false;
    	
    	Long entityId = (long) 0;
    	try {
			if(avsList != null && avsList.size() > 0){
				
				EntityType entityType = permissionManager.load(EntityType.EntityTypes.PEOPLE.getValue());		
			
				Entity entity = new Entity();
				
				entity.setAttributeValueStorage(avsList);
				entity.setEntityType(entityType);
				entity.setEntityTypeId(entityType.getId());
				
				EntityStatus entityStatus = new EntityStatus();
				
				entityStatus.setEntityId(entity.getEntityId());
				entityStatus.setEntityValid(false);
				
				for(AttributeValueStorage avs : avsList){
					if(entityId > 0) {
						avs.setEntityId(entityId);	
					}
					avs.setEntityTypeId(entity.getEntityTypeId());
				}	
				entityId = permissionManager.quickSave(entity,avsList);
			}
			if (entityId != null && entityId > 0) {
				status = permissionManager.assignUserRole(entityId, signup.getRole());			
			} 
    	}  catch(Exception e){
			e.printStackTrace();
		}
		return ResponseEntity.ok(status);    	
    }

    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(jwtUserToken);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = null;
        UserDetails userDetails = null;
		try {
			userDetails =  permissionManager.loadUserByUsername(username);
			user = permissionManager.loadJwtUserByUserDetails(userDetails);

	        if (jwtTokenUtil.canTokenBeRefreshed(token, jwtTokenUtil.getExpirationDateFromToken(token))) {
	            String refreshedToken = jwtTokenUtil.refreshToken(token);
	            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
	        } else {
	            return ResponseEntity.badRequest().body(null);
	        }
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

    }

}
