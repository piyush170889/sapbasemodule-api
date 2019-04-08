package com.sapbasemodule.persitence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sapbasemodule.domain.Master;

public interface MasterRepository extends JpaRepository<Master, Integer> {

	List<Master> findByIsActive(Byte byte1);

}
