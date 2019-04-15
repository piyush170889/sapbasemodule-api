package com.sapbasemodule.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "RDR1")
@IdClass(OrderItemKey.class)
public class OrderItems {

	@Id
	@Column(name = "DocEntry")
	private int docEntry; // Order No. With Which this orderItem is associated

	@Id
	@Column(name = "LineNum")
	private int lineNum;

	@Column(name = "ItemCode")
	private String itemCode;

	@Column(name = "Dscription")
	private String dscription;

	@Column(name = "Quantity")
	private Float quantity;

	@Column(name = "OpenQty")
	private Float openQty;

	@Column(name = "DelivrdQty")
	private Float delivrdQty;

	public OrderItems() {
	}

	public OrderItems(String itemCode, String dscription, Float quantity, Float openQty, Float delivrdQty) {
		this.itemCode = itemCode;
		this.dscription = dscription;
		this.quantity = quantity;
		this.openQty = openQty;
		this.delivrdQty = delivrdQty;
	}

//	public OrderItemKey getId() {
//		return id;
//	}
//
//	public void setId(OrderItemKey id) {
//		this.id = id;
//	}
	
	

	public String getItemCode() {
		return itemCode;
	}

	public int getDocEntry() {
		return docEntry;
	}

	public void setDocEntry(int docEntry) {
		this.docEntry = docEntry;
	}

	public int getLineNum() {
		return lineNum;
	}

	public void setLineNum(int lineNum) {
		this.lineNum = lineNum;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getDscription() {
		return dscription;
	}

	public void setDscription(String dscription) {
		this.dscription = dscription;
	}

	public Float getQuantity() {
		if (quantity == null)
			quantity = 0F;
		return quantity;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}

	public Float getOpenQty() {
		if (openQty == null)
			openQty = 0F;
		return openQty;
	}

	public void setOpenQty(Float openQty) {
		this.openQty = openQty;
	}

	public Float getDelivrdQty() {
		if (delivrdQty == null)
			delivrdQty = 0F;
		return delivrdQty;
	}

	public void setDelivrdQty(Float delivrdQty) {
		this.delivrdQty = delivrdQty;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderItems [docEntry=");
		builder.append(docEntry);
		builder.append(", lineNum=");
		builder.append(lineNum);
		builder.append(", itemCode=");
		builder.append(itemCode);
		builder.append(", dscription=");
		builder.append(dscription);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", openQty=");
		builder.append(openQty);
		builder.append(", delivrdQty=");
		builder.append(delivrdQty);
		builder.append("]");
		return builder.toString();
	}

}
