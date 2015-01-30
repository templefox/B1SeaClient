package com.sap.sea.b1.client;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.JerseyWebTarget;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.sea.b1.client.island.InconstantIsland;
import com.sap.sea.b1.client.island.Island;
import com.sap.sea.b1.client.island.IslandInfo;
import com.sap.sea.b1.client.island.TargetedIsland;
import com.sap.sea.b1.client.selector.Selector;

public class SeaClient {
	private static final String SUB_PATH = "";
	private static final String HTTP = "http://";
	private String path;

	private Hub hub;

	public static JerseyClient jClient = JerseyClientBuilder.createClient();

	public SeaClient(String host) {
		this.path = HTTP + host + SUB_PATH;
	}

	public Set<Island> getIslands() {
		Set<IslandInfo> set = null;
		try {
			ObjectMapper mapper = new ObjectMapper();

			JerseyWebTarget target = jClient.target(getPath()).path("/islands").path("/list");

			String lists = target.request().get(String.class);

			set = mapper.readValue(lists, new TypeReference<Set<IslandInfo>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		Set<Island> islands = new LinkedHashSet();
		for (Iterator<IslandInfo> iterator = set.iterator(); iterator.hasNext();) {
			IslandInfo islandInfo = iterator.next();
			TargetedIsland island = getIsland(islandInfo);

			islands.add(island);
		}
		return islands;
	}

	public boolean putIslands(IslandInfo info) {
		try {
			ObjectMapper mapper = new ObjectMapper();

			JerseyWebTarget target = jClient.target(getPath()).path("/islands").path("/list");
			String a = mapper.writeValueAsString(info);
			Response response = target.request().post(
					Entity.entity(mapper.writeValueAsString(info), MediaType.APPLICATION_JSON));

			if (response.getStatus()==Status.OK.getStatusCode()) {
				return true;
			}else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteIslands(String host) {
		try {
			ObjectMapper mapper = new ObjectMapper();

			JerseyWebTarget target = jClient.target(getPath()).path("/islands").path("/list").path("/").path(host);

			Response response = target.request().delete();

			if (response.getStatus()==Status.OK.getStatusCode()) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getPath() {
		return path;
	}

	public Hub getHub() {
		JerseyWebTarget target = jClient.target(getPath()).path("/hub").path("/ip");
		String host = target.request().get(String.class);
		if (hub != null && host.equals(hub.getHost())) {
			return hub;
		} else {
			hub = new Hub(this, host);
			return hub;
		}
	}

	public TargetedIsland getIsland(IslandInfo info) {
		return new TargetedIsland(this, info);
	}
	
	public TargetedIsland getIsland(String host) {
		return getIsland(new IslandInfo(host));
	}

	public InconstantIsland select(Selector selector) {
		return new InconstantIsland(this, selector);
	}
}
