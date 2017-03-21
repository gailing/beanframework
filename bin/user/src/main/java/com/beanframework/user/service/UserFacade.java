package com.beanframework.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.beanframework.platform.core.base.BaseFacade;
import com.beanframework.user.DeleteWithRelationshipException;
import com.beanframework.user.EmailDuplicatedException;
import com.beanframework.user.EmailMissingException;
import com.beanframework.user.ForceLogoutUserException;
import com.beanframework.user.NameDuplicatedException;
import com.beanframework.user.NameRequiredException;
import com.beanframework.user.PasswordMissingException;
import com.beanframework.user.UsernameDuplicatedException;
import com.beanframework.user.UsernameMissingException;
import com.beanframework.user.domain.Group;
import com.beanframework.user.domain.GroupSearchCriteria;
import com.beanframework.user.domain.Permission;
import com.beanframework.user.domain.Role;
import com.beanframework.user.domain.RoleSearchCriteria;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserSearchCriteria;
import com.beanframework.user.utils.PasswordUtils;
import com.beanframework.user.utils.UserManager;

@Component
public class UserFacade extends BaseFacade {

	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PermissionService permissionService;

	@Autowired
	private UserManager userManager;

	public User createUser() {
		User user = new User();
		user.setEnabled(true);
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		
		return user;
	}

	public Group createGroup() {
		Group group = new Group();

		return group;
	}

	public Role createRole() {
		Role role = new Role();

		return role;
	}
	
	public Permission createPermission() {
		Permission permission = new Permission();

		return permission;
	}

	public boolean isUsernameExists(String username) {
		return userService.isUsernameExists(username);
	}

	public boolean isEmailExists(String email) {
		return userService.isEmailExists(email);
	}

	public boolean isGroupNameExists(String name) {
		return groupService.isNameExists(name);
	}

	public boolean isRoleNameExists(String name) {
		return roleService.isNameExists(name);

	}

	public Page<User> getUserPage(String query, String keyword, String field, String direction, int page, int pageSize) {

		// Search Criteria
		UserSearchCriteria searchCriteria = null;
		if (StringUtils.isNotEmpty(query) && StringUtils.isNotEmpty(keyword)) {
			searchCriteria = new UserSearchCriteria(query, keyword);
		}

		// Pageable
		int pageIndex = page - 1;
		PageRequest pageRequest;
		if (!StringUtils.isEmpty(field) && !StringUtils.isEmpty(direction)) {
			pageRequest = new PageRequest(pageIndex, pageSize, Sort.Direction.fromString(direction), field);
		} else {
			pageRequest = new PageRequest(pageIndex, pageSize, Sort.Direction.DESC, User.CREATED_DATE);
		}

		return userService.findAllUsers(searchCriteria, pageRequest);
	}

	public Page<Group> getGroupPage(String query, String keyword, String field, String direction, int page, int pageSize) {

		// Search Criteria
		GroupSearchCriteria searchCriteria = null;
		if (StringUtils.isNotEmpty(query) && StringUtils.isNotEmpty(keyword)) {
			searchCriteria = new GroupSearchCriteria(query, keyword);
		}

		// Pageable
		int pageIndex = page - 1;
		PageRequest pageRequest;
		if (!StringUtils.isEmpty(field) && !StringUtils.isEmpty(direction)) {
			pageRequest = new PageRequest(pageIndex, pageSize, Sort.Direction.fromString(direction), field);
		} else {
			pageRequest = new PageRequest(pageIndex, pageSize, Sort.Direction.DESC, Group.CREATED_DATE);
		}

		return groupService.findAllGroups(searchCriteria, pageRequest);
	}

	public Page<Role> getRolePage(String query, String keyword, String field, String direction, int page, int pageSize) {

		// Search Criteria
		RoleSearchCriteria searchCriteria = null;
		if (StringUtils.isNotEmpty(query) && StringUtils.isNotEmpty(keyword)) {
			searchCriteria = new RoleSearchCriteria(query, keyword);
		}

		// Pageable
		int pageIndex = page - 1;
		PageRequest pageRequest;
		if (!StringUtils.isEmpty(field) && !StringUtils.isEmpty(direction)) {
			pageRequest = new PageRequest(pageIndex, pageSize, Sort.Direction.fromString(direction), field);
		} else {
			pageRequest = new PageRequest(pageIndex, pageSize, Sort.Direction.DESC, Role.CREATED_DATE);
		}

		return roleService.findAllRoles(searchCriteria, pageRequest);
	}

