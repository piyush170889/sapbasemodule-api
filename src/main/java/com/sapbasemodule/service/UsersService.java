package com.sapbasemodule.service;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import com.sapbasemodule.domain.Customers;
import com.sapbasemodule.domain.SiteDtls;
import com.sapbasemodule.domain.SiteVisitHistory;
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

	BaseWrapper doGetLoggedInUsersVisitHistory();

	BaseWrapper doPunchUsersVisitEntry(SiteVisitHistory request);

	BaseWrapper doPunchUsersVisitExit(SiteVisitHistory request) throws ServicesException;

	List<SiteVisitHistory> doGetUsersVisitHistory();

	SiteVisitHistory findVisitSiteById(Integer siteVisitHistoryId);

	void saveVisitSites(List<SiteVisitHistory> siteVisitHistoryList);

	List<SiteVisitHistory> findVisitSitesByIds(Set<Integer> siteVisitHistoryIdsSet);

	List<Customers> doGetCustomersListToPopulateDD();

	List<SiteDtls> doGetLocationsListToPopulateDD();
}
