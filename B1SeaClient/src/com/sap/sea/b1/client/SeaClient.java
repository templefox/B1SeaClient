package com.sap.sea.b1.client;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.JerseyWebTarget;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SeaClient {
	private static final String SUB_PATH = "/Sea";
	private static final String HTTP = "http://";
	private String path;

	static JerseyClient jClient = JerseyClientBuilder.createClient();

	public SeaClient(String host) {
		this.path = HTTP + host +SUB_PATH;
	}

	public Set<IslandInfo> getIslands() {
		Set<IslandInfo> set = null;
		try {
			ObjectMapper mapper = new ObjectMapper();

			JerseyWebTarget target = jClient.target(getPath()).path("/islands").path("/list");
			System.out.println(target.getUri());

			String lists = target.request().get(String.class);

			set = mapper.readValue(lists, new TypeReference<Set<IslandInfo>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		return set;
	}

	public String getPath() {
		return path;
	}

}
