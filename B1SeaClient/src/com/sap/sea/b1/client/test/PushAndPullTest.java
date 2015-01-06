package com.sap.sea.b1.client.test;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.sap.sea.b1.client.SeaClient;
import com.sap.sea.b1.client.island.Island;
import com.sap.sea.b1.client.island.IslandInfo;
import com.spotify.docker.client.messages.Image;

public class PushAndPullTest {
	@Test
	public void pullTest() {
		SeaClient client = new SeaClient(IslandNodeTest.HOST);
		Island island = client.getIsland(new IslandInfo("10.58.136.164:7777"));

		String hello_image = "10.58.77.129:5000/hello";
		
		if (island.listImages().stream().anyMatch(image -> image.repoTags().stream().anyMatch(s->s.startsWith(hello_image)))) {
			island.removeImage(hello_image);
		}

		Assert.assertFalse(island.listImages().stream().anyMatch(image -> image.repoTags().contains(hello_image)));

		island.pullImage(hello_image);

		Assert.assertTrue(island.listImages().stream()
				.anyMatch(image -> image.repoTags().stream().anyMatch(s -> s.startsWith(hello_image))));
	}
	
	@Test
	public void tagAndpushTest(){
		SeaClient client = new SeaClient(IslandNodeTest.HOST);
		Island island = client.getIsland(new IslandInfo("10.58.136.164:7777"));

		String hello_image = "10.58.77.129:5000/hello";
		
		//Image image = island.listImages().stream().filter(i->i.repoTags().stream().anyMatch(s->s.startsWith(hello_image))).findFirst().get();
		
		island.pullImage(hello_image);
		String tag = "10.58.77.129:5000/hello2";
		island.tagImage(hello_image,tag);
		
		Assert.assertTrue(island.listImages().stream().anyMatch(i->i.repoTags().stream().anyMatch(s->s.startsWith(tag))));
	
		island.pushImage(tag);
	}
	
	@Ignore
	@Test
	public void pullBigImageTest(){
		SeaClient client = new SeaClient(IslandNodeTest.HOST);
		Island island = client.getIsland(new IslandInfo("10.58.136.166:7777"));
		
		String bigImage = "10.58.77.129:5000/hana:74.3";
		
		island.pullImage(bigImage);
	}
}
