package com.sapbasemodule.model;

import java.util.List;

public class LocationTrackingData {

	private String trackingDate;
	
	private String distanceTravelled;
	
    private List<TrackingData> trackingData;
    
    private PaginationDetails trackDataPaginationDetails;
    
    public LocationTrackingData() {}

	public LocationTrackingData(String trackingDate, List<TrackingData> trackingData,
			PaginationDetails trackDataPaginationDetails) {
		this.trackingDate = trackingDate;
		this.trackingData = trackingData;
		this.trackDataPaginationDetails = trackDataPaginationDetails;
	}

	public LocationTrackingData(String trackingDate, List<TrackingData> trackingData,
			String distanceTravelled) {
		this.trackingDate = trackingDate;
		this.trackingData = trackingData;
		this.distanceTravelled = distanceTravelled;
	}
	
	public String getDistanceTravelled() {
		return distanceTravelled;
	}

	public void setDistanceTravelled(String distanceTravelled) {
		this.distanceTravelled = distanceTravelled;
	}

	public PaginationDetails getTrackDataPaginationDetails() {
		return trackDataPaginationDetails;
	}

	public void setTrackDataPaginationDetails(PaginationDetails trackDataPaginationDetails) {
		this.trackDataPaginationDetails = trackDataPaginationDetails;
	}

	public String getTrackingDate() {
		return trackingDate;
	}

	public void setTrackingDate(String trackingDate) {
		this.trackingDate = trackingDate;
	}

	public List<TrackingData> getTrackingData() {
		return trackingData;
	}

	public void setTrackingData(List<TrackingData> trackingData) {
		this.trackingData = trackingData;
	}
    
    
}
