package com.github.hwestphal.osgidemo.webapp.core.api;

public class AddOn {

	private final String id;
	private final String label;
	private final Object content;

	public AddOn(String id, String label, Object content) {
		this.id = id;
		this.label = label;
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public Object getContent() {
		return content;
	}

}
