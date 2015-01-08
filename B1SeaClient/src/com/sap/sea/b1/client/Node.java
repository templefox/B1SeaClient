package com.sap.sea.b1.client;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.client.JerseyWebTarget;

import com.sap.sea.b1.client.data.BuildLog;
import com.sap.sea.b1.client.island.Island;

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

	public String getHostName() {
		JerseyWebTarget target = SeaClient.jClient.target(path).path("/hostname");
		String hostname = target.request().get(String.class);
		return hostname;
	}
	
	public BuildLog build(String buildPath){
		JerseyWebTarget target = SeaClient.jClient.target(path).path("/build");
		Response response = target.request().post(Entity.text(buildPath));
		if (response.getStatus()==Status.OK.getStatusCode()) {
			return new BuildLog(response.readEntity(String.class));
		}else {
			return null;
		}
	}
}
