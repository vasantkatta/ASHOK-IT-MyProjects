package com.spwosi.binding;

import java.util.List;

import lombok.Data;

@Data
public class DC_Summary {

	private DC_Income income;
	
	private DC_Education education;
	
	private List<DC_Children> childs;
	
	private String planName;
	
}
