package com.github.hwestphal.osgidemo.webapp.core.internal;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class TestResource extends ServerResource {

	@Get
	public String toString() {
		return "Resource URI  : " + getReference() + '\n' + "Root URI      : " + getRootRef() + '\n' + "Routed part   : " + getReference().getBaseRef() + '\n'
				+ "Remaining part: " + getReference().getRemainingPart();
	}

}
