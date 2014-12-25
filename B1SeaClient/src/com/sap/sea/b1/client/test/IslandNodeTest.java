package com.sap.sea.b1.client.test;

import java.util.Iterator;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.sap.sea.b1.client.Node;
import com.sap.sea.b1.client.SeaClient;
import com.sap.sea.b1.client.island.Island;
import com.sap.sea.b1.client.island.IslandInfo;
import com.sap.sea.b1.client.island.TargetedIsland;

public class IslandNodeTest {
	
	@Test
	public void initializeTest(){
		SeaClient client = new SeaClient("localhost:8080");
		
		Set<TargetedIsland> islands = client.getIslands();
		
		Assert.assertTrue(islands.size()>0);
	}
	
	@Test
	public void pingTest(){
		SeaClient client = new SeaClient("localhost:8080");

		Set<? extends Island> islands = client.getIslands();
		
		for (Iterator<? extends Island> iterator = islands.iterator(); iterator.hasNext();) {
			Island island = iterator.next();
			
			Assert.assertTrue(island.ping());
		}
	}
	
	@Test
	public void nodeStatusTest(){
		SeaClient client = new SeaClient("localhost:8080");
		
		Set<TargetedIsland> islands = client.getIslands();

		for (Iterator<TargetedIsland> iterator = islands.iterator(); iterator.hasNext();) {
			TargetedIsland island = iterator.next();
			
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
