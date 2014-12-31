package com.sap.sea.b1.client;

import com.sap.sea.b1.client.island.Island;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;

public class Docker {
	private static final String SUB_PATH = "/docker";
	private String path;
	
	public Docker(Island island) {
		path = island.getPath()+SUB_PATH;
	}
}
