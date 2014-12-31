package com.sap.sea.b1.client.island;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.sea.b1.client.SeaClient;

public class IslandInfo {
	@JsonProperty("ip")
	private String host;
	@JsonProperty("user")
	private String user;
	@JsonProperty("pass")
	private String pass;

	public IslandInfo(String host) {
		this.host = host;
	}
	
	public IslandInfo(String host, String user, String pass) {
		this.host = host;
		this.user = user;
		this.pass = pass;
	}
	
	public String gethost() {
		return host;
	}

	public void sethost(String host) {
		this.host = host;
	}
}
