package com.sapbasemodule.service;

import java.util.Optional;

import com.sapbasemodule.model.BaseWrapper;

public interface OrdersService {

	BaseWrapper doGetOrders(Optional<Integer> pageNo, Optional<Integer> limit);

	BaseWrapper doGetOrdersDetails(int orderDlsId);

//	BaseWrapper doGetOrderItemsDetails(int orderDlsId, String itemCode);

//	BaseWrapper doGetOrderItemsDetailsAfterDate(int orderDlsId, String itemCode, String date);

}
