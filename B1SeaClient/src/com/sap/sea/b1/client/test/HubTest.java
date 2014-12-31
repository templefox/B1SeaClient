package com.sap.sea.b1.client.test;

import org.junit.Assert;
import static com.sap.sea.b1.client.test.IslandNodeTest.HOST;
import org.junit.Test;

import com.sap.sea.b1.client.Hub;
import com.sap.sea.b1.client.SeaClient;


public class HubTest {

	@Test
	public void getSetHubTest() {
		SeaClient client = new SeaClient(HOST);
		Hub hub = client.getHub();
		
		String host = hub.getHost();
		String tempHost = "xxx";
		hub.setHost(tempHost);
		
		Assert.assertTrue(client.getHub().getHost().equals(tempHost));
		
		hub.setHost(host);
	}
	
}
