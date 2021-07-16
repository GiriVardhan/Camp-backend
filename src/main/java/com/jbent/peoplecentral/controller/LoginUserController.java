package com.jbent.peoplecentral.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.manager.LoginUserManager;
import com.jbent.peoplecentral.web.SessionContext;

@Controller

public class LoginUserController extends WebApplicationObjectSupport{
	
	@Autowired
	private LoginUserManager loginUserManager;

	@RequestMapping(value={"/user/login","/*/user/login"},method=RequestMethod.GET)	
	public MappingJackson2JsonView loadLoginPage(@RequestParam(value = "error",required = false) String error) {
	// return back to index.jsp
		MappingJackson2JsonView view = new MappingJackson2JsonView();
	        if (error != null) {
	        	view.getAttributesMap().put("error", "Invalid Credentials provided.");
	        }
	       // model.setViewName("login/login");
	        return view;
	}
	
	@RequestMapping(value = {"/user/home","/*/user/home"}, method = RequestMethod.GET)
	public RedirectView dashboard() throws DataException{
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		String client = SessionContext.getSessionContext().getSchemaContext();
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		RedirectView rv = new RedirectView("/app/" + client);
		rv.setExposeModelAttributes(false);
		loginUserManager.setUserInSessionContext();
		
		return rv;
	}
	
	@RequestMapping(value = {"/user/loggedout","/*/user/loggedout"}, method = RequestMethod.GET)
	public RedirectView logout(){
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		String client = SessionContext.getSessionContext().getSchemaContext();
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		RedirectView rv = new RedirectView("/app/" + client+"/user/login");
		rv.setExposeModelAttributes(false);
		return rv;
	}
	
	@RequestMapping(value = {"/swaggerlogin","/*/swaggerlogin"},method = RequestMethod.POST)
	public ModelAndView swaggerLogin(){
		return new ModelAndView("redirect:" + "/app/swagger-ui.html");
	}
	
	@RequestMapping(value = {"/*/swagger-ui.html"},method = RequestMethod.GET)
	public ModelAndView swaggerLogin1(){
		return new ModelAndView("redirect:" + "/app/swagger-ui.html");
	}
	
//	@RequestMapping(value = {"/swagger-ui.html"},method = RequestMethod.POST)
//	public ModelAndView swaggerLogin2(){
//		return new ModelAndView("redirect:" + "/app/swagger-ui.html");
//	}
}
