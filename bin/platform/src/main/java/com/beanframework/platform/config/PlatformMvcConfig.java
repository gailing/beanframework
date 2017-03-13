package com.beanframework.platform.config;

import java.io.IOException;
import java.util.Locale;

import com.beanframework.platform.PlatformConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class PlatformMvcConfig extends WebMvcConfigurerAdapter {

	//////////////
	// Resource //
	//////////////

	private static final String[] RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/" };

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if (!registry.hasMappingForPattern("/static/**")) {
			registry.addResourceHandler("/static/**").addResourceLocations(RESOURCE_LOCATIONS);
		}
		if (!registry.hasMappingForPattern("/webjars/**")) {
			registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		}
	}

	////////////
	// i18n //
	////////////

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.US);
		return slr;
	}

	@Value(PlatformConstants.LOCALE_PARAM)
	private String localParam;

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName(localParam);
		return lci;
	}

	@Value(PlatformConstants.SPRING_MESSAGE_CLASSPATH)
	private String springMessagesClasspath;

	@Autowired
	public ResourceLoader resourceLoader;

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

		Resource[] resources = loadResources(springMessagesClasspath + "/*.properties");

		if (resources != null) {
			for (Resource resource : resources) {
				String fileName = resource.getFilename();
				String basename = fileName.substring(0, fileName.indexOf('_'));
				messageSource.addBasenames("classpath:" + springMessagesClasspath + "/" + basename);
			}
		}

		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(3600); // refresh cache once per hour
		return messageSource;
	}

	public Resource[] loadResources(String pattern) {
		try {
			return ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(pattern);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
