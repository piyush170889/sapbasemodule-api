package com.sapbasemodule.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Optional;

import com.sapbasemodule.model.BaseWrapper;

public interface CustomersService {

	BaseWrapper doGetCustomersList(Optional<Integer> pageNo);

	BaseWrapper doGetCustomersSearchList(String searchTerm);

	BaseWrapper doGetCustomerAgingReport(String custCode, String fromDate) throws ClassNotFoundException, SQLException, ParseException;

}
