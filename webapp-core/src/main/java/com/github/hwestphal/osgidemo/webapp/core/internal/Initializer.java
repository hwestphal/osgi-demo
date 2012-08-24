package com.github.hwestphal.osgidemo.webapp.core.internal;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Reference;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.restlet.Component;
import org.restlet.ext.servlet.ServerServlet;

@org.apache.felix.scr.annotations.Component
public class Initializer {

	private static final String ROOT_ALIAS = "/demo";
	private static final String REST_ALIAS = "/demo/rest";

	@SuppressWarnings("unused")
	@Reference
	private HttpService httpService;

	public void bindHttpService(HttpService httpService) throws NamespaceException, ServletException {
		HttpContext httpContext = httpService.createDefaultHttpContext();
		httpService.registerResources(ROOT_ALIAS, "/", httpContext);
		ServerServlet restletServlet = new ServerServlet();
		httpService.registerServlet(REST_ALIAS, restletServlet, null, httpContext);

		Component component = restletServlet.getComponent();
		component.getDefaultHost().attach(REST_ALIAS + "/", TestResource.class);
	}

	public void unbindHttpService(HttpService httpService) {
		httpService.unregister(REST_ALIAS);
		httpService.unregister(ROOT_ALIAS);
	}

}
