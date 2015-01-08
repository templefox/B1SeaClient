package com.sap.sea.b1.client.test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.JerseyWebTarget;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import com.sap.sea.b1.client.SeaClient;
import com.sap.sea.b1.client.data.BuildLog;
import com.sap.sea.b1.client.island.Island;
import com.sap.sea.b1.client.island.IslandInfo;
import com.spotify.docker.client.LogsResponseReader;
import com.spotify.docker.client.ObjectMapperProvider;
import com.spotify.docker.client.ProgressResponseReader;
import com.spotify.docker.client.messages.RemovedImage;

public class BuildTest {
	@Test
	public void getImageID() {
		BuildLog log = new BuildLog("Removing intermediate container 060b6428bd07 Successfully built 455f2b7f013e");

		Assert.assertTrue(log.getImageID().equals("455f2b7f013e"));
	}

	@Test
	public void buildTest() {
		//SeaClient client = new SeaClient("localhost:8080/Sea");
		SeaClient client = new SeaClient("10.58.77.129:20050");
		
		
		IslandInfo info = new IslandInfo("10.58.136.164:7777");

		Island island = client.getIsland(info);

		BuildLog log = island.build("/home/dockerbuild");
		Assert.assertFalse(log == null);

		String id = log.getImageID();
		Assert.assertFalse(id == null);
		Assert.assertFalse(id.isEmpty());

		List<String> removedImages = island.removeImage(id).value();
		Assert.assertTrue(removedImages.get(0).startsWith(id));
	}
}
