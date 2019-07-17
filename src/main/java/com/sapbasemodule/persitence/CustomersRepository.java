package com.sapbasemodule.persitence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sapbasemodule.domain.Customers;
import com.sapbasemodule.model.AgingDetails;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, String> {

	// @Query("select c from Customers c where c.cardType='C' and
	// lower(c.cardCode) like ?1% or lower(c.cardName) like ?1% or c.phone1 like
	// ?1% or c.phone2 like ?1%")
	@Query("select c from Customers c where c.cardType='C' and lower(c.cardName) like ?1%")
	List<Customers> findCustomerBySearchTerm(String searchTerm);

	@Query(nativeQuery = true, value = "select T1.ShortName,(select a1.CardName from OCRD a1 where a1.CardCode=T1.ShortName)'CardName' ,"
			+ "(SUM(T1.BalDueDeb)-SUM(T1.BalDueCred))'BalanceDue', "
			+ "(select case when (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) is null then 0 else (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) end from JDT1 a1 where a1.ShortName=T1.ShortName and DATEDIFF(DAY,a1.DueDate, ?1 )>=0 and DATEDIFF(DAY,a1.DueDate, ?1 )<=30)'FirstQ',"
			+ "(select case when (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) is null then 0 else (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) end from JDT1 a1 where a1.ShortName=T1.ShortName and DATEDIFF(DAY,a1.DueDate, ?1 )>30 and DATEDIFF(DAY,a1.DueDate, ?1 )<=60)'SecondQ',"
			+ "(select case when (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) is null then 0 else (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) end from JDT1 a1 where a1.ShortName=T1.ShortName and DATEDIFF(DAY,a1.DueDate, ?1 )>60 and DATEDIFF(DAY,a1.DueDate, ?1 )<=90)'ThirdQ',"
			+ "(select case when (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) is null then 0 else (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) end from JDT1 a1 where a1.ShortName=T1.ShortName and DATEDIFF(DAY,a1.DueDate, ?1 )>90 and DATEDIFF(DAY,a1.DueDate, ?1 )<=120)'FourthQ',"
			+ "(select case when (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) is null then 0 else (SUM(a1.BalDueDeb)-SUM(a1.BalDueCred)) end from JDT1 a1 where a1.ShortName=T1.ShortName and DATEDIFF(DAY,a1.DueDate, ?1 )>120)'OtherQ' "
			+ "from OJDT T0 inner join JDT1 T1 on T0.TransId=T1.TransId where "
			+ "T0.DueDate<= ?1 and T1.ShortName= ?2 group by T1.ShortName "
			+ "having T1.ShortName in (select a1.CardCode from OCRD a1 where a1.CardType='C') and (SUM(T1.BalDueDeb)-SUM(T1.BalDueCred))>0 "
			+ "order by T1.ShortName")
	AgingDetails getAgingDetails(String date, String custCode);

	List<Customers> findByCardType(String customerType, Pageable pageRequest);

	List<Customers> findByCardType(String customerType);

	@Query("select SUM(c.balance) from Customers c where c.cardType=?1")
	Float getTotalCustomerOutstandingByType(String type);

	List<Customers> findByCardTypeAndSlpCode(String string, String slpCode);

	@Query("select c.cardName from Customers c where c.cardCode=?1")
	String findCardNameByCardCode(String cardCode);

	// List<Customers> findByCardCodeOrCardNameOrPhone1OrPhone2Containing(String
	// searchTerm);

}
