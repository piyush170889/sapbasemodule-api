package com.sapbasemodule.model;

import java.io.Serializable;

public class CustomerAddressesKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8194196602928886251L;

	private String address;

	private String cardCode;

	private String adresType;

	public CustomerAddressesKey() {
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getAdresType() {
		return adresType;
	}

	public void setAdresType(String adresType) {
		this.adresType = adresType;
	}

}
