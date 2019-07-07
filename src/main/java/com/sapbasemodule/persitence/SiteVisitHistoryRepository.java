package com.sapbasemodule.persitence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapbasemodule.domain.SiteVisitHistory;

@Repository
public interface SiteVisitHistoryRepository extends JpaRepository<SiteVisitHistory, Integer> {

	List<SiteVisitHistory> findByVisitorId(String visitorId);

}
