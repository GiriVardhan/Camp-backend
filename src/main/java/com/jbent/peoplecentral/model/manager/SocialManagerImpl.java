/**
 *
 */
package com.jbent.peoplecentral.model.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

import com.jbent.peoplecentral.exception.AffiliateAPIException;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.dao.EntityDAO;
import com.jbent.peoplecentral.model.pojo.Entity;
import com.jbent.peoplecentral.permission.PermissionManager;
import com.jbent.peoplecentral.util.WebKeys;


/**
 * @author RaviT
 *
 */
public class SocialManagerImpl extends ApplicationObjectSupport implements SocialManager, InitializingBean {

	@Autowired
	private EntityDAO entityDAO; 
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private PermissionManager permissionManager;
	@Value("${facebook.callback}")
	private String facebookCallback;
	@Value("${twitter.callback}")
	private String twitterCallback;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getFacebookURL(String postOn)	throws DataException {
		// TODO Auto-generated method stub
		OAuth2Parameters oAuth2Parameters;
		Entity socialUser = loadSocialConfigDetails(postOn);
		FacebookConnectionFactory connectionFactory = configureUserFacebookConnection(socialUser);
			OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
		oAuth2Parameters = new OAuth2Parameters();
		oAuth2Parameters.setRedirectUri(facebookCallback);
		oAuth2Parameters.setState("cocoaOwlfacebook"+permissionManager.getUsername()+"token");
		oAuth2Parameters.setScope("manage_pages, publish_pages,publish_actions");
		String authorizeUrl = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);
	
