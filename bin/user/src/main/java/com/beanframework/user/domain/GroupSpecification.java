package com.beanframework.user.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class GroupSpecification {
	public static Specification<Group> findByCriteria(final GroupSearchCriteria searchCriteria) {

		return new Specification<Group>() {

			@Override
			public Predicate toPredicate(Root<Group> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotEmpty(searchCriteria.getName())) {
					predicates.add(cb.or(cb.like(root.get(Group.NAME), "%" + searchCriteria.getName() + "%")));
				}
				if (StringUtils.isNotEmpty(searchCriteria.getDescription())) {
					predicates.add(cb.or(cb.like(root.get(Group.DESCRIPTION), "%" + searchCriteria.getDescription() + "%")));
				}

				return cb.or(predicates.toArray(new Predicate[] {}));
			}
		};
	}
}