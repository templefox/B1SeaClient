package com.sap.sea.b1.client.test;

import java.util.Set;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import com.sap.sea.b1.client.SeaClient;
import com.sap.sea.b1.client.data.BuildLog;
import com.sap.sea.b1.client.island.Island;
import com.sap.sea.b1.client.island.IslandInfo;

public class BuildTest {
	@Test
	public void getImageID(){
		BuildLog log = new BuildLog("Removing intermediate container 060b6428bd07 Successfully built 455f2b7f013e");
		
		Assert.assertTrue(log.getImageID().equals("455f2b7f013e"));
	}
	
	@Test
	public void buildTest(){
		SeaClient client = new SeaClient("localhost:8080/Sea");
		
		IslandInfo info = new IslandInfo("10.58.136.164:7777");
		
		Island island = client.getIsland(info);
		
		BuildLog log = island.node().build("/home/dockerbuild");
		System.out.println(log);
		Assert.assertFalse(log==null);
		
		String id = log.getImageID();
		Assert.assertFalse(id==null);
		Assert.assertFalse(id.isEmpty());
	}
}
