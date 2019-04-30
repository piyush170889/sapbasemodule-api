package com.sapbasemodule.persitence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapbasemodule.domain.AppOrderItem;

@Repository
public interface AppOrderItemRepository extends JpaRepository<AppOrderItem, Integer> {

	
}
