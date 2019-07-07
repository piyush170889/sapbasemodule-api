package com.sapbasemodule.restcontrollers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sapbasemodule.domain.SiteVisitHistory;
import com.sapbasemodule.domain.UserLoginDtl;
import com.sapbasemodule.exception.ServicesException;
import com.sapbasemodule.model.AdminUserDetails;
import com.sapbasemodule.service.UsersService;
import com.sapbasemodule.utils.RoleType;

@RestController
@RequestMapping(value = "users")
public class UsersController {

	@Autowired
	private UsersService usersService;

	@RequestMapping(value = "/customers", method = RequestMethod.GET)
	public Object getRegisteredCustomers(@RequestParam(value = "pageno", required = true) int pageNo) {

		List<String> customerRoles = new ArrayList<>();
		customerRoles.add(RoleType.ROLE_CUSTOMER.toString());

		return usersService.doGetUsers(pageNo, customerRoles);
	}

	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	public Object getLogedInUsersRoles() {

		return usersService.doGetLoggenInUsersRoles();
	}

	@RequestMapping(value = "/admin-users", method = RequestMethod.GET)
	public Object getAdminUserCustomers(@RequestParam(value = "pageNo", required = true) int pageNo) {

		List<String> adminRoles = new ArrayList<>();
		adminRoles.add(RoleType.ROLE_ADMIN.toString());
		adminRoles.add(RoleType.ROLE_SALES.toString());

		return usersService.doGetUsers(pageNo, adminRoles);
	}

	@RequestMapping(value = "/admin-users", method = RequestMethod.POST)
	public Object addAdminUsers(@RequestBody AdminUserDetails request) {

		return usersService.doAddAdminUsers(request);
	}

	@RequestMapping(value = "/admin-users", method = RequestMethod.PUT)
	public Object updateAdminUsers(@RequestBody AdminUserDetails request) {

		return usersService.doUpdateAdminUsers(request);
	}

	@RequestMapping(value = "/customers/{customer-id}", method = RequestMethod.GET)
	public Object getRegisteredCustomersDetails(@PathVariable(value = "customer-id") String userDtlsId) {

		return usersService.doGetUserDetails(userDtlsId);
	}

	// TODO: Test API
	@PostMapping("validate-password")
	public Object validatePassword(@RequestBody UserLoginDtl request) throws ServicesException {

		return usersService.doValidatePassword(request);
	}

	@PostMapping("change-password")
	public Object forceChangePassword(@RequestBody UserLoginDtl request) throws ServicesException {

		return usersService.doForceChangePassword(request);
	}

	@PostMapping("user-change-password")
	public Object changePasswordOfUserFromAdmin(@RequestBody UserLoginDtl request) throws ServicesException {

		return usersService.doChangePasswordOfUserFromAdmin(request);
	}

	@GetMapping(value = "/{user-dtls-id}/tracking-history")
	public Object getUserHistoryWithTrackingData(@PathVariable(value = "user-dtls-id") String userDtlsId,
			@RequestParam(value = "track-date", required = true) String trackDate) throws ParseException {

		System.out.println("Request User Details - " + userDtlsId + " And Tracking Data for Date " + trackDate);
		return usersService.doGetUserHistoryWithTrackData(userDtlsId, trackDate);
	}

	@GetMapping("/admin-users/visits-sync")
	public Object getLoggedInUsersVisitHistory() {

		return usersService.doGetLoggedInUsersVisitHistory();
	}
	
	
	@PostMapping("/admin-users/punch-visit-entry")
	public Object punchUsersVisitEntry(@RequestBody SiteVisitHistory request) {

		return usersService.doPunchUsersVisitEntry(request);
	}

	@PutMapping("/admin-users/punch-visit-exit")
	public Object punchUsersVisitExit(@RequestBody SiteVisitHistory request) throws ServicesException {

		return usersService.doPunchUsersVisitExit(request);
	}
	
}
