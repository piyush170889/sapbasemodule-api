package com.sapbasemodule.persitence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapbasemodule.domain.OrderDeliveryDetails;

@Repository
public interface OrderDeliveryDetailsRepository extends JpaRepository<OrderDeliveryDetails, Integer> {

	List<OrderDeliveryDetails> findByAppOrdrDlvryId(int orderDlsId);

	List<OrderDeliveryDetails> findByAppOrdrId(int orderDlsId);

}
