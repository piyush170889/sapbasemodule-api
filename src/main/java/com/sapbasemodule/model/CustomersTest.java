package com.sapbasemodule.model;

import javax.persistence.Column;

public class CustomersTest {

	@Column(name="CardCode")
	private String cardCode;

	@Column(name="CardName")
	private String cardName;

	public CustomersTest() {
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomersTest [cardCode=");
		builder.append(cardCode);
		builder.append(", cardName=");
		builder.append(cardName);
		builder.append("]");
		return builder.toString();
	}

}
