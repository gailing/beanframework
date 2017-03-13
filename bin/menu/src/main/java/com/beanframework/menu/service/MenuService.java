package com.beanframework.menu.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.beanframework.menu.MenuConstants;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuRepository;
import com.beanframework.menu.domain.MenuSearchCriteria;
import com.beanframework.menu.domain.MenuSpecification;
import com.beanframework.platform.core.base.BaseService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@CacheConfig(cacheNames = MenuConstants.CACHE_MENUS)
public class MenuService extends BaseService {

	@Autowired
	private MenuRepository menuRepository;

	public boolean isMenuExists(UUID uuid) {
		return menuRepository.isMenuExists(uuid);
	}

	public boolean isNameExists(String name) {
		return menuRepository.isNameExists(name);
	}
	
	public boolean isPathExists(String name) {
		return menuRepository.isPathExists(name);
	}

	public Menu findOne(UUID uuid) {

		if (!isMenuExists(uuid)) {
			return null;
		}

		return menuRepository.findOne(uuid);
	}

	public List<Menu> find(UUID... ids) {

		List<Menu> menus = new ArrayList<Menu>();

		for (UUID uuid : ids) {
			menus.add(findOne(uuid));
		}

		return menus;
	}

	public Menu findByName(String name) {

		if (!isNameExists(name)) {
			return null;
		}

		return menuRepository.findByName(name);
	}
	
	public Menu findByPath(String path) {

		if (!isPathExists(path)) {
			return null;
		}

		Menu menu = menuRepository.findByPath(path);
		return menu;
	}

	public List<Menu> findAll() {
		return menuRepository.findAll(new Sort(Sort.Direction.ASC, Menu.SORT));
	}
	
	public Menu getRootMenu(){
		return menuRepository.findRootMenu();
	}

	@Cacheable
	public Menu findAllMenu(boolean filterWithVisible) {
		Menu rootMenu = menuRepository.findRootMenu();
		
		if(rootMenu == null){
			return null;
		}
		
		initializeMenuChilds(rootMenu, filterWithVisible);
		
		return rootMenu;
	}
	
	public Menu findPermissionedMenu(List<String> permissions) {
		Menu rootMenu = getRootMenu();
		
		List<Menu> childs = findAllEnabledVisibleMenu(rootMenu.getUuid());
		filterWithGrantedPermission(childs, permissions);
		rootMenu.setChilds(childs);
		
		for (Menu menu : rootMenu.getChilds()) {
			findEnabledVisibleChildMenu(menu, permissions);
		}
		return rootMenu;
	}
	
	//////////////////////////////////////////////////
	// Helpers
	//////////////////////////////////////////////////
	private void initializeMenuChilds(Menu rootMenu, boolean filterWithVisible){
		
		Hibernate.initialize(rootMenu.getChilds());
		
		if(filterWithVisible){
			filterWithVisible(rootMenu.getChilds());
		}
		
		for (Menu menu : rootMenu.getChilds()) {
			Hibernate.initialize(menu.getChilds());
			
			if(filterWithVisible){
				filterWithVisible(menu.getChilds());
			}
			
			if(!menu.getChilds().isEmpty()){
				initializeMenuChilds(menu, filterWithVisible);
			}
		}
	}
	
	private void filterWithVisible(List<Menu> menuList){
		for (Iterator<Menu> iterator = menuList.iterator(); iterator.hasNext();) {
		    Menu menu = iterator.next();
		    if (!menu.isVisible()) {
		        // Remove the current element from the iterator and the list.
		        iterator.remove();
		    }
		}
	}
	
	private void filterWithGrantedPermission(List<Menu> menuList, List<String> permissions){
		
		for (Iterator<Menu> iterator = menuList.iterator(); iterator.hasNext();) {
		    Menu menu = iterator.next();
		    if (!isGrantedPermission(menu, permissions)) {
		        // Remove the current element from the iterator and the list.
		        iterator.remove();
		    }
		}
	}
	
	private boolean isGrantedPermission(Menu menu, List<String> permissions){
		for (String permission : permissions) {
			if(menu.getUuid().toString().equals(permission)){
				return true;
			}
		}
		return false;
	}
	
