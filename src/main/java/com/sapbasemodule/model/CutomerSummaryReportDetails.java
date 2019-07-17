package com.sapbasemodule.model;

public class CutomerSummaryReportDetails {

	private String cardCode;

	private String cardName;

	private String salesEmpName;

	private String brand;

	private String apr;

	private String may;

	private String jun;

	private String jul;

	private String aug;

	private String sep;

	private String oct;

	private String nov;

	private String dec;

	private String jan;

	private String feb;

	private String mar;

	public CutomerSummaryReportDetails() {
	}

	public CutomerSummaryReportDetails(String cardCode, String cardName, String salesEmpName, String brand, String apr,
			String may, String jun, String jul, String aug, String sep, String oct, String nov, String dec, String jan,
			String feb, String mar) {
		this.cardCode = cardCode;
		this.cardName = cardName;
		this.salesEmpName = salesEmpName;
		this.brand = brand;
		this.apr = apr;
		this.may = may;
		this.jun = jun;
		this.jul = jul;
		this.aug = aug;
		this.sep = sep;
		this.oct = oct;
		this.nov = nov;
		this.dec = dec;
		this.jan = jan;
		this.feb = feb;
		this.mar = mar;
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

	public String getSalesEmpName() {
		return salesEmpName;
	}

	public void setSalesEmpName(String salesEmpName) {
		this.salesEmpName = salesEmpName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getApr() {
		return apr;
	}

	public void setApr(String apr) {
		this.apr = apr;
	}

	public String getMay() {
		return may;
	}

	public void setMay(String may) {
		this.may = may;
	}

	public String getJun() {
		return jun;
	}

	public void setJun(String jun) {
		this.jun = jun;
	}

	public String getJul() {
		return jul;
	}

	public void setJul(String jul) {
		this.jul = jul;
	}

	public String getAug() {
		return aug;
	}

	public void setAug(String aug) {
		this.aug = aug;
	}

	public String getSep() {
		return sep;
	}

	public void setSep(String sep) {
		this.sep = sep;
	}

	public String getOct() {
		return oct;
	}

	public void setOct(String oct) {
		this.oct = oct;
	}

	public String getNov() {
		return nov;
	}

	public void setNov(String nov) {
		this.nov = nov;
	}

	public String getDec() {
		return dec;
	}

	public void setDec(String dec) {
		this.dec = dec;
	}

	public String getJan() {
		return jan;
	}

	public void setJan(String jan) {
		this.jan = jan;
	}

	public String getFeb() {
		return feb;
	}

	public void setFeb(String feb) {
		this.feb = feb;
	}

	public String getMar() {
		return mar;
	}

	public void setMar(String mar) {
		this.mar = mar;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CutomerSummaryReportDetails [cardCode=");
		builder.append(cardCode);
		builder.append(", cardName=");
		builder.append(cardName);
		builder.append(", salesEmpName=");
		builder.append(salesEmpName);
		builder.append(", brand=");
		builder.append(brand);
		builder.append(", apr=");
		builder.append(apr);
		builder.append(", may=");
		builder.append(may);
		builder.append(", jun=");
		builder.append(jun);
		builder.append(", jul=");
		builder.append(jul);
		builder.append(", aug=");
		builder.append(aug);
		builder.append(", sep=");
		builder.append(sep);
		builder.append(", oct=");
		builder.append(oct);
		builder.append(", nov=");
		builder.append(nov);
		builder.append(", dec=");
		builder.append(dec);
		builder.append(", jan=");
		builder.append(jan);
		builder.append(", feb=");
		builder.append(feb);
		builder.append(", mar=");
		builder.append(mar);
		builder.append("]");
		return builder.toString();
	}

}
