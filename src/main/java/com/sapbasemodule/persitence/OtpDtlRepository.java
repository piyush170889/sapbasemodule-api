package com.sapbasemodule.persitence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sapbasemodule.domain.OtpDtl;

@Repository
public interface OtpDtlRepository extends JpaRepository<OtpDtl, Integer>{

//	List<OtpDtl> findByCellnumberAndOtp(String cntcNum, int otp);

	List<OtpDtl> findByCellnumber(String cntcNum);

	@Query("select o from OtpDtl o where o.cellnumber=?1 and o.otp=?2 and o.deviceInfo=?3")
	List<OtpDtl> findByCellnumberAndOtpAndDeviceInfo(String cntcNum, int otp, String deviceInfo);

}
