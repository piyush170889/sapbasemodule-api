package com.sapbasemodule.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="vehicle_dtls")
public class VehicleDtls {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="VEHICLE_DTLS_ID")
	private int vehicleDtlsId;
	
	@NotNull(message="701")
	@NotEmpty(message="701")
	@Column(name="VIN")
	private String vin;
	
	@NotNull(message="702")
	@NotEmpty(message="702")
	@Column(name="VEHICLE_NO")
	private String vehicleNo;
	
	@NotNull(message="629")
	@NotEmpty(message="629")
	@Column(name="IMEI_NO")
	private String imeiNo;
	
	@Transient
	private String firebaseId;
	
	@NotNull(message="703")
	@NotEmpty(message="703")
	@Column(name="ASSIGNED_DRV_F_NM")
	private String assignedDrvFNm;
	
	@Column(name="ASSIGNED_DRV_S_NM")
	private String assignedDrvSNm;
	
	@NotNull(message="704")
	@NotEmpty(message="704")
	@Column(name="ASSIGNED_DRV_CNTC_NUM")
	private String assignedDrvCntcNum;
	
	@NotEmpty(message="705")
	@NotNull(message="705")
	@Column(name="PURCHASE_DT")
	private String purchaseDt;
	
	@NotNull(message="706")
	@NotEmpty(message="706")
	@Column(name="MAKE")
	private String make;
	
	@NotNull(message="707")
	@NotEmpty(message="707")
	@Column(name="VEHICLE_TYPE")
	private String vehicleType;
	
	@Column(name="IS_ACTIVE")
	private byte isActive = (byte)1;
	
	@Column(name="CREATED_TS")
	private Date createdTs;
	
	
	@Transient
	private String todaysTravel;
	
	
	public VehicleDtls() {}


	public String getTodaysTravel() {
		return todaysTravel;
	}


	public void setTodaysTravel(String todaysTravel) {
		this.todaysTravel = todaysTravel;
	}


	public Date getCreatedTs() {
		return createdTs;
	}


	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}


	public String getFirebaseId() {
		return firebaseId;
	}


	public void setFirebaseId(String firebaseId) {
		this.firebaseId = firebaseId;
	}


	public String getImeiNo() {
		return imeiNo;
	}


	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}


	public int getVehicleDtlsId() {
		return vehicleDtlsId;
	}


	public void setVehicleDtlsId(int vehicleDtlsId) {
		this.vehicleDtlsId = vehicleDtlsId;
	}


	public String getVin() {
		return vin;
	}


	public void setVin(String vin) {
		this.vin = vin;
	}


	public String getVehicleNo() {
		return vehicleNo;
	}


	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}


	public String getAssignedDrvFNm() {
		return assignedDrvFNm;
	}


	public void setAssignedDrvFNm(String assignedDrvFNm) {
		this.assignedDrvFNm = assignedDrvFNm;
	}


	public String getAssignedDrvSNm() {
		return assignedDrvSNm;
	}


	public void setAssignedDrvSNm(String assignedDrvSNm) {
		this.assignedDrvSNm = assignedDrvSNm;
	}


	public String getAssignedDrvCntcNum() {
		return assignedDrvCntcNum;
	}


	public void setAssignedDrvCntcNum(String assignedDrvCntcNum) {
		this.assignedDrvCntcNum = assignedDrvCntcNum;
	}


	public String getPurchaseDt() {
		return purchaseDt;
	}


	public void setPurchaseDt(String purchaseDt) {
		this.purchaseDt = purchaseDt;
	}


	public String getMake() {
		return make;
	}


	public void setMake(String make) {
		this.make = make;
	}


	public String getVehicleType() {
		return vehicleType;
	}


	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}


	public byte getIsActive() {
		return isActive;
	}


	public void setIsActive(byte isActive) {
		this.isActive = isActive;
	}


	@Override
	public String toString() {
		return "VehicleDtls [vehicleDtlsId=" + vehicleDtlsId + ", vin=" + vin + ", vehicleNo=" + vehicleNo + ", imeiNo="
				+ imeiNo + ", assignedDrvFNm=" + assignedDrvFNm + ", assignedDrvSNm=" + assignedDrvSNm
				+ ", assignedDrvCntcNum=" + assignedDrvCntcNum + ", purchaseDt=" + purchaseDt + ", make=" + make
				+ ", vehicleType=" + vehicleType + ", isActive=" + isActive + "]";
	}

}
