package com.sap.sea.b1.client;

import com.fasterxml.jackson.annotation.JsonRawValue;

public class IslandInfo {
	private String ip;

	public IslandInfo(String ip) {
		this.ip = ip;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Island fromSea(SeaClient client) {
		return new Island(client,this);
	}
}
