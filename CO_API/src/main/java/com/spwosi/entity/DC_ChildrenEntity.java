package com.spwosi.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="DC_CHILDREN")
public class DC_ChildrenEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer childId;
	private String childName;
	private Integer childAge;
	private Long childSsn;
	private Long caseNum;

}
