package com.sap.sea.b1.client.island;

import com.sap.sea.b1.client.Node;
import com.sap.sea.b1.client.SeaClient;
import com.sap.sea.b1.client.selector.Selector;

public class TargetedIsland extends Island{
	private static final String SUB_PATH = "/island/";
	private Node node;
	public TargetedIsland(SeaClient client,IslandInfo info) {
		super(client.getPath()+SUB_PATH+info.gethost());
		setHost(info.gethost());		
	}
	
	@Override
	public Node node() {
		if (node==null) {
			node = new Node(this);
		}
		return node;
	}
}
