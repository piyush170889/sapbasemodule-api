package com.sapbasemodule.model;

import java.util.List;

public class InvoicesDetails {

	private int invoiceDtlsId;

	private String invoiceNo;

	private String invoiceDate;

	private String dueDate;

	private long paymentDueDays;

	private String isPaid;

	private float amountDue;

	private String cardCode;

	private String cardName;

	private String type;

	private List<InvoiceItems> invoiceItemsList;

	private float netAmount;

	private float cgst;

	private float sgst;

	private float igst;

	private float cgstTax;

	private float sgstTax;

	private float igstTax;

	private float roundOff;

	private float grossTotal;

	private String narration;

	private long dueDateInDays;

	private String amountInWords;

	private String taxAmountInWords;

	private String transId;

	private String originNo;

	private String debit;

	private String credit;

	private String cumulativeBalance;

	public InvoicesDetails() {
	}

	public InvoicesDetails(int invoiceDtlsId, String invoiceNo, String invoiceDate, String dueDate, long paymentDueDays,
			String isPaid, float amountDue, String cardCode, String cardName, String type,
			List<InvoiceItems> invoiceItemsList, float netAmount, float grossTotal, String narration,
			long dueDateInDays, String amountInWords, String taxAmountInWords, String transId, String originNo,
			String debit, String credit, String cumulativeBalance) {
		this.invoiceDtlsId = invoiceDtlsId;
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.dueDate = dueDate;
		this.paymentDueDays = paymentDueDays;
		this.isPaid = isPaid;
		this.amountDue = amountDue;
		this.cardCode = cardCode;
		this.cardName = cardName;
		this.type = type;
		this.invoiceItemsList = invoiceItemsList;
		this.netAmount = netAmount;
		this.grossTotal = grossTotal;
		this.narration = narration;
		this.dueDateInDays = dueDateInDays;
		this.amountInWords = amountInWords;
		this.taxAmountInWords = taxAmountInWords;
		this.transId = transId;
		this.originNo = originNo;
		this.debit = debit;
		this.credit = credit;
		this.cumulativeBalance = cumulativeBalance;
	}

	public InvoicesDetails(int invoiceDtlsId, String invoiceNo, String invoiceDate, String dueDate, long paymentDueDays,
			String isPaid, float amountDue, String cardCode, String cardName, String type,
			List<InvoiceItems> invoiceItemsList, float netAmount, float cgst, float sgst, float roundOff,
			float grossTotal, String narration, long dueDateInDays, float igst, float igstTax, float cgstTax,
			float sgstTax, String amountInWords, String taxAmountInWords) {
		this.invoiceDtlsId = invoiceDtlsId;
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.dueDate = dueDate;
		this.paymentDueDays = paymentDueDays;
		this.isPaid = isPaid;
		this.amountDue = amountDue;
		this.cardCode = cardCode;
		this.cardName = cardName;
		this.type = type;
		this.invoiceItemsList = invoiceItemsList;
		this.netAmount = netAmount;
		this.grossTotal = grossTotal;
		this.narration = narration;
		this.dueDateInDays = dueDateInDays;
		this.roundOff = roundOff;
		this.cgst = cgst;
		this.sgst = sgst;
		this.igst = igst;
		this.igstTax = igstTax;
		this.cgstTax = cgstTax;
		this.sgstTax = sgstTax;
		this.amountInWords = amountInWords;
		this.taxAmountInWords = taxAmountInWords;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
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

	public String getAmountInWords() {
		return amountInWords;
	}

	public void setAmountInWords(String amountInWords) {
		this.amountInWords = amountInWords;
	}

	public String getTaxAmountInWords() {
		return taxAmountInWords;
	}

	public void setTaxAmountInWords(String taxAmountInWords) {
		this.taxAmountInWords = taxAmountInWords;
	}

	public float getIgst() {
		return igst;
	}

	public void setIgst(float igst) {
		this.igst = igst;
	}

	public float getCgstTax() {
		return cgstTax;
	}

	public void setCgstTax(float cgstTax) {
		this.cgstTax = cgstTax;
	}

	public float getSgstTax() {
		return sgstTax;
	}

	public void setSgstTax(float sgstTax) {
		this.sgstTax = sgstTax;
	}

	public float getIgstTax() {
		return igstTax;
	}

	public void setIgstTax(float igstTax) {
		this.igstTax = igstTax;
	}

	public int getInvoiceDtlsId() {
		return invoiceDtlsId;
	}

	public void setInvoiceDtlsId(int invoiceDtlsId) {
		this.invoiceDtlsId = invoiceDtlsId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public long getPaymentDueDays() {
		return paymentDueDays;
	}

	public void setPaymentDueDays(long paymentDueDays) {
		this.paymentDueDays = paymentDueDays;
	}

	public String getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(String isPaid) {
		this.isPaid = isPaid;
	}

	public float getAmountDue() {
		return amountDue;
	}

	public void setAmountDue(float amountDue) {
		this.amountDue = amountDue;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<InvoiceItems> getInvoiceItemsList() {
		return invoiceItemsList;
	}

	public void setInvoiceItemsList(List<InvoiceItems> invoiceItemsList) {
		this.invoiceItemsList = invoiceItemsList;
	}

	public float getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(float netAmount) {
		this.netAmount = netAmount;
	}

	public float getCgst() {
		return cgst;
	}

	public void setCgst(float cgst) {
		this.cgst = cgst;
	}

	public float getSgst() {
		return sgst;
	}

	public void setSgst(float sgst) {
		this.sgst = sgst;
	}

	public float getRoundOff() {
		return roundOff;
	}

	public void setRoundOff(float roundOff) {
		this.roundOff = roundOff;
	}

	public float getGrossTotal() {
		return grossTotal;
	}

	public void setGrossTotal(float grossTotal) {
		this.grossTotal = grossTotal;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public long getDueDateInDays() {
		return dueDateInDays;
	}

	public void setDueDateInDays(long dueDateInDays) {
		this.dueDateInDays = dueDateInDays;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InvoicesDetails [invoiceDtlsId=");
		builder.append(invoiceDtlsId);
		builder.append(", invoiceNo=");
		builder.append(invoiceNo);
		builder.append(", invoiceDate=");
		builder.append(invoiceDate);
		builder.append(", dueDate=");
		builder.append(dueDate);
		builder.append(", paymentDueDays=");
		builder.append(paymentDueDays);
		builder.append(", isPaid=");
		builder.append(isPaid);
		builder.append(", amountDue=");
		builder.append(amountDue);
		builder.append(", cardCode=");
		builder.append(cardCode);
		builder.append(", cardName=");
		builder.append(cardName);
		builder.append(", type=");
		builder.append(type);
		builder.append(", invoiceItemsList=");
		builder.append(invoiceItemsList);
		builder.append(", netAmount=");
		builder.append(netAmount);
		builder.append(", cgst=");
		builder.append(cgst);
		builder.append(", sgst=");
		builder.append(sgst);
		builder.append(", roundOff=");
		builder.append(roundOff);
		builder.append(", grossTotal=");
		builder.append(grossTotal);
		builder.append(", narration=");
		builder.append(narration);
		builder.append(", dueDateInDays=");
		builder.append(dueDateInDays);
		builder.append("]");
		return builder.toString();
	}

}
