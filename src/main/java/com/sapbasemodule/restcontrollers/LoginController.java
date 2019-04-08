package com.sapbasemodule.restcontrollers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sapbasemodule.domain.UserLoginDtl;
import com.sapbasemodule.model.BaseWrapper;
import com.sapbasemodule.service.LoginService;

@RestController
public class LoginController {
	
	@Autowired
	private LoginService loginService; 
	
	@Autowired PasswordEncoder passwordEncoder;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public Object doLoginUserWitValidCreadentials(@Valid @RequestBody UserLoginDtl userLoginDtl) throws Exception{
		return loginService.doLoginUserWitValidCreadentials(userLoginDtl);
	}
	
	@RequestMapping(value="/getPasscode", method=RequestMethod.GET)
	public Object doLoginUserWitValidCreadentials() {
		return new BaseWrapper(passwordEncoder.encode("demo"));
	}
	

}
