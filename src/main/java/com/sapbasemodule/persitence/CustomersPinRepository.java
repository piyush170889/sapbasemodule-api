package com.sapbasemodule.persitence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapbasemodule.domain.CustomerPin;

@Repository
public interface CustomersPinRepository extends JpaRepository<CustomerPin, Integer> {

	CustomerPin findByCardCode(String custCode);

	CustomerPin findByCardCodeAndCustomersPin(String cardCode, String customersPin);

	List<CustomerPin> findByCardCodeIn(List<String> custIds);

}
