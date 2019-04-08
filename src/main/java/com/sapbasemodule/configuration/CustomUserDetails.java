package com.sapbasemodule.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sapbasemodule.domain.RoleMasterDtl;
import com.sapbasemodule.domain.UserDtl;

public class CustomUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6608152914878047686L;

	
	private String password;
	
	private Set<RoleMasterDtl> roles;
	
	private String userLoginDtlsId;

	private String activationCode;

	private String contactNum;

	private String forgotPassCode;

	private byte isActive;

	private byte isEmailVerified;

	private byte isPasswordChanged;

	private byte termsAndCondition;
	
	private UserDtl userDtl;

	public CustomUserDetails() {}
	
	
	public String getUserLoginDtlsId() {
		return userLoginDtlsId;
	}


	public void setUserLoginDtlsId(String userLoginDtlsId) {
		this.userLoginDtlsId = userLoginDtlsId;
	}


	public String getActivationCode() {
		return activationCode;
	}


	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}


	public String getContactNum() {
		return contactNum;
	}


	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}


	public String getForgotPassCode() {
		return forgotPassCode;
	}


	public void setForgotPassCode(String forgotPassCode) {
		this.forgotPassCode = forgotPassCode;
	}


	public byte getIsActive() {
		return isActive;
	}


	public void setIsActive(byte isActive) {
		this.isActive = isActive;
	}


	public byte getIsEmailVerified() {
		return isEmailVerified;
	}


	public void setIsEmailVerified(byte isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}


	public byte getIsPasswordChanged() {
		return isPasswordChanged;
	}


	public void setIsPasswordChanged(byte isPasswordChanged) {
		this.isPasswordChanged = isPasswordChanged;
	}


	public byte getTermsAndCondition() {
		return termsAndCondition;
	}


	public void setTermsAndCondition(byte termsAndCondition) {
		this.termsAndCondition = termsAndCondition;
	}


	public UserDtl getUserDtl() {
		return userDtl;
	}


	public void setUserDtl(UserDtl userDtl) {
		this.userDtl = userDtl;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Set<RoleMasterDtl> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<RoleMasterDtl> roles) {
		this.roles = roles;
	}
	
	//Security Framework Specific Methods
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<SimpleGrantedAuthority>();

		for (RoleMasterDtl role : roles) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getRolesMasterDtlsId()));
		}

		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		
		return password;
	}

	@Override
	public String getUsername() {
		
		return contactNum;
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomUserDetails [password=");
		builder.append(password);
		builder.append(", roles=");
		builder.append(roles);
		builder.append(", userLoginDtlsId=");
		builder.append(userLoginDtlsId);
		builder.append(", activationCode=");
		builder.append(activationCode);
		builder.append(", contactNum=");
		builder.append(contactNum);
		builder.append(", forgotPassCode=");
		builder.append(forgotPassCode);
		builder.append(", isActive=");
		builder.append(isActive);
		builder.append(", isEmailVerified=");
		builder.append(isEmailVerified);
		builder.append(", isPasswordChanged=");
		builder.append(isPasswordChanged);
		builder.append(", termsAndCondition=");
		builder.append(termsAndCondition);
		builder.append(", userDtl=");
		builder.append(userDtl);
		builder.append("]");
		return builder.toString();
	}

	
	
}
