package dataload;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import datamodel.buildingblocks.LineBlock;

public class RawFileLineLoader {

	public void load(String filePath, List<LineBlock> lineblocks) {
		
		FileReader filereader;
		BufferedReader reader;
		 
		try {
			filereader = new FileReader(filePath);
			reader = new BufferedReader(filereader);
			
			String currentLine = reader.readLine();
			while (currentLine != null) {
				
				List<String> block = new ArrayList<String>();
				while (!currentLine.equals("")) {
					block.add(currentLine);
					
					currentLine = reader.readLine();
					if (currentLine == null)
						break;
				}
				
				if (block.size() != 0) {
					LineBlock lineblock = new LineBlock(block);
					lineblocks.add(lineblock);
				}
				
				currentLine = reader.readLine();
			}
			
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
