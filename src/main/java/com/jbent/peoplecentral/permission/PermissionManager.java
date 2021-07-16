package com.jbent.peoplecentral.permission;

import java.util.List;

import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.jbent.peoplecentral.exception.AttributeValueNotValidException;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;
import com.jbent.peoplecentral.model.pojo.CompletePermissions;
import com.jbent.peoplecentral.model.pojo.Entity;
import com.jbent.peoplecentral.model.pojo.EntityStatus;
import com.jbent.peoplecentral.model.pojo.EntityType;
import com.jbent.peoplecentral.model.pojo.ImportEntity;
import com.jbent.peoplecentral.security.jwt.JwtUser;
import com.jbent.peoplecentral.security.jwt.auth.JwtAuthenticationRequest;




public interface PermissionManager {

	public String encodePassword(String password)throws DataException;
	public boolean  assignRoleToUser(String userName,String role) throws DataException;
	public boolean removeUserRole(String userName,String role,MappingJackson2JsonView view) throws DataException;
	public boolean isRoleAssigned(String userName,String role) throws DataException;
	public String getUsername() throws DataException;
	public String getPreviousUser() throws DataException;
	public void logInUser(String username, String password);
	public boolean takeOffRolePermission(Permissionable permissionable,org.springframework.security.acls.model.Permission oldPermission,org.springframework.security.acls.model.Permission newPermission,Sid recipient) throws DataException;
	public boolean takeOffAllRolePermissions(Permissionable permissionable,org.springframework.security.acls.model.Permission oldPermission,org.springframework.security.acls.model.Permission newPermission,Sid recipient) throws DataException;
	public boolean createPermissionsOnNewEntities(List<ImportEntity> newEntities) throws DataException;
	public boolean createPermissionOnEntity(Permissionable entity,Permissionable parent) throws DataException;
	public List<AttributeValueStorage> loadAssignNewRoles() throws DataException;
	public boolean permissionHandler(Permissionable object,String role,String permission) throws DataException;
	public MutableAcl fetchAcl(Permissionable object) throws DataException;
	public void deletePermission(Permissionable permissionable) throws DataException;
	public List<CompletePermissions> fetchPermissionRoles(List<AccessControlEntry> permissionEntries,long entityId,String roleOn) throws DataException;
	public boolean setAsParent(Permissionable entityType)throws DataException;
	public boolean addDefaultVisiblePermissionOnAttribute(Permissionable permissionable) throws DataException;
	public List<CompletePermissions> combinedPermissionRolesAndNoPermissionRoles(List<CompletePermissions> permissionsList,long objId,String roleOn) throws DataException;
	public boolean  createCustomUser(String userName,String password,boolean enabled,boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,String role) throws DataException;
	public Entity loadLoginUserEntity() throws DataException;
	public UserDetails loadUserByUsername(String userName) throws DataException;
	public UsernamePasswordAuthenticationToken getJWTAuthentication(UserDetails userDetails) throws DataException;
	public UserDetails CheckJWTUserAuthentication(JwtAuthenticationRequest authenticationRequest) throws DataException;
	public JwtUser loadJwtUserByUserDetails(UserDetails userDetails) throws DataException;
	public Long quickSave(Entity entity, List<AttributeValueStorage> attValuelist) throws DataException;
	public EntityType load(long id) throws DataException;
	public boolean assignUserRole(long entityId,String role) throws DataException;
}
