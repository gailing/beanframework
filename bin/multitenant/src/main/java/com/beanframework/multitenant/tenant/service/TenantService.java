package com.beanframework.multitenant.tenant.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import com.beanframework.multitenant.platform.location.Location;
import com.beanframework.multitenant.platform.location.LocationService;
import com.beanframework.multitenant.tenant.domain.Tenant;
import com.beanframework.multitenant.tenant.domain.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;

@Service
public class TenantService {
	@Autowired
	private TenantRepository tenantRepository;

	@Autowired
	private LocationService locationService;

	public Iterable<Tenant> getTenants() {
		return tenantRepository.findAll();
	}

	public void save(Tenant tenant) {
		tenantRepository.save(tenant);
	}

	public Iterable<Location> getLocations() {
		return locationService.getLocations();
	}

	@Autowired
	public ResourceLoader resourceLoader;

	public void importSqlByFile(String driver, String url, String username, String password) {

		SingleConnectionDataSource ds = null;
		Connection conn = null;

		try {
			ds = new SingleConnectionDataSource();
			ds.setDriverClassName(driver);
			ds.setUrl(url);
			ds.setUsername(username);
			ds.setPassword(password);

			conn = ds.getConnection();

			DatabaseMetaData dbmd = conn.getMetaData();

			String databaseType = dbmd.getDatabaseProductName().toLowerCase();

			// JdbcTemplate jt = new JdbcTemplate(ds);

			ScriptUtils.executeSqlScript(conn,
					new ClassPathResource("import/sql/schema/schema-" + databaseType + "-location.sql"));
			ScriptUtils.executeSqlScript(conn,
					new ClassPathResource("import/sql/data/data-" + databaseType + "-location.sql"));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (ds != null) {
				ds.destroy();
			}
		}
	}
}
