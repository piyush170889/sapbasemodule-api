package com.sapbasemodule.model;

import java.util.List;

public class PdfValues {

	private String clientName;

	private String orgName;

	private String quotationNo;

	private String quoteSubject;

	private String date;

	private String mobileNo;

	private String address;

	private String siteAddress;

	private String executiveName;

	private String executiveDesignation;

	private String executiveMobNo;

	private String customerName;

	private String custDesignation;

	private String custCellNo;

	private String custEmail;

	private String cementBagRate;

	private Float pumpingRate;

	private String jsAddrName;
	
	private String jsStAddr;
	
	private String landMark;
	
	private String jsAreaNm;
	
	private String jsCityNm;
	
	private String jsStateNm;
	
	private String executiveEmail;
	
	private List<String> termsAndConditions;

	public PdfValues() {
	}

	public String getExecutiveEmail() {
		return executiveEmail;
	}

	public void setExecutiveEmail(String executiveEmail) {
		this.executiveEmail = executiveEmail;
	}

	public String getJsAddrName() {
		return jsAddrName;
	}

	public void setJsAddrName(String jsAddrName) {
		this.jsAddrName = jsAddrName;
	}

	public String getJsStAddr() {
		return jsStAddr;
	}

	public void setJsStAddr(String jsStAddr) {
		this.jsStAddr = jsStAddr;
	}

	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	public String getJsAreaNm() {
		return jsAreaNm;
	}

	public void setJsAreaNm(String jsAreaNm) {
		this.jsAreaNm = jsAreaNm;
	}

	public String getJsCityNm() {
		return jsCityNm;
	}

	public void setJsCityNm(String jsCityNm) {
		this.jsCityNm = jsCityNm;
	}

	public String getJsStateNm() {
		return jsStateNm;
	}

	public void setJsStateNm(String jsStateNm) {
		this.jsStateNm = jsStateNm;
	}

	public String getCementBagRate() {
		return cementBagRate;
	}

	public void setCementBagRate(String cementBagRate) {
		this.cementBagRate = cementBagRate;
	}

	public Float getPumpingRate() {
		return pumpingRate;
	}

	public void setPumpingRate(Float pumpingRate) {
		this.pumpingRate = pumpingRate;
	}

	public String getCustEmail() {
		return custEmail;
	}

	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getQuotationNo() {
		return quotationNo;
	}

	public void setQuotationNo(String quotationNo) {
		this.quotationNo = quotationNo;
	}

	public String getQuoteSubject() {
		return quoteSubject;
	}

	public void setQuoteSubject(String quoteSubject) {
		this.quoteSubject = quoteSubject;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSiteAddress() {
		return siteAddress;
	}

	public void setSiteAddress(String siteAddress) {
		this.siteAddress = siteAddress;
	}

	public String getExecutiveName() {
		return executiveName;
	}

	public void setExecutiveName(String executiveName) {
		this.executiveName = executiveName;
	}

	public String getExecutiveDesignation() {
		return executiveDesignation;
	}

	public void setExecutiveDesignation(String executiveDesignation) {
		this.executiveDesignation = executiveDesignation;
	}

	public String getExecutiveMobNo() {
		return executiveMobNo;
	}

	public void setExecutiveMobNo(String executiveMobNo) {
		this.executiveMobNo = executiveMobNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustDesignation() {
		return custDesignation;
	}

	public void setCustDesignation(String custDesignation) {
		this.custDesignation = custDesignation;
	}

	public String getCustCellNo() {
		return custCellNo;
	}

	public void setCustCellNo(String custCellNo) {
		this.custCellNo = custCellNo;
	}

	public List<String> getTermsAndConditions() {
		return termsAndConditions;
	}

	public void setTermsAndConditions(List<String> termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}

	@Override
	public String toString() {
		return "PdfValues [clientName=" + clientName + ", orgName=" + orgName + ", quotationNo=" + quotationNo
				+ ", quoteSubject=" + quoteSubject + ", date=" + date + ", mobileNo=" + mobileNo + ", address="
				+ address + ", siteAddress=" + siteAddress + ", executiveName=" + executiveName
				+ ", executiveDesignation=" + executiveDesignation + ", executiveMobNo=" + executiveMobNo
				+ ", customerName=" + customerName + ", custDesignation=" + custDesignation + ", custCellNo="
				+ custCellNo + ", termsAndConditions=" + termsAndConditions + "]";
	}

	public PdfValues(String clientName, String orgName, String quotationNo, String quoteSubject, String date,
			String mobileNo, String address, String siteAddress, String executiveName, String executiveDesignation,
			String executiveMobNo, String customerName, String custDesignation, String custCellNo,
			List<String> termsAndConditions, String custEmail, String cementBagRate, Float pumpingRate, String jsAddrName, 
			String jsStAddr, String landMark, String jsAreaNm, String jsCityNm, String jsStateNm, String executiveEmail) {
		this.clientName = clientName;
		this.orgName = orgName;
		this.quotationNo = quotationNo;
		this.quoteSubject = quoteSubject;
		this.date = date;
		this.mobileNo = mobileNo;
		this.address = address;
		this.siteAddress = siteAddress;
		this.executiveName = executiveName;
		this.executiveDesignation = executiveDesignation;
		this.executiveMobNo = executiveMobNo;
		this.customerName = customerName;
		this.custDesignation = custDesignation;
		this.custCellNo = custCellNo;
		this.termsAndConditions = termsAndConditions;
		this.custEmail = custEmail;
		this.cementBagRate = cementBagRate;
		this.pumpingRate = pumpingRate;
		this.jsAddrName=jsAddrName;
		this.jsStAddr=jsStAddr;
		this.landMark=landMark;
		this.jsAreaNm=jsAreaNm;
		this.jsCityNm=jsCityNm;
		this.jsStateNm=jsStateNm;
		this.executiveEmail = executiveEmail;
	}

}
