package com.spwosi.service;

import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
import com.spwosi.entity.EligibilityDtls;
import com.spwosi.repo.EligibilityDtlsRepo;
import com.spwosi.request.SearchRequest;
import com.spwosi.response.SearchResponse;

@Service
public class ReportsServiceImpl implements ReportsService {
	
	@Autowired
	private EligibilityDtlsRepo eligRepo;

	@Override
	public List<String> getUniquePlanNames() {
		
		return eligRepo.findPlanName();
	}

	@Override
	public List<String> getUniquePlanStatuses() {
		
		
		return eligRepo.findPlanStatus();
	}

	@Override
	public List<SearchResponse> search(SearchRequest request) {
		
		List<SearchResponse> response = new ArrayList<>();
		
		EligibilityDtls queryBuilder = new EligibilityDtls();
		
		String planName = request.getPlanName();
		
		if(planName!=null && planName.equals("")) {
			queryBuilder.setPlanName(planName);
		}
		
		String planStatus = request.getPlanStatus();
		if(planStatus!=null && !planStatus.equals("")) {
			queryBuilder.setPlanStatus(planStatus);
		}
		
		LocalDate planStartDate = request.getPlanStartDate();
		if(planStartDate!=null) {
			queryBuilder.setPlanStartDate(planStartDate);
		}
		
		LocalDate planEndDate = request.getPlanEndDate();
		if(planEndDate!=null) {
			queryBuilder.setPlanEndDate(planEndDate);
		}
		Example<EligibilityDtls> example = Example.of(queryBuilder);
			
		List<EligibilityDtls> entities = eligRepo.findAll(example);
		
		for(EligibilityDtls entity : entities) {
			
			SearchResponse sr = new SearchResponse();
			BeanUtils.copyProperties(entity, sr);
			response.add(sr);
		}
		return response;
	}

	@Override
	public void generateExcel(HttpServletResponse response) throws Exception {
		
		List<EligibilityDtls> entities = eligRepo.findAll();
		
		HSSFWorkbook  workBook = new HSSFWorkbook();
		HSSFSheet sheet = workBook.createSheet();
		HSSFRow headerRow = sheet.createRow(0);
		
		headerRow.createCell(0).setCellValue("Name");
		headerRow.createCell(1).setCellValue("Email");
		headerRow.createCell(2).setCellValue("Mobile");
		headerRow.createCell(3).setCellValue("Gender");
		headerRow.createCell(4).setCellValue("SSN");
		
		int i=1;
		
		for(EligibilityDtls entity : entities) {
					
			HSSFRow dataRow = sheet.createRow(i);
			dataRow.createCell(0).setCellValue(entity.getName());
			dataRow.createCell(1).setCellValue(entity.getEmail());
			dataRow.createCell(2).setCellValue(entity.getMobile());
			dataRow.createCell(3).setCellValue(entity.getGender());
			dataRow.createCell(4).setCellValue(entity.getSsn());
		
			i++;
		}
		
		ServletOutputStream outputStream = response.getOutputStream();
		workBook.write(outputStream);
		workBook.close();
		outputStream.close();
		
	}

	@Override
	public void generatePdf(HttpServletResponse response) throws Exception {
		
		List<EligibilityDtls> entities = eligRepo.findAll();
		
		Document document = new Document(PageSize.A4);
		
		PdfWriter.getInstance(document, response.getOutputStream());
		
		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);
		
		Paragraph p = new Paragraph("List of Users", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(p);
		
		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] {1f, 3f, 2f, 2f, 2f});
		table.setSpacingBefore(10);
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);
		
		font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		
		cell.setPhrase(new Phrase("USER ID", font));
		
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("E-mail",font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Full Name",font));
		table.addCell(cell);	
		
		cell.setPhrase(new Phrase("Phone No.",font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Gender",font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("SSN",font));
		table.addCell(cell);
		
		for(EligibilityDtls entity : entities) {
			
			table.addCell(entity.getName());
			table.addCell(entity.getEmail());
			table.addCell(String.valueOf(entity.getMobile()));		
			table.addCell(String.valueOf(entity.getGender()));
			table.addCell(String.valueOf(entity.getSsn()));
			
		}
		
		document.close();
		
		
	}
	

}
