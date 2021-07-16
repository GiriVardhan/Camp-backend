/**
 * 
 */
package com.jbent.peoplecentral.model.manager;

import java.io.File;
import java.util.List;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.pojo.AttributeFileStorage;



/**
 * @author RaviT
 *
 */
public interface SocialManager {
	
	//@Transactional(rollbackFor = Exception.class)
	public String getFacebookURL(String postOn) throws DataException;
	public Facebook getFacebookConnection(String authorizationCode, String state,String postOn) throws DataException;
	public Facebook getPageConnection(String authorizationCode, String state,String postOn) throws DataException;
	
	
	public Twitter getTwitterConnection(String postOn) throws DataException;
	
		
	
}
