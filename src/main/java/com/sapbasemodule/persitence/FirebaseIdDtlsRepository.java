package com.sapbasemodule.persitence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapbasemodule.domain.FirebaseIdDtls;

@Repository
public interface FirebaseIdDtlsRepository extends JpaRepository<FirebaseIdDtls, Integer> {

	List<FirebaseIdDtls> findByImeiNoIn(List<String> userDtlsIdList);

	FirebaseIdDtls findByImeiNo(String imeiNo);

}
