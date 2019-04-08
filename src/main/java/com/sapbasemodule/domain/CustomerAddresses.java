package com.sapbasemodule.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.sapbasemodule.model.CustomerAddressesKey;

@Entity
@Table(name = "CRD1")
@IdClass(CustomerAddressesKey.class)
public class CustomerAddresses {

	@Id
	@Column(name = "Address")
	private String address;

	@Id
	@Column(name = "CardCode")
	private String cardCode;

	@Id
	@Column(name = "AdresType")
	private String adresType;

	@Column(name = "Street")
	private String street;

	@Column(name = "Block")
	private String block;

	@Column(name = "ZipCode")
	private String zipCode;

	@Column(name = "City")
	private String city;

	@Column(name = "County")
	private String county;

	@Column(name = "Country")
	private String country;

	@Column(name = "State")
	private String state;

	@Column(name = "UserSign")
	private String userSign;

	@Column(name = "Building")
	private String building;

	@Column(name = "Address2")
	private String address2;

	@Column(name = "Address3")
	private String address3;

	@Column(name = "StreetNo")
	private String streetNo;

	@Column(name = "AltCrdName")
	private String altCrdName;

	@Column(name = "GSTRegnNo")
	private String gstRegnNo;

	@Column(name = "GSTType")
	private String gstType;

	@Column(name = "U_SitDis")
	private String uSitDis;
	
	public CustomerAddresses() {}

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

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUserSign() {
		return userSign;
	}

	public void setUserSign(String userSign) {
		this.userSign = userSign;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getStreetNo() {
		return streetNo;
	}

	public void setStreetNo(String streetNo) {
		this.streetNo = streetNo;
	}

	public String getAltCrdName() {
		return altCrdName;
	}

	public void setAltCrdName(String altCrdName) {
		this.altCrdName = altCrdName;
	}

	public String getGstRegnNo() {
		return gstRegnNo;
	}

	public void setGstRegnNo(String gstRegnNo) {
		this.gstRegnNo = gstRegnNo;
	}

	public String getGstType() {
		return gstType;
	}

	public void setGstType(String gstType) {
		this.gstType = gstType;
	}

	public String getuSitDis() {
		return uSitDis;
	}

	public void setuSitDis(String uSitDis) {
		this.uSitDis = uSitDis;
	}
	
	
}
