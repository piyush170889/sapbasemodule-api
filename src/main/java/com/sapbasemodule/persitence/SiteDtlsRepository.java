package com.sapbasemodule.persitence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapbasemodule.domain.SiteDtls;

@Repository
public interface SiteDtlsRepository extends JpaRepository<SiteDtls, Integer> {

}
