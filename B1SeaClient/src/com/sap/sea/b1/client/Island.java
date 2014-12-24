package com.sap.sea.b1.client;

public class Island {
	private static final String SUB_PATH = "/island/";
	private String path;
	
	private Node node;
	
	public Island(SeaClient client,IslandInfo info) {
		path = client.getPath()+SUB_PATH+info.getIp();
	}

	public String getPath() {
		return path;
	}

	public Node node() {
		if (node==null) {
			node = new Node(this);
		}
		return node;
	}
}
