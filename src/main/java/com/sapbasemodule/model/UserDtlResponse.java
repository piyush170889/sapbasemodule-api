package com.sapbasemodule.model;

import com.sapbasemodule.configuration.CustomUserDetails;

public class UserDtlResponse extends BaseWrapper {

	private CustomUserDetails customUserDetails;
	
	public UserDtlResponse() {}
	
	public UserDtlResponse(CustomUserDetails customUserDetails) {
		this.customUserDetails = customUserDetails;
	}

	public CustomUserDetails getCustomUserDetails() {
		return customUserDetails;
	}

	public void setCustomUserDetails(CustomUserDetails customUserDetails) {
		this.customUserDetails = customUserDetails;
	}

	@Override
	public String toString() {
		return "UserDtlResponse [customUserDetails=" + customUserDetails + "]";
	}





	
	
}
