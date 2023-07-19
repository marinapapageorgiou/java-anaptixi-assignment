package datamodel.rules;

import java.util.List;

import datamodel.buildingblocks.LineBlock;

public class RuleInPosition extends AbstractRule {
	
	List<LineBlock> lineblocks;
	List<Integer> positions;
	public RuleInPosition(List<LineBlock> pLineblocks, List<Integer> pPositions) {
		this.lineblocks = pLineblocks;
		this.positions = pPositions;
	}
	
	@Override
	public boolean isValid(LineBlock paragraph) {
		
		for (int i=0; i<lineblocks.size(); i++) {
			if (lineblocks.get(i).getLines().equals(paragraph.getLines())) {
				if (positions.contains(i)) {
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public String toString() {
		return "RuleInPosition";
	}

}
