package com.github.hwestphal.osgidemo.bootstrap;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

public class Launcher {

	public static final String DEFAULT_BUNDLE_DIRECTORY = "target/dependency";

	private final Map<String, String> configuration;
	private final String bundleDirectory;

	public Launcher(Map<String, String> configuration, String bundleDirectory) {
		this.configuration = configuration;
		this.bundleDirectory = bundleDirectory;
	}

	public Launcher(String bundleDirectory) {
		this(null, bundleDirectory);
	}

	public Launcher() {
		this(null, DEFAULT_BUNDLE_DIRECTORY);
	}

	public void launchAndWait() throws BundleException, InterruptedException {
		final Framework framework = initFramework();
		installAndStartBundles(framework);
		installShutdownHook(framework);
		System.err.println("OSGi framework started, waiting for OSGi framework to stop...");
		framework.waitForStop(0);
		System.err.println("OSGi framework stopped.");
	}

	private void installShutdownHook(final Framework framework) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					framework.stop();
					framework.waitForStop(0);
				} catch (Exception e) {
					System.err.println("Error while stopping the framework: " + e);
				}
			}
		});
	}

	private void installAndStartBundles(Framework framework) throws BundleException {
		BundleContext context = framework.getBundleContext();
		installBundles(context);
		startBundles(context);
	}

	private void installBundles(BundleContext context) throws BundleException {
		List<String> jarLocations = getJarLocations();
		installBundles(context, jarLocations);
	}

	private void installBundles(BundleContext context, List<String> jarLocations) throws BundleException {
		Map<String, Bundle> alreadyInstalledBundles = getAlreadyInstalledBundles(context);
		for (String jarLocation : jarLocations) {
			if (alreadyInstalledBundles.containsKey(jarLocation)) {
				Bundle bundle = alreadyInstalledBundles.remove(jarLocation);
				bundle.update();
				System.err.println("Updated bundle " + bundle);
			} else {
				Bundle bundle = context.installBundle(jarLocation);
				System.err.println("Installed bundle " + bundle);
			}
		}
		for (Bundle bundle : alreadyInstalledBundles.values()) {
			if (!isSystemBundle(bundle)) {
				bundle.uninstall();
				System.err.println("Uninstalled bundle " + bundle);
			}
		}
	}

	private Map<String, Bundle> getAlreadyInstalledBundles(BundleContext context) {
		Map<String, Bundle> alreadyInstalledBundles = new HashMap<String, Bundle>();
		for (Bundle bundle : context.getBundles()) {
			alreadyInstalledBundles.put(bundle.getLocation(), bundle);
		}
		return alreadyInstalledBundles;
	}

	private List<String> getJarLocations() {
		File[] files = new File(bundleDirectory).listFiles();
		List<String> jarLocations = new LinkedList<String>();
		if (files != null) {
			Arrays.sort(files);
			for (File file : files) {
				if (file.getName().toLowerCase().endsWith(".jar")) {
					jarLocations.add(file.toURI().toString());
				}
			}
		}
		return jarLocations;
	}

	private void startBundles(BundleContext context) {
		for (Bundle bundle : context.getBundles()) {
			try {
				if (!isFragment(bundle)) {
					bundle.start();
					System.err.println("Started bundle " + bundle);
				}
			} catch (BundleException e) {
				System.err.println("Bundle " + bundle + " could not be started: " + e);
			}
		}
	}

	private Framework initFramework() throws BundleException {
		FrameworkFactory frameworkFactory = ServiceLoader.load(FrameworkFactory.class).iterator().next();
		final Framework framework = frameworkFactory.newFramework(configuration);
		framework.init();
		return framework;
	}

	private boolean isFragment(Bundle bundle) {
		return bundle.getHeaders().get(Constants.FRAGMENT_HOST) != null;
	}

	private boolean isSystemBundle(Bundle bundle) {
		return bundle.getBundleId() == 0;
	}

	public static void main(String[] args) {
		try {
			new Launcher().launchAndWait();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

}
