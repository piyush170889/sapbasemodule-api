package com.sapbasemodule.persitence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sapbasemodule.domain.SiteDtls;

@Repository
public interface SiteDtlsRepository extends JpaRepository<SiteDtls, Integer> {

	SiteDtls findByCardCode(String cardCode);

	@Query("select new SiteDtls(sd.cardCode, sd.geofenceName) from SiteDtls sd")
	List<SiteDtls> selectLocationsForPopulatingDD();

}
