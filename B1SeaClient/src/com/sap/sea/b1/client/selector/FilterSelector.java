package com.sap.sea.b1.client.selector;

public class FilterSelector extends Selector {
	private static final String SUB_PATH = "/filter/";
	
	private Long memGap;
	
	public FilterSelector(Long memGap) {
		this.memGap = memGap;
	}
	
	public FilterSelector(Integer memGap) {
		this.memGap = memGap.longValue()*1024;
	}
	
	@Override
	public String getSubPath() {
		return SUB_PATH+memGap.toString();
	}

}
