package com.beanframework.user.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class RoleSpecification {
	public static Specification<Role> findByCriteria(final RoleSearchCriteria searchCriteria) {

		return new Specification<Role>() {

			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotEmpty(searchCriteria.getName())) {
					predicates.add(cb.or(cb.like(root.get(Role.NAME), "%" + searchCriteria.getName() + "%")));
				}
				if (StringUtils.isNotEmpty(searchCriteria.getDescription())) {
					predicates.add(cb.or(cb.like(root.get(Role.DESCRIPTION), "%" + searchCriteria.getDescription() + "%")));
				}

				return cb.or(predicates.toArray(new Predicate[] {}));
			}
		};
	}
}