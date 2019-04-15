package com.sapbasemodule.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sapbasemodule.domain.CustomerAddresses;
import com.sapbasemodule.domain.Customers;
import com.sapbasemodule.domain.OrderItems;
import com.sapbasemodule.domain.Orders;
import com.sapbasemodule.model.AgingDetails;
import com.sapbasemodule.model.BaseWrapper;
import com.sapbasemodule.model.CustomerDetailsWrapper;
import com.sapbasemodule.model.InvoiceItems;
import com.sapbasemodule.model.InvoicesDetails;
import com.sapbasemodule.model.PaginationDetails;
import com.sapbasemodule.persitence.CustomersAddressesRepository;
import com.sapbasemodule.persitence.CustomersRepository;
import com.sapbasemodule.persitence.OrderItemsRepository;
import com.sapbasemodule.persitence.OrdersRepository;
import com.sapbasemodule.utils.CommonUtility;

@Service
@Transactional(rollbackFor = Throwable.class)
public class CustomersServiceImpl implements CustomersService {

	@Autowired
	private CustomersRepository customersRepository;

	@Autowired
	private CustomersAddressesRepository customersAddressesRepository;

	@Autowired
	private OrdersRepository ordersRepository;

	@Autowired
	private OrderItemsRepository orderItemsRepository;

	@Autowired
	private CommonUtility commonUtility;

	@Override
	public BaseWrapper doGetCustomersList(Optional<Integer> pageNo) {

		List<Customers> customersList;
		PaginationDetails paginationDetails = null;

		long totalCustCount = customersRepository.count();
		if (pageNo.isPresent()) {
			Sort sort = new Sort(Direction.DESC, "cardCode");
			PageRequest pageRequest = commonUtility.getPageRequest(pageNo.get(), sort);

			customersList = customersRepository.findAll(pageRequest).getContent();

			paginationDetails = commonUtility.getPaginationDetails(totalCustCount, pageNo.get());
		} else {
			customersList = customersRepository.findAll();
		}

		if (customersList.isEmpty())
			customersList = new ArrayList<Customers>();

		// Create Response And Send
		List<CustomerDetailsWrapper> response = getCustomerDetailsWarpperFromCustomersList(customersList);

		return new BaseWrapper(response, paginationDetails);
	}

	@Override
	public BaseWrapper doGetCustomersSearchList(String searchTerm) {

		List<Customers> customersList = customersRepository.findCustomerBySearchTerm(searchTerm.toLowerCase());

		if (customersList.isEmpty())
			customersList = new ArrayList<Customers>();

		// Create Response And Send
		List<CustomerDetailsWrapper> response = getCustomerDetailsWarpperFromCustomersList(customersList);

		return new BaseWrapper(response);
	}

	private List<CustomerDetailsWrapper> getCustomerDetailsWarpperFromCustomersList(List<Customers> customersList) {

		List<CustomerDetailsWrapper> response = new ArrayList<CustomerDetailsWrapper>();

		for (Customers customers : customersList) {
			String custCode = customers.getCardCode();

			List<CustomerAddresses> customerAddressesList = customersAddressesRepository.findByCardCode(custCode);

			CustomerDetailsWrapper customerDetailsWrapper = new CustomerDetailsWrapper(customers,
					customerAddressesList);

			response.add(customerDetailsWrapper);
		}

		return response;
	}

	@Override
	public BaseWrapper doGetCustomerAgingReport(String custCode, String fromDate)
			throws ClassNotFoundException, SQLException, ParseException {

		// TODO: Uncomment Below Section
		// Connection conn = getDbConnection();
		//
		// String fromDateFormatted = new
		// SimpleDateFormat("yyyyMMdd").format(new
		// SimpleDateFormat("yyyy-MM-dd").parse(fromDate));
		//
		// String sqlQuery = "select T1.ShortName,(select a1.CardName from OCRD
		// a1 where a1.CardCode=T1.ShortName)'CardName' ,"
		// + "(SUM(T1.BalDueDeb)-SUM(T1.BalDueCred))'BalanceDue', "
		// + "(select case when (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) is null
		// then 0 else (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) end from JDT1 a1
		// where a1.ShortName=T1.ShortName and DATEDIFF(DAY,a1.DueDate, '" +
		// fromDateFormatted + "' )>=0 and DATEDIFF(DAY,a1.DueDate, '" +
		// fromDateFormatted + "' )<=30)'FirstQ',"
		// + "(select case when (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) is null
		// then 0 else (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) end from JDT1 a1
		// where a1.ShortName=T1.ShortName and DATEDIFF(DAY,a1.DueDate, '" +
		// fromDateFormatted + "' )>30 and DATEDIFF(DAY,a1.DueDate, '" +
		// fromDateFormatted + "' )<=60)'SecondQ',"
		// + "(select case when (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) is null
		// then 0 else (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) end from JDT1 a1
		// where a1.ShortName=T1.ShortName and DATEDIFF(DAY,a1.DueDate, '" +
		// fromDateFormatted + "' )>60 and DATEDIFF(DAY,a1.DueDate, '" +
		// fromDateFormatted + "' )<=90)'ThirdQ',"
		// + "(select case when (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) is null
		// then 0 else (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) end from JDT1 a1
		// where a1.ShortName=T1.ShortName and DATEDIFF(DAY,a1.DueDate, '" +
		// fromDateFormatted + "' )>90 and DATEDIFF(DAY,a1.DueDate, '" +
		// fromDateFormatted + "' )<=120)'FourthQ',"
		// + "(select case when (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) is null
		// then 0 else (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) end from JDT1 a1
		// where a1.ShortName=T1.ShortName and DATEDIFF(DAY,a1.DueDate, '" +
		// fromDateFormatted + "' )>120)'OtherQ' "
		// + "from OJDT T0 inner join JDT1 T1 on T0.TransId=T1.TransId where "
		// + "T0.DueDate<= '" + fromDateFormatted + "' and T1.ShortName= '" +
		// custCode + "' group by T1.ShortName "
		// + "having T1.ShortName in (select a1.CardCode from OCRD a1 where
		// a1.CardType='C') and (SUM(T1.BalDueDeb)-SUM(T1.BalDueCred))>0 "
		// + "order by T1.ShortName";
		//
		// System.out.println("Final SQL = " + sqlQuery);
		//
		// PreparedStatement ps = conn.prepareStatement(sqlQuery);
		// ResultSet rs = ps.executeQuery();
		//
		// AgingDetails agingDetails = null;
		// if(rs.next())
		// agingDetails = new AgingDetails(custCode, rs.getString("CardName"),
		// rs.getString("BalanceDue"), rs.getString("FirstQ"),
		// rs.getString("SecondQ"),
		// rs.getString("ThirdQ"), rs.getString("FourthQ"),
		// rs.getString("OtherQ"));
		// else
		// agingDetails = new AgingDetails(custCode, "", "0", "0", "0",
		// "0","0","0");

		AgingDetails agingDetails = new AgingDetails(custCode, "Piyush", "60000", "20000", "30000", "40000", "50000",
				"60000");

		return new BaseWrapper(agingDetails);
	}

	private Connection getDbConnection() throws ClassNotFoundException, SQLException {

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Adit_Infra_Final",
				"sa", "replete@123");

		return conn;
	}

	// TODO: Write Proper API For This
	@Override
	public BaseWrapper doGetCustomerInvoices(String custCode, int noOfDays) {

		List<InvoicesDetails> invoiceDetailsList = new ArrayList<InvoicesDetails>();

		List<InvoiceItems> invoiceItemsList = new ArrayList<>();
		InvoiceItems invoiceItems = new InvoiceItems(1, "ITM001", "Birla Super", 100F, 257.81F, 25781.25F, "25232910",
				"14", "14", "");
		invoiceItemsList.add(invoiceItems);

		for (int i = 0; i < 10; i++) {

			InvoicesDetails invoiceDetails = new InvoicesDetails((i + 1), "JB001", "1 Apr", "14 Apr", 1, 0, 31000F,
					custCode, "Piyush", "Sales GST", invoiceItemsList, 25781.25F, 14F, 14F, -0.01F, 33000F,
					"Dispatch Report As On 28.2.19", "30");
			invoiceDetailsList.add(invoiceDetails);
		}

		return new BaseWrapper(invoiceDetailsList);
	}

	
	@Override
	public BaseWrapper doGetCustomerOrders(String custCode, Optional<Integer> pageNo, Optional<Integer> limit) {

		List<Orders> ordersList = null;
		PageRequest pageRequest = null;
		Sort sort = new Sort(Direction.DESC, "docEntry");

		if (pageNo.isPresent() && limit.isPresent()) {
			pageRequest = new PageRequest((pageNo.get() - 1), limit.get(), sort);
		} else if (pageNo.isPresent() && !limit.isPresent()) {
			pageRequest = commonUtility.getPageRequest(pageNo.get());
		}

		if (pageRequest != null)
			ordersList = ordersRepository.findByCardCode(custCode, pageRequest);
		else {
			ordersList = ordersRepository.findByCardCode(custCode);
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
}
