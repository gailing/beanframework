//package com.beanframework.multitenant.tenant.web;
//
//import com.beanframework.multitenant.datasource.CurrentTenantId;
//import com.beanframework.multitenant.platform.location.Location;
//import com.beanframework.multitenant.tenant.domain.Tenant;
//import com.beanframework.multitenant.tenant.service.TenantService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//@Controller
//public class TenantController {
//
//	@Autowired
//	private TenantService dp;
//
//	@RequestMapping(path = "/test", method = RequestMethod.GET)
//	public ResponseEntity<?> test() {
//		
//		Tenant ghi = new Tenant("t1", "org.mariadb.jdbc.Driver", "jdbc:mysql://localhost:3306/beanframework_t1", "root", null);
//		dp.save(ghi);
//		ghi = new Tenant("t2", "org.mariadb.jdbc.Driver", "jdbc:mysql://localhost:3306/beanframework_t2", "root", null);
//		dp.save(ghi);
//		
//		System.out.println("----------------------------------------");
//		System.out.println("          BEFORE CREATING T3");
//		System.out.println("----------------------------------------");
//		
//		// iterate the tenants and show location data from each database
//		for (Tenant t : dp.getTenants()) {
//			System.out.println(t.getUrl());
//			CurrentTenantId.set(t.getId());
////			dp.importSqlByFile(t.getDriver(), t.getUrl(), t.getUsername(), t.getPassword());
//			for (Location l : dp.getLocations()) {
//				System.out.println(l.getName());
//			}
//		}
//
////		// Add a new record to the tenant table and do it again
////		// This proves we can configure new tenants on the fly
////		Tenant ghi = new Tenant("t3", "org.mariadb.jdbc.Driver", "jdbc:mysql://localhost:3306/beanframework_t3", "root",
////				null);
////		dp.save(ghi);
////
////		System.out.println("----------------------------------------");
////		System.out.println("          AFTER CREATING T3");
////		System.out.println("----------------------------------------");
////		// iterate the tenants and show location data from each database
////		for (Tenant t : dp.getTenants()) {
////			System.out.println(t.getUrl());
////			CurrentTenantId.set(t.getId());
////			for (Location l : dp.getLocations()) {
////				System.out.println(l.getName());
////			}
////		}
////		System.out.println("----------------------------------------");
//
//		return ResponseEntity.ok("Done");
//	}
//}
