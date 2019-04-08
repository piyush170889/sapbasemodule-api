package com.sapbasemodule.persitence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sapbasemodule.domain.ConfigurationDtls;

@Repository
public interface ConfigurationDtlsRepository extends JpaRepository<ConfigurationDtls, Integer> {

	List<ConfigurationDtls> findByIsActive(byte isActive);

	ConfigurationDtls findByConfigurationName(String configurationName);

	@Modifying
	@Query("update ConfigurationDtls cd set cd.configurationValue=?2 where cd.configurationDtlsId=?1")
	void updateConfigurationValue(int configDtlsId, String configurationValue);
}
