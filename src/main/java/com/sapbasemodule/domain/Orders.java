package com.sapbasemodule.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "ORDR")
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DocEntry")
	private int docEntry;

	@Column(name = "DocNum")
	private int docNum;

	@Column(name = "NumAtCard")
	private String numAtCard;

	@Column(name = "CardCode")
	private String cardCode;

	@Column(name = "CardName")
	private String cardName;

	@Column(name = "ShipToCode")
	private String shipToCode;

	@Transient
	private float openQuantity;

	@Transient
	private float quantity;

	@Transient
	private float deliveredQuantity;

	@Transient
	private float balanceQuantity;

	public Orders() {
	}

	public Orders(int docEntry, int docNum, String numAtCard, String cardCode, String cardName, String shipToCode) {
		this.docEntry = docEntry;
		this.docNum = docNum;
		this.numAtCard = numAtCard;
		this.cardCode = cardCode;
		this.cardName = cardName;
		this.shipToCode = shipToCode;
	}

	public Orders(int docEntry, int docNum, String numAtCard, String cardCode, String cardName, String shipToCode,
			float quantity, float openQuantity, float deliveredQuantity, float balanceQuantity) {
		this.docEntry = docEntry;
		this.docNum = docNum;
		this.numAtCard = numAtCard;
		this.cardCode = cardCode;
		this.cardName = cardName;
		this.shipToCode = shipToCode;
		this.quantity = quantity;
		this.openQuantity = openQuantity;
		this.deliveredQuantity = deliveredQuantity;
		this.balanceQuantity = balanceQuantity;
	}

	public float getOpenQuantity() {
		return openQuantity;
	}

	public void setOpenQuantity(float openQuantity) {
		this.openQuantity = openQuantity;
	}

	public float getDeliveredQuantity() {
		return deliveredQuantity;
	}

	public void setDeliveredQuantity(float deliveredQuantity) {
		this.deliveredQuantity = deliveredQuantity;
	}

	public float getBalanceQuantity() {
		return balanceQuantity;
	}

	public void setBalanceQuantity(float balanceQuantity) {
		this.balanceQuantity = balanceQuantity;
	}

	public int getDocEntry() {
		return docEntry;
	}

	public void setDocEntry(int docEntry) {
		this.docEntry = docEntry;
	}

	public int getDocNum() {
		return docNum;
	}

	public void setDocNum(int docNum) {
		this.docNum = docNum;
	}

	public String getNumAtCard() {
		return numAtCard;
	}

	public void setNumAtCard(String numAtCard) {
		this.numAtCard = numAtCard;
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

	public String getShipToCode() {
		return shipToCode;
	}

	public void setShipToCode(String shipToCode) {
		this.shipToCode = shipToCode;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Orders [docEntry=");
		builder.append(docEntry);
		builder.append(", docNum=");
		builder.append(docNum);
		builder.append(", numAtCard=");
		builder.append(numAtCard);
		builder.append(", cardCode=");
		builder.append(cardCode);
		builder.append(", cardName=");
		builder.append(cardName);
		builder.append(", shipToCode=");
		builder.append(shipToCode);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append("]");
		return builder.toString();
	}

}
