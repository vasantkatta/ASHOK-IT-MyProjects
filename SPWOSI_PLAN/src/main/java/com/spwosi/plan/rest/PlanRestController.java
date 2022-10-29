package com.spwosi.plan.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spwosi.plan.constant.AppConstants;
import com.spwosi.plan.entity.Plans;
import com.spwosi.plan.props.AppProperties;
import com.spwosi.plan.service.PlanService;

@RestController
public class PlanRestController {

	@Autowired
	private PlanService planService;

	private Map<String,String> messages;
	
	public PlanRestController(PlanService planService, AppProperties appProps) {
		
		this.planService = planService;
		this.messages = appProps.getMessages();
		
	}
	
	@GetMapping("/categories")
	public ResponseEntity<Map<Integer, String>> planCategories() {

		Map<Integer, String> categories = planService.getPlanCategories();
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}

	@PostMapping("/plan")
	public ResponseEntity<String> savePlan(@RequestBody Plans plan) {

		
		String responseMsg = AppConstants.EMPTY_STR;
		
		boolean isSaved = planService.savePlan(plan);

		
		if (isSaved) {
			responseMsg = messages.get(AppConstants.PLAN_SAVE_SUCC);
		} else {
			responseMsg = messages.get(AppConstants.PLAN_SAVE_FAIL);
		}

		return new ResponseEntity<>(responseMsg, HttpStatus.CREATED);
	}

	@GetMapping("/plans")
	public ResponseEntity<List<Plans>> plans() {
		List<Plans> allPlans = planService.getAllPlans();
		return new ResponseEntity<>(allPlans, HttpStatus.OK);
	}
	
	@GetMapping("/plan/{planId}")
	public ResponseEntity<Plans> editPlan(@PathVariable Integer planId){
		
		Plans plan = planService.getPlanById(planId);
		return new ResponseEntity<>(plan, HttpStatus.OK);
		
	}
	
	
	@DeleteMapping("/plan/{planId}")
	public ResponseEntity<String> deletePlan(@PathVariable Integer planId){
		
		boolean isDeleted = planService.deletePlanById(planId);
		
		
		String msg = AppConstants.EMPTY_STR;
		
		if(isDeleted) {
			msg = messages.get(AppConstants.PLAN_DELETE_SUCC);
		}else {
			msg = messages.get(AppConstants.PLAN_DELETE_FAIL);
		}
		
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}
	
	@PutMapping("/status-change/{planId}/{status}")
	public ResponseEntity<String> statusChange(@PathVariable Integer planId, @PathVariable String status){
		
		String msg=AppConstants.EMPTY_STR;
		boolean isStatusChanged = planService.planStatusChange(planId, status);
		
		
		if(isStatusChanged) {
			msg = messages.get(AppConstants.PLAN_SATUS_CHANGE);
		} else {
			msg = messages.get(AppConstants.PLAN_STATUS_FAIL);
		}
		
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}
	
	
	@PutMapping("/plan")
	public ResponseEntity<String> UpdatePlan(@RequestBody Plans plan){
		
		boolean isUpdated = planService.updatePlan(plan);
		
		
		String msg = AppConstants.EMPTY_STR;
		if(isUpdated) {
			msg = messages.get(AppConstants.PLAN_UPDATE_SUCC);
		} else {
			msg = messages.get(AppConstants.PLAN_UPDATE_FAIL);
		}
		
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}

	
	
	
	
}


