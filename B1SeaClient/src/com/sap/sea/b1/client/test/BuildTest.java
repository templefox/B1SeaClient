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
	public void getImageID(){
		BuildLog log = new BuildLog("Removing intermediate container 060b6428bd07 Successfully built 455f2b7f013e");
		
		Assert.assertTrue(log.getImageID().equals("455f2b7f013e"));
	}
	
	@Test
	public void buildTest(){
		SeaClient client = new SeaClient("localhost:8080/Sea");
		
		IslandInfo info = new IslandInfo("192.168.1.106:7777");
		
		Island island = client.getIsland(info);
		
		BuildLog log = island.node().build("/home/dockerbuild");
		System.out.println(log);
		Assert.assertFalse(log==null);
		
		String id = log.getImageID();
		Assert.assertFalse(id==null);
		Assert.assertFalse(id.isEmpty());
		
		island.docker().removeImage(id);
	}
	  private static final ClientConfig DEFAULT_CONFIG = new ClientConfig(
		      ObjectMapperProvider.class,
		      JacksonFeature.class,
		      LogsResponseReader.class,
		      ProgressResponseReader.class);
	@Test
	public void redirectTest(){       
		
		final ClientConfig config = DEFAULT_CONFIG
	.connectorProvider(new ApacheConnectorProvider());
		Client client = ClientBuilder.newClient(config);
		WebTarget webTarget = client.target("http://localhost:8080/Sea/island/192.168.1.106:7777/docker/v1.12/images/4f9066f37bd1?force=true&noprune=false");
		try {
			System.out.println(webTarget.request().async().method("DELETE", new GenericType<List<RemovedImage>>() {}).get());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
