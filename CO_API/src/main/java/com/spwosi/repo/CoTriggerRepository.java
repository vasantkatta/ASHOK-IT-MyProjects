package com.spwosi.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spwosi.entity.CoTriggerEntity;

public interface CoTriggerRepository extends JpaRepository<CoTriggerEntity, Serializable>{


	public List<CoTriggerEntity> findByTrgStatus(String status);

	public List<CoTriggerEntity> findByCaseNum(Long caseNum);

	
}
