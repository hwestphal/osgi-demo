package com.github.hwestphal.osgidemo.webapp.addon1.internal;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import com.github.hwestphal.osgidemo.webapp.core.api.Place;

@Component
@Service(value = Place.class)
public class Hamburg extends Place {

	public Hamburg() {
		super(Hamburg.class.getName(), Hamburg.class.getSimpleName(), "The City of Hamburg", 53.5510846, 9.9936818);
	}

}
