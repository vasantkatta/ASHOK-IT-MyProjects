package com.spwosi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spwosi.binding.DC_PlanSelection;
import com.spwosi.service.DataCollection;

@RestController
public class PlanSelectionRestController {

	@Autowired
	private DataCollection service;
	
	@PostMapping("/plansel")
	public ResponseEntity<Long> planSelection(@RequestBody DC_PlanSelection planSel) {
		
		Long caseNum = service.savePlanSelection(planSel);
		
		return new ResponseEntity<>(caseNum, HttpStatus.CREATED);
	}
}

