package com.sap.sea.b1.client.test;


import org.junit.Assert;
import org.junit.Test;

import com.sap.sea.b1.client.SeaClient;
import com.sap.sea.b1.client.island.Island;

public class RunB1ahTest {
	@Test
	public void runTest(){
		SeaClient client = new SeaClient("localhost:8080/Sea");
		Island island = client.getIsland("10.58.136.164:7777");
		
		island = island.prepareB1ah().image("luckydog:0.1").name("testRun").bind40000(40000).run();
		
		boolean has = island.listContainers(true).value().stream().anyMatch(c->c.names().stream().anyMatch(s->s.contains("testRun")));
		Assert.assertTrue(has);
		
		island.removeContainer("testRun", true);
		
		has = island.listContainers().value().stream().anyMatch(c->c.names().stream().anyMatch(s->s.contains("testRun")));
		Assert.assertTrue(!has);
	}
	
	@Test
	public void runKvm(){
		SeaClient client = new SeaClient("localhost:8080/Sea");
		Island island = client.getIsland("10.58.136.164:7777");
		
		island = island.prepareKvm().name("kvmTest2").bind3389(3399).run();
		island.prepareKvm().name("kvmTest3").bind3389(3399).run();
		island.removeContainer("kvmTest2",true);
		island.removeContainer("kvmTest3",true);
	}
}
