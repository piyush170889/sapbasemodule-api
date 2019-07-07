package com.sapbasemodule.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sapbasemodule.domain.NormalPacketDescDtls;
import com.sapbasemodule.domain.SiteDtls;
import com.sapbasemodule.service.LocationMgmtService;

@RestController
@RequestMapping("/")
public class LocationMgmtController {

	@Autowired
	private LocationMgmtService locationMgmtService;

	@PutMapping(value = "ext/location")
	public Object updateUserLocation(@RequestBody NormalPacketDescDtls request) throws Exception {

		System.out.println("Location Data " + request.toString());
		return locationMgmtService.doUpdateUserLocation(request);
	}

	@PostMapping(value = "location")
	public Object addUpdateSiteLocation(@RequestBody SiteDtls request) throws Exception {

		System.out.println("Site Data " + request.toString());
		return locationMgmtService.doAddUpdateSiteLocation(request);
	}
	
	@GetMapping(value = "location")
	public Object getSiteLocations() throws Exception {

		return locationMgmtService.doGetSiteLocations();
	}

}
