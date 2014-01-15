/**
 * 
 */
package com.github.simbo1905.rule2;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.github.simbo1905.osgi.RulesService;

public class ServiceActivator implements BundleActivator {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) {
		RulesService service = new RuleTwo();
		context.registerService(RuleTwo.class.getName(), service, null);
		System.out.println("RuleTwo Service Registered");
	}


	public void stop(BundleContext context) {
		System.out.println("Goodbye From " + this.getClass().getCanonicalName());
	}
}