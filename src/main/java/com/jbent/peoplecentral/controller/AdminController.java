package com.jbent.peoplecentral.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.manager.AdminManager;
import com.jbent.peoplecentral.model.manager.EntityTypeManager;
import com.jbent.peoplecentral.model.manager.LoginUserManager;
import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;
import com.jbent.peoplecentral.model.pojo.Users;
import com.jbent.peoplecentral.permission.PermissionManager;
import com.jbent.peoplecentral.web.SessionContext;

@Controller

public class AdminController extends WebApplicationObjectSupport{

	private AdminManager adminManager;
	private PermissionManager permissionManager;
	@Autowired
	private EntityTypeManager entityTypeManager = null;	
	@Autowired
	private LoginUserManager loginUserManager;
	
	@RequestMapping(value={"/admin","/*/admin"},method=RequestMethod.GET)	
    public MappingJackson2JsonView admin()  { 
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		List<AttributeValueStorage> attValueList = null ;
		List <AttributeValueStorage> usersList = null;
		Users users = new Users();			
		view.getAttributesMap().put("users", users);
		try {
			usersList = adminManager.loadUsers();
			attValueList = adminManager.loadRoles();
		} catch (DataException e) {
			logger.error("error while loading users :"+ e.getMessage(),e);
		}catch (Exception e) {
			logger.error("error while loading users :"+ e.getMessage(),e);
		}
		view.getAttributesMap().put("usersList", usersList);
		view.getAttributesMap().put("rolesList",attValueList);
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return view;
    }
	
	@RequestMapping(value = {"/admin/add/client","/*/admin/add/client"},method = RequestMethod.POST)
	public MappingJackson2JsonView addClient( @RequestParam ("schemaName") String schemaName, @RequestParam ("clientName") String clientName){
		List <AttributeValueStorage> usersList = new ArrayList<AttributeValueStorage>();
		List<AttributeValueStorage> attValueList = null ;
		Users users = new Users();	
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.getAttributesMap().put("users", users);
		try {
			adminManager.createClient(schemaName, clientName);
			usersList = adminManager.loadUsers();
			attValueList = adminManager.loadRoles();
		} catch (DataException e) {
			logger.error("error while creating a client :"+ e.getMessage(),e);
		}catch (Exception e) {
			logger.error("error while creating a client :"+ e.getMessage(),e);
		}  
		view.getAttributesMap().put("usersList", usersList);
		view.getAttributesMap().put("rolesList",attValueList);
		view.getAttributesMap().put("message", "Client Created");
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return view;
   }
		
	@RequestMapping(value={"/admin/signin","/*/admin/signin"}, method=RequestMethod.GET)
	public MappingJackson2JsonView signIn(HttpServletRequest request){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());			
        return view;
    }  
	
	@RequestMapping(value={"/admin/switchUser","/*/admin/switchUser"}, method=RequestMethod.GET)
	public View switchUser(HttpServletRequest request,ServletRequest session){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		String previousUser = null;
		String currentUser = null;	
		String client = SessionContext.getSessionContext().getSchemaContext();
		RedirectView rv = new RedirectView("/app/" + client);
		rv.setExposeModelAttributes(false);
		try {
			previousUser= permissionManager.getPreviousUser();
			currentUser= permissionManager.getUsername();	
			SessionContext signInAs = SessionContext.getSessionContext();
			view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
			logger.info("switchUser:- SessionContext.getSessionContext().signInAs.getSignIn() :"+ signInAs.getSignIn());
			if(signInAs.getSignIn() != null){
				if(signInAs.getSignIn().equalsIgnoreCase("no")){
					SessionContext.getSignedInAsSessionContext().destroySignedInAsSessionContext();	
					loginUserManager.setUserInSessionContext();
					return rv;
				}
			}
			if(previousUser != null){			
				if(previousUser.equalsIgnoreCase(currentUser)){
					SessionContext.getSignedInAsSessionContext().destroySignedInAsSessionContext();
					view.getAttributesMap().put("message", "SignIn as different User");
					RedirectView rv1 = new RedirectView("/admin/switchuser");
					rv1.setExposeModelAttributes(false);
					return  rv1;
				}
			}
			loginUserManager.setUserInSessionContext();
			SessionContext.getSignedInAsSessionContext();		
		} catch (DataException e) {
			logger.error("Error in switching user :"+ e.getMessage());
		}catch (Exception e) {
			logger.error("Error in switching user :"+ e.getMessage());
		}
		return rv;
    } 
	
	@RequestMapping(value={"/admin/signOut","/*/admin/signOut"}, method=RequestMethod.GET)
	public RedirectView signOut(HttpServletRequest request){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		SessionContext.setSignedOutAsSessionContext();		
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		RedirectView rv = new RedirectView("/j_spring_security_exit_user");
		rv.setExposeModelAttributes(false);
		return rv;       
    } 
	@RequestMapping(value={"/admin/loggedout","/*/admin/loggedout"}, method=RequestMethod.GET)
	public MappingJackson2JsonView logOut(HttpServletRequest request){
		lockEntityTypes();
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		SessionContext.getSessionContext().destroySessionContext();
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return view;

    }  
	
	/*public RedirectView logOut(HttpServletRequest request,Model model){	
		String client = ClientManageUtil.loadClientSchema();
		lockEntityTypes();
		SessionContext.getSessionContext().destroySessionContext();
		model.addAttribute("client", ClientManageUtil.loadClientSchema());
		RedirectView rv = new RedirectView("/app/" + client+"/dashboard");
		rv.setExposeModelAttributes(false);
		return rv;
//		return "/admin/entitytype/list";
    } */ 

	@RequestMapping(value = {"/admin/assign/roles","/*/admin/assign/roles"},method=RequestMethod.POST)
	public MappingJackson2JsonView saveRoles(@RequestParam ("userName") String username, @RequestParam ("authority") String authoritie) throws DataException{	  
		List <AttributeValueStorage> usersList = null;
		List<AttributeValueStorage> attValueList = null ;
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		try {
			usersList = adminManager.loadUsers();
			attValueList = adminManager.loadRoles();
		} catch (DataException e) {
			logger.error("error in assign roles :"+ e.getMessage(),e);
		}catch (Exception e) {
			logger.error("error in assign roles :"+ e.getMessage(),e);
		}
		permissionManager.assignRoleToUser(username,authoritie);
   		Users users = new Users();			
   		view.getAttributesMap().put("users", users);
   		view.getAttributesMap().put("usersList", usersList); 
   		view.getAttributesMap().put("rolesList",attValueList);
   		view.getAttributesMap().put("message", "Role Assigned");
   		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
   		return view;
	}
	
		
	private void lockEntityTypes(){
		SessionContext unLockedEntityTypeSC = SessionContext.getUnlockedEnittyTypesSessionContext();
		if(unLockedEntityTypeSC.getEtIdsList() != null){
			try {			
				entityTypeManager.setToLock(unLockedEntityTypeSC.getEtIdsList());
				
			} catch (DataException e) {
				logger.error("error in lock entityType :"+ e.getMessage(),e);		
			}catch (Exception e) {
				logger.error("error in lock entityType :"+ e.getMessage(),e);		
			}
		}
	}
	
	
	@Autowired
	public void setAdminManager(AdminManager adminManager) {
		this.adminManager = adminManager;
	}
	
	@Autowired
	public void setPermissionManager(PermissionManager permissionManager) {
		this.permissionManager = permissionManager;
	}
	
	
		
}
