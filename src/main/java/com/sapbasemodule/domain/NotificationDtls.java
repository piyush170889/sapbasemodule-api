package com.sapbasemodule.domain;

import javax.persistence.Table;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name="notification_dtls")
public class NotificationDtls {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="NOTIFICATION_DTLS_ID")
	private int notificationDtlsId;
	
	@Column(name="NOTIFICATION_MSSG")
	private String notificationMssg;
	
	@Column(name="IS_EMAIL_SENT")
	private byte isEmailSent;
	
	@Column(name="CREATED_TS", insertable=false, updatable=false)
	private Date createdTs;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="MODIFIED_BY")
	private String modifiedBy;
	
	@Column(name="MODIFIED_TS", insertable=false, updatable=false)
	private Date modifiedTs;


	public NotificationDtls() {}

	public NotificationDtls(String notificationMssg, byte isEmailSent, String createdBy, String modifiedBy) {
		this.notificationMssg = notificationMssg;
		this.isEmailSent = isEmailSent;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
	}


	public int getNotificationDtlsId() {
		return notificationDtlsId;
	}


	public void setNotificationDtlsId(int notificationDtlsId) {
		this.notificationDtlsId = notificationDtlsId;
	}


	public String getNotificationMssg() {
		return notificationMssg;
	}


	public void setNotificationMssg(String notificationMssg) {
		this.notificationMssg = notificationMssg;
	}


	public byte getIsEmailSent() {
		return isEmailSent;
	}


	public void setIsEmailSent(byte isEmailSent) {
		this.isEmailSent = isEmailSent;
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

	
	
}
