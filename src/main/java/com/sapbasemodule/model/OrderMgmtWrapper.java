package com.sapbasemodule.model;

import java.util.List;

import com.sapbasemodule.domain.Orders;

public class OrderMgmtWrapper {

	private List<Orders> orderDetailsList;

	private int bookedOrderCount;

	public OrderMgmtWrapper() {
	}

	public OrderMgmtWrapper(List<Orders> orderDetailsList) {
		this.orderDetailsList = orderDetailsList;
	}

	public OrderMgmtWrapper(List<Orders> orderDetailsList, int bookedOrderCount) {
		this.orderDetailsList = orderDetailsList;
		this.bookedOrderCount = bookedOrderCount;
	}

	public List<Orders> getOrderDetailsList() {
		return orderDetailsList;
	}

	public void setOrderDetailsList(List<Orders> orderDetailsList) {
		this.orderDetailsList = orderDetailsList;
	}

	public int getBookedOrderCount() {
		return bookedOrderCount;
	}

	public void setBookedOrderCount(int bookedOrderCount) {
		this.bookedOrderCount = bookedOrderCount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderMgmtWrapper [orderDetailsList=");
		builder.append(orderDetailsList);
		builder.append(", bookedOrderCount=");
		builder.append(bookedOrderCount);
		builder.append("]");
		return builder.toString();
	}

}
