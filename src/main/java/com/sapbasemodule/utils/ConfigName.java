package com.sapbasemodule.utils;

public enum ConfigName {

	QUOTATION_TNC("quotationTnc"),
	CEMENT_BAG_RATE("cementBagRate"),
	PUMPING_RATE("pumpingRate");
	
	private String configName;
	
	ConfigName(String configName) {
		this.configName = configName;
	}
	
	@Override
	public String toString() {
		return this.configName.toString();
	}
}
