package com.sapbasemodule.persitence;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapbasemodule.domain.SiteVisitHistory;

@Repository
public interface SiteVisitHistoryRepository extends JpaRepository<SiteVisitHistory, Integer> {

	List<SiteVisitHistory> findByVisitorId(String visitorId);

	List<SiteVisitHistory> findBySiteVisitHistoryIdIn(Set<Integer> siteVisitHistoryIdsSet);

}
