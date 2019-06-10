package com.sapbasemodule.persitence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sapbasemodule.domain.OSLP;

@Repository
public interface OSLPRepository extends JpaRepository<OSLP, String> {

	@Query("select o.slpCode from OSLP o where o.mobil LIKE %?1")
	String selectSlpCodeByMobil(String mobilNo);

	
}
