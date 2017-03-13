package com.beanframework.user.utils;
//package com.beanframework.platform.user.utils;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import org.apache.commons.lang.StringUtils;
//import com.beanframework.platform.user.ConfirmPasswordMissingException;
//import com.beanframework.platform.user.DeleteDefaultAdminException;
//import com.beanframework.platform.user.DeleteWithRelationshipException;
//import com.beanframework.platform.user.EmailDuplicatedException;
//import com.beanframework.platform.user.NameDuplicatedException;
//import com.beanframework.platform.user.NewAndConfirmPasswordNotMatchException;
//import com.beanframework.platform.user.UsernameDuplicatedException;
//import com.beanframework.platform.user.UsernameMissingException;
//import com.beanframework.platform.user.domain.Group;
//import com.beanframework.platform.user.domain.Permission;
//import com.beanframework.platform.user.domain.Role;
//import com.beanframework.platform.user.domain.User;
//import com.beanframework.platform.user.service.GroupService;
//import com.beanframework.platform.user.service.PermissionService;
//import com.beanframework.platform.user.service.RoleService;
//import com.beanframework.platform.user.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserValidateConverter {
//
//	@Autowired
//	private UserService userService;
//
//	@Autowired
//	private GroupService groupService;
//
//	@Autowired
//	private RoleService roleService;
//
//	@Autowired
//	private PermissionService permissionService;
//
//	@Autowired
//	private UserPasswordManager userPasswordManager;
//	
//	@Autowired
//	private UserManager userManager;
//
//	public void saveUser(User user) {
//
//		if (user.getUuid() == null) {
//			if (StringUtils.isEmpty(user.getNewUsername())) {
//				throw new UsernameMissingException("Username Required");
//			}
//		}
//
//		// New password given
//		if (StringUtils.isNotEmpty(user.getNewPassword())) {
//			// Confirm password empty
//			if (StringUtils.isEmpty(user.getConfirmNewPassword())) {
//				throw new ConfirmPasswordMissingException("Confirm Password Required");
//			}
//
//			// New password and confirm password not match
//			if (!user.getNewPassword().equals(user.getConfirmNewPassword())) {
//				throw new NewAndConfirmPasswordNotMatchException("New Password and Confirm Password not match");
//			}
//
//			user.setPassword(userPasswordManager.encode(user.getNewPassword()));
//		}
//
//		// New username given
//		if (StringUtils.isNotEmpty(user.getNewUsername()) && !user.getNewUsername().equals(user.getUsername())) {
//
//			if (userService.isUsernameExists(user.getNewUsername())) {
//				throw new UsernameDuplicatedException("Duplicated username in database");
//			} else {
//				user.setUsername(user.getNewUsername());
//			}
//		}
//
//		// New email given
//		if (StringUtils.isNotEmpty(user.getNewEmail()) && !user.getNewEmail().equals(user.getEmail())) {
//
//			if (userService.isEmailExists(user.getNewEmail())) {
//				throw new EmailDuplicatedException("Duplicated email in database");
//			} else {
//				user.setEmail(user.getNewEmail());
//			}
//		}
//
//		if (user.getSelectedGroupIds() != null && user.getSelectedGroupIds().length > 0) {
//			user.setGroups(getAllGroupsByIds(user.getSelectedGroupIds()));
//		}
//
//		if (user.getSelectedRoleIds() != null && user.getSelectedRoleIds().length > 0) {
//			user.setRoles(getAllRolesByIds(user.getSelectedRoleIds()));
//		}
//		
//		user.setSuperAdmin(false);
//	}
//	
//	public void saveSuperAdmin(User user) {
//		saveUser(user);
//		user.setSuperAdmin(true);
//	}
//
//	public void saveGroup(Group group) {
//		// New name given
//		if (StringUtils.isNotEmpty(group.getNewName()) && !group.getNewName().equals(group.getName())) {
//
//			if (groupService.isNameExists(group.getNewName())) {
//				throw new NameDuplicatedException("Duplicated name in database");
//			} else {
//				group.setName(group.getNewName());
//			}
//		}
//
//		if (group.getSelectedRoleIds() != null && group.getSelectedRoleIds().length > 0) {
//			group.setRoles(getAllRolesByIds(group.getSelectedRoleIds()));
//		}
//	}
//
//	public void saveRole(Role role) {
//		// New name given
//		if (StringUtils.isNotEmpty(role.getNewName()) && !role.getNewName().equals(role.getName())) {
//
//			if (roleService.isNameExists(role.getNewName())) {
//				throw new NameDuplicatedException("Duplicated name in database");
//			} else {
//				role.setName(role.getNewName());
//			}
//		}
//
//		if (role.getSelectedPermissionIds() != null && role.getSelectedPermissionIds().length > 0) {
//			role.setPermissions(getAllPermissionsByIds(role.getSelectedPermissionIds()));
//		}
//	}
//
//	public void deleteUser(User user) {
//		if (user.getUsername().equals(userManager.getSuperAdmin().getUsername())) {
//			throw new DeleteDefaultAdminException("Super Admin cannot be deleted.");
//		}
//	}
//
//	public void deleteGroup(Group group) {
//
//		if (group.getUsers() != null && !group.getUsers().isEmpty()) {
//			throw new DeleteWithRelationshipException("Group has user relationship, cannot be deleted");
//		}
//	}
//
//	public void deleteRole(Role role) {
//
//		if (role.getGroups() != null && !role.getGroups().isEmpty()) {
//			throw new DeleteWithRelationshipException("Role has group relationship, cannot be deleted");
//		}
//
//		if (role.getUsers() != null && !role.getUsers().isEmpty()) {
//			throw new DeleteWithRelationshipException("Role has user relationship, cannot be deleted");
//		}
//
//	}
//
//	private List<Group> getAllGroupsByIds(String... ids) {
//
//		if (ids == null || ids.length == 0) {
//			return new ArrayList<Group>();
//		}
//
//		UUID[] uuids = new UUID[ids.length];
//
//		for (int i = 0; i < uuids.length; i++) {
//			uuids[i] = UUID.fromString(ids[i]);
//		}
//
//		return groupService.find(uuids);
//	}
//
//	private List<Role> getAllRolesByIds(String... ids) {
//
//		if (ids == null || ids.length == 0) {
//			return new ArrayList<Role>();
//		}
//
//		UUID[] uuids = new UUID[ids.length];
//
//		for (int i = 0; i < uuids.length; i++) {
//			uuids[i] = UUID.fromString(ids[i]);
//		}
//
//		return roleService.find(uuids);
//	}
//
//	private List<Permission> getAllPermissionsByIds(String... ids) {
//
//		if (ids == null || ids.length == 0) {
//			return new ArrayList<Permission>();
//		}
//
//		UUID[] uuids = new UUID[ids.length];
//
//		for (int i = 0; i < uuids.length; i++) {
//			uuids[i] = UUID.fromString(ids[i]);
//		}
//
//		return permissionService.find(uuids);
//	}
//}
