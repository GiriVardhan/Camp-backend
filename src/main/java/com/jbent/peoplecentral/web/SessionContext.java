/**
 * 
 */
package com.jbent.peoplecentral.web;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jbent.peoplecentral.model.pojo.AttributeFieldType;
import com.jbent.peoplecentral.model.pojo.CompletePermissions;
import com.jbent.peoplecentral.model.pojo.Entity;




/**
 * @author Jason Tesser
 *
 */
public class SessionContext extends ApplicationObjectSupport {
	

	private String schemaContext;
	private String signIn;
	private List<Long> etIdsList = null;
	private String errormsg;
	private AttributeFieldType attributeFieldType;
	private JasperPrint jasperPrint;
	private String showAllRoles;
	private String profileImagePath;
	private Entity userEntity;
	
	

	public String getProfileImagePath() {
		return profileImagePath;
	}

	public void setProfileImagePath(String profileImagePath) {
		this.profileImagePath = profileImagePath;
	}

	private PagedListHolder<CompletePermissions> combinedRoles = null;
	
	

	public static final String SESSION_ATTRIBUTE_NAME_ERRORMSG = "SC";
	public static final String SESSION_ATTRIBUTE_NAME_UNLOCKED_ENITTYPES = "SC";
	public static final String SESSION_ATTRIBUTE_NAME_SIGNED_IN_AS = "SC";
	public static final String SESSION_ATTRIBUTE_NAME = "SC";
	public static final String SESSION_ATTRIBUTE_FIELDTYPE= "SC";
	public static final String SESSION_JASPER_PRINT= "SC";
	public static final String SESSION_SHOW_ALL_ROLES= "SC";
	public static final String SESSION_COMBINED_ROLES = "SC";
	public static final String SESSION_PROFILE_IMAGE = "SC";
	public static final String SESSION_USER_ENTITY = "SC";
	public static final String SESSION_SCHEMA_CONTEXT = "SESSION_SCHEMA_CONTEXT";

	private SessionContext() {}
	
	public static SessionContext getSessionContext(){
		SessionContext signedInAsSC = (SessionContext)getSession().getAttribute(SESSION_ATTRIBUTE_NAME_SIGNED_IN_AS);
		if(signedInAsSC != null){
			getSession().setAttribute(SESSION_ATTRIBUTE_NAME_SIGNED_IN_AS,signedInAsSC);
			return signedInAsSC;
		}
		SessionContext sc = (SessionContext)getSession().getAttribute(SESSION_ATTRIBUTE_NAME);
		if(sc == null){
			sc = new SessionContext();
			getSession().setAttribute(SESSION_ATTRIBUTE_NAME_SIGNED_IN_AS,sc);
		}
		return sc;
	}
	
	/**
	 * Will get and/or create the signed in as Session Context
	 * @return
	 */
	public static SessionContext getSignedInAsSessionContext(){
		SessionContext signedInAsSC = (SessionContext)getSession().getAttribute(SESSION_ATTRIBUTE_NAME_SIGNED_IN_AS);
		if(signedInAsSC != null){
			signedInAsSC.destroySignedInAsSessionContext();
		}
		//signedInAsSC = new SessionContext();
		signedInAsSC.setSignIn("yes");
		getSession().setAttribute(SESSION_ATTRIBUTE_NAME_SIGNED_IN_AS,signedInAsSC);
		return signedInAsSC;
	}
	
	public static SessionContext setSignedOutAsSessionContext(){
		SessionContext signedInAsSC = (SessionContext)getSession().getAttribute(SESSION_ATTRIBUTE_NAME_SIGNED_IN_AS);
		if(signedInAsSC != null){
			signedInAsSC.destroySignedInAsSessionContext();
		}
		//signedInAsSC = new SessionContext();
		signedInAsSC.setSignIn("no");
		getSession().setAttribute(SESSION_ATTRIBUTE_NAME_SIGNED_IN_AS,signedInAsSC);
		return signedInAsSC;
	}
	
	
	
	public void destroySignedInAsSessionContext(){
		getSession().setAttribute(SESSION_ATTRIBUTE_NAME_SIGNED_IN_AS,null);
	}
	
