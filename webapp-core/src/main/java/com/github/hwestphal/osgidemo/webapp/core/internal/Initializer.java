package com.github.hwestphal.osgidemo.webapp.core.internal;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.ext.servlet.ServerServlet;

import com.github.hwestphal.osgidemo.webapp.core.api.AddOn;

@Component
@Reference(name = "addOn", referenceInterface = AddOn.class, cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, policy = ReferencePolicy.DYNAMIC)
public class Initializer {

	private static final String ROOT_ALIAS = "/demo";
	private static final String REST_ALIAS = "/demo/rest";

	private final List<AddOn> addOns = new CopyOnWriteArrayList<AddOn>();

	@Reference
	private HttpService httpService;

	@Activate
	public void activate() throws NamespaceException, ServletException {
		HttpContext httpContext = httpService.createDefaultHttpContext();
		httpService.registerResources(ROOT_ALIAS, "/", httpContext);
		ServerServlet restletServlet = new ServerServlet();
		httpService.registerServlet(REST_ALIAS, restletServlet, null, httpContext);
		restletServlet.getComponent().getDefaultHost().attach(REST_ALIAS + "/", new Restlet() {
			@Override
			public void handle(Request request, Response response) {
				super.handle(request, response);

				if (!Method.GET.equals(request.getMethod())) {
					response.setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
					return;
				}

				String path = request.getResourceRef().getRemainingPart();
				if (path.isEmpty()) {
					response.setEntity(new JacksonRepresentation<Object>(addOns));
					return;
				}

				for (AddOn addOn : addOns) {
					if (path.equals(addOn.getId())) {
						response.setEntity(new JacksonRepresentation<Object>(addOn));
						return;
					}
				}

				response.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
			}
		});
	}

	@Deactivate
	public void deactivate() {
		httpService.unregister(REST_ALIAS);
		httpService.unregister(ROOT_ALIAS);
	}

	public void bindAddOn(AddOn addOn) {
		addOns.add(addOn);
	}

	public void unbindAddOn(AddOn addOn) {
		addOns.remove(addOn);
	}

}
