package com.sap.sea.b1.client.selector;

public class RandomSelector extends Selector{
	private static final String SUB_PATH = "/random";
	
	@Override
	public String getSubPath() {
		return SUB_PATH;
	}

}
