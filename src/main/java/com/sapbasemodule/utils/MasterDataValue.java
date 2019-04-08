package com.sapbasemodule.utils;

public enum MasterDataValue {

	SUBGROUP_TERMS_CONDITION("terms.condition"),
	GROUP_UOM("UOM"),	
	GROUP_TERMS("terms"),
	GROUP_ADMIN("admin");
	
	private String value;
	
	MasterDataValue(String value){
		this.value = value;
	}
	
	@Override
	public String toString() {
		return this.value.toString();
	}
}
