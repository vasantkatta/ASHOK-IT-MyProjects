package com.spwosi.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;


@Entity
@Data
@Table(name="EligibilityDetails")
public class EligibilityDtls {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ELIGIBITY_ID")
	private Integer eligId;
	
	@Column(name="CUST_NAME")
	private String name;
	
	@Column(name="MOBILE_NO")
	private Long mobile;

	@Column(name="EMAIL_ID")
	private String email;
	
	@Column(name="GENDER")
	private Character gender;
	
	@Column(name="SSN")
	private Long ssn;
	
	@Column(name="PLAN_NAME")
	private String planName;
	
	@Column(name="PLAN_STATUS")
	private String planStatus;
	
	@Column(name="START_DATE")
	@UpdateTimestamp
	private LocalDate planStartDate;
	
	@Column(name="END_DATE")
	@UpdateTimestamp
	private LocalDate planEndDate;
	
	@Column(name="CREATE_DATE")
	@UpdateTimestamp
	private LocalDate planCreateDate;
	
	@Column(name="UPDATE_DATE")
	@UpdateTimestamp
	private LocalDate planUpdateDate;
	
	@Column(name="CREATED_BY")
	private String planCreateBy;
	
	@Column(name="UPDATED_BY")
	private String planUpdateBy;
	
}
