package com.sapbasemodule.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "APP_ORDR")
public class AppOrders {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "App_Ordr_Id")
	private int appOrdrId;

	@Column(name = "DocEntry")
	private int docEntry;

	@Column(name = "DocNum")
	private int docNum;

	@Column(name = "DocDate")
	private String docDate;

	@Column(name = "CardCode")
	private String cardCode;

	@Column(name = "CardName")
	private String cardName;

	@Column(name = "ShipToCode")
	private String shipToCode;

	@Column(name = "CreatedTs")
	private Date createdTs;

	@Column(name = "UpdatedTs")
	private Date updatedTs;

	@Column(name = "CreatedBy")
	private String createdBy;

	@Column(name = "ModifiedBy")
	private String modifiedBy;

	@Column(name = "IsSynched")
	private byte isSynched;

	@Transient
	private List<AppOrderItem> itemsList;

	public AppOrders() {
	}

	public AppOrders(int appOrdrId, int docEntry, int docNum, String docDate, String cardCode, String cardName,
			String shipToCode, Date createdTs, Date updatedTs, String createdBy, String modifiedBy, byte isSynched) {
		this.isSynched = isSynched;
		this.appOrdrId = appOrdrId;
		this.docEntry = docEntry;
		this.docNum = docNum;
		this.docDate = docDate;
		this.cardCode = cardCode;
		this.cardName = cardName;
		this.shipToCode = shipToCode;
		this.createdTs = createdTs;
		this.updatedTs = updatedTs;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
	}

	public byte getIsSynched() {
		return isSynched;
	}

	public void setIsSynched(byte isSynched) {
		this.isSynched = isSynched;
	}

	public List<AppOrderItem> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<AppOrderItem> itemsList) {
		this.itemsList = itemsList;
	}

	public int getAppOrdrId() {
		return appOrdrId;
	}

	public void setAppOrdrId(int appOrdrId) {
		this.appOrdrId = appOrdrId;
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

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public Date getUpdatedTs() {
		return updatedTs;
	}

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AppOrders [appOrdrId=");
		builder.append(appOrdrId);
		builder.append(", DocEntry=");
		builder.append(docEntry);
		builder.append(", docNum=");
		builder.append(docNum);
		builder.append(", docDate=");
		builder.append(docDate);
		builder.append(", cardCode=");
		builder.append(cardCode);
		builder.append(", cardName=");
		builder.append(cardName);
		builder.append(", shipToCode=");
		builder.append(shipToCode);
		builder.append(", createdTs=");
		builder.append(createdTs);
		builder.append(", updatedTs=");
		builder.append(updatedTs);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", modifiedBy=");
		builder.append(modifiedBy);
		builder.append(", isSynched=");
		builder.append(isSynched);
		builder.append("]");
		return builder.toString();
	}

}
