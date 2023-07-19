package exporters;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import datamodel.buildingblocks.Document;
import datamodel.buildingblocks.FormatEnum;
import datamodel.buildingblocks.LineBlock;
import datamodel.buildingblocks.StyleEnum;

public class PdfExporter {
	
	Document document;
	String outputFileName;
	
	public PdfExporter(Document document, String outputFileName) {
		this.document = document;
		this.outputFileName = outputFileName;
	}

	public int export() {
		com.itextpdf.text.Document documentPdf = new com.itextpdf.text.Document();
		List<LineBlock> lineblocks = document.getLineblocks();
		
		int lineblockSize = lineblocks.size();
		
		try {	
			PdfWriter.getInstance(documentPdf, new FileOutputStream(outputFileName));
			documentPdf.open();
			
			for(LineBlock l: lineblocks) {
				String fileContents = "";
				StyleEnum style = l.getStyle();
				FormatEnum format = l.getFormat();
				
				Font font = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL);
				
				if (style.equals(StyleEnum.H1)) {
					font = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);
				}
				else if (style.equals(StyleEnum.H2)) {
					font = new Font(Font.FontFamily.COURIER, 15, Font.BOLD);
				}
				else if (style.equals(StyleEnum.OMITTED)) {
					lineblockSize--;
					continue;
				}
				else if (style.equals(StyleEnum.NORMAL)) {
					if (format.equals(FormatEnum.BOLD)) {
						font = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);					
					}
					else if (format.equals(FormatEnum.ITALICS)) {
						font = new Font(Font.FontFamily.COURIER, 12, Font.ITALIC);
					}
				}
				
				for (String line: l.getLines()) {
					fileContents += line + " ";
				}
							
				documentPdf.add(new Paragraph(fileContents, font));		
				documentPdf.add(new Paragraph("\n"));		
			}
			
			documentPdf.close();
			
		} catch (DocumentException | FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return lineblockSize;
	}

}
