package com.github.hwestphal.osgidemo.webapp.core.internal;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

@Component
public class Initializer {

	private static final String ROOT_ALIAS = "/demo";

	@SuppressWarnings("unused")
	@Reference
	private HttpService httpService;

	public void bindHttpService(HttpService httpService) throws NamespaceException {
		HttpContext httpContext = httpService.createDefaultHttpContext();
		httpService.registerResources(ROOT_ALIAS, "/", httpContext);
	}

	public void unbindHttpService(HttpService httpService) {
		httpService.unregister(ROOT_ALIAS);
	}

}
