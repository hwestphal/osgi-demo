package com.github.hwestphal.osgidemo.webapp.addon1.internal;

import java.util.Arrays;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import com.github.hwestphal.osgidemo.webapp.core.api.AddOn;

@Component
@Service(value = AddOn.class)
public class AlphaAddOn extends AddOn {

	public AlphaAddOn() {
		super(AlphaAddOn.class.getName(), AlphaAddOn.class.getSimpleName(), Arrays.asList(1, 2, 3, 4));
	}

}
