package com.spwosi.plan.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spwosi.plan.entity.Plans;

public interface PlanRepo extends JpaRepository<Plans, Integer>{


	
}
