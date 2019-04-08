package com.sapbasemodule.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the oauth_access_token database table.
 * 
 */
@Entity
@Table(name="oauth_access_token")
@NamedQuery(name="OauthAccessToken.findAll", query="SELECT o FROM OauthAccessToken o")
public class OauthAccessToken implements Serializable {

	private static final long serialVersionUID = 1L;

	@Lob
//	@Column(name="AUTHENTICATION")
	private byte[] authentication;

	@Column(name="AUTHENTICATION_ID")
	private String authenticationId;

	@Column(name="CLIENT_ID")
	private String clientId;

	@Column(name="CREATED_TS")
	private Timestamp createdTs;

	@Column(name="MODIFIED_TS")
	private Timestamp modifiedTs;

	@Column(name="REFRESH_TOKEN")
	private String refreshToken;

	@Lob
//	@Column(name="TOKEN")
	private byte[] token;

	@Column(name="TOKEN_ID")
	private String tokenId;

	@Column(name="USER_NAME")
	private String userName;

	public OauthAccessToken() {
	}

	public byte[] getAuthentication() {
		return this.authentication;
	}

	public void setAuthentication(byte[] authentication) {
		this.authentication = authentication;
	}

	public String getAuthenticationId() {
		return this.authenticationId;
	}

	public void setAuthenticationId(String authenticationId) {
		this.authenticationId = authenticationId;
	}

	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
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

	public String getRefreshToken() {
		return this.refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
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

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}