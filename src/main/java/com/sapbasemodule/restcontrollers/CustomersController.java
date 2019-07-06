package com.sapbasemodule.restcontrollers;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sapbasemodule.domain.InvoicesAcknowledgementDetails;
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
			@RequestParam(value = "no-of-days", required = true) int noOfDays)
					throws ClassNotFoundException, ParseException, SQLException {

		System.out.println("Cust Code = " + custCode + ", noOfDays = " + noOfDays + ", dueDate = " + dueDate);
		return customersService.doGetCustomerInvoices(custCode, noOfDays, dueDate);
	}

	@GetMapping(value = "/{cust-code}/orders")
	public Object getCustomersOrders(@PathVariable("cust-code") String custCode,
			@RequestParam(value = "pageNo", required = true) Optional<Integer> pageNo,
			@RequestParam(value = "limit", required = true) Optional<Integer> limit) {

		System.out.println("Cust Code = " + custCode);
		return customersService.doGetCustomerOrders(custCode, pageNo, limit);
	}

	@GetMapping(value = "/{cust-code}/ledger-report")
	public Object getCustomersOrders(@PathVariable("cust-code") String custCode)
			throws ClassNotFoundException, SQLException, ParseException {

		System.out.println("Cust Code = " + custCode);
		return customersService.doGetCustomerLedgerReport(custCode);
	}

	@GetMapping(value = "/{cust-code}/new-ledger-report")
	public Object getCustomersLedgerReportNew(@PathVariable("cust-code") String custCode)
			throws ClassNotFoundException, SQLException, ParseException {

		System.out.println("Cust Code = " + custCode);
		return customersService.doGetCustomersLedgerReportNew(custCode);
	}

	@GetMapping(value = "/{cust-code}/all-invoices")
	public Object getCustomersLedgerReportNew(@PathVariable("cust-code") String custCode,
			@RequestParam(value = "till-date") String tillDate)
					throws ClassNotFoundException, SQLException, ParseException {

		System.out.println("Cust Code = " + custCode);
		return customersService.doGetCustomersAllInvoices(custCode, tillDate);
	}

	@GetMapping(value = "/{cust-code}/pending-invoices")
	public Object getCustomersPendingInvoices(@PathVariable("cust-code") String custCode,
			@RequestParam(value = "from-date", required = true) String fromDate,
			@RequestParam(value = "to-date", required = true) String toDate)
					throws ClassNotFoundException, SQLException, ParseException {

		System.out.println("custCode: " + custCode + ", fromDate: " + fromDate + ", toDate: " + toDate);
		return customersService.doGetCustomersPendingInvoices(custCode, fromDate, toDate);
	}

	@GetMapping(value = "/sync")
	public Object getCustomersDataForSync() throws ClassNotFoundException, SQLException, ParseException {

		return customersService.doGetCustomerDataForSync();
	}

	@PostMapping(value = "/{invoice-no}/invoice-acknowledgement")
	public Object saveInvoiceAcknowledgement(@PathVariable("invoice-no") int invoiceNo,
			@RequestBody InvoicesAcknowledgementDetails invoiceAcknowledgementDetails) throws SQLException {

		return customersService.doSaveInvoiceAcknowledgement(invoiceNo, invoiceAcknowledgementDetails);
	}
}
