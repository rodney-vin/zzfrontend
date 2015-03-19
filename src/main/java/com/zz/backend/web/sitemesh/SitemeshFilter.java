package com.zz.backend.web.sitemesh;
import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

import com.zz.backend.Constants;

public class SitemeshFilter extends ConfigurableSiteMeshFilter {

	private static final String SITEMESH_DECORATOR_TEMPLATE = "/pages/sitemesh/decorator.html";
	private static final String SITEMESH_DECORATOR_DASHBOARD_TEMPLATE = "/pages/sitemesh/decorator_dashboard.html";

	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		builder.addDecoratorPath("/*", SITEMESH_DECORATOR_TEMPLATE)
				.addDecoratorPath(Constants.PAGE_PATH_ADMIN+"/*", SITEMESH_DECORATOR_DASHBOARD_TEMPLATE)
				.addExcludedPath(Constants.PAGE_PATH_ADMIN+"/login.html")
				.addExcludedPath("/jersey/**")
				.addExcludedPath(Constants.RESTPATH_SPRING_SAMPLE+"/*")
				.addExcludedPath("/pages/sitemesh/*");
	}
}
