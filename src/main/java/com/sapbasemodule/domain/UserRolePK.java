package com.sapbasemodule.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserRolePK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6096490448311594012L;

	@Column(name="USER_ROLE_DTLS_ID")
	private int userRoleDtlsId;

	@Column(name="USER_LOGIN_DTLS_ID")
	private String userLoginDtlsId;

	@Column(name="ROLES_MASTER_DTLS_ID")
	private String rolesMasterDtlsId;

	public UserRolePK() {}
	
	
	public UserRolePK(String userLoginDtlsId, String rolesMasterDtlsId) {
		this.userLoginDtlsId = userLoginDtlsId;
		this.rolesMasterDtlsId = rolesMasterDtlsId;
	}


	public int getUserRoleDtlsId() {
		return userRoleDtlsId;
	}


	public void setUserRoleDtlsId(int userRoleDtlsId) {
		this.userRoleDtlsId = userRoleDtlsId;
	}


	public String getUserLoginDtlsId() {
		return userLoginDtlsId;
	}


	public void setUserLoginDtlsId(String userLoginDtlsId) {
		this.userLoginDtlsId = userLoginDtlsId;
	}


	public String getRolesMasterDtlsId() {
		return rolesMasterDtlsId;
	}


	public void setRolesMasterDtlsId(String rolesMasterDtlsId) {
		this.rolesMasterDtlsId = rolesMasterDtlsId;
	}


	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserRolePK)) {
			return false;
		}
		UserRolePK castOther = (UserRolePK) other;
		return this.userLoginDtlsId.equals(castOther.userLoginDtlsId) && this.rolesMasterDtlsId.equals(castOther.rolesMasterDtlsId);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userLoginDtlsId.hashCode();
		hash = hash * prime + this.rolesMasterDtlsId.hashCode();

		return hash;
	}

	@Override
	public String toString() {
		return "UserRolePK [userRoleDtlsId=" + userRoleDtlsId + ", userLoginDtlsId=" + userLoginDtlsId
				+ ", rolesMasterDtlsId=" + rolesMasterDtlsId + "]";
	}
	
	
}
