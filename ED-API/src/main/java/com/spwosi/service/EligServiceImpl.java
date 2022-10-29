package com.spwosi.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spwosi.entity.CoTriggerEntity;
import com.spwosi.entity.DC_CasesEntity;
import com.spwosi.entity.DC_ChildrenEntity;
import com.spwosi.entity.DC_CitizenAppEntity;
import com.spwosi.entity.DC_EducationEntity;
import com.spwosi.entity.DC_IncomeEntity;
import com.spwosi.entity.EligDtlsEntity;
import com.spwosi.repo.CoTriggerRepository;
import com.spwosi.repo.DC_CasesRepo;
import com.spwosi.repo.DC_ChildrenRepo;
import com.spwosi.repo.DC_CitizenAppRepo;
import com.spwosi.repo.DC_EducationRepo;
import com.spwosi.repo.DC_IncomeRepo;
import com.spwosi.repo.DC_PlanRepo;
import com.spwosi.repo.EligDtlsRepository;
import com.spwosi.response.EligResponse;

@Service
public class EligServiceImpl implements EligService{

	@Autowired
	private DC_CasesRepo dcCaseRepo;
	
	@Autowired
	private DC_PlanRepo planRepo;
	
	@Autowired
	private DC_IncomeRepo incomeRepo;
	
	@Autowired
	private DC_EducationRepo eduRepo;
	
	@Autowired
	private DC_ChildrenRepo childRepo;
	
	@Autowired
	private DC_CitizenAppRepo appRepo;
	
	@Autowired
	private EligDtlsRepository eligRepo;
	
	@Autowired
	private CoTriggerRepository coTrgRepo;
	
	@Override
	public EligResponse determineEligibility(Long caseNum) {

		Optional<DC_CasesEntity> caseEntity = dcCaseRepo.findById(caseNum);
		Integer planId = null;
		String planName = null;
		Integer appId = null;
		
		if(caseEntity.isPresent()) {
			DC_CasesEntity dcCaseEntity = caseEntity.get();
			planId = dcCaseEntity.getPlanId();
			appId = dcCaseEntity.getAppId();
		}
		
		Optional<DC_CitizenAppEntity> app = appRepo.findById(appId);
		Integer age = 0;
		DC_CitizenAppEntity citizenAppEntity = null;
		
		if(app.isPresent()) {
			citizenAppEntity = app.get();
			LocalDate dob = citizenAppEntity.getDob();
			LocalDate now = LocalDate.now();
			age = Period.between(dob, now).getYears();
		}
		
		EligResponse eligResponse = executePlanConditions(caseNum, planName, age);
		
		EligDtlsEntity eligEntity = new EligDtlsEntity();
		BeanUtils.copyProperties(eligResponse, eligEntity);
		
		eligEntity.setCaseNum(caseNum);
		eligEntity.setHolderName(citizenAppEntity.getFullName());
		eligEntity.setHolderSsn(citizenAppEntity.getSsn());
		
		eligRepo.save(eligEntity);
		
		CoTriggerEntity coEntity = new CoTriggerEntity();
		coEntity.setCaseNum(caseNum);
		coEntity.setTrgStatus("Pendi");
		
		coTrgRepo.save(coEntity);
		
		return eligResponse;
	}

	
	private EligResponse executePlanConditions(Long caseNum, String planName, Integer age) {
	
		EligResponse response = new EligResponse();
		
		response.setPlanName(planName);
		DC_IncomeEntity income = incomeRepo.findByCaseNum(caseNum);
		
		if("SNAP".equals(planName)) {
			Double empIncome = income.getEmpIncome();
			
			if(empIncome <=300) {
				response.setPlanStatus("Approved");
			} else {
				response.setPlanStatus("Denied");
				response.setDenialReason("High Income");
			}
		}else if("CCAP".equals(planName)) {
			boolean ageCondition = true;
			boolean kidsCountCondition = false;
			
			List<DC_ChildrenEntity> childs = childRepo.findByCaseNum(caseNum);
			if(!childs.isEmpty()) {
				kidsCountCondition = true;
				for(DC_ChildrenEntity entity : childs) {
					Integer childAge = entity.getChildAge();
					if(childAge> 16) {
						ageCondition = false;
						break;
					}
				}
			}
			
			if(income.getEmpIncome()<=300 && kidsCountCondition && ageCondition) {
				response.setPlanStatus("Approved");
			}else {
				response.setPlanStatus("Dennied");
				response.setDenialReason("Not satisfied business rules");
			}
			
			
		} else if("Medicaid".equals(planName)) {
			Double empIncome = income.getEmpIncome();
			Double propertyIncome = income.getPropertyIncome();
			
			if(empIncome<=300 && propertyIncome==0) {
				response.setPlanStatus("Approved");
			} else {
				response.setPlanStatus("Denied");
				response.setDenialReason("High Income");
			}
		}else if("NJW".equals(planName)) {
			DC_EducationEntity educationEntity = eduRepo.findByCaseNum(caseNum);
			Integer graduationYear = educationEntity.getGraduationYear();
			int currYear = LocalDate.now().getYear();
			
			if(income.getEmpIncome()<=0 && graduationYear<currYear) {
				response.setPlanStatus("Approved");
			}else {
				response.setPlanStatus("Denied");
				response.setDenialReason("Rules Not Satisfied");
			}
		}
		
		if(response.getPlanStatus().equals("Approved")) {
			response.setPlanStartDate(LocalDate.now());
			response.setPlanEndDate(LocalDate.now().plusMonths(6));
			response.setBenefitAmt(350.00);
		}
		
		return response;
	}
	
}
