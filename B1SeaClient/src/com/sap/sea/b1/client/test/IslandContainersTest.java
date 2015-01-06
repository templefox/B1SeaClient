package com.sap.sea.b1.client.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.sap.sea.b1.client.SeaClient;
import com.sap.sea.b1.client.island.Island;
import com.sap.sea.b1.client.island.IslandInfo;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient.ListContainersParam;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerConfig;

public class IslandContainersTest {
	@Test
	public void listContainersTest(){
		SeaClient client = new SeaClient(IslandNodeTest.HOST);
		Island island = client.getIsland(new IslandInfo("10.58.136.166:7777"));
		
		List<Container> containers = island.listContainers();
		Assert.assertTrue(containers.size()>=0);
		
		for (Container container : containers) {
			Assert.assertTrue(container.id().length()>10);
		}
	}
	
	@Test
	public void createAndRemoveContainerTest(){
		SeaClient client = new SeaClient(IslandNodeTest.HOST);
		Island island = client.getIsland(new IslandInfo("10.58.136.164:7777"));
		
		String name = "UThelloworld";
		String containerId = island.createContainer(ContainerConfig.builder().image("hello-world").build(), name);
		Assert.assertTrue(island.listContainers(ListContainersParam.allContainers(true)).stream().filter(c->c.id().equals(containerId)).count()>0);
		
		island.removeContainer(containerId);
		
		Assert.assertTrue(island.listContainers(ListContainersParam.allContainers(true)).stream().filter(c->c.id().equals(containerId)).count()==0);
		
	}
	
	@Test
	public void startAndStopContainerTest(){
		SeaClient client = new SeaClient(IslandNodeTest.HOST);
		Island island = client.getIsland(new IslandInfo("10.58.136.164:7777"));
		
		String name = "startandstop";
		
		try {
			String containerId = island.createContainer(ContainerConfig.builder().image("hello-world").build(), name);
			
			island.startContainer(containerId, null);
			
			Assert.assertTrue(island.listContainers(ListContainersParam.allContainers(true)).stream().filter(c->c.id().equals(containerId)).count()==1);

			island.stopContainer(containerId);
			
			Assert.assertTrue(island.listContainers(ListContainersParam.allContainers(false)).stream().filter(c->c.id().equals(containerId)).count()==0);
			Assert.assertTrue(island.listContainers(ListContainersParam.allContainers(true)).stream().filter(c->c.id().equals(containerId)).count()==1);

		}finally{
			island.removeContainer(name);
		}

	}
}
