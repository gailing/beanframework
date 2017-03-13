package com.beanframework.menu.web;

import java.text.MessageFormat;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import com.beanframework.common.AdminBaseController;
import com.beanframework.menu.NameDuplicatedException;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.service.MenuFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(MenuWebConstants.PATH_ROOT)
public class MenuController extends AdminBaseController {

	public static final String FORM_MODEL = "menu";
	public static final String FORM_PARENT = "parent";
	public static final String FORM_UUID = "uuid";
	public static final String FORM_PARENT_ID = "parentId";
	public static final String FORM_FROM_ID = "fromId";
	public static final String FORM_TO_ID = "toId";
	public static final String FORM_TO_INDEX = "toIndex";
	public static final String FORM_NAME = "name";
	public static final String FORM_DESCRIPTION = "description";
	public static final String FORM_PERMISSIONS = "permissions";
	public static final String FORM_ENABLED = "enabled";
	public static final String FORM_ICON = "icon";
	public static final String FORM_PATH = "path";
	public static final String FORM_MOVED_MENU = "movedMenu";

	public static final String FORM_FIELD_NAME = "name";
	public static final String FORM_FIELD_DESCRIPTION = "description";

	public static final String FORM_MESSAGE_SUCCESS_SAVE = "Menu has been successfully saved.";
	public static final String FORM_MESSAGE_SUCCESS_MOVED = "Menu has been successfully moved.";
	public static final String FORM_MESSAGE_NAME_EXISTS = "Name in use, please choose another name.";
	public static final MessageFormat FORM_MESSAGE_DELETE_SUCCESS = new MessageFormat("Menu [{0}] has been successfully deleted.");

	@Autowired
	private MenuFacade menuFacade;

	@RequestMapping
	public String list(Model model, @RequestParam Map<String, Object> allRequestParams) throws Exception {

		// Request Query Parameters
		String selectedMenuId = getParamValue(FORM_UUID, allRequestParams);

		String parentId = getParamValue(FORM_PARENT_ID, allRequestParams);

		Menu menu = new Menu();
		if (!StringUtils.isEmpty(selectedMenuId)) {
			menu = menuFacade.getMenuById(selectedMenuId);
		}

		if (!StringUtils.isEmpty(parentId)) {
			model.addAttribute(FORM_PARENT_ID, parentId);
		}

		model.addAttribute(FORM_MODEL, menu);

		return page(MenuWebConstants.PAGE, model, allRequestParams);
	}

	@RequestMapping(value = MenuWebConstants.PATH_SAVE, method = RequestMethod.POST)
	public String save(Model model, @RequestParam Map<String, Object> allRequestParams, final RedirectAttributes redirectAttributes) throws Exception {

		String uuid = getParamValue(FORM_UUID, allRequestParams);
		String name = getParamValue(FORM_NAME, allRequestParams);
		String description = getParamValue(FORM_DESCRIPTION, allRequestParams);
		String icon = getParamValue(FORM_ICON, allRequestParams);
		String path = getParamValue(FORM_PATH, allRequestParams);
		boolean enabled = parseBoolean(getParamValue(FORM_ENABLED, allRequestParams));
		String parentId = getParamValue(FORM_PARENT_ID, allRequestParams);
		String permissions = getParamValue(FORM_PERMISSIONS, allRequestParams);

		Menu menu = null;

		try {
			if (StringUtils.isNotEmpty(uuid)) {
				// Update
				menu = menuFacade.getMenuById(uuid);
			} else {
				// Create
				menu = new Menu();
			}

			menu.setName(name);
			menu.setDescription(description);
			menu.setEnabled(enabled);
			menu.setPath(path);
			menu.setPermissions(permissions);
			menu.setIcon(icon);

			menu = menuFacade.saveMenu(parentId, menu);
			model.addAttribute(PARAM_MESSAGE, FORM_MESSAGE_SUCCESS_SAVE);

		} catch (NameDuplicatedException e) {
			model.addAttribute(PARAM_ERROR, FORM_MESSAGE_NAME_EXISTS);
		} catch (Exception e) {
			model.addAttribute(PARAM_ERROR, e.getMessage());
		}

		model.addAttribute(FORM_UUID, menu.getUuid().toString());

		return redirect(MenuWebConstants.PATH_ROOT, model, redirectAttributes);
	}

	@RequestMapping(value = MenuWebConstants.PATH_MOVE, method = RequestMethod.POST)
	public String change(Model model, @RequestParam Map<String, Object> allRequestParams, final RedirectAttributes redirectAttributes) throws Exception {

		String fromId = getParamValue(FORM_FROM_ID, allRequestParams);
		String toId = getParamValue(FORM_TO_ID, allRequestParams);
		String toIndex = getParamValue(FORM_TO_INDEX, allRequestParams);

		try {
			// Save parent
			menuFacade.changePosition(fromId, toId, toIndex);
			model.addAttribute(PARAM_MESSAGE, FORM_MESSAGE_SUCCESS_MOVED);
		} catch (Exception e) {
			model.addAttribute(PARAM_ERROR, e.getMessage());
		}

		model.addAttribute(FORM_UUID, fromId);

		return redirect(MenuWebConstants.PATH_ROOT, model, redirectAttributes);
	}

	@RequestMapping(value = MenuWebConstants.PATH_DELETE, method = RequestMethod.POST)
	public String delete(Model model, @RequestParam Map<String, Object> allRequestParams, final RedirectAttributes redirectAttributes) throws Exception {

		String uuid = getParamValue(FORM_UUID, allRequestParams);

		try {
			menuFacade.deleteMenuById(uuid);
		} catch (Exception e) {
			model.addAttribute(PARAM_ERROR, e.getMessage());
		}

		return redirect(MenuWebConstants.PATH_ROOT, model, redirectAttributes);
	}

}
