package com.sapbasemodule.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the oauth_refresh_token database table.
 * 
 */
@Entity
@Table(name="oauth_refresh_token")
@NamedQuery(name="OauthRefreshToken.findAll", query="SELECT o FROM OauthRefreshToken o")
public class OauthRefreshToken implements Serializable {
	private static final long serialVersionUID = 1L;

	@Lob
//	@Column(name="AUTHENTICATION")
	private byte[] authentication;

	@Column(name="CREATED_TS")
	private Timestamp createdTs;

	@Column(name="MODIFIED_TS")
	private Timestamp modifiedTs;

	@Lob
//	@Column(name="TOKEN")
	private byte[] token;

	@Column(name="TOKEN_ID")
	private String tokenId;

	public OauthRefreshToken() {
	}

	public byte[] getAuthentication() {
		return this.authentication;
	}

	public void setAuthentication(byte[] authentication) {
		this.authentication = authentication;
	}

	public Timestamp getCreatedTs() {
		return this.createdTs;
	}

	public void setCreatedTs(Timestamp createdTs) {
		this.createdTs = createdTs;
	}

	public Timestamp getModifiedTs() {
		return this.modifiedTs;
	}

	public void setModifiedTs(Timestamp modifiedTs) {
		this.modifiedTs = modifiedTs;
	}

	public byte[] getToken() {
		return this.token;
	}

	public void setToken(byte[] token) {
		this.token = token;
	}

	public String getTokenId() {
		return this.tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

}