package com.sapbasemodule.model;

public class ExcelSheet {

	private String dt;

	// private String tm;

	private String pn;

	private String qt;

	private String ip;

	private String mrp;

	private String ty;

	private String subTotal;

	private String discount;

	private String t;

	public ExcelSheet() {
	}

	/*public ExcelSheet(String dt , String pn, String qt, String ip, String mrp, String ty, String t) {
		this.dt = dt;
		// this.tm = tm;
		this.pn = pn;
		this.qt = qt;
		this.ip = ip;
		this.mrp = mrp;
		this.ty = ty;
		this.t = t;
	}*/

	public ExcelSheet(String dt, String pn, String qt, String ip, String mrp, String ty, String subTotal,
			String discount, String t) {
		this.dt = dt;
		this.pn = pn;
		this.qt = qt;
		this.ip = ip;
		this.mrp = mrp;
		this.ty = ty;
		this.subTotal = subTotal;
		this.discount = discount;
		this.t = t;
	}

	public String getDt() {
		return dt;
	}

	public void setDt(String dt) {
		this.dt = dt;
	}

	// public String getTm() {
	// return tm;
	// }
	//
	// public void setTm(String tm) {
	// this.tm = tm;
	// }

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getQt() {
		return qt;
	}

	public void setQt(String qt) {
		this.qt = qt;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMrp() {
		return mrp;
	}

	public void setMrp(String mrp) {
		this.mrp = mrp;
	}

	public String getTy() {
		return ty;
	}

	public void setTy(String ty) {
		this.ty = ty;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExcelSheet [dt=");
		builder.append(dt);
		builder.append(", pn=");
		builder.append(pn);
		builder.append(", qt=");
		builder.append(qt);
		builder.append(", ip=");
		builder.append(ip);
		builder.append(", mrp=");
		builder.append(mrp);
		builder.append(", ty=");
		builder.append(ty);
		builder.append(", subTotal=");
		builder.append(subTotal);
		builder.append(", discount=");
		builder.append(discount);
		builder.append(", t=");
		builder.append(t);
		builder.append("]");
		return builder.toString();
	}

}
