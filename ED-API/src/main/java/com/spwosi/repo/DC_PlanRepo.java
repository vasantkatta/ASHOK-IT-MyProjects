package com.spwosi.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spwosi.entity.DC_PlanEntity;

@Repository
public interface DC_PlanRepo extends JpaRepository<DC_PlanEntity, Serializable> {

}
