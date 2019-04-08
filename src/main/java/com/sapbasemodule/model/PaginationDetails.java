package com.sapbasemodule.model;

public class PaginationDetails {

	private double totalPages;
	
	private double currentPageNo;

	public PaginationDetails() {}

	public PaginationDetails(double totalPages, double currentPageNo) {
		this.totalPages = totalPages;
		this.currentPageNo = currentPageNo;
	}

	public double getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(double totalPages) {
		this.totalPages = totalPages;
	}

	public double getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(double currentPageNo) {
		this.currentPageNo = currentPageNo;
	}
	
}
