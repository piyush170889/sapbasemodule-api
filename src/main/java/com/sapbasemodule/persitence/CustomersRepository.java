package com.sapbasemodule.persitence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sapbasemodule.domain.Customers;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, String> {

	@Query("select c from Customers c where lower(c.cardCode) like %?1% or lower(c.cardName) like %?1% or c.phone1 like %?1% or c.phone2 like %?1%")
	List<Customers> findCustomerBySearchTerm(String searchTerm);

//	List<Customers> findByCardCodeOrCardNameOrPhone1OrPhone2Containing(String searchTerm);

}
