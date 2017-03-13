package com.beanframework.user.web;

import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import com.beanframework.common.AdminBaseController;
import com.beanframework.platform.core.domain.PageWrapper;
import com.beanframework.user.GroupWebConstants;
import com.beanframework.user.domain.Group;
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
@RequestMapping(GroupWebConstants.PATH_ROOT)
public class GroupController extends AdminBaseController {

	public static final String FORM_GROUP = "group";
	public static final String FORM_ROLES = "roles";
	public static final String FORM_SELECTED_ROLES = "selectedRoles";
	public static final String FORM_UUID = "uuid";
	public static final String FORM_USER_ID = "userId";
	public static final String FORM_NAME = "name";
	public static final String FORM_DESCRIPTION = "description";

	public static final String FORM_FIELD_NAME = "name";
	public static final String FORM_FIELD_DESCRIPTION = "description";

	public static final String FORM_MESSAGE_SUCCESS_SAVE = "Group has been successfully saved.";
	public static final String FORM_MESSAGE_NAME_EXISTS = "Name in use, please choose another name.";
	public static final String FORM_MESSAGE_SUCESS_REMOVE_USER = "User has been successfully removed from this Group.";
	public static final String FORM_MESSAGE_DELETE_FAILED_USER_RELATIONSHIP = "This Group has user relationships, unable to delete this Group.";
	public static final MessageFormat FORM_MESSAGE_DELETE_SUCCESS = new MessageFormat("Group [{0}] has been successfully deleted.");

	@Autowired
	private UserFacade userFacade;

	@RequestMapping(GroupWebConstants.PATH_LIST)
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
		Page<Group> resultPage = userFacade.getGroupPage(query, keyword, field, direction, pageNumber, GroupWebConstants.PAGE_LIST_SIZE);

		// Page
		String path = GroupWebConstants.PATH_ROOT + GroupWebConstants.PATH_LIST + "?" + PARAM_QUERY + "=" + query + "&" + PARAM_KEYWORD + "=" + keyword + "&" + PARAM_FIELD + "=" + field + "&"
				+ PARAM_DIRECTION + "=" + direction;

		model.addAttribute(MODEL_ATTRIBUTE_PAGE, new PageWrapper<Group>(resultPage, path));

		return page(GroupWebConstants.PAGE_LIST, model, allRequestParams);
	}

	@RequestMapping(GroupWebConstants.PATH_ADD)
	public String add(Model model, @RequestParam Map<String, Object> allRequestParams) {

		model.addAttribute(FORM_GROUP, userFacade.createGroup());

		model.addAttribute(FORM_ROLES, userFacade.getAllRoles());

		return page(GroupWebConstants.PAGE_FORM, model, allRequestParams);
	}

	@RequestMapping(GroupWebConstants.PATH_EDIT)
	public String edit(Model model, @RequestParam Map<String, Object> allRequestParams) {

		String uuid = getParamValue(FORM_UUID, allRequestParams);

		Group group = userFacade.getGroupWithUsersAndRoles(UUID.fromString(uuid));

		model.addAttribute(FORM_SELECTED_ROLES, UserController.getSelectedRoleIds(group.getRoles()));
		model.addAttribute(FORM_GROUP, group);

		model.addAttribute(FORM_ROLES, userFacade.getAllRoles());

		return page(GroupWebConstants.PAGE_FORM, model, allRequestParams);
	}

	@RequestMapping(value = GroupWebConstants.PATH_SAVE, method = RequestMethod.POST)
	public String save(Model model, @RequestParam Map<String, Object> allRequestParams, @RequestParam(value = FORM_SELECTED_ROLES, required = false) String[] selectedRoleIds,
			final RedirectAttributes redirectAttributes) {

		String uuid = getParamValue(FORM_UUID, allRequestParams);
		String name = getParamValue(FORM_NAME, allRequestParams);
		String description = getParamValue(FORM_DESCRIPTION, allRequestParams);

		Group group = userFacade.createGroup();

		try {

			if (StringUtils.isNotEmpty(uuid)) {
				// Update
				group.setUuid(UUID.fromString(uuid));
				group = userFacade.getGroupById(UUID.fromString(uuid));
			} else {
				// Create
				group = userFacade.createGroup();
				group.setName(name);
			}

			group.setDescription(description);
			group.setSelectedRoleIds(convertToUUID(selectedRoleIds));

			group = userFacade.saveGroup(group);
			model.addAttribute(PARAM_MESSAGE, FORM_MESSAGE_SUCCESS_SAVE);
		} catch (Exception e) {
			model.addAttribute(PARAM_ERROR, e.getMessage());
		}

		model.addAttribute(FORM_UUID, group.getUuid().toString());

		return redirect(GroupWebConstants.PATH_ROOT + GroupWebConstants.PATH_EDIT, model, redirectAttributes);
	}

	@RequestMapping(value = GroupWebConstants.PATH_DELETE, method = RequestMethod.POST)
	public String delete(Model model, @RequestParam Map<String, Object> allRequestParams, final RedirectAttributes redirectAttributes) {

		String uuid = getParamValue(FORM_UUID, allRequestParams);

		try {
			Group group = userFacade.deleteGroupById(UUID.fromString(uuid));

			model.addAttribute(PARAM_MESSAGE, FORM_MESSAGE_DELETE_SUCCESS.format(new Object[] { group.getName() }));

			return redirect(GroupWebConstants.PATH_ROOT + GroupWebConstants.PATH_LIST, model, redirectAttributes);
		} catch (Exception e) {
			model.addAttribute(PARAM_ERROR, e.getMessage());
			model.addAttribute(FORM_UUID, uuid);

			return redirect(GroupWebConstants.PATH_ROOT + GroupWebConstants.PATH_EDIT, model, redirectAttributes);
		}
	}

	@RequestMapping(value = GroupWebConstants.PATH_REMOVE_USER, method = RequestMethod.POST)
	public String remove(Model model, @RequestParam Map<String, Object> allRequestParams, final RedirectAttributes redirectAttributes) {

		String uuid = getParamValue(FORM_UUID, allRequestParams);
		String userId = getParamValue(FORM_USER_ID, allRequestParams);

		try {
			userFacade.removeGroupFromUser(userId, uuid);

			model.addAttribute(PARAM_MESSAGE, FORM_MESSAGE_SUCESS_REMOVE_USER);
		} catch (Exception e) {
			model.addAttribute(PARAM_ERROR, e.getMessage());
		}

		model.addAttribute(FORM_UUID, uuid);

		return redirect(GroupWebConstants.PATH_ROOT + GroupWebConstants.PATH_EDIT, model, redirectAttributes);
	}
}