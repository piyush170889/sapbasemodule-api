package com.sapbasemodule.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sapbasemodule.exception.ServicesException;
import com.sapbasemodule.service.CommonService;


@Controller
public class ResetEnteredPassword {
	
	@Autowired
	private CommonService commonService;
	
	@RequestMapping(value="ext/update-password/{forgetPasswordCode}", method= RequestMethod.GET)
	public ModelAndView getPasswordResetPage(@PathVariable("forgetPasswordCode") String forgetPasswordCode){
		ModelAndView model = new ModelAndView();
		model.addObject("forgetPasswordCode", forgetPasswordCode);
		return new ModelAndView("PasswordResetPage");
	}
	
	@RequestMapping(value="ext/reset-password", method= RequestMethod.POST)
	public ModelAndView getPassword(HttpServletRequest request) throws ServicesException{
		int isRowUpdated = commonService.updateUserLoginDtls(request);
		if(isRowUpdated==1){
			return new ModelAndView("PasswordChangedSuccess");
		}else{
			return new ModelAndView("PasswordChangedError");
		}
	}
}

