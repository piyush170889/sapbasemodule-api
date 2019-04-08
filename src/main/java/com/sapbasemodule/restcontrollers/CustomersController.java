package com.sapbasemodule.restcontrollers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sapbasemodule.service.CustomersService;

@RestController
@RequestMapping(value="customers")
public class CustomersController {

	
	@Autowired
	private CustomersService customersService;
	
	
	@GetMapping(value="")
	public Object getCustomersList(@RequestParam(value="pageNo", required=false) Optional<Integer> pageNo) {
		
		return customersService.doGetCustomersList(pageNo);
	}
	
	@GetMapping(value="/search-term")
	public Object getCustomersSearchList(@RequestParam(value="search-term", required=true) String searchTerm) {
		
		return customersService.doGetCustomersSearchList(searchTerm);
	}
	
}
