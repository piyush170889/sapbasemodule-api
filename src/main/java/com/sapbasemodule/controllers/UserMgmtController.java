package com.sapbasemodule.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sapbasemodule.constants.Constants;
import com.sapbasemodule.domain.UserDtl;
import com.sapbasemodule.domain.UserLoginDtl;
import com.sapbasemodule.exception.ServicesException;
import com.sapbasemodule.persitence.UserDtlRepository;
import com.sapbasemodule.persitence.UserLoginDtlRepository;
import com.sapbasemodule.persitence.UserRoleDtlRepository;
import com.sapbasemodule.service.UsersService;
import com.sapbasemodule.utils.RoleType;

@Controller
@RequestMapping("ext")
public class UserMgmtController {

	@Autowired
	private UserRoleDtlRepository userRoleDtlRepository;

	@Autowired
	private UserDtlRepository userDtlRepository;

	@GetMapping("/recordings")
	public ModelAndView getRecordingsView(@RequestParam(value = "seid", required = true) String seId,
			ModelAndView modelAndView) {

		try {
			UserDtl userDtl = userDtlRepository.findOne(seId);
			modelAndView.addObject("userDtl", userDtl);
			modelAndView.setViewName("invoices/recordings");
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject("errorMssg", "Unable to retreive SE Details");
			modelAndView.setViewName("invoices/invoice-acknowledgment");
		}

		return modelAndView;
	}

	@Autowired
	private UserLoginDtlRepository userLoginDtlRepository;

	@GetMapping("/sales-executive")
	public ModelAndView getSalesExecutiveListing(ModelAndView modelAndView) {

		try {
			List<String> roles = new ArrayList<String>();
			roles.add(RoleType.ROLE_SALES.toString());

			List<String> adminUserLoginIds = userRoleDtlRepository.selectUserLoginDtlsIdByRoles(roles);

			List<UserLoginDtl> salesEmpDetailsListing;

			if (adminUserLoginIds.size() > 0)
				salesEmpDetailsListing = userLoginDtlRepository.findByLoginDtlsIdList(adminUserLoginIds);
			else
				salesEmpDetailsListing = new ArrayList<UserLoginDtl>();

			modelAndView.addObject("salesEmpDetailsListing", salesEmpDetailsListing);
			modelAndView.setViewName("user-mgmt/SaleEmpListing");
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject("errorMssg", "Unable to retreive Recordings Details");
			modelAndView.setViewName("invoices/invoice-acknowledgment");
		}

		return modelAndView;
	}

	@Autowired
	private UsersService usersService;
	
	@PostMapping("sales-executive")
	public ModelAndView updateSalesExecutiveCrDriveUrl(HttpServletRequest servletRequest, ModelAndView modelAndView)
			throws ServicesException {

		try {
			String crDriveUrl = servletRequest.getParameter("crDriveUrl");
			String userDtlsId = servletRequest.getParameter("userDtlsId");

			if (null != crDriveUrl && !crDriveUrl.isEmpty() && null != userDtlsId && !userDtlsId.isEmpty()) {
				usersService.updateCrDriveUrl(crDriveUrl, userDtlsId);
				modelAndView = getSalesExecutiveListing(modelAndView);
				modelAndView.addObject(Constants.SUCCESS_MSSG_LABEL, "Successfully Updated Drive Url Details");
			} else
				throw new ServicesException("630");
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject("errorMssg", "Unable to Update Drive Url Details");
			modelAndView.setViewName("user-mgmt/SaleEmpListing");
		}

		return modelAndView;

	}

}
