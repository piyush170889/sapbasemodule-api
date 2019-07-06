package com.sapbasemodule.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Optional;

import com.sapbasemodule.domain.InvoicesAcknowledgementDetails;
import com.sapbasemodule.model.BaseWrapper;

public interface CustomersService {

	BaseWrapper doGetCustomersList(Optional<Integer> pageNo);

	BaseWrapper doGetCustomersSearchList(String searchTerm);

	BaseWrapper doGetCustomerAgingReport(String custCode, String fromDate) throws ClassNotFoundException, SQLException, ParseException;

	BaseWrapper doGetCustomerInvoices(String custCode, int noOfDays, String dueDate) throws ParseException, ClassNotFoundException, SQLException;

	BaseWrapper doGetCustomerOrders(String custCode, Optional<Integer> pageNo, Optional<Integer> limit);

	BaseWrapper doGetCustomerLedgerReport(String custCode) throws ClassNotFoundException, SQLException, ParseException;

	BaseWrapper doGetCustomersPendingInvoices(String custCode, String fromDate, String toDate) throws ClassNotFoundException, SQLException, ParseException;

	BaseWrapper doGetCustomersLedgerReportNew(String custCode) throws ClassNotFoundException, SQLException;

	BaseWrapper doGetCustomersAllInvoices(String custCode, String tillDate) throws ClassNotFoundException, SQLException, ParseException;

	BaseWrapper doGetCustomerDataForSync() throws ClassNotFoundException, SQLException, ParseException;

	BaseWrapper doSaveInvoiceAcknowledgement(int invoiceNo, InvoicesAcknowledgementDetails invoiceAcknowledgementDetails);

}
