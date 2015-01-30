package com.sap.sea.b1.client.test;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import com.sap.sea.b1.client.SeaClient;
import com.sap.sea.b1.client.island.Island;
import com.sap.sea.b1.client.island.IslandAnchor;
import com.sap.sea.b1.client.selector.RandomSelector;
import com.spotify.docker.client.messages.Image;

public class Inconstant2TargetedTest {
	@Test
	public void convertThroughListImages() {
		SeaClient client = new SeaClient(IslandNodeTest.HOST);
		Island randomIsland = client.select(new RandomSelector());

		IslandAnchor<List<Image>> anchor1 = randomIsland.listImages();

		Island targetedIsland = anchor1.self();

		IslandAnchor<List<Image>> anchor2 = targetedIsland.listImages();

		anchor1.value()
				.forEach(i -> Assert.assertTrue(anchor2.value().stream().anyMatch(i2 -> i.id().equals(i2.id()))));
	}

	@Test
	public void concurrentTest() {
		int num = 25;
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(num);
		for (int i = 0; i < num; i++) {
			pool.schedule(new Runnable() {
				@Override
				public void run() {
					convertThroughListImages();
				}
			}, (long) Math.random(), TimeUnit.SECONDS);
		}
		try {
			pool.shutdown();
			pool.awaitTermination(3, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
