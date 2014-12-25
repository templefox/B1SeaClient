package com.sap.sea.b1.client;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.JerseyWebTarget;
import org.hamcrest.core.Is;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.sea.b1.client.island.InconstantIsland;
import com.sap.sea.b1.client.island.IslandInfo;
import com.sap.sea.b1.client.island.TargetedIsland;
import com.sap.sea.b1.client.selector.Selector;

public class SeaClient {
	private static final String SUB_PATH = "/Sea";
	private static final String HTTP = "http://";
	private String path;

	private Hub hub;

	public static JerseyClient jClient = JerseyClientBuilder.createClient();

	public SeaClient(String host) {
		this.path = HTTP + host + SUB_PATH;
	}

	public Set<TargetedIsland> getIslands() {
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
		
		Set<TargetedIsland> islands = new LinkedHashSet();
		for (Iterator<IslandInfo> iterator = set.iterator(); iterator.hasNext();) {
			IslandInfo islandInfo = iterator.next();
			TargetedIsland island = getIsland(islandInfo);
			
			islands.add(island);
		}
		return islands;
	}

	public String getPath() {
		return path;
	}

	public Hub getHub() {
		JerseyWebTarget target = jClient.target(getPath()).path("/hub").path("/ip");
		String host = target.request().get(String.class);
		if (hub!=null&&host.equals(hub.getHost())) {
			return hub;
		}else {
			hub = new Hub(this, host);
			return hub;			
		}
	}
	
	public TargetedIsland getIsland(IslandInfo info){
		return new TargetedIsland(this,info);
	}

	public InconstantIsland select(Selector selector) {
		return new InconstantIsland(this,selector);
	}
}
