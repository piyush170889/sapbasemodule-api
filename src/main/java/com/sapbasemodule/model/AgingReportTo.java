package com.sapbasemodule.model;

import java.util.List;

public class AgingReportTo {

	private AgingDetails agingDetails;

	private List<InvoicesDetails> firstQInvoicesList;

	private List<InvoicesDetails> secondQInvoicesList;

	private List<InvoicesDetails> thirdQInvoicesList;

	private List<InvoicesDetails> fourthQInvoicesList;

	private List<InvoicesDetails> otherQInvoicesList;

	public AgingReportTo() {
	}

	public AgingReportTo(AgingDetails agingDetails, List<InvoicesDetails> firstQInvoicesList,
			List<InvoicesDetails> secondQInvoicesList, List<InvoicesDetails> thirdQInvoicesList,
			List<InvoicesDetails> fourthQInvoicesList, List<InvoicesDetails> otherQInvoicesList) {
		super();
		this.agingDetails = agingDetails;
		this.firstQInvoicesList = firstQInvoicesList;
		this.secondQInvoicesList = secondQInvoicesList;
		this.thirdQInvoicesList = thirdQInvoicesList;
		this.fourthQInvoicesList = fourthQInvoicesList;
		this.otherQInvoicesList = otherQInvoicesList;
	}

	public AgingDetails getAgingDetails() {
		return agingDetails;
	}

	public void setAgingDetails(AgingDetails agingDetails) {
		this.agingDetails = agingDetails;
	}

	public List<InvoicesDetails> getFirstQInvoicesList() {
		return firstQInvoicesList;
	}

	public void setFirstQInvoicesList(List<InvoicesDetails> firstQInvoicesList) {
		this.firstQInvoicesList = firstQInvoicesList;
	}

	public List<InvoicesDetails> getSecondQInvoicesList() {
		return secondQInvoicesList;
	}

	public void setSecondQInvoicesList(List<InvoicesDetails> secondQInvoicesList) {
		this.secondQInvoicesList = secondQInvoicesList;
	}

	public List<InvoicesDetails> getThirdQInvoicesList() {
		return thirdQInvoicesList;
	}

	public void setThirdQInvoicesList(List<InvoicesDetails> thirdQInvoicesList) {
		this.thirdQInvoicesList = thirdQInvoicesList;
	}

	public List<InvoicesDetails> getFourthQInvoicesList() {
		return fourthQInvoicesList;
	}

	public void setFourthQInvoicesList(List<InvoicesDetails> fourthQInvoicesList) {
		this.fourthQInvoicesList = fourthQInvoicesList;
	}

	public List<InvoicesDetails> getOtherQInvoicesList() {
		return otherQInvoicesList;
	}

	public void setOtherQInvoicesList(List<InvoicesDetails> otherQInvoicesList) {
		this.otherQInvoicesList = otherQInvoicesList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AgingReportTo [agingDetails=");
		builder.append(agingDetails);
		builder.append(", firstQInvoicesList=");
		builder.append(firstQInvoicesList);
		builder.append(", secondQInvoicesList=");
		builder.append(secondQInvoicesList);
		builder.append(", thirdQInvoicesList=");
		builder.append(thirdQInvoicesList);
		builder.append(", fourthQInvoicesList=");
		builder.append(fourthQInvoicesList);
		builder.append(", otherQInvoicesList=");
		builder.append(otherQInvoicesList);
		builder.append("]");
		return builder.toString();
	}

}
