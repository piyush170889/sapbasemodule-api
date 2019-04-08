package com.sapbasemodule.model;

public class TrackingData {

	private String time;
	
	private String address;
	
	private String ignition;

	private Double latitude;
	
	private Double longitude;
	
	private String utcDate;
	
	private String utcTime;
	
	public TrackingData() {}

	
	public TrackingData(String time, String address, String ignition, Double latitude, Double longitude, String utcDate,
			String utcTime) {
		this.time = time;
		this.address = address;
		this.ignition = ignition;
		this.latitude = latitude;
		this.longitude = longitude;
		this.utcDate = utcDate;
		this.utcTime = utcTime;
	}


	public String getUtcDate() {
		return utcDate;
	}


	public void setUtcDate(String utcDate) {
		this.utcDate = utcDate;
	}


	public String getUtcTime() {
		return utcTime;
	}


	public void setUtcTime(String utcTime) {
		this.utcTime = utcTime;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIgnition() {
		return ignition;
	}

	public void setIgnition(String ignition) {
		this.ignition = ignition;
	}

	@Override
	public String toString() {
		return "TrackingData [time=" + time + ", address=" + address + ", ignition=" + ignition + ", latitude="
				+ latitude + ", longitude=" + longitude + "]";
	}

}
