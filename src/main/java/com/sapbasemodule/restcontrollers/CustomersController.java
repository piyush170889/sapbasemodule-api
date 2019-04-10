package com.sapbasemodule.restcontrollers;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sapbasemodule.model.BaseWrapper;
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

	/*
	@PersistenceContext
	private EntityManager em;

	@GetMapping("/ext/text")
	public Object testNative() {

		Object emReturn = em.createNativeQuery("select CardCode, CardName from OCRD where CardCode='WCT001'")
				.getSingleResult();
		System.out.println("EmRteurn = " + emReturn.toString());

		return new BaseWrapper();
	}*/
}
