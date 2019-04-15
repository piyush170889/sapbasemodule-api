package com.sapbasemodule.domain;

import java.io.Serializable;

public class OrderItemKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4223799786669063304L;

	private int docEntry; // Order No. With Which this orderItem is associated

	private int lineNum;

	public OrderItemKey() {
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderItemKey [docEntry=");
		builder.append(docEntry);
		builder.append(", lineNum=");
		builder.append(lineNum);
		builder.append("]");
		return builder.toString();
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserRolePK)) {
			return false;
		}
		OrderItemKey castOther = (OrderItemKey) other;
		return (this.docEntry == castOther.docEntry && this.lineNum == castOther.lineNum);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + Integer.toString(this.docEntry).hashCode();
		hash = hash * prime + Integer.toString(this.lineNum).hashCode();

		return hash;
	}

}
