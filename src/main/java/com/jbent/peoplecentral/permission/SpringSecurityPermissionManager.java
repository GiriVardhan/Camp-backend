/**
 * 
 */
/**
 * 
 */
package com.jbent.peoplecentral.permission;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.switchuser.SwitchUserGrantedAuthority;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.jbent.peoplecentral.exception.AttributeValueNotValidException;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.dao.AdminDAO;
import com.jbent.peoplecentral.model.dao.EntityDAO;
import com.jbent.peoplecentral.model.dao.EntityTypeDAO;
import com.jbent.peoplecentral.model.manager.EntityManager;
import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;
import com.jbent.peoplecentral.model.pojo.CompletePermissions;
import com.jbent.peoplecentral.model.pojo.Entity;
import com.jbent.peoplecentral.model.pojo.EntityStatus;
import com.jbent.peoplecentral.model.pojo.EntityType;
import com.jbent.peoplecentral.model.pojo.ImportEntity;
import com.jbent.peoplecentral.model.pojo.Attribute.Attributes;
import com.jbent.peoplecentral.model.pojo.EntityType.EntityTypes;
import com.jbent.peoplecentral.postgres.PGAttributeValueStorage;
import com.jbent.peoplecentral.postgres.PGEntity;
import com.jbent.peoplecentral.security.jwt.JwtUser;
import com.jbent.peoplecentral.security.jwt.auth.JwtAuthenticationRequest;

/**
 * @author jasontesser
 * 
 */
