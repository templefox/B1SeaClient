package com.sap.sea.b1.client.island;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.JerseyWebTarget;

import com.sap.sea.b1.client.Docker;
import com.sap.sea.b1.client.Node;
import com.sap.sea.b1.client.SeaClient;
import com.sap.sea.b1.client.business.B1ahBuilder;
import com.sap.sea.b1.client.business.KvmBuilder;
import com.sap.sea.b1.client.data.BuildLog;
import com.spotify.docker.client.DockerClient.ListContainersParam;
import com.spotify.docker.client.DockerClient.ListImagesParam;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.HostConfig;
import com.spotify.docker.client.messages.Image;

public abstract class Island {

	protected String path;
	protected Docker docker;
	private String host;

	protected SeaClient client;
	protected Integer uid = null;

	protected Island(String path) {
		this.path = path;
	}

	public final String getPath() {
		return path;
	}

	public final boolean ping() {
		JerseyWebTarget target = SeaClient.jClient.target(path).path("/ping");
		Response response = target.request().get();
		if (response.getStatus() == Status.OK.getStatusCode()) {
			return true;
		} else {
			return false;
		}
	}

	public String getHost() {
		return host;
	}

	protected void setHost(String host) {
		this.host = host;
	}

	protected abstract Node node();

	protected Docker docker() {
		if (docker == null) {
			docker = new Docker(this, getUid());
		}
		return docker;
	}

	public String getHostName() {
		return node().getHostName();
	}

	public BuildLog build(String buildPath, String name) {
		return node().build(buildPath, name);
	}

	public long getMemTotal() {
		return node().getMemTotal();
	}

	public long getMemUsed() {
		return node().getMemUsed();
	}

	public long getMemFree() {
		return node().getMemFree();
	}

	public double getMemUsage() {
		return node().getMemUsage();
	}

	public IslandAnchor<List<String>> removeImage(String id) {
		return new IslandAnchor<List<String>>(docker().removeImage(id), convertToTargeted());
	}

	public IslandAnchor<List<Container>> listContainers(ListContainersParam... para) {
		return new IslandAnchor<List<Container>>(docker().listContainers(para), convertToTargeted());
	}

	public IslandAnchor<List<Container>> listContainers(boolean all) {
		return listContainers(ListContainersParam.allContainers(true));
	}

	public IslandAnchor<String> createContainer(ContainerConfig config, String name) {
		ContainerCreation creation = docker().createContainer(config, name);
		return new IslandAnchor<String>(creation.id(), convertToTargeted());
	}

	public IslandAnchor<Void> removeContainer(String id) {
		docker().removeContainer(id, false);
		return new IslandAnchor<Void>(null, convertToTargeted());
	}

	public IslandAnchor<Void> removeContainer(String id, boolean force) {
		docker().removeContainer(id, force);
		return new IslandAnchor<Void>(null, convertToTargeted());
	}

	public IslandAnchor<Void> startContainer(String containerId, HostConfig hostConfig) {
		docker().startContainer(containerId, hostConfig);
		return new IslandAnchor<Void>(null, convertToTargeted());
	}

	public IslandAnchor<Void> stopContainer(String containerId) {
		docker().stopContainer(containerId);
		return new IslandAnchor<Void>(null, convertToTargeted());
	}

	public IslandAnchor<Void> pullImage(String name) {
		docker().pullImage(name);
		return new IslandAnchor<Void>(null, convertToTargeted());
	}

	public IslandAnchor<List<Image>> listImages(ListImagesParam... param) {
		List<Image> images = docker().listImages(param);
		return new IslandAnchor<List<Image>>(images, convertToTargeted());
	}

	public IslandAnchor<Void> tagImage(String name, String tag) {
		docker().tagImage(name, tag);
		return new IslandAnchor<Void>(null, convertToTargeted());
	}

	public IslandAnchor<Void> pushImage(String image) {
		docker().pushImage(image);
		return new IslandAnchor<Void>(null, convertToTargeted());

	}	
	
	public IslandAnchor<Boolean> save(String image, String file) {
		boolean success = node().save(image,file);
		return new IslandAnchor<Boolean>(success, convertToTargeted());
	}	
	
	public IslandAnchor<Boolean> load(String file) {
		boolean success = node().load(file);
		return new IslandAnchor<Boolean>(success, convertToTargeted());
	}

	abstract protected TargetedIsland convertToTargeted();

	public Integer getUid() {
		return uid;
	}

	public B1ahBuilder prepareB1ah() {
		return new B1ahBuilder(this);
	}

	public KvmBuilder prepareKvm() {
		return new KvmBuilder(this);
	}




}