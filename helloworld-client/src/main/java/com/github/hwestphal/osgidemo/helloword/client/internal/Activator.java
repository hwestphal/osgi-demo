package com.github.hwestphal.osgidemo.helloword.client.internal;

import java.util.Timer;
import java.util.TimerTask;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private HelloWorldServiceTracker serviceTracker;
	private Timer timer;

	@Override
	public void start(BundleContext context) throws Exception {
		final HelloWorldDigester helloWorldDigester = new HelloWorldDigester();
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					System.out.println("Digest: " + helloWorldDigester.digest("Harald"));
				} catch (IllegalStateException e) {
					System.out.println("Digest failed: " + e);
				}
			}
		}, 0, 2000);

		serviceTracker = new HelloWorldServiceTracker(context, helloWorldDigester);
		serviceTracker.open();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		serviceTracker.close();
		serviceTracker = null;
		timer.cancel();
		timer = null;
	}

}
