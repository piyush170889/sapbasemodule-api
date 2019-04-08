package com.sapbasemodule.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UserDtlWrapper extends BaseWrapper {

	private String userDtlsId;
	
	private String userLoginDtlsId;
	
	@NotNull(message="816")
	@NotEmpty(message="816")
	@Email(message="824")
	private String emailId;

	@NotNull(message="817")
	@NotEmpty(message="817")
	private String firstName;

	@NotNull(message="818")
	@NotEmpty(message="818")
	private String gstin;

	@NotNull(message="819")
	@NotEmpty(message="819")
	private String lastName;
	
	@NotNull(message="820")
	@NotEmpty(message="820")
	private String contactNum; 
	
	private byte termsAndCondition;
	
	@NotNull(message="821")
	@NotEmpty(message="821")
	private String password;
	
	private String activationCode;

	private String changedEmail;
	
	private String forgotPassCode;
	
	private byte isEmailVerified;
	
	private byte isPasswordChanged;
	
	
	public UserDtlWrapper() {}

	@Override
	public String toString() {
		return "UserDtlWrapper [userDtlsId=" + userDtlsId + ", userLoginDtlsId=" + userLoginDtlsId + ", emailId="
				+ emailId + ", firstName=" + firstName + ", gstin=" + gstin + ", lastName=" + lastName + ", contactNum="
				+ contactNum + ", termsAndCondition=" + termsAndCondition + ", password=" + password
				+ ", activationCode=" + activationCode + ", changedEmail=" + changedEmail + ", forgotPassCode="
				+ forgotPassCode + ", isEmailVerified=" + isEmailVerified + ", isPasswordChanged=" + isPasswordChanged
				+ "]";
	}

	public String getUserDtlsId() {
		return userDtlsId;
	}

	public void setUserDtlsId(String userDtlsId) {
		this.userDtlsId = userDtlsId;
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

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getContactNum() {
		return contactNum;
	}

	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}

	public byte getTermsAndCondition() {
		return termsAndCondition;
	}

	public void setTermsAndCondition(byte termsAndCondition) {
		this.termsAndCondition = termsAndCondition;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserLoginDtlsId() {
		return userLoginDtlsId;
	}

	public void setUserLoginDtlsId(String userLoginDtlsId) {
		this.userLoginDtlsId = userLoginDtlsId;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public String getChangedEmail() {
		return changedEmail;
	}

	public void setChangedEmail(String changedEmail) {
		this.changedEmail = changedEmail;
	}

	public String getForgotPassCode() {
		return forgotPassCode;
	}

	public void setForgotPassCode(String forgotPassCode) {
		this.forgotPassCode = forgotPassCode;
	}

	public byte getIsEmailVerified() {
		return isEmailVerified;
	}

	public void setIsEmailVerified(byte isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}

	public byte getIsPasswordChanged() {
		return isPasswordChanged;
	}

	public void setIsPasswordChanged(byte isPasswordChanged) {
		this.isPasswordChanged = isPasswordChanged;
	}
	
	
	
	
}
