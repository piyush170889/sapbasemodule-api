package com.sapbasemodule.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "site_visit_history")
public class SiteVisitHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SITE_VISIT_HISTORY_ID")
	private int siteVisitHistoryId;

	@ManyToOne
	@JoinColumn(name = "GEOFENCING_DTLS_ID")
	private SiteDtls siteDtls;

	@Column(name = "VISITOR_ID")
	private String visitorId;

	@Column(name = "VISITOR_NM")
	private String visitorNm;

	@Column(name = "ENTRY_DT")
	private String entryDt;

	@Column(name = "ENTRY_TM")
	private String entryTm;

	@Column(name = "VISIT_PURPOSE")
	private String visitPurpose;

	@Column(name = "EXIT_DT")
	private String exitDt;

	@Column(name = "EXIT_TM")
	private String exitTm;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "LATITUDE")
	private String latitude;

	@Column(name = "LONGITUDE")
	private String longitude;

	@Column(name = "EXIT_LATITUDE")
	private String exitLatitude;

	@Column(name = "EXIT_LONGITUDE")
	private String exitLongitude;

	@Column(name = "EXIT_LOCATION")
	private String exitLocation;

	@Transient
	private String entryTs;

	@Transient
	private String exitTs;

	public SiteVisitHistory() {
	}

	public String getEntryTs() {
		return entryTs;
	}

	public void setEntryTs(String entryTs) {
		this.entryTs = entryTs;
	}

	public String getExitTs() {
		return exitTs;
	}

	public void setExitTs(String exitTs) {
		this.exitTs = exitTs;
	}

	public String getVisitorNm() {
		return visitorNm;
	}

	public void setVisitorNm(String visitorNm) {
		this.visitorNm = visitorNm;
	}

	public String getExitLatitude() {
		return exitLatitude;
	}

	public void setExitLatitude(String exitLatitude) {
		this.exitLatitude = exitLatitude;
	}

	public String getExitLongitude() {
		return exitLongitude;
	}

	public void setExitLongitude(String exitLongitude) {
		this.exitLongitude = exitLongitude;
	}

	public String getExitLocation() {
		return exitLocation;
	}

	public void setExitLocation(String exitLocation) {
		this.exitLocation = exitLocation;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public int getSiteVisitHistoryId() {
		return siteVisitHistoryId;
	}

	public void setSiteVisitHistoryId(int siteVisitHistoryId) {
		this.siteVisitHistoryId = siteVisitHistoryId;
	}

	public SiteDtls getSiteDtls() {
		return siteDtls;
	}

	public void setSiteDtls(SiteDtls siteDtls) {
		this.siteDtls = siteDtls;
	}

	public String getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}

	public String getEntryDt() {
		return entryDt;
	}

	public void setEntryDt(String entryDt) {
		this.entryDt = entryDt;
	}

	public String getEntryTm() {
		return entryTm;
	}

	public void setEntryTm(String entryTm) {
		this.entryTm = entryTm;
	}

	public String getVisitPurpose() {
		return visitPurpose;
	}

	public void setVisitPurpose(String visitPurpose) {
		this.visitPurpose = visitPurpose;
	}

	public String getExitDt() {
		return exitDt;
	}

	public void setExitDt(String exitDt) {
		this.exitDt = exitDt;
	}

	public String getExitTm() {
		return exitTm;
	}

	public void setExitTm(String exitTm) {
		this.exitTm = exitTm;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public SiteVisitHistory(int siteVisitHistoryId, SiteDtls siteDtls, String visitorId, String entryDt, String entryTm,
			String visitPurpose, String exitDt, String exitTm, String remarks, String latitude, String longitude) {
		this.siteVisitHistoryId = siteVisitHistoryId;
		this.siteDtls = siteDtls;
		this.visitorId = visitorId;
		this.entryDt = entryDt;
		this.entryTm = entryTm;
		this.visitPurpose = visitPurpose;
		this.exitDt = exitDt;
		this.exitTm = exitTm;
		this.remarks = remarks;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public SiteVisitHistory(int siteVisitHistoryId, SiteDtls siteDtls, String visitorId, String entryDt, String entryTm,
			String visitPurpose, String exitDt, String exitTm, String remarks, String latitude, String longitude,
			String exitLatitude, String exitLongitude, String exitLocation) {
		this.siteVisitHistoryId = siteVisitHistoryId;
		this.siteDtls = siteDtls;
		this.visitorId = visitorId;
		this.entryDt = entryDt;
		this.entryTm = entryTm;
		this.visitPurpose = visitPurpose;
		this.exitDt = exitDt;
		this.exitTm = exitTm;
		this.remarks = remarks;
		this.latitude = latitude;
		this.longitude = longitude;
		this.exitLatitude = exitLatitude;
		this.exitLongitude = exitLongitude;
		this.exitLocation = exitLocation;
	}

	public SiteVisitHistory(int siteVisitHistoryId, SiteDtls siteDtls, String visitorId, String entryDt, String entryTm,
			String visitPurpose, String exitDt, String exitTm, String remarks, String latitude, String longitude,
			String exitLatitude, String exitLongitude, String exitLocation, String visitorNm) {
		this.siteVisitHistoryId = siteVisitHistoryId;
		this.siteDtls = siteDtls;
		this.visitorId = visitorId;
		this.entryDt = entryDt;
		this.entryTm = entryTm;
		this.visitPurpose = visitPurpose;
		this.exitDt = exitDt;
		this.exitTm = exitTm;
		this.remarks = remarks;
		this.latitude = latitude;
		this.longitude = longitude;
		this.exitLatitude = exitLatitude;
		this.exitLongitude = exitLongitude;
		this.exitLocation = exitLocation;
		this.visitorNm = visitorNm;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SiteVisitHistory [siteVisitHistoryId=");
		builder.append(siteVisitHistoryId);
		builder.append(", siteDtls=");
		builder.append(siteDtls);
		builder.append(", visitorId=");
		builder.append(visitorId);
		builder.append(", visitorNm=");
		builder.append(visitorNm);
		builder.append(", entryDt=");
		builder.append(entryDt);
		builder.append(", entryTm=");
		builder.append(entryTm);
		builder.append(", visitPurpose=");
		builder.append(visitPurpose);
		builder.append(", exitDt=");
		builder.append(exitDt);
		builder.append(", exitTm=");
		builder.append(exitTm);
		builder.append(", remarks=");
		builder.append(remarks);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", exitLatitude=");
		builder.append(exitLatitude);
		builder.append(", exitLongitude=");
		builder.append(exitLongitude);
		builder.append(", exitLocation=");
		builder.append(exitLocation);
		builder.append("]");
		return builder.toString();
	}

}
