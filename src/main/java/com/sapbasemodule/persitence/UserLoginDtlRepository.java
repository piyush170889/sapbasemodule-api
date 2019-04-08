package com.sapbasemodule.persitence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sapbasemodule.domain.UserLoginDtl;

@Repository
public interface UserLoginDtlRepository extends JpaRepository<UserLoginDtl, Integer> {

	UserLoginDtl findByContactNum(String mobileNo);

	@Modifying
	@Query("update UserLoginDtl u set u.contactNum=?1 where u.userDtl.userDtlsId=?2")
	void updateContactNumber(String contactNum, String loggedUserId);

	@Query("select u from UserLoginDtl u where u.userDtl.userDtlsId=?1")
	UserLoginDtl findByUserDtlsId(String loggedUserId);

	@Modifying
	@Query("update UserLoginDtl u set u.password=?2, u.forgotPassCode=null where u.forgotPassCode=?1")
	int updatePassword(String forgetPasswordCode, String password);

	@Modifying
	@Query("update UserLoginDtl u set u.forgotPassCode=?1 where u.userLoginDtlsId=?2")
	void updateForgetPasswordCode(String forgetPasswordCode, String userLoginDtlsId);

	@Modifying
	@Query("update UserLoginDtl u set u.password=?1 where u.userDtl.userDtlsId=?2")
	void updatePasswordByUserDtlsId(String password, String loggedUserId);

	@Modifying
	@Query("update UserLoginDtl u set u.password=?1 where u.userLoginDtlsId=?2")
	void updatePasswordByUserLoginDtlsId(String password, String userLoginDtlsId);

	UserLoginDtl findByUsername(String username);

	@Query("select u from UserLoginDtl u where u.userDtl.emailId=?1")
	UserLoginDtl selectUsersByEmailId(String recoveryContact);

	@Query("select u.password from UserLoginDtl u where u.userDtl.userDtlsId=?1")
	String selectUserPassByUserDtlsId(String loggedInUserId);

	@Query("select u from UserLoginDtl u where u.userLoginDtlsId IN (?1) order by u.createdTs desc")
	List<UserLoginDtl> findByLoginDtlsIdList(List<String> adminUserLoginIds);

}
