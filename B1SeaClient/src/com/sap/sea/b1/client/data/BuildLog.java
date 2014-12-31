package com.sap.sea.b1.client.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuildLog {
	private String log;
	private Pattern pattern = Pattern.compile("Successfully built (.*)");
	
	public BuildLog(String log) {
		this.setLog(log);
	}
	
	public String getLog() {
		return log;
	}
	
	private void setLog(String log) {
		this.log = log;
	}
	
	public String getImageID(){
		Matcher matcher = pattern.matcher(log);
		matcher.find();
		return matcher.group(1);
	}
}
