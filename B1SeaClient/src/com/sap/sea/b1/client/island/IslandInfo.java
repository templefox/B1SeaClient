package com.sap.sea.b1.client.island;

import com.sap.sea.b1.client.SeaClient;

public class IslandInfo {
	private String host;

	public IslandInfo(String host) {
		this.host = host;
	}
	
	public String gethost() {
		return host;
	}

	public void sethost(String host) {
		this.host = host;
	}

	@Deprecated
	public Island fromSea(SeaClient client) {
		return new TargetedIsland(client,this);
	}
}
