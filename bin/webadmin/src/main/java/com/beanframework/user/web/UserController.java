package com.beanframework.user.web;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import com.beanframework.common.AdminBaseController;
import com.beanframework.platform.core.domain.PageWrapper;
import com.beanframework.user.UserWebConstants;
import com.beanframework.user.domain.Group;
import com.beanframework.user.domain.Role;
import com.beanframework.user.domain.User;
import com.beanframework.user.service.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(UserWebConstants.PATH_ROOT)
public class UserController extends AdminBaseController {

	public static final String FORM_USER = "user";
	public static final String FORM_GROUPS = "groups";
	public static final String FORM_ROLES = "roles";
	public static final String FORM_SELECTED_GROUPS = "selectedGroups";
	public static final String FORM_SELECTED_ROLES = "selectedRoles";
	public static final String FORM_UUID = "uuid";
	public static final String FORM_USERNAME = "username";
	public static final String FORM_PASSWORD = "password";
	public static final String FORM_ENABLED = "enabled";
	public static final String FORM_ACCOUNT_NON_EXPIRED = "accountNonExpired";
	public static final String FORM_ACCOUNT_NON_LOCKED = "accountNonLocked";
	public static final String FORM_CREDENTIALS_NON_EXPIRED = "credentialsNonExpired";
	public static final String FORM_FIRSTNAME = "firstName";
	public static final String FORM_LASTNAME = "lastName";
	public static final String FORM_EMAIL = "email";

	public static final String FORM_MESSAGE_SUCCESS_SAVE = "User has been successfully saved.";
	public static final MessageFormat FORM_MESSAGE_DELETE_SUCCESS = new MessageFormat("User [{0}] has been successfully deleted.");

	@Autowired
	private UserFacade userFacade;

	public static List<UUID> getSelectedGroupIds(List<Group> groups) {

		if (groups == null) {
			return new ArrayList<UUID>();
		}

		List<UUID> ids = new ArrayList<UUID>();
		for (Group group : groups) {
			ids.add(group.getUuid());
		}
		return ids;
	}

	public static List<UUID> getSelectedRoleIds(List<Role> roles) {

		if (roles == null) {
			return new ArrayList<UUID>();
		}

		List<UUID> ids = new ArrayList<UUID>();
		for (Role role : roles) {
			ids.add(role.getUuid());
		}
		return ids;
	}

	@RequestMapping(UserWebConstants.PATH_LIST)
	public String list(Model model, @RequestParam Map<String, Object> allRequestParams) {

		// Request Query Parameters
		String query = getParamValue(PARAM_QUERY, allRequestParams);
		String keyword = getParamValue(PARAM_KEYWORD, allRequestParams);
		String field = getParamValue(PARAM_FIELD, allRequestParams);
		String direction = getParamValue(PARAM_DIRECTION, allRequestParams);
		String page = getParamValue(PARAM_PAGE, allRequestParams);
		int pageNumber = 1;

		if (StringUtils.isNotEmpty(page)) {
			pageNumber = Integer.valueOf(page);
		}

		// Result
		Page<User> resultPage = userFacade.getUserPage(query, keyword, field, direction, pageNumber, UserWebConstants.PAGE_LIST_SIZE);

		// Page
		String path = UserWebConstants.PATH_ROOT + UserWebConstants.PATH_LIST + "?" + PARAM_QUERY + "=" + query + "&" + PARAM_KEYWORD + "=" + keyword + "&" + PARAM_FIELD + "=" + field + "&"
				+ PARAM_DIRECTION + "=" + direction;

		model.addAttribute(MODEL_ATTRIBUTE_PAGE, new PageWrapper<User>(resultPage, path));

		return page(UserWebConstants.PAGE_LIST, model, allRequestParams);
	}

	@RequestMapping(value = { UserWebConstants.PATH_ADD })
	public String add(Model model, @RequestParam Map<String, Object> allRequestParams) {

		model.addAttribute(FORM_USER, userFacade.createUser());

		model.addAttribute(FORM_GROUPS, userFacade.getAllGroups());
		model.addAttribute(FORM_ROLES, userFacade.getAllRoles());

		return page(UserWebConstants.PAGE_FORM, model, allRequestParams);
	}

