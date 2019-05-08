package com.sapbasemodule.configuration;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class BaseModuleInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {

		return new Class[] { BaseModuleConfiguration.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {

		return null;
	}

	@Override
	protected String[] getServletMappings() {

		return new String[] { "/v1/*" };
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		// servletContext.addListener(new SessionListener());

		System.out.println("Initializing Cors Filter");
		FilterRegistration.Dynamic corsFilter = servletContext.addFilter("corsFilter", new CORSFilter());
		corsFilter.addMappingForUrlPatterns(null, false, "/*");
		corsFilter.setAsyncSupported(true);

		// SET PROFILES : "local", "dev", "staging", "prod"
		servletContext.setInitParameter("spring.profiles.active", "local");
	}
}
