package datamodel.rules;

import datamodel.buildingblocks.LineBlock;

public class RuleAllCaps extends AbstractRule {

	@Override
	public boolean isValid(LineBlock paragraph) {
		String text = "";
		for (int i=0; i<paragraph.getLines().size(); i++) {
			text += paragraph.getLines().get(i) + "\n";
		}
		
		if (text.toUpperCase().equals(text)) {
			return true;
		}
		else {		
			return false;
		}
	}

	@Override
	public String toString() {
		return "RuleAllCaps";
	}

}
