package com.spwosi.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spwosi.entity.DC_ChildrenEntity;
@Repository
public interface DC_ChildrenRepo extends JpaRepository<DC_ChildrenEntity, Serializable>{

	public List<DC_ChildrenEntity> findByCaseNum(Long caseNum);


	
}