	/**
	 * Will get and/or create the UnlockedEnittyTypes Session Context
	 * @return
	 */
	public static SessionContext setUnlockedEnittyTypesSessionContext(List<Long> etIdsList){
		SessionContext unlockedEnittyTypesSC = (SessionContext)getSession().getAttribute(SESSION_ATTRIBUTE_NAME_UNLOCKED_ENITTYPES);
		if(unlockedEnittyTypesSC != null){
			unlockedEnittyTypesSC.destroyUnlockedEnittyTypesSessionContext();
		}
		//unlockedEnittyTypesSC = new SessionContext();
		unlockedEnittyTypesSC.setEtIdsList(etIdsList);
		getSession().setAttribute(SESSION_ATTRIBUTE_NAME_UNLOCKED_ENITTYPES,unlockedEnittyTypesSC);
		return unlockedEnittyTypesSC;
	}
	
	public static SessionContext getUnlockedEnittyTypesSessionContext(){
		SessionContext unlockedEnittyTypesSC = (SessionContext)getSession().getAttribute(SESSION_ATTRIBUTE_NAME_UNLOCKED_ENITTYPES);
		return unlockedEnittyTypesSC;
	}
	
	public void destroyUnlockedEnittyTypesSessionContext(){
		getSession().setAttribute(SESSION_ATTRIBUTE_NAME_UNLOCKED_ENITTYPES,null);
	}
	
	/**
	 * Will get and/or create the combinedRoles Session Context
	 * @return
	 */
	public static SessionContext setCombinedRolesSessionContext(PagedListHolder<CompletePermissions> combinedRoles){
		SessionContext combinedRolesSC = (SessionContext)getSession().getAttribute(SESSION_COMBINED_ROLES);
		if(combinedRolesSC != null){
			combinedRolesSC.destroyUnlockedEnittyTypesSessionContext();
		}
		//combinedRolesSC = new SessionContext();
		combinedRolesSC.setCombinedRoles(combinedRoles);
		getSession().setAttribute(SESSION_COMBINED_ROLES,combinedRolesSC);
		return combinedRolesSC;
	}
	
	public static SessionContext getCombinedRolesSessionContext(){
		SessionContext combinedRolesSC = (SessionContext)getSession().getAttribute(SESSION_COMBINED_ROLES);
		return combinedRolesSC;
	}
	
	public void destroyCombinedRolesSessionContext(){
		getSession().setAttribute(SESSION_COMBINED_ROLES,null);
	}

	/**
	 * Will get and/or create the Attribute FieldType Session Context
	 * @return
	 */
	
	public static SessionContext setFiedlTypeSessionContext(AttributeFieldType attributeFieldType){
		SessionContext fieldTypeSC = (SessionContext)getSession().getAttribute(SESSION_ATTRIBUTE_FIELDTYPE);
		if(fieldTypeSC != null){
			fieldTypeSC.destroyFieldTypeSessionContext();
		}
		//fieldTypeSC = new SessionContext();
		fieldTypeSC.setAttributeFieldType(attributeFieldType);
		getSession().setAttribute(SESSION_ATTRIBUTE_FIELDTYPE,fieldTypeSC);
		return fieldTypeSC;
	}
	
	public static SessionContext getFieldTypeSessionContext(){
		SessionContext fieldTypeSC = (SessionContext)getSession().getAttribute(SESSION_ATTRIBUTE_FIELDTYPE);
		return fieldTypeSC;
	}
	public void destroyFieldTypeSessionContext(){
		getSession().setAttribute(SESSION_ATTRIBUTE_FIELDTYPE,null);
	}

	/**
	 * Will get and/or create the ErrorMsg Session Context
	 * @return
	 */
	public static SessionContext setErrorMsgSessionContext(String errormsg){
		SessionContext errorMsgSC = (SessionContext)getSession().getAttribute(SESSION_ATTRIBUTE_NAME_ERRORMSG);
		if(errorMsgSC != null){
			errorMsgSC.destroyErrorMsgSessionContext();
		}
		//errorMsgSC = new SessionContext();
		errorMsgSC.setErrormsg(errormsg);
		getSession().setAttribute(SESSION_ATTRIBUTE_NAME_ERRORMSG,errorMsgSC);
		return errorMsgSC;
	}
	
