package com.github.simbo1905.osgi;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

public class ExtendableApplication {

	public static void main(String[] args) throws Exception {
		FrameworkFactory frameworkFactory = ServiceLoader.load(FrameworkFactory.class).iterator().next();
		Map<String, String> config = new HashMap<String, String>();

		System.out.println("using boot delegation and parent=framework");
		config.put(Constants.FRAMEWORK_BUNDLE_PARENT, Constants.FRAMEWORK_BUNDLE_PARENT_FRAMEWORK);
		config.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA, "com.github.simbo1905.osgi; version=1.0.0");

		Framework framework = frameworkFactory.newFramework(config);

		framework.init();

		framework.start();

		BundleContext context = framework.getBundleContext();
		List<Bundle> installedBundles = new LinkedList<Bundle>();

		installedBundles.add(context
.installBundle("RuleOne", new FileInputStream("./osgi-rules-1/target/osgi-rules-1-0.0.1-SNAPSHOT.jar")));
		installedBundles.add(context
.installBundle("RuleTwo", new FileInputStream("./osgi-rules-2/target/osgi-rules-2-0.0.1-SNAPSHOT.jar")));

		List<ServiceReference<?>> services = new ArrayList<>();

		for (Bundle bundle : installedBundles) {
			if (bundle.getHeaders().get(Constants.FRAGMENT_HOST) == null) {
				bundle.start();
			}
			services.addAll(Arrays.asList(bundle.getRegisteredServices()));
		}

		System.out.println("services.size(): " + services.size());
		
		for (ServiceReference<?> s : services) {
			final RulesService rule = (RulesService) context.getService(s);
			final String ruleName = rule.getClass().getSimpleName();
			RulesOutput output = rule.apply(new RulesInput(ruleName));
			System.out.println(ruleName + " gives a magic number output of " + output.getValue());
		}

		try {
			System.out.println("awaiting stop");
		    framework.waitForStop(0);
		} finally {
		    System.exit(0);
		}
	}

}
