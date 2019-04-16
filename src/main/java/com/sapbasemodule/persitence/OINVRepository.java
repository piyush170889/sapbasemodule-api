package com.sapbasemodule.persitence;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sapbasemodule.domain.OINV;

@Repository
public interface OINVRepository extends JpaRepository<OINV, Integer> {

	@Query("SELECT T0.docEntry,T0.docNum, T0.docDate, T0.docDueDate, T0.cardCode, T0.cardName, T0.docTotal, T0.docStatus FROM OINV T0 WHERE T0.cardCode = ?1 "
			+ "AND  T0.docDate >=?2 AND T0.docDate <=?3 and T0.docStatus IN (?4) ORDER BY T0.docStatus DESC")
	List<OINV> selectByCustCodeAndDates(String custCode, Date toDate, Date fromDate, List<String> docStatusList);

}
