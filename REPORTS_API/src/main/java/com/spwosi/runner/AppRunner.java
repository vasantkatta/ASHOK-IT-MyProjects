package com.spwosi.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.spwosi.entity.EligibilityDtls;
import com.spwosi.repo.EligibilityDtlsRepo;

@Component
public class AppRunner implements ApplicationRunner{

	@Autowired
	private EligibilityDtlsRepo repo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		EligibilityDtls entity1 = new EligibilityDtls();
		entity1.setEligId(1);
		entity1.setName("Mary");
		entity1.setMobile(2343L);
		entity1.setSsn(32534L);
		entity1.setGender('F');
		entity1.setPlanName("SNAP");
		entity1.setPlanStatus("Approved");
		
		repo.save(entity1);
		
		EligibilityDtls entity2 = new EligibilityDtls();
		entity1.setEligId(2);
		entity1.setName("Vasant");
		entity1.setMobile(23343L);
		entity1.setSsn(325534L);
		entity1.setGender('M');
		entity1.setPlanName("CCP");
		entity1.setPlanStatus("Approved");
		
		repo.save(entity2);
		
		
		EligibilityDtls entity3 = new EligibilityDtls();
		entity1.setEligId(3);
		entity1.setName("Jay");
		entity1.setMobile(123343L);
		entity1.setSsn(2325534L);
		entity1.setGender('M');
		entity1.setPlanName("PPP");
		entity1.setPlanStatus("Denied");
		
		repo.save(entity3);

		
	}
	
	

	
	
}
