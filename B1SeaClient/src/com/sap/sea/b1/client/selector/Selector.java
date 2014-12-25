package com.sap.sea.b1.client.selector;

public abstract class Selector {
	private static final String SUB_PATH = "/selector";

	public String getPath() {
		return SUB_PATH+getSubPath();
	}
	
	public abstract String getSubPath();

}
