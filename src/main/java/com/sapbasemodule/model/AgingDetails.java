package com.sapbasemodule.model;

public class AgingDetails {

	private String shortName;

	private String cardName;

	private String balanceDue;

	private String firstQ;

	private String secondQ;

	private String thirdQ;

	private String fourthQ;

	private String otherQ;

	public AgingDetails() {
	}

	public AgingDetails(String shortName, String cardName, String balanceDue, String firstQ, String secondQ,
			String thirdQ, String fourthQ, String otherQ) {
		this.shortName = shortName;
		this.cardName = cardName;
		this.firstQ = firstQ;
		this.secondQ = secondQ;
		this.thirdQ = thirdQ;
		this.fourthQ = fourthQ;
		this.otherQ = otherQ;
		this.balanceDue = balanceDue;
	}

	public String getBalanceDue() {
		return balanceDue;
	}

	public void setBalanceDue(String balanceDue) {
		this.balanceDue = balanceDue;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getFirstQ() {
		return firstQ;
	}

	public void setFirstQ(String firstQ) {
		this.firstQ = firstQ;
	}

	public String getSecondQ() {
		return secondQ;
	}

	public void setSecondQ(String secondQ) {
		this.secondQ = secondQ;
	}

	public String getThirdQ() {
		return thirdQ;
	}

	public void setThirdQ(String thirdQ) {
		this.thirdQ = thirdQ;
	}

	public String getFourthQ() {
		return fourthQ;
	}

	public void setFourthQ(String fourthQ) {
		this.fourthQ = fourthQ;
	}

	public String getOtherQ() {
		return otherQ;
	}

	public void setOtherQ(String otherQ) {
		this.otherQ = otherQ;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AgingDetails [shortName=");
		builder.append(shortName);
		builder.append(", cardName=");
		builder.append(cardName);
		builder.append(", firstQ=");
		builder.append(firstQ);
		builder.append(", secondQ=");
		builder.append(secondQ);
		builder.append(", thirdQ=");
		builder.append(thirdQ);
		builder.append(", fourthQ=");
		builder.append(fourthQ);
		builder.append(", otherQ=");
		builder.append(otherQ);
		builder.append("]");
		return builder.toString();
	}

}