	@RequestMapping(value = { UserWebConstants.PATH_EDIT })
	public String edit(Model model, @RequestParam Map<String, Object> allRequestParams) {

		String uuid = getParamValue(FORM_UUID, allRequestParams);

		User user = userFacade.getUserByUuId(UUID.fromString(uuid));

		model.addAttribute(FORM_SELECTED_GROUPS, getSelectedGroupIds(user.getGroups()));
		model.addAttribute(FORM_SELECTED_ROLES, getSelectedRoleIds(user.getRoles()));
		model.addAttribute(FORM_USER, user);

		model.addAttribute(FORM_GROUPS, userFacade.getAllGroups());
		model.addAttribute(FORM_ROLES, userFacade.getAllRoles());

		return page(UserWebConstants.PAGE_FORM, model, allRequestParams);
	}

	@RequestMapping(value = UserWebConstants.PATH_SAVE, method = RequestMethod.POST)
	public String save(Model model, @RequestParam Map<String, Object> allRequestParams, @RequestParam(value = FORM_SELECTED_GROUPS, required = false) String[] selectedGroupIds,
			@RequestParam(value = FORM_SELECTED_ROLES, required = false) String[] selectedRoleIds, final RedirectAttributes redirectAttributes) {

		String uuid = getParamValue(FORM_UUID, allRequestParams);
		String username = getParamValue(FORM_USERNAME, allRequestParams);
		String password = getParamValue(FORM_PASSWORD, allRequestParams);
		boolean enabled = parseBoolean(getParamValue(FORM_ENABLED, allRequestParams));
		boolean accountNonExpired = parseBoolean(getParamValue(FORM_ACCOUNT_NON_EXPIRED, allRequestParams));
		boolean accountNonLocked = parseBoolean(getParamValue(FORM_ACCOUNT_NON_LOCKED, allRequestParams));
		boolean credentialsNonExpired = parseBoolean(getParamValue(FORM_CREDENTIALS_NON_EXPIRED, allRequestParams));
		String firstName = getParamValue(FORM_FIRSTNAME, allRequestParams);
		String lastName = getParamValue(FORM_LASTNAME, allRequestParams);
		String email = getParamValue(FORM_EMAIL, allRequestParams);

		User user = userFacade.createUser();

		try {
			if (StringUtils.isNotEmpty(uuid)) {
				// Update
				user.setUuid(UUID.fromString(uuid));
			} else {
				// Create
				user.setUsername(username);
			}

			user.setPassword(password);
			user.setEnabled(enabled);
			user.setAccountNonExpired(accountNonExpired);
			user.setAccountNonLocked(accountNonLocked);
			user.setCredentialsNonExpired(credentialsNonExpired);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			user.setGroups(userFacade.getGroupsByIds(convertToUUID(selectedGroupIds)));
			user.setRoles(userFacade.getRolesByIds(convertToUUID(selectedRoleIds)));

			user = userFacade.saveUser(user);
			model.addAttribute(PARAM_MESSAGE, FORM_MESSAGE_SUCCESS_SAVE);
			model.addAttribute(FORM_UUID, user.getUuid().toString());
		} catch (Exception e) {
			model.addAttribute(PARAM_ERROR, e.getMessage());
		}

		model.addAttribute(FORM_UUID, user.getUuid().toString());

		return redirect(UserWebConstants.PATH_ROOT + UserWebConstants.PATH_EDIT, model, redirectAttributes);
	}

	@RequestMapping(value = UserWebConstants.PATH_DELETE, method = RequestMethod.POST)
	public String delete(Model model, @RequestParam Map<String, Object> allRequestParams, final RedirectAttributes redirectAttributes) {

		String uuid = getParamValue(FORM_UUID, allRequestParams);

		try {
			User user = userFacade.deleteUserById(UUID.fromString(uuid));

			model.addAttribute(PARAM_MESSAGE, FORM_MESSAGE_DELETE_SUCCESS.format(new Object[] { user.getUsername() }));

			return redirect(UserWebConstants.PATH_ROOT + UserWebConstants.PATH_LIST, model, redirectAttributes);
		} catch (Exception e) {
			model.addAttribute(PARAM_ERROR, e.getMessage());
			model.addAttribute(FORM_UUID, uuid);

			return redirect(UserWebConstants.PATH_ROOT + UserWebConstants.PATH_EDIT, model, redirectAttributes);
		}
	}
}