	public User getUserByUuId(UUID uuid) {
		return userService.findByUuid(uuid);
	}

	public Group getGroupById(UUID uuid) {
		return groupService.findByUuid(uuid);
	}

	public Role getRoleById(UUID uuid) {
		return roleService.findByUuid(uuid);
	}

	public User getUserByUsername(String username) {
		return userService.findByUsername(username);
	}

	public Group getGroupWithUsersAndRoles(UUID uuid) {
		Group group = groupService.findByUuid(uuid);
		return group;
	}

	public Role getRoleWithUsers(UUID uuid) {
		Role role = roleService.findByUuid(uuid);
		return role;
	}

	public List<Group> getAllGroups() {
		return groupService.findAllGroups();
	}

	public List<Role> getAllRoles() {
		return roleService.findAllRoles();
	}

	public List<Group> getGroupsByIds(UUID[] ids) {
		return groupService.find(ids);
	}

	public List<Role> getRolesByIds(UUID[] ids) {
		return roleService.find(ids);
	}
	
	public User saveUser(User user) {
		
		if (user.getUuid() == null) {
			// Add User
			if (StringUtils.isEmpty(user.getUsername())) {
				throw new UsernameMissingException("Username Required");
			} else {
				if (userService.isUsernameExists(user.getUsername())) {
					throw new UsernameDuplicatedException("Duplicated username in database");
				}
			}

			if (StringUtils.isEmpty(user.getPassword())) {
				throw new PasswordMissingException("Password Required");
			} else {
				user.setPassword(PasswordUtils.encode(user.getPassword()));
			}

			if (StringUtils.isEmpty(user.getEmail())) {
				throw new EmailMissingException("Email Required");
			} else {
				if (userService.isEmailExists(user.getEmail())) {
					throw new EmailDuplicatedException("Duplicated email in database");
				}
			}
			return userService.save(user);

		} else {
			// Update User
			User updateUser = userService.findByUuid(user.getUuid());

			if (StringUtils.isNotEmpty(user.getPassword())) {
				updateUser.setPassword(PasswordUtils.encode(user.getPassword()));
			}
			if (StringUtils.isNotEmpty(user.getUsername()) && !updateUser.getUsername().equals(user.getUsername())) {
				if (userService.isUsernameExists(user.getUsername())) {
					throw new UsernameDuplicatedException("Duplicated username in database");
				} else {
					updateUser.setUsername(user.getUsername());
				}
			}
			if (StringUtils.isNotEmpty(user.getEmail()) && !updateUser.getEmail().equals(user.getEmail())) {
				if (userService.isEmailExists(user.getEmail())) {
					throw new EmailDuplicatedException("Duplicated email in database");
				} else {
					updateUser.setEmail(user.getEmail());
				}
			}
			updateUser.setEnabled(user.isEnabled());
			updateUser.setAccountNonExpired(user.isAccountNonExpired());
			updateUser.setAccountNonLocked(user.isAccountNonLocked());
			updateUser.setCredentialsNonExpired(user.isCredentialsNonExpired());
			updateUser.setFirstName(user.getFirstName());
			updateUser.setLastName(user.getLastName());
			updateUser.setGroups(user.getGroups());
			updateUser.setRoles(user.getRoles());
			return userService.save(updateUser);
		}
	}

	public Group saveGroup(Group group) {

		if (group.getUuid() == null) {
			// Add Group
			if(StringUtils.isEmpty(group.getName())){
				throw new NameRequiredException("Name Required!");
			}
			
			return groupService.save(group);
		}
		else{
			// Update group
			Group updateGroup = groupService.findByUuid(group.getUuid());
			
			// New name given
			if (StringUtils.isNotEmpty(group.getName()) && !updateGroup.getName().equals(group.getName())) {

				if (roleService.isNameExists(group.getName())) {
					throw new NameDuplicatedException("Duplicated name in database");
				} else {
					updateGroup.setName(group.getName());
				}
			}
			
			updateGroup.setDescription(group.getDescription());
			updateGroup.setUsers(group.getUsers());
			if (group.getSelectedRoleIds() != null && group.getSelectedRoleIds().length > 0) {
				group.setRoles(roleService.find(group.getSelectedRoleIds()));
			}
			return groupService.save(updateGroup);
		}
	}

