package com.sapbasemodule.model;

import java.util.List;

import com.sapbasemodule.domain.UserLoginDtl;

public class LoggedInUserDetailsWrapper {

	private List<String> roles;

	private UserLoginDtl userDetails;

	public LoggedInUserDetailsWrapper() {
	}

	public LoggedInUserDetailsWrapper(List<String> roles, UserLoginDtl userDetails) {
		this.roles = roles;
		this.userDetails = userDetails;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public UserLoginDtl getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserLoginDtl userDetails) {
		this.userDetails = userDetails;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoggedInUserDetailsWrapper [roles=");
		builder.append(roles);
		builder.append(", userDetails=");
		builder.append(userDetails);
		builder.append("]");
		return builder.toString();
	}

}
