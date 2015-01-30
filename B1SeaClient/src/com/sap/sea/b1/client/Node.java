package com.sap.sea.b1.client;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.hk2.api.MultiException;
import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.client.JerseyWebTarget;

import com.google.common.base.Strings;
import com.sap.sea.b1.client.data.BuildLog;
import com.sap.sea.b1.client.island.Island;

public class Node {
	private static final String SUB_PATH = "/node";
	private String path;

	public Node(Island island) {
		path = island.getPath() + SUB_PATH;
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

	public BuildLog build(String buildPath, String name) {
		JerseyWebTarget target = SeaClient.jClient.target(path).path("/build");
		Response response = target.queryParam("name", name).request().post(Entity.text(buildPath));
		if (response.getStatus() == Status.OK.getStatusCode()) {
			return new BuildLog(response.readEntity(String.class));

		} else {
			return null;
		}
	}

	public boolean save(String image, String file) {
		JerseyWebTarget target = SeaClient.jClient.target(path).path("/save");
		Response response = target.queryParam("image", image).queryParam("file", file).request().post(Entity.text(""));
		if (response.getStatus() == Status.OK.getStatusCode()) {
			return true;
		}else {
			return false;
		}
	}

	public boolean load(String file) {
		JerseyWebTarget target = SeaClient.jClient.target(path).path("/load");
		Response response = target.queryParam("file", file).request().post(Entity.text(""));
		boolean success = Strings.isNullOrEmpty(response.readEntity(String.class));
		if (response.getStatus() == Status.OK.getStatusCode()) {
			return success;
		}else {
			return false;
		}
	}
}
