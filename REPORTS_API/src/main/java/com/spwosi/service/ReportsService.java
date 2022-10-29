package com.spwosi.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.spwosi.request.SearchRequest;
import com.spwosi.response.SearchResponse;

public interface ReportsService {

	public List<String> getUniquePlanNames();
	
	public List<String> getUniquePlanStatuses();
	
	public List<SearchResponse> search(SearchRequest request);
	
	public void generateExcel(HttpServletResponse response) throws Exception;
	
	public void generatePdf(HttpServletResponse response) throws Exception;
	
}
