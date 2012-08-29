package com.github.hwestphal.osgidemo.webapp.addon2.internal;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.UUID;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import com.github.hwestphal.osgidemo.webapp.core.api.Place;

@Component
public class PlaceFactory implements ServiceFactory<Place> {

	private ServiceRegistration<?> registration;

	@Activate
	public void activate(BundleContext context) {
		Dictionary<String, Object> props = new Hashtable<String, Object>();
		props.put("place.name", "New York");
		props.put("place.description", "The City of New York");
		props.put("place.lat", 40.7143528);
		props.put("place.lng", -74.0059731);
		registration = context.registerService(Place.class.getName(), this, props);
	}

	@Deactivate
	public void deactivate() {
		registration.unregister();
	}

	@Override
	public Place getService(Bundle bundle, ServiceRegistration<Place> registration) {
		ServiceReference<Place> ref = registration.getReference();
		String id = UUID.randomUUID().toString();
		String name = (String) ref.getProperty("place.name");
		String description = (String) ref.getProperty("place.description");
		double lat = (Double) ref.getProperty("place.lat");
		double lng = (Double) ref.getProperty("place.lng");
		return new Place(id, name, description, lat, lng);
	}

	@Override
	public void ungetService(Bundle bundle, ServiceRegistration<Place> registration, Place service) {
	}

}
