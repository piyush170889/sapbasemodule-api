package com.sapbasemodule.service;

import java.text.ParseException;
import java.util.Optional;

import com.sapbasemodule.domain.AppOrders;
import com.sapbasemodule.domain.OrderDeliveryDetails;
import com.sapbasemodule.model.BaseWrapper;

public interface OrdersService {

	BaseWrapper doGetOrders(Optional<Integer> pageNo, Optional<Integer> limit);

	BaseWrapper doGetOrdersDetails(int orderDlsId);

	BaseWrapper doPlaceOrder(AppOrders request) throws ParseException;

	BaseWrapper doGetBookedOrders();

	BaseWrapper doGetOrdersDeliveryDetails(int orderDlsId);

	BaseWrapper doSaveOrdersDeliveryDetails(int orderDlsId, OrderDeliveryDetails request);

	// BaseWrapper doGetOrderItemsDetails(int orderDlsId, String itemCode);

	// BaseWrapper doGetOrderItemsDetailsAfterDate(int orderDlsId, String
	// itemCode, String date);

}
