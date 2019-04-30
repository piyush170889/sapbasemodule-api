package com.sapbasemodule.persitence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapbasemodule.domain.AppOrders;

@Repository
public interface AppOrdersRepository extends JpaRepository<AppOrders, Integer> {

}
