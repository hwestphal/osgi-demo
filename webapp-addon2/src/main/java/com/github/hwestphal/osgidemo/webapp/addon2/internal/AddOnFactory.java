package com.github.hwestphal.osgidemo.webapp.addon2.internal;

import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;

import com.github.hwestphal.osgidemo.webapp.core.api.AddOn;

@Component
public class AddOnFactory implements ServiceFactory<AddOn> {

	private ServiceRegistration<?> registration;

	@Activate
	public void activate(BundleContext context) {
		registration = context.registerService(AddOn.class.getName(), this, null);
	}

	@Deactivate
	public void deactivate() {
		registration.unregister();
	}

	@Override
	public AddOn getService(Bundle bundle, ServiceRegistration<AddOn> registration) {
		String uuid = UUID.randomUUID().toString();
		return new AddOn(uuid, "Dynamic AddOn (" + uuid + ")", Collections.singletonMap("create", new Date()));
	}

	@Override
	public void ungetService(Bundle bundle, ServiceRegistration<AddOn> registration, AddOn service) {
	}

}
