package com.beanframework.user.web.api;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.beanframework.menu.service.MenuFacade;
import com.beanframework.platform.core.base.BaseController;
import com.beanframework.platform.core.domain.TreeJson;
import com.beanframework.platform.core.domain.TreeJsonState;
import com.beanframework.user.domain.Permission;
import com.beanframework.user.domain.Role;
import com.beanframework.user.service.UserFacade;
import com.beanframework.user.web.RoleWebConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RoleWebConstants.PATH_API_ROOT)
public class RoleResource extends BaseController {

	public static final String FORM_NAME = "name";
	public static final String FORM_ROLE_UUID = "roleUuid";

	@Autowired
	private UserFacade userFacade;

	@Autowired
	private MenuFacade menuFacade;

	@RequestMapping(RoleWebConstants.PATH_API_CHECK_NAME)
	public String checkname(Model model, @RequestParam Map<String, Object> allRequestParams) {
		String name = getParamValue(FORM_NAME, allRequestParams);
		boolean exists = userFacade.isRoleNameExists(name);
		return exists ? "false" : "true";
	}

	@RequestMapping(RoleWebConstants.PATH_PERMISSION)
	public List<TreeJson> permission(Model model, @RequestParam Map<String, Object> allRequestParams) {
		String roleUuid = getParamValue(FORM_ROLE_UUID, allRequestParams);

		List<TreeJson> tree = menuFacade.getTreeMenu(null);

		if (roleUuid == null) {
			return tree;
		} else {

			Role role = userFacade.getRoleById(UUID.fromString(roleUuid));

			if (role.getPermissions() != null && !role.getPermissions().isEmpty()) {
				filterTreeSelection(tree, role.getPermissions());
			}

			return tree;
		}
	}

	public void filterTreeSelection(List<TreeJson> tree, List<Permission> permissions) {

		for (int i = 0; i < tree.size(); i++) {
			for (Permission permission : permissions) {
				if(permission.getName().equals(tree.get(i).getId())){
					tree.get(i).setState(new TreeJsonState(true));
				}
			}

			if (tree.get(i).hasChildren()) {
				filterTreeSelection(tree.get(i).getChildren(), permissions);
			}
		}
	}

}