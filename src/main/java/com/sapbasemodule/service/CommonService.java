package com.sapbasemodule.service;

import javax.servlet.http.HttpServletRequest;

import com.sapbasemodule.domain.OtpDtl;
import com.sapbasemodule.exception.ServicesException;
import com.sapbasemodule.model.BaseWrapper;
import com.sapbasemodule.model.ChangePassword;

public interface CommonService {

	BaseWrapper doSendOtp(OtpDtl otpDetails) throws Exception;

	BaseWrapper recoverForgetPassword(String recoveryContact) throws ServicesException;

	int updateUserLoginDtls(HttpServletRequest request) throws ServicesException;

	BaseWrapper changePassword(ChangePassword changePassword) throws ServicesException;

}
