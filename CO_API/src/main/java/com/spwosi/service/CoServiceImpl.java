package com.spwosi.service;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.spwosi.binding.CoResponse;
import com.spwosi.entity.CoTriggerEntity;
import com.spwosi.entity.DC_CasesEntity;
import com.spwosi.entity.DC_CitizenAppEntity;
import com.spwosi.entity.EligDtlsEntity;
import com.spwosi.repo.CoTriggerRepository;
import com.spwosi.repo.DC_CasesRepo;
import com.spwosi.repo.DC_CitizenAppRepo;
import com.spwosi.repo.EligDtlsRepository;
import com.spwosi.utils.EmailUtils;

@Service
public class CoServiceImpl implements CoService {

	@Autowired
	private CoTriggerRepository coTrgRepo;

	@Autowired
	private EligDtlsRepository eligRepo;

	@Autowired
	private DC_CitizenAppRepo appRepo;

	@Autowired
	private DC_CasesRepo dcCaseRepo;

	@Autowired
	private EmailUtils emailUtils;

	@Override
	public CoResponse processPendingTriggers() {

		CoResponse response = new CoResponse();

		DC_CitizenAppEntity appEntity = null;

		List<CoTriggerEntity> pendingTrgs = coTrgRepo.findByTrgStatus("Pending");

		response.setTotalTriggers(Long.valueOf(pendingTrgs.size()));

		for (CoTriggerEntity entity : pendingTrgs) {

			EligDtlsEntity elig = eligRepo.findByCaseNum(entity.getCaseNum());

			Optional<DC_CasesEntity> findById = dcCaseRepo.findById(entity.getCaseNum());

			if (findById.isPresent()) {

				DC_CasesEntity dcCaseEntity = findById.get();
				Integer appId = dcCaseEntity.getAppId();
				Optional<DC_CitizenAppEntity> appEntityOptional = appRepo.findById(appId);

				if (appEntityOptional.isPresent()) {
					appEntity = appEntityOptional.get();
				}
			}
		

				Long failed = 0l;
				Long success = 0l;
				
				try {
				generateAndSendPdf(elig, appEntity);
				success++;
				} catch (Exception e) {
					e.printStackTrace();
					failed++;
				}
		
			response.setSuccessTriggers(success);
			response.setFailedTrigger(failed);
			
			
		}
		
		return response;
	}


	private void generateAndSendPdf(EligDtlsEntity eligData, DC_CitizenAppEntity appEntity) throws Exception{
		
		Document document = new Document(PageSize.A4);
		
		File file = new File(eligData.getCaseNum() + ".pdf");
		FileOutputStream fos = null;
				
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
		PdfWriter.getInstance(document, fos);
		
		document.open();
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);
		
		Paragraph p = new Paragraph("Eligibility Report", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(p);
		
		PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 1.5f, 3.0f, 1.5f, 3.0f});
		table.setSpacingBefore(10);
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);
		
		font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		
		cell.setPhrase(new Phrase("Citizen Name", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Plan Name", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Plan Status", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Plan Start Date", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Plan End Date", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Benefit Amount", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Denial Reason", font));
		table.addCell(cell);
		
		table.addCell(appEntity.getFullName());
		table.addCell(eligData.getPlanName());
		table.addCell(eligData.getPlanStatus());
		table.addCell(eligData.getPlanStartDate()+"");
		table.addCell(eligData.getPlanEndDate()+"");
		table.addCell(eligData.getBenefitAmt()+"");
		table.addCell(eligData.getDenialReason()+"");
		
		document.add(table);
		document.close();
		
		String subject = "HIS Eligibility Information";
		String body = "HIS Eligibility Information";
		
		emailUtils.sendEmail(appEntity.getEmail(), subject, body, file);
		updateTrigger(eligData.getCaseNum(), file);
		
		file.delete();
	} 
		

	private void updateTrigger(Long caseNum, File file) throws Exception{
		
		CoTriggerEntity coEntity = (CoTriggerEntity) coTrgRepo.findByCaseNum(caseNum);
		byte[] arr = new byte[(byte) file.length()];
		
		FileInputStream fis = new FileInputStream(file);
		fis.read(arr);
		
		coEntity.setCoPdf(arr);
		
		coEntity.setTrgStatus("Complete");
		
		coTrgRepo.save(coEntity);
		
		fis.close();
		
	}
	
}


