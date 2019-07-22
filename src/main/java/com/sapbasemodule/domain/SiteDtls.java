package com.sapbasemodule.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "site_dtls")
public class SiteDtls {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "GEOFENCING_DTLS_ID")
	private int geofencingDtlsId;

	@Column(name = "GEOFENCE_NAME")
	private String geofenceName;

	@Column(name = "CARD_CODE")
	private String cardCode;

	@Column(name = "GEOFENCE_ADDR")
	private String geofenceAddr;

	@Column(name = "LATITUDE")
	private String latitude;

	@Column(name = "LONGITUDE")
	private String longitude;

	@Column(name = "UPDATED_DT")
	private String updatedDt;

	@Column(name = "UPDATED_TS")
	private String updatedTs;

	public SiteDtls() {
	}

	public SiteDtls(String cardCode, String geofenceName) {
		this.cardCode = cardCode;
		this.geofenceName = geofenceName;
	}

	public SiteDtls(int geofencingDtlsId, String geofenceName, String cardCode, String geofenceAddr, String latitude,
			String longitude, String updatedDt, String updatedTs) {
		this.geofencingDtlsId = geofencingDtlsId;
		this.geofenceName = geofenceName;
		this.cardCode = cardCode;
		this.geofenceAddr = geofenceAddr;
		this.latitude = latitude;
		this.longitude = longitude;
		this.updatedDt = updatedDt;
		this.updatedTs = updatedTs;
	}

	public int getGeofencingDtlsId() {
		return geofencingDtlsId;
	}

	public void setGeofencingDtlsId(int geofencingDtlsId) {
		this.geofencingDtlsId = geofencingDtlsId;
	}

	public String getGeofenceName() {
		return geofenceName;
	}

	public void setGeofenceName(String geofenceName) {
		this.geofenceName = geofenceName;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getGeofenceAddr() {
		return geofenceAddr;
	}

	public void setGeofenceAddr(String geofenceAddr) {
		this.geofenceAddr = geofenceAddr;
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

	public String getUpdatedDt() {
		return updatedDt;
	}

	public void setUpdatedDt(String updatedDt) {
		this.updatedDt = updatedDt;
	}

	public String getUpdatedTs() {
		return updatedTs;
	}

	public void setUpdatedTs(String updatedTs) {
		this.updatedTs = updatedTs;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SiteDtls [geofencingDtlsId=");
		builder.append(geofencingDtlsId);
		builder.append(", geofenceName=");
		builder.append(geofenceName);
		builder.append(", cardCode=");
		builder.append(cardCode);
		builder.append(", geofenceAddr=");
		builder.append(geofenceAddr);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", updatedDt=");
		builder.append(updatedDt);
		builder.append(", updatedTs=");
		builder.append(updatedTs);
		builder.append("]");
		return builder.toString();
	}

}
