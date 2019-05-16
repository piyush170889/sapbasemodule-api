package com.sapbasemodule.model;

public class InvoiceDetailsNewTo {

	private int invoiceDocEntry;

	private int invoiceNo;

	private String invoiceStatus;

	private String transId;

	private String postingDate;

	private String dueDate;

	private String origin;

	private String originNo;

	private String ref2;

	private String debit;

	private String credit;

	private String cumulativeBalance;

	private String balance;

	public InvoiceDetailsNewTo() {
	}

	public InvoiceDetailsNewTo(int invoiceNo, String invoiceStatus, String transId, String postingDate, String dueDate,
			String origin, String originNo, String debit, String credit, String cumulativeBalance, String balance,
			int invoiceDocEntry, String ref2) {
		this.ref2 = ref2;
		this.invoiceNo = invoiceNo;
		this.invoiceStatus = invoiceStatus;
		this.transId = transId;
		this.postingDate = postingDate;
		this.dueDate = dueDate;
		this.origin = origin;
		this.originNo = originNo;
		this.debit = debit;
		this.credit = credit;
		this.cumulativeBalance = cumulativeBalance;
		this.balance = balance;
		this.invoiceDocEntry = invoiceDocEntry;
	}

	public InvoiceDetailsNewTo(int invoiceNo, String invoiceStatus, String transId, String postingDate, String dueDate,
			String origin, String originNo, String debit, String credit, String cumulativeBalance, String balance) {
		this.invoiceNo = invoiceNo;
		this.invoiceStatus = invoiceStatus;
		this.transId = transId;
		this.postingDate = postingDate;
		this.dueDate = dueDate;
		this.origin = origin;
		this.originNo = originNo;
		this.debit = debit;
		this.credit = credit;
		this.cumulativeBalance = cumulativeBalance;
		this.balance = balance;
	}

	public String getRef2() {
		return ref2;
	}

	public void setRef2(String ref2) {
		this.ref2 = ref2;
	}

	public int getInvoiceDocEntry() {
		return invoiceDocEntry;
	}

	public void setInvoiceDocEntry(int invoiceDocEntry) {
		this.invoiceDocEntry = invoiceDocEntry;
	}

	public int getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
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

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getOriginNo() {
		return originNo;
	}

	public void setOriginNo(String originNo) {
		this.originNo = originNo;
	}

	public String getDebit() {
		return debit;
	}

	public void setDebit(String debit) {
		this.debit = debit;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getCumulativeBalance() {
		return cumulativeBalance;
	}

	public void setCumulativeBalance(String cumulativeBalance) {
		this.cumulativeBalance = cumulativeBalance;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InvoiceDetailsNewTo [invoiceNo=");
		builder.append(invoiceNo);
		builder.append(", invoiceStatus=");
		builder.append(invoiceStatus);
		builder.append(", transId=");
		builder.append(transId);
		builder.append(", postingDate=");
		builder.append(postingDate);
		builder.append(", dueDate=");
		builder.append(dueDate);
		builder.append(", origin=");
		builder.append(origin);
		builder.append(", originNo=");
		builder.append(originNo);
		builder.append(", debit=");
		builder.append(debit);
		builder.append(", credit=");
		builder.append(credit);
		builder.append(", cumulativeBalance=");
		builder.append(cumulativeBalance);
		builder.append(", balance=");
		builder.append(balance);
		builder.append("]");
		return builder.toString();
	}

}
