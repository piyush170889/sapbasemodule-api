package com.sapbasemodule.restcontrollers;

import java.text.ParseException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sapbasemodule.domain.AppOrders;
import com.sapbasemodule.domain.OrderDeliveryDetails;
import com.sapbasemodule.exception.ServicesException;
import com.sapbasemodule.service.OrdersService;

@RestController
@RequestMapping(value = "orders")
public class OrdersController {

	@Autowired
	private OrdersService ordersService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Object getOrders(@RequestParam(value = "pageNo", required = false) Optional<Integer> pageNo,
			@RequestParam(value = "limit", required = false) Optional<Integer> limit) throws ServicesException {

		return ordersService.doGetOrders(pageNo, limit);
	}

	@RequestMapping(value = "{order-dtls-id}", method = RequestMethod.GET)
	public Object getOrdersDetails(@PathVariable(value = "order-dtls-id") int orderDlsId) throws ServicesException {

		return ordersService.doGetOrdersDetails(orderDlsId);
	}

	@GetMapping("{order-dtls-id}/delivery-details")
	public Object getOrdersDeliveryDetails(@PathVariable(value = "order-dtls-id") int orderDlsId)
			throws ServicesException {

		return ordersService.doGetOrdersDeliveryDetails(orderDlsId);
	}

	@PostMapping("{order-dtls-id}/delivery-details")
	public Object postOrdersDeliveryDetails(@PathVariable(value = "order-dtls-id") int orderDlsId,
			@RequestBody OrderDeliveryDetails request) throws ServicesException {

		return ordersService.doSaveOrdersDeliveryDetails(orderDlsId, request);
	}

	@PostMapping("")
	public Object placeOrder(@RequestBody AppOrders request) throws ParseException {

		return ordersService.doPlaceOrder(request);
	}

	@GetMapping("/booked-orders")
	public Object getBookedOrders() {

		return ordersService.doGetBookedOrders();
	}

	/*
	 * @RequestMapping(value = "/{order-dtls-id}/item-details/{item-code}",
	 * method = RequestMethod.GET) public Object
	 * getOrdersItemDetails(@PathVariable(value = "order-dtls-id") int
	 * orderDlsId,
	 * 
	 * @PathVariable(value = "item-code") String itemCode) throws
	 * ServicesException {
	 * 
	 * return ordersService.doGetOrderItemsDetails(orderDlsId, itemCode); }
	 * 
	 * @RequestMapping(value =
	 * "/{order-dtls-id}/item-details/{item-code}/by-date/{date}", method =
	 * RequestMethod.GET) public Object getOrdersItemDetails(@PathVariable(value
	 * = "order-dtls-id") int orderDlsId,
	 * 
	 * @PathVariable(value = "item-code") String itemCode, @PathVariable(value =
	 * "date") String date) throws ServicesException {
	 * 
	 * return ordersService.doGetOrderItemsDetailsAfterDate(orderDlsId,
	 * itemCode, date); }
	 */

}
