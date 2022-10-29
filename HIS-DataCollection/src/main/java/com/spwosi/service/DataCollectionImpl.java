package com.spwosi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spwosi.binding.ChildRequest;
import com.spwosi.binding.DC_Children;
import com.spwosi.binding.DC_Education;
import com.spwosi.binding.DC_Income;
import com.spwosi.binding.DC_PlanSelection;
import com.spwosi.binding.DC_Summary;
import com.spwosi.entity.DC_CasesEntity;
import com.spwosi.entity.DC_ChildrenEntity;
import com.spwosi.entity.DC_CitizenAppEntity;
import com.spwosi.entity.DC_EducationEntity;
import com.spwosi.entity.DC_IncomeEntity;
import com.spwosi.entity.DC_PlanEntity;
import com.spwosi.repo.DC_CasesRepo;
import com.spwosi.repo.DC_ChildrenRepo;
import com.spwosi.repo.DC_CitizenAppRepo;
import com.spwosi.repo.DC_EducationRepo;
import com.spwosi.repo.DC_IncomeRepo;
import com.spwosi.repo.DC_PlanRepo;

@Service
public class DataCollectionImpl implements DataCollection{

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
	
	
	@Override
	public Long loadCaseNum(Integer appId) {

		Optional<DC_CitizenAppEntity> app = appRepo.findById(appId);
		
		if(app.isPresent()) {
		DC_CasesEntity entity = new DC_CasesEntity();
		entity.setAppId(appId);
		
		entity = dcCaseRepo.save(entity);
		
		return entity.getCaseNum();
		}
		return 0l;
	}

	@Override
	public Map<Integer, String> getPlanNames() {

		List<DC_PlanEntity> findAll = planRepo.findAll();
		
		Map<Integer, String> plansMap = new HashMap<>();
		
		for(DC_PlanEntity entity : findAll) {
			plansMap.put(entity.getPlanCategoryId(), entity.getPlanName());
		}
		
		return plansMap;
	}

	@Override
	public Long savePlanSelection(DC_PlanSelection planSelection) {

		Optional<DC_CasesEntity> findById = dcCaseRepo.findById(planSelection.getCaseNum());
		
		if(findById.isPresent()) {
			DC_CasesEntity dcCaseEntity = findById.get();
			dcCaseEntity.setPlanId(planSelection.getPlanId());
			
			dcCaseRepo.save(dcCaseEntity);
			
			return planSelection.getCaseNum();
		
		}
		
		return null;
	}

	@Override
	public Long saveIncomeData(DC_Income income) {
		DC_IncomeEntity entity = new DC_IncomeEntity();
		BeanUtils.copyProperties(income, entity);
		
		incomeRepo.save(entity);
		
		return income.getCaseNum();
		
	}

	@Override
	public Long saveEducation(DC_Education education) {

		DC_EducationEntity entity = new DC_EducationEntity();
		
		BeanUtils.copyProperties(education, entity);
		
		eduRepo.save(entity);
		
		return education.getCaseNum();

	}

	@Override
	public Long saveChildren(ChildRequest request) {

		List<DC_Children> childs = request.getChilds();
		Long caseNum = request.getCaseNum();
		
		for(DC_Children c : childs) {
			DC_ChildrenEntity entity = new DC_ChildrenEntity();
			BeanUtils.copyProperties(c, entity);
			entity.setCaseNum(caseNum);
			childRepo.save(entity);
			
		}
	//	childRepo.saveAll(entities);
		
		return request.getCaseNum();
	}

	@Override
	public DC_Summary getSummary(Long caseNum) {

	String planName = "";
		
	DC_IncomeEntity incomeEntity = incomeRepo.findByCaseNum(caseNum);
	DC_EducationEntity educationEntity = eduRepo.findByCaseNum(caseNum);	
	List<DC_ChildrenEntity> childsEntity = childRepo.findByCaseNum(caseNum);
	Optional<DC_CasesEntity> dcCase = dcCaseRepo.findById(caseNum);
	
	if(dcCase.isPresent()) {
		Integer planId = dcCase.get().getPlanId();
		Optional<DC_PlanEntity> plan = planRepo.findById(planId);
		if(plan.isPresent()) {
			planName = plan.get().getPlanName();
		}
	}
	
	
	DC_Summary summary = new DC_Summary();
	
	summary.setPlanName(planName);
	
	DC_Income income = new DC_Income();
	BeanUtils.copyProperties(incomeEntity, income);
	summary.setIncome(income);
	
	DC_Education edu = new DC_Education();
	BeanUtils.copyProperties(educationEntity, edu);
	summary.setEducation(edu);
	
	List<DC_Children> childs = new ArrayList<>();
	
	for(DC_ChildrenEntity entity : childsEntity) {
		DC_Children ch = new DC_Children();
		BeanUtils.copyProperties(entity, ch);
		childs.add(ch);
	}
	
		summary.setChilds(childs);
		return summary;
	}

}