	public Role saveRole(Role role) {
		
		if (role.getUuid() == null) {
			// Add Role
			if(StringUtils.isEmpty(role.getName())){
				throw new NameRequiredException("Name Required!");
			}
			
			return roleService.save(role);
		}
		else{
			// Update Role
			Role updateRole = roleService.findByUuid(role.getUuid());
			
			// New name given
			if (StringUtils.isNotEmpty(role.getName()) && !updateRole.getName().equals(role.getName())) {

				if (roleService.isNameExists(role.getName())) {
					throw new NameDuplicatedException("Duplicated name in database");
				} else {
					updateRole.setName(role.getName());
				}
			}
			
			updateRole.setDescription(role.getDescription());
			updateRole.setUsers(role.getUsers());
			updateRole.setGroups(role.getGroups());
			updateRole.setPermissions(role.getPermissions());
			return roleService.save(updateRole);
		}
	}
	
	public List<Permission> savePermissions(List<String> permissions){
		List<Permission> permissionList = new ArrayList<Permission>(0);
		if(permissions != null){
			for (String permission : permissions) {
				Permission newPermission = createPermission();
				newPermission.setName(permission);
				permissionList.add(permissionService.save(newPermission));
			}
		}
		return permissionList;
	}

	public User deleteUserById(UUID uuid) {

		User user = getUserByUuId(uuid);
		userManager.expireAllSessionsByUsername(user.getUsername());
		userService.delete(user.getUuid());

		return user;
	}

	public Group deleteGroupById(UUID uuid) {

		Group group = getGroupById(uuid);

		return deleteGroup(group);
	}

	public Role deleteRoleById(UUID uuid) throws Exception {

		Role role = getRoleById(uuid);
		
		if(role.isNonDelete()){
			throw new Exception("Cannot delete default role.");
		}

		return deleteRole(role);
	}

	private Group deleteGroup(Group group) {

		if (group.getUsers() != null && !group.getUsers().isEmpty()) {
			throw new DeleteWithRelationshipException("Group has user relationship, cannot be deleted");
		}

		groupService.delete(group.getUuid());

		return group;
	}

	private Role deleteRole(Role role) {

		if (role.getGroups() != null && !role.getGroups().isEmpty()) {
			throw new DeleteWithRelationshipException("Role has group relationship, cannot be deleted");
		}

		if (role.getUsers() != null && !role.getUsers().isEmpty()) {
			throw new DeleteWithRelationshipException("Role has user relationship, cannot be deleted");
		}

		roleService.delete(role.getUuid());
		return role;
	}

	public void removeGroupFromUser(String userId, String... groupIds) {
		userService.removeGroupFromUser(UUID.fromString(userId), convertToUUIDArray(groupIds));
	}

	public void removeRoleFromUser(String userId, String... roleIds) {
		userService.removeRoleFromUser(UUID.fromString(userId), convertToUUIDArray(roleIds));
	}

	public void removeRoleFromGroup(String groupId, String... roleIds) {
		groupService.removeRoleFromGroup(UUID.fromString(groupId), convertToUUIDArray(roleIds));
	}

	public boolean isUserLoggedInByUsername(String username) {
		return userManager.isLoggedInByUsername(username);
	}

	public void forceLogoutUserByUsername(UUID uuid) {

		User user = getUserByUuId(uuid);
		userManager.expireAllSessionsByUsername(user.getUsername());

		if (isUserLoggedInByUsername(user.getUsername())) {
			throw new ForceLogoutUserException("After force user logout, session still exists.");
		}
	}

	public UUID[] convertToUUIDArray(String... ids) {

		UUID[] uuids = new UUID[ids.length];
		for (int i = 0; i < ids.length; i++) {
			uuids[i] = UUID.fromString(ids[i]);
		}
		return uuids;
	}

	public void updateLastLogonDateById(Date lastLogonDate, UUID uuid) {
		userService.updateLastLogonDateById(lastLogonDate, uuid);
	}

	public Role findRoleByName(String name) {
		return roleService.findByName(name);
	}

	public List<Group> findAllGroups() {
		return groupService.findAllGroups();
	}

	public List<Role> findAllRoles() {
		return roleService.findAllRoles();
	}
}
