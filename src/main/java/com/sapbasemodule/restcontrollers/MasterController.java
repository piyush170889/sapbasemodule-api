package com.sapbasemodule.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sapbasemodule.model.BaseWrapper;
import com.sapbasemodule.service.MasterService;

@RestController
public class MasterController {

	@Autowired
	MasterService masterservice;

	@RequestMapping(value = "/master", method = RequestMethod.GET)
	public BaseWrapper getMasterData() {
		return new BaseWrapper(masterservice.getMasterData());
	}
}
