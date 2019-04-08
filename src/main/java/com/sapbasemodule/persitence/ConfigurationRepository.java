package com.sapbasemodule.persitence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapbasemodule.domain.Configuration;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer>{

	Object findByConfigName(String configName);

}
