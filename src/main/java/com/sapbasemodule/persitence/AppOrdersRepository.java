package com.sapbasemodule.persitence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sapbasemodule.domain.AppOrders;

@Repository
public interface AppOrdersRepository extends JpaRepository<AppOrders, Integer> {

	@Query("select count(ao) from AppOrders ao where ao.isSynched=?1")
	int countByIsSynched(byte isSynched);

	@Query("select ao from AppOrders ao where ao.isSynched=?1")
	List<AppOrders> findByIsSynched(byte isSynched);

}
