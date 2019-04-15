package com.sapbasemodule.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sapbasemodule.domain.OrderItems;
import com.sapbasemodule.domain.Orders;
import com.sapbasemodule.model.BaseWrapper;
import com.sapbasemodule.model.OrderDetailsWrapper;
import com.sapbasemodule.persitence.OrderItemsRepository;
import com.sapbasemodule.persitence.OrdersRepository;
import com.sapbasemodule.utils.CommonUtility;

@Service
@Transactional(rollbackFor = Throwable.class)
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	private OrdersRepository ordersRepository;

	@Autowired
	private OrderItemsRepository orderItemsRepository;

	@Autowired
	private CommonUtility commonUtility;

/*	@Autowired
	private DeliveryFooterRepository deliveryFooterRepository;

	@Autowired
	private DeliveryHeaderRepository deliveryHeaderRepository;
*/
	@Override
	public BaseWrapper doGetOrders(Optional<Integer> pageNo, Optional<Integer> limit) {

		List<Orders> ordersList = null;
		PageRequest pageRequest = null;
		Sort sort = new Sort(Direction.DESC, "docEntry");

		if (pageNo.isPresent() && limit.isPresent()) {
			pageRequest = new PageRequest((pageNo.get() - 1), limit.get(), sort);
		} else if (pageNo.isPresent() && !limit.isPresent()) {
			pageRequest = commonUtility.getPageRequest(pageNo.get());
		}

		if (pageRequest != null)
			ordersList = ordersRepository.findAll(pageRequest).getContent();
		else {
			ordersList = ordersRepository.findAll();
		}

		List<Orders> finalOrdersList = new ArrayList<Orders>();
		if (ordersList != null && !ordersList.isEmpty()) {

			// Get All Order Ids In A List to get the OrderItems Associated with
			// Them
			List<Integer> orderIdsList = new ArrayList<Integer>();
			for (Orders orders : ordersList)
				orderIdsList.add(orders.getDocNum());

			// Get All Order Items Associated with Orders
			List<OrderItems> orderItemsList = orderItemsRepository.findByDocEntryIn(orderIdsList);

			// Map the quantities for each order
			Map<Integer, Float> orderQtyMap = new HashMap<Integer, Float>();
			for (OrderItems orderItems : orderItemsList) {
				float oldQty = 0F;

				int orderId = orderItems.getDocEntry();
				if (orderQtyMap.containsKey(orderId))
					oldQty = orderQtyMap.get(orderId);

				oldQty = oldQty + orderItems.getQuantity();

				orderQtyMap.put(orderId, oldQty);

			}

			// Update the Sale Order Quantity for each order from the
			// orderQtyMap
			for (Orders orders : ordersList) {
				float orderQty = 0F;
				orderQty = orderQtyMap.get(orders.getDocNum());
				orders.setQuantity(orderQty);

				finalOrdersList.add(orders);
			}
		}

		ordersList = null; // Make the Object Available for Garbage Collection

		return new BaseWrapper(finalOrdersList);
	}

	@Override
	public BaseWrapper doGetOrdersDetails(int orderDtlsId) {

		Orders ordersDetails = ordersRepository.findByDocEntry(orderDtlsId);

		List<OrderItems> orderItemsList = orderItemsRepository.findByDocEntry(orderDtlsId);

		for (int i = 0; i < orderItemsList.size(); i++)
			System.out.println("Doc Entry = " + orderItemsList.get(i).getDocEntry() + ", Item Code = "
					+ orderItemsList.get(i).getItemCode() + ", Qty = " + orderItemsList.get(i).getQuantity());

		// Set Total Order Qty, Total Delivered Qty, Total Opening Qty And Total
		// Balance In Orders
		System.out.println("ordersDetails.getOpenQuantity() = " + ordersDetails.getOpenQuantity() 
		+ ", orderItems.getOpenQty() = " + orderItemsList.get(0).getOpenQty());
		for (OrderItems orderItems : orderItemsList) {
			// Sales Order Qty
			ordersDetails.setQuantity(ordersDetails.getQuantity() + orderItems.getQuantity());
			// Opening Qty
			ordersDetails.setOpenQuantity(ordersDetails.getOpenQuantity() + orderItems.getOpenQty()); 
			// Delivered Qty
			ordersDetails.setDeliveredQuantity(ordersDetails.getDeliveredQuantity() + orderItems.getDelivrdQty());
			// Balance Qty
			ordersDetails.setBalanceQuantity(ordersDetails.getBalanceQuantity() + orderItems.getOpenQty()); 
		}

		OrderDetailsWrapper response = new OrderDetailsWrapper();
		BeanUtils.copyProperties(ordersDetails, response);
		response.setOrderItemsList(orderItemsList);

		return new BaseWrapper(response);
	}

