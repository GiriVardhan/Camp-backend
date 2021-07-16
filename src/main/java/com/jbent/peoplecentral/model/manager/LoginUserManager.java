/**
 * 
 */
package com.jbent.peoplecentral.model.manager;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;

/**
 * @author PrasadBHVN & Santosh
 *
 */
public interface LoginUserManager {

	public void setProfileImagePath() throws DataException;	
	public void setUserInSessionContext() throws DataException;
	
}
