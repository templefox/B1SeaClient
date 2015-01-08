package com.sap.sea.b1.client.island;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import com.sap.sea.b1.client.Docker;
import com.sap.sea.b1.client.Node;
import com.sap.sea.b1.client.SeaClient;
import com.sap.sea.b1.client.selector.Selector;

public class InconstantIsland extends Island{
	private boolean isAnchored = false;
	
	public InconstantIsland(SeaClient client,Selector selector) {
		super(client.getPath()+selector.getPath());
		this.client = client;
	}

	@Override
	public Node node() {
		throw new NoSuchMethodError("Cannot visit node through InconstantIsland");
	}
	
	@Override
	protected Docker docker() {
		if (isAnchored) {
			throw new IllegalAccessError("Once do request, the inconstantIsland should be translate into targetedIsland.");
		}
		Response response = SeaClient.jClient.target(path).path("/anchor").request().post(Entity.text(""));
		uid = new Integer(response.getCookies().get("uid").getValue());
		isAnchored = true;
		return super.docker();
	}

	@Override
	protected TargetedIsland convertToTargeted() {
		String host = SeaClient.jClient.target(path).path("/anchor").request().cookie("uid", uid.toString()).get(String.class);
		return new TargetedIsland(client, new IslandInfo(host));
	}
}
