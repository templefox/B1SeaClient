package com.sap.sea.b1.client;

import java.util.Arrays;
import java.util.List;

import com.sap.sea.b1.client.island.Island;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerException;
import com.spotify.docker.client.messages.RemovedImage;

public class Docker {
	private static final String SUB_PATH = "/docker";
	private String path;
	
	public Docker(Island island) {
		path = island.getPath()+SUB_PATH;
	}
	
	public List<String> removeImage(String image){
		DockerClient client = new DefaultDockerClient(path);
		
		try {
			List<RemovedImage> removedImages = client.removeImage(image, true, false);
			return Arrays.asList(removedImages.stream().map((images)->images.imageId()).toArray(String[]::new));
		} catch (DockerException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
