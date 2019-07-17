package com.sapbasemodule.model;

import java.util.List;

import com.sapbasemodule.domain.CustomerAddresses;
import com.sapbasemodule.domain.Customers;

public class CustomerDetailsWrapper {

	private Customers customerDetails;

	private List<CustomerAddresses> customerAddressesList;

	private List<InvoicesDetails> customerInvoicesList;

	private List<CutomerSummaryReportDetails> cutomerSummaryReportDetailsList;

	public CustomerDetailsWrapper() {
	}

	public CustomerDetailsWrapper(Customers customers, List<CustomerAddresses> customerAddressesList) {
		this.customerDetails = customers;
		this.customerAddressesList = customerAddressesList;
	}

	public CustomerDetailsWrapper(Customers customers, List<CustomerAddresses> customerAddressesList,
			List<InvoicesDetails> customerInvoicesList,
			List<CutomerSummaryReportDetails> cutomerSummaryReportDetailsList) {
		this.customerDetails = customers;
		this.customerAddressesList = customerAddressesList;
		this.customerInvoicesList = customerInvoicesList;
		this.cutomerSummaryReportDetailsList = cutomerSummaryReportDetailsList;
	}

	public List<CutomerSummaryReportDetails> getCutomerSummaryReportDetailsList() {
		return cutomerSummaryReportDetailsList;
	}

	public void setCutomerSummaryReportDetailsList(List<CutomerSummaryReportDetails> cutomerSummaryReportDetailsList) {
		this.cutomerSummaryReportDetailsList = cutomerSummaryReportDetailsList;
	}

	public List<InvoicesDetails> getCustomerInvoicesList() {
		return customerInvoicesList;
	}

	public void setCustomerInvoicesList(List<InvoicesDetails> customerInvoicesList) {
		this.customerInvoicesList = customerInvoicesList;
	}

	public Customers getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(Customers customerDetails) {
		this.customerDetails = customerDetails;
	}

	public List<CustomerAddresses> getCustomerAddressesList() {
		return customerAddressesList;
	}

	public void setCustomerAddressesList(List<CustomerAddresses> customerAddressesList) {
		this.customerAddressesList = customerAddressesList;
	}

}
