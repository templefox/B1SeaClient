package com.sap.sea.b1.client.test;

import java.util.Iterator;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.sap.sea.b1.client.Island;
import com.sap.sea.b1.client.IslandInfo;
import com.sap.sea.b1.client.Node;
import com.sap.sea.b1.client.SeaClient;

public class BasicTest {
	
	@Test
	public void initializeTest(){
		SeaClient client = new SeaClient("localhost:8080");
		
		Set<IslandInfo> islands = client.getIslands();
		
		Assert.assertTrue(islands.size()>0);
	}
	
	@Test
	public void nodeStatusTest(){
		SeaClient client = new SeaClient("localhost:8080");
		
		Set<IslandInfo> islands = client.getIslands();

		for (Iterator iterator = islands.iterator(); iterator.hasNext();) {
			IslandInfo islandInfo = (IslandInfo) iterator.next();
			Island island = islandInfo.fromSea(client);
			
			Node node = island.node();
			double usage = node.getMemUsage();
			Assert.assertTrue(usage<1);
			Assert.assertTrue(usage>0);
			
			long used = node.getMemUsed();
			long total = node.getMemTotal();
			long free = node.getMemFree();
			
			Assert.assertTrue(Math.ulp(total*0.1)==Math.ulp((used+free)*0.1));
		}
	}
}
