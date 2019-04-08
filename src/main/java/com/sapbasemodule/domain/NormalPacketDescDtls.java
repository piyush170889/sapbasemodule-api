package com.sapbasemodule.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "normal_packet_desc_dtls")
public class NormalPacketDescDtls {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "NORMAL_PACKET_DESC_DTLS_ID")
	private int normalPacketDescDtlsId;

	private String imei;

	private Double latitude;

	private Double longitude;

	@Column(name = "DIGITAL_INPUT_STATUS")
	private String digitalInputStatus;

	@Column(name = "UTC_DT")
	private String utcDt;

	@Column(name = "UTC_TM")
	private String utcTm;

	@Column(name = "PACKET_SERIAL_NO")
	private String packetSerialNo = "0";

	@Column(name = "CREATED_TS", insertable = false, updatable = false)
	private Timestamp createdTs;

	public NormalPacketDescDtls() {
	}

	public NormalPacketDescDtls(Double latitude, Double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public NormalPacketDescDtls(Double latitude, Double longitude, String utcTm, String utcDt, String digitalInputStatus) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.utcTm=utcTm;
		this.utcDt=utcDt;
		this.digitalInputStatus=digitalInputStatus;
	}
	
	public String getPacketSerialNo() {
		return packetSerialNo;
	}

	public void setPacketSerialNo(String packetSerialNo) {
		this.packetSerialNo = packetSerialNo;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Timestamp getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Timestamp createdTs) {
		this.createdTs = createdTs;
	}

	public int getNormalPacketDescDtlsId() {
		return normalPacketDescDtlsId;
	}

	public void setNormalPacketDescDtlsId(int normalPacketDescDtlsId) {
		this.normalPacketDescDtlsId = normalPacketDescDtlsId;
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

	public String getDigitalInputStatus() {
		return digitalInputStatus;
	}

	public void setDigitalInputStatus(String digitalInputStatus) {
		this.digitalInputStatus = digitalInputStatus;
	}

	public String getUtcDt() {
		return utcDt;
	}

	public void setUtcDt(String utcDt) {
		this.utcDt = utcDt;
	}

	public String getUtcTm() {
		return utcTm;
	}

	public void setUtcTm(String utcTm) {
		this.utcTm = utcTm;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NormalPacketDescDtls [normalPacketDescDtlsId=");
		builder.append(normalPacketDescDtlsId);
		builder.append(", imei=");
		builder.append(imei);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", digitalInputStatus=");
		builder.append(digitalInputStatus);
		builder.append(", utcDt=");
		builder.append(utcDt);
		builder.append(", utcTm=");
		builder.append(utcTm);
		builder.append(", packetSerialNo=");
		builder.append(packetSerialNo);
		builder.append(", createdTs=");
		builder.append(createdTs);
		builder.append("]");
		return builder.toString();
	}


}
