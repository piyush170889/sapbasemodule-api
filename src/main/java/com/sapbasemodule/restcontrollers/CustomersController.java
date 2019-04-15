package com.sapbasemodule.restcontrollers;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sapbasemodule.service.CustomersService;

@RestController
@RequestMapping(value = "customers")
public class CustomersController {

	@Autowired
	private CustomersService customersService;

	@GetMapping(value = "")
	public Object getCustomersList(@RequestParam(value = "pageNo", required = false) Optional<Integer> pageNo) {

		return customersService.doGetCustomersList(pageNo);
	}

	@GetMapping(value = "/search-term")
	public Object getCustomersSearchList(@RequestParam(value = "search-term", required = true) String searchTerm) {

		return customersService.doGetCustomersSearchList(searchTerm);
	}

	@GetMapping(value = "/{cust-code}/aging-report")
	public Object getCustomersAgingReport(@PathVariable("cust-code") String custCode,
			@RequestParam(value = "from-date", required = true) String fromDate)
					throws ClassNotFoundException, SQLException, ParseException {

		System.out.println("Cust Code = " + custCode + ", fromDate = " + fromDate);
		return customersService.doGetCustomerAgingReport(custCode, fromDate);
	}

	@GetMapping(value = "/{cust-code}/invoices")
	public Object getCustomersInvoices(@PathVariable("cust-code") String custCode,
			@RequestParam(value = "due-date", required = true) String dueDate, 
			@RequestParam(value = "no-of-days", required = true) int noOfDays) {

		System.out.println("Cust Code = " + custCode + ", noOfDays = " + noOfDays);
		return customersService.doGetCustomerInvoices(custCode, noOfDays);
	}
	
	@GetMapping(value = "/{cust-code}/orders")
	public Object getCustomersOrders(@PathVariable("cust-code") String custCode,
			@RequestParam(value = "pageNo", required = true) Optional<Integer> pageNo, 
			@RequestParam(value = "limit", required = true) Optional<Integer> limit) {

		System.out.println("Cust Code = " + custCode);
		return customersService.doGetCustomerOrders(custCode, pageNo, limit);
	}
}
