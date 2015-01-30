package com.sap.sea.b1.client.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.ServerErrorException;

import com.sap.sea.b1.client.island.Island;
import com.sap.sea.b1.client.island.IslandAnchor;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.HostConfig;
import com.spotify.docker.client.messages.PortBinding;

public class KvmBuilder {


	private Island island;
	private Integer portTo3389;
	private Map<String, List<PortBinding>> map = new HashMap<String, List<PortBinding>>();
	private String image;
	private String name;
	private String volumn = "/dev/kvm:/dev/kvm";
	private boolean privileged = true;
	private Long mem = 2048L;

	public KvmBuilder(Island island) {
		this.island = island;
	}

	public KvmBuilder bind3389(Integer port) {
		bind("3389/tcp", port);
		return this;
	}
	
	public KvmBuilder image(String image){
		this.image = image;
		return this;
	}
	
	public KvmBuilder withWin7(){
		return image(KvmImages.windows7);
	}

	public KvmBuilder withWin8(){
		return image(KvmImages.windows8);
	}
	
	public KvmBuilder bind8080(Integer port) {
		bind("8080/tcp", port);
		return this;
	}

	public KvmBuilder memMega(Long mem) {
		this.mem = mem;
		return this;
	}
	
	public KvmBuilder name(String name){
		this.name = name;
		return this;
	}

	public Island run() {

		IslandAnchor<String> islandAnchor = island.createContainer(
				ContainerConfig.builder().image(image).env("mem=" + mem.toString()).build(), name);

		island = islandAnchor.self();
		try {
			
		island.startContainer(islandAnchor.value(), HostConfig.builder().privileged(privileged).portBindings(map)
				.binds(volumn).build());
		} catch (ServerErrorException e) {
			e.printStackTrace();
			island.removeContainer(name, true);
		}
		return islandAnchor.self();
	}

	private void bind(String exposed, Integer port) {
		List<PortBinding> bindings = new ArrayList<PortBinding>();
		bindings.add(PortBinding.of("0.0.0.0", port));
		map.put(exposed, bindings);
	}
}
