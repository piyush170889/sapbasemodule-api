package com.sapbasemodule.service;

import java.text.ParseException;
import java.util.List;

import com.sapbasemodule.domain.UserLoginDtl;
import com.sapbasemodule.exception.ServicesException;
import com.sapbasemodule.model.AdminUserDetails;
import com.sapbasemodule.model.BaseWrapper;

public interface UsersService {

	BaseWrapper doGetUsers(int pageNo, List<String> roles);

	BaseWrapper doGetUserDetails(String userDtlsId);

	BaseWrapper doGetLoggenInUsersRoles();

	BaseWrapper doAddAdminUsers(AdminUserDetails request);

	BaseWrapper doUpdateAdminUsers(AdminUserDetails request);

	BaseWrapper doValidatePassword(UserLoginDtl request) throws ServicesException;

	BaseWrapper doForceChangePassword(UserLoginDtl request);

	BaseWrapper doChangePasswordOfUserFromAdmin(UserLoginDtl request);

	BaseWrapper doGetUserHistoryWithTrackData(String userDtlsId, String trackDate) throws ParseException;
}
