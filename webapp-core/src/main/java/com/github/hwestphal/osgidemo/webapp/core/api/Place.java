package com.github.hwestphal.osgidemo.webapp.core.api;

public class Place {

	private final String id;
	private final String label;
	private final String description;
	private final double lat;
	private final double lng;

	public Place(String id, String label, String description, double lat, double lng) {
		this.id = id;
		this.label = label;
		this.description = description;
		this.lat = lat;
		this.lng = lng;
	}

	public String getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public String getDescription() {
		return description;
	}

	public double getLat() {
		return lat;
	}

	public double getLng() {
		return lng;
	}

}
