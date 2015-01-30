package com.sap.sea.b1.client.test;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;

import com.sap.sea.b1.client.SeaClient;
import com.sap.sea.b1.client.data.BuildLog;
import com.sap.sea.b1.client.island.Island;
import com.sap.sea.b1.client.island.IslandInfo;

public class BuildTest {
	@Test
	public void getImageID() {
		BuildLog log = new BuildLog("Removing intermediate container 060b6428bd07 Successfully built 455f2b7f013e");

		Assert.assertTrue(log.getImageID().equals("455f2b7f013e"));
	}

	@Test
	public void buildTest() {
		SeaClient client = new SeaClient(IslandNodeTest.HOST);
		//SeaClient client = new SeaClient("10.58.77.129:20050");
		
		
		IslandInfo info = new IslandInfo("10.58.136.164:7777");

		Island island = client.getIsland(info);

		BuildLog log = island.build("/home/dockerbuild","buildtest");
		System.out.println(log);
		Assert.assertFalse(log == null);
		
		boolean has = island.listImages().value().stream().anyMatch(i->i.repoTags().stream().anyMatch(s->s.startsWith("buildtest")));

		Assert.assertTrue(has);
		
		String id = log.getImageID();
		Assert.assertFalse(id == null);
		Assert.assertFalse(id.isEmpty());

		List<String> removedImages = island.removeImage(id).value();
		Assert.assertTrue(removedImages.stream().anyMatch(s->s.startsWith(id)));
	}
	
	@Test
	public void buildFailTest(){
		SeaClient client = new SeaClient("localhost:8080/Sea");
		
		IslandInfo info = new IslandInfo("10.58.136.164:7777");

		Island island = client.getIsland(info);

		BuildLog log = island.build("/home/dockerbuild","aa");
		Assert.assertFalse(log == null);

		String id = log.getImageID();
		//Assert.assertFalse(id != null);

		System.out.println(log.getLog());
	}
}
