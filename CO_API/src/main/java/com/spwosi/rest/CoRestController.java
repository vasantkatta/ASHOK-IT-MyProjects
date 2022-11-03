package com.spwosi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spwosi.binding.CoResponse;
import com.spwosi.service.CoService;

@RestController
public class CoRestController {

	@Autowired
	private CoService service;
	
	@GetMapping("/process")
	public CoResponse proceTriggers() {
		
		return service.processPendingTriggers();
	}
	
}
