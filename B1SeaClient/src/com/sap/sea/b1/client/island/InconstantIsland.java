package com.sap.sea.b1.client.island;

import com.sap.sea.b1.client.Node;
import com.sap.sea.b1.client.SeaClient;
import com.sap.sea.b1.client.selector.Selector;

public class InconstantIsland extends Island{
	public InconstantIsland(SeaClient client,Selector selector) {
		super(client.getPath()+selector.getPath());
	}

	@Override
	public Node node() {
		throw new NoSuchMethodError("Cannot visit node through InconstantIsland");
	}
}
