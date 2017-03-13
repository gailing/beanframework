package com.beanframework.console.config;
//package com.beanframework.install.config;
//
//import java.util.List;
//
//import com.beanframework.common.AdminBaseConstants;
//import com.beanframework.platform.core.base.BaseConfig;
//import com.beanframework.platform.install.service.InstallFacade;
//import com.beanframework.platform.install.service.InstallWizard;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class InstallConfig extends BaseConfig{
//
//	@Value(AdminBaseConstants.INSTALL_WIZARDS)
//	private List<String> installWizards;
//	
//	@Autowired
//	private ApplicationContext context;
//
//	@Bean
//	public InstallFacade installFacade() {
//		InstallFacade installFacade = new InstallFacade();
//
//		for (String wizard : installWizards) {
//			InstallWizard installWizard = (InstallWizard) context.getBean(wizard);
//			installFacade.addInstallWizard(installWizard);
//		}
//		
//		return installFacade;
//	}
//}
