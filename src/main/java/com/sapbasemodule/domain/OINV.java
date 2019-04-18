package com.sapbasemodule.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sapbasemodule.model.InvoiceItems;

@Entity
@Table(name = "OINV")
public class OINV {

	@Id
	@Column(name = "DocEntry")
	private int docEntry;

	@Column(name = "DocNum")
	private int docNum;

	@Column(name = "DocDate")
	private String docDate;

	@Column(name = "DocDueDate")
	private String docDueDate;

	@Column(name = "CardCode")
	private String cardCode;

	@Column(name = "CardName")
	private String cardName;

	@Column(name = "DocTotal")
	private Float docTotal;

	@Column(name = "DocStatus")
	private String docStatus;

	@Transient
	private String type;

	@Transient
	private List<InvoiceItems> invoiceItemsList;

	public OINV() {
	}

	public OINV(int docEntry, int docNum, String docDate, String docDueDate, String cardCode, String cardName,
			Float docTotal, String docStatus) {
		this.docEntry = docEntry;
		this.docNum = docNum;
		this.docDate = docDate;
		this.docDueDate = docDueDate;
		this.cardCode = cardCode;
		this.cardName = cardName;
		this.docTotal = docTotal;
		this.docStatus = docStatus;
	}

	public OINV(int docEntry, int docNum, String docDate, String docDueDate, String cardCode, String cardName,
			Float docTotal, String docStatus, String type) {
		this.docEntry = docEntry;
		this.docNum = docNum;
		this.docDate = docDate;
		this.docDueDate = docDueDate;
		this.cardCode = cardCode;
		this.cardName = cardName;
		this.docTotal = docTotal;
		this.docStatus = docStatus;
		this.type = type;
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

	public String getDocDate() {
		return docDate;
	}

	public void setDocDate(String docDate) {
		this.docDate = docDate;
	}

	public String getDocDueDate() {
		return docDueDate;
	}

	public void setDocDueDate(String docDueDate) {
		this.docDueDate = docDueDate;
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

	public Float getDocTotal() {
		return docTotal;
	}

	public void setDocTotal(Float docTotal) {
		this.docTotal = docTotal;
	}

	public String getDocStatus() {
		return docStatus;
	}

	public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OINV [docEntry=");
		builder.append(docEntry);
		builder.append(", docNum=");
		builder.append(docNum);
		builder.append(", docDate=");
		builder.append(docDate);
		builder.append(", docDueDate=");
		builder.append(docDueDate);
		builder.append(", cardCode=");
		builder.append(cardCode);
		builder.append(", cardName=");
		builder.append(cardName);
		builder.append(", docTotal=");
		builder.append(docTotal);
		builder.append(", docStatus=");
		builder.append(docStatus);
		builder.append(", type=");
		builder.append(type);
		builder.append(", invoiceItemsList=");
		builder.append(invoiceItemsList);
		builder.append("]");
		return builder.toString();
	}

}
