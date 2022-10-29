package com.spwosi.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spwosi.entity.DC_IncomeEntity;

public interface DC_IncomeRepo extends JpaRepository<DC_IncomeEntity, Serializable> {

	public DC_IncomeEntity findByCaseNum(Long caseNum);
	
}
