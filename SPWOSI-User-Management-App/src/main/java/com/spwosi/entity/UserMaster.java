package com.spwosi.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Data
@Table(name="USER_MASTER")
public class UserMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;
	
	private String fullName;
	
	private String email;
	
	private Long mobile;
	
	private String gender;
	
	private LocalDate dob;
	
	private Long ssn;
	
	private String password;
	
	private String accStatus;
	
	@Column(name="CREATED_DATE", updatable=false)
	@CreationTimestamp
	private LocalDate createDate;
			
	@Column(name="UPDATED_DATE", insertable=false)
	@UpdateTimestamp
	private LocalDate updateDate;
	
	private String createdBy;
	
	private String updatedBy;
	
	
	
	
	
}
