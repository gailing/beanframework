package com.beanframework.platform.core.domain;
//package de.lekkerland.lldm.common.domain;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Sort;
//
//import de.lekkerland.lldm.user.constant.UserConstant;
//
//public class PageSession {
//	
//	public static final String NAME = "PageSession";
//	public static final String RESTORE = "restore";
//
//	private boolean last;
//	private long totalElements;
//	private int totalPages;
//	private int size = UserConstant.PAGE_LIST_SIZE;
//	private int number;
//	private Sort sort;
//	private boolean first;
//	private int numberOfElements;
//	private Object searchCriteria;
//	
//	public PageSession(Page<?> page, Object searchCriteria) {
//		this.last = page.isLast();
//		this.totalElements = page.getTotalElements();
//		this.totalPages = page.getTotalPages();
//		this.number = page.getNumber();
//		this.sort = page.getSort();
//		this.first = page.isFirst();
//		this.numberOfElements = page.getNumberOfElements();
//		this.searchCriteria = searchCriteria;
//	}
//
//	public boolean isLast() {
//		return last;
//	}
//
//	public void setLast(boolean last) {
//		this.last = last;
//	}
//
//	public long getTotalElements() {
//		return totalElements;
//	}
//
//	public void setTotalElements(long totalElements) {
//		this.totalElements = totalElements;
//	}
//
//	public int getTotalPages() {
//		return totalPages;
//	}
//
//	public void setTotalPages(int totalPages) {
//		this.totalPages = totalPages;
//	}
//
//	public int getSize() {
//		return size;
//	}
//
//	public void setSize(int size) {
//		this.size = size;
//	}
//
//	public int getNumber() {
//		return number;
//	}
//
//	public void setNumber(int number) {
//		this.number = number;
//	}
//
//	public Sort getSort() {
//		return sort;
//	}
//
//	public void setSort(Sort sort) {
//		this.sort = sort;
//	}
//
//	public boolean isFirst() {
//		return first;
//	}
//
//	public void setFirst(boolean first) {
//		this.first = first;
//	}
//
//	public int getNumberOfElements() {
//		return numberOfElements;
//	}
//
//	public void setNumberOfElements(int numberOfElements) {
//		this.numberOfElements = numberOfElements;
//	}
//
//	public Object getSearchCriteria() {
//		return searchCriteria;
//	}
//
//	public void setSearchCriteria(Object searchCriteria) {
//		this.searchCriteria = searchCriteria;
//	}
//
//}
