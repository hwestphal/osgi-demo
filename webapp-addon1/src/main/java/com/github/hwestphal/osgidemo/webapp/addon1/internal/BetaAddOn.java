package com.github.hwestphal.osgidemo.webapp.addon1.internal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import com.github.hwestphal.osgidemo.webapp.core.api.AddOn;

@Component
@Service(value = AddOn.class)
public class BetaAddOn extends AddOn {

	public BetaAddOn() {
		super(BetaAddOn.class.getName(), BetaAddOn.class.getSimpleName(), createContent());
	}

	private static Object createContent() {
		Map<String, Object> content = new HashMap<String, Object>();
		content.put("prop1", "Test");
		content.put("prop2", Arrays.asList(5, 6));
		return content;
	}

}
