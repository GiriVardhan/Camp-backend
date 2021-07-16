package com.jbent.peoplecentral.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.jbent.peoplecentral.web.SessionContext;

@Controller
public class HTTPErrorController extends WebApplicationObjectSupport{
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value = {"/errors/400.html","/*/errors/400.html"})
    public MappingJackson2JsonView handle400(Locale locale) {
		MappingJackson2JsonView view = new MappingJackson2JsonView();	
		view.getAttributesMap().put("errorMsg", messageSource.getMessage("error.http.400", null, locale));
       return view;
    }
	
	@RequestMapping(value = {"/errors/401.html","/*/errors/401.html"})
    public MappingJackson2JsonView handle401(Locale locale) {
		MappingJackson2JsonView view = new MappingJackson2JsonView();	
		SessionContext.getSessionContext().getSchemaContext();
		view.getAttributesMap().put("client", SessionContext.getSessionContext().getSchemaContext());
		view.getAttributesMap().put("errorMsg", messageSource.getMessage("error.http.401", null, locale));
       return view;
    }

//	@RequestMapping(value = {"/errors/404.html","/*/errors/404.html"})
//    public String handle404(Model model,Locale locale) {
//		SessionContext.getSessionContext().getSchemaContext();
//		model.addAttribute("client", SessionContext.getSessionContext().getSchemaContext());
//		model.addAttribute("errorMsg", messageSource.getMessage("error.http.404", null, locale));
//       return "error";
//    }
	@RequestMapping(value = {"/errors/405.html","/*/errors/405.html"})
    public MappingJackson2JsonView handle405(Locale locale) {
		MappingJackson2JsonView view = new MappingJackson2JsonView();	
		view.getAttributesMap().put("errorMsg", messageSource.getMessage("error.http.405", null, locale));
        return view;
    }
	
	@RequestMapping(value = {"/errors/408.html","/*/errors/408.html"})
    public MappingJackson2JsonView handle408(Locale locale) {
		MappingJackson2JsonView view = new MappingJackson2JsonView();	
		view.getAttributesMap().put("errorMsg", messageSource.getMessage("error.http.408", null, locale));
        return view;
    }

	@RequestMapping(value = {"/errors/500.html","/*/errors/500.html"})
    public MappingJackson2JsonView handle500(Locale locale) {
		MappingJackson2JsonView view = new MappingJackson2JsonView();	
		view.getAttributesMap().put("errorMsg", messageSource.getMessage("error.http.500", null, locale));
        return view;
    }

}
