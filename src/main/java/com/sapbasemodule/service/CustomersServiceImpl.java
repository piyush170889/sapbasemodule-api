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
import com.sapbasemodule.domain.CustomerPin;
import com.sapbasemodule.domain.Customers;
import com.sapbasemodule.domain.InvoicesAcknowledgementDetails;
import com.sapbasemodule.domain.OINV;
import com.sapbasemodule.domain.OrderItems;
import com.sapbasemodule.domain.Orders;
import com.sapbasemodule.exception.ServicesException;
import com.sapbasemodule.model.AgingDetails;
import com.sapbasemodule.model.AgingReportTo;
import com.sapbasemodule.model.BaseWrapper;
import com.sapbasemodule.model.CustomerDetailsWrapper;
import com.sapbasemodule.model.CutomerSummaryReportDetails;
import com.sapbasemodule.model.InvoiceDetailsNewTo;
import com.sapbasemodule.model.InvoiceItems;
import com.sapbasemodule.model.InvoicesDetails;
import com.sapbasemodule.model.LedgerReport;
import com.sapbasemodule.model.OrderMgmtWrapper;
import com.sapbasemodule.model.PaginationDetails;
import com.sapbasemodule.model.PendingInvoicesTo;
import com.sapbasemodule.persitence.CustomersAddressesRepository;
import com.sapbasemodule.persitence.CustomersPinRepository;
import com.sapbasemodule.persitence.CustomersRepository;
import com.sapbasemodule.persitence.InvoiceAcknowledgementDetailsRepository;
import com.sapbasemodule.persitence.OSLPRepository;
import com.sapbasemodule.persitence.OrderItemsRepository;
import com.sapbasemodule.persitence.OrdersRepository;
import com.sapbasemodule.utils.CommonUtility;
import com.sapbasemodule.utils.MessageUtility;
import com.sapbasemodule.utils.NumberToWord;
import com.sapbasemodule.utils.RoleType;

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

	/*
	 * @Autowired private OINVRepository oinvRepository;
	 */
	@Override
	public BaseWrapper doGetCustomersList(Optional<Integer> pageNo) {

		List<Customers> customersList;
		PaginationDetails paginationDetails = null;

		long totalCustCount = customersRepository.count();
		if (pageNo.isPresent()) {
			Sort sort = new Sort(Direction.ASC, "cardName");
			PageRequest pageRequest = commonUtility.getPageRequest(pageNo.get(), sort);

			customersList = customersRepository.findByCardType("C", pageRequest);
			paginationDetails = commonUtility.getPaginationDetails(totalCustCount, pageNo.get());
		} else {
			customersList = customersRepository.findByCardType("C");
		}

		if (customersList.isEmpty())
			customersList = new ArrayList<Customers>();

		// Create Response And Send
		List<CustomerDetailsWrapper> response = getCustomerDetailsWarpperFromCustomersList(customersList);

		Float totalOutstanding = customersRepository.getTotalCustomerOutstandingByType("C");
		System.out.println("totalOutstanding = " + totalOutstanding);

		return new BaseWrapper(response, paginationDetails, totalOutstanding);
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

			Float creditLimit = customers.getCreditLine() == null ? 0F : customers.getCreditLine();

			Float custBalance = customers.getBalance();
			customers.setCreditDeviation(creditLimit < custBalance ? (creditLimit - custBalance) : 0F);

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

		String sqlQuery = "Select [Bp Code],[BP Name], [t] as Type ,[Posting date], [Due date], [Doc Date],[Invoice No.], [Ref1],[Invoice Status],SUM([Balance Due])'Balance',"
				+ "SUM(Future)'Future', SUM([0-30 days])'FirstQ',SUM([31-60 days])'SecondQ',SUM([61-90 days])'ThirdQ',SUM([90-120 days])'FourthQ',"
				+ "Sum([120+ days])'OtherQ',Trans from (select T1.cardcode 'BP Code',T1.cardname 'BP Name',sysdeb 'Debit Amount',syscred 'Credit Amount', "
				+ "(T0.BALDUEDEB - T0.BALDUECRED) as 'Balance Due', T0.AdjTran as 'Trans', case T0.transtype when '13' then 'A/R Inv' "
				+ "when '14' then 'AR DN' when '24' then 'Incoming' when '-2' then 'OB' else 'Other' end as 'T' , T0.Ref1, "
				+ "fccurrency 'BP Currency', CONVERT(VARCHAR(10), refdate, 103) 'Posting Date', CONVERT(VARCHAR(10), duedate, 103) 'Due Date', "
				+ "CONVERT(VARCHAR(10), T0.taxdate, 103) 'Doc Date' , CASE When (DATEDIFF(dd,refdate,'"
				+ fromDateFormatted + "')) < 1 then case "
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
				+ "')) > 121 then case when BALDUECRED= 0 then BALDUEDEB when BALDUEDEB= 0 then -BALDUECRED end end '120+ days', "
				+ "T5.DocNum as 'Invoice No.', T5.DocStatus as 'Invoice Status' "
				+ "from dbo.JDT1 T0 INNER JOIN dbo.OCRD T1 ON T0.shortname = T1.cardcode and T1.cardtype = 'C' "
				+ "LEFT JOIN OINV T5 ON T0.Ref1 = T5.DocNum " + "where T0.intrnmatch = '0' "
				+ "and T0.BALDUEDEB != T0.BALDUECRED and T1.CardCode=  '" + custCode + "' and T0.RefDate <='"
				+ fromDateFormatted + "') "
				+ "sub group by [BP Code],[BP Name],[t],[Posting date], [Due date],[Doc Date],[Ref1],Trans,[Invoice No.],[Invoice Status] "
				+ "order by [BP Code]";

		// System.out.println("Final SQL = " + sqlQuery);

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

			// System.out.println(ref1 + " = " + firstQ + ", " + secondQ + ", "
			// + thirdQ + ", " + fourthQ + ", " + otherQ);
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

				// System.out.println(ref1 + " = " + firstQ + ", " + secondQ +
				// ", " + thirdQ + ", " + fourthQ + ", " + otherQ);

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

		DateFormat dfYYYYMMDD = new SimpleDateFormat("yyyyMMdd");
		String fromDateFormatted = dfYYYYMMDD.format(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
		String toDateFormatted = dfYYYYMMDD.format(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));

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
				+ " sub group by [BP Code],[BP Name],[t],[Posting date], [Due date],[Doc Date],[Ref1],Trans order by [Posting date]";

		// System.out.println("Final SQL = " + sqlQuery);

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

		String invoiceIdsInString = getInvoiceDocIdsInString(invoiceDocEntriesList);

		// System.out.println("Invoice Id String = " + invoiceIdsInString);

		String sqlQuery = "SELECT T0.DocEntry,T0.DocNum,T0.DocDate,T0.DocDueDate As'Due Date',T0.cardcode,T0.cardname, "
				+ "T0.Address as 'Pay_To', T0.Address2 as 'Ship_To',T1.ItemCode,T1.Dscription,T0.RoundDif, T1.Quantity, T1.Price,T1.LineTotal,T0.DocTotal, "
				+ "T0.ShipToCode, (SELECT Top 1 T11.[PymntGroup] FROM OCTG T11  Where T11.[GroupNum] = T0.[GroupNum])As'Payment Terms', "
				+ "(SELECT  TOP 1  T14.[City] FROM  CRD1 T14  where T14.CardCode=T0.cardcode and T14.[City]<>'' and T3.ShipToDef=T14.Address "
				+ "and T14.AdresType='S' )As'Party City' , (select Distinct  T14.[GSTRegnNo] from CRD1 T14  where T14.CardCode=T0.cardcode "
				+ "and T14.[GSTRegnNo]<>'' and T3.ShipToDef=T14.Address and T14.AdresType='S' ) as 'Party GSTIN No',  (Select Distinct Replace(T7.ChapterID,'.','')  "
				+ "from OCHP T7 INNER JOIN OITM T5 On T5.ChapterID=T7.AbsEntry And T5.ItemCode=T1.ItemCode )as'Chap_Id', (select distinct T16.eCode from OCST T16 "
				+ "inner join CRD1 T17 on T16.Code=T17.State And T16.Country=T17.Country where T17.CardCode=T0.CardCode and T17.AdresType='S' and T3.ShipToDef=T17.Address) as 'StateCode', "
				+ "(select distinct T16.Name from OCST T16 inner join CRD1 T17 on T16.Code=T17.State And T16.Country=T17.Country where T17.CardCode=T0.CardCode and T17.AdresType='S' and T3.ShipToDef=T17.Address) as 'StateName', "
				+ "(Select  Top 1 T22.TaxRate  from INV4 T22 Where T22.staType='-100' And T22.DocEntry=T1.DocEntry And T22.LineNum=T1.LineNum )As'CGST Rate', "
				+ "(Select  Top 1 T22.TaxRate  from INV4 T22 Where T22.staType='-110' And T22.DocEntry=T1.DocEntry And T22.LineNum=T1.LineNum )As'SGST Rate', "
				+ "(Select  Top 1 T22.TaxRate  from INV4 T22 Where T22.staType='-120' And T22.DocEntry=T1.DocEntry And T22.LineNum=T1.LineNum )As'IGST Rate', "
				+ "isnull((Select  Sum(T22.TaxSum)  from INV4 T22 Where T22.staType='-100' And T22.DocEntry=T1.DocEntry And T22.LineNum=T1.LineNum ),0)As'CGST TAX', "
				+ "isnull((Select  Sum(T22.TaxSum)  from INV4 T22 Where T22.staType='-110' And T22.DocEntry=T1.DocEntry And T22.LineNum=T1.LineNum ),0)As'SGST TAX', "
				+ "isnull((Select  Sum(T22.TaxSum)  from INV4 T22 Where T22.staType='-120' And T22.DocEntry=T1.DocEntry And T22.LineNum=T1.LineNum ),0)As'IGST TAX', "
				+ "isnull((SELECT distinct Top 1  T1.[ExpnsName] FROM INV3 T10  INNER JOIN OEXD T1 ON T10.[ExpnsCode] = T1.[ExpnsCode] WHERE T10.[DocEntry] =T0.DocEntry),'')'Frieght Name', "
				+ "isnull((SELECT distinct Top 1 T10.[LineTotal] FROM INV3 T10  INNER JOIN OEXD T1 ON T10.[ExpnsCode] = T1.[ExpnsCode] WHERE T10.[DocEntry] =T0.DocEntry),0)'Frieght Amt', "
				+ "(SELECT distinct  Top 1 T1.SacCode  FROM INV3 T10  INNER JOIN OEXD T1 ON T10.[ExpnsCode] = T1.[ExpnsCode] WHERE T10.[DocEntry] =T0.DocEntry)'Sac_Code' "
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

	private String getInvoiceDocIdsInString(List<Integer> invoiceDocEntriesList) {

		int size = invoiceDocEntriesList.size();

		String invoiceIdsInString = "";
		for (int i = 0; i < size; i++) {
			invoiceIdsInString += invoiceDocEntriesList.get(i);
			if ((i + 1) < size)
				invoiceIdsInString += ",";
		}

		return invoiceIdsInString;
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

			String invoiceAmountInWords = numberToWord.convert(Math.round(oinv.getDocTotal()));

			InvoicesDetails invoicesDetails = new InvoicesDetails(invoiceDocEntry, Integer.toString(oinv.getDocNum()),
					invoiceDate, invoiceDueDate, paymentDueDays, oinv.getDocStatus(), 0F, custCode, "", oinv.getType(),
					null, 0F, 0F, 0F, 0F, oinv.getDocTotal(), "", dueDateInDays, 0F, 0F, 0F, 0F, invoiceAmountInWords,
					"");

			invoiceDetailsList.add(invoicesDetails);
		}

		return new BaseWrapper(invoiceDetailsList);
	}

	@Override
	public BaseWrapper doGetCustomersLedgerReportNew(String custCode) throws ClassNotFoundException, SQLException {

		String startDate = "20190401";

		DateFormat dfYYYYMMDD = new SimpleDateFormat("yyyyMMdd");
		dfYYYYMMDD.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));

		String endDate = dfYYYYMMDD.format(new Date());

		String ledgerReportQuery = "Select T0.TransId ,T0.RefDate As'Posting Date',T0.DueDate,(Select (Case "
				+ "When T2.TransType=13 Then 'IN' " + "When T2.TransType=-2 Then 'OB' "
				+ "When T2.TransType=24 Then 'RC' " + "When T2.TransType=30 Then 'JE' "
				+ "When T2.TransType=321 Then 'M.Reco' " + "When T2.TransType=18 Then 'CN' " + "Else 'Other' End) "
				+ "From OJDT T2 Where T2.TransId =T0.TransId)As'Origin', " + "T0.Ref1 As'Origin No', "
				+ "T0.Debit  As'Debit',T0.Credit  As'Credit', "
				+ "((select sum(T4.debit)-sum(T4.Credit) from OJDT T3 INNER JOIN JDT1 T4 on T3.TransId=T4.TransId "
				+ "WHERE T4.ShortName=T0.ShortName and T3.TransId<=T0.TransId)) 'Cumulative Balance', "
				+ " (T0.BALDUEDEB - T0.BALDUECRED) as 'Balance Due' "
				+ "From JDT1 T0 INNER JOIN OCRD T1 ON T1.CardCode=T0.ShortName " + "WHere " + "T0.RefDate >='"
				+ startDate + "' " + "And " + "T0.RefDate <='" + endDate + "' " + "And " + "T1.CardCode='" + custCode
				+ "' " + "And T1.CardType ='C' order by [Posting Date],TransId";

		System.out.println("Final New Ledger Query = " + ledgerReportQuery);

		Connection con = commonUtility.getDbConnection();
		PreparedStatement ps = con.prepareStatement(ledgerReportQuery);

		ResultSet rs = ps.executeQuery();

		List<LedgerReport> ledgerReportInvociesList = new ArrayList<LedgerReport>();
		while (rs.next()) {
			LedgerReport ledgerReport = new LedgerReport(rs.getString("TransId"), rs.getString("Posting Date"),
					rs.getString("DueDate"), rs.getString("Origin"), rs.getString("Origin No"), rs.getString("Debit"),
					rs.getString("Credit"), rs.getString("Cumulative Balance"), rs.getString("Balance Due"));

			ledgerReportInvociesList.add(ledgerReport);
		}

		return new BaseWrapper(ledgerReportInvociesList);
	}

	@Override
	public BaseWrapper doGetCustomersPendingInvoices(String custCode, String fromDate, String toDate)
			throws ClassNotFoundException, SQLException, ParseException {

		// String pendingInvoicesSqlQuery = "Select T0.Docnum 'Ref.No',
		// T0.DocDate 'Inv.Date',"
		// + "(SELECT Top 1 T0.[ExtraDays] FROM OCTG T10 Where T10.[GroupNum] =
		// T0.[GroupNum])As'Due Date OR Credit Days', "
		// + "DateDiff(DAY,T0.[DocDate],GETDATE())as[Over Due For Billing], "
		// + "T0.CardName As'Party Name',T0.DocTotal as'Opening Amount', "
		// + "(T0.DocTotal - T0.PaidToDate)As'pending Amount',T0.DocDueDate 'Due
		// On', "
		// + "DateDiff(DAY,T0.[DocDueDate],GETDATE())as[Over Due Days] "
		// + "From OINV T0 INNER JOIN INV1 T1 ON T0.DocEntry=T1.DocEntry "
		// + "Where T0.CANCELED='N' And T1.TargetType <> 14 And " + "T0.DocDate
		// >='" + fromDate + "'"
		// + "And T0.DocDate <='" + toDate + "' " + "And T0.CardCode ='" +
		// custCode + "' order by 'Inv.Date' ASC";

		String pendingInvoicesSqlQuery = "SELECT Distinct 0 as 'Ref.No', T10.RefDate as 'Inv.Date', T11.[Debit] as OB, "
				+ "	0 as [Due Date OR Credit Days],	0 as [Over Due For Billing], '' as [Party Name], 0 as [Opening Amount], "
				+ "0 as [pending Amount], '' as [Due On], 0 as [Over Due Days] FROM OJDT T10 Inner Join [dbo].[JDT1] T11 on T10.TransId=T11.TransId "
				+ "Left join OCRD T14 ON T14.CardCode = T11.ShortName Where T10.RefDate >='" + fromDate
				+ "' And T10.RefDate <='" + toDate + "' And T14.CardCode ='" + custCode + "' "
				+ "and T10.TransType=-2 Union All Select T0.Docnum 'Ref.No', T0.DocDate 'Inv.Date', 0 OB, "
				+ "(SELECT Top 1 T0.[ExtraDays] FROM OCTG T10  Where T10.[GroupNum] = T0.[GroupNum])As'Due Date OR Credit Days', "
				+ "DateDiff(DAY,T0.[DocDate],GETDATE())as[Over Due For Billing], T0.CardName As'Party Name',T0.DocTotal as'Opening Amount', "
				+ "(T0.DocTotal - T0.PaidToDate)As'pending Amount',T0.DocDueDate 'Due On', DateDiff(DAY,T0.[DocDueDate],GETDATE())as[Over Due Days] "
				+ "From OINV T0 Where T0.CANCELED='N' And T0.DocDate >='" + fromDate + "' And T0.DocDate <='" + toDate
				+ "' And T0.CardCode ='" + custCode + "' "
				+ "And T0.DocStatus <>'C' And (Select Distinct T1.TargetType  From INV1 T1 Where T1.DocEntry =T0.DocEntry)<>14";

		System.out.println("Final Sql Query = " + pendingInvoicesSqlQuery);

		Connection conn = commonUtility.getDbConnection();

		PreparedStatement ps = conn.prepareStatement(pendingInvoicesSqlQuery);
		ResultSet rs = ps.executeQuery();

		List<PendingInvoicesTo> pendingInvoicesList = new ArrayList<PendingInvoicesTo>();
		while (rs.next()) {
			PendingInvoicesTo pendingInvoices = new PendingInvoicesTo(rs.getString("Ref.No"), rs.getString("Inv.Date"),
					rs.getString("Due Date OR Credit Days"), rs.getString("Over Due For Billing"),
					rs.getString("Party Name"), rs.getString("Opening Amount"), rs.getString("pending Amount"),
					rs.getString("Due On"), rs.getString("Over Due Days"), rs.getString("OB"));

			pendingInvoicesList.add(pendingInvoices);
		}

		return new BaseWrapper(pendingInvoicesList);
	}

	@Override
	public BaseWrapper doGetCustomersAllInvoices(String custCode, String tillDate)
			throws ClassNotFoundException, SQLException, ParseException {

		// DateFormat dfYYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
		// dfYYYY_MM_DD.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));
		// DateFormat dfYYYYMMDD = new SimpleDateFormat("yyyyMMdd");
		// dfYYYYMMDD.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));
		//
		// String tillDateFormatted =
		// dfYYYYMMDD.format(dfYYYY_MM_DD.parse(tillDate));
		//
		// String custAllInvoicesQuery = "Select ISNULL(T5.DocNum, '0') As
		// 'Invoice No.', T5.DocStatus As 'Invoice Status', "
		// + "T0.TransId ,T0.RefDate As'Posting Date',T0.DueDate, "
		// + "(Select "
		// + "(Case "
		// + "When T2.TransType=13 Then 'IN' "
		// + "When T2.TransType=-2 Then 'OB' "
		// + "When T2.TransType=24 Then 'RC' "
		// + "When T2.TransType=30 Then 'JE' "
		// + "When T2.TransType=321 Then 'M.Reco' "
		// + "When T2.TransType=18 Then 'CN' "
		// + "Else 'Other' "
		// + "End) "
		// + "From OJDT T2 Where T2.TransId =T0.TransId)As'Origin', "
		// + "T0.Ref1 As'Origin No', T0.Debit As'Debit',T0.Credit As'Credit', "
		// + "((select "
		// + "sum(T4.debit)-sum(T4.Credit) from OJDT T3 INNER JOIN JDT1 T4 on
		// T3.TransId=T4.TransId "
		// + "WHERE "
		// + "T4.ShortName=T0.ShortName and "
		// + "T3.TransId<=T0.TransId "
		// + ")) 'Cumulative Balance', "
		// + "(T0.BALDUEDEB - T0.BALDUECRED) as 'Balance Due' "
		// + "From JDT1 T0 INNER JOIN OCRD T1 ON T1.CardCode=T0.ShortName "
		// + "LEFT JOIN OINV T5 ON T0.Ref1=T5.DocNum "
		// + "WHere T0.RefDate <='" + tillDateFormatted + "' And
		// T1.CardCode='"+custCode+"'"
		// + " And T1.CardType ='C' order by [Posting Date],TransId";
		//
		// System.out.println("Final All Invoicees Query = " +
		// custAllInvoicesQuery);
		//
		// Connection con = commonUtility.getDbConnection();
		// PreparedStatement ps = con.prepareStatement(custAllInvoicesQuery);
		//
		// ResultSet rs = ps.executeQuery();
		//
		// List<InvoiceDetailsNewTo> custAllInvociesList = new
		// ArrayList<InvoiceDetailsNewTo>();
		// while (rs.next()) {
		// InvoiceDetailsNewTo invoiceDetailsNewTo = new
		// InvoiceDetailsNewTo(rs.getInt("Invoice No."), rs.getString("Invoice
		// Status"),
		// rs.getString("TransId"), rs.getString("Posting Date"),
		// rs.getString("DueDate"), rs.getString("Origin"), rs.getString("Origin
		// No"), rs.getString("Debit"),
		// rs.getString("Credit"), rs.getString("Cumulative Balance"),
		// rs.getString("Balance Due"));
		//
		// custAllInvociesList.add(invoiceDetailsNewTo);
		// }

		List<InvoicesDetails> customersInvoiceDetailsList = getCustomerAllInvoices(custCode, new Date());

		return new BaseWrapper(customersInvoiceDetailsList);
	}

	@Autowired
	private OSLPRepository oslpRepository;

	@Override
	public BaseWrapper doGetCustomerDataForSync(String startDate, String endDate)
			throws ClassNotFoundException, SQLException, ParseException {

		List<Customers> customersList = null;
		// Prepare Summary Report Details Map For All Customers
		Map<String, List<CutomerSummaryReportDetails>> customerSummaryReportByCardCodeMap = new HashMap<String, List<CutomerSummaryReportDetails>>();

		List<CutomerSummaryReportDetails> cutomerSummaryReportDetailsList = new ArrayList<CutomerSummaryReportDetails>();

		if (commonUtility.hasRole(RoleType.ROLE_SALES.toString())) {
			// System.out.println("Logged Users ID = " +
			// commonUtility.getLoggedUser().getUsername());
			String slpCode = oslpRepository.selectSlpCodeByMobil(commonUtility.getLoggedUser().getUsername());
			customersList = customersRepository.findByCardTypeAndSlpCode("C", slpCode);
			cutomerSummaryReportDetailsList = getCustomerSummaryReportDetails(slpCode);
		} else if (commonUtility.hasRole(RoleType.ROLE_ADMIN.toString())) {
			customersList = customersRepository.findByCardType("C");
			cutomerSummaryReportDetailsList = getCustomerSummaryReportDetails(null);
		}

		for (CutomerSummaryReportDetails cutomerSummaryReportDetails : cutomerSummaryReportDetailsList) {

			String cardCode = cutomerSummaryReportDetails.getCardCode();

			List<CutomerSummaryReportDetails> cutomerSummaryReportDetailsListInMap = null;
			if (customerSummaryReportByCardCodeMap.containsKey(cardCode)) {
				cutomerSummaryReportDetailsListInMap = customerSummaryReportByCardCodeMap.get(cardCode);
			} else
				cutomerSummaryReportDetailsListInMap = new ArrayList<CutomerSummaryReportDetails>();

			cutomerSummaryReportDetailsListInMap.add(cutomerSummaryReportDetails);

			customerSummaryReportByCardCodeMap.put(cardCode, cutomerSummaryReportDetailsListInMap);
		}

		if (customersList.isEmpty())
			customersList = new ArrayList<Customers>();

		// Get All Invoices Of Customers And Put them against each customer in a
		// map
		List<String> custIds = new ArrayList<String>();
		for (Customers customers : customersList)
			custIds.add(customers.getCardCode());

		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

		Date tillDate = null;
		if (endDate == null)
			tillDate = new Date();
		else
			tillDate = df.parse(endDate);

		Date fromDate = null;
		if (startDate != null)
			fromDate = df.parse(startDate);

		List<InvoicesDetails> allCustomersInvoiceDetailsList = null;

		// All Invoices Query
		if (custIds.size() > 0)
			allCustomersInvoiceDetailsList = getAllInvoicesForCustIds(custIds, fromDate, tillDate);
		else
			allCustomersInvoiceDetailsList = new ArrayList<InvoicesDetails>();

		Map<String, List<InvoicesDetails>> customerWiseInvoicesMap = new HashMap<String, List<InvoicesDetails>>();
		for (InvoicesDetails invoicesDetails : allCustomersInvoiceDetailsList) {

			String custCode = invoicesDetails.getCardCode();
			List<InvoicesDetails> customersInvoicesList = null;

			if (customerWiseInvoicesMap.containsKey(custCode))
				customersInvoicesList = customerWiseInvoicesMap.get(custCode);
			else
				customersInvoicesList = new ArrayList<InvoicesDetails>();

			customersInvoicesList.add(invoicesDetails);

			customerWiseInvoicesMap.put(custCode, customersInvoicesList);
		}

		// Get Pins For All Cust IDs
		List<CustomerPin> customerPinsList = customersPinRepository.findAll();
		Map<String, String> customerPinForCardCodeMap = new HashMap<String, String>();

		for (CustomerPin customerPin : customerPinsList)
			customerPinForCardCodeMap.put(customerPin.getCardCode(), customerPin.getCustomersPin());

		// Create Response And Send
		List<CustomerDetailsWrapper> response = new ArrayList<CustomerDetailsWrapper>();

		for (Customers customers : customersList) {
			String custCode = customers.getCardCode();

			customers.setPin(customerPinForCardCodeMap.get(custCode));

			// List<CustomerAddresses> customerAddressesList =
			// customersAddressesRepository.findByCardCode(custCode);

			// List<InvoicesDetails> customersInvoiceDetailsList =
			// getCustomerAllInvoices(custCode, tillDate);
			List<InvoicesDetails> customersInvoiceDetailsList = customerWiseInvoicesMap.get(custCode);

			Float creditLimit = customers.getCreditLine() == null ? 0F : customers.getCreditLine();

			Float custBalance = customers.getBalance();
			customers.setCreditDeviation(creditLimit < custBalance ? (creditLimit - custBalance) : 0F);

			// CustomerDetailsWrapper customerDetailsWrapper = new
			// CustomerDetailsWrapper(customers, customerAddressesList,
			// customersInvoiceDetailsList);

			// Customer Summary Report List
			List<CutomerSummaryReportDetails> cutomerSummaryReportDetailsListFromMap = customerSummaryReportByCardCodeMap
					.get(custCode);

			CustomerDetailsWrapper customerDetailsWrapper = new CustomerDetailsWrapper(customers, null,
					customersInvoiceDetailsList, cutomerSummaryReportDetailsListFromMap);

			response.add(customerDetailsWrapper);
		}

		return new BaseWrapper(response);
	}

	@Override
	public List<CutomerSummaryReportDetails> getCustomerSummaryReportDetails(String slpCode)
			throws ClassNotFoundException, SQLException {

		String fromDate = "20190401";

		DateFormat dfYYYYMMDD = new SimpleDateFormat("yyyyMMdd");
		dfYYYYMMDD.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));

		String tillDate = dfYYYYMMDD.format(new Date());

		String custSummaryReportQuery = null;

		if (null == slpCode)
			custSummaryReportQuery = "SELECT  CardCode,Name,[Sales Emp Name],Brand ,[4] as [Apr],[5] as [May],[6] as [Jun],[7] as [Jul],[8] as [Aug],[9] as [Sep],[10] as [Oct],[11] as [Nov],[12] as [Dec],[1] as [Jan],[2] as [Feb],[3] as [Mar] FROM("
					+ " SELECT  T0.CardCode As 'CardCode',T0.CardName As'Name',(Sum(T1.Quantity))/20 as 'Qty', month(t0.docdate) as 'Month',T1.Dscription As'Brand',T4.SlpName As'Sales Emp Name' FROM OINV T0 INNER JOIN INV1 T1 ON T0.Docentry=T1.DocEntry"
					+ " INNER JOIN OSLP T4 ON T4.SlpCode =T0.SlpCode WHERE T0.[DocDate]  >='" + fromDate
					+ "' AND  T0.[DocDate] <='" + tillDate
					+ "' And T1.Dscription In ('Birla Super','Ultratech PPC','Rajashree Plus Cement') And T1.TargetType <>14"
					+ " Group By T0.CardCode,t0.docdate,T0.CardName,T1.Dscription,T4.SlpName) S"
					+ " PIVOT  (Sum(S.Qty) FOR [month] IN ([4],[5],[6],[7],[8],[9],[10],[11],[12],[1],[2],[3])) P";
		else
			custSummaryReportQuery = "SELECT  CardCode,Name,[Sales Emp Name],Brand ,[4] as [Apr],[5] as [May],[6] as [Jun],[7] as [Jul],[8] as [Aug],[9] as [Sep],[10] as [Oct],[11] as [Nov],[12] as [Dec],[1] as [Jan],[2] as [Feb],[3] as [Mar] FROM("
					+ " SELECT  T0.CardCode As 'CardCode',T0.CardName As'Name',(Sum(T1.Quantity))/20 as 'Qty', month(t0.docdate) as 'Month',T1.Dscription As'Brand',T4.SlpName As'Sales Emp Name' FROM OINV T0 INNER JOIN INV1 T1 ON T0.Docentry=T1.DocEntry"
					+ " INNER JOIN OSLP T4 ON T4.SlpCode =T0.SlpCode WHERE T0.[DocDate]  >='" + fromDate
					+ "' AND  T0.[DocDate] <='" + tillDate
					+ "' And T1.Dscription In ('Birla Super','Ultratech PPC','Rajashree Plus Cement') And T1.TargetType <>14 AND T4.SlpCode="
					+ slpCode + " Group By T0.CardCode,t0.docdate,T0.CardName,T1.Dscription,T4.SlpName) S"
					+ " PIVOT  (Sum(S.Qty) FOR [month] IN ([4],[5],[6],[7],[8],[9],[10],[11],[12],[1],[2],[3])) P";

		System.out.println("custSummaryReportQuery = " + custSummaryReportQuery);

		Connection con = commonUtility.getDbConnection();
		PreparedStatement ps = con.prepareStatement(custSummaryReportQuery);

		ResultSet rs = ps.executeQuery();

		List<CutomerSummaryReportDetails> cutomerSummaryReportDetailsList = extractSummaryReportOfResultset(rs);

		return cutomerSummaryReportDetailsList;
	}

	private List<CutomerSummaryReportDetails> extractSummaryReportOfResultset(ResultSet rs)
			throws NumberFormatException, SQLException {

		List<CutomerSummaryReportDetails> cutomerSummaryReportDetailsList = new ArrayList<CutomerSummaryReportDetails>();

		while (rs.next()) {
			String apr = rs.getString("Apr");
			String may = rs.getString("May");
			String jun = rs.getString("Jun");
			String july = rs.getString("Jul");
			String aug = rs.getString("Aug");
			String sep = rs.getString("Sep");
			String oct = rs.getString("Oct");
			String nov = rs.getString("Nov");
			String dec = rs.getString("Dec");
			String jan = rs.getString("Jan");
			String feb = rs.getString("Feb");
			String mar = rs.getString("Mar");

			CutomerSummaryReportDetails cutomerSummaryReportDetails = new CutomerSummaryReportDetails(
					rs.getString("CardCode"), rs.getString("Name"), rs.getString("Sales Emp Name"),
					rs.getString("Brand"),
					null == apr || apr.isEmpty() ? "0.00" : commonUtility.roundString(Double.parseDouble(apr), 2),
					null == may || may.isEmpty() ? "0.00" : commonUtility.roundString(Double.parseDouble(may), 2),
					null == jun || jun.isEmpty() ? "0.00" : commonUtility.roundString(Double.parseDouble(jun), 2),
					null == july || july.isEmpty() ? "0.00" : commonUtility.roundString(Double.parseDouble(july), 2),
					null == aug || aug.isEmpty() ? "0.00" : commonUtility.roundString(Double.parseDouble(aug), 2),
					null == sep || sep.isEmpty() ? "0.00" : commonUtility.roundString(Double.parseDouble(sep), 2),
					null == oct || oct.isEmpty() ? "0.00" : commonUtility.roundString(Double.parseDouble(oct), 2),
					null == nov || nov.isEmpty() ? "0.00" : commonUtility.roundString(Double.parseDouble(nov), 2),
					null == dec || dec.isEmpty() ? "0.00" : commonUtility.roundString(Double.parseDouble(dec), 2),
					null == jan || jan.isEmpty() ? "0.00" : commonUtility.roundString(Double.parseDouble(jan), 2),
					null == feb || feb.isEmpty() ? "0.00" : commonUtility.roundString(Double.parseDouble(feb), 2),
					null == mar || mar.isEmpty() ? "0.00" : commonUtility.roundString(Double.parseDouble(mar), 2));
			cutomerSummaryReportDetailsList.add(cutomerSummaryReportDetails);
		}

		return cutomerSummaryReportDetailsList;
	}

	public List<CutomerSummaryReportDetails> getCustomerSummaryReportDetailsByBrands(List<String> brandsList)
			throws ClassNotFoundException, SQLException {

		String brandsNameCommaSeparated = "";

		for (String brand : brandsList) {
			if (brandsNameCommaSeparated.isEmpty())
				brandsNameCommaSeparated = brandsNameCommaSeparated + "'" + brand + "'";
			else
				brandsNameCommaSeparated = brandsNameCommaSeparated + ", '" + brand + "'";
		}

		String fromDate = "20190401";

		DateFormat dfYYYYMMDD = new SimpleDateFormat("yyyyMMdd");
		dfYYYYMMDD.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));

		String tillDate = dfYYYYMMDD.format(new Date());

		String custSummaryReportQuery = null;

		custSummaryReportQuery = "SELECT  CardCode,Name,[Sales Emp Name],Brand ,[4] as [Apr],[5] as [May],[6] as [Jun],[7] as [Jul],[8] as [Aug],[9] as [Sep],[10] as [Oct],[11] as [Nov],[12] as [Dec],[1] as [Jan],[2] as [Feb],[3] as [Mar] FROM("
				+ " SELECT  T0.CardCode As 'CardCode',T0.CardName As'Name',(Sum(T1.Quantity))/20 as 'Qty', month(t0.docdate) as 'Month',T1.Dscription As'Brand',T4.SlpName As'Sales Emp Name' FROM OINV T0 INNER JOIN INV1 T1 ON T0.Docentry=T1.DocEntry"
				+ " INNER JOIN OSLP T4 ON T4.SlpCode =T0.SlpCode WHERE T0.[DocDate]  >='" + fromDate
				+ "' AND  T0.[DocDate] <='" + tillDate + "'" + " And T1.TargetType <>14 AND T1.Dscription IN ("
				+ brandsNameCommaSeparated + ")"
				+ " Group By T0.CardCode,t0.docdate,T0.CardName,T1.Dscription,T4.SlpName) S"
				+ " PIVOT  (Sum(S.Qty) FOR [month] IN ([4],[5],[6],[7],[8],[9],[10],[11],[12],[1],[2],[3])) P";

		System.out.println("custSummaryReportQuery = " + custSummaryReportQuery);

		Connection con = commonUtility.getDbConnection();
		PreparedStatement ps = con.prepareStatement(custSummaryReportQuery);

		ResultSet rs = ps.executeQuery();

		// List<CutomerSummaryReportDetails> cutomerSummaryReportDetailsList =
		// new ArrayList<CutomerSummaryReportDetails>();
		//
		// while (rs.next()) {
		// CutomerSummaryReportDetails cutomerSummaryReportDetails = new
		// CutomerSummaryReportDetails(
		// rs.getString("CardCode"), rs.getString("Name"), rs.getString("Sales
		// Emp Name"),
		// rs.getString("Brand"), rs.getString("Apr"), rs.getString("May"),
		// rs.getString("Jun"),
		// rs.getString("Jul"), rs.getString("Aug"), rs.getString("Sep"),
		// rs.getString("Oct"),
		// rs.getString("Nov"), rs.getString("Dec"), rs.getString("Jan"),
		// rs.getString("Feb"),
		// rs.getString("Mar"));
		// cutomerSummaryReportDetailsList.add(cutomerSummaryReportDetails);
		// }

		List<CutomerSummaryReportDetails> cutomerSummaryReportDetailsList = extractSummaryReportOfResultset(rs);

		return cutomerSummaryReportDetailsList;
	}

	private List<InvoicesDetails> getCustomerAllInvoices(String custCode, Date tillDate)
			throws ClassNotFoundException, SQLException, ParseException {

		DateFormat dfYYYYMMDD = new SimpleDateFormat("yyyyMMdd");
		dfYYYYMMDD.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));

		String tillDateFormatted = dfYYYYMMDD.format(tillDate);

		String custAllInvoicesQuery = "Select ISNULL(T5.DocEntry, '0') As 'DocEntry', ISNULL(T5.DocNum, '0') As 'Invoice No.', T5.DocStatus As 'Invoice Status', "
				+ "T0.TransId ,T0.RefDate As'Posting Date',T0.DueDate, " + "(Select  " + "(Case  "
				+ "When T2.TransType=13 Then 'IN' " + "When T2.TransType=-2 Then 'OB'  "
				+ "When T2.TransType=24 Then 'RC'  " + "When T2.TransType=30 Then 'JE'  "
				+ "When T2.TransType=321 Then 'M.Reco'  " + "When T2.TransType=18 Then 'CN'  " + "Else 'Other'  "
				+ "End)  " + "From OJDT T2 Where T2.TransId =T0.TransId) As 'Origin', "
				+ "T0.Ref1 As'Origin No', T0.Ref2 As'Ref2', T0.Debit As 'Debit',T0.Credit As'Credit', " + "((select  "
				+ "sum(T4.debit)-sum(T4.Credit) from OJDT T3 INNER JOIN JDT1 T4 on T3.TransId=T4.TransId " + "WHERE  "
				+ "T4.ShortName=T0.ShortName and " + "T3.TransId<=T0.TransId " + ")) 'Cumulative Balance', "
				+ "(T0.BALDUEDEB - T0.BALDUECRED) as 'Balance Due' "
				+ "From JDT1 T0 INNER JOIN OCRD T1 ON T1.CardCode=T0.ShortName "
				+ "LEFT JOIN OINV T5 ON T0.Ref1=T5.DocNum  " + "WHere T0.RefDate <='" + tillDateFormatted
				+ "' And T1.CardCode='" + custCode + "'" + " And T1.CardType = 'C' order by [Posting Date],TransId";

		// System.out.println("Final All Invoices Query = " +
		// custAllInvoicesQuery);

		Connection con = commonUtility.getDbConnection();
		PreparedStatement ps = con.prepareStatement(custAllInvoicesQuery);

		ResultSet rs = ps.executeQuery();

		List<InvoiceDetailsNewTo> custAllInvociesList = new ArrayList<InvoiceDetailsNewTo>();
		List<Integer> invoiceDocEntriesList = new ArrayList<Integer>();

		while (rs.next()) {
			String invoiceType = rs.getString("Origin");
			int invoiceNo = rs.getInt("Invoice No.");

			InvoiceDetailsNewTo invoicesDetails = new InvoiceDetailsNewTo(invoiceNo, rs.getString("Invoice Status"),
					rs.getString("TransId"), rs.getString("Posting Date"), rs.getString("DueDate"), invoiceType,
					rs.getString("Origin No"), Double.toString(commonUtility.round(rs.getDouble("Debit"), 2)),
					Double.toString(commonUtility.round(rs.getDouble("Credit"), 2)),
					Double.toString(commonUtility.round(rs.getDouble("Cumulative Balance"), 2)),
					rs.getString("Balance Due"), rs.getInt("DocEntry"), rs.getString("Ref2"), custCode);

			if (invoiceType.equalsIgnoreCase("IN") && invoiceNo != 0) {
				invoiceDocEntriesList.add(invoiceNo);
			}

			custAllInvociesList.add(invoicesDetails);
		}

		// System.out.println(invoiceDocEntriesList.toString());

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

			// Get Acknowledgement Details Of All Invoices Ids and set signature
			// of each against their invoice id
			List<InvoicesAcknowledgementDetails> invoicesAcknowledgementDetailsList = invoiceAcknowledgementDetailsRepository
					.selectByInvoiceNos(invoiceDocEntriesList);

			Map<Integer, String> invoiceAcknowledgementMap = new HashMap<Integer, String>();
			for (InvoicesAcknowledgementDetails invoicesAcknowledgementDetails : invoicesAcknowledgementDetailsList)
				invoiceAcknowledgementMap.put(invoicesAcknowledgementDetails.getInvoiceNo(),
						invoicesAcknowledgementDetails.getSignature());

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));

			String currentDate = df.format(new Date());
			NumberToWord numberToWord = new NumberToWord();
			for (InvoiceDetailsNewTo custInvoice : custAllInvociesList) {

				int invoiceDocEntry = custInvoice.getInvoiceDocEntry();
				long paymentDueDays = 0L;
				long dueDateInDays = 0L;
				String invoiceAmountInWords = null;
				String taxAmountInWords = null;
				List<InvoiceItems> invoiceItemsListFromMap = null;
				String invoiceDate = custInvoice.getPostingDate();
				String invoiceDueDate = custInvoice.getDueDate();

				Float balanceAmt = Float.parseFloat(custInvoice.getBalance());
				if (invoiceDocEntry != 0 && custInvoice.getOrigin().equalsIgnoreCase("IN")) {

					// System.out.println("Invoice Doc Entry In Iteration : " +
					// invoiceDocEntry);

					paymentDueDays = commonUtility.getDaysDiffBetweenDates(invoiceDate, invoiceDueDate);
					dueDateInDays = commonUtility.getDaysDiffBetweenDates(currentDate, invoiceDueDate);

					invoiceItemsListFromMap = invoiceItemsMap.get(invoiceDocEntry);
					// System.out.println("invoiceItemsListFromMap = " +
					// invoiceItemsListFromMap);

					invoiceAmountInWords = numberToWord.convert(Math.round(balanceAmt));

					float finalTaxAmount = 0F;

					if (null != invoiceItemsListFromMap) {
						for (InvoiceItems invoiceItems : invoiceItemsListFromMap)
							finalTaxAmount = finalTaxAmount + invoiceItems.getCgstTax() + invoiceItems.getSgstTax();
					}
					taxAmountInWords = numberToWord.convert((int) Math.floor(finalTaxAmount));

				}

				InvoicesDetails invoicesDetails = new InvoicesDetails(invoiceDocEntry,
						Integer.toString(custInvoice.getInvoiceNo()), invoiceDate, invoiceDueDate, paymentDueDays,
						custInvoice.getInvoiceStatus(), balanceAmt, custCode, "", custInvoice.getOrigin(),
						invoiceItemsListFromMap, 0F, balanceAmt, "", dueDateInDays, invoiceAmountInWords,
						taxAmountInWords, custInvoice.getTransId(), custInvoice.getOriginNo(), custInvoice.getDebit(),
						custInvoice.getCredit(), custInvoice.getCumulativeBalance(), custInvoice.getRef2(),
						invoiceAcknowledgementMap.get(invoiceDocEntry));

				invoiceDetailsList.add(invoicesDetails);
			}
		}

		return invoiceDetailsList;
	}

	private List<InvoicesDetails> getAllInvoicesForCustIds(List<String> custCodesList, Date fromDate, Date tillDate)
			throws ClassNotFoundException, SQLException, ParseException {

		DateFormat dfYYYYMMDD = new SimpleDateFormat("yyyyMMdd");
		dfYYYYMMDD.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));

		String tillDateFormatted = dfYYYYMMDD.format(tillDate);

		String fromDateFormatted = "";
		if (fromDate == null) {
			Calendar cal = Calendar.getInstance();

			// TODO: Set To And Till Date from UI absed on the Financial Year Selection
//		int currentMonth = cal.get(Calendar.MONTH);
//		if (currentMonth < 3)
//			cal.add(Calendar.YEAR, -1);
			cal.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.MONTH, Calendar.APRIL);
			cal.set(Calendar.YEAR, 2019);

			fromDateFormatted = dfYYYYMMDD.format(cal.getTime());
		} else {
			fromDateFormatted = dfYYYYMMDD.format(fromDate);
		}

		String custCodesCommaSeparated = "";
		int custCodesListSize = custCodesList.size();

		for (int i = 0; i < custCodesListSize; i++) {

			custCodesCommaSeparated = custCodesCommaSeparated + "'" + custCodesList.get(i) + "'";

			if ((i + 1) < custCodesListSize)
				custCodesCommaSeparated = custCodesCommaSeparated + ",";
		}

		String custAllInvoicesQuery = "Select T1.CardCode As CardCode, ISNULL(T5.DocEntry, '0') As 'DocEntry', ISNULL(T5.DocNum, '0') As 'Invoice No.', T5.DocStatus As 'Invoice Status', "
				+ "T0.TransId ,T0.RefDate As'Posting Date',T0.DueDate, " + "(Select  " + "(Case  "
				+ "When T2.TransType=13 Then 'IN' " + "When T2.TransType=-2 Then 'OB'  "
				+ "When T2.TransType=24 Then 'RC'  " + "When T2.TransType=30 Then 'JE'  "
				+ "When T2.TransType=321 Then 'M.Reco'  " + "When T2.TransType=18 Then 'CN'  " + "Else 'Other'  "
				+ "End)  " + "From OJDT T2 Where T2.TransId =T0.TransId) As 'Origin', "
				+ "T0.Ref1 As'Origin No', T0.Ref2 As'Ref2', T0.Debit As 'Debit',T0.Credit As'Credit', " + "((select  "
				+ "sum(T4.debit)-sum(T4.Credit) from OJDT T3 INNER JOIN JDT1 T4 on T3.TransId=T4.TransId " + "WHERE  "
				+ "T4.ShortName=T0.ShortName and " + "T3.TransId<=T0.TransId " + ")) 'Cumulative Balance', "
				+ "(T0.BALDUEDEB - T0.BALDUECRED) as 'Balance Due' "
				+ "From JDT1 T0 INNER JOIN OCRD T1 ON T1.CardCode=T0.ShortName AND ISNUMERIC(T0.Ref1 + '.0e0')=1 "
				+ "LEFT JOIN OINV T5 ON CAST(T0.Ref1 As float)=CAST(T5.DocNum As float) " + "Where T0.RefDate >='"
				+ fromDateFormatted + "' And T0.RefDate <='" + tillDateFormatted + "' And T1.CardCode IN ("
				+ custCodesCommaSeparated + ")" + " And T1.CardType = 'C' order by [Posting Date],TransId";

		System.out.println("Final All Invoices Query = " + custAllInvoicesQuery);

		Connection con = commonUtility.getDbConnection();
		PreparedStatement ps = con.prepareStatement(custAllInvoicesQuery);

		ResultSet rs = ps.executeQuery();

		List<InvoiceDetailsNewTo> custOtherInvociesList = new ArrayList<InvoiceDetailsNewTo>();
		List<InvoiceDetailsNewTo> arInvociesList = new ArrayList<InvoiceDetailsNewTo>();
		List<Integer> invoiceDocEntriesList = new ArrayList<Integer>();

		while (rs.next()) {

			String invoiceType = rs.getString("Origin");
			// int invoiceNo = rs.getInt("Invoice No.");
			int invoiceDocEntry = rs.getInt("DocEntry");

			InvoiceDetailsNewTo invoicesDetails = new InvoiceDetailsNewTo(rs.getInt("Invoice No."),
					rs.getString("Invoice Status"), rs.getString("TransId"), rs.getString("Posting Date"),
					rs.getString("DueDate"), invoiceType, rs.getString("Origin No"),
					Double.toString(commonUtility.round(rs.getDouble("Debit"), 2)),
					Double.toString(commonUtility.round(rs.getDouble("Credit"), 2)),
					Double.toString(commonUtility.round(rs.getDouble("Cumulative Balance"), 2)),
					rs.getString("Balance Due"), rs.getInt("DocEntry"), rs.getString("Ref2"), rs.getString("CardCode"));

			// if (invoiceType.equalsIgnoreCase("IN") && invoiceNo != 0) {
			// invoiceDocEntriesList.add(invoiceNo);
			if (invoiceType.equalsIgnoreCase("IN") && invoiceDocEntry != 0) {
				invoiceDocEntriesList.add(invoiceDocEntry);
				arInvociesList.add(invoicesDetails);
			} else {
				custOtherInvociesList.add(invoicesDetails);
			}

		}

		// System.out.println(invoiceDocEntriesList.toString());

		List<InvoiceItems> invoiceItemsList;
		Map<Integer, List<InvoiceItems>> invoiceItemsMap;
		List<InvoicesDetails> invoiceDetailsList = new ArrayList<InvoicesDetails>();
		List<InvoicesAcknowledgementDetails> invoicesAcknowledgementDetailsList;

		int invoiceDocEntriesListSize = invoiceDocEntriesList.size();
		if (invoiceDocEntriesList.size() > 0) {

			int recordCount = 0;
			int loopLimit = 0;
			int prevBatchIndex = 0;

			// System.out.println("invoiceDocEntriesListSize = " +
			// invoiceDocEntriesListSize);
			while (recordCount < invoiceDocEntriesListSize) {

				prevBatchIndex = recordCount;

				loopLimit = loopLimit + ((invoiceDocEntriesListSize - recordCount > 2095) ? 2095
						: (invoiceDocEntriesListSize - recordCount));
				List<Integer> invoiceDocEntriesBatchList = new ArrayList<Integer>();
				// System.out.println("recordCount = " + recordCount + ",
				// loopLimit = " + loopLimit);
				for (; recordCount < loopLimit; recordCount++) {
					invoiceDocEntriesBatchList.add(invoiceDocEntriesList.get(recordCount));
				}
				// System.out.println("invoiceDocEntriesBatchListSize = " +
				// invoiceDocEntriesBatchList.size());

				// invoiceItemsList =
				// getInvoiceItemsListForDocEntries(invoiceDocEntriesList);
				invoiceItemsList = getInvoiceItemsListForDocEntries(invoiceDocEntriesBatchList);

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

				// Get Acknowledgement Details Of All Invoices Ids and set
				// signature
				// of each against their invoice id
				// invoicesAcknowledgementDetailsList =
				// invoiceAcknowledgementDetailsRepository
				// .selectByInvoiceNos(invoiceDocEntriesList);
				invoicesAcknowledgementDetailsList = invoiceAcknowledgementDetailsRepository
						.selectByInvoiceNos(invoiceDocEntriesBatchList);

				Map<Integer, String> invoiceAcknowledgementMap = new HashMap<Integer, String>();
				for (InvoicesAcknowledgementDetails invoicesAcknowledgementDetails : invoicesAcknowledgementDetailsList)
					invoiceAcknowledgementMap.put(invoicesAcknowledgementDetails.getInvoiceNo(),
							invoicesAcknowledgementDetails.getSignature());

				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				df.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));

				String currentDate = df.format(new Date());
				NumberToWord numberToWord = new NumberToWord();
				// System.out.println("prevBatchIndex = " + prevBatchIndex + ",
				// recordCount = " + recordCount);
				// for (InvoiceDetailsNewTo custInvoice : custAllInvociesList) {
				for (int i = prevBatchIndex; i < recordCount; i++) {

					// int invoiceDocEntry = custInvoice.getInvoiceDocEntry();
					InvoiceDetailsNewTo custInvoice = arInvociesList.get(i);
					int invoiceDocEntry = custInvoice.getInvoiceDocEntry();
					long paymentDueDays = 0L;
					long dueDateInDays = 0L;
					String invoiceAmountInWords = null;
					String taxAmountInWords = null;
					List<InvoiceItems> invoiceItemsListFromMap = null;
					String invoiceDate = custInvoice.getPostingDate();
					String invoiceDueDate = custInvoice.getDueDate();

					Float balanceAmt = Float.parseFloat(custInvoice.getBalance());
					if (invoiceDocEntry != 0 && custInvoice.getOrigin().equalsIgnoreCase("IN")) {

						// System.out.println("Invoice Doc Entry In Iteration :
						// " +
						// invoiceDocEntry);

						paymentDueDays = commonUtility.getDaysDiffBetweenDates(invoiceDate, invoiceDueDate);
						dueDateInDays = commonUtility.getDaysDiffBetweenDates(currentDate, invoiceDueDate);

						invoiceItemsListFromMap = invoiceItemsMap.get(invoiceDocEntry);
						// System.out.println("invoiceItemsListFromMap = " +
						// invoiceItemsListFromMap);

						invoiceAmountInWords = numberToWord.convert(Math.round(balanceAmt));

						float finalTaxAmount = 0F;

						if (null != invoiceItemsListFromMap) {
							for (InvoiceItems invoiceItems : invoiceItemsListFromMap)
								finalTaxAmount = finalTaxAmount + invoiceItems.getCgstTax() + invoiceItems.getSgstTax();
						}
						taxAmountInWords = numberToWord.convert((int) Math.floor(finalTaxAmount));

					}

					String custCode = custInvoice.getCustCode();
					InvoicesDetails invoicesDetails = new InvoicesDetails(invoiceDocEntry,
							Integer.toString(custInvoice.getInvoiceNo()), invoiceDate, invoiceDueDate, paymentDueDays,
							custInvoice.getInvoiceStatus(), balanceAmt, custCode, "", custInvoice.getOrigin(),
							invoiceItemsListFromMap, 0F, balanceAmt, "", dueDateInDays, invoiceAmountInWords,
							taxAmountInWords, custInvoice.getTransId(), custInvoice.getOriginNo(),
							custInvoice.getDebit(), custInvoice.getCredit(), custInvoice.getCumulativeBalance(),
							custInvoice.getRef2(), invoiceAcknowledgementMap.get(invoiceDocEntry));

					invoiceDetailsList.add(invoicesDetails);
				}
			}
		}

		for (InvoiceDetailsNewTo custInvoice : custOtherInvociesList) {

			int invoiceDocEntry = custInvoice.getInvoiceDocEntry();
			long paymentDueDays = 0L;
			long dueDateInDays = 0L;
			String invoiceAmountInWords = null;
			String taxAmountInWords = null;
			List<InvoiceItems> invoiceItemsListFromMap = null;
			String invoiceDate = custInvoice.getPostingDate();
			String invoiceDueDate = custInvoice.getDueDate();

			Float balanceAmt = Float.parseFloat(custInvoice.getBalance());

			String custCode = custInvoice.getCustCode();
			InvoicesDetails invoicesDetails = new InvoicesDetails(invoiceDocEntry,
					Integer.toString(custInvoice.getInvoiceNo()), invoiceDate, invoiceDueDate, paymentDueDays,
					custInvoice.getInvoiceStatus(), balanceAmt, custCode, "", custInvoice.getOrigin(),
					invoiceItemsListFromMap, 0F, balanceAmt, "", dueDateInDays, invoiceAmountInWords, taxAmountInWords,
					custInvoice.getTransId(), custInvoice.getOriginNo(), custInvoice.getDebit(),
					custInvoice.getCredit(), custInvoice.getCumulativeBalance(), custInvoice.getRef2(), null);

			invoiceDetailsList.add(invoicesDetails);
		}

		return invoiceDetailsList;
	}

	// private List<InvoicesAcknowledgementDetails>
	// getInvoicesAcknowledgementListForDocEntries(
	// List<Integer> invoiceDocEntriesList) {
	//
	// String invoiceIdsInString =
	// getInvoiceDocIdsInString(invoiceDocEntriesList);
	//
	//
	// }

	@Autowired
	private InvoiceAcknowledgementDetailsRepository invoiceAcknowledgementDetailsRepository;

	@Override
	public BaseWrapper doSaveInvoiceAcknowledgement(int invoiceNo,
			InvoicesAcknowledgementDetails invoiceAcknowledgementDetails) {

		InvoicesAcknowledgementDetails invoicesAcknowledgementDetailsByInvoiceNo = invoiceAcknowledgementDetailsRepository
				.findByInvoiceNo(invoiceNo);

		if (null != invoicesAcknowledgementDetailsByInvoiceNo)
			invoiceAcknowledgementDetails.setInvoicesAcknowledgementDtlsId(
					invoicesAcknowledgementDetailsByInvoiceNo.getInvoicesAcknowledgementDtlsId());

		invoiceAcknowledgementDetails.setInvoiceNo(invoiceNo);

		Date currentDate = new Date();

		DateFormat sdfDt = new SimpleDateFormat("ddMMYY");
		sdfDt.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));
		String currentDt = sdfDt.format(currentDate);
		invoiceAcknowledgementDetails.setCreatedDt(currentDt);

		DateFormat sdfTm = new SimpleDateFormat("HHmmss");
		sdfTm.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));
		String currentTm = sdfTm.format(currentDate);
		invoiceAcknowledgementDetails.setCreatedTime(currentTm);

		invoiceAcknowledgementDetails = invoiceAcknowledgementDetailsRepository.save(invoiceAcknowledgementDetails);

		return new BaseWrapper(invoiceAcknowledgementDetails.getInvoicesAcknowledgementDtlsId());
	}

	@Autowired
	private CustomersPinRepository customersPinRepository;

	@Autowired
	private MessageUtility messageUtility;

	@Override
	public BaseWrapper doGenerateCustomerPin(String custCode, String contactNo)
			throws NumberFormatException, Exception {

		CustomerPin customerPin = customersPinRepository.findByCardCode(custCode);
		String pin = "";

		if (null == customerPin) {
			pin = commonUtility.getOTP();

			customerPin = new CustomerPin(custCode, pin, commonUtility.getDtInDDMMYYFormatIST(),
					commonUtility.getTsInHHmmssFormatIST());

			customerPin = customersPinRepository.save(customerPin);
		}

		pin = customerPin.getCustomersPin();

		contactNo = commonUtility.formatMobileNoToSendOtp(contactNo, true);
		if (messageUtility.sendOTP(pin, contactNo))
			return new BaseWrapper(customerPin);
		else
			throw new ServicesException("804");
	}

	@Override
	public BaseWrapper doVerifyCustomerPin(CustomerPin customerPin) throws ServicesException {

		CustomerPin customerPin2 = customersPinRepository
				.findByCardCodeAndCustomersPin(customerPin.getCardCode().trim(), customerPin.getCustomersPin().trim());

		if (null == customerPin2)
			throw new ServicesException("803");

		return new BaseWrapper();
	}

	@Override
	public BaseWrapper doSaveOfflineInvoiceAcknowledgement(
			List<InvoicesAcknowledgementDetails> invoiceAcknowledgementDetailsList) {

		if (invoiceAcknowledgementDetailsList.size() > 0) {

			List<Integer> invoiceNosList = new ArrayList<Integer>();
			for (InvoicesAcknowledgementDetails invoicesAcknowledgementDetails : invoiceAcknowledgementDetailsList)
				invoiceNosList.add(invoicesAcknowledgementDetails.getInvoiceNo());

			List<Integer> existingInvoiceNosList = invoiceAcknowledgementDetailsRepository
					.selectExistingInvoiceFrom(invoiceNosList);

			List<InvoicesAcknowledgementDetails> invoiceAcknowledgementDetailsListFinal = new ArrayList<InvoicesAcknowledgementDetails>();
			String createdDt = commonUtility.getDtInDDMMYYFormatIST();
			String createdTs = commonUtility.getTsInHHmmssFormatIST();

			for (InvoicesAcknowledgementDetails invoicesAcknowledgementDetails : invoiceAcknowledgementDetailsList) {

				if (!existingInvoiceNosList.contains(invoicesAcknowledgementDetails.getInvoiceNo())) {
					invoicesAcknowledgementDetails.setCreatedDt(createdDt);
					invoicesAcknowledgementDetails.setCreatedTime(createdTs);

					invoiceAcknowledgementDetailsListFinal.add(invoicesAcknowledgementDetails);
				}
			}

			if (invoiceAcknowledgementDetailsListFinal.size() > 0) {
				invoiceAcknowledgementDetailsRepository.save(invoiceAcknowledgementDetailsListFinal);
			}
		}

		return new BaseWrapper();
	}

	@Override
	public List<InvoicesDetails> getInvoiceAcknowledgments()
			throws ParseException, ClassNotFoundException, SQLException {

		List<Integer> invoiceIdsList = invoiceAcknowledgementDetailsRepository.selectAcknowledgedInvoiceNos();

		List<InvoicesDetails> invoiceDetailsList;

		if (invoiceIdsList.size() > 0) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date fromDate = df.parse("2019-04-01");
			invoiceDetailsList = getInvoiceDetailsByInvoiceIds(invoiceIdsList, fromDate, new Date());
		} else {
			invoiceDetailsList = new ArrayList<InvoicesDetails>();
		}

		return invoiceDetailsList;
	}

	public List<InvoicesDetails> getInvoiceDetailsByInvoiceIds(List<Integer> invoiceIdsList, Date fromDate,
			Date tillDate) throws ClassNotFoundException, SQLException, ParseException {

		DateFormat dfYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
		dfYYYYMMDD.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));

		String tillDateFormatted = dfYYYYMMDD.format(tillDate);
		String fromDateFormatted = dfYYYYMMDD.format(fromDate);

		String invoiceIdCommaSeparated = "";
		int invoiceIdListSize = invoiceIdsList.size();

		for (int i = 0; i < invoiceIdListSize; i++) {

			invoiceIdCommaSeparated = invoiceIdCommaSeparated + "'" + invoiceIdsList.get(i) + "'";

			if ((i + 1) < invoiceIdListSize)
				invoiceIdCommaSeparated = invoiceIdCommaSeparated + ",";
		}

		String custAllInvoicesQuery = "Select T1.CardCode As CardCode, ISNULL(T5.DocEntry, '0') As 'DocEntry', ISNULL(T5.DocNum, '0') As 'Invoice No.', T5.DocStatus As 'Invoice Status', "
				+ "T0.TransId ,T0.RefDate As'Posting Date',T0.DueDate, " + "(Select  " + "(Case  "
				+ "When T2.TransType=13 Then 'IN' " + "When T2.TransType=-2 Then 'OB'  "
				+ "When T2.TransType=24 Then 'RC'  " + "When T2.TransType=30 Then 'JE'  "
				+ "When T2.TransType=321 Then 'M.Reco'  " + "When T2.TransType=18 Then 'CN'  " + "Else 'Other'  "
				+ "End)  " + "From OJDT T2 Where T2.TransId =T0.TransId) As 'Origin', "
				+ "T0.Ref1 As'Origin No', T0.Ref2 As'Ref2', T0.Debit As 'Debit',T0.Credit As'Credit', " + "((select  "
				+ "sum(T4.debit)-sum(T4.Credit) from OJDT T3 INNER JOIN JDT1 T4 on T3.TransId=T4.TransId " + "WHERE  "
				+ "T4.ShortName=T0.ShortName and " + "T3.TransId<=T0.TransId " + ")) 'Cumulative Balance', "
				+ "(T0.BALDUEDEB - T0.BALDUECRED) as 'Balance Due' "
				+ "From JDT1 T0 INNER JOIN OCRD T1 ON T1.CardCode=T0.ShortName "
				+ "LEFT JOIN OINV T5 ON T0.Ref1=T5.DocNum  " + "Where T0.RefDate >='" + fromDateFormatted
				+ "' and T0.RefDate <='" + tillDateFormatted + "' And T5.DocNum IN (" + invoiceIdCommaSeparated + ")"
				+ "  And T1.CardType = 'C' And T0.TransType = 13 order by [Posting Date],TransId";

		System.out.println("Final All Invoices Query = " + custAllInvoicesQuery);

		Connection con = commonUtility.getDbConnection();
		PreparedStatement ps = con.prepareStatement(custAllInvoicesQuery);

		ResultSet rs = ps.executeQuery();

		List<InvoiceDetailsNewTo> custAllInvociesList = new ArrayList<InvoiceDetailsNewTo>();
		List<Integer> invoiceDocEntriesList = new ArrayList<Integer>();

		while (rs.next()) {
			String invoiceType = rs.getString("Origin");
			int invoiceNo = rs.getInt("Invoice No.");

			InvoiceDetailsNewTo invoicesDetails = new InvoiceDetailsNewTo(invoiceNo, rs.getString("Invoice Status"),
					rs.getString("TransId"), rs.getString("Posting Date"), rs.getString("DueDate"), invoiceType,
					rs.getString("Origin No"), Double.toString(commonUtility.round(rs.getDouble("Debit"), 2)),
					Double.toString(commonUtility.round(rs.getDouble("Credit"), 2)),
					Double.toString(commonUtility.round(rs.getDouble("Cumulative Balance"), 2)),
					rs.getString("Balance Due"), rs.getInt("DocEntry"), rs.getString("Ref2"), rs.getString("CardCode"));

			if (invoiceType.equalsIgnoreCase("IN") && invoiceNo != 0) {
				invoiceDocEntriesList.add(invoiceNo);
			}

			custAllInvociesList.add(invoicesDetails);
		}

		// System.out.println(invoiceDocEntriesList.toString());

		List<InvoiceItems> invoiceItemsList;
		Map<Integer, List<InvoiceItems>> invoiceItemsMap;
		List<InvoicesDetails> invoiceDetailsList = new ArrayList<InvoicesDetails>();
		List<InvoicesAcknowledgementDetails> invoicesAcknowledgementDetailsList;

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

			// Get Acknowledgement Details Of All Invoices Ids and set signature
			// of each against their invoice id
			invoicesAcknowledgementDetailsList = invoiceAcknowledgementDetailsRepository
					.selectByInvoiceNos(invoiceDocEntriesList);

			Map<Integer, String> invoiceAcknowledgementMap = new HashMap<Integer, String>();
			for (InvoicesAcknowledgementDetails invoicesAcknowledgementDetails : invoicesAcknowledgementDetailsList)
				invoiceAcknowledgementMap.put(invoicesAcknowledgementDetails.getInvoiceNo(),
						invoicesAcknowledgementDetails.getSignature());

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));

			DateFormat dfDDMMMYYY = new SimpleDateFormat("dd MMM yyyy");
			df.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));

			String currentDate = df.format(new Date());
			NumberToWord numberToWord = new NumberToWord();
			for (InvoiceDetailsNewTo custInvoice : custAllInvociesList) {

				int invoiceDocEntry = custInvoice.getInvoiceDocEntry();
				long paymentDueDays = 0L;
				long dueDateInDays = 0L;
				String invoiceAmountInWords = null;
				String taxAmountInWords = null;
				List<InvoiceItems> invoiceItemsListFromMap = null;
				String invoiceDate = custInvoice.getPostingDate();
				String invoiceDueDate = custInvoice.getDueDate();

				Float balanceAmt = Float.parseFloat(custInvoice.getBalance());
				if (invoiceDocEntry != 0 && custInvoice.getOrigin().equalsIgnoreCase("IN")) {

					// System.out.println("Invoice Doc Entry In Iteration : " +
					// invoiceDocEntry);

					paymentDueDays = commonUtility.getDaysDiffBetweenDates(invoiceDate, invoiceDueDate);
					dueDateInDays = commonUtility.getDaysDiffBetweenDates(currentDate, invoiceDueDate);

					invoiceItemsListFromMap = invoiceItemsMap.get(invoiceDocEntry);
					// System.out.println("invoiceItemsListFromMap = " +
					// invoiceItemsListFromMap);

					invoiceAmountInWords = numberToWord.convert(Math.round(balanceAmt));

					float finalTaxAmount = 0F;

					if (null != invoiceItemsListFromMap) {
						for (InvoiceItems invoiceItems : invoiceItemsListFromMap)
							finalTaxAmount = finalTaxAmount + invoiceItems.getCgstTax() + invoiceItems.getSgstTax();
					}
					taxAmountInWords = numberToWord.convert((int) Math.floor(finalTaxAmount));

				}

				String custCode = custInvoice.getCustCode();
				InvoicesDetails invoicesDetails = new InvoicesDetails(invoiceDocEntry,
						Integer.toString(custInvoice.getInvoiceNo()), dfDDMMMYYY.format(df.parse(invoiceDate)),
						dfDDMMMYYY.format(df.parse(invoiceDueDate)), paymentDueDays, custInvoice.getInvoiceStatus(),
						balanceAmt, custCode, "", custInvoice.getOrigin(), invoiceItemsListFromMap, 0F, balanceAmt, "",
						dueDateInDays, invoiceAmountInWords, taxAmountInWords, custInvoice.getTransId(),
						custInvoice.getOriginNo(), custInvoice.getDebit(), custInvoice.getCredit(),
						custInvoice.getCumulativeBalance(), custInvoice.getRef2(),
						invoiceAcknowledgementMap.get(invoiceDocEntry));

				invoiceDetailsList.add(invoicesDetails);
			}
		}

		return invoiceDetailsList;
	}

	@Override
	public InvoicesDetails getInvoiceDetailsByInvoiceId(String invoiceId)
			throws ServicesException, ClassNotFoundException, SQLException, ParseException {

		String custInvoicesDetailsQuery = "Select T1.CardCode As CardCode, ISNULL(T5.DocEntry, '0') As 'DocEntry', ISNULL(T5.DocNum, '0') As 'Invoice No.', T5.DocStatus As 'Invoice Status', "
				+ "T0.TransId ,T0.RefDate As'Posting Date',T0.DueDate, " + "(Select  " + "(Case  "
				+ "When T2.TransType=13 Then 'IN' " + "When T2.TransType=-2 Then 'OB'  "
				+ "When T2.TransType=24 Then 'RC'  " + "When T2.TransType=30 Then 'JE'  "
				+ "When T2.TransType=321 Then 'M.Reco'  " + "When T2.TransType=18 Then 'CN'  " + "Else 'Other'  "
				+ "End)  " + "From OJDT T2 Where T2.TransId =T0.TransId) As 'Origin', "
				+ "T0.Ref1 As'Origin No', T0.Ref2 As'Ref2', T0.Debit As 'Debit',T0.Credit As'Credit', " + "((select  "
				+ "sum(T4.debit)-sum(T4.Credit) from OJDT T3 INNER JOIN JDT1 T4 on T3.TransId=T4.TransId " + "WHERE  "
				+ "T4.ShortName=T0.ShortName and " + "T3.TransId<=T0.TransId " + ")) 'Cumulative Balance', "
				+ "(T0.BALDUEDEB - T0.BALDUECRED) as 'Balance Due' "
				+ "From JDT1 T0 INNER JOIN OCRD T1 ON T1.CardCode=T0.ShortName "
				+ "LEFT JOIN OINV T5 ON T0.Ref1=T5.DocNum Where T5.DocNum=" + invoiceId + " And T1.CardType = 'C'";

		System.out.println("Final custInvoicesDetailsQuery = " + custInvoicesDetailsQuery);

		Connection con = commonUtility.getDbConnection();
		PreparedStatement ps = con.prepareStatement(custInvoicesDetailsQuery);

		ResultSet rs = ps.executeQuery();

		InvoiceDetailsNewTo invoicesDetailsNewTo = null;

		if (rs.next()) {
			String invoiceType = rs.getString("Origin");
			int invoiceNo = rs.getInt("Invoice No.");

			invoicesDetailsNewTo = new InvoiceDetailsNewTo(invoiceNo, rs.getString("Invoice Status"),
					rs.getString("TransId"), rs.getString("Posting Date"), rs.getString("DueDate"), invoiceType,
					rs.getString("Origin No"), Double.toString(commonUtility.round(rs.getDouble("Debit"), 2)),
					Double.toString(commonUtility.round(rs.getDouble("Credit"), 2)),
					Double.toString(commonUtility.round(rs.getDouble("Cumulative Balance"), 2)),
					rs.getString("Balance Due"), rs.getInt("DocEntry"), rs.getString("Ref2"), rs.getString("CardCode"));
		} else
			throw new ServicesException("Error Finding Invoice");

		List<InvoiceItems> invoiceItemsList;
		Map<Integer, List<InvoiceItems>> invoiceItemsMap;
		List<InvoicesAcknowledgementDetails> invoicesAcknowledgementDetailsList;

		List<Integer> invoiceDocEntriesList = new ArrayList<Integer>();
		invoiceDocEntriesList.add(invoicesDetailsNewTo.getInvoiceNo());

		InvoicesDetails invoicesDetails = null;
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

			// Get Acknowledgement Details Of All Invoices Ids and set signature
			// of each against their invoice id
			invoicesAcknowledgementDetailsList = invoiceAcknowledgementDetailsRepository
					.selectByInvoiceNos(invoiceDocEntriesList);

			Map<Integer, String> invoiceAcknowledgementMap = new HashMap<Integer, String>();
			for (InvoicesAcknowledgementDetails invoicesAcknowledgementDetails : invoicesAcknowledgementDetailsList)
				invoiceAcknowledgementMap.put(invoicesAcknowledgementDetails.getInvoiceNo(),
						invoicesAcknowledgementDetails.getSignature());

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));

			DateFormat dfDDMMMYYY = new SimpleDateFormat("dd MMM yyyy");
			df.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));

			String currentDate = df.format(new Date());
			NumberToWord numberToWord = new NumberToWord();

			int invoiceDocEntry = invoicesDetailsNewTo.getInvoiceDocEntry();
			long paymentDueDays = 0L;
			long dueDateInDays = 0L;
			String invoiceAmountInWords = null;
			String taxAmountInWords = null;
			List<InvoiceItems> invoiceItemsListFromMap = null;
			String invoiceDate = invoicesDetailsNewTo.getPostingDate();
			String invoiceDueDate = invoicesDetailsNewTo.getDueDate();

			Float balanceAmt = Float.parseFloat(invoicesDetailsNewTo.getBalance());
			if (invoiceDocEntry != 0 && invoicesDetailsNewTo.getOrigin().equalsIgnoreCase("IN")) {

				// System.out.println("Invoice Doc Entry In Iteration : " +
				// invoiceDocEntry);

				paymentDueDays = commonUtility.getDaysDiffBetweenDates(invoiceDate, invoiceDueDate);
				dueDateInDays = commonUtility.getDaysDiffBetweenDates(currentDate, invoiceDueDate);

				invoiceItemsListFromMap = invoiceItemsMap.get(invoiceDocEntry);
				// System.out.println("invoiceItemsListFromMap = " +
				// invoiceItemsListFromMap);

				invoiceAmountInWords = numberToWord.convert(Math.round(balanceAmt));

				float finalTaxAmount = 0F;

				if (null != invoiceItemsListFromMap) {
					for (InvoiceItems invoiceItems : invoiceItemsListFromMap)
						finalTaxAmount = finalTaxAmount + invoiceItems.getCgstTax() + invoiceItems.getSgstTax();
				}
				taxAmountInWords = numberToWord.convert((int) Math.floor(finalTaxAmount));

			}

			String custCode = invoicesDetailsNewTo.getCustCode();
			invoicesDetails = new InvoicesDetails(invoiceDocEntry,
					Integer.toString(invoicesDetailsNewTo.getInvoiceNo()), dfDDMMMYYY.format(df.parse(invoiceDate)),
					dfDDMMMYYY.format(df.parse(invoiceDueDate)), paymentDueDays,
					invoicesDetailsNewTo.getInvoiceStatus(), balanceAmt, custCode, "", invoicesDetailsNewTo.getOrigin(),
					invoiceItemsListFromMap, 0F, balanceAmt, "", dueDateInDays, invoiceAmountInWords, taxAmountInWords,
					invoicesDetailsNewTo.getTransId(), invoicesDetailsNewTo.getOriginNo(),
					invoicesDetailsNewTo.getDebit(), invoicesDetailsNewTo.getCredit(),
					invoicesDetailsNewTo.getCumulativeBalance(), invoicesDetailsNewTo.getRef2(),
					invoiceAcknowledgementMap.get(invoiceDocEntry));

			float invoiceItemTotal = 0F;
			float invoiceItemCgstTotal = 0F;
			float invoiceItemSgstTotal = 0F;
			float invoiceItemRoundOffTotal = 0F;
			float totalTax = 0F;

			List<InvoiceItems> invoiceItemsList1 = invoicesDetails.getInvoiceItemsList();

			for (InvoiceItems invoiceItems : invoiceItemsList1) {
				invoiceItemTotal = invoiceItemTotal + invoiceItems.getTotal();
				invoiceItemCgstTotal = invoiceItemCgstTotal + invoiceItems.getCgstTax();
				invoiceItemSgstTotal = invoiceItemSgstTotal + invoiceItems.getSgstTax();
				invoiceItemRoundOffTotal = invoiceItemRoundOffTotal + invoiceItems.getRoundDif();
			}

			totalTax = invoiceItemCgstTotal + invoiceItemSgstTotal;

			if (invoicesDetails.getGrossTotal() == 0F) {
				invoicesDetails.setGrossTotal(
						invoiceItemTotal + invoiceItemCgstTotal + invoiceItemSgstTotal + invoiceItemRoundOffTotal);
			}

			invoicesDetails.setTotalTax(Float.toString(totalTax));
		} else {
			invoicesDetails = new InvoicesDetails();
		}

		return invoicesDetails;
	}

	@Override
	public BaseWrapper doGetCustomersSummaryReport() throws ClassNotFoundException, SQLException {

		List<CutomerSummaryReportDetails> cutomerSummaryReportDetailsList = new ArrayList<CutomerSummaryReportDetails>();

		if (commonUtility.hasRole(RoleType.ROLE_SALES.toString())) {
			String slpCode = oslpRepository.selectSlpCodeByMobil(commonUtility.getLoggedUser().getUsername());
			cutomerSummaryReportDetailsList = getCustomerSummaryReportDetails(slpCode);
		} else if (commonUtility.hasRole(RoleType.ROLE_ADMIN.toString())) {
			cutomerSummaryReportDetailsList = getCustomerSummaryReportDetails(null);
		}

		return new BaseWrapper(cutomerSummaryReportDetailsList);
	}
}
