package com.sap.sea.b1.client;

import org.glassfish.jersey.client.JerseyWebTarget;

public class Node {
	private static final String SUB_PATH = "/node";
	private String path;
	
	public Node(Island island) {
		path = island.getPath()+SUB_PATH;
	}

	public Double getMemUsage() {
		JerseyWebTarget target = SeaClient.jClient.target(path).path("/mem/usage");
		String str = target.request().get(String.class);
		Double usage = Double.valueOf(str);
		return usage;
	}

	public Long getMemUsed() {
		JerseyWebTarget target = SeaClient.jClient.target(path).path("/mem/used");
		String str = target.request().get(String.class);
		Long used = Long.valueOf(str);
		return used;
	}

	public Long getMemTotal() {
		JerseyWebTarget target = SeaClient.jClient.target(path).path("/mem/total");
		String str = target.request().get(String.class);
		Long used = Long.valueOf(str);
		return used;
	}
	
	public Long getMemFree() {
		JerseyWebTarget target = SeaClient.jClient.target(path).path("/mem/free");
		String str = target.request().get(String.class);
		Long free = Long.valueOf(str);
		return free;
	}
}
