package com.github.hwestphal.osgidemo.webapp.addon1.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AddOnData {

	private final String name;
	private final List<AddOnData> data;

	public AddOnData(String name, AddOnData... data) {
		this.name = name;
		this.data = Collections.unmodifiableList(Arrays.asList(data));
	}

	public String getName() {
		return name;
	}

	public List<AddOnData> getData() {
		return data;
	}

}
