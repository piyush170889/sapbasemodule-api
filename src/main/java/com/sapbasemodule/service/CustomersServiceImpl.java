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
import com.sapbasemodule.model.AgingReportTo;
import com.sapbasemodule.model.BaseWrapper;
import com.sapbasemodule.model.CustomerDetailsWrapper;
import com.sapbasemodule.model.InvoiceItems;
import com.sapbasemodule.model.InvoicesDetails;
import com.sapbasemodule.model.OrderMgmtWrapper;
import com.sapbasemodule.model.PaginationDetails;
import com.sapbasemodule.model.PendingInvoicesTo;
import com.sapbasemodule.persitence.CustomersAddressesRepository;
import com.sapbasemodule.persitence.CustomersRepository;
import com.sapbasemodule.persitence.OrderItemsRepository;
import com.sapbasemodule.persitence.OrdersRepository;
import com.sapbasemodule.utils.CommonUtility;
import com.sapbasemodule.utils.NumberToWord;

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

/*	@Autowired
	private OINVRepository oinvRepository;
*/
	@Override
	public BaseWrapper doGetCustomersList(Optional<Integer> pageNo) {

		List<Customers> customersList;
		PaginationDetails paginationDetails = null;

		long totalCustCount = customersRepository.count();
		if (pageNo.isPresent()) {
			Sort sort = new Sort(Direction.ASC, "cardName");
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

		AgingReportTo agingReportTo = getAgingDetails(custCode, fromDate);

		return new BaseWrapper(agingReportTo);
	}

	private AgingReportTo getAgingDetails(String custCode, String fromDate)
			throws ClassNotFoundException, SQLException, ParseException {

		Connection conn = commonUtility.getDbConnection();

		String fromDateFormatted = new SimpleDateFormat("yyyyMMdd")
				.format(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate));

		// String sqlQuery = "Select [Bp Code],[BP Name], [t] as Type ,[Posting
		// date], [Due date], [Doc Date],[Ref1],SUM([Balance
		// Due])'Balance',SUM(Future)'Future', "
		// + "SUM([0-30 days])'FirstQ',SUM([31-60 days])'SecondQ',SUM([61-90
		// days])'ThirdQ',SUM([90-120 days])'FourthQ', "
		// + "Sum([120+ days])'OtherQ',Trans from "
		// + "(select T1.cardcode 'BP Code',T1.cardname 'BP Name',sysdeb 'Debit
		// Amount',syscred 'Credit Amount', "
		// + "(T0.BALDUEDEB - T0.BALDUECRED) as 'Balance Due', T0.AdjTran as
		// 'Trans', "
		// + "case T0.transtype when '-2' then 'OB' when '13' then 'A/R Inv'
		// when '14' then 'AR DN' when '24' then 'Incoming' else 'Other' end as
		// 'T' ,Ref1, "
		// + "fccurrency 'BP Currency', " + "CONVERT(VARCHAR(10), refdate, 103)
		// 'Posting Date', "
		// + "CONVERT(VARCHAR(10), duedate, 103) 'Due Date', " +
		// "CONVERT(VARCHAR(10), taxdate, 103) 'Doc Date' , "
		// + "CASE When (DATEDIFF(dd,duedate,'" + fromDateFormatted + "')) < 1
		// then "
		// + "case when BALDUECRED <> 0 then -BALDUECRED else BALDUEDEB end end
		// 'Future', "
		// + "CASE when (DATEDIFF(dd,duedate,'" + fromDateFormatted + "')) < 31
		// and (DATEDIFF(dd,duedate,'"
		// + fromDateFormatted + "')) >= 1 then "
		// + "case when BALDUECRED <> 0 then -BALDUECRED else BALDUEDEB end end
		// '0-30 days', "
		// + "case when ((datediff(dd,duedate,'" + fromDateFormatted + "')) > 30
		// and (datediff(dd,duedate,'"
		// + fromDateFormatted + "'))< 61) then "
		// + "case when BALDUECRED <> 0 then -BALDUECRED else BALDUEDEB end end
		// '31-60 days', "
		// + "case when ((datediff(dd,DueDate,'" + fromDateFormatted + "')) > 60
		// and (datediff(dd,duedate,'"
		// + fromDateFormatted + "'))< 91) then "
		// + "case when BALDUECRED <> 0 then -BALDUECRED else BALDUEDEB end end
		// '61-90 days', "
		// + "CASE when ((DATEDIFF(dd,duedate,'" + fromDateFormatted + "')) > 90
		// and (datediff(dd,duedate,'"
		// + fromDateFormatted + "'))< 121) then "
		// + "case when BALDUECRED= 0 then BALDUEDEB when BALDUEDEB= 0 then
		// -BALDUECRED end end '90-120 days', "
		// + "CASE when (DATEDIFF(dd,duedate,'" + fromDateFormatted + "')) > 121
		// then "
		// + "case when BALDUECRED= 0 then BALDUEDEB when BALDUEDEB= 0 then
		// -BALDUECRED end end '120+ days' "
		// + "from dbo.JDT1 T0 " + "INNER JOIN dbo.OCRD T1 ON T0.shortname =
		// T1.cardcode "
		// + "and T1.cardtype = 'C' where T0.intrnmatch = '0' and T0.BALDUEDEB
		// != T0.BALDUECRED and T1.CardCode= '"
		// + custCode + "' and " + "T0.RefDate <='" + fromDateFormatted + "')"
		// + " sub group by [BP Code],[BP Name],[t],[Posting date], [Due
		// date],[Doc Date],[Ref1],Trans order by [BP Code]";

		String sqlQuery = "Select [Bp Code],[BP Name], [t] as Type ,[Posting date], [Due date], [Doc Date],[Ref1],SUM([Balance Due])'Balance',"
				+ "SUM(Future)'Future', SUM([0-30 days])'FirstQ',SUM([31-60 days])'SecondQ',SUM([61-90 days])'ThirdQ',SUM([90-120 days])'FourthQ',"
				+ "Sum([120+ days])'OtherQ',Trans from (select T1.cardcode 'BP Code',T1.cardname 'BP Name',sysdeb 'Debit Amount',syscred 'Credit Amount', "
				+ "(T0.BALDUEDEB - T0.BALDUECRED) as 'Balance Due', T0.AdjTran as 'Trans', case T0.transtype when '13' then 'A/R Inv' "
				+ "when '14' then 'AR DN' when '24' then 'Incoming' when '-2' then 'OB' else 'Other' end as 'T' , Ref1, "
				+ "fccurrency 'BP Currency', CONVERT(VARCHAR(10), refdate, 103) 'Posting Date', CONVERT(VARCHAR(10), duedate, 103) 'Due Date', "
				+ "CONVERT(VARCHAR(10), taxdate, 103) 'Doc Date' , CASE When (DATEDIFF(dd,refdate,'" + fromDateFormatted
				+ "')) < 1 then case "
				+ "when BALDUECRED <> 0 then -BALDUECRED else BALDUEDEB end end 'Future', CASE when (DATEDIFF(dd,refdate,'"
				+ fromDateFormatted + "')) < 31 " + "and (DATEDIFF(dd,refdate,'" + fromDateFormatted
				+ "')) >= 1 then case when BALDUECRED <> 0 then -BALDUECRED else BALDUEDEB end end '0-30 days', "
				+ "case when ((datediff(dd,refdate,'" + fromDateFormatted + "')) > 30 and (datediff(dd,refdate,'"
				+ fromDateFormatted + "'))< 61) then case "
				+ "when BALDUECRED <> 0 then -BALDUECRED else BALDUEDEB end end '31-60 days', case when ((datediff(dd,refdate,'"
				+ fromDateFormatted + "')) > 60 " + "and (datediff(dd,refdate,'" + fromDateFormatted
				+ "'))< 91) then case when BALDUECRED <> 0 then -BALDUECRED else BALDUEDEB  end end '61-90 days', "
				+ "CASE when ((DATEDIFF(dd,refdate,'" + fromDateFormatted + "')) > 90 and (datediff(dd,refdate,'"
				+ fromDateFormatted + "'))< 121) then case "
				+ "when BALDUECRED= 0 then BALDUEDEB when BALDUEDEB= 0 then -BALDUECRED end end '90-120 days', CASE "
				+ "when (DATEDIFF(dd,refdate,'" + fromDateFormatted
				+ "')) > 121 then case when BALDUECRED= 0 then BALDUEDEB when BALDUEDEB= 0 then -BALDUECRED end end '120+ days' "
				+ "from dbo.JDT1 T0 INNER JOIN dbo.OCRD T1 ON T0.shortname = T1.cardcode and T1.cardtype = 'C' where T0.intrnmatch = '0' "
				+ "and T0.BALDUEDEB != T0.BALDUECRED and T1.CardCode=  '" + custCode + "' and T0.RefDate <='"
				+ fromDateFormatted + "') "
				+ "sub group by [BP Code],[BP Name],[t],[Posting date], [Due date],[Doc Date],[Ref1],Trans order by [BP Code]";

		System.out.println("Final SQL = " + sqlQuery);

		PreparedStatement ps = conn.prepareStatement(sqlQuery);
		ResultSet rs = ps.executeQuery();

		AgingDetails agingDetails = null;
		List<OINV> firstQInvoicesList = new ArrayList<OINV>();
		List<OINV> secondQInvoicesList = new ArrayList<OINV>();
		List<OINV> thirdQInvoicesList = new ArrayList<OINV>();
		List<OINV> fourthQInvoicesList = new ArrayList<OINV>();
		List<OINV> otherQInvoicesList = new ArrayList<OINV>();

		List<InvoicesDetails> finalFirstQInvoicesList = new ArrayList<InvoicesDetails>();
		List<InvoicesDetails> finalSecondQInvoicesList = new ArrayList<InvoicesDetails>();
		List<InvoicesDetails> finalThirdQInvoicesList = new ArrayList<InvoicesDetails>();
		List<InvoicesDetails> finalFourthQInvoicesList = new ArrayList<InvoicesDetails>();
		List<InvoicesDetails> finalOtherQInvoicesList = new ArrayList<InvoicesDetails>();

		if (rs.next()) {

			DateFormat dfDash = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat dfSlash = new SimpleDateFormat("dd/MM/yyyy");

			int ref1 = Integer.parseInt(
					null == rs.getString("Ref1") || rs.getString("Ref1").isEmpty() ? "0" : rs.getString("Ref1"));

			Float balanceDue = rs.getFloat("Balance");
			Float firstQ = rs.getFloat("FirstQ");
			Float secondQ = rs.getFloat("SecondQ");
			Float thirdQ = rs.getFloat("ThirdQ");
			Float fourthQ = rs.getFloat("FourthQ");
			Float otherQ = rs.getFloat("OtherQ");
			String custName = rs.getString("BP Name");

			OINV oinv = new OINV(ref1, ref1, dfDash.format(dfSlash.parse(rs.getString("Posting date"))),
					dfDash.format(dfSlash.parse(rs.getString("Due date"))), custCode, custName, balanceDue, "O",
					rs.getString("Type"));

//			System.out.println(ref1 + " = " + firstQ + ", " + secondQ + ", " + thirdQ + ", " + fourthQ + ", " + otherQ);
			if (firstQ != 0)
				firstQInvoicesList.add(oinv);
			else if (secondQ != 0)
				secondQInvoicesList.add(oinv);
			else if (thirdQ != 0)
				thirdQInvoicesList.add(oinv);
			else if (fourthQ != 0)
				fourthQInvoicesList.add(oinv);
			else if (otherQ != 0)
				otherQInvoicesList.add(oinv);

			while (rs.next()) {

				int ref1Next = Integer.parseInt(
						null == rs.getString("Ref1") || rs.getString("Ref1").isEmpty() ? "0" : rs.getString("Ref1"));

				balanceDue = balanceDue + rs.getFloat("Balance");
				Float rsFirstQ = rs.getFloat("FirstQ");
				firstQ = firstQ + rsFirstQ;
				
				Float rsSecondQ = rs.getFloat("SecondQ");
				secondQ = secondQ + rsSecondQ;
				
				Float rsThirdQ = rs.getFloat("ThirdQ");
				thirdQ = thirdQ + rsThirdQ;
				
				Float rsFourthQ = rs.getFloat("FourthQ");
				fourthQ = fourthQ + rsFourthQ;
				
				Float rsOtherQ = rs.getFloat("OtherQ");
				otherQ = otherQ + rsOtherQ;

				OINV oinvNext = new OINV(ref1Next, ref1Next, dfDash.format(dfSlash.parse(rs.getString("Posting date"))),
						dfDash.format(dfSlash.parse(rs.getString("Due date"))), custCode, custName,
						rs.getFloat("Balance"), "O", rs.getString("Type"));

//				System.out.println(ref1 + " = " + firstQ + ", " + secondQ + ", " + thirdQ + ", " + fourthQ + ", " + otherQ);
				
				if (rsFirstQ != 0)
					firstQInvoicesList.add(oinvNext);
				else if (rsSecondQ != 0)
					secondQInvoicesList.add(oinvNext);
				else if (rsThirdQ != 0)
					thirdQInvoicesList.add(oinvNext);
				else if (rsFourthQ != 0)
					fourthQInvoicesList.add(oinvNext);
				else if (rsOtherQ != 0)
					otherQInvoicesList.add(oinvNext);
			}

			agingDetails = new AgingDetails(custCode, custName, Float.toString(balanceDue), Float.toString(firstQ),
					Float.toString(secondQ), Float.toString(thirdQ), Float.toString(fourthQ), Float.toString(otherQ));

			finalFirstQInvoicesList = processInvoices(firstQInvoicesList, custCode);
			finalSecondQInvoicesList = processInvoices(secondQInvoicesList, custCode);
			finalThirdQInvoicesList = processInvoices(thirdQInvoicesList, custCode);
			finalFourthQInvoicesList = processInvoices(fourthQInvoicesList, custCode);
			finalOtherQInvoicesList = processInvoices(otherQInvoicesList, custCode);
		} else
			agingDetails = new AgingDetails(custCode, "", "0", "0", "0", "0", "0", "0");

		AgingReportTo agingReportTo = new AgingReportTo(agingDetails, finalFirstQInvoicesList, finalSecondQInvoicesList,
				finalThirdQInvoicesList, finalFourthQInvoicesList, finalOtherQInvoicesList);

		return agingReportTo;
	}

	public List<InvoicesDetails> processInvoices(List<OINV> invoicesList, String custCode)
			throws ParseException, ClassNotFoundException, SQLException {

		if (invoicesList.isEmpty())
			invoicesList = new ArrayList<OINV>();

		// Get DocEntry for all invoices
		List<Integer> invoiceDocEntriesList = new ArrayList<Integer>();
		for (OINV oinv : invoicesList)
			invoiceDocEntriesList.add(oinv.getDocEntry());

		System.out.println(invoiceDocEntriesList.toString());

		List<InvoiceItems> invoiceItemsList;
		Map<Integer, List<InvoiceItems>> invoiceItemsMap;
		List<InvoicesDetails> invoiceDetailsList = new ArrayList<InvoicesDetails>();
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

			System.out.println("Invoice Doc Entry Keys In Map = " + invoiceItemsMap.keySet().toString());

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));

			String currentDate = df.format(new Date());
			NumberToWord numberToWord = new NumberToWord();
			for (OINV oinv : invoicesList) {

				int invoiceDocEntry = oinv.getDocEntry();
				System.out.println("Invoice Doc Entry In Iteration : " + invoiceDocEntry);

				String invoiceDate = oinv.getDocDate();
				String invoiceDueDate = oinv.getDocDueDate();
				long paymentDueDays = commonUtility.getDaysDiffBetweenDates(invoiceDate, invoiceDueDate);
				long dueDateInDays = commonUtility.getDaysDiffBetweenDates(currentDate, invoiceDueDate);
				List<InvoiceItems> invoiceItemsListFromMap = invoiceItemsMap.get(invoiceDocEntry);
				System.out.println("invoiceItemsListFromMap = " + invoiceItemsListFromMap);

				String invoiceAmountInWords = numberToWord.convert(Math.round(oinv.getDocTotal()));

				float finalTaxAmount = 0F;

				if (null != invoiceItemsListFromMap) {
					for (InvoiceItems invoiceItems : invoiceItemsListFromMap) {
						// finalTaxAmount = finalTaxAmount +
						// Float.parseFloat(invoiceItems.getCgstTax())
						// + Float.parseFloat(invoiceItems.getSgstTax());
						finalTaxAmount = finalTaxAmount + invoiceItems.getCgstTax() + invoiceItems.getSgstTax();
					}
				}
				String taxAmountInWords = numberToWord.convert((int) Math.floor(finalTaxAmount));

				InvoicesDetails invoicesDetails = new InvoicesDetails(invoiceDocEntry,
						Integer.toString(oinv.getDocNum()), invoiceDate, invoiceDueDate, paymentDueDays,
						oinv.getDocStatus(), 0F, custCode, "", oinv.getType(), invoiceItemsListFromMap, 0F, 0F, 0F, 0F,
						oinv.getDocTotal(), "", dueDateInDays, 0F, 0F, 0F, 0F, invoiceAmountInWords, taxAmountInWords);

				invoiceDetailsList.add(invoicesDetails);
			}
		}

		return invoiceDetailsList;
	}


	@Override
	public BaseWrapper doGetCustomerInvoices(String custCode, int noOfDays, String dueDate)
			throws ParseException, ClassNotFoundException, SQLException {

		// Working Code
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		df.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));

		Date toDate = df.parse(dueDate);

		Calendar cal = Calendar.getInstance();
		cal.setTime(toDate);
		cal.add(Calendar.DATE, noOfDays);
		String fromDate = df.format(cal.getTime());

		List<String> docStatusList = new ArrayList<String>();
		docStatusList.add("O");
		docStatusList.add("C");

		System.out.println("From Date = " + fromDate + "To Date = " + toDate.toString());

		// List<OINV> invoicesList =
		// oinvRepository.selectByCustCodeAndDates(custCode, fromDate, dueDate,
		// docStatusList);

		List<OINV> invoicesList = getInvoicesList(custCode, dueDate, fromDate);

		if (invoicesList.isEmpty())
			invoicesList = new ArrayList<OINV>();

		// Get DocEntry for all invoices
		List<Integer> invoiceDocEntriesList = new ArrayList<Integer>();
		for (OINV oinv : invoicesList)
			invoiceDocEntriesList.add(oinv.getDocEntry());

		System.out.println(invoiceDocEntriesList.toString());

		List<InvoiceItems> invoiceItemsList;
		Map<Integer, List<InvoiceItems>> invoiceItemsMap;
		List<InvoicesDetails> invoiceDetailsList = new ArrayList<InvoicesDetails>();
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

			System.out.println("Invoice Doc Entry Keys In Map = " + invoiceItemsMap.keySet().toString());

			String currentDate = df.format(new Date());
			NumberToWord numberToWord = new NumberToWord();
			for (OINV oinv : invoicesList) {

				int invoiceDocEntry = oinv.getDocEntry();
				System.out.println("Invoice Doc Entry In Iteration : " + invoiceDocEntry);

				String invoiceDate = oinv.getDocDate();
				String invoiceDueDate = oinv.getDocDueDate();
				long paymentDueDays = commonUtility.getDaysDiffBetweenDates(invoiceDate, invoiceDueDate);
				long dueDateInDays = commonUtility.getDaysDiffBetweenDates(currentDate, invoiceDueDate);
				List<InvoiceItems> invoiceItemsListFromMap = invoiceItemsMap.get(invoiceDocEntry);
				System.out.println("invoiceItemsListFromMap = " + invoiceItemsListFromMap);

				String invoiceAmountInWords = numberToWord.convert(Math.round(oinv.getDocTotal()));

				float finalTaxAmount = 0F;

				if (null != invoiceItemsListFromMap) {
					for (InvoiceItems invoiceItems : invoiceItemsListFromMap) {
						// finalTaxAmount = finalTaxAmount +
						// Float.parseFloat(invoiceItems.getCgstTax())
						// + Float.parseFloat(invoiceItems.getSgstTax());
						finalTaxAmount = finalTaxAmount + invoiceItems.getCgstTax() + invoiceItems.getSgstTax();
					}
				}
				String taxAmountInWords = numberToWord.convert((int) Math.floor(finalTaxAmount));

				InvoicesDetails invoicesDetails = new InvoicesDetails(invoiceDocEntry,
						Integer.toString(oinv.getDocNum()), invoiceDate, invoiceDueDate, paymentDueDays,
						oinv.getDocStatus(), 0F, custCode, "", oinv.getType(), invoiceItemsListFromMap, 0F, 0F, 0F, 0F,
						oinv.getDocTotal(), "", dueDateInDays, 0F, 0F, 0F, 0F, invoiceAmountInWords, taxAmountInWords);

				invoiceDetailsList.add(invoicesDetails);
			}
		}

		return new BaseWrapper(invoiceDetailsList);
	}

	private List<OINV> getInvoicesList(String custCode, String endDate, String startDate)
			throws ClassNotFoundException, SQLException, ParseException {

		Connection conn = commonUtility.getDbConnection();

		String fromDateFormatted = new SimpleDateFormat("yyyyMMdd")
				.format(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));

		String toDateFormatted = new SimpleDateFormat("yyyyMMdd")
				.format(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));

		String sqlQuery = "Select [Bp Code],[BP Name], [t] as Type ,[Posting date], [Due date], [Doc Date],[Ref1],SUM([Balance Due])'Balance',SUM(Future)'Future', "
				+ "SUM([0-30 days])'FirstQ',SUM([31-60 days])'SecondQ',SUM([61-90 days])'ThirdQ',SUM([90-120 days])'FourthQ', "
				+ "Sum([120+ days])'OtherQ',Trans from "
				+ "(select T1.cardcode 'BP Code',T1.cardname 'BP Name',sysdeb 'Debit Amount',syscred 'Credit Amount', "
				+ "(T0.BALDUEDEB - T0.BALDUECRED) as 'Balance Due', T0.AdjTran as 'Trans', "
				+ "case T0.transtype when '-2' then 'OB' when '13' then 'A/R Inv' when '14' then 'AR DN' when '24' then 'Incoming' else 'Other' end as 'T' ,Ref1, "
				+ "fccurrency 'BP Currency', " + "CONVERT(VARCHAR(10), refdate, 103) 'Posting Date', "
				+ "CONVERT(VARCHAR(10), refdate, 103) 'Due Date', " + "CONVERT(VARCHAR(10), taxdate, 103) 'Doc Date' , "
				+ "CASE When (DATEDIFF(dd,refdate,'" + fromDateFormatted + "')) < 1 then "
				+ "case when BALDUECRED <> 0 then -BALDUECRED else BALDUEDEB end end 'Future', "
				+ "CASE when (DATEDIFF(dd,refdate,'" + fromDateFormatted + "')) < 31 and (DATEDIFF(dd,refdate,'"
				+ fromDateFormatted + "')) >= 1 then "
				+ "case when BALDUECRED <> 0 then -BALDUECRED else BALDUEDEB end end '0-30 days', "
				+ "case when ((datediff(dd,refdate,'" + fromDateFormatted + "')) > 30 and (datediff(dd,refdate,'"
				+ fromDateFormatted + "'))< 61) then "
				+ "case when BALDUECRED <> 0 then -BALDUECRED else BALDUEDEB end end '31-60 days', "
				+ "case when ((datediff(dd,refdate,'" + fromDateFormatted + "')) > 60 and (datediff(dd,refdate,'"
				+ fromDateFormatted + "'))< 91) then "
				+ "case when BALDUECRED <> 0 then -BALDUECRED else BALDUEDEB  end end '61-90 days', "
				+ "CASE when ((DATEDIFF(dd,refdate,'" + fromDateFormatted + "')) > 90 and (datediff(dd,refdate,'"
				+ fromDateFormatted + "'))< 121) then "
				+ "case when BALDUECRED= 0 then BALDUEDEB when BALDUEDEB= 0 then -BALDUECRED end end '90-120 days', "
				+ "CASE when (DATEDIFF(dd,refdate,'" + fromDateFormatted + "')) > 121 then "
				+ "case when BALDUECRED= 0 then BALDUEDEB when BALDUEDEB= 0 then -BALDUECRED end end '120+ days' "
				+ "from dbo.JDT1 T0 " + "INNER JOIN dbo.OCRD T1 ON T0.shortname = T1.cardcode "
				+ "and T1.cardtype = 'C' where T0.intrnmatch = '0' and  T0.BALDUEDEB != T0.BALDUECRED and  T1.CardCode= '"
				+ custCode + "' and T0.RefDate >='" + fromDateFormatted + "' and T0.RefDate <='" + toDateFormatted
				+ "')"
				+ " sub group by [BP Code],[BP Name],[t],[Posting date], [Due date],[Doc Date],[Ref1],Trans order by [BP Code]";

		System.out.println("Final SQL = " + sqlQuery);

		PreparedStatement ps = conn.prepareStatement(sqlQuery);
		ResultSet rs = ps.executeQuery();

		List<OINV> invoicesList = new ArrayList<OINV>();

		if (rs.next()) {

			DateFormat dfDash = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat dfSlash = new SimpleDateFormat("dd/MM/yyyy");

			int ref1 = Integer.parseInt(
					null == rs.getString("Ref1") || rs.getString("Ref1").isEmpty() ? "0" : rs.getString("Ref1"));

			System.out.println("type = " + rs.getString("Type"));
			OINV oinv = new OINV(ref1, ref1, dfDash.format(dfSlash.parse(rs.getString("Posting date"))),
					dfDash.format(dfSlash.parse(rs.getString("Due date"))), custCode, rs.getString("BP Name"),
					rs.getFloat("Balance"), "O", rs.getString("Type"));
			invoicesList.add(oinv);

			while (rs.next()) {
				OINV oinvNext = new OINV(
						Integer.parseInt(rs.getString("Ref1") == null || rs.getString("Ref1").isEmpty() ? "0"
								: rs.getString("Ref1")),
						Integer.parseInt(rs.getString("Ref1") == null || rs.getString("Ref1").isEmpty() ? "0"
								: rs.getString("Ref1")),
						dfDash.format(dfSlash.parse(rs.getString("Posting date"))),
						dfDash.format(dfSlash.parse(rs.getString("Due date"))), custCode, rs.getString("BP Name"),
						rs.getFloat("Balance"), "O", rs.getString("Type"));
				invoicesList.add(oinvNext);
			}
		}

		return invoicesList;
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
				+ "Where T0.DocEntry IN (" + invoiceIdsInString + ")";

		System.out.println("Final SQL = " + sqlQuery);

		PreparedStatement ps = conn.prepareStatement(sqlQuery);
		ResultSet rs = ps.executeQuery();

		List<InvoiceItems> invoicesItemsDetailsList = new ArrayList<InvoiceItems>();
		while (rs.next()) {
			InvoiceItems invoiceItems = new InvoiceItems(rs.getInt("DocEntry"), rs.getInt("DocNum"),
					rs.getString("ItemCode"), rs.getString("Dscription"), rs.getFloat("Quantity"), rs.getFloat("Price"),
					rs.getFloat("LineTotal"), rs.getString("Chap_Id"), rs.getFloat("CGST Rate"),
					rs.getFloat("SGST Rate"), rs.getFloat("IGST Rate"), rs.getString("Pay_To"), rs.getString("Ship_To"),
					rs.getFloat("RoundDif"), rs.getString("Payment Terms"), rs.getString("Party City"),
					rs.getString("Party GSTIN No"), rs.getString("StateCode"), rs.getString("StateName"),
					rs.getFloat("CGST TAX"), rs.getFloat("SGST TAX"), rs.getFloat("IGST TAX"),
					rs.getString("Frieght Name"), rs.getString("Frieght Amt"), rs.getString("Sac_Code"));

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

		OrderMgmtWrapper response = new OrderMgmtWrapper(finalOrdersList);

		return new BaseWrapper(response);
	}

	@Override
	public BaseWrapper doGetCustomerLedgerReport(String custCode)
			throws ClassNotFoundException, SQLException, ParseException {

		String startDate = "2019-04-01";
		String endDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		// TODO: Get Ledger Report Dynamically
		List<OINV> invoicesList = getInvoicesList(custCode, endDate, startDate);

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		df.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));
		String currentDate = df.format(new Date());

		List<InvoicesDetails> invoiceDetailsList = new ArrayList<InvoicesDetails>();
		NumberToWord numberToWord = new NumberToWord();

		for (OINV oinv : invoicesList) {

			int invoiceDocEntry = oinv.getDocEntry();
			String invoiceDate = oinv.getDocDate();
			String invoiceDueDate = oinv.getDocDueDate();
			long paymentDueDays = commonUtility.getDaysDiffBetweenDates(invoiceDate, invoiceDueDate);
			long dueDateInDays = commonUtility.getDaysDiffBetweenDates(currentDate, invoiceDueDate);
			/*
			 * List<InvoiceItems> invoiceItems =
			 * invoiceItemsMap.get(invoiceDocEntry);
			 */

			String invoiceAmountInWords = numberToWord.convert(Math.round(oinv.getDocTotal()));

			InvoicesDetails invoicesDetails = new InvoicesDetails(invoiceDocEntry, Integer.toString(oinv.getDocNum()),
					invoiceDate, invoiceDueDate, paymentDueDays, oinv.getDocStatus(), 0F, custCode, "", oinv.getType(),
					null, 0F, 0F, 0F, 0F, oinv.getDocTotal(), "", dueDateInDays, 0F, 0F, 0F, 0F, invoiceAmountInWords,
					"");

			invoiceDetailsList.add(invoicesDetails);
		}

		// List<OINV> invoicesList = new ArrayList<OINV>();

		// OINV oinv = new OINV(1, 1, "2 Apr 19", "3 May 19", "", "", 36000F,
		// "O", "OB");
		// invoicesList.add(oinv);
		//
		// for (int i = 1; i <= 3; i++) {
		// OINV oinvNext = new OINV((i + 2), (i + 2), (i + 2) + " Apr 19", (i +
		// 2) + " May 19", "", "", i * 36000F,
		// "O", "AR IN");
		// invoicesList.add(oinvNext);
		// }

		return new BaseWrapper(invoiceDetailsList);
	}
	
	@Override
	public BaseWrapper doGetCustomersPendingInvoices(String custCode, String fromDate, String toDate)
			throws ClassNotFoundException, SQLException, ParseException {

		String pendingInvoicesSqlQuery = "Select T0.Docnum 'Ref.No', T0.DocDate 'Inv.Date',"
				+ "(SELECT Top 1 T0.[ExtraDays] FROM OCTG T10  Where T10.[GroupNum] = T0.[GroupNum])As'Due Date OR Credit Days', "
				+ "DateDiff(DAY,T0.[DocDate],GETDATE())as[Over Due For Billing], "
				+ "T0.CardName As'Party Name',T0.DocTotal as'Opening Amount', "
				+ "(T0.DocTotal - T0.PaidToDate)As'pending Amount',T0.DocDueDate 'Due On', "
				+ "DateDiff(DAY,T0.[DocDueDate],GETDATE())as[Over Due Days] "
				+ "From OINV T0 INNER JOIN INV1 T1 ON T0.DocEntry=T1.DocEntry "
				+ "Where T0.CANCELED='N' And T1.TargetType <> 14 And  " + "T0.DocDate >='" + fromDate + "'"
				+ "And T0.DocDate <='" + toDate + "' " + "And T0.CardCode ='" + custCode + "' order by 'Inv.Date' ASC";
		
		System.out.println("Final Sql Query = " + pendingInvoicesSqlQuery);
		
		Connection conn = commonUtility.getDbConnection();

		PreparedStatement ps = conn.prepareStatement(pendingInvoicesSqlQuery);
		ResultSet rs = ps.executeQuery();

		List<PendingInvoicesTo> pendingInvoicesList = new ArrayList<PendingInvoicesTo>();
		while (rs.next()) {
			PendingInvoicesTo pendingInvoices = new PendingInvoicesTo(rs.getString("Ref.No"), rs.getString("Inv.Date"),
					rs.getString("Due Date OR Credit Days"), rs.getString("Over Due For Billing"), rs.getString("Party Name"), 
					rs.getString("Opening Amount"), rs.getString("pending Amount"), rs.getString("Due On"), rs.getString("Over Due Days"));

			pendingInvoicesList.add(pendingInvoices);
		}

		return new BaseWrapper(pendingInvoicesList);
	}
}
