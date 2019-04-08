package com.sapbasemodule.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "firebaseid_dtls")
public class FirebaseIdDtls {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FIREBASEID_DTLS_ID")
	private int firebaseidDtlsId;

	@Column(name = "IMEI_NO")
	private String imeiNo;

	@Column(name = "FIREBASE_ID")
	private String firebaseId;

	@Column(name = "IS_ACTIVE")
	private int isActive;

	public FirebaseIdDtls() {
	}

	public FirebaseIdDtls(String imeiNo, String firebaseId, int isActive) {
		this.imeiNo = imeiNo;
		this.firebaseId = firebaseId;
		this.isActive = isActive;
	}

	public int getFirebaseidDtlsId() {
		return firebaseidDtlsId;
	}

	public void setFirebaseidDtlsId(int firebaseidDtlsId) {
		this.firebaseidDtlsId = firebaseidDtlsId;
	}

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}

	public String getFirebaseId() {
		return firebaseId;
	}

	public void setFirebaseId(String firebaseId) {
		this.firebaseId = firebaseId;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "FirebaseIdDtls [firebaseidDtlsId=" + firebaseidDtlsId + ", imeiNo=" + imeiNo + ", firebaseId="
				+ firebaseId + ", isActive=" + isActive + "]";
	}

}
