package datamodel.buildingblocks;

import java.util.List;

public class LineBlock {
	
	private List<String> lines;
	private StyleEnum fileStyle;
	private FormatEnum fileFormat;
	
	public LineBlock(List<String> lines) {
		this.lines = lines;
		this.fileStyle = StyleEnum.NORMAL;
		this.fileFormat = FormatEnum.REGULAR;
	}

	public String getStatsAsString() {
		String stats = "Lines: " + lines.size() + " Words: " + countWords(); 
		return stats;
	}

	public int getNumWords() {
		return countWords();
	}

	public void setStyle(StyleEnum determineHeadingStatus) {
		fileStyle = determineHeadingStatus;
		
	}

	public void setFormat(FormatEnum determineFormatStatus) {
		fileFormat = determineFormatStatus;
		
	}

	public List<String> getLines() {
		return this.lines;
	}

	public StyleEnum getStyle() {
		return fileStyle;
	}

	public FormatEnum getFormat() {
		return fileFormat;
	}
	
	private int countWords() {
		int count = 0;
		for (int i=0; i<lines.size(); i++) {
			String [] lineWords = lines.get(i).split("\\s+");
			count += lineWords.length;
		}
		
		return count;
	}

}
