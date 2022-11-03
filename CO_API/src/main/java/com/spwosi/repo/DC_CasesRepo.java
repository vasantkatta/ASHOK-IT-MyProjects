package com.spwosi.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spwosi.entity.DC_CasesEntity;

public interface DC_CasesRepo extends JpaRepository<DC_CasesEntity, Serializable> {

	public DC_CasesEntity findByAppId(Integer appId);
}
