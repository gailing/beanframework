package com.beanframework.multitenant.platform.location;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import com.beanframework.platform.core.base.BaseDomain;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tenant_location")
public class Location extends BaseDomain{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6945351570077688741L;
	private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
