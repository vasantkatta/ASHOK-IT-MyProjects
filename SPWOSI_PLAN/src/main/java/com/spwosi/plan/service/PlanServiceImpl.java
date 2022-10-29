package com.spwosi.plan.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spwosi.plan.entity.PlanCategory;
import com.spwosi.plan.entity.Plans;
import com.spwosi.plan.repo.PlanCategoryRepo;
import com.spwosi.plan.repo.PlanRepo;

@Service
public class PlanServiceImpl implements PlanService{
	
	@Autowired
	private PlanRepo planRepo;
	
	@Autowired
	private PlanCategoryRepo planCategoryRepo;

	@Override
	public Map<Integer, String> getPlanCategories() {
		
		List<PlanCategory> categories = planCategoryRepo.findAll();
		
		Map<Integer, String> categoryMap = new HashMap<>();
		
		categories.forEach(category -> {
			categoryMap.put(category.getCategoryId(), category.getCategoryName());
		});
		
		return categoryMap;
	}

	@Override
	public boolean savePlan(Plans plans) {
		
		Plans saved = planRepo.save(plans);
		
		return saved.getPlanId()!=null;
	}

	@Override
	public List<Plans> getAllPlans() {
		
		return planRepo.findAll();
		
	}

	@Override
	public Plans getPlanById(Integer planId) {
		
		Optional<Plans> findById = planRepo.findById(planId);
		
		if(findById.isPresent()) {
			return findById.get();
		}
		
		return null;
	}

	@Override
	public boolean updatePlan(Plans plan) {
		
		planRepo.save(plan);
		
		return plan.getPlanId()!=null;
		
	}

	@Override
	public boolean deletePlanById(Integer planId) {
		
		boolean status = false;
		try {
		planRepo.deleteById(planId);
		status=true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public boolean planStatusChange(Integer planId, String status) {
		
		
		Optional<Plans> findById = planRepo.findById(planId);
		if(findById.isPresent()) {
			Plans plan = findById.get();
			plan.setActiveSw(status);
			planRepo.save(plan);
				
			return true;
		}
	
		return false;
	
	}	
}
