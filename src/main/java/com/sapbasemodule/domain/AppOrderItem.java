package com.sapbasemodule.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AppOrderItem")
public class AppOrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AppOrderItemId")
	private int appOrderItemId;

	@Column(name = "App_Ordr_Id")
	private int appOrdrId;

	@Column(name = "ItemCode")
	private String itemCode;

	@Column(name = "ItemName")
	private String itemName;

	@Column(name = "Qty")
	private String qty;

	@Column(name = "CreatedBy")
	private String createdBy;

	@Column(name = "CreatedTs")
	private Date createdTs;

	@Column(name = "UpdatedTs")
	private Date updatedTs;

	@Column(name = "ModifiedBy")
	private String modifiedBy;

	public AppOrderItem() {
	}

	public int getAppOrderItemId() {
		return appOrderItemId;
	}

	public void setAppOrderItemId(int appOrderItemId) {
		this.appOrderItemId = appOrderItemId;
	}

	public int getAppOrdrId() {
		return appOrdrId;
	}

	public void setAppOrdrId(int appOrdrId) {
		this.appOrdrId = appOrdrId;
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

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AppOrderItem [appOrderItem=");
		builder.append(appOrderItemId);
		builder.append(", appOrdrId=");
		builder.append(appOrdrId);
		builder.append(", itemCode=");
		builder.append(itemCode);
		builder.append(", itemName=");
		builder.append(itemName);
		builder.append(", qty=");
		builder.append(qty);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdTs=");
		builder.append(createdTs);
		builder.append(", updatedTs=");
		builder.append(updatedTs);
		builder.append(", modifiedBy=");
		builder.append(modifiedBy);
		builder.append("]");
		return builder.toString();
	}

}
