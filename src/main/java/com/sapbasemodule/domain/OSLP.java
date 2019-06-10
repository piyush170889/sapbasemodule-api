package com.sapbasemodule.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OSLP")
public class OSLP {

	@Id
	@Column(name = "SlpCode")
	private String slpCode;

	@Column(name = "SlpName")
	private String slpName;

	@Column(name = "Mobil")
	private String mobil;

	public OSLP() {
	}

	public OSLP(String slpCode, String slpName, String mobil) {
		this.slpCode = slpCode;
		this.slpName = slpName;
		this.mobil = mobil;
	}

	public String getSlpCode() {
		return slpCode;
	}

	public void setSlpCode(String slpCode) {
		this.slpCode = slpCode;
	}

	public String getSlpName() {
		return slpName;
	}

	public void setSlpName(String slpName) {
		this.slpName = slpName;
	}

	public String getMobil() {
		return mobil;
	}

	public void setMobil(String mobil) {
		this.mobil = mobil;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OSLP [slpCode=");
		builder.append(slpCode);
		builder.append(", slpName=");
		builder.append(slpName);
		builder.append(", mobil=");
		builder.append(mobil);
		builder.append("]");
		return builder.toString();
	}

}
