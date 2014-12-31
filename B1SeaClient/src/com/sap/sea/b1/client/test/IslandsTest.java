package com.sap.sea.b1.client.test;

import java.util.Set;

import static com.sap.sea.b1.client.test.IslandNodeTest.HOST;

import org.junit.Assert;
import org.junit.Test;

import com.sap.sea.b1.client.SeaClient;
import com.sap.sea.b1.client.island.Island;
import com.sap.sea.b1.client.island.IslandInfo;
import com.sap.sea.b1.client.island.TargetedIsland;

public class IslandsTest {
	@Test
	public void getIslands() {
		SeaClient client = new SeaClient(HOST);

		Set<Island> islands = client.getIslands();

		Assert.assertTrue(islands.size() > 0);
	}

	@Test
	public void putAndDelete() {
		SeaClient client = new SeaClient(HOST);

		String host = "10.58.136:7777";
		IslandInfo info = new IslandInfo(host, "root", "User@123");
		client.putIslands(info);

		Set<Island> islands = client.getIslands();
		boolean has = false;
		for (Island island : islands) {
			if (island.getHost().equals(host)) {
				has = true;
			}
		}
		Assert.assertTrue(has);
		
		
		
		client.deleteIslands(host);
		
		Set<Island> islands2 = client.getIslands();
		boolean nothas = true;
		for (Island island2 : islands2) {
			if (island2.getHost().equals(host)) {
				nothas = false;
			}
		}
		Assert.assertTrue(nothas);
	}
}
