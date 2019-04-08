package com.sapbasemodule.persitence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sapbasemodule.domain.UserDtl;

@Repository
public interface UserDtlRepository extends JpaRepository<UserDtl, String>{

	UserDtl findByUserDtlsId(String loggedUserId);

	@Modifying
	@Query("update UserDtl u set u.firstName=?1, u.lastName=?2 where u.userDtlsId=?3")
	void updateUserDtls(String firstName, String lastName, String loggedUserId);
	
	@Modifying
	@Query("update UserDtl u set u.changedEmail=?1, u.emailVerificationCode=?2 where u.userDtlsId=?3")
	void updateEmailId(String emailId, String emailVerificationCode, String loggedUserId );

	@Query("select u from UserDtl u where u.emailVerificationCode=?1")
	UserDtl findByEmailVerificationCode(String emailVerificationCode);

	@Modifying
	@Query("update UserDtl u set u.emailId=?1,u.changedEmail=null, u.emailVerificationCode=null where u.userDtlsId=?2")
	int updateChangedEmailIdToEmailId(String changedEmail, String userDtlsId);

	@Modifying
	@Query("update UserDtl u set u.img=?1 where u.userDtlsId=?2")
	int updateProfilePic(String contentUrl, String loggedUserId);

}
