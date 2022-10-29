package com.spwosi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spwosi.binding.ChildRequest;
import com.spwosi.binding.DC_Summary;
import com.spwosi.service.DataCollection;

@RestController
public class ChildRestController {

	@Autowired
	private DataCollection dcService;
	
	@PostMapping("/childrens")
	public ResponseEntity<DC_Summary> saveChilds(ChildRequest request){
	
		Long caseNum = dcService.saveChildren(request);
		DC_Summary  summary = dcService.getSummary(caseNum);
		
		return new ResponseEntity<>(summary, HttpStatus.OK);
	}
	
	
}
