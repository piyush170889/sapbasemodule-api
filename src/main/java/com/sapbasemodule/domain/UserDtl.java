package com.sapbasemodule.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * The persistent class for the user_dtls database table.
 * 
 */
@Entity
@Table(name = "user_dtls")
public class UserDtl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "USER_DTLS_ID")
	private String userDtlsId;

	@Column(name = "IMG")
	private String img;

	@Column(name = "CHANGED_EMAIL")
	private String changedEmail;

	@Column(name = "EMAIL_ID")
	private String emailId;

	@NotNull(message = "817")
	@NotEmpty(message = "817")
	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "EMAIL_VERIFICATION_CODE")
	private String emailVerificationCode;

	@Column(name = "IS_ACTIVE")
	private byte isActive;

	@NotNull(message = "819")
	@NotEmpty(message = "819")
	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "CREATED_TS", insertable = false, updatable = false)
	private Timestamp createdTs;

	@Column(name = "CREATED_BY", insertable = true, updatable = false)
	private String createdBy;

	@Column(name = "MODIFIED_TS", insertable = false, updatable = false)
	private Timestamp modifiedTs;

	@Column(name = "MODIFIED_BY", insertable = true, updatable = true)
	private String modifiedBy;

	public UserDtl() {
	}

	public UserDtl(String userDtlsId, String emailId, String firstName, byte isActive, String lastName,
			String createdBy, String modifiedBy) {
		this.userDtlsId = userDtlsId;
		this.emailId = emailId;
		this.firstName = firstName;
		this.isActive = isActive;
		this.lastName = lastName;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
	}

	public UserDtl(String userDtlsId) {
		this.userDtlsId = userDtlsId;
	}

	public UserDtl(String userDtlsId, String firstName, String lastName, String emailId) {
		this.userDtlsId = userDtlsId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
	}
	
	public UserDtl(String userDtlsId, String firstName, String lastName, String emailId, byte isActive, String createdBy, String modifiedBy) {
		this.userDtlsId = userDtlsId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
	}

	public String getUserDtlsId() {
		return userDtlsId;
	}

	public void setUserDtlsId(String userDtlsId) {
		this.userDtlsId = userDtlsId;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getChangedEmail() {
		return changedEmail;
	}

	public void setChangedEmail(String changedEmail) {
		this.changedEmail = changedEmail;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmailVerificationCode() {
		return emailVerificationCode;
	}

	public void setEmailVerificationCode(String emailVerificationCode) {
		this.emailVerificationCode = emailVerificationCode;
	}

	public byte getIsActive() {
		return isActive;
	}

	public void setIsActive(byte isActive) {
		this.isActive = isActive;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Timestamp getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Timestamp createdTs) {
		this.createdTs = createdTs;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getModifiedTs() {
		return modifiedTs;
	}

	public void setModifiedTs(Timestamp modifiedTs) {
		this.modifiedTs = modifiedTs;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserDtl [userDtlsId=");
		builder.append(userDtlsId);
		builder.append(", img=");
		builder.append(img);
		builder.append(", changedEmail=");
		builder.append(changedEmail);
		builder.append(", emailId=");
		builder.append(emailId);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", emailVerificationCode=");
		builder.append(emailVerificationCode);
		builder.append(", isActive=");
		builder.append(isActive);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", createdTs=");
		builder.append(createdTs);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", modifiedTs=");
		builder.append(modifiedTs);
		builder.append(", modifiedBy=");
		builder.append(modifiedBy);
		builder.append("]");
		return builder.toString();
	}

}