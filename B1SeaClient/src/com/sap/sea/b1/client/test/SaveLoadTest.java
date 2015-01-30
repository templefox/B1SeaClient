package com.sap.sea.b1.client.test;

import org.junit.Assert;
import org.junit.Test;

import com.sap.sea.b1.client.SeaClient;
import com.sap.sea.b1.client.island.Island;
import com.sap.sea.b1.client.island.IslandInfo;

public class SaveLoadTest {
	@Test
	public void savetest(){
		SeaClient client = new SeaClient("localhost:8080/Sea");
		Island island = client.getIsland(new IslandInfo("10.58.136.164:7777"));
		
		island.save("hello-world","/home/dockerbuild/testbuild.tar");
	}
	
	@Test
	public void loadTest(){
		SeaClient client = new SeaClient("localhost:8080/Sea");
		Island island = client.getIsland(new IslandInfo("10.58.136.164:7777"));
		
		Assert.assertTrue(island.load("/home/dockerbuild/testbuild.tar").value());
	}
}