		return authorizeUrl;
	}
	
	public Facebook getFacebookConnection(String authorizationCode, String state,String postOn) throws DataException{
		Entity socialUser = loadSocialConfigDetails(postOn);
		FacebookConnectionFactory connectionFactory = configureUserFacebookConnection(socialUser);
		OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
		AccessGrant accessGrant = oauthOperations.exchangeForAccess(authorizationCode, facebookCallback, null);
		Connection<Facebook> connection = connectionFactory.createConnection(accessGrant);
		Facebook facebook = connection != null ? connection.getApi() : new FacebookTemplate(null);
		return facebook;
	}
	
	public Facebook getPageConnection(String authorizationCode, String state,String postOn) throws DataException{
		Facebook facebookPage = null;
		Entity socialUser = loadSocialConfigDetails(postOn);
		FacebookConnectionFactory connectionFactory = configureUserFacebookConnection(socialUser);
		OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
		AccessGrant accessGrant = oauthOperations.exchangeForAccess(authorizationCode, facebookCallback, null);
		//Get CompayPage Connection
		String pageAccessToken;
		try {
			pageAccessToken = getPageAccessToken(socialUser,accessGrant.getAccessToken());
			facebookPage = new FacebookTemplate(pageAccessToken);
		} catch (AffiliateAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DataException("SocialMangerImpl:getCompanyPageConnection:"+e.getMessage());
		}
		return facebookPage;
	}	
		
	public Twitter getTwitterConnection(String postOn) throws DataException{
		Entity socialUserEntity = loadSocialConfigDetails(postOn);
		Twitter twitter = null;
		try{			
			String consumerKey = entityManager.getAttributeValue(socialUserEntity.getAttributeValueStorage(), WebKeys.TWITTER_CONSUMER);
			String consumerSecret = entityManager.getAttributeValue(socialUserEntity.getAttributeValueStorage(), WebKeys.TWITTER_CONSUMER_SECRET);
			String accessToken = entityManager.getAttributeValue(socialUserEntity.getAttributeValueStorage(), WebKeys.TWITTER_ACCESS);
			String accessTokenSecret = entityManager.getAttributeValue(socialUserEntity.getAttributeValueStorage(), WebKeys.TWITTER_ACCESS_SECRET);
			twitter = new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
		} catch (DataException e) {
			logger.error("Error in SocialMangerImpl:getTwitterConnection:"+e.getStackTrace());
			throw new DataException("SocialMangerImpl:getTwitterConnection:"+e.getMessage());
		}		
		return twitter;
	}	
	
	private FacebookConnectionFactory configureUserFacebookConnection(Entity socialUserEntity) throws DataException{
		FacebookConnectionFactory connectionFactory=null;
		
		try {
			
			String appId = entityManager.getAttributeValue(socialUserEntity.getAttributeValueStorage(), WebKeys.FACEBOOK_APPID);
			String appSecreate = entityManager.getAttributeValue(socialUserEntity.getAttributeValueStorage(), WebKeys.FACEBOOK_APP_SECRET);
			//connectionFactory =   new FacebookConnectionFactory("1526226294370096", "a0ef811dc2d8e79b4abf823528d4cd4d");
			connectionFactory =   new FacebookConnectionFactory(appId, appSecreate);
		  
		} catch (DataException e) {
			logger.error("Error in SocialMangerImpl:configureUserFacebookConnection:"+e.getStackTrace());
			throw new DataException("SocialMangerImpl:configureUserFacebookConnection:"+e.getMessage());
		}
		 return connectionFactory;
	}
	
	private Entity loadSocialConfigDetails(String postOn){
		Entity loginUserEnt =null;
		try {
				// 	load Login User Entity
				loginUserEnt = permissionManager.loadLoginUserEntity();
				
				if(postOn.equalsIgnoreCase(WebKeys.COMPANY)){
					//load Client entity
					String clientName = loginUserEnt.getMod_user();
					loginUserEnt = entityManager.loadEntityByUsername(clientName);
				}
					  
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loginUserEnt;
	}
	
	private String getPageAccessToken(Entity socailUser, String shortTermUserToken) throws AffiliateAPIException{
			
			try {
			   	    String pageId = entityManager.getAttributeValue(socailUser.getAttributeValueStorage(), WebKeys.FACEBOOK_PAGEID);
			   	    String urlString = "https://graph.facebook.com/me/accounts?access_token="+shortTermUserToken;
					
		            URL url = new URL(urlString);
		            HttpURLConnection con = (HttpURLConnection) url.openConnection();
		            con.setRequestMethod("GET");
		          
		            int status = con.getResponseCode();

		            switch(status) {

		                case HttpURLConnection.HTTP_GONE:
		                    // The timestamp is expired.
		                    throw new AffiliateAPIException("URL expired");

		                case HttpURLConnection.HTTP_UNAUTHORIZED:
		                    // The API Token or the Tracking ID is invalid.
		                    throw new AffiliateAPIException("API Token or Affiliate Tracking ID invalid.");

		                case HttpURLConnection.HTTP_FORBIDDEN:
		                    // Tampered URL, i.e., there is a signature mismatch.
		                    // The URL contents are modified from the originally returned value.
		                    throw new AffiliateAPIException("Tampered URL - The URL contents are modified from the originally returned value");

		                case HttpURLConnection.HTTP_OK:

		                    BufferedReader in = new BufferedReader(
		                            new InputStreamReader(con.getInputStream()));
		                    String inputLine;
		                    StringBuffer response = new StringBuffer();

		                    while ((inputLine = in.readLine()) != null) {
		                        response.append(inputLine);
		                    }
		                    in.close();
		                    //System.out.println(response.toString());
		                    JSONObject obj = new JSONObject(response.toString());
		                    JSONArray accessTokenArray = obj.getJSONArray("data");
		                    String pageAccessToken="";
		                    for(int i =0; i < accessTokenArray.length(); i++) {
		                    	String page_id = accessTokenArray.getJSONObject(i).getString("id");
		                    	if(page_id.equalsIgnoreCase(pageId)){
		                    		pageAccessToken = accessTokenArray.getJSONObject(i).getString("access_token");
		                    	 	return pageAccessToken;
		                    	}
		                    }
		                   
		                   

		                default:
		                    throw new AffiliateAPIException("Connection error with the Affiliate API service: HTTP/" + status);
		            }
		        }
		        catch(MalformedURLException mfe) {
		        }
		        catch(IOException ioe) {
		        } catch (DataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		        return "";
		}
	
	
}