	public static SessionContext getErrorMsgSessionContext(){
		SessionContext errorMsgSC = (SessionContext)getSession().getAttribute(SESSION_ATTRIBUTE_NAME_ERRORMSG);
		return errorMsgSC;
	}
	public void destroyErrorMsgSessionContext(){
		getSession().setAttribute(SESSION_ATTRIBUTE_NAME_ERRORMSG,null);
	}
	
	/**
	 * Will get and/or create the JasperPrint Session Context
	 * @return
	 */
	public static SessionContext setJasperPrintSessionContext(JasperPrint jp){
		SessionContext jasperPrintSC = (SessionContext)getSession().getAttribute(SESSION_JASPER_PRINT);
		if(jasperPrintSC != null){
			jasperPrintSC.destroyJasperPrintSessionContext();
		}
		jasperPrintSC.setJasperPrint(jp);
		getSession().setAttribute(SESSION_JASPER_PRINT,jasperPrintSC);
		return jasperPrintSC;
	}
	
	public static SessionContext getJasperPrintSessionContext(){
		SessionContext jasperPrintSC = (SessionContext)getSession().getAttribute(SESSION_JASPER_PRINT);
		return jasperPrintSC;
	}
	public void destroyJasperPrintSessionContext(){
		getSession().setAttribute(SESSION_JASPER_PRINT,null);
	}
	
	/**
	 * Will get and/or create the Show all roles Session Context
	 * @return
	 */
	public static SessionContext setShowAllRolesSessionContext(String showAllRoles){
		SessionContext showAllRolesSC = (SessionContext)getSession().getAttribute(SESSION_SHOW_ALL_ROLES);
		if(showAllRolesSC != null){
			showAllRolesSC.destroyShowAllRolesSessionContext();
		}
		showAllRolesSC.setShowAllRoles(showAllRoles);
		getSession().setAttribute(SESSION_SHOW_ALL_ROLES,showAllRolesSC);
		return showAllRolesSC;
	}
	
	public static SessionContext getShowAllRolesSessionContext(){
		SessionContext showAllRolesSC = (SessionContext)getSession().getAttribute(SESSION_SHOW_ALL_ROLES);
		return showAllRolesSC;
	}
	public void destroyShowAllRolesSessionContext(){
		getSession().setAttribute(SESSION_SHOW_ALL_ROLES,null);
	}
	
	
	/**
	 * Will get and/or create the Profile Image Session Context
	 * @return
	 */
	public static SessionContext setProfileImageSessionContext(String profileImage){
		SessionContext profileImageSC = (SessionContext)getSession().getAttribute(SESSION_PROFILE_IMAGE);
		if(profileImageSC != null){
			profileImageSC.destroyProfileImageSessionContext();
		}
		//profileImageSC = new SessionContext();
		profileImageSC.setProfileImagePath(profileImage);
		getSession().setAttribute(SESSION_PROFILE_IMAGE,profileImageSC);
		return profileImageSC;
	}
	
	public static SessionContext getProfileImageSessionContext(){
		SessionContext profileImageSC = (SessionContext)getSession().getAttribute(SESSION_PROFILE_IMAGE);
		return profileImageSC;
	}
	public void destroyProfileImageSessionContext(){
		getSession().setAttribute(SESSION_PROFILE_IMAGE,null);
	}

	/**
	 * Will get and/or create the USER ENTITY Session Context
	 * @return
	 */
	public static SessionContext setUserEntitySessionContext(Entity userEntity){
		SessionContext userEntitySC = (SessionContext)getSession().getAttribute(SESSION_USER_ENTITY);
		if(userEntitySC != null){
			userEntitySC.destroyUserEntitySessionContext();
		}
		//userEntitySC = new SessionContext();
		userEntitySC.setUserEntity(userEntity);
		getSession().setAttribute(SESSION_USER_ENTITY,userEntitySC);
		return userEntitySC;
	}
	
	public static SessionContext getUserEntitySessionContext(){
		SessionContext userEntitySC = (SessionContext)getSession().getAttribute(SESSION_USER_ENTITY);
		return userEntitySC;
	}
	public void destroyUserEntitySessionContext(){
		getSession().setAttribute(SESSION_USER_ENTITY,null);
	}

	
	/**
	 * Destory Session Context
	 * @return
	 */

	public void destroySessionContext(){
		SessionContext signedInAsSC = (SessionContext)getSession().getAttribute(SESSION_ATTRIBUTE_NAME_SIGNED_IN_AS);
		SessionContext unlockedEnittyTypesSC = (SessionContext)getSession().getAttribute(SESSION_ATTRIBUTE_NAME_UNLOCKED_ENITTYPES);
		SessionContext errorMsgSC = (SessionContext)getSession().getAttribute(SESSION_ATTRIBUTE_NAME_ERRORMSG);
		SessionContext jasperPrintSC = (SessionContext)getSession().getAttribute(SESSION_JASPER_PRINT);
		SessionContext showAllRolesSC = (SessionContext)getSession().getAttribute(SESSION_SHOW_ALL_ROLES);
		SessionContext combinedRolesSC = (SessionContext)getSession().getAttribute(SESSION_COMBINED_ROLES);
		SessionContext profileImageSC = (SessionContext)getSession().getAttribute(SESSION_PROFILE_IMAGE);
		SessionContext userEntitySC = (SessionContext)getSession().getAttribute(SESSION_USER_ENTITY);
		
		if(signedInAsSC.getSignIn()!= null){
			destroySignedInAsSessionContext();
		}
		if(unlockedEnittyTypesSC.getEtIdsList()!= null){
			destroyUnlockedEnittyTypesSessionContext();
		}
		if(errorMsgSC.getErrormsg()!= null){
			destroyErrorMsgSessionContext();
		}
		if(jasperPrintSC.getJasperPrint()!= null){
			destroyJasperPrintSessionContext();
		}
		if(showAllRolesSC.getShowAllRoles()!= null){
			destroyShowAllRolesSessionContext();
		}
		if(combinedRolesSC.getSignIn()!= null){
			destroyCombinedRolesSessionContext();
		}
		if(profileImageSC.getProfileImagePath()!= null){
			destroyProfileImageSessionContext();
		}
		if(userEntitySC.getUserEntity()!= null){
			destroyUserEntitySessionContext();
		}
		getSession().setAttribute(SESSION_ATTRIBUTE_NAME,null);
		getSession().invalidate();
	}
	
	/**
	 * @param schemaContext the schemaContext to set
	 */
	public void setSchemaContext(String schemaContext) {
		getSession().setAttribute(SESSION_SCHEMA_CONTEXT,schemaContext);
	}

	/**
	 * @return the schemaContext
	 */
	public String getSchemaContext() {
		 return (String) getSession().getAttribute(SESSION_SCHEMA_CONTEXT);
	}
	
	
	public String getSignIn() {
		return signIn;
	}

	public void setSignIn(String signIn) {
		this.signIn = signIn;
	}
	
	public List<Long> getEtIdsList() {
		return etIdsList;
	}

	public void setEtIdsList(List<Long> etIdsList) {
		this.etIdsList = etIdsList;
	}
	
	public String getErrormsg() {
		return errormsg;
	}

	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	public AttributeFieldType getAttributeFieldType() {
		return attributeFieldType;
	}
	
	public void setAttributeFieldType(AttributeFieldType attributeFieldType) {
		this.attributeFieldType = attributeFieldType;
	}
	
	
	public JasperPrint getJasperPrint() {
		return jasperPrint;
	}

	public void setJasperPrint(JasperPrint jasperPrint) {
		this.jasperPrint = jasperPrint;
	}
	/**
	 * @return the showAllRoles
	 */
	public String getShowAllRoles() {
		return showAllRoles;
	}
	
	/**
	 * @return the Profile Image Path
	 
	public String getProfileImage() {
		return profileImagePath;
	}

	public void setProfileImage(String profileImagePath) {
		this.profileImagePath = profileImagePath;
	}*/
	

	/**
	 * @return the combinedRoles
	 */
	public PagedListHolder<CompletePermissions> getCombinedRoles() {
		return combinedRoles;
	}

	/**
	 * @param combinedRoles the combinedRoles to set
	 */
	public void setCombinedRoles(PagedListHolder<CompletePermissions> combinedRoles) {
		this.combinedRoles = combinedRoles;
	}

	/**
	 * @param showAllRoles the showAllRoles to set
	 */
	public void setShowAllRoles(String showAllRoles) {
		this.showAllRoles = showAllRoles;
	}
	public static HttpSession getSession() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession();
	}
	
	public static HttpServletRequest getRequest() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest();
	}
	
	public Entity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(Entity userEntity) {
		this.userEntity = userEntity;
	}
	
}
