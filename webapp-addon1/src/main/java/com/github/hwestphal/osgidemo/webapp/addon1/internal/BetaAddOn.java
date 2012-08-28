package com.github.hwestphal.osgidemo.webapp.addon1.internal;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import com.github.hwestphal.osgidemo.webapp.core.api.AddOn;

@Component
@Service(value = AddOn.class)
public class BetaAddOn extends AddOn {

	public BetaAddOn() {
		super(BetaAddOn.class.getName(), BetaAddOn.class.getSimpleName(), new AddOnData("addOn_B", new AddOnData("nested", new AddOnData("1"), new AddOnData(
				"2"))));
	}

}
