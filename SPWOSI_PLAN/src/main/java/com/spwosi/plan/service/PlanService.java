package com.spwosi.plan.service;

import java.util.List;
import java.util.Map;

import com.spwosi.plan.entity.Plans;


public interface PlanService {

	public Map<Integer, String>  getPlanCategories();
	
	public boolean savePlan(Plans plans);
	
	public List<Plans> getAllPlans();
	
	public Plans getPlanById(Integer planId);
	
	public boolean updatePlan(Plans plan);
	
	public boolean deletePlanById(Integer planId);
	
	public boolean planStatusChange(Integer planId, String status);
	
	
	
	
}
