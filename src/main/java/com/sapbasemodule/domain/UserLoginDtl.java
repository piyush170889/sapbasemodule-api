package com.sapbasemodule.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * The persistent class for the user_login_dtls database table.
 * 
 */
@Entity
@Table(name = "user_login_dtls")
@NamedQuery(name = "UserLoginDtl.findAll", query = "SELECT u FROM UserLoginDtl u")
public class UserLoginDtl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "USER_LOGIN_DTLS_ID")
	private String userLoginDtlsId;

	@Column(name = "ACTIVATION_CODE")
	private String activationCode;

	@Column(name = "CONTACT_NUM")
	private String contactNum;

	@Column(name = "FORGOT_PASS_CODE")
	private String forgotPassCode;

	@Column(name = "IS_ACTIVE")
	private byte isActive;

	@Column(name = "IS_EMAIL_VERIFIED")
	private byte isEmailVerified;

	@Column(name = "IS_PASSWORD_CHANGED")
	private byte isPasswordChanged;

	@Column(name = "TERMS_AND_CONDITION")
	private byte termsAndCondition;

	@NotNull(message = "706")
	@NotEmpty(message = "706")
	@Column(name = "PASSWORD")
	private String password;

	@NotNull(message = "705")
	@NotEmpty(message = "705")
	@Column(name = "USERNAME")
	private String username;

	// bi-directional many-to-one association to UserDtl
	@OneToOne
	@JoinColumn(name = "USER_DTLS_ID")
	private UserDtl userDtl;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role_dtls", joinColumns = {
			@JoinColumn(name = "USER_LOGIN_DTLS_ID", referencedColumnName = "USER_LOGIN_DTLS_ID") }, inverseJoinColumns = {
					@JoinColumn(name = "ROLES_MASTER_DTLS_ID", referencedColumnName = "ROLES_MASTER_DTLS_ID") })
	private Set<RoleMasterDtl> roles;

/*	@Column(name = "CREATED_TS", insertable = false, updatable = false)
	private Timestamp createdTs;*/

	@Column(name = "CREATED_BY", insertable = true, updatable = false)
	private String createdBy;

/*	@Column(name = "MODIFIED_TS", insertable = false, updatable = false)
	private Timestamp modifiedTs;*/

	@Column(name = "MODIFIED_BY", insertable = true, updatable = true)
	private String modifiedBy;

	@Transient
	private String firebaseId;

	@Transient
	private String distanceTravelled;

	public UserLoginDtl() {
	}

	public UserLoginDtl(String userLoginDtlsId, UserDtl userDtl, String contactNum, byte isActive, byte isEmailVerified,
			byte isPasswordChanged, byte termsAndCondition, String password, String username, String createdBy,
			String modifiedBy) {
		this.contactNum = contactNum;
		this.isActive = isActive;
		this.isEmailVerified = isEmailVerified;
		this.isPasswordChanged = isPasswordChanged;
		this.termsAndCondition = termsAndCondition;
		this.password = password;
		this.username = username;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.userLoginDtlsId = userLoginDtlsId;
		this.userDtl = userDtl;
	}

	public String getDistanceTravelled() {
		return distanceTravelled;
	}

	public void setDistanceTravelled(String distanceTravelled) {
		this.distanceTravelled = distanceTravelled;
	}

	public String getFirebaseId() {
		return firebaseId;
	}

	public void setFirebaseId(String firebaseId) {
		this.firebaseId = firebaseId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<RoleMasterDtl> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleMasterDtl> roles) {
		this.roles = roles;
	}

	public byte getTermsAndCondition() {
		return termsAndCondition;
	}

	public void setTermsAndCondition(byte termsAndCondition) {
		this.termsAndCondition = termsAndCondition;
	}

	public String getUserLoginDtlsId() {
		return this.userLoginDtlsId;
	}

	public void setUserLoginDtlsId(String userLoginDtlsId) {
		this.userLoginDtlsId = userLoginDtlsId;
	}

	public String getActivationCode() {
		return this.activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public String getContactNum() {
		return this.contactNum;
	}

	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}

	public String getForgotPassCode() {
		return this.forgotPassCode;
	}

	public void setForgotPassCode(String forgotPassCode) {
		this.forgotPassCode = forgotPassCode;
	}

	public byte getIsActive() {
		return this.isActive;
	}

	public void setIsActive(byte isActive) {
		this.isActive = isActive;
	}

	public byte getIsEmailVerified() {
		return this.isEmailVerified;
	}

	public void setIsEmailVerified(byte isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}

	public byte getIsPasswordChanged() {
		return this.isPasswordChanged;
	}

	public void setIsPasswordChanged(byte isPasswordChanged) {
		this.isPasswordChanged = isPasswordChanged;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserDtl getUserDtl() {
		return this.userDtl;
	}

	public void setUserDtl(UserDtl userDtl) {
		this.userDtl = userDtl;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserLoginDtl [userLoginDtlsId=");
		builder.append(userLoginDtlsId);
		builder.append(", activationCode=");
		builder.append(activationCode);
		builder.append(", contactNum=");
		builder.append(contactNum);
		builder.append(", forgotPassCode=");
		builder.append(forgotPassCode);
		builder.append(", isActive=");
		builder.append(isActive);
		builder.append(", isEmailVerified=");
		builder.append(isEmailVerified);
		builder.append(", isPasswordChanged=");
		builder.append(isPasswordChanged);
		builder.append(", termsAndCondition=");
		builder.append(termsAndCondition);
		builder.append(", password=");
		builder.append(password);
		builder.append(", userDtl=");
		builder.append(userDtl);
		builder.append("]");
		return builder.toString();
	}

}