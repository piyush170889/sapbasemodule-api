package com.sapbasemodule.persitence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapbasemodule.domain.OrderItems;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {

	List<OrderItems> findByDocEntryIn(List<Integer> orderIdsList);

	List<OrderItems> findByDocEntry(int orderDtlsId);

}
