package com.sapbasemodule.model;

import java.util.List;

import com.sapbasemodule.domain.UserLoginDtl;

public class UsersDetailsWrapper extends BaseWrapper {

	private List<UserLoginDtl> usersList;
	
	private PaginationDetails paginationDetails;
	
	public UsersDetailsWrapper() {}
	
	public UsersDetailsWrapper(List<UserLoginDtl> usersList, PaginationDetails paginationDetails) {
		this.usersList = usersList;
		this.paginationDetails = paginationDetails;
	}

	public PaginationDetails getPaginationDetails() {
		return paginationDetails;
	}

	public void setPaginationDetails(PaginationDetails paginationDetails) {
		this.paginationDetails = paginationDetails;
	}

	public List<UserLoginDtl> getUsersList() {
		return usersList;
	}


	public void setUsersList(List<UserLoginDtl> usersList) {
		this.usersList = usersList;
	}

}
