package com.spwosi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spwosi.entity.EligibilityDtls;

@Repository
public interface EligibilityDtlsRepo extends JpaRepository <EligibilityDtls, Integer> {

	@Query("select distinct planName from EligibilityDtls")
	public List<String> findPlanName();
	
	@Query("select distinct planStatus from eligibilityDtls")
	public List<String> findPlanStatus();
	
}
