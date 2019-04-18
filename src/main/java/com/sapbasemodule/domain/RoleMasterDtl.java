package com.sapbasemodule.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the role_master_dtls database table.
 * 
 */
@Entity
@Table(name="role_master_dtls")
@NamedQuery(name="RoleMasterDtl.findAll", query="SELECT r FROM RoleMasterDtl r")
public class RoleMasterDtl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ROLES_MASTER_DTLS_ID")
	private String rolesMasterDtlsId;

	@Column(name="IS_ACTIVE")
	private byte isActive;

	@Column(name="ROLE_DESCRIPTION")
	private String roleDescription;
/*
	@Column(name="CREATED_TS",insertable=false, updatable=false)
	private Timestamp createdTs;
*/
	@Column(name="CREATED_BY", insertable=true, updatable=false)
	private String createdBy;

/*	@Column(name="MODIFIED_TS",insertable=false, updatable=false)
	private Timestamp modifiedTs;*/

	@Column(name="MODIFIED_BY", insertable=true, updatable=true)
	private String modifiedBy;
	
	public RoleMasterDtl() {
	}

	public String getRolesMasterDtlsId() {
		return this.rolesMasterDtlsId;
	}

	public void setRolesMasterDtlsId(String rolesMasterDtlsId) {
		this.rolesMasterDtlsId = rolesMasterDtlsId;
	}

	public byte getIsActive() {
		return this.isActive;
	}

	public void setIsActive(byte isActive) {
		this.isActive = isActive;
	}


	public String getRoleDescription() {
		return this.roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}


}