package com.sapbasemodule.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sapbasemodule.domain.ConfigurationDtls;
import com.sapbasemodule.service.ConfigurationDtlsService;

@RestController
@RequestMapping(value="/config")
public class ConfigurationController {

	
	@Autowired private ConfigurationDtlsService configurationService;
	
	
	@RequestMapping(value="", method=RequestMethod.PUT)
	public Object updateConfigValue(@RequestBody ConfigurationDtls request) {
		
		return configurationService.updateConfigValue(request);
	}
}
