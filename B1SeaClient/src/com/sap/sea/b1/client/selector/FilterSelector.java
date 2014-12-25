package com.sap.sea.b1.client.selector;

public class FilterSelector extends Selector {
	private static final String SUB_PATH = "/filter/";
	
	private Long memGap;
	
	public FilterSelector(Long memGap) {
		this.memGap = memGap;
	}
	
	@Override
	public String getSubPath() {
		return SUB_PATH+memGap.toString();
	}

}
