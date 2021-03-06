package com.sap.sea.b1.client.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.sap.sea.b1.client.island.Island;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerClient.ListImagesParam;
import com.spotify.docker.client.DockerException;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.Image;

public class DockerClientTest {
	// final String dockerhost = "http://192.168.1.106:7777";
	final String dockerhost = "http://10.58.136.164:7777";

	@Test
	public void SimspleTest() {
		DockerClient client = new DefaultDockerClient(dockerhost);

		try {
			client.startContainer("kvmTest2");
		} catch (DockerException | InterruptedException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void seaDockerTest() {
		// DockerClient client = new
		// DefaultDockerClient("http://localhost:8080/Sea/island/192.168.1.106:7777/docker");
		DockerClient client = new DefaultDockerClient("http://10.58.77.129:20050/island/10.58.136.166:7777/docker/null");
		try {
			List<Image> images = client.listImages();
			Assert.assertTrue(images.size() > 0);
		} catch (DockerException | InterruptedException e) {
			e.printStackTrace();
			Assert.fail();
		}

	}

	@Test
	public void runTest() {
		DockerClient client = new DefaultDockerClient(dockerhost);
		try {
			List<Image> images = client.listImages();
			Image image = images.get(images.size() - 1);
			ContainerConfig config = ContainerConfig.builder().image(image.id()).build();

			String name = "test";
			ContainerCreation creation = client.createContainer(config, name);
			client.startContainer(name);

			name = "/" + name;
			List<Container> containers = client.listContainers();
			boolean contains = false;
			for (Container container : containers) {
				if (container.names().contains(name)) {
					contains = true;
				}
			}
			Assert.assertTrue(contains);

			client.stopContainer(name, 0);
			for (Container container : containers) {
				if (container.names().contains(name)) {
					contains = false;
				}
			}
			Assert.assertFalse(contains);

			client.removeContainer(name);

		} catch (DockerException | InterruptedException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
