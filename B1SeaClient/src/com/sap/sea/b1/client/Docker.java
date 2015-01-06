package com.sap.sea.b1.client;

import java.util.Arrays;
import java.util.List;

import com.sap.sea.b1.client.island.Island;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerClient.ListImagesParam;
import com.spotify.docker.client.DockerException;
import com.spotify.docker.client.ProgressHandler;
import com.spotify.docker.client.DockerClient.ListContainersParam;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.HostConfig;
import com.spotify.docker.client.messages.Image;
import com.spotify.docker.client.messages.ProgressMessage;
import com.spotify.docker.client.messages.RemovedImage;

public class Docker {
	private static final String SUB_PATH = "/docker";
	private String path;
	private DockerClient client;

	public Docker(Island island) {
		path = island.getPath() + SUB_PATH;
		client = new DefaultDockerClient(path);
	}

	public List<String> removeImage(String image) {

		try {
			List<RemovedImage> removedImages = client.removeImage(image, true, false);
			return Arrays.asList(removedImages.stream().map((images) -> images.imageId()).toArray(String[]::new));
		} catch (DockerException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Container> listContainers(ListContainersParam... para) {
		try {
			return client.listContainers(para);
		} catch (DockerException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ContainerCreation createContainer(ContainerConfig config, String name) {
		try {
			return client.createContainer(config, name);
		} catch (DockerException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void removeContainer(String id) {
		try {
			client.removeContainer(id);
		} catch (DockerException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void startContainer(String containerId, HostConfig hostConfig) {
		try {
			if (hostConfig != null) {
				client.startContainer(containerId, hostConfig);
			} else {
				client.startContainer(containerId);
			}
		} catch (DockerException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void stopContainer(String containerId) {
		try {
			client.stopContainer(containerId, 0);
		} catch (DockerException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void pullImage(String name) {
		try {
			client.pull(name);
		} catch (DockerException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Image> listImages(ListImagesParam... param) {
		try {
			return client.listImages(param);
		} catch (DockerException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void tagImage(String image, String tag) {
		try {
			client.tag(image, tag);
		} catch (DockerException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void pushImage(String image) {
		try {
			client.push(image);
		} catch (DockerException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
