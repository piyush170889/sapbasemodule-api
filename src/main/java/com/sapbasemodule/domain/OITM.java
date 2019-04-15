package com.sapbasemodule.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OITM")
public class OITM {

	@Id
	@Column(name = "ItemCode")
	private String itemCode;

	@Column(name = "ItemName")
	private String itemName;

	@Column(name = "ItmsGrpCod")
	private int itmsGrpCod;

	@Column(name = "Deleted")
	private String deleted;

	@Column(name = "InvntryUom")
	private String invntryUom;

	public OITM() {
	}

	public OITM(String itemCode, String itemName, int itmsGrpCod, String deleted, String invntryUom) {
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.itmsGrpCod = itmsGrpCod;
		this.deleted = deleted;
		this.invntryUom = invntryUom;
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

	public int getItmsGrpCod() {
		return itmsGrpCod;
	}

	public void setItmsGrpCod(int itmsGrpCod) {
		this.itmsGrpCod = itmsGrpCod;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getInvntryUom() {
		return invntryUom;
	}

	public void setInvntryUom(String invntryUom) {
		this.invntryUom = invntryUom;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OITM [itemCode=");
		builder.append(itemCode);
		builder.append(", itemName=");
		builder.append(itemName);
		builder.append(", itmsGrpCod=");
		builder.append(itmsGrpCod);
		builder.append(", deleted=");
		builder.append(deleted);
		builder.append(", invntryUom=");
		builder.append(invntryUom);
		builder.append("]");
		return builder.toString();
	}

}
