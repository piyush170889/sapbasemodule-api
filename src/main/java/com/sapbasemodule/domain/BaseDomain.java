package com.sapbasemodule.domain;

import java.sql.Timestamp;

import javax.persistence.Column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sapbasemodule.utils.CommonUtility;

@Component
public class BaseDomain {

	@Autowired
	private CommonUtility commonUtility;
	
	@Column(name="CREATED_TS",insertable=false, updatable=false)
	private Timestamp createdTs;

	@Column(name="CREATED_BY", insertable=true, updatable=false)
	private String createdBy = "Piyush";

	@Column(name="MODIFIED_TS",insertable=false, updatable=false)
	private Timestamp modifiedTs;

	@Column(name="MODIFIED_BY", insertable=true, updatable=true)
	private String modifiedBy = "Piyush";

	public BaseDomain() {}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BaseDomain [modifiedBy=");
		builder.append(modifiedBy);
		builder.append(", modifiedTs=");
		builder.append(modifiedTs);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdTs=");
		builder.append(createdTs);
		builder.append("]");
		return builder.toString();
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getModifiedTs() {
		return modifiedTs;
	}

	public void setModifiedTs(Timestamp modifiedTs) {
		this.modifiedTs = modifiedTs;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Timestamp createdTs) {
		this.createdTs = createdTs;
	}

	public void setCreateAndModifiedBy() {
		this.createdBy = commonUtility.getLoggedUserId();
		this.modifiedBy = this.createdBy;
	}
	
}
