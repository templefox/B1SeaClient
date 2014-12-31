package com.sap.sea.b1.client.island;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.JerseyWebTarget;

import com.sap.sea.b1.client.Docker;
import com.sap.sea.b1.client.Node;
import com.sap.sea.b1.client.SeaClient;

public abstract class Island {

	protected String path;
	protected Docker docker;
	private String host;

	protected Island(String path) {
		this.path = path;
	}

	public final String getPath() {
		return path;
	}

	public final boolean ping() {
		JerseyWebTarget target = SeaClient.jClient.target(path).path("/ping");
		Response response = target.request().get();
		if (response.getStatus()==Status.OK.getStatusCode()) {
			return true;
		}else {
			return false;
		}
	}

	public String getHost() {
		return host;
	}

	protected void setHost(String host) {
		this.host = host;
	}

	public abstract Node node();

	public Docker docker(){
		if (docker == null) {
			docker = new Docker(this);
		}
		return docker;
	}
}