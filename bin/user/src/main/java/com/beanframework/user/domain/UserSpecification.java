package com.beanframework.user.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
	public static Specification<User> findByCriteria(final UserSearchCriteria searchCriteria) {

		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotEmpty(searchCriteria.getUsername())) {
					predicates.add(cb.or(cb.like(root.get(User.USERNAME), "%" + searchCriteria.getUsername() + "%")));
				}
				if (StringUtils.isNotEmpty(searchCriteria.getFirstName())) {
					predicates.add(cb.or(cb.like(root.get(User.FIRST_NAME), "%" + searchCriteria.getFirstName() + "%")));
				}
				if (StringUtils.isNotEmpty(searchCriteria.getLastName())) {
					predicates.add(cb.or(cb.like(root.get(User.LAST_NAME), "%" + searchCriteria.getLastName() + "%")));
				}
				if (StringUtils.isNotEmpty(searchCriteria.getEmail())) {
					predicates.add(cb.or(cb.like(root.get(User.EMAIL), "%" + searchCriteria.getEmail() + "%")));
				}

				return cb.or(predicates.toArray(new Predicate[] {}));
			}
		};
	}
}