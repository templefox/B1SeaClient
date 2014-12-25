package com.sap.sea.b1.client.test;

import org.junit.Assert;
import org.junit.Test;

import com.sap.sea.b1.client.SeaClient;
import com.sap.sea.b1.client.island.InconstantIsland;
import com.sap.sea.b1.client.island.Island;
import com.sap.sea.b1.client.selector.*;

public class SelectorTest {

	@Test
	public void RandomSelectorPingTest(){
		SeaClient client = new SeaClient("localhost:8080");
		
		Island island = client.select(new RandomSelector());
		
		Assert.assertTrue(island.ping());
		
	}
	
	@Test
	public void FilterSelectorPingTest(){
		SeaClient client = new SeaClient("localhost:8080");
		
		InconstantIsland island = client.select(new FilterSelector(0L));
		
		Assert.assertTrue(island.ping());
		
		InconstantIsland island2 = client.select(new FilterSelector(Long.MAX_VALUE));
		Assert.assertFalse(island2.ping());
		
	}
}
