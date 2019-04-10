package com.sapbasemodule.model;

public class CustomerAgingReport {

	private String postingDate;

	private String dueDate;

	private String blanketAgreement;

	private String type;

	private String docNo;

	private String installationNo;

	private String bpRefNo;

	private String balanceDueCurrency;

	private String balanceDue;

	private String futureRemitCurrency;

	private String futureRemit;

	private String paymentMethodCode;

	private String noOfDays;

	public CustomerAgingReport() {
	}

	public CustomerAgingReport(String postingDate, String dueDate, String blanketAgreement, String type, String docNo,
			String installationNo, String bpRefNo, String balanceDueCurrency, String balanceDue,
			String futureRemitCurrency, String futureRemit, String paymentMethodCode, String noOfDays) {
		this.postingDate = postingDate;
		this.dueDate = dueDate;
		this.blanketAgreement = blanketAgreement;
		this.type = type;
		this.docNo = docNo;
		this.installationNo = installationNo;
		this.bpRefNo = bpRefNo;
		this.balanceDueCurrency = balanceDueCurrency;
		this.balanceDue = balanceDue;
		this.futureRemitCurrency = futureRemitCurrency;
		this.futureRemit = futureRemit;
		this.paymentMethodCode = paymentMethodCode;
		this.noOfDays = noOfDays;
	}

	public String getPostingDate() {
		return postingDate;
	}

	public void setPostingDate(String postingDate) {
		this.postingDate = postingDate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getBlanketAgreement() {
		return blanketAgreement;
	}

	public void setBlanketAgreement(String blanketAgreement) {
		this.blanketAgreement = blanketAgreement;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public String getInstallationNo() {
		return installationNo;
	}

	public void setInstallationNo(String installationNo) {
		this.installationNo = installationNo;
	}

	public String getBpRefNo() {
		return bpRefNo;
	}

	public void setBpRefNo(String bpRefNo) {
		this.bpRefNo = bpRefNo;
	}

	public String getBalanceDueCurrency() {
		return balanceDueCurrency;
	}

	public void setBalanceDueCurrency(String balanceDueCurrency) {
		this.balanceDueCurrency = balanceDueCurrency;
	}

	public String getBalanceDue() {
		return balanceDue;
	}

	public void setBalanceDue(String balanceDue) {
		this.balanceDue = balanceDue;
	}

	public String getFutureRemitCurrency() {
		return futureRemitCurrency;
	}

	public void setFutureRemitCurrency(String futureRemitCurrency) {
		this.futureRemitCurrency = futureRemitCurrency;
	}

	public String getFutureRemit() {
		return futureRemit;
	}

	public void setFutureRemit(String futureRemit) {
		this.futureRemit = futureRemit;
	}

	public String getPaymentMethodCode() {
		return paymentMethodCode;
	}

	public void setPaymentMethodCode(String paymentMethodCode) {
		this.paymentMethodCode = paymentMethodCode;
	}

	public String getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomerAgingReport [postingDate=");
		builder.append(postingDate);
		builder.append(", dueDate=");
		builder.append(dueDate);
		builder.append(", blanketAgreement=");
		builder.append(blanketAgreement);
		builder.append(", type=");
		builder.append(type);
		builder.append(", docNo=");
		builder.append(docNo);
		builder.append(", installationNo=");
		builder.append(installationNo);
		builder.append(", bpRefNo=");
		builder.append(bpRefNo);
		builder.append(", balanceDueCurrency=");
		builder.append(balanceDueCurrency);
		builder.append(", balanceDue=");
		builder.append(balanceDue);
		builder.append(", futureRemitCurrency=");
		builder.append(futureRemitCurrency);
		builder.append(", futureRemit=");
		builder.append(futureRemit);
		builder.append(", paymentMethodCode=");
		builder.append(paymentMethodCode);
		builder.append(", noOfDays=");
		builder.append(noOfDays);
		builder.append("]");
		return builder.toString();
	}

}
