package com.sapbasemodule.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="master_data")
public class Master {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="MASTER_DATA_ID")
	private int masterDataId;
	
	@Column(name="GRP")
	private String grp;
	
	@Column(name="SUB_GROUP")
	private String subGroup;
	
	@Column(name="DISPLAY_TEXT")
	private String displayText;
	
	@Column(name="VALUE")
	private String value;
	
	@Column(name="CODE")
	private String code;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="IS_ACTIVE")
	private byte isActive;
	
	public int getMasterDataId() {
		return masterDataId;
	}

	public void setMasterDataId(int masterDataId) {
		this.masterDataId = masterDataId;
	}

	public String getGrp() {
		return grp;
	}

	public void setGrp(String grp) {
		this.grp = grp;
	}

	public String getSubGroup() {
		return subGroup;
	}

	public void setSubGroup(String subGroup) {
		this.subGroup = subGroup;
	}

	public String getDisplayText() {
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte getIsActive() {
		return isActive;
	}

	public void setIsActive(byte isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "MasterData [masterDataId=" + masterDataId + ", grp=" + grp + ", subGroup=" + subGroup + ", displayText="
				+ displayText + ", value=" + value + ", code=" + code + ", description=" + description + ", isActive="
				+ isActive + "]";
	}
	
	
	
}

