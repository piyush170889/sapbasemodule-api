package com.sapbasemodule.model;

public class InvoiceItems {

	private int docEntry;

	private String itemCode;

	private String itemName;

	private float qty;

	private float ratePerBag;

	private float total;

	private String hsnSac;

	private String cgst;

	private String sgst;

	private String igst;

	private int docNum;

	private String payTo;

	private String shipTo;

	private String roundDif;

	private String paymentTerms;

	private String partyCity;

	private String partyGstinNo;

	private String stateCode;

	private String stateName;

	private String cgstTax;

	private String sgstTax;

	private String igstTax;

	private String frieghtName;

	private String frieghtAmt;

	private String sacCode;

	public InvoiceItems() {
	}

	public InvoiceItems(int docEntry, int docNum, String itemCode, String itemName, float qty, float ratePerBag,
			float total, String hsnSac, String cgst, String sgst, String igst, String payTo, String shipTo,
			String roundDif, String paymentTerms, String partyCity, String partyGstinNo, String stateCode,
			String stateName, String cgstTax, String sgstTax, String igstTax, String frieghtName, String frieghtAmt,
			String sacCode) {
		this.docEntry = docEntry;
		this.docNum = docNum;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.qty = qty;
		this.ratePerBag = ratePerBag;
		this.total = total;
		this.hsnSac = hsnSac;
		this.cgst = cgst;
		this.sgst = sgst;
		this.igst = igst;
		this.payTo = payTo;
		this.shipTo = shipTo;
		this.roundDif = roundDif;
		this.paymentTerms = paymentTerms;
		this.partyCity = partyCity;
		this.partyGstinNo = partyGstinNo;
		this.stateCode = stateCode;
		this.stateName = stateName;
		this.cgstTax = cgstTax;
		this.sgstTax = sgstTax;
		this.igstTax = igstTax;
		this.frieghtName = frieghtName;
		this.frieghtAmt = frieghtAmt;
		this.sacCode = sacCode;
	}

	public String getPayTo() {
		return payTo;
	}

	public void setPayTo(String payTo) {
		this.payTo = payTo;
	}

	public String getShipTo() {
		return shipTo;
	}

	public void setShipTo(String shipTo) {
		this.shipTo = shipTo;
	}

	public String getRoundDif() {
		return roundDif;
	}

	public void setRoundDif(String roundDif) {
		this.roundDif = roundDif;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getPartyCity() {
		return partyCity;
	}

	public void setPartyCity(String partyCity) {
		this.partyCity = partyCity;
	}

	public String getPartyGstinNo() {
		return partyGstinNo;
	}

	public void setPartyGstinNo(String partyGstinNo) {
		this.partyGstinNo = partyGstinNo;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCgstTax() {
		return cgstTax;
	}

	public void setCgstTax(String cgstTax) {
		this.cgstTax = cgstTax;
	}

	public String getSgstTax() {
		return sgstTax;
	}

	public void setSgstTax(String sgstTax) {
		this.sgstTax = sgstTax;
	}

	public String getIgstTax() {
		return igstTax;
	}

	public void setIgstTax(String igstTax) {
		this.igstTax = igstTax;
	}

	public String getFrieghtName() {
		return frieghtName;
	}

	public void setFrieghtName(String frieghtName) {
		this.frieghtName = frieghtName;
	}

	public String getFrieghtAmt() {
		return frieghtAmt;
	}

	public void setFrieghtAmt(String frieghtAmt) {
		this.frieghtAmt = frieghtAmt;
	}

	public String getSacCode() {
		return sacCode;
	}

	public void setSacCode(String sacCode) {
		this.sacCode = sacCode;
	}

	public int getDocNum() {
		return docNum;
	}

	public void setDocNum(int docNum) {
		this.docNum = docNum;
	}

	public int getDocEntry() {
		return docEntry;
	}

	public void setDocEntry(int docEntry) {
		this.docEntry = docEntry;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public float getQty() {
		return qty;
	}

	public void setQty(float qty) {
		this.qty = qty;
	}

	public float getRatePerBag() {
		return ratePerBag;
	}

	public void setRatePerBag(float ratePerBag) {
		this.ratePerBag = ratePerBag;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public String getHsnSac() {
		return hsnSac;
	}

	public void setHsnSac(String hsnSac) {
		this.hsnSac = hsnSac;
	}

	public String getCgst() {
		return cgst;
	}

	public void setCgst(String cgst) {
		this.cgst = cgst;
	}

	public String getSgst() {
		return sgst;
	}

	public void setSgst(String sgst) {
		this.sgst = sgst;
	}

	public String getIgst() {
		return igst;
	}

	public void setIgst(String igst) {
		this.igst = igst;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InvoiceItems [docEntry=");
		builder.append(docEntry);
		builder.append(", itemCode=");
		builder.append(itemCode);
		builder.append(", itemName=");
		builder.append(itemName);
		builder.append(", qty=");
		builder.append(qty);
		builder.append(", ratePerBag=");
		builder.append(ratePerBag);
		builder.append(", total=");
		builder.append(total);
		builder.append(", hsnSac=");
		builder.append(hsnSac);
		builder.append(", cgst=");
		builder.append(cgst);
		builder.append(", sgst=");
		builder.append(sgst);
		builder.append(", igst=");
		builder.append(igst);
		builder.append("]");
		return builder.toString();
	}

}
