package com.sapbasemodule.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customers_pin")
public class CustomerPin {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CUSTOMERS_PIN_ID")
	private int customersPinId;

	@Column(name = "CARD_CODE")
	private String cardCode;

	@Column(name = "CUSTOMERS_PIN")
	private String customersPin;

	@Column(name = "UPDATED_DT")
	private String updatedDt;

	@Column(name = "UPDATED_TS")
	private String updatedTs;

	public CustomerPin() {
	}

	public CustomerPin(int customersPinId, String cardCode, String customersPin, String updatedDt, String updatedTs) {
		this.customersPinId = customersPinId;
		this.cardCode = cardCode;
		this.customersPin = customersPin;
		this.updatedDt = updatedDt;
		this.updatedTs = updatedTs;
	}

	public CustomerPin(String cardCode, String customersPin, String updatedDt, String updatedTs) {
		this.cardCode = cardCode;
		this.customersPin = customersPin;
		this.updatedDt = updatedDt;
		this.updatedTs = updatedTs;
	}

	public int getCustomersPinId() {
		return customersPinId;
	}

	public void setCustomersPinId(int customersPinId) {
		this.customersPinId = customersPinId;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getCustomersPin() {
		return customersPin;
	}

	public void setCustomersPin(String customersPin) {
		this.customersPin = customersPin;
	}

	public String getUpdatedDt() {
		return updatedDt;
	}

	public void setUpdatedDt(String updatedDt) {
		this.updatedDt = updatedDt;
	}

	public String getUpdatedTs() {
		return updatedTs;
	}

	public void setUpdatedTs(String updatedTs) {
		this.updatedTs = updatedTs;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomerPin [customersPinId=");
		builder.append(customersPinId);
		builder.append(", cardCode=");
		builder.append(cardCode);
		builder.append(", customersPin=");
		builder.append(customersPin);
		builder.append(", updatedDt=");
		builder.append(updatedDt);
		builder.append(", updatedTs=");
		builder.append(updatedTs);
		builder.append("]");
		return builder.toString();
	}

}
