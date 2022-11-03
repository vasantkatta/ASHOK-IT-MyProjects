package com.spwosi.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name="PLAN_MASTER")
public class DC_PlanEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String planName;
	
	private LocalDate planStartDate;
	
	private LocalDate planEndDate;
	
	private String activeSw;
	
	private Integer planCategoryId;
	
	private String createBy;
	
	private String updatedBy;
	
	@CreationTimestamp
	private LocalDate createDate;
	
	@UpdateTimestamp
	private LocalDate updateDate;
	
}
