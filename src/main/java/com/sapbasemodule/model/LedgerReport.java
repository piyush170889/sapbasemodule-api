package com.sapbasemodule.model;

public class LedgerReport {

	private String transId;

	private String postingDate;

	private String dueDate;

	private String origin;

	private String originNo;

	private String debit;

	private String credit;

	private String cumulativeBalance;
	
	private String balance;

	public LedgerReport() {
	}

	public LedgerReport(String transId, String postingDate, String dueDate, String origin, String originNo,
			String debit, String credit, String cumulativeBalance, String balance) {
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

	
	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LedgerReport [transId=");
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
		builder.append("]");
		return builder.toString();
	}

}
