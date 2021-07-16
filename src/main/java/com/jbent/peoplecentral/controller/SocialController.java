package com.jbent.peoplecentral.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookLink;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.twitter.api.TweetData;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.manager.SocialManager;
import com.jbent.peoplecentral.util.FileUploadHelperUtil;
import com.jbent.peoplecentral.web.SessionContext;

@Controller

public class SocialController extends WebApplicationObjectSupport {

 	//@Autowired
	//private ConnectionFactoryRegistry  connectionFactoryRegistry;
 	@Autowired
 	private FileUploadHelperUtil fileUploadHelpUtil;
 	@Autowired
	private SocialManager  socialManager;
	
 	@Value("${twitter.callback}")
	private String twitterCallback;
	@Value("${facebook.callback}")
	private String facebookCallback;	
	
	private String DESCRIPTION;
	private String IMAGEPATH;
	private String ENTITYID;
	private String POSTON;
	@RequestMapping(value = {"/entity/facebook","/*/entity/facebook"}, method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView signinFaceBook(HttpServletRequest request,
			@RequestParam("sDesc") String sDesc,
			@RequestParam("sImg") String sImg,
			@RequestParam("sEntityId") String sEntityId,
		  @RequestParam("sPostOn") String postOn,	
		 HttpServletResponse response) throws Exception {
		String serverPath = fileUploadHelpUtil.getServerPath();
		POSTON = postOn;
		DESCRIPTION = sDesc;
		if(sImg != "")
			IMAGEPATH = "http://localhost:8080" + sImg;
		
		ENTITYID = sEntityId;	
		String authorizeUrl =   socialManager.getFacebookURL(POSTON);
		RedirectView redirectView = new RedirectView(authorizeUrl);
		return new ModelAndView(redirectView);
	}
	
	@RequestMapping(value = {"/facebook/callback","/*/facebook/callback"}, method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView postOnWallFacebook(@RequestParam("code") String authorizationCode,
			@RequestParam("state") String state, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		try{
			Facebook facebook = null;
			if(POSTON.equalsIgnoreCase("MINE")){
				// get UserFacebook Connection
				facebook =   socialManager.getFacebookConnection(authorizationCode,state,POSTON);
			}else{
				//Company Page Connection
				facebook =   socialManager.getPageConnection(authorizationCode,state,POSTON);
			}
			if(IMAGEPATH != "" && IMAGEPATH != null){
				//Resource  resource = new FileSystemResource(IMAGEPATH);
				// Not working for URL http://localhost:8080/clients/images/testclient/7224555/9/5/9/9596/bell5.jpg
				FacebookLink link = new FacebookLink("https://pbs.twimg.com/profile_images/638751551457103872/KN-NzuRl.png", 
				        "Spring Social", 
				        "The Spring Social Project", 
				        "Spring Social is an extension to Spring to enable applications to connect with service providers.");
				facebook.feedOperations().postLink("I'm trying out Spring Social!", link);
			}else{	
				facebook.feedOperations().updateStatus(DESCRIPTION);
			}	
			IMAGEPATH = "";
			session.setAttribute("postStatus","Success");
		}catch (Exception e){
			logger.error("Error in SocialController:postOnWallFacebook:"+e.getStackTrace());
			session.setAttribute("postStatus","Failed");
		}
		return new ModelAndView("redirect:/app/"+ClientManageUtil.loadClientSchema()+"/entity/view/"+ENTITYID);
	}
	
	
	
	@RequestMapping(value = {"/entity/twitter","/*/entity/twitter"}, method = RequestMethod.GET)
	public ModelAndView signinTwitter(HttpServletRequest request,
			@RequestParam("sDesc") String sDesc,
			@RequestParam("sImg") String sImg,
			@RequestParam("sEntityId") String sEntityId,
			@RequestParam("sPostOn") String sPostOn,
			HttpServletResponse response, HttpSession session) throws Exception {
		try{
			String serverPath = fileUploadHelpUtil.getServerPath();
			DESCRIPTION = sDesc;
			if(sImg != "")
				IMAGEPATH = serverPath + sImg;
			ENTITYID = sEntityId;	
			POSTON = sPostOn;
			
			Twitter twitter = socialManager.getTwitterConnection(POSTON);
			
			TweetData td = new TweetData(DESCRIPTION);
			if(IMAGEPATH != "" && IMAGEPATH != null){
				Resource  resource = new FileSystemResource(IMAGEPATH);
				td.withMedia(resource);
			}		
			twitter.timelineOperations().updateStatus(td);
			IMAGEPATH = "";
			session.setAttribute("postStatus","Success");
		}catch (Exception e){
			logger.error("Error in SocialController:signinTwitter:"+e.getStackTrace());
			session.setAttribute("postStatus","Failed");
		}
		return new ModelAndView("redirect:/app/"+ClientManageUtil.loadClientSchema()+"/entity/view/"+ENTITYID);
	}
}