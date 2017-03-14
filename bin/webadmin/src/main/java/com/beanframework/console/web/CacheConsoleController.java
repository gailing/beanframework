package com.beanframework.console.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.beanframework.common.AdminBaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

@Controller
@RequestMapping(ConsoleWebConstants.PATH_ROOT)
public class CacheConsoleController extends AdminBaseController {

	@Autowired
	private CacheManager cacheManager;

	@RequestMapping(value = { ConsoleWebConstants.PATH_CACHE }, method = { RequestMethod.GET, RequestMethod.POST })
	public String cache(Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {

		
		List<Cache> caches = new ArrayList<Cache>();
		
		for (String name : cacheManager.getCacheNames()) {
			Cache cache = cacheManager.getCache(name);
			caches.add(cache);
		}
		
		model.addAttribute("caches", caches);

		return page(ConsoleWebConstants.PAGE_CACHE, model, allRequestParams);
	}

	@RequestMapping(value = { ConsoleWebConstants.PATH_CACHE_CLEAR }, method = { RequestMethod.GET, RequestMethod.POST })
	public String clear(Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		cacheManager.clearAll();
		model.addAttribute(PARAM_MESSAGE, "All caches has been cleared");
		return redirect(ConsoleWebConstants.PATH_ROOT + ConsoleWebConstants.PATH_CACHE, model, redirectAttributes);
	}

}
