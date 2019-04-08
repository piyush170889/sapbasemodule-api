package com.sapbasemodule.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the user_role_dtls database table.
 * 
 */
@Entity
@Table(name = "user_role_dtls")
@NamedQuery(name = "UserRoleDtl.findAll", query = "SELECT u FROM UserRoleDtl u")
public class UserRoleDtl extends BaseDomain implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserRolePK id;

	@Column(name = "IS_ACTIVE")
	private byte isActive;

	// uni-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "USER_LOGIN_DTLS_ID", insertable = false, updatable = false)
	private UserLoginDtl userLoginDtl;

	public UserRoleDtl() {
	}

	public UserRoleDtl(String userDtlsId, String firstName, String lastName, String contactNum, String emailId,
			Set<RoleMasterDtl> roles) {

		UserLoginDtl userLoginDtl = new UserLoginDtl();
		userLoginDtl.setContactNum(contactNum);
		userLoginDtl.setRoles(roles);

		UserDtl userDtl = new UserDtl(userDtlsId, firstName, lastName, emailId);
		userLoginDtl.setUserDtl(userDtl);

		this.userLoginDtl = userLoginDtl;
	}

	public UserRoleDtl(UserRolePK id, byte isActive, UserLoginDtl userLoginDtl) {
		this.id = id;
		this.isActive = isActive;
		this.userLoginDtl = userLoginDtl;
	}

	public UserRolePK getId() {
		return id;
	}

	public void setId(UserRolePK id) {
		this.id = id;
	}

	public byte getIsActive() {
		return isActive;
	}

	public void setIsActive(byte isActive) {
		this.isActive = isActive;
	}

	public UserLoginDtl getUserLoginDtl() {
		return userLoginDtl;
	}

	public void setUserLoginDtl(UserLoginDtl userLoginDtl) {
		this.userLoginDtl = userLoginDtl;
	}

	@Override
	public String toString() {
		return "UserRoleDtl [id=" + id + ", isActive=" + isActive + ", userLoginDtl=" + userLoginDtl + "]";
	}

}