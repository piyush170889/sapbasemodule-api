package com.sapbasemodule.persitence;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sapbasemodule.domain.RoleMasterDtl;
import com.sapbasemodule.domain.UserRoleDtl;

@Repository
public interface UserRoleDtlRepository extends JpaRepository<UserRoleDtl, Integer>{

	Set<RoleMasterDtl> findByUserLoginDtl(String userLoginDtlsId);	//Required for security

	@Query("select new UserRoleDtl(urd.userLoginDtl.userDtl.userDtlsId,urd.userLoginDtl.userDtl.firstName,urd.userLoginDtl.userDtl.lastName,"
			+ "urd.userLoginDtl.contactNum,urd.userLoginDtl.userDtl.emailId,urd.userLoginDtl.roles) from UserRoleDtl urd where "
			+ "urd.id.rolesMasterDtlsId IN (?1) and urd.userLoginDtl.isActive=1 order by "
			+ "urd.userLoginDtl.userDtl.createdTs desc")
	List<UserRoleDtl> selectAllActiveUsersByRoles(List<String> roles, Pageable pageRequest);

	@Query("select urd.userLoginDtl.userLoginDtlsId from UserRoleDtl urd where "
			+ "urd.id.rolesMasterDtlsId IN (?1) and urd.userLoginDtl.isActive=1 order by "
			+ "urd.userLoginDtl.createdTs desc")
	List<String> selectUserLoginDtlsIdByRoles(List<String> roles, Pageable pageRequest);

	@Query("select urd from UserRoleDtl urd where urd.id.rolesMasterDtlsId=?1")
	List<UserRoleDtl> selectAllActiveUsersByRole(String role, Pageable pageRequest);

	@Query("select count(distinct urd.userLoginDtl.userLoginDtlsId) from UserRoleDtl urd where urd.id.rolesMasterDtlsId IN (?1) "
			+ "and urd.userLoginDtl.isActive=1 order by urd.userLoginDtl.userDtl.createdTs desc")
	double selectCountOfAllActiveUsersByRoles(List<String> roles);

	@Query("select urd.id.rolesMasterDtlsId from UserRoleDtl urd where urd.userLoginDtl.userDtl.userDtlsId=?1")
	List<String> selectAssignedRolesByUserId(String loggedInUserId);

	@Query("select urd from UserRoleDtl urd where urd.userLoginDtl.userLoginDtlsId=?1")
	List<UserRoleDtl> selectByUserLoginDtlId(String userLoginDtlId);

}
