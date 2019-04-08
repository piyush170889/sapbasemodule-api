package com.sapbasemodule.model;

public class LocationDetails {

	private String imeiNo;

	private Double latitude;

	private Double longitude;

	private String updatedTs;

	private String ignition;

	public LocationDetails() {
	}

	public LocationDetails(Double latitude, Double longitude, String updatedTs, String ignition) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.updatedTs = updatedTs;
		this.ignition = ignition;
	}

	public LocationDetails(String imeiNo, Double latitude, Double longitude, String updatedTs, String ignition) {
		this.imeiNo = imeiNo;
		this.latitude = latitude;
		this.longitude = longitude;
		this.updatedTs = updatedTs;
		this.ignition = ignition;
	}

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}

	public String getIgnition() {
		return ignition;
	}

	public void setIgnition(String ignition) {
		this.ignition = ignition;
	}

	public String getUpdatedTs() {
		return updatedTs;
	}

	public void setUpdatedTs(String updatedTs) {
		this.updatedTs = updatedTs;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}
