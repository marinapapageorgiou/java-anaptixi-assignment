package datamodel.buildingblocks;

import java.util.ArrayList;
import java.util.List;

public class Document {

	private DocumentRawType inputFileType;
	private List<LineBlock> lineblocks = new ArrayList<LineBlock>();
	private String inputFilePath;

	public Document(String pFilePath, DocumentRawType docType) {
		this.inputFilePath = pFilePath;
		this.inputFileType = docType;
	}

	public enum DocumentRawType {
		RAW, ANNOTATED
	}

	public List<LineBlock> getLineblocks() {
		return lineblocks;
	}
	
	public DocumentRawType getInputFileType() {
		return inputFileType;
	}

	public String getInputFilePath() {
		return inputFilePath;
	}

}
