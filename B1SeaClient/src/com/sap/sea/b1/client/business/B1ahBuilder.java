package com.sap.sea.b1.client.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sap.sea.b1.client.island.Island;
import com.sap.sea.b1.client.island.IslandAnchor;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.HostConfig;
import com.spotify.docker.client.messages.PortBinding;

public class B1ahBuilder {
	private boolean privileged = true;
	private Island island;
	private String image;
	private String name;
	private Map<String, List<PortBinding>> map = new HashMap<String, List<PortBinding>>();

	public B1ahBuilder(Island island) {
		this.island = island;
	}

	public B1ahBuilder image(String image) {
		this.image = image;
		return this;
	}

	public B1ahBuilder name(String name) {
		this.name = name;
		return this;
	}

	public B1ahBuilder bind40000(Integer port) {
		bind("40000/tcp", port);
		return this;
	}

	public B1ahBuilder bind30015(Integer port) {
		bind("30015/tcp", port);
		return this;
	}

	public B1ahBuilder bind8000(Integer port) {
		bind("8000/tcp", port);
		return this;
	}

	public B1ahBuilder bind4300(Integer port) {
		bind("4300/tcp", port);
		return this;
	}

	public B1ahBuilder bind22(Integer port) {
		bind("22/tcp", port);
		return this;
	}

	private void bind(String exposed, Integer port) {
		List<PortBinding> bindings = new ArrayList<PortBinding>();
		bindings.add(PortBinding.of("0.0.0.0", port));
		map.put(exposed, bindings);
	}

	public Island run() {
		IslandAnchor<String> islandAnchor = island
				.createContainer(ContainerConfig.builder().image(image).build(), name);

		island = islandAnchor.self();
		island.startContainer(islandAnchor.value(), HostConfig.builder().privileged(privileged).portBindings(map)
				.build());
		return islandAnchor.self();
	}
}