	private void findEnabledVisibleChildMenu(Menu parentMenu, List<String> permissions){
		
		List<Menu> childs = findAllEnabledVisibleMenu(parentMenu.getUuid());
		filterWithGrantedPermission(childs, permissions);
		parentMenu.setChilds(childs);
		
		for (Menu menu : parentMenu.getChilds()) {
			findEnabledVisibleChildMenu(menu, permissions);
		}
	}
	
//	private void initializeMenuParent(Menu rootMenu) {
//
//		Hibernate.initialize(rootMenu.getParent());
//		
//		if(rootMenu.getParent() != null){
//			initializeMenuParent(rootMenu.getParent().getParent());
//		}
//	}
	
	public List<Menu> getAllMenuParent(Menu rootMenu){
		
		List<Menu> menuList = new ArrayList<Menu>();
		
		Hibernate.initialize(rootMenu.getParent());
		if(rootMenu.getParent() != null){
			menuList.add(rootMenu.getParent());
			menuList.addAll(getAllMenuParent(rootMenu.getParent()));
		}
		
		return menuList;
	}
	
	@Cacheable
	private List<Menu> findAllEnabledVisibleMenu(UUID uuid){
		return menuRepository.findAllEnabledVisibleMenu(uuid);
	}

	public List<Menu> findMenuByParentId(UUID parentId) {
		return menuRepository.findAllMenu(parentId);
	}

	public Page<Menu> findAll(MenuSearchCriteria searchCriteria, Pageable pageable) {
		if (searchCriteria == null) {
			return findAll(pageable);
		} else {
			return findAll(MenuSpecification.findByCriteria(searchCriteria), pageable);
		}
	}

	private Page<Menu> findAll(Pageable pageable) {
		return menuRepository.findAll(pageable);
	}

	private Page<Menu> findAll(Specification<Menu> specification, Pageable pageable) {
		return menuRepository.findAll(specification, pageable);
	}

	@CacheEvict(allEntries = true)
	@Transactional(readOnly = false)
	public Menu save(Menu menu) {
		return menuRepository.save(menu);
	}

	@CacheEvict(allEntries = true)
	@Transactional(readOnly = false)
	public void delete(UUID uuid) {
		menuRepository.delete(uuid);
	}

	@CacheEvict(allEntries = true)
	@Transactional(readOnly = false)
	public void deleteByName(String name) {
		menuRepository.deleteByName(name);
	}
	
	@CacheEvict(allEntries = true)
	@Transactional(readOnly = false)
	public void deleteAll() {
		menuRepository.deleteAll();
	}

	@CacheEvict(allEntries = true)
	@Transactional(readOnly = false)
	public void savePosition(UUID parentId, UUID fromId, int toIndex) {

		List<Menu> currentLevelMenuList;

		if (parentId == null) {
			currentLevelMenuList = findMenuByParentId(getRootMenu().getUuid());
		} else {
			currentLevelMenuList = findMenuByParentId(parentId);
		}

		menuRepository.save(changePosition(currentLevelMenuList, fromId, toIndex));
	}

	private List<Menu> changePosition(List<Menu> menuList, UUID fromId, int toIndex) {

		int topIndex;
		int bottomIndex;

		int fromIndex = 0;
		for (int i = 0; i < menuList.size(); i++) {
			if (menuList.get(i).getUuid().equals(fromId)) {
				fromIndex = i;
			}
		}

		// Move left to right
		if (fromIndex < toIndex) {
			topIndex = fromIndex;
			bottomIndex = toIndex;

			int currentIndex = topIndex;

			while (currentIndex != bottomIndex) {
				Collections.swap(menuList, currentIndex, currentIndex + 1);
				currentIndex++;
			}
		} else {
			// Move right to left
			topIndex = toIndex;
			bottomIndex = fromIndex;

			int currentIndex = bottomIndex;

			while (currentIndex != topIndex) {
				Collections.swap(menuList, currentIndex, currentIndex - 1);
				currentIndex--;
			}
		}

		for (int i = 0; i < menuList.size(); i++) {
			menuList.get(i).setSort(i);
		}

		return menuList;
	}
}
