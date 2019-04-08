package com.sapbasemodule.persitence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapbasemodule.domain.NotificationDtls;

@Repository
public interface NotificationDtlsRepository extends JpaRepository<NotificationDtls, Integer> {

}
