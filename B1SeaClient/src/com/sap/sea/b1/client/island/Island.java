package com.sap.sea.b1.client.island;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.JerseyWebTarget;

import com.sap.sea.b1.client.Docker;
import com.sap.sea.b1.client.Node;
import com.sap.sea.b1.client.SeaClient;
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

	protected abstract Node node();

	protected Docker docker(){
		if (docker == null) {
			docker = new Docker(this);
		}
		return docker;
	}

	public String getHostName() {
		return node().getHostName();
	}

	public BuildLog build(String buildPath) {
		return node().build(buildPath);
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

	public List<String> removeImage(String id) {
		return docker().removeImage(id);
	}

	public List<Container> listContainers(ListContainersParam...para) {
		return docker().listContainers(para);
		
	}

	public String createContainer(ContainerConfig config,String name) {
		ContainerCreation creation =  docker().createContainer(config,name);
		return creation.id();
	}

	public void removeContainer(String id) {
		docker().removeContainer(id);
	}

	public void startContainer(String containerId, HostConfig hostConfig) {
		docker().startContainer(containerId, hostConfig);
	}

	public void stopContainer(String containerId) {
		docker().stopContainer(containerId);
	}

	public void pullImage(String name) {
		docker().pullImage(name);
	}

	public List<Image> listImages(ListImagesParam... param) {
		return docker().listImages(param);
	}

	public void tagImage(String name, String tag) {
		docker().tagImage(name,tag);
	}

	public void pushImage(String image) {
		docker().pushImage(image);
	}
}