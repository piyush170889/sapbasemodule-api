package com.sapbasemodule.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import com.sapbasemodule.domain.CustomerPin;
import com.sapbasemodule.domain.InvoicesAcknowledgementDetails;
import com.sapbasemodule.exception.ServicesException;
import com.sapbasemodule.model.BaseWrapper;
import com.sapbasemodule.model.InvoicesDetails;

public interface CustomersService {

	BaseWrapper doGetCustomersList(Optional<Integer> pageNo);

	BaseWrapper doGetCustomersSearchList(String searchTerm);

	BaseWrapper doGetCustomerAgingReport(String custCode, String fromDate)
			throws ClassNotFoundException, SQLException, ParseException;

	BaseWrapper doGetCustomerInvoices(String custCode, int noOfDays, String dueDate)
			throws ParseException, ClassNotFoundException, SQLException;

	BaseWrapper doGetCustomerOrders(String custCode, Optional<Integer> pageNo, Optional<Integer> limit);

	BaseWrapper doGetCustomerLedgerReport(String custCode) throws ClassNotFoundException, SQLException, ParseException;

	BaseWrapper doGetCustomersPendingInvoices(String custCode, String fromDate, String toDate)
			throws ClassNotFoundException, SQLException, ParseException;

	BaseWrapper doGetCustomersLedgerReportNew(String custCode) throws ClassNotFoundException, SQLException;

	BaseWrapper doGetCustomersAllInvoices(String custCode, String tillDate)
			throws ClassNotFoundException, SQLException, ParseException;

	BaseWrapper doGetCustomerDataForSync() throws ClassNotFoundException, SQLException, ParseException;

	BaseWrapper doSaveInvoiceAcknowledgement(int invoiceNo,
			InvoicesAcknowledgementDetails invoiceAcknowledgementDetails);

	BaseWrapper doGenerateCustomerPin(String custCode, String contactNo) throws NumberFormatException, Exception;

	BaseWrapper doVerifyCustomerPin(CustomerPin customerPin) throws ServicesException;

	BaseWrapper doSaveOfflineInvoiceAcknowledgement(
			List<InvoicesAcknowledgementDetails> invoiceAcknowledgementDetailsList);

	List<InvoicesDetails> getInvoiceAcknowledgments() throws ParseException, ClassNotFoundException, SQLException;

	InvoicesDetails getInvoiceDetailsByInvoiceId(String invoiceId)
			throws ServicesException, ClassNotFoundException, SQLException, ParseException;
}
