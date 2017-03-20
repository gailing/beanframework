package com.beanframework.accountpolicy.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class AccountpolicySpecification {
	public static Specification<Accountpolicy> findByCriteria(final AccountpolicySearchCriteria searchCriteria) {

		return new Specification<Accountpolicy>() {

			@Override
			public Predicate toPredicate(Root<Accountpolicy> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotEmpty(searchCriteria.getName())) {
					predicates.add(cb.or(cb.like(root.get(Accountpolicy.NAME), "%" + searchCriteria.getName() + "%")));
				}
				if (StringUtils.isNotEmpty(searchCriteria.getDescription())) {
					predicates.add(cb.or(cb.like(root.get(Accountpolicy.DESCRIPTION), "%" + searchCriteria.getDescription() + "%")));
				}

				return cb.or(predicates.toArray(new Predicate[] {}));
			}
		};
	}
}