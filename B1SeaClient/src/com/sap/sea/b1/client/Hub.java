package com.sap.sea.b1.client;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class Hub {
	private static final String SUB_PATH = "/hub";
	private String path;
	
	private String host;

	public Hub(SeaClient client,String host) {
		path = client.getPath()+SUB_PATH;
		this.host = host;
	}
	
	public String getHost() {
		return host;
	}
	
	public boolean setHost(String host){
		Response response = SeaClient.jClient.target(path).path("/ip").request().post(Entity.text(host));
		if (response.getStatus()==Status.OK.getStatusCode()) {
			this.host = host;
			return true;
		}else {
			return false;
		}
	}

}
