package com.sapbasemodule.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sapbasemodule.domain.CustomerAddresses;
import com.sapbasemodule.domain.Customers;
import com.sapbasemodule.model.AgingDetails;
import com.sapbasemodule.model.BaseWrapper;
import com.sapbasemodule.model.CustomerDetailsWrapper;
import com.sapbasemodule.model.PaginationDetails;
import com.sapbasemodule.persitence.CustomersAddressesRepository;
import com.sapbasemodule.persitence.CustomersRepository;
import com.sapbasemodule.utils.CommonUtility;

@Service
@Transactional(rollbackFor = Throwable.class)
public class CustomersServiceImpl implements CustomersService {

	@Autowired
	private CustomersRepository customersRepository;

	@Autowired
	private CustomersAddressesRepository customersAddressesRepository;

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

			CustomerDetailsWrapper customerDetailsWrapper = new CustomerDetailsWrapper(customers, customerAddressesList);

			response.add(customerDetailsWrapper);
		}

		return response;
	}

	@Override
	public BaseWrapper doGetCustomerAgingReport(String custCode, String fromDate) throws ClassNotFoundException, SQLException, ParseException {

		// TODO: Uncomment Below Section
//		Connection conn = getDbConnection();
//
//		String fromDateFormatted = new SimpleDateFormat("yyyyMMdd").format(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate));
//		
//		String sqlQuery = "select T1.ShortName,(select a1.CardName from OCRD a1 where a1.CardCode=T1.ShortName)'CardName' ,"
//				+ "(SUM(T1.BalDueDeb)-SUM(T1.BalDueCred))'BalanceDue', "
//				+ "(select case when (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) is null then 0 else (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) end from JDT1 a1 where a1.ShortName=T1.ShortName and DATEDIFF(DAY,a1.DueDate, '" + fromDateFormatted + "' )>=0 and DATEDIFF(DAY,a1.DueDate, '" + fromDateFormatted + "' )<=30)'FirstQ',"
//				+ "(select case when (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) is null then 0 else (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) end from JDT1 a1 where a1.ShortName=T1.ShortName and DATEDIFF(DAY,a1.DueDate, '" + fromDateFormatted + "' )>30 and DATEDIFF(DAY,a1.DueDate, '" + fromDateFormatted + "' )<=60)'SecondQ',"
//				+ "(select case when (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) is null then 0 else (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) end from JDT1 a1 where a1.ShortName=T1.ShortName and DATEDIFF(DAY,a1.DueDate, '" + fromDateFormatted + "' )>60 and DATEDIFF(DAY,a1.DueDate, '" + fromDateFormatted + "' )<=90)'ThirdQ',"
//				+ "(select case when (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) is null then 0 else (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) end from JDT1 a1 where a1.ShortName=T1.ShortName and DATEDIFF(DAY,a1.DueDate, '" + fromDateFormatted + "' )>90 and DATEDIFF(DAY,a1.DueDate, '" + fromDateFormatted + "' )<=120)'FourthQ',"
//				+ "(select case when (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) is null then 0 else (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) end from JDT1 a1 where a1.ShortName=T1.ShortName and DATEDIFF(DAY,a1.DueDate, '" + fromDateFormatted + "' )>120)'OtherQ' "
//				+ "from OJDT T0 inner join JDT1 T1 on T0.TransId=T1.TransId where "
//				+ "T0.DueDate<= '" + fromDateFormatted + "' and T1.ShortName= '" + custCode + "' group by T1.ShortName "
//				+ "having T1.ShortName in (select a1.CardCode from OCRD a1 where a1.CardType='C') and (SUM(T1.BalDueDeb)-SUM(T1.BalDueCred))>0 "
//				+ "order by T1.ShortName";
//		
//		System.out.println("Final SQL = " + sqlQuery);
//		
//		PreparedStatement ps = conn.prepareStatement(sqlQuery);
//		ResultSet rs = ps.executeQuery();
//
//		AgingDetails agingDetails = null;
//		if(rs.next())
//			agingDetails = new AgingDetails(custCode, rs.getString("CardName"), rs.getString("BalanceDue"), rs.getString("FirstQ"), rs.getString("SecondQ"), 
//					rs.getString("ThirdQ"), rs.getString("FourthQ"), rs.getString("OtherQ"));
//		else
//			agingDetails = new AgingDetails(custCode, "", "0", "0", "0", "0","0","0");

		AgingDetails agingDetails = new AgingDetails(custCode, "Piyush", "60000", "20000", "30000", "40000","50000","60000");
		
		return new BaseWrapper(agingDetails);
	}

	
	private Connection getDbConnection() throws ClassNotFoundException, SQLException {

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Adit_Infra_Final", 
				"sa", "replete@123");
		
		return conn;
	}
}
