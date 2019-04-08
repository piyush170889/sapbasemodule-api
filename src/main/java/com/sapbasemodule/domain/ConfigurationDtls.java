package com.sapbasemodule.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="configuration_dtls")
public class ConfigurationDtls {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CONFIGURATION_DTLS_ID")
	private int configurationDtlsId;
	
	@Column(name="CONFIGURATION_NAME")
	private String configurationName;
	
	@Column(name="CONFIGURATION_VALUE")
	private String configurationValue;
	
	@Column(name="CONFIGURATION_DESC")
	private String configurationDesc;
	
	@Column(name="IS_ACTIVE")
	private byte isActive;
	
	@JsonIgnore
	@Column(name="CREATED_TS")
	private String createdTs;
	
	@JsonIgnore
	@Column(name="MODIFIED_TS")
	private String modifiedTs;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name="CREATED_BY")
	private UserDtl createdBy;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name="MODIFIED_BY")
	private UserDtl modifiedBy;
	
	public ConfigurationDtls() {}

	public int getConfigurationDtlsId() {
		return configurationDtlsId;
	}

	public void setConfigurationDtlsId(int configurationDtlsId) {
		this.configurationDtlsId = configurationDtlsId;
	}

	public String getConfigurationName() {
		return configurationName;
	}

	public void setConfigurationName(String configurationName) {
		this.configurationName = configurationName;
	}

	public String getConfigurationValue() {
		return configurationValue;
	}

	public void setConfigurationValue(String configurationValue) {
		this.configurationValue = configurationValue;
	}

	public String getConfigurationDesc() {
		return configurationDesc;
	}

	public void setConfigurationDesc(String configurationDesc) {
		this.configurationDesc = configurationDesc;
	}

	public byte getIsActive() {
		return isActive;
	}

	public void setIsActive(byte isActive) {
		this.isActive = isActive;
	}

	public String getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(String createdTs) {
		this.createdTs = createdTs;
	}

	public String getModifiedTs() {
		return modifiedTs;
	}

	public void setModifiedTs(String modifiedTs) {
		this.modifiedTs = modifiedTs;
	}

	public UserDtl getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserDtl createdBy) {
		this.createdBy = createdBy;
	}

	public UserDtl getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(UserDtl modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Override
	public String toString() {
		return "ConfigurationDtls [configurationDtlsId=" + configurationDtlsId + ", configurationName="
				+ configurationName + ", configurationValue=" + configurationValue + ", configurationDesc="
				+ configurationDesc + ", isActive=" + isActive + ", createdTs=" + createdTs + ", modifiedTs="
				+ modifiedTs + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + "]";
	}
	
	
}
