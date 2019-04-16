package com.sapbasemodule.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sapbasemodule.constants.Constants;
import com.sapbasemodule.domain.CustomerAddresses;
import com.sapbasemodule.domain.Customers;
import com.sapbasemodule.domain.OINV;
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
import com.sapbasemodule.persitence.OINVRepository;
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

	@Autowired
	private OINVRepository oinvRepository;

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
		AgingDetails agingDetails = getAgingDetails(custCode, fromDate);

		// AgingDetails agingDetails = new AgingDetails(custCode, "Piyush",
		// "60000", "20000", "30000", "40000", "50000",
		// "60000");

		return new BaseWrapper(agingDetails);
	}

	private AgingDetails getAgingDetails(String custCode, String fromDate)
			throws ClassNotFoundException, SQLException, ParseException {

		Connection conn = commonUtility.getDbConnection();

		String fromDateFormatted = new SimpleDateFormat("yyyyMMdd")
				.format(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate));

		String sqlQuery = "select T1.ShortName,(select a1.CardName from OCRD a1 where a1.CardCode=T1.ShortName)'CardName' ,"
				+ "(SUM(T1.BalDueDeb)-SUM(T1.BalDueCred))'BalanceDue', "
				+ "(select case when (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) is null then 0 else (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) end from JDT1 a1 where a1.ShortName=T1.ShortName and DATEDIFF(DAY,a1.DueDate, '"
				+ fromDateFormatted + "' )>=0 and DATEDIFF(DAY,a1.DueDate, '" + fromDateFormatted + "' )<=30)'FirstQ',"
				+ "(select case when (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) is null then 0 else (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) end from JDT1 a1 where a1.ShortName=T1.ShortName and DATEDIFF(DAY,a1.DueDate, '"
				+ fromDateFormatted + "' )>30 and DATEDIFF(DAY,a1.DueDate, '" + fromDateFormatted + "' )<=60)'SecondQ',"
				+ "(select case when (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) is null then 0 else (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) end from JDT1 a1 where a1.ShortName=T1.ShortName and DATEDIFF(DAY,a1.DueDate, '"
				+ fromDateFormatted + "' )>60 and DATEDIFF(DAY,a1.DueDate, '" + fromDateFormatted + "' )<=90)'ThirdQ',"
				+ "(select case when (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) is null then 0 else (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) end from JDT1 a1 where a1.ShortName=T1.ShortName and DATEDIFF(DAY,a1.DueDate, '"
				+ fromDateFormatted + "' )>90 and DATEDIFF(DAY,a1.DueDate, '" + fromDateFormatted
				+ "' )<=120)'FourthQ',"
				+ "(select case when (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) is null then 0 else (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) end from JDT1 a1 where a1.ShortName=T1.ShortName and DATEDIFF(DAY,a1.DueDate, '"
				+ fromDateFormatted + "' )>120)'OtherQ' "
				+ "from OJDT T0 inner join JDT1 T1 on T0.TransId=T1.TransId where " + "T0.DueDate<= '"
				+ fromDateFormatted + "' and T1.ShortName= '" + custCode + "' group by T1.ShortName "
				+ "having T1.ShortName in (select a1.CardCode from OCRD a1 where a1.CardType='C') and (SUM(T1.BalDueDeb)-SUM(T1.BalDueCred))>0 "
				+ "order by T1.ShortName";

		System.out.println("Final SQL = " + sqlQuery);

		PreparedStatement ps = conn.prepareStatement(sqlQuery);
		ResultSet rs = ps.executeQuery();

		AgingDetails agingDetails = null;
		if (rs.next())
			agingDetails = new AgingDetails(custCode, rs.getString("CardName"), rs.getString("BalanceDue"),
					rs.getString("FirstQ"), rs.getString("SecondQ"), rs.getString("ThirdQ"), rs.getString("FourthQ"),
					rs.getString("OtherQ"));
		else
			agingDetails = new AgingDetails(custCode, "", "0", "0", "0", "0", "0", "0");

		return agingDetails;
	}

	// TODO: Write Proper API For This
	@Override
	public BaseWrapper doGetCustomerInvoices(String custCode, int noOfDays, String dueDate) {

		/*
		 * List<InvoicesDetails> invoiceDetailsList = new
		 * ArrayList<InvoicesDetails>();
		 * 
		 * List<InvoiceItems> invoiceItemsList = new ArrayList<>(); InvoiceItems
		 * invoiceItems = new InvoiceItems(1, "ITM001", "Birla Super", 100F,
		 * 257.81F, 25781.25F, "25232910", "14", "14", "");
		 * invoiceItemsList.add(invoiceItems);
		 * 
		 * for (int i = 0; i < 10; i++) {
		 * 
		 * InvoicesDetails invoiceDetails = new InvoicesDetails((i + 1),
		 * "JB001", "1 Apr", "14 Apr", 1, 0, 31000F, custCode, "Piyush",
		 * "Sales GST", invoiceItemsList, 25781.25F, 14F, 14F, -0.01F, 33000F,
		 * "Dispatch Report As On 28.2.19", "30");
		 * invoiceDetailsList.add(invoiceDetails); }
		 */

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		df.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));

		Date toDate = df.parse(dueDate);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, noOfDays);
		Date fromDate = df.parse(df.format(cal.getTime()));

		List<String> docStatusList = new ArrayList<String>();
		docStatusList.add("O");
		docStatusList.add("C");

		List<OINV> invoicesList = oinvRepository.selectByCustCodeAndDates(custCode, toDate, fromDate, docStatusList);
		if (invoicesList.isEmpty())
			invoicesList = new ArrayList<OINV>();

		// Get DocEntry for all invoices
		List<Integer> invoiceDocEntriesList = new ArrayList<Integer>();
		for (OINV oinv : invoicesList)
			invoiceDocEntriesList.add(oinv.getDocEntry());

		List<InvoiceItems> invoiceItemsList;
		Map<Integer, List<InvoiceItems>> invoiceItemsMap;
		List<InvoicesDetails> invoiceDetailsList = new ArrayList<InvoicesDetails>()
		if (invoiceDocEntriesList.size() > 0) {
			invoiceItemsList = getInvoiceItemsListForDocEntries(invoiceDocEntriesList);
			
			// Separate All Invoice Items As Per Invoice No (DocEntry)
			invoiceItemsMap = new HashMap<Integer, List<InvoiceItems>>();
			for (InvoiceItems invoiceItems : invoiceItemsList) {
				int invoiceDocEntry = invoiceItems.getDocEntry();
				
				List<InvoiceItems> invoiceItemMapList;
				if (invoiceItemsMap.containsKey(invoiceDocEntry))
					invoiceItemMapList = invoiceItemsMap.get(invoiceDocEntry);
				else
					invoiceItemMapList = new ArrayList<InvoiceItems>();
				
				invoiceItemMapList.add(invoiceItems);
				
				invoiceItemsMap.put(invoiceDocEntry, invoiceItemMapList);
			}
			
			for (OINV oinv : invoicesList) {
				InvoicesDetails invoicesDetails = new InvoicesDetails(oinv.getDocEntry(), Integer.toString(oinv.getDocNum()),
						oinv.getDocDate(), oinv.getDocDueDate(), paymentDueDays, isPaid, amountDue, custCode, "", 
						type, invoiceItemsList, netAmount, cgst, sgst, roundOff, oinv.getDocTotal(), narration, dueDateInDays, igst, igsTax, cgstTax, sgstTax); 
			}
			
		} else
			invoiceItemsList = new ArrayList<InvoiceItems>();

		
		return new BaseWrapper(invoiceDetailsList);
	}

	private List<InvoiceItems> getInvoiceItemsListForDocEntries(List<Integer> invoiceDocEntriesList)
			throws ClassNotFoundException, SQLException {

		Connection conn = commonUtility.getDbConnection();

		String invoiceIdsInString = "";
		int size = invoiceDocEntriesList.size();
		for (int i = 0; i < size; i++) {
			invoiceIdsInString += invoiceDocEntriesList.get(i);
			if ((i + 1) < size)
				invoiceIdsInString += ",";
		}
		
		System.out.println("Invoice Id String = " + invoiceIdsInString);

		String sqlQuery = "SELECT T0.DocEntry,T0.DocNum,T0.DocDate,T0.DocDueDate As'Due Date',T0.cardcode,T0.cardname, "
				+ "T0.Address as 'Pay_To', T0.Address2 as 'Ship_To',T1.ItemCode,T1.Dscription,T0.RoundDif, T1.Quantity, T1.Price,T1.LineTotal,T0.DocTotal, "
				+ "T0.ShipToCode, (SELECT Top 1 T11.[PymntGroup] FROM OCTG T11  Where T11.[GroupNum] = T0.[GroupNum])As'Payment Terms', "
				+ "(SELECT  TOP 1  T14.[City] FROM  CRD1 T14  where T14.CardCode=T0.cardcode and T14.[City]<>'' and T3.ShipToDef=T14.Address "
				+ "and T14.AdresType='S' )As'Party City' , (select Distinct  T14.[GSTRegnNo] from CRD1 T14  where T14.CardCode=T0.cardcode "
				+ "and T14.[GSTRegnNo]<>'' and T3.ShipToDef=T14.Address and T14.AdresType='S' ) as 'Party GSTIN No',  (Select Distinct Replace(T7.ChapterID,'.','')  "
				+ "from OCHP T7 INNER JOIN OITM T5 On T5.ChapterID=T7.AbsEntry And T5.ItemCode=T1.ItemCode )as'Chap_Id', (select distinct T16.eCode from OCST T16 "
				+ "inner join CRD1 T17 on T16.Code=T17.State where T17.CardCode=T0.CardCode and T17.AdresType='S' and T3.ShipToDef=T17.Address) as 'StateCode', "
				+ "(select distinct T16.Name from OCST T16 inner join CRD1 T17 on T16.Code=T17.State where T17.CardCode=T0.CardCode and T17.AdresType='S' and T3.ShipToDef=T17.Address) as 'StateName', "
				+ "(Select  Top 1 T22.TaxRate  from INV4 T22 Where T22.staType='-100' And T22.DocEntry=T1.DocEntry And T22.LineNum=T1.LineNum )As'CGST Rate', "
				+ "(Select  Top 1 T22.TaxRate  from INV4 T22 Where T22.staType='-110' And T22.DocEntry=T1.DocEntry And T22.LineNum=T1.LineNum )As'SGST Rate', "
				+ "(Select  Top 1 T22.TaxRate  from INV4 T22 Where T22.staType='-120' And T22.DocEntry=T1.DocEntry And T22.LineNum=T1.LineNum )As'IGST Rate', "
				+ "isnull((Select  Sum(T22.TaxSum)  from INV4 T22 Where T22.staType='-100' And T22.DocEntry=T1.DocEntry And T22.LineNum=T1.LineNum ),0)As'CGST TAX', "
				+ "isnull((Select  Sum(T22.TaxSum)  from INV4 T22 Where T22.staType='-110' And T22.DocEntry=T1.DocEntry And T22.LineNum=T1.LineNum ),0)As'SGST TAX', "
				+ "isnull((Select  Sum(T22.TaxSum)  from INV4 T22 Where T22.staType='-120' And T22.DocEntry=T1.DocEntry And T22.LineNum=T1.LineNum ),0)As'IGST TAX', "
				+ "isnull((SELECT distinct Top 1  T1.[ExpnsName] FROM INV3 T10  INNER JOIN OEXD T1 ON T10.[ExpnsCode] = T1.[ExpnsCode] WHERE T10.[DocEntry] =T0.DocEntry),'')'Frieght Name', "
				+ "isnull((SELECT distinct Top 1 T10.[LineTotal] FROM INV3 T10  INNER JOIN OEXD T1 ON T10.[ExpnsCode] = T1.[ExpnsCode] WHERE T10.[DocEntry] =T0.DocEntry),0)'Frieght Amt', "
				+ "(SELECT distinct  T1.SacCode  FROM INV3 T10  INNER JOIN OEXD T1 ON T10.[ExpnsCode] = T1.[ExpnsCode] WHERE T10.[DocEntry] =T0.DocEntry)'Sac_Code' "
				+ "FROM OINV T0 INNER JOIN INV1  T1 on T0.DocEntry=T1.DocEntry INNER JOIN OCRD T3 ON T3.CardCode=T0.CardCode "
				+ "Where T0.DocEntry IN (' " + invoiceIdsInString + "')";

		System.out.println("Final SQL = " + sqlQuery);

		PreparedStatement ps = conn.prepareStatement(sqlQuery);
		ResultSet rs = ps.executeQuery();

		List<InvoiceItems> invoicesItemsDetailsList = new ArrayList<InvoiceItems>();
		while (rs.next()) {
			InvoiceItems invoiceItems = new InvoiceItems(rs.getInt("DocEntry"), rs.getInt("DocNum"),
					rs.getString("ItemCode"), rs.getString("Dscription"), rs.getFloat("Quantity"), rs.getFloat("Price"),
					rs.getFloat("LineTotal"), rs.getString("Chap_Id"), rs.getString("CGST Rate"),
					rs.getString("SGST Rate"), rs.getString("IGST Rate"), rs.getString("Pay_To"),
					rs.getString("Ship_To"), rs.getString("RoundDif"), rs.getString("Payment Terms"),
					rs.getString("Party City"), rs.getString("Party GSTIN No"), rs.getString("StateCode"),
					rs.getString("StateName"), rs.getString("CGST TAX"), rs.getString("SGST TAX"),
					rs.getString("IGST TAX"), rs.getString("Frieght Name"), rs.getString("Frieght Amt"),
					rs.getString("Sac_Code"));

			invoicesItemsDetailsList.add(invoiceItems);
		}

		return invoicesItemsDetailsList;
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
