package com.sapbasemodule.model;

import com.sapbasemodule.domain.UserDtl;

public class CompleteUserDtlsWrapper extends BaseWrapper {

	private UserDtl userDetails;
	
	private PaginationDetails paginationDetails;
	
	public CompleteUserDtlsWrapper() {}
	
	public CompleteUserDtlsWrapper(UserDtl userDetails,
			PaginationDetails paginationDetails) {
		this.userDetails = userDetails;
		this.paginationDetails = paginationDetails;
	}

	public PaginationDetails getPaginationDetails() {
		return paginationDetails;
	}

	public void setPaginationDetails(PaginationDetails paginationDetails) {
		this.paginationDetails = paginationDetails;
	}

	public UserDtl getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDtl userDetails) {
		this.userDetails = userDetails;
	}

}
