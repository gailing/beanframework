package com.beanframework.user.web;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import com.beanframework.common.AdminBaseController;
import com.beanframework.platform.core.domain.PageWrapper;
import com.beanframework.user.RoleWebConstants;
import com.beanframework.user.domain.Role;
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
@RequestMapping(RoleWebConstants.PATH_ROOT)
public class RoleController extends AdminBaseController {

	public static final String FORM_ROLE = "role";
	public static final String FORM_PERMISSIONS = "permissions";
	public static final String FORM_SELECTED_PERMISSIONS = "selectedPermissions";
	public static final String FORM_UUID = "uuid";
	public static final String FORM_USER_ID = "userId";
	public static final String FORM_GROUP_ID = "groupId";
	public static final String FORM_NAME = "name";
	public static final String FORM_DESCRIPTION = "description";

	public static final String FORM_FIELD_NAME = "name";
	public static final String FORM_FIELD_DESCRIPTION = "description";

	public static final String FORM_MESSAGE_SUCCESS_SAVE = "Role has been successfully saved.";
	public static final String FORM_MESSAGE_NAME_EXISTS = "Name in use, please choose another name.";
	public static final String FORM_MESSAGE_SUCESS_REMOVE_USER = "User has been successfully removed from this Role.";
	public static final String FORM_MESSAGE_SUCESS_REMOVE_GROUP = "Group has been successfully removed from this Role.";
	public static final String FORM_MESSAGE_DELETE_FAILED_USER_RELATIONSHIP = "This Role has user relationships, unable to delete this Role.";
	public static final String FORM_MESSAGE_DELETE_FAILED_GROUP_RELATIONSHIP = "This Role has group relationships, unable to delete this Role.";
	public static final MessageFormat FORM_MESSAGE_DELETE_SUCCESS = new MessageFormat("Role [{0}] has been successfully deleted.");

	@Autowired
	private UserFacade userFacade;

	@RequestMapping(RoleWebConstants.PATH_LIST)
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
		Page<Role> resultPage = userFacade.getRolePage(query, keyword, field, direction, pageNumber, RoleWebConstants.PAGE_LIST_SIZE);

		// Page
		String path = RoleWebConstants.PATH_ROOT + RoleWebConstants.PATH_LIST + "?" + PARAM_QUERY + "=" + query + "&" + PARAM_KEYWORD + "=" + keyword + "&" + PARAM_FIELD + "=" + field + "&"
				+ PARAM_DIRECTION + "=" + direction;

		model.addAttribute(MODEL_ATTRIBUTE_PAGE, new PageWrapper<Role>(resultPage, path));

		return page(RoleWebConstants.PAGE_LIST, model, allRequestParams);
	}

	@RequestMapping(RoleWebConstants.PATH_ADD)
	public String add(Model model, @RequestParam Map<String, Object> allRequestParams) {

		model.addAttribute(FORM_ROLE, userFacade.createRole());

		return page(RoleWebConstants.PAGE_FORM, model, allRequestParams);
	}

	@RequestMapping(RoleWebConstants.PATH_EDIT)
	public String edit(Model model, @RequestParam Map<String, Object> allRequestParams) {

		String uuid = getParamValue(FORM_UUID, allRequestParams);

		Role role = userFacade.getRoleWithUsers(UUID.fromString(uuid));
		model.addAttribute(FORM_ROLE, role);

		return page(RoleWebConstants.PAGE_FORM, model, allRequestParams);
	}

	@RequestMapping(value = RoleWebConstants.PATH_SAVE, method = RequestMethod.POST)
	public String save(Model model, @RequestParam Map<String, Object> allRequestParams, final RedirectAttributes redirectAttributes) throws Exception {

		String uuid = getParamValue(FORM_UUID, allRequestParams);
		String name = getParamValue(FORM_NAME, allRequestParams);
		String description = getParamValue(FORM_DESCRIPTION, allRequestParams);
		String permissionIds = getParamValue(FORM_SELECTED_PERMISSIONS, allRequestParams);

		Role role = userFacade.createRole();

		try {

			if (StringUtils.isNotEmpty(uuid)) {
				// Update
				role.setUuid(UUID.fromString(uuid));
			} else {
				// Create
				role.setName(name);
			}

			role.setDescription(description);

			if (permissionIds != null) {
				List<String> permissions = Arrays.asList(permissionIds.split(","));
				role.setPermissions(userFacade.savePermissions(permissions));
			}

			role = userFacade.saveRole(role);
			model.addAttribute(PARAM_MESSAGE, FORM_MESSAGE_SUCCESS_SAVE);
		} catch (Exception e) {
			model.addAttribute(PARAM_ERROR, e.getMessage());
		}

		model.addAttribute(FORM_UUID, role.getUuid().toString());

		return redirect(RoleWebConstants.PATH_ROOT + RoleWebConstants.PATH_EDIT, model, redirectAttributes);
	}

	@RequestMapping(value = RoleWebConstants.PATH_DELETE, method = RequestMethod.POST)
	public String delete(Model model, @RequestParam Map<String, Object> allRequestParams, final RedirectAttributes redirectAttributes) throws Exception {

		String uuid = getParamValue(FORM_UUID, allRequestParams);

		try {
			Role role = userFacade.deleteRoleById(UUID.fromString(uuid));

			model.addAttribute(PARAM_MESSAGE, FORM_MESSAGE_DELETE_SUCCESS.format(new Object[] { role.getName() }));

			return redirect(RoleWebConstants.PATH_ROOT + RoleWebConstants.PATH_LIST, model, redirectAttributes);
		} catch (Exception e) {
			model.addAttribute(PARAM_ERROR, e.getMessage());
			model.addAttribute(FORM_UUID, uuid);

			return redirect(RoleWebConstants.PATH_ROOT + RoleWebConstants.PATH_EDIT, model, redirectAttributes);
		}
	}

	@RequestMapping(value = RoleWebConstants.PATH_REMOVE_GROUP, method = RequestMethod.POST)
	public String removeGroup(Model model, @RequestParam Map<String, Object> allRequestParams, final RedirectAttributes redirectAttributes) throws Exception {

		String uuid = getParamValue(FORM_UUID, allRequestParams);
		String groupId = getParamValue(FORM_GROUP_ID, allRequestParams);

		try {
			userFacade.removeRoleFromGroup(groupId, uuid);
			model.addAttribute(PARAM_MESSAGE, FORM_MESSAGE_SUCESS_REMOVE_GROUP);
		} catch (Exception e) {
			model.addAttribute(PARAM_ERROR, e.getMessage());
		}

		model.addAttribute(FORM_UUID, uuid);

		return redirect(RoleWebConstants.PATH_ROOT + RoleWebConstants.PATH_EDIT, model, redirectAttributes);
	}

	@RequestMapping(value = RoleWebConstants.PATH_REMOVE_USER, method = RequestMethod.POST)
	public String removeUser(Model model, @RequestParam Map<String, Object> allRequestParams, final RedirectAttributes redirectAttributes) throws Exception {

		String uuid = getParamValue(FORM_UUID, allRequestParams);
		String userId = getParamValue(FORM_USER_ID, allRequestParams);

		try {
			userFacade.removeRoleFromUser(userId, uuid);

			model.addAttribute(PARAM_MESSAGE, FORM_MESSAGE_SUCESS_REMOVE_USER);
		} catch (Exception e) {
			model.addAttribute(PARAM_ERROR, e.getMessage());
		}

		model.addAttribute(FORM_UUID, uuid);

		return redirect(RoleWebConstants.PATH_ROOT + RoleWebConstants.PATH_EDIT, model, redirectAttributes);
	}
}