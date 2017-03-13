//package com.beanframework.user.web.api;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//import org.apache.commons.lang3.StringUtils;
//import com.beanframework.platform.core.base.BaseController;
//import com.beanframework.platform.core.domain.TreeJson;
//import com.beanframework.platform.core.domain.TreeJsonState;
//import com.beanframework.user.domain.Permission;
//import com.beanframework.user.service.PermissionService;
//import com.beanframework.user.service.RoleService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/admin/api/user/permission")
//public class PermissionResource extends BaseController {
//	
//	public static final String FORM_ROLE_ID = "roleId";
//
//	@Autowired
//	private PermissionService permissionService;
//
//	@Autowired
//	private RoleService roleService;
//
//	@RequestMapping("/list")
//	public List<TreeJson> list(Model model, @RequestParam Map<String, Object> allRequestParams) {
//
//		String roleId = getParamValue(FORM_ROLE_ID, allRequestParams);
//
//		List<Permission> allPermissions = permissionService.findAllParent();
//		List<TreeJson> data = new ArrayList<TreeJson>();
//
//		for (Permission permission : allPermissions) {
//			if(StringUtils.isEmpty(roleId)){
//				data.add(convertToJson(permission, null));
//			}
//			else{
//				data.add(convertToJson(permission, UUID.fromString(roleId)));
//			}
//		}
//
//		return data;
//	}
//
//	public TreeJson convertToJson(Permission permission, UUID roleId) {
//
//		TreeJson parent = new TreeJson();
//
//		parent.setId(permission.getUuid().toString());
//		parent.setText(permission.getName());
//
//		if (roleId != null) {
//			boolean selected = isPermissionSelected(roleId, permission.getUuid());
//			parent.setState(new TreeJsonState(selected));
//		} else {
//			parent.setState(new TreeJsonState(false));
//		}
//
//		List<TreeJson> children = new ArrayList<TreeJson>();
//		if (!permission.getChilds().isEmpty()) {
//
//			for (Permission child : permission.getChilds()) {
//				children.add(convertToJson(child, roleId));
//			}
//		}
//		parent.setChildren(children);
//
//		return parent;
//	}
//
//	public boolean isPermissionSelected(UUID roleId, UUID permissionId) {
//		return roleService.isPermissionSelected(roleId, permissionId);
//	}
//}
