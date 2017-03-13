package com.beanframework.menu.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class MenuSpecification {
	public static Specification<Menu> findByCriteria(final MenuSearchCriteria searchCriteria) {

		return new Specification<Menu>() {

			@Override
			public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotEmpty(searchCriteria.getName())) {
					predicates.add(cb.or(cb.like(root.get(Menu.NAME), "%" + searchCriteria.getName() + "%")));
				}
				if (StringUtils.isNotEmpty(searchCriteria.getDescription())) {
					predicates.add(cb.or(cb.like(root.get(Menu.DESCRIPTION), "%" + searchCriteria.getDescription() + "%")));
				}

				return cb.or(predicates.toArray(new Predicate[] {}));
			}
		};
	}
}