package com.beanframework.multitenant.datasource;

import java.util.HashMap;

import com.beanframework.multitenant.tenant.domain.Tenant;
import com.beanframework.multitenant.tenant.domain.TenantRepository;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
public class TenantDataSourceMap extends HashMap<Object, Object> implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object get(Object key) {
        Object value = super.get(key);
        if (value == null) {

            // Can't autowire this because it apparently creates a chicken/egg
            // problem during configuration.
            TenantRepository repo = applicationContext.getBean(TenantRepository.class);

            Tenant tenant = repo.findById((String) key);
            if (tenant != null) {
                DriverManagerDataSource dataSource = new DriverManagerDataSource();
                dataSource.setDriverClassName(tenant.getDriver());
                dataSource.setUrl(tenant.getUrl());
                dataSource.setUsername(tenant.getUsername());
                dataSource.setPassword(tenant.getPassword());

                value = dataSource;
                super.put(key, value);
            }
        }
        return value;
    }

}
