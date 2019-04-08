package com.sapbasemodule.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OCRD")
public class Customers {

	@Id
	@Column(name = "CardCode")
	private String cardCode;

	@Column(name = "CardName")
	private String cardName;

	@Column(name = "CardType")
	private String cardType;

	@Column(name = "GroupCode")
	private Integer groupCode;

	@Column(name = "CmpPrivate")
	private String cmpPrivate;

	@Column(name = "Phone1")
	private String phone1;

	@Column(name = "Phone2")
	private String phone2;

	@Column(name = "Fax")
	private String fax;

	@Column(name = "CntctPrsn")
	private String cntctPrsn;

	@Column(name = "Notes")
	private String notes;

	@Column(name = "Balance")
	private Float balance;

	@Column(name = "ChecksBal")
	private Float checksBal;

	@Column(name = "DNotesBal")
	private Float dNotesBal;

	@Column(name = "OrdersBal")
	private Float ordersBal;

	@Column(name = "GroupNum")
	private Integer groupNum;

	@Column(name = "Deleted")
	private String deleted;

	@Column(name = "DocEntry")
	private Integer docEntry;

	public Customers() {
	}

	public Customers(String cardCode, String cardName, String cardType, Integer groupCode, String cmpPrivate, String phone1,
			String phone2, String fax, String cntctPrsn, String notes, Float balance, Float checksBal, Float dNotesBal,
			Float ordersBal, Integer groupNum, String deleted, Integer docEntry) {
		this.cardCode = cardCode;
		this.cardName = cardName;
		this.cardType = cardType;
		this.groupCode = groupCode;
		this.cmpPrivate = cmpPrivate;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.fax = fax;
		this.cntctPrsn = cntctPrsn;
		this.notes = notes;
		this.balance = balance;
		this.checksBal = checksBal;
		this.dNotesBal = dNotesBal;
		this.ordersBal = ordersBal;
		this.groupNum = groupNum;
		this.deleted = deleted;
		this.docEntry = docEntry;
	}

	public String getDeleted() {
		return this.deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public Integer getDocEntry() {
		return this.docEntry;
	}

	public void setDocEntry(Integer docEntry) {
		this.docEntry = docEntry;
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

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public Integer getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(Integer groupCode) {
		this.groupCode = groupCode;
	}

	public String getCmpPrivate() {
		return cmpPrivate;
	}

	public void setCmpPrivate(String cmpPrivate) {
		this.cmpPrivate = cmpPrivate;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getCntctPrsn() {
		return cntctPrsn;
	}

	public void setCntctPrsn(String cntctPrsn) {
		this.cntctPrsn = cntctPrsn;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

	public Float getChecksBal() {
		return checksBal;
	}

	public void setChecksBal(Float checksBal) {
		this.checksBal = checksBal;
	}

	public Float getdNotesBal() {
		return dNotesBal;
	}

	public void setdNotesBal(Float dNotesBal) {
		this.dNotesBal = dNotesBal;
	}

	public Float getOrdersBal() {
		return ordersBal;
	}

	public void setOrdersBal(Float ordersBal) {
		this.ordersBal = ordersBal;
	}

	public Integer getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(Integer groupNum) {
		this.groupNum = groupNum;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Customers [cardCode=");
		builder.append(cardCode);
		builder.append(", cardName=");
		builder.append(cardName);
		builder.append(", cardType=");
		builder.append(cardType);
		builder.append(", groupCode=");
		builder.append(groupCode);
		builder.append(", cmpPrivate=");
		builder.append(cmpPrivate);
		builder.append(", phone1=");
		builder.append(phone1);
		builder.append(", phone2=");
		builder.append(phone2);
		builder.append(", fax=");
		builder.append(fax);
		builder.append(", cntctPrsn=");
		builder.append(cntctPrsn);
		builder.append(", notes=");
		builder.append(notes);
		builder.append(", balance=");
		builder.append(balance);
		builder.append(", checksBal=");
		builder.append(checksBal);
		builder.append(", dNotesBal=");
		builder.append(dNotesBal);
		builder.append(", ordersBal=");
		builder.append(ordersBal);
		builder.append(", groupNum=");
		builder.append(groupNum);
		builder.append(", deleted=");
		builder.append(deleted);
		builder.append(", docEntry=");
		builder.append(docEntry);
		builder.append("]");
		return builder.toString();
	}

}
