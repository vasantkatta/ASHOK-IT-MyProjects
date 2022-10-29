package com.spwosi.service;

import javax.servlet.http.HttpServletResponse;

public interface CoService {

	public String readCoTriggerPendig();
	public String sendPdftoCitizen();
	public void generatePdf(HttpServletResponse response) throws Exception;
	
}
