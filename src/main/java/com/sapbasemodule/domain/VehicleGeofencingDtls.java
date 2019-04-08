package com.sapbasemodule.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vehicle_geofencing_dtls")
public class VehicleGeofencingDtls {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="VEHICLE_GEOFENCING_DTLS_ID")
	private int vehicleGeofencingDtlsId;
	
	@Column(name="IMEI_NO")
	private String imeiNo;
	
	@Column(name="GEOFENCING_DTLS_ID")
	private int geofencingDtlsId;
	
	@Column(name="IS_WITHIN_GEOFENCE")
	private byte isWithinGeofence;
	
	@Column(name="IS_ENTRY_NOTIFIED")
	private int isEntryNotified;
	
	@Column(name="LAST_ENTRY_TS")
	private Date lastEntryTs;
	
	@Column(name="IS_EXIT_NOTIFIED")
	private int isExitNotified;

	@Column(name="LAST_EXIT_TS")
	private Date lastExitTs;
	
	@Column(name="CREATED_TS", insertable=false, updatable=false)
	private Date createdTs;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="MODIFIED_BY")
	private String modifiedBy;
	
	@Column(name="MODIFIED_TS", insertable=false, updatable=false)
	private Date modifiedTs;
	
	public VehicleGeofencingDtls() {}

	
	public VehicleGeofencingDtls(String imeiNo, int geofencingDtlsId, byte isWithinGeofence, int isEntryNotified,
			int isExitNotified, String createdBy, String modifiedBy) {
		this.imeiNo = imeiNo;
		this.geofencingDtlsId = geofencingDtlsId;
		this.isWithinGeofence = isWithinGeofence;
		this.isEntryNotified = isEntryNotified;
		this.isExitNotified = isExitNotified;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
	}


	public Date getLastEntryTs() {
		return lastEntryTs;
	}


	public void setLastEntryTs(Date lastEntryTs) {
		this.lastEntryTs = lastEntryTs;
	}


	public Date getLastExitTs() {
		return lastExitTs;
	}


	public void setLastExitTs(Date lastExitTs) {
		this.lastExitTs = lastExitTs;
	}


	public int getVehicleGeofencingDtlsId() {
		return vehicleGeofencingDtlsId;
	}

	public void setVehicleGeofencingDtlsId(int vehicleGeofencingDtlsId) {
		this.vehicleGeofencingDtlsId = vehicleGeofencingDtlsId;
	}

	public int getGeofencingDtlsId() {
		return geofencingDtlsId;
	}

	public void setGeofencingDtlsId(int geofencingDtlsId) {
		this.geofencingDtlsId = geofencingDtlsId;
	}

	public byte getIsWithinGeofence() {
		return isWithinGeofence;
	}

	public void setIsWithinGeofence(byte isWithinGeofence) {
		this.isWithinGeofence = isWithinGeofence;
	}

	public int getIsEntryNotified() {
		return isEntryNotified;
	}

	public void setIsEntryNotified(int isEntryNotified) {
		this.isEntryNotified = isEntryNotified;
	}

	public int getIsExitNotified() {
		return isExitNotified;
	}

	public void setIsExitNotified(int isExitNotified) {
		this.isExitNotified = isExitNotified;
	}

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedTs() {
		return modifiedTs;
	}

	public void setModifiedTs(Date modifiedTs) {
		this.modifiedTs = modifiedTs;
	}


	@Override
	public String toString() {
		return "VehicleGeofencingDtls [vehicleGeofencingDtlsId=" + vehicleGeofencingDtlsId + ", imeiNo=" + imeiNo
				+ ", geofencingDtlsId=" + geofencingDtlsId + ", isWithinGeofence=" + isWithinGeofence
				+ ", isEntryNotified=" + isEntryNotified + ", lastEntryTs=" + lastEntryTs + ", isExitNotified="
				+ isExitNotified + ", lastExitTs=" + lastExitTs + ", createdTs=" + createdTs + ", createdBy="
				+ createdBy + ", modifiedBy=" + modifiedBy + ", modifiedTs=" + modifiedTs + "]";
	}

	
}
