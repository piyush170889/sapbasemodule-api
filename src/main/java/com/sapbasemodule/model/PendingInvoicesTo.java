package com.sapbasemodule.model;

public class PendingInvoicesTo {

	private String refNo;

	private String invDate;

	private String dueDateOrCreditDays;

	private String overDueForBilling;

	private String partyName;

	private String openingAmount;

	private String pendingAmount;

	private String dueOn;

	private String overDueDays;

	public PendingInvoicesTo() {
	}

	public PendingInvoicesTo(String refNo, String invDate, String dueDateOrCreditDays, String overDueForBilling,
			String partyName, String openingAmount, String pendingAmount, String dueOn, String overDueDays) {
		this.refNo = refNo;
		this.invDate = invDate;
		this.dueDateOrCreditDays = dueDateOrCreditDays;
		this.overDueForBilling = overDueForBilling;
		this.partyName = partyName;
		this.openingAmount = openingAmount;
		this.pendingAmount = pendingAmount;
		this.dueOn = dueOn;
		this.overDueDays = overDueDays;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getInvDate() {
		return invDate;
	}

	public void setInvDate(String invDate) {
		this.invDate = invDate;
	}

	public String getDueDateOrCreditDays() {
		return dueDateOrCreditDays;
	}

	public void setDueDateOrCreditDays(String dueDateOrCreditDays) {
		this.dueDateOrCreditDays = dueDateOrCreditDays;
	}

	public String getOverDueForBilling() {
		return overDueForBilling;
	}

	public void setOverDueForBilling(String overDueForBilling) {
		this.overDueForBilling = overDueForBilling;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getOpeningAmount() {
		return openingAmount;
	}

	public void setOpeningAmount(String openingAmount) {
		this.openingAmount = openingAmount;
	}

	public String getPendingAmount() {
		return pendingAmount;
	}

	public void setPendingAmount(String pendingAmount) {
		this.pendingAmount = pendingAmount;
	}

	public String getDueOn() {
		return dueOn;
	}

	public void setDueOn(String dueOn) {
		this.dueOn = dueOn;
	}

	public String getOverDueDays() {
		return overDueDays;
	}

	public void setOverDueDays(String overDueDays) {
		this.overDueDays = overDueDays;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PendingInvoicesTo [refNo=");
		builder.append(refNo);
		builder.append(", invDate=");
		builder.append(invDate);
		builder.append(", dueDateOrCreditDays=");
		builder.append(dueDateOrCreditDays);
		builder.append(", overDueForBilling=");
		builder.append(overDueForBilling);
		builder.append(", partyName=");
		builder.append(partyName);
		builder.append(", openingAmount=");
		builder.append(openingAmount);
		builder.append(", pendingAmount=");
		builder.append(pendingAmount);
		builder.append(", dueOn=");
		builder.append(dueOn);
		builder.append(", overDueDays=");
		builder.append(overDueDays);
		builder.append("]");
		return builder.toString();
	}

}
