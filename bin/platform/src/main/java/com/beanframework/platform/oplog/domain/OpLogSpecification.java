package com.beanframework.platform.oplog.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class OpLogSpecification {
	public static Specification<OpLog> findByCriteria(final OpLogSearchCriteria searchCriteria) {

		return new Specification<OpLog>() {

			@Override
			public Predicate toPredicate(Root<OpLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				
				Predicate validFrom = cb.or(cb.greaterThanOrEqualTo(root.get(OpLog.CREATED_DATE), searchCriteria.getCreatedDateFrom()));
				Predicate validTo = cb.or(cb.lessThanOrEqualTo(root.get(OpLog.CREATED_DATE), searchCriteria.getCreatedDateTo()));

				if(searchCriteria.getCreatedDateFrom() != null && searchCriteria.getCreatedDateTo() != null){
					
					if (StringUtils.isNotEmpty(searchCriteria.getCreatedBy())) {
						predicates.add(cb.and(cb.like(root.get(OpLog.CREATED_BY), "%" + searchCriteria.getCreatedBy() + "%"),cb.and(validFrom, validTo)));
					}
					if (StringUtils.isNotEmpty(searchCriteria.getIp())) {
						predicates.add(cb.and(cb.like(root.get(OpLog.IP), "%" + searchCriteria.getIp() + "%"),cb.and(validFrom, validTo)));
					}
					if (StringUtils.isNotEmpty(searchCriteria.getChannel())) {
						predicates.add(cb.and(cb.like(root.get(OpLog.CHANNEL), "%" + searchCriteria.getChannel() + "%"),cb.and(validFrom, validTo)));
					}
					if (StringUtils.isNotEmpty(searchCriteria.getOperate())) {
						predicates.add(cb.and(cb.like(root.get(OpLog.OPERATE), "%" + searchCriteria.getOperate() + "%"),cb.and(validFrom, validTo)));
					}
					if (StringUtils.isNotEmpty(searchCriteria.getResult())) {
						predicates.add(cb.and(cb.like(root.get(OpLog.RESULT), "%" + searchCriteria.getResult() + "%"),cb.and(validFrom, validTo)));
					}
					
					if(predicates.isEmpty()){
						predicates.add(cb.and(validFrom, validTo));
					}
				}
				else{
					if (StringUtils.isNotEmpty(searchCriteria.getCreatedBy())) {
						predicates.add(cb.or(cb.like(root.get(OpLog.CREATED_BY), "%" + searchCriteria.getCreatedBy() + "%")));
					}
					if (StringUtils.isNotEmpty(searchCriteria.getIp())) {
						predicates.add(cb.or(cb.like(root.get(OpLog.IP), "%" + searchCriteria.getIp() + "%")));
					}
					if (StringUtils.isNotEmpty(searchCriteria.getChannel())) {
						predicates.add(cb.or(cb.like(root.get(OpLog.CHANNEL), "%" + searchCriteria.getChannel() + "%")));
					}
					if (StringUtils.isNotEmpty(searchCriteria.getOperate())) {
						predicates.add(cb.or(cb.like(root.get(OpLog.OPERATE), "%" + searchCriteria.getOperate() + "%")));
					}
					if (StringUtils.isNotEmpty(searchCriteria.getResult())) {
						predicates.add(cb.or(cb.like(root.get(OpLog.RESULT), "%" + searchCriteria.getResult() + "%")));
					}
				}
				
				
				
				return cb.or(predicates.toArray(new Predicate[] {}));
			}
		};
	}
}