package com.sapbasemodule.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the master_data database table.
 * 
 */
@Entity
@Table(name="master_data")
@NamedQuery(name="MasterData.findAll", query="SELECT m FROM MasterData m")
public class MasterData extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="MASTER_DATA_ID")
	private int masterDataId;

	private String code;

	private String description;

	@Column(name="DISPLAY_TEXT")
	private String displayText;

	private String grp;

	@Column(name="IS_ACTIVE")
	private byte isActive;

	@Column(name="SUB_GROUP")
	private String subGroup;

	private String value;


	public MasterData() {
	}

	public int getMasterDataId() {
		return this.masterDataId;
	}

	public void setMasterDataId(int masterDataId) {
		this.masterDataId = masterDataId;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDisplayText() {
		return this.displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	public String getGrp() {
		return this.grp;
	}

	public void setGrp(String grp) {
		this.grp = grp;
	}

	public byte getIsActive() {
		return this.isActive;
	}

	public void setIsActive(byte isActive) {
		this.isActive = isActive;
	}


	public String getSubGroup() {
		return this.subGroup;
	}

	public void setSubGroup(String subGroup) {
		this.subGroup = subGroup;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}


}