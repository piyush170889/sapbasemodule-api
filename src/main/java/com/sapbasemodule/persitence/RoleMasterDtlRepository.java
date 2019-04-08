package com.sapbasemodule.persitence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sapbasemodule.domain.RoleMasterDtl;

@Repository
public interface RoleMasterDtlRepository extends JpaRepository<RoleMasterDtl, Integer>{

	List<RoleMasterDtl> findByIsActive(byte b);

	@Query("select rmd from RoleMasterDtl rmd where rmd.isActive=?1 and rmd.rolesMasterDtlsId NOT IN (?2)")
	List<RoleMasterDtl> findRolesMasterAndNotIn(byte isActive, String userLoginIdToSkip);

}
