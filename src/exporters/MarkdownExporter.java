package exporters;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import datamodel.buildingblocks.Document;
import datamodel.buildingblocks.FormatEnum;
import datamodel.buildingblocks.LineBlock;
import datamodel.buildingblocks.StyleEnum;

public class MarkdownExporter {
	
	Document document;
	String outputFileName;
	
	public MarkdownExporter(Document document, String outputFileName) {
		this.document = document;
		this.outputFileName = outputFileName;
	}

	public int export() {
		List<LineBlock> lineblocks = document.getLineblocks();
		String fileContents = "";
		int lineblockSize = lineblocks.size();
		
		for(LineBlock l: lineblocks) {
			StyleEnum style = l.getStyle();
			FormatEnum format = l.getFormat();
			
			String endingPrefix = "";
			
			if (style.equals(StyleEnum.H1)) {
				fileContents += "#";
			}
			else if (style.equals(StyleEnum.H2)) {
				fileContents += "##";
			}
			else if (style.equals(StyleEnum.OMITTED)) {
				lineblockSize--;
				continue;
			}
			else if (style.equals(StyleEnum.NORMAL)) {
				if (format.equals(FormatEnum.BOLD)) {
					fileContents += "**";
					endingPrefix = "**";
				}
				else if (format.equals(FormatEnum.ITALICS)) {
					fileContents += "_";
					endingPrefix = "_";
				}
			}
			
			for (String line: l.getLines()) {
				fileContents += line + " ";
			}
			
			fileContents += endingPrefix + "\n\n";
		}
		
		FileWriter myWriter;
		try {
			myWriter = new FileWriter(outputFileName);
			myWriter.write(fileContents);
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return lineblockSize;
	}

}
