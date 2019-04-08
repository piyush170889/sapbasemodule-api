
package com.sapbasemodule.restcontrollers;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sapbasemodule.domain.OtpDtl;
import com.sapbasemodule.exception.ServicesException;
import com.sapbasemodule.model.BaseWrapper;
import com.sapbasemodule.model.ChangePassword;
import com.sapbasemodule.service.CommonService;

@RestController
public class CommonController {
	
	@Autowired
	private CommonService commonService;

	private Logger logger = Logger.getLogger(CommonController.class);

	/**
	 * @Description : Get mobile number send otp on mobile number.
	 * @Modified By : Rahul Gaikwad
	 * @Modified Date : 09 July, 2018
	 * @return : Object
	 * @throws Exception 
	 * @throws ServicesException
	 * 
	 */
	@RequestMapping(value="send-otp", method=RequestMethod.POST)
	public Object doSendOtp(@Valid @RequestBody OtpDtl otpDetails) throws Exception{
		System.out.println("otpDetails = " + otpDetails.toString());
		return commonService.doSendOtp(otpDetails);
	}
	
	
	/**
	 * Description : FORGET PASSWORD.
	 * @modify By : Piyush Jadhav
	 * @modified Date : 2 Aug, 2018
	 * @return object()
	 * @throws ServicesException
	 */
	
	@RequestMapping(value="ext/forget-password", method=RequestMethod.GET)
	public Object recoverForgetPassword(@RequestParam(value="recovery-contact", required=true) String recoveryContact) throws ServicesException {
		
		logger.debug("recoveryContact DEBUG - " + recoveryContact);
		return commonService.recoverForgetPassword(recoveryContact);
		
	}
	
	/**
	 * Description : CHANGE PASSWORD.
	 * @modify By : Rahul Gaikwad
	 * @modified Date : 13 July, 2018
	 * @return object()
	 * @throws ServicesException
	 */
	
	@RequestMapping(value="/change-password",method=RequestMethod.POST)
	public Object changePassword(@Valid @RequestBody ChangePassword changePassword) throws ServicesException {
		return commonService.changePassword(changePassword);
		
	}
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping(value="ext/encode/{term}",method=RequestMethod.GET)
	public Object encodeTerm(@PathVariable(value="term") String term) throws ServicesException {
		
		return new BaseWrapper(passwordEncoder.encode(term));
		
	}

	@RequestMapping(value="ext/testResponse",method=RequestMethod.GET)
	public Object encodeTerm() throws ServicesException {
		
		throw new ServicesException("601");
		
	}

}
