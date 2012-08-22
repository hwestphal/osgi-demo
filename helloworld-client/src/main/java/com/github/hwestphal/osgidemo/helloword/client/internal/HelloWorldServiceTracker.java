package com.github.hwestphal.osgidemo.helloword.client.internal;

import java.util.SortedMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import com.github.hwestphal.osgidemo.helloword.api.IHelloWorldService;

/**
 * Service tracker implementation which always sets the highest ranked service
 * available in the given consumer.
 */
public class HelloWorldServiceTracker extends ServiceTracker<IHelloWorldService, IHelloWorldService> {

	private final IHelloWorldServiceConsumer serviceConsumer;

	public HelloWorldServiceTracker(BundleContext bundleContext, IHelloWorldServiceConsumer serviceConsumer) {
		super(bundleContext, IHelloWorldService.class, null);
		this.serviceConsumer = serviceConsumer;
	}

	@Override
	public IHelloWorldService addingService(ServiceReference<IHelloWorldService> reference) {
		IHelloWorldService service = context.getService(reference);
		SortedMap<ServiceReference<IHelloWorldService>, IHelloWorldService> tracked = getTracked();
		if (tracked.isEmpty() || reference.compareTo(tracked.firstKey()) > 0) {
			serviceConsumer.setHelloWorldService(service);
		}
		return service;
	}

	@Override
	public void modifiedService(ServiceReference<IHelloWorldService> reference, IHelloWorldService service) {
		serviceConsumer.setHelloWorldService(getTracked().values().iterator().next());
	}

	@Override
	public void removedService(ServiceReference<IHelloWorldService> reference, IHelloWorldService service) {
		SortedMap<ServiceReference<IHelloWorldService>, IHelloWorldService> tracked = getTracked();
		if (tracked.isEmpty()) {
			serviceConsumer.setHelloWorldService(null);
		} else {
			serviceConsumer.setHelloWorldService(tracked.values().iterator().next());
		}
	}

}
