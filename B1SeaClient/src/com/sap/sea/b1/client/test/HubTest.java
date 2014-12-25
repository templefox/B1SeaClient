package com.sap.sea.b1.client.test;

import org.junit.Assert;
import org.junit.Test;

import com.sap.sea.b1.client.Hub;
import com.sap.sea.b1.client.SeaClient;


public class HubTest {

	@Test
	public void getSetHubTest() {
		SeaClient client = new SeaClient("localhost:8080");
		Hub hub = client.getHub();
		
		String host = hub.getHost();
		String tempHost = "xxx";
		hub.setHost(tempHost);
		
		Assert.assertTrue(client.getHub().getHost().equals(tempHost));
		
		hub.setHost(host);
	}
	
}
