package com.spwosi.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="DATA_CASES")
public class DC_CasesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer caseId;
	private Long caseNum;
	private Integer appId;
	private Integer planId;
	
}
