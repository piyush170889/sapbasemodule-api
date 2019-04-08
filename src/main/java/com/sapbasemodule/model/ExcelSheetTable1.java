package com.sapbasemodule.model;

public class ExcelSheetTable1 {

	private String cl;

	private String wd;

	private String uu;

	private String ts;

	private String creditIssued;

	public ExcelSheetTable1() {
	}

	public ExcelSheetTable1(String cl, String wd, String uu, String ts, String creditIssued) {
		this.cl = cl;
		this.wd = wd;
		this.uu = uu;
		this.ts = ts;
		this.creditIssued = creditIssued;
	}

	public String getCreditIssued() {
		return creditIssued;
	}

	public void setCreditIssued(String creditIssued) {
		this.creditIssued = creditIssued;
	}

	public String getCl() {
		return cl;
	}

	public void setCl(String cl) {
		this.cl = cl;
	}

	public String getWd() {
		return wd;
	}

	public void setWd(String wd) {
		this.wd = wd;
	}

	public String getUu() {
		return uu;
	}

	public void setUu(String uu) {
		this.uu = uu;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Override
	public String toString() {
		return "ExcelSheetTable1 [cl=" + cl + ", wd=" + wd + ", uu=" + uu + ", ts=" + ts + "]";
	}

}
