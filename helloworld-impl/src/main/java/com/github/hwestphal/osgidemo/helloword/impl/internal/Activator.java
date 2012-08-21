package com.github.hwestphal.osgidemo.helloword.impl.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.github.hwestphal.osgidemo.helloword.api.IHelloWorldService;
import com.github.hwestphal.osgidemo.helloword.impl.HelloWorldService;

public class Activator implements BundleActivator {

	private ServiceRegistration<IHelloWorldService> helloWorldServiceRegistration;

	@Override
	public void start(BundleContext context) throws Exception {
		IHelloWorldService helloWorldService = new HelloWorldService();
		helloWorldServiceRegistration = context.registerService(IHelloWorldService.class, helloWorldService, null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		helloWorldServiceRegistration.unregister();
		helloWorldServiceRegistration = null;
	}

}
