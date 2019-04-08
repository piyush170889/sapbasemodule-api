package com.sapbasemodule.persitence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sapbasemodule.domain.NormalPacketDescDtls;

@Repository
public interface NormalPacketDescDtlsRepository extends JpaRepository<NormalPacketDescDtls, Integer> {

//	@Query("select npdd from NormalPacketDescDtls npdd where npdd.imei=?2 and DATE_FORMAT(convert_tz(npdd.createdTs,'+00:00','+05:30'), '%Y-%m-%d') = ?1 order by STR_TO_DATE(npdd.utcDt, '%d%m%y'),CONVERT(npdd.utcTm, TIME) asc")
	@Query("select npdd from NormalPacketDescDtls npdd where npdd.imei=?2 and DATE_FORMAT(convert_tz(concat(STR_TO_DATE(UTC_DT, '%d%m%y'),' ', CONVERT(UTC_TM, TIME)),'+00:00','+05:30'), '%Y-%m-%d') = ?1 order by STR_TO_DATE(npdd.utcDt, '%d%m%y'),CONVERT(npdd.utcTm, TIME) asc")
	List<NormalPacketDescDtls> findAllTrackingDataByDateAndImei(String trackDate, String imeiNo, Pageable pageRequest);

//	@Query("select COALESCE(count(npdd), 0) from NormalPacketDescDtls npdd where npdd.imei=?2 and DATE_FORMAT(convert_tz(npdd.createdTs,'+00:00','+05:30'), '%Y-%m-%d') = ?1")
	@Query("select COALESCE(count(npdd), 0) from NormalPacketDescDtls npdd where npdd.imei=?2 and DATE_FORMAT(convert_tz(concat(STR_TO_DATE(UTC_DT, '%d%m%y'),' ', CONVERT(UTC_TM, TIME)),'+00:00','+05:30'), '%Y-%m-%d') = ?1")
	int countAllTrackingDataByDateAndImei(String trackDate, String imeiNo);

//	@Query("select new NormalPacketDescDtls(npdd.latitude, npdd.longitude) from NormalPacketDescDtls npdd where npdd.imei=?2 and DATE_FORMAT(convert_tz(npdd.createdTs,'+00:00','+05:30'), '%Y-%m-%d') = ?1 order by STR_TO_DATE(npdd.utcDt, '%d%m%y'),CONVERT(npdd.utcTm, TIME) asc")
	@Query("select new NormalPacketDescDtls(npdd.latitude, npdd.longitude) from NormalPacketDescDtls npdd where npdd.imei=?2 and DATE_FORMAT(convert_tz(concat(STR_TO_DATE(UTC_DT, '%d%m%y'),' ', CONVERT(UTC_TM, TIME)),'+00:00','+05:30'), '%Y-%m-%d') = ?1 order by STR_TO_DATE(npdd.utcDt, '%d%m%y'),CONVERT(npdd.utcTm, TIME) asc")
	List<NormalPacketDescDtls> selectLatLongByImeiNoAndDate(String date, String imeiNo);
	
//	@Query("select new NormalPacketDescDtls(npdd.latitude, npdd.longitude) from NormalPacketDescDtls npdd where npdd.imei=?3 and DATE_FORMAT(npdd.createdTs, '%Y-%m-%d') >= ?1 and DATE_FORMAT(convert_tz(npdd.createdTs,'+00:00','+05:30'), '%Y-%m-%d') <= ?2 order by STR_TO_DATE(npdd.utcDt, '%d%m%y'),CONVERT(npdd.utcTm, TIME) asc")
	@Query("select new NormalPacketDescDtls(npdd.latitude, npdd.longitude) from NormalPacketDescDtls npdd where npdd.imei=?3 and DATE_FORMAT(npdd.createdTs, '%Y-%m-%d') >= ?1 and DATE_FORMAT(convert_tz(concat(STR_TO_DATE(UTC_DT, '%d%m%y'),' ', CONVERT(UTC_TM, TIME)),'+00:00','+05:30'), '%Y-%m-%d') <= ?2 order by STR_TO_DATE(npdd.utcDt, '%d%m%y'),CONVERT(npdd.utcTm, TIME) asc")
	List<NormalPacketDescDtls> selectLatLongByImeiNoAndBetwDates(String startDate, String endDate, String imeiNo);

//	@Query("select new NormalPacketDescDtls(npdd.latitude,npdd.longitude,npdd.utcTm,npdd.utcDt,npdd.digitalInputStatus) from NormalPacketDescDtls npdd where npdd.imei=?2 and DATE_FORMAT(convert_tz(npdd.createdTs,'+00:00','+05:30'), '%Y-%m-%d') = ?1 order by STR_TO_DATE(npdd.utcDt, '%d%m%y'),CONVERT(npdd.utcTm, TIME) asc")
	@Query("select new NormalPacketDescDtls(npdd.latitude,npdd.longitude,npdd.utcTm,npdd.utcDt,npdd.digitalInputStatus) from NormalPacketDescDtls npdd where npdd.imei=?2 and DATE_FORMAT(convert_tz(concat(STR_TO_DATE(UTC_DT, '%d%m%y'),' ', CONVERT(UTC_TM, TIME)),'+00:00','+05:30'), '%Y-%m-%d') = ?1 order by STR_TO_DATE(npdd.utcDt, '%d%m%y'),CONVERT(npdd.utcTm, TIME) asc")
	List<NormalPacketDescDtls> findTrackingDataByDateAndImei(String trackDate, String imeiNo);

	@Query("select npdd from NormalPacketDescDtls npdd where npdd.imei=?1 order by npdd.createdTs desc")
	List<NormalPacketDescDtls> selectLastPacketDtlsByImeiNo(String imeiNo, Pageable pageable);

	@Query("select COALESCE(count(npdd), 0) from NormalPacketDescDtls npdd where npdd.imei=?1 and npdd.utcDt=?2 and npdd.utcTm=?3")
	int getPacketCountByImeiNoUtcDtAndUtcTm(String imei, String utcDt, String utcTm);
	
}
