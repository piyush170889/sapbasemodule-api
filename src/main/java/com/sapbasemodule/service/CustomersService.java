package com.sapbasemodule.service;

import java.util.Optional;

import com.sapbasemodule.model.BaseWrapper;

public interface CustomersService {

	BaseWrapper doGetCustomersList(Optional<Integer> pageNo);

	BaseWrapper doGetCustomersSearchList(String searchTerm);

}
