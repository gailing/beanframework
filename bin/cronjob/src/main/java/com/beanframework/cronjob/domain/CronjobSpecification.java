package com.beanframework.cronjob.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class CronjobSpecification {
	public static Specification<Cronjob> findByCriteria(final CronjobSearchCriteria searchCriteria) {

		return new Specification<Cronjob>() {

			@Override
			public Predicate toPredicate(Root<Cronjob> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotEmpty(searchCriteria.getJobGroup())) {
					predicates.add(cb.or(cb.like(root.get(Cronjob.JOB_GROUP), "%" + searchCriteria.getJobGroup() + "%")));
				}
				if (StringUtils.isNotEmpty(searchCriteria.getJobName())) {
					predicates.add(cb.or(cb.like(root.get(Cronjob.JOB_NAME), "%" + searchCriteria.getJobName() + "%")));
				}
				if (StringUtils.isNotEmpty(searchCriteria.getDescription())) {
					predicates.add(cb.or(cb.like(root.get(Cronjob.DESCRIPTION), "%" + searchCriteria.getDescription() + "%")));
				}

				return cb.or(predicates.toArray(new Predicate[] {}));
			}
		};
	}
}