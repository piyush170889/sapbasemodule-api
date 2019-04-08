package com.sapbasemodule.domain;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the configuration database table.
 * 
 */
@Entity
@NamedQuery(name="Configuration.findAll", query="SELECT c FROM Configuration c")
public class Configuration extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CONFIG_ID")
	private int configId;

	@Column(name="CONFIG_DESC")
	private String configDesc;

	@Column(name="CONFIG_IMG")
	private String configImg;

	@Column(name="CONFIG_NAME")
	private String configName;

	@Column(name="CONFIG_VAL")
	private String configVal;


	public Configuration() {
	}

	public int getConfigId() {
		return this.configId;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}

	public String getConfigDesc() {
		return this.configDesc;
	}

	public void setConfigDesc(String configDesc) {
		this.configDesc = configDesc;
	}

	public String getConfigImg() {
		return this.configImg;
	}

	public void setConfigImg(String configImg) {
		this.configImg = configImg;
	}

	public String getConfigName() {
		return this.configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getConfigVal() {
		return this.configVal;
	}

	public void setConfigVal(String configVal) {
		this.configVal = configVal;
	}


}