/*	@Override
	public BaseWrapper doGetOrderItemsDetails(int orderDlsId, String itemCode) {

		List<DeliveryFooter> deliveryFooterDetailsList = deliveryFooterRepository.findByBaseEntryAndItemCode(orderDlsId,
				itemCode);

		// Create Response Object
		Map<String, List<DeliveryDetailsWrapper>> response = new HashMap<String, List<DeliveryDetailsWrapper>>();

		DateFormat dfDdMmYyyy = new SimpleDateFormat("dd-MM-yyyy");
		if (deliveryFooterDetailsList.size() > 0) {
			// Get Delivery Header Details
			List<Integer> deliveryHeaderLookUpIdsList = new ArrayList<Integer>();
			for (DeliveryFooter deliveryFooter : deliveryFooterDetailsList)
				deliveryHeaderLookUpIdsList.add(deliveryFooter.getDocEntry());

			List<DeliveryHeader> deliveryHeaderDetailsList = deliveryHeaderRepository
					.findByDocEntryIn(deliveryHeaderLookUpIdsList);

			// Create A Map With Delivery Header Id As Key And The Object as
			// value
			Map<Integer, DeliveryHeader> deliveryHeaderMap = new HashMap<Integer, DeliveryHeader>();
			for (DeliveryHeader deliveryHeader : deliveryHeaderDetailsList)
				deliveryHeaderMap.put(deliveryHeader.getDocEntry(), deliveryHeader);

			// Create A Final Hashmap with all delivery date as key and all
			// deliveries for date as value
			for (DeliveryFooter deliveryFooter : deliveryFooterDetailsList) {
				DeliveryHeader deliveryHeader = deliveryHeaderMap.get(deliveryFooter.getDocEntry());

				List<DeliveryDetailsWrapper> deliveryDetailsWrapperList;

				Date deliveryDate = deliveryHeader.getDocDate();
				String deliveryDateFormatted = dfDdMmYyyy.format(deliveryDate);

				if (response.containsKey(deliveryDateFormatted)) {
					deliveryDetailsWrapperList = response.get(deliveryDateFormatted);
					System.out.println("deliveryDateFormatted = " + deliveryDateFormatted + ", In if");
				} else {
					deliveryDetailsWrapperList = new ArrayList<DeliveryDetailsWrapper>();
					System.out.println("deliveryDateFormatted = " + deliveryDateFormatted + ", In Else");
				}

				DeliveryDetailsWrapper deliveryDetailsWrapper = new DeliveryDetailsWrapper(deliveryHeader.getDocNum(),
						deliveryFooter.getBaseEntry(), deliveryFooter.getItemCode(), deliveryFooter.getDscription(),
						deliveryFooter.getQuantity(), deliveryHeader.getDocDate(), deliveryHeader.getDocTime(),
						deliveryHeader.getuVehno(), deliveryHeader.getuDrname());

				deliveryDetailsWrapperList.add(deliveryDetailsWrapper);
				System.out.println("deliveryDetailsWrapperListSize = " + deliveryDetailsWrapperList.size());
				System.out.println("deliveryDetailsWrapperList = " + deliveryDetailsWrapperList.toString());

				response.put(deliveryDateFormatted, deliveryDetailsWrapperList);
			}
		}

		return new BaseWrapper(response);
	}

	@Override
	public BaseWrapper doGetOrderItemsDetailsAfterDate(int orderDlsId, String itemCode, String date) {

		List<DeliveryFooter> deliveryFooterDetailsList = deliveryFooterRepository
				.findDeliveryFooterDetailsByBaseEntryAndItemCodeAndDate(orderDlsId, itemCode, date);

		// Create Response Object
		Map<String, List<DeliveryDetailsWrapper>> response = new HashMap<String, List<DeliveryDetailsWrapper>>();

		DateFormat dfDdMmYyyy = new SimpleDateFormat("dd-MM-yyyy");
		if (deliveryFooterDetailsList.size() > 0) {
			// Get Delivery Header Details
			List<Integer> deliveryHeaderLookUpIdsList = new ArrayList<Integer>();
			for (DeliveryFooter deliveryFooter : deliveryFooterDetailsList)
				deliveryHeaderLookUpIdsList.add(deliveryFooter.getDocEntry());

			List<DeliveryHeader> deliveryHeaderDetailsList = deliveryHeaderRepository
					.findByDocEntryIn(deliveryHeaderLookUpIdsList);

			// Create A Map With Delivery Header Id As Key And The Object as
			// value
			Map<Integer, DeliveryHeader> deliveryHeaderMap = new HashMap<Integer, DeliveryHeader>();
			for (DeliveryHeader deliveryHeader : deliveryHeaderDetailsList)
				deliveryHeaderMap.put(deliveryHeader.getDocEntry(), deliveryHeader);

			// Create A Final Hashmap with all delivery date as key and all
			// deliveries for date as value
			for (DeliveryFooter deliveryFooter : deliveryFooterDetailsList) {
				DeliveryHeader deliveryHeader = deliveryHeaderMap.get(deliveryFooter.getDocEntry());

				List<DeliveryDetailsWrapper> deliveryDetailsWrapperList;

				Date deliveryDate = deliveryHeader.getDocDate();
				String deliveryDateFormatted = dfDdMmYyyy.format(deliveryDate);

				if (response.containsKey(deliveryDateFormatted))
					deliveryDetailsWrapperList = response.get(deliveryDateFormatted);
				else
					deliveryDetailsWrapperList = new ArrayList<DeliveryDetailsWrapper>();

				DeliveryDetailsWrapper deliveryDetailsWrapper = new DeliveryDetailsWrapper(deliveryHeader.getDocNum(),
						deliveryFooter.getBaseEntry(), deliveryFooter.getItemCode(), deliveryFooter.getDscription(),
						deliveryFooter.getQuantity(), deliveryHeader.getDocDate(), deliveryHeader.getDocTime(),
						deliveryHeader.getuVehno(), deliveryHeader.getuDrname());

				deliveryDetailsWrapperList.add(deliveryDetailsWrapper);

				response.put(deliveryDateFormatted, deliveryDetailsWrapperList);
			}
		}

		return new BaseWrapper(response);
	}*/
	

}
