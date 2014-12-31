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
	
	public static final String HOST = "10.58.77.129:20050";
	
	/**
	 * island.ping();
	 */
	@Test
	public void pingTest(){
		SeaClient client = new SeaClient(HOST);

		Set<? extends Island> islands = client.getIslands();
		
		for (Iterator<? extends Island> iterator = islands.iterator(); iterator.hasNext();) {
			Island island = iterator.next();
			
			Assert.assertTrue(island.ping());
		}
	}
	
	/**
	 * island.node().getHostName()
	 */
	@Test
	public void hostnameTest(){
		SeaClient client = new SeaClient(HOST);

		Set<Island> islands = client.getIslands();
		
		for (Island targetedIsland : islands) {
			Assert.assertTrue(targetedIsland.node().getHostName()!=null);
		}
		
	}
	
	/**
	 * island.node().getMemUsed();
	 */
	@Test
	public void nodeStatusTest(){
		SeaClient client = new SeaClient(HOST);
		
		Set<Island> islands = client.getIslands();

		for (Iterator<Island> iterator = islands.iterator(); iterator.hasNext();) {
			Island island = iterator.next();
			
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
