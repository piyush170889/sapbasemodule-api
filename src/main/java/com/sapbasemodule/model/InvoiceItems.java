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

	public InvoiceItems() {
	}
	
	public InvoiceItems(int docEntry, String itemCode, String itemName, float qty, float ratePerBag, float total,
			String hsnSac, String cgst, String sgst, String igst) {
		this.docEntry = docEntry;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.qty = qty;
		this.ratePerBag = ratePerBag;
		this.total = total;
		this.hsnSac = hsnSac;
		this.cgst = cgst;
		this.sgst = sgst;
		this.igst = igst;
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
