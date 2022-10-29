package com.spwosi.service;

import java.util.Map;

import com.spwosi.binding.ChildRequest;
import com.spwosi.binding.DC_Education;
import com.spwosi.binding.DC_Income;
import com.spwosi.binding.DC_PlanSelection;
import com.spwosi.binding.DC_Summary;

public interface DataCollection {
	
	public Long loadCaseNum(Integer appId);
	
	public Map<Integer, String> getPlanNames();
	
	public Long savePlanSelection(DC_PlanSelection planSelection);
	
	public Long saveIncomeData(DC_Income income);
	
	public Long saveEducation(DC_Education education);
	
	public Long saveChildren(ChildRequest request);
	
	public DC_Summary getSummary(Long caseNum);
	
}
