package com.beanframework.menu.web.api;

import java.util.List;
import java.util.Map;

import com.beanframework.menu.service.MenuFacade;
import com.beanframework.menu.web.MenuWebConstants;
import com.beanframework.platform.core.base.BaseController;
import com.beanframework.platform.core.domain.TreeJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(MenuWebConstants.PATH_API_ROOT)
public class MenuResource extends BaseController {

	public static final String FORM_UUID = "uuid";

	@Autowired
	private MenuFacade menuFacade;

	@RequestMapping
	public List<TreeJson> list(Model model, @RequestParam Map<String, Object> allRequestParams) {
		String selectedId = getParamValue(FORM_UUID, allRequestParams);

		return menuFacade.getTreeMenu(selectedId);
	}

}