package com.sapbasemodule.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the otp_dtls database table.
 * 
 */
@Entity
@Table(name="otp_dtls")
@NamedQuery(name="OtpDtl.findAll", query="SELECT o FROM OtpDtl o")
public class OtpDtl extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="OTP_ID")
	private int otpId;

	private String cellnumber;

	@Column(name="DEVICE_INFO")
	private String deviceInfo;

	@Column(name="NUM_OF_ATTEMPTS")
	private int numOfAttempts;

	private int otp;

	public OtpDtl() {
	}

	public int getOtpId() {
		return this.otpId;
	}

	public void setOtpId(int otpId) {
		this.otpId = otpId;
	}

	public String getCellnumber() {
		return this.cellnumber;
	}

	public void setCellnumber(String cellnumber) {
		this.cellnumber = cellnumber;
	}


	public String getDeviceInfo() {
		return this.deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}


	public int getNumOfAttempts() {
		return this.numOfAttempts;
	}

	public void setNumOfAttempts(int numOfAttempts) {
		this.numOfAttempts = numOfAttempts;
	}

	public int getOtp() {
		return this.otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OtpDtl [otpId=");
		builder.append(otpId);
		builder.append(", cellnumber=");
		builder.append(cellnumber);
		builder.append(", deviceInfo=");
		builder.append(deviceInfo);
		builder.append(", numOfAttempts=");
		builder.append(numOfAttempts);
		builder.append(", otp=");
		builder.append(otp);
		builder.append("]");
		return builder.toString();
	}

}