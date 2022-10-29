package com.spwosi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spwosi.binding.DC_Education;
import com.spwosi.service.DataCollection;

@RestController
public class EducationRestController {

	@Autowired
	private DataCollection service;
	
	@PostMapping("/education")
	public ResponseEntity<Long> saveEducation(DC_Education education){
		
		Long caseNum = service.saveEducation(education);
		
		return new ResponseEntity<>(caseNum, HttpStatus.CREATED);
	}
}
