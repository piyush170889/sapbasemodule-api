package com.sapbasemodule.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "APP_ORDR_DLVRY")
public class OrderDeliveryDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AppOrdrDlvryId")
	private int appOrdrDlvryId;

	@Column(name = "Quantity")
	private String quantity;

	@Column(name = "App_Ordr_Id")
	private int appOrdrId;

	@Column(name = "TransporterName")
	private String transporterName;

	@Column(name = "VehNo")
	private String vehNo;

	@Column(name = "MaterialSource")
	private String materialSource;

	@Column(name = "ActualDestination")
	private String actualDestination;

	@Column(name = "DestLat")
	private String destLat;

	@Column(name = "DestLon")
	private String destLon;

	@Column(name = "CreatedTs")
	private String createdTs;

	@Transient
	private String firebaseId;

	public OrderDeliveryDetails() {
	}

	public String getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(String createdTs) {
		this.createdTs = createdTs;
	}

	public String getDestLat() {
		return destLat;
	}

	public void setDestLat(String destLat) {
		this.destLat = destLat;
	}

	public String getDestLon() {
		return destLon;
	}

	public void setDestLon(String destLon) {
		this.destLon = destLon;
	}

	public int getAppOrdrId() {
		return appOrdrId;
	}

	public void setAppOrdrId(int appOrdrId) {
		this.appOrdrId = appOrdrId;
	}

	public String getFirebaseId() {
		return firebaseId;
	}

	public void setFirebaseId(String firebaseId) {
		this.firebaseId = firebaseId;
	}

	public int getAppOrdrDlvryId() {
		return appOrdrDlvryId;
	}

	public void setAppOrdrDlvryId(int appOrdrDlvryId) {
		this.appOrdrDlvryId = appOrdrDlvryId;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getTransporterName() {
		return transporterName;
	}

	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}

	public String getVehNo() {
		return vehNo;
	}

	public void setVehNo(String vehNo) {
		this.vehNo = vehNo;
	}

	public String getMaterialSource() {
		return materialSource;
	}

	public void setMaterialSource(String materialSource) {
		this.materialSource = materialSource;
	}

	public String getActualDestination() {
		return actualDestination;
	}

	public void setActualDestination(String actualDestination) {
		this.actualDestination = actualDestination;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderDeliveryDetails [appOrdrDlvryId=");
		builder.append(appOrdrDlvryId);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", transporterName=");
		builder.append(transporterName);
		builder.append(", vehNo=");
		builder.append(vehNo);
		builder.append(", materialSource=");
		builder.append(materialSource);
		builder.append(", actualDestination=");
		builder.append(actualDestination);
		builder.append("]");
		return builder.toString();
	}

}
