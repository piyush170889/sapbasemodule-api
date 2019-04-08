package com.sapbasemodule.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sapbasemodule.domain.NormalPacketDescDtls;
import com.sapbasemodule.service.LocationMgmtService;

@RestController
@RequestMapping("/ext/location")
public class LocationMgmtController {

	@Autowired
	private LocationMgmtService locationMgmtService;

	@PutMapping(value = "")
	public Object updateUserLocation(@RequestBody NormalPacketDescDtls request) throws Exception {

		System.out.println("Location Data " + request.toString());
		return locationMgmtService.doUpdateUserLocation(request);
	}

}
