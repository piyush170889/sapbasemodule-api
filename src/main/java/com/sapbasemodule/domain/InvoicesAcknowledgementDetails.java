package com.sapbasemodule.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "invoices_acknowledgement_details")
public class InvoicesAcknowledgementDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "INVOICE_ACKNOWLEDGEMENT_DTLS_ID")
	private int invoicesAcknowledgementDtlsId;

	@Column(name = "INVOICE_NO")
	private int invoiceNo;

	@Column(name = "SIGNATURE")
	private String signature;

	@Column(name = "CREATED_DT")
	private String createdDt;

	@Column(name = "CREATED_TIME")
	private String createdTime;

	public InvoicesAcknowledgementDetails() {
	}

	public InvoicesAcknowledgementDetails(int invoicesAcknowledgementDtlsId, int invoiceNo, String signature,
			String createdDt, String createdTime) {
		this.invoicesAcknowledgementDtlsId = invoicesAcknowledgementDtlsId;
		this.invoiceNo = invoiceNo;
		this.signature = signature;
		this.createdDt = createdDt;
		this.createdTime = createdTime;
	}

	public String getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(String createdDt) {
		this.createdDt = createdDt;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public int getInvoicesAcknowledgementDtlsId() {
		return invoicesAcknowledgementDtlsId;
	}

	public void setInvoicesAcknowledgementDtlsId(int invoicesAcknowledgementDtlsId) {
		this.invoicesAcknowledgementDtlsId = invoicesAcknowledgementDtlsId;
	}

	public int getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InvoicesAcknowledgementDetails [invoicesAcknowledgementDtlsId=");
		builder.append(invoicesAcknowledgementDtlsId);
		builder.append(", invoiceNo=");
		builder.append(invoiceNo);
		builder.append(", signature=");
		builder.append(signature);
		builder.append(", createdDt=");
		builder.append(createdDt);
		builder.append(", createdTime=");
		builder.append(createdTime);
		builder.append("]");
		return builder.toString();
	}

}
