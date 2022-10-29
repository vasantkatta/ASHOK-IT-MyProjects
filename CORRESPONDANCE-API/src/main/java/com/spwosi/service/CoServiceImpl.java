package com.spwosi.service;

import java.awt.Color;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.spwosi.entity.CoTriggerEntity;
import com.spwosi.repo.CoTriggerRepository;



@Service
public class CoServiceImpl implements CoService {

	@Autowired
	private CoTriggerRepository coTriggerRepo;
	
	@Override
	public String readCoTriggerPendig() {

		
		return null;
	}

	@Override
	public String sendPdftoCitizen() {

		
		return null;
	}

	@Override
	public void generatePdf(HttpServletResponse response) throws Exception  {
		
		List<CoTriggerEntity> entities = coTriggerRepo.findAll();
		
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
		
		for(CoTriggerEntity entity : entities) {
	
			
		}
		
		document.close();
		
		
	}

}
