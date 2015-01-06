package com.sap.sea.b1.client.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.sap.sea.b1.client.SeaClient;
import com.sap.sea.b1.client.island.Island;
import com.sap.sea.b1.client.island.IslandInfo;
import com.spotify.docker.client.DockerClient.ListImagesParam;
import com.spotify.docker.client.messages.Image;

public class IslandImageTest {

	@Test
	public void ListImages(){
		SeaClient client = new SeaClient(IslandNodeTest.HOST);
		Island island = client.getIsland(new IslandInfo("10.58.136.164:7777"));
		
		List<Image> images = island.listImages(ListImagesParam.allImages(false));
		
		images.stream().forEach(image->Assert.assertTrue(image.id().length()>10));
	}
}
