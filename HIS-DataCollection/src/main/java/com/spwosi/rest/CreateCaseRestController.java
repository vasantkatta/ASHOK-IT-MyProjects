package com.spwosi.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.spwosi.binding.CreateCaseResponse;
import com.spwosi.service.DataCollection;

@RestController
public class CreateCaseRestController {

	@Autowired
	private DataCollection service;
	
	@GetMapping("/case/{appId}")
	public ResponseEntity<CreateCaseResponse> createCase(@PathVariable Integer appId){
		
		Long caseNum = service.loadCaseNum(appId); 
	
		Map<Integer, String> planMap = service.getPlanNames();
		
		CreateCaseResponse response = new CreateCaseResponse();
		response.setCaseNum(caseNum);
		response.setPlansNames(planMap);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	
	
	
	
	
}