@SuppressWarnings("unchecked")
public class SpringSecurityPermissionManager extends ApplicationObjectSupport
		implements PermissionManager, InitializingBean {

	private JdbcMutableAclService aclService;
	private MutableAcl updatedAcl,createdAcl;
	private JdbcUserDetailsManager jdbcUserDetailsManager;
	@Autowired
	private SpringSecurityCustomAclService customAclService;
	private AuthenticationManager authManager;
	private TransactionTemplate tt;
	@Autowired
	private EntityDAO entityDAO;
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private AdminDAO adminDAO;
	
	@Autowired
	private EntityTypeDAO entityTypeDAO;
	  

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(entityDAO, "entityDAO is null");
		Assert.notNull(adminDAO, "adminDAO is null");
		Assert.notNull(aclService, "aclService is null");
		Assert.notNull(jdbcUserDetailsManager,"jdbcUserDetailsManager is null");
		Assert.notNull(tt, "platformTransactionManager required");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jbent.peoplecentral.permission.PermissionManager#addPermission(com
	 * .jbent.peoplecentral.permission.Permissionable)
	 */
	
	 /*
	  * Permission for Entity Type
	  * */
	
	public boolean permissionHandler(Permissionable permissionable,String role,String permission) throws DataException{
		boolean status = false;
		Sid  recipent = null;
		String deSanRole; 
		deSanRole = deSanitizeRole(role);
		recipent = new GrantedAuthoritySid(deSanRole);
		if(permission.equalsIgnoreCase("admin"))
			status = grantPermission(permissionable, null,BasePermission.ADMINISTRATION,recipent);
		else if(permission.equalsIgnoreCase("edit"))
			status = grantPermission(permissionable, BasePermission.ADMINISTRATION,BasePermission.WRITE,recipent);
		else if(permission.equalsIgnoreCase("view"))
			status = grantPermission(permissionable, BasePermission.WRITE,BasePermission.READ,recipent);
		else if(permission.equalsIgnoreCase("none"))
			status = takeOffRolePermission(permissionable, BasePermission.READ,null,recipent);
		else if(permission.equalsIgnoreCase("true"))
			status = grantPermission(permissionable, null,BasePermission.CREATE,recipent);
		else if(permission.equalsIgnoreCase("false")){
			status = takeOffRolePermission(permissionable, BasePermission.CREATE,null,recipent);
		}
		else if(permission.equalsIgnoreCase("remove")){
			status = takeOffAllRolePermissions(permissionable, null,null,recipent);
		}
		else if(permission.equalsIgnoreCase("removefromchildren")){
			status = removeRoleFromAllChildren(permissionable, null,null,recipent);
		}
		return status;
	}

	private boolean grantPermission(Permissionable permissionable,org.springframework.security.acls.model.Permission oldPermission,org.springframework.security.acls.model.Permission newPermission,Sid recipient) throws DataException{
		MutableAcl acl = null,aceCreate=null,updatedAcl=null;
		final ObjectIdentity oid = new ObjectIdentityImpl(permissionable.getPermissionType(), permissionable.getPermissionId());
		try {
			acl = (MutableAcl) aclService.readAclById(oid);
			// Check acl is already exist
			if(acl != null){
				// modify the permission
				return modifyPermission(acl,permissionable,oldPermission,newPermission,recipient);
			}	
		} catch (NotFoundException nfe) {
			// if NOT create a new Acl 
			acl = createACL(oid);
		}	
		try{
			if(acl != null){
				aceCreate = insertACE(acl,newPermission,recipient);
				if(aceCreate != null){
					aceCreate.setOwner(new PrincipalSid(getUsername()));
					updatedAcl= updateACL(aceCreate);
				}
			}
		}catch(DataException e){
			logger.error("grantPermission:Error while granting permission " + newPermission + " for Sid "	+recipient + " " + permissionable.getPermissionType()
					+ ":" + permissionable.getPermissionId());
			throw new DataException("grantPermission:Error while granting permission " + newPermission + " for Sid "	+recipient + " " + permissionable.getPermissionType()
					+ ":" + permissionable.getPermissionId());
		}
		if(updatedAcl != null){
			logger.debug("grantPermission:Added permission - success " + newPermission + " for Sid "	+recipient + " " + permissionable.getPermissionType()
					+ ":" + permissionable.getPermissionId());
		}
		return true;
	}
	
	/* 
	 * takeOff a permission  for a Sid
	 */
	
	public boolean takeOffRolePermission(Permissionable permissionable,org.springframework.security.acls.model.Permission oldPermission,org.springframework.security.acls.model.Permission newPermission,Sid recipient) throws DataException{
		MutableAcl updatedAcl = null,acl = null;
		int aceIndex;
		List<AccessControlEntry> aceList = null;
		final ObjectIdentity oid = new ObjectIdentityImpl(permissionable.getPermissionType(), permissionable.getPermissionId());
		try {
			acl = (MutableAcl) aclService.readAclById(oid);
			if(acl != null){
				aceList = acl.getEntries();
				aceIndex = indexACE(aceList,recipient,oldPermission);		
				//---Delete one Permission------
				if(newPermission == null && aceIndex >= 0){
					// delete the one permission	
					acl = takeOffPermission(acl,aceIndex);
					updatedAcl= updateACL(acl);
				}
				
			}
		} catch (NotFoundException nfe) {
			logger.error(nfe.getMessage(),nfe);
			throw new DataException("takeOffRolePermission:Unable to takeOffRolePermission:ACL Not Found " + oldPermission + " for Sid "	+recipient + " " + permissionable.getPermissionType()
					+ ":" + permissionable.getPermissionId());

		} catch (DataException e) {
			logger.error("takeOffRolePermission:Error while takeOffRolePermission " + oldPermission + " for Sid "	+recipient + " " + permissionable.getPermissionType()
					+ ":" + permissionable.getPermissionId());
			throw new DataException("takeOffRolePermission:Error while takeOffRolePermission " + oldPermission + " for Sid "	+recipient + " " + permissionable.getPermissionType()
					+ ":" + permissionable.getPermissionId());
		}
		if(updatedAcl != null){
			logger.debug("takeOffRolePermission: success " + oldPermission + " for Sid "	+recipient + " " + permissionable.getPermissionType()
				+ ":" + permissionable.getPermissionId());
		}	
		return true;
	}
	
	/* 
	 * Remove All Permissions for a SID
	 */
	
	public boolean takeOffAllRolePermissions(Permissionable permissionable,org.springframework.security.acls.model.Permission oldPermission,org.springframework.security.acls.model.Permission newPermission,Sid recipient) throws DataException{
		MutableAcl acl = null;
		List<AccessControlEntry> aceList = null;
		final ObjectIdentity oid = new ObjectIdentityImpl(permissionable.getPermissionType(), permissionable.getPermissionId());
		try {
			acl = (MutableAcl) aclService.readAclById(oid);
			if(acl != null){
				aceList = acl.getEntries();	
				if(newPermission == null){
					//Remove All Permissions for a SID
					for(AccessControlEntry entrie:aceList){
						if (entrie.getSid().equals(recipient)) 
							takeOffRolePermission(permissionable,entrie.getPermission(),newPermission,recipient);
					}
				}
			}
		} catch (NotFoundException nfe) {
			logger.error(nfe.getMessage(),nfe);
			throw new DataException("takeOffAllRolePermissions:Unable to takeOffAllRolePermissions:ACL Not Found " + oldPermission + " for Sid "	+recipient + " " + permissionable.getPermissionType()
					+ ":" + permissionable.getPermissionId());
		} catch (DataException e) {
			logger.error("takeOffAllRolePermissions:Error while takeOffAllRolePermissions " +e);
			throw new DataException("takeOffAllRolePermissions:Error while takeOffAllRolePermissions: " + oldPermission + " for Sid "	+recipient + " " + permissionable.getPermissionType()
					+ ":" + permissionable.getPermissionId());
		}
		return true;
	}

	public boolean removeRoleFromAllChildren(Permissionable permissionable,org.springframework.security.acls.model.Permission oldPermission,org.springframework.security.acls.model.Permission newPermission,Sid recipient) throws DataException {
		
		MutableAcl entityTypeAcl = null,childEntityacl = null;
		List<AccessControlEntry> aceChildList = null,aceEntityTypeList = null;
		List<ObjectIdentity> childEntities = null;
		final ObjectIdentity oid = new ObjectIdentityImpl(permissionable.getPermissionType(), permissionable.getPermissionId());
		try{
			entityTypeAcl = (MutableAcl) aclService.readAclById(oid);
			childEntities =aclService.findChildren(oid);
			
			// Remove the Role from EntityType
			if(entityTypeAcl != null){
				aceEntityTypeList = entityTypeAcl.getEntries();
				if(aceEntityTypeList!=null && aceEntityTypeList.size()>0){
					if(newPermission == null){
						for(AccessControlEntry entrie:aceEntityTypeList){
							if (entrie.getSid().equals(recipient)) 
								takeOffRolePermission(permissionable,entrie.getPermission(),newPermission,recipient);
						}
					}
				}
			}
			// Remove the Role from all children Entities
			if(childEntities!=null && childEntities.size()>0){
				for(ObjectIdentity childEntity:childEntities){
					childEntityacl = (MutableAcl) aclService.readAclById(childEntity);
					aceChildList = childEntityacl.getEntries();
					//	---Delete one Permission------
					if(newPermission == null){
						for(AccessControlEntry entrie:aceChildList){
							if (entrie.getSid().equals(recipient)) 
								takeOffRolefromChildEntites(childEntity,entrie.getPermission(),newPermission,recipient);
						}
					}
				}
			}

		}catch (NotFoundException nfe) {
			logger.error(nfe.getMessage(),nfe); 
			throw new DataException("removeRoleFromAllChildren:Unable to removeRoleFromAllChildren:ACL Not Found " + oldPermission + " for Sid "	+recipient + " " + permissionable.getPermissionType()
					+ ":" + permissionable.getPermissionId());
		}
		catch(DataException e){
			logger.error("removeRoleFromAllChildren:Error while removeRoleFromAllChildren " +e);
			throw new DataException("removeRoleFromAllChildren:Error while removeRoleFromAllChildren: " + oldPermission + " for Sid "	+recipient + " " + permissionable.getPermissionType()
					+ ":" + permissionable.getPermissionId());

		}
		logger.debug("removed permission " + newPermission + " for Sid "	+ getUsername() + " " + permissionable.getPermissionType()
				+ ":" + permissionable.getPermissionId());	
		return true;
	}

	private void takeOffRolefromChildEntites(ObjectIdentity oid,org.springframework.security.acls.model.Permission oldPermission,org.springframework.security.acls.model.Permission newPermission,Sid recipient) throws DataException{
		MutableAcl acl = null;
		int aceIndex;
		
		List<AccessControlEntry> aceList = null;
		try {
			acl = (MutableAcl) aclService.readAclById(oid);
			if(acl != null){
				aceList = acl.getEntries();
				aceIndex = indexACE(aceList,recipient,oldPermission);		
				//---Delete one Permission------
				if(newPermission == null && aceIndex>=0){
					// delete the one permission	
					acl = takeOffPermission(acl,aceIndex);
					updateACL(acl);
				}
			}
		} catch (NotFoundException nfe) {
			logger.error(nfe.getMessage(),nfe); 
			throw new DataException("takeOffRolefromChildEntites:Unable to takeOffRolefromChildEntites:ACL Not Found " + oldPermission + " for Sid "	+recipient + " " );
		} catch (DataException e) {
			logger.error("takeOffRolefromChildEntites:Error while takeOffRolefromChildEntites " +e);
			throw new DataException("takeOffRolefromChildEntites:Error while takeOffRolefromChildEntites: " + oldPermission + " for Sid "	+recipient + " " );
		}
		
		logger.debug("Deleted permission " + newPermission + " for Sid "	+ getUsername() + " " + oid.getType()
				+ ":" + oid.getIdentifier());	
	}

	/* 
	 * modify a Permissions for a SID
	 */

	private boolean modifyPermission(final MutableAcl acl,Permissionable permissionable,org.springframework.security.acls.model.Permission oldPermission,org.springframework.security.acls.model.Permission newPermission,Sid recipient)throws DataException {
		MutableAcl updatedAcl = null,aceCreate;
		int aceIndex;
		if(acl != null){
			List<AccessControlEntry>  aceList = acl.getEntries();
			aceIndex = indexACE(aceList,recipient,oldPermission);
			//---Create or Update the Permission-------
			try{
				if(newPermission != null ){
					if( aceIndex < 0 ){
						//create the permission for same ObjectIdentity But different SID
						aceCreate = insertACE(acl,newPermission,recipient);
						updatedAcl = updateACL(aceCreate);
						if(updatedAcl != null){
							logger.debug("grantPermission:Added permission - success " + newPermission + " for Sid "	+recipient + " " + permissionable.getPermissionType()
									+ ":" + permissionable.getPermissionId());
							
						}
					}
					else{
						// 	update the permission	
						acl.updateAce(aceIndex, newPermission);
						updatedAcl= updateACL(acl);
						if(updatedAcl != null){
							logger.debug("modifyPermission:Modified permission - success: " + newPermission + " for Sid "	+ recipient + " " + permissionable.getPermissionType()
									+ ":" + permissionable.getPermissionId());
						}
					}
				}
			}catch (NotFoundException nfe) {
				logger.error(nfe.getMessage(),nfe); 
				throw new DataException("modifyPermission:Unable to modifyPermission:ACL Not Found " + oldPermission + " for Sid "	+recipient + " " );
			} catch (DataException e) {
				logger.debug("modified permission: " + newPermission + " for Sid "	+ getUsername() + " " + permissionable.getPermissionType()
						+ ":" + permissionable.getPermissionId());
				throw new DataException("modified permission:Unable to modifyPermission: " + newPermission + " for Sid "	+ getUsername() + " " + permissionable.getPermissionType()
						+ ":" + permissionable.getPermissionId());

			}
		}
		return true;
	}

	private int indexACE(List<AccessControlEntry> aceList,Sid recipient,org.springframework.security.acls.model.Permission oldPermission) throws DataException{
		int aceIndex = -1;
		if(aceList != null && aceList.size()>0){
			for(AccessControlEntry ace:aceList){				
				if( ace.getSid().equals(recipient) && ace.getPermission().equals(oldPermission))
					aceIndex = aceList.indexOf(ace);
			}
		}
		return aceIndex;
	}

	  /*
	  * grant Edit Permission on Entity to roles marked as assign_at_new
	  */
	 public boolean createPermissionsOnNewEntities(List<ImportEntity> importedEntities) throws DataException {
		 MutableAcl acl = null,parent = null;List<AttributeValueStorage> assingNewRoles= null;	List<ObjectIdentity> allAObjectIdentites = new ArrayList<ObjectIdentity>(); 
		 ObjectIdentity oid;
		 try { 
			 if(importedEntities != null && importedEntities.size()>0){				
				 EntityType entType = importedEntities.get(0).getParent();
				 assingNewRoles = loadAssignNewRoles();
				 parent = fetchAcl(entType);
				 String userName = getUsername();
				 logger.debug("creating ObjectIdentiites..."+getDateTime());
				  for(ImportEntity imptEntity: importedEntities){					 					 
					  oid = createObjectIdentityFor(imptEntity.getEntity(),parent,userName);
					  allAObjectIdentites.add(oid);
					 //createWRITEPermission(acl,assingNewRoles);
				 } 
				  //clearing list to avoid java heap memory problem
				  importedEntities.clear();				  
				  logger.debug("loading All ObjectIdentiites Acls ..."+getDateTime());
				  Map<ObjectIdentity, Acl> acls = aclService.readAclsById(allAObjectIdentites);
				  //clearing list to avoid java heap memory problem
				  allAObjectIdentites.clear();
				  logger.debug("Started creating write permissions..."+getDateTime());				  
				  for (Acl loadedAcl : acls.values()) {
					  acl = (MutableAcl) loadedAcl;
					  acl.setParent(parent);
					  customAclService.updateObjectIdentity(acl);
					  createWRITEPermission(acl,assingNewRoles);
                  }
				  
			 }
			
		 } catch (DataException e) {
			logger.error("createPermissionsOnNewEntities:-Error loading : " + e.getMessage(),e);
			throw new DataException("createPermissionsOnNewEntities:-Error loading : " + e.getMessage(),e);
	     }
		 catch (Exception e) {
				logger.error("createPermissionsOnNewEntities:-Error loading : " + e.getMessage(),e);
				throw new DataException("createPermissionsOnNewEntities:-Error loading : " + e.getMessage(),e);
		}
		 logger.debug("Permissions Completed......in"+getDateTime());
		return true;
	}
	
	private String getDateTime() {
	       DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	       Date date = new Date();
	       return dateFormat.format(date);
	} 
	private ObjectIdentity createObjectIdentityFor(Permissionable entity,MutableAcl parent,String userName) throws DataException{
		return customAclService.createObjectIdentityFor(new ObjectIdentityImpl(entity.getPermissionType(), entity.getPermissionId()));
	}
	
	private boolean createWRITEPermission(MutableAcl acl,List<AttributeValueStorage> assingNewRoles) throws DataException{
		
		try{
			if(acl != null){
				if(assingNewRoles != null && assingNewRoles.size() > 0){
					for(AttributeValueStorage role:assingNewRoles){
						// Granting WRITE permission to the assign at new marked as true roles the User has 							
						acl = insertACE(acl,BasePermission.WRITE,new GrantedAuthoritySid(role.getValueVarchar()));					
					}
					customAclService.createEntries(acl);
				} 
			 }
		}catch(Exception e){
			logger.error("createWRITEPermission:-Error while createWRITEPermission : " + e.getMessage(),e);
			throw new DataException("createWRITEPermission:-Error while createWRITEPermission : " + e.getMessage(),e);
		}
		return true;
	}
	 
	public List<CompletePermissions> fetchPermissionRoles(List<AccessControlEntry> aclEntries,long objId,String roleOn) throws DataException{
		List<CompletePermissions> permissionRoles = new ArrayList<CompletePermissions>();
		CompletePermissions permissionRole;
		boolean isRoleExist = false;
		try{
			if(aclEntries != null && aclEntries.size()>0){
				for(AccessControlEntry aclAce : aclEntries){
					permissionRole = fectchPermissionRole(aclEntries,aclAce.getSid(),objId,roleOn);
		 			//To avoid repeated role names from the Acl_entries table, means ROLE_MANAGER = 3 and ROLE_MANAGER = 4(CREATE)
					isRoleExist = checkRoleExist(permissionRoles,permissionRole);
		 			if(!isRoleExist)
		 				permissionRoles.add(permissionRole);
				}
			}
		}catch(DataException e){
			logger.error("retrievePermisssions:-Error while retrievePermissions : " + e.getMessage(),e);
			throw new DataException("retrievePermisssions:-Error while retrievePermissions : " + e.getMessage(),e);
		}
		if(permissionRoles.size()>0){
			logger.debug("retrievePermisssions:-retrievePermissions:Success  for objcet :" + objId);
 			return permissionRoles;
		}	
 		else{
 			logger.debug("retrievePermisssions:- No Permissions found while retrievePermissions:Success  for objcet :" + objId);
 			return null;
 		}	
	} 
	
	private boolean checkRoleExist(List<CompletePermissions> permissionsList,CompletePermissions permissions) throws DataException{
			for(CompletePermissions completePer:permissionsList){
				if(completePer.getRole().equalsIgnoreCase(permissions.getRole()))
					return true;
			}
		return false;
	}
	
	private CompletePermissions fectchPermissionRole(List<AccessControlEntry> aclEntries,Sid sid,long objId,String roleOn) throws DataException{
		CompletePermissions permissionRole = new CompletePermissions();
		boolean hasPermissionSet = true;
		if(aclEntries != null && aclEntries.size()>0){
			for( AccessControlEntry per : aclEntries){
				if(per.getSid().equals(sid)){
					GrantedAuthoritySid authority = (GrantedAuthoritySid) per.getSid();
					int mask = per.getPermission().getMask();
					// role have only one permission or permission and additional permission(CREATE)
					if(hasPermissionSet){
						String uuid = UUID.randomUUID().toString();
						permissionRole.setId(uuid);
						permissionRole.setObjId(objId);					
						permissionRole.setRole(sanitizeRole(authority.getGrantedAuthority()));
						permissionRole.setRoleOn(roleOn);
					}
					if(mask!=4){
						permissionRole.setPermission(mask);
						hasPermissionSet = false;
					}	
					if(mask==4){
						permissionRole.setAdditionalPermission(mask);
						hasPermissionSet = false;
					}	
				}
			}
		}else{
			logger.error("retrieveSidPermisssions:- No permissions found to retrieve for Sid:"+sid+" ObjectId:"+objId);
		}
		return permissionRole;
	}
	
	public List<CompletePermissions> combinedPermissionRolesAndNoPermissionRoles(List<CompletePermissions> permissionRoles,long objId,String roleOn) throws DataException{
		boolean roleHasPermission=false;
		String roleName;
		List<CompletePermissions> combinedAllRoles = new ArrayList<CompletePermissions>();
		CompletePermissions permission ;
		List<AttributeValueStorage> allRoles = null;
		try {
			allRoles = entityManager.loadRoles();
			if(permissionRoles != null)
				combinedAllRoles.addAll(permissionRoles);
			if(allRoles != null){
				// Sorts the specified list into ascending order, according to the natural ordering of its elements.
				Collections.sort(combinedAllRoles);
				Collections.sort(allRoles);
				
				for(AttributeValueStorage role:allRoles){
					roleName = sanitizeRole(role.getValueVarchar());
					if(!roleName.equalsIgnoreCase("ADMINISTRATOR")){
						// filter the roles which has permissions on this entity
						roleHasPermission = roleHasPermission(roleName,permissionRoles);
						// By default max 10 roles only displaying on permission section.
						if(!roleHasPermission){
							String uuid = UUID.randomUUID().toString();
							permission = new CompletePermissions();
							permission.setId(uuid);	
							permission.setObjId(objId);
							permission.setRole(roleName);
							permission.setRoleOn(roleOn);
							permission.setPermission(0);
							permission.setAdditionalPermission(0);
							combinedAllRoles.add(permission);
						}
					}
				}
			}else{
				logger.error("completeRolesAndPermissions:- NO roles found to prepare CompletePermissions:"+allRoles+" ObjectId:"+objId);
			}
		} catch (DataException e) {
			logger.error("completeRolesAndPermissions:-Unable to load Roles from PermissionManager", e);
			throw new DataException("completeRolesAndPermissions:-Unable to load Roles from PermissionManager", e);
	
		}
	return combinedAllRoles;
	}
	
	private boolean roleHasPermission(String role,List<CompletePermissions> permissionsList) throws DataException{
		if(permissionsList != null && permissionsList.size()>0){
			for(CompletePermissions permissionedRole : permissionsList ){
				if(permissionedRole.getRole().equalsIgnoreCase(role))
					return true;
			}
		}
		return false;
	}

	@Transactional
	public MutableAcl fetchAcl(Permissionable object) throws DataException{
		MutableAcl mutAcl= null;
		ObjectIdentity oid = new ObjectIdentityImpl(object.getPermissionType(), object.getPermissionId());
		try {
			mutAcl = (MutableAcl) aclService.readAclById(oid);
		} catch (NotFoundException nfe) {
			logger.debug("fetchAcl: acl not found "+" for object:"+object.getPermissionType());
			createACL(oid);
			mutAcl = (MutableAcl) aclService.readAclById(oid);
		}
		if(mutAcl != null) logger.debug("fetchAcl: fetching Acl success :"+mutAcl+ " for object:"+object.getPermissionType());
		return mutAcl;
	}

    public void deletePermission(Permissionable permissionable)throws DataException {
    
    	// Delete the ACL information as well. Executes while doing delete operations on EntityType and attributes
    	final ObjectIdentity oid = new ObjectIdentityImpl(permissionable.getPermissionType(), permissionable.getPermissionId());
    	try {
			deleteACL(oid);
		} catch (DataException e) {
			logger.error("deletePermission:-Unable to delete Permission for :"+ permissionable.getPermissionId()+":"+permissionable.getPermissionType());
			throw new DataException("deletePermission:-Unable to delete Permission for :"+ permissionable.getPermissionId()+":"+permissionable.getPermissionType());
		}
    	//aclService.deleteAcl(oid, true);
    	if (logger.isDebugEnabled()) {
           logger.debug("Deleted " + permissionable.getPermissionType() + " including ACL permissions");
	    }
	}

	public String getUsername() throws DataException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getPrincipal() instanceof UserDetails) {			
			return ((UserDetails) auth.getPrincipal()).getUsername();
		} else {
			return auth.getPrincipal().toString();
		}
	}
	public String getPreviousUser() throws DataException {
		String previousUser = null;
		//previousUser = ((SwitchUserGrantedAuthority)((List)SecurityContextHolder.getContext().getAuthentication().getAuthorities()).get(1)).getSource().getName();
		List<GrantedAuthority> auth = (List) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		if(auth!=null && auth.size()>0){
			for(GrantedAuthority grantedAuthority:auth){
				if(grantedAuthority instanceof SwitchUserGrantedAuthority){
					previousUser = ((SwitchUserGrantedAuthority) grantedAuthority).getSource().getName();
				}
			}
			//previousUser = ((SwitchUserGrantedAuthority)((List)SecurityContextHolder.getContext().getAuthentication().getAuthorities()).get(auth.size()-1)).getSource().getName();
		}
		logger.debug("getPreviousUser:-Previouse User :"+ previousUser);
		return previousUser;
		
	}

	public String encodePassword(String  password) throws DataException{
		String encodePassword = "";
		try {
			encodePassword = new Md5PasswordEncoder().encodePassword(password, null);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataException("encodePassword:- Unable encodePassword:");
		}
		return encodePassword;
	}

	@Override
	public boolean assignRoleToUser(String userName,String role) throws DataException{
		// Assign role to User
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		UserDetails userFromUserQuery = null;
		try{
			 userFromUserQuery = jdbcUserDetailsManager.loadUserByUsername(userName);
			 if(userFromUserQuery != null){
					grantedAuthorities = new ArrayList<GrantedAuthority>(userFromUserQuery.getAuthorities());
					grantedAuthorities.add(new SimpleGrantedAuthority(deSanitizeRole(role)));
					UserDetails userDetails = new SSAuthorities().createUserDetails(userName, userFromUserQuery, grantedAuthorities);
					jdbcUserDetailsManager.updateUser(userDetails);
				}	
		}catch(UsernameNotFoundException e){
			String uName="",Pwd="";
			Entity entity = entityManager.loadEntityByUsername(userName);
			if(entity != null){
				if(entity.getEntityTypeId() == EntityTypes.PEOPLE.getValue()){
					List<AttributeValueStorage> users = entity.getAttributeValueStorage();
					if(users != null && users.size() > 0){
						for(AttributeValueStorage user:users){
							if(user.getId() == Attributes.USERNAME.getValue()){
								uName = user.getValueVarchar();
							}
							if(user.getId() == Attributes.PASSWORD.getValue()){
								Pwd = user.getValueVarchar();
							}
						 }
						
					}
				 }
			}
			grantedAuthorities.add(new SimpleGrantedAuthority(deSanitizeRole(role)));
		 	UserDetails customUser = new CustomUser(uName, Pwd, true, true, true, true, grantedAuthorities);
			jdbcUserDetailsManager.updateUser(customUser);

			
		}
		catch(Exception e){
			logger.error("assignUserRole:-Unable to assignUserRole :"+ e.getMessage());
			throw new DataException("encodePassword:- Unable assignUserRole:"+e.getMessage());
		}
		return true;
		
	}
	@Override
	public boolean removeUserRole(String userName,String role,MappingJackson2JsonView view) throws DataException {
		// Remove User Role
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		UserDetails userFromUserQuery = null;
		try{
			 userFromUserQuery = jdbcUserDetailsManager.loadUserByUsername(userName);
			 if(userFromUserQuery != null){
					grantedAuthorities = new ArrayList<GrantedAuthority>(userFromUserQuery.getAuthorities());
					// remove roles only if user has more than one
					//if(grantedAuthorities.size()>1){
						for(GrantedAuthority auth:grantedAuthorities){
							if(auth.getAuthority().equalsIgnoreCase(deSanitizeRole(role))){
								grantedAuthorities.remove(auth);
								break;
							}	  
						}
					//}else{
					//	model.addAttribute("errorMsg", "You can't remove this Role.User should have atleast one Role...");
					//}
					UserDetails userDetails = new SSAuthorities().createUserDetails(userName, userFromUserQuery, grantedAuthorities);
					jdbcUserDetailsManager.updateUser(userDetails);
				}	
		}catch(UsernameNotFoundException e){
			logger.error("removeUserRole:-Unable to removeUserRole UsernameNotFoundException :"+ e.getMessage());
			throw new DataException("removeUserRole:- Unable removeUserRole UsernameNotFoundException:"+e.getMessage());
			
		}
		catch(Exception e){
			logger.error("removeUserRole:-Unable to removeUserRole :"+ e.getMessage());
			throw new DataException("encodePassword:- Unable removeUserRole:"+e.getMessage());
		}
		return true;
		
	}
	
	@Override
	public boolean isRoleAssigned(String userName,String role) throws DataException{
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		UserDetails userFromUserQuery = null;
		try{
			 userFromUserQuery = jdbcUserDetailsManager.loadUserByUsername(userName);
		}catch(UsernameNotFoundException e){
			logger.debug("isRoleAssigned:- No roles found while loading User"+e.getMessage());
			return false;
		}
		if(userFromUserQuery != null){
			grantedAuthorities = new ArrayList<GrantedAuthority>(userFromUserQuery.getAuthorities());
			if(grantedAuthorities != null && grantedAuthorities.size()>0){
				for(GrantedAuthority auth:grantedAuthorities){
					if(auth.getAuthority().equalsIgnoreCase(role)){
						return true;
					}	  
				}
			}
		}	
		return false;		
	}
	
		
	public boolean addDefaultVisiblePermissionOnAttribute(Permissionable attribute) throws DataException{
		List<AttributeValueStorage> allRoles = null;
		MutableAcl attAcl;
		try {
			attAcl = fetchAcl(attribute);
			//Already Acl exists
			if(attAcl!=null){
				if(attAcl.getEntries().size() > 0)
					return true;
			}
			allRoles = entityManager.loadRoles();
			if(allRoles != null){
				for(AttributeValueStorage role: allRoles){
					grantPermission(attribute,null,BasePermission.READ,new GrantedAuthoritySid(role.getValueVarchar())); 
				}
			}
		} catch (DataException e) {
			logger.error("addDefaultVisiblePermissionOnAttribute:-Error..while granting Default View Permission on Attribute : " + e.getMessage(),e);
			throw new DataException("addDefaultVisiblePermissionOnAttribute:-Error..while granting Default View Permission on Attribute:", e);
		}
		
		return true;
	}
	

	
	public boolean setAsParent(Permissionable Object) throws DataException{
		MutableAcl objAcl,updatedAcl = null;
		
		// create/retrieve the  Acl of enttiyType
		ObjectIdentity objectOID = new ObjectIdentityImpl(Object.getPermissionType(), Object.getPermissionId());
		try {
			objAcl = (MutableAcl) aclService.readAclById(objectOID);
			if(objAcl != null){
				logger.debug("setAsParent: EntityType:"+Object.getPermissionType()+" alredy has Acl...");
				return true;
			}
			
		} catch (NotFoundException nfe) {
			// create a new Acl 
			objAcl = createACL(objectOID);
		}
		try{
			// set EntityTye Parent 
			if(objAcl.getParentAcl() == null){
				updatedAcl= updateACL(objAcl);
			}	
			else{
				//Object has GrandParent
				logger.debug("setAsParent: EntityType:"+Object.getPermissionType()+" alredy has the ParentAcl...");
				return true;
			}
		}catch(DataException e){
			logger.error("setAsParent:-Error..while while creating ACl for EntityType : " + e.getMessage(),e);
			throw new DataException("setAsParent:-Error..while while creating ACl for EntityType:", e);
		}
		if(updatedAcl != null){
			logger.debug("setAsParent: Parent Acl is created success for the EntityType: "+Object.getPermissionType());
		}
		return true;
	}
	
	
	private MutableAcl takeOffPermission(final MutableAcl acl,final int aceIndex) throws DataException{
		acl.deleteAce(aceIndex);
		logger.debug("takeOffPermission: takeOffPermission : success");
        
	return acl;
		
	}
	private MutableAcl insertACE(final MutableAcl acl,final org.springframework.security.acls.model.Permission permission,final Sid recipient)throws DataException{
		
		acl.insertAce(acl.getEntries().size(), permission, recipient, true);
		if(acl == null)
        	throw new DataException("insertACE:inserting ACE failed for Sid:" + recipient+",permission:"+permission);
       
		return acl;
	}
	
	private MutableAcl updateACL(final MutableAcl acl) throws DataException{
       	updatedAcl = aclService.updateAcl(acl);
        if(updatedAcl == null)
        	throw new DataException("updateACL:Updating Acl failed " + acl);

        return updatedAcl;
	}
	
	private MutableAcl createACL(final ObjectIdentity objectOID) throws DataException{
        createdAcl = aclService.createAcl(objectOID);                	
        if(createdAcl == null)
        	throw new DataException("createACL:creating Acl failed " + createdAcl);
        else
        	//logger.info("createACL: created ACL for the ObjectID: "+objectOID);
        
		return createdAcl;
	}
	
	private void deleteACL(final ObjectIdentity objectOID) throws DataException{
        aclService.deleteAcl(objectOID, true);                	
        logger.debug("deleteAcl: Deleted ACL for the ObjectID: "+objectOID);
	}
	
	/**
	 * @param authManager the authManager to set
	 */
	@Autowired
	public void setAuthManager(AuthenticationManager authManager) {
		this.authManager = authManager;
	}
	
	
	private String sanitizeRole(String role){
		return role.trim().substring(5);
	}
	private String deSanitizeRole(String role){
		return "ROLE_"+role.trim();
	}
	
	public List<AttributeValueStorage> loadAssignNewRoles() throws DataException {
		 int count = 0;
		/* Preparing total granted Authorities that User has */						
		 List<AttributeValueStorage> assingedRoles = null;
		 try{
			 List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
			 String[] authorities = null ;
			 if(grantedAuthorities !=null && grantedAuthorities.size() > 0){
				 authorities =  new String[grantedAuthorities.size()];
				 for (GrantedAuthority authority: grantedAuthorities){
					 authorities[count]= authority.getAuthority();
					 count++;
				 }
				 assingedRoles = entityDAO.loadAssignNewRoles(authorities);
			 }	
			 
				if(assingedRoles != null && assingedRoles.size() == 0){
					logger.debug("loadAssignNewRoles:- Assign at new is NOT marked to Any ROLE to given authorities:-"+authorities);
					return null;
			}
		 }catch(Exception e){
			 logger.debug("loadAssignNewRoles: Error while loading assignNewRoles.."+e.getMessage());
			 throw new DataException("loadAssignNewRoles: Error while loading assignNewRoles.."+e.getMessage());
		 }
		return assingedRoles;
	}


		

	@Autowired
	public void setJdbcUserDetailsManager(JdbcUserDetailsManager jdbcUserDetailsManager) {
		this.jdbcUserDetailsManager = jdbcUserDetailsManager;
	}

	@Autowired
	public void setAclService(JdbcMutableAclService aclService) {
		this.aclService = aclService;
	}
	@Autowired
	public void setAclCache(AclCache aclCache) {
	}
	@Autowired
    public void setPlatformTransactionManager(final PlatformTransactionManager platformTransactionManager) {
    	this.tt = new TransactionTemplate(platformTransactionManager);
	}

	@Override
	public void logInUser(String username, String password) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
		SecurityContextHolder.getContext().setAuthentication(authManager.authenticate(authentication));
	}
	
	public boolean  createCustomUser(String userName,String password,boolean enabled,boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,String role) throws DataException{
		List<GrantedAuthority> customAuthorities = new ArrayList<GrantedAuthority>();
		
		if(role !=null){
			customAuthorities.add(new SimpleGrantedAuthority(role));
		}else{
			// Add default Role ROLE_USER while creating new User.
			customAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		UserDetails customUser = new CustomUser(userName, password, true, true, true, true, customAuthorities);
		jdbcUserDetailsManager.updateUser(customUser);
		UserDetails userFromUserQuery = jdbcUserDetailsManager.loadUserByUsername(userName);
		if(userFromUserQuery !=null)
			return true;
		
		return false;
		
	}

	@Override
	public boolean createPermissionOnEntity(Permissionable child,Permissionable parent) throws DataException {
		MutableAcl parentAcl,acl = null;
		 List<AttributeValueStorage> assingedRoles = null ;
		 parentAcl = (MutableAcl) fetchAcl(parent);
		
		 // Parent should be set first. 
		 if(parentAcl == null){
			logger.error("createPermissionOnEntity:-Parent Acl"+parent.getPermissionType()+" should not be null for child"+child.getPermissionType()) ;
		 }
		 final ObjectIdentity childOid = new ObjectIdentityImpl(child.getPermissionType(), child.getPermissionId());
		 try {
			 acl = (MutableAcl) aclService.readAclById(childOid);
		 }catch (NotFoundException nfe) {
			acl = createACL(childOid);
			
		 }
		 try {
			 // we need to check this while editing entity from avs save.
			 if(acl.getEntries().size()>0)
				 return true;
			 
			// load the roles only  assign_at_new marked as true   
			assingedRoles = loadAssignNewRoles();
			//
			acl.setOwner(new PrincipalSid(getUsername()));
			acl.setParent(parentAcl);
			customAclService.updateObjectIdentity(acl);
			/* Granting Edit permission to the assign at new marked as true roles the User has */
			createWRITEPermission(acl,assingedRoles);
			
			
		 } catch (DataException e) {
			logger.error("createPermissionOnEntity:-Error loading : " + e.getMessage(),e);
			throw new DataException("createPermissionOnEntity:-Error loading : " + e.getMessage(),e);
	     }	
		
		 return true;
	}
	
	@Override
	public Entity loadLoginUserEntity() throws DataException {
		return entityManager.loadEntityByUsername(getUsername());
	}
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws DataException {
		
		UserDetails userDetails;
		try{
			userDetails = jdbcUserDetailsManager.loadUserByUsername(userName);
		}catch(UsernameNotFoundException e){
			logger.debug("loadUserByUsername:- No user found in the AVS"+e.getMessage());
			return null;
		}
		return userDetails;
	}
	
	@Override
	public UsernamePasswordAuthenticationToken getJWTAuthentication(UserDetails userDetails) throws DataException {
		
		 UsernamePasswordAuthenticationToken authentication = null;
		try{
			 authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            
		}catch(UsernameNotFoundException e){
			logger.debug("getJWTAuthentication:- jwtUser is not Found"+e.getMessage());
			return null;
		}
		return authentication;
	}
	
	@Override
	public UserDetails CheckJWTUserAuthentication(JwtAuthenticationRequest authenticationRequest) throws DataException {
		
		final Authentication authentication = authManager.authenticate(
	               new UsernamePasswordAuthenticationToken(
	               		authenticationRequest.getUsername(),
	               		authenticationRequest.getPassword()
	               )
	      );
	       
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        UserDetails userDetails = loadUserByUsername(authenticationRequest.getUsername());
		return userDetails;
	}

	@Override
	public JwtUser loadJwtUserByUserDetails(UserDetails userDetails) throws DataException {
		JwtUser jwtUser = new JwtUser();
		try{
			jwtUser.setUsername(userDetails.getUsername());
			jwtUser.setPassword(userDetails.getPassword());
			jwtUser.setAuthorities(userDetails.getAuthorities());
        
		}catch(UsernameNotFoundException e){
			logger.debug("loadJwtUserByUserDetails User details not Found"+e.getMessage());
			return null;
		}
		return jwtUser;
	}
	
	@Override
    public Long quickSave(Entity entity,List<AttributeValueStorage> attValuelist) throws DataException  {
    	Long entityId;
    	
		try {
			for(AttributeValueStorage avs : attValuelist){
				if(entity.getEntityTypeId() == EntityTypes.PEOPLE.getValue() && avs.getId() == Attributes.PASSWORD.getValue()){
	           		String password = encodePassword(avs.getValueVarchar());
	           		avs.setValueVarchar(password);
	           	}
			}
			entityId = entityDAO.quickSave(entity,attValuelist);
		} catch (DataException e) {
			logger.error("quickSave:- Unable to save entity :-",e);
			throw new DataException("quickSave:- Unable to save entity :-",e);
		}
        return entityId;	 
    }  
	
	@Override
	public EntityType load(long id) throws DataException  {
		EntityType entityType = null;
		try {
			entityType = entityTypeDAO.load(id);
		} catch (DataException e) {
			logger.error("load: Error load EntityFype failed:"+e);
			throw new DataException("load: Error load EntityFype failed:"+e);
		}
		return entityType;
	}
	
	@Override
	public boolean assignUserRole(long entityId, String roleOrUser) throws DataException{
		Entity entity;boolean status = false;
		try {
			entity = entityDAO.loadEntity(entityId);
			String userName ="",roleName ="";
			if(entity != null){
				// if the entity type is people or person
				if(entity.getEntityTypeId() == EntityTypes.PEOPLE.getValue()){
					List<AttributeValueStorage> users = entity.getAttributeValueStorage();
					if(users != null && users.size() > 0){
						for(AttributeValueStorage user:users){
							if(user.getId() == Attributes.USERNAME.getValue()){
								userName = user.getValueVarchar();
								break;
							}	
						 }
						status = assignRoleToUser(userName,roleOrUser);
					}
				 }else if(entity.getEntityTypeId() == EntityTypes.ROLE.getValue()){
					 List<AttributeValueStorage> roles = entity.getAttributeValueStorage();
						if(roles != null && roles.size() > 0){
							for(AttributeValueStorage role:roles){
								if(role.getId() == Attributes.ROLENAME.getValue()){
									roleName = role.getValueVarchar();
									break;
								}	
							 }
							status = assignRoleToUser(roleOrUser,sanitizeRole(roleName));
						}
				 }
				
			 }
		} catch (DataException e) {
			logger.error("assignUserRole:-Unable to assignUserRole:"+e);
			throw new DataException("assignUserRole:-Unable to assignUserRole:"+e);
		}
		return status;
	}
	
	private String filterValue_Long(String value_Long) throws DataException {		
		StringBuffer strbuf = new StringBuffer();		
		for (int i=0; i<= value_Long.length()-1; i++ ) {
			char c = value_Long.charAt(i);
			if(c =='(' || c == ')' || c == '-' || c == '.' || c == ' '){
				continue;
			}else strbuf.append(c);			
         }		
		return strbuf.toString();
	}
	
	
	
}
