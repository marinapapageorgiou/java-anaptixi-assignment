package datamodel.rules;

import datamodel.buildingblocks.LineBlock;

public class RuleStartWith extends AbstractRule {
	
	String prefix;
	public RuleStartWith(String prefix) {
		this.prefix = prefix;
	}
	@Override
	public boolean isValid(LineBlock paragraph) {
		String firstLine = paragraph.getLines().get(0);
				
		if (firstLine.startsWith(prefix)) {
			return true;
		}
		else {		
			return false;
		}
	}

	@Override
	public String toString() {
		return "RuleStartWith";
	}

}
