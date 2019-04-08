package com.sapbasemodule.model;

import java.util.List;

public class AdminUserDetails {

	private String userLoginDtlsId;
	
	private String userDtlsId;
	
	private String contactNum;

	private String password;

	private String emailId;

	private String firstName;

	private String lastName;

	private List<String> rolesMasterDtlsId;
	
	public AdminUserDetails() {}

	public String getUserLoginDtlsId() {
		return userLoginDtlsId;
	}

	public void setUserLoginDtlsId(String userLoginDtlsId) {
		this.userLoginDtlsId = userLoginDtlsId;
	}

	public String getUserDtlsId() {
		return userDtlsId;
	}

	public void setUserDtlsId(String userDtlsId) {
		this.userDtlsId = userDtlsId;
	}

	public String getContactNum() {
		return contactNum;
	}

	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<String> getRolesMasterDtlsId() {
		return rolesMasterDtlsId;
	}

	public void setRolesMasterDtlsId(List<String> rolesMasterDtlsId) {
		this.rolesMasterDtlsId = rolesMasterDtlsId;
	}


}
