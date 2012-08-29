package com.github.hwestphal.osgidemo.webapp.addon1.internal;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import com.github.hwestphal.osgidemo.webapp.core.api.Place;

@Component
@Service(value = Place.class)
public class Bielefeld extends Place {

	public Bielefeld() {
		super(Bielefeld.class.getName(), Bielefeld.class.getSimpleName(), "The City of Bielefeld", 52.0212965, 8.5302966);
	}

}
