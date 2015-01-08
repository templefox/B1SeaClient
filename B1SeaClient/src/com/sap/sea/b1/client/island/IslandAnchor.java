package com.sap.sea.b1.client.island;

public class IslandAnchor<T> {
	private final T value;
	private final TargetedIsland targetedIsland;
	
	public IslandAnchor(T value,TargetedIsland island) {
		this.value = value;
		this.targetedIsland = island;
	}

	public TargetedIsland self() {
		return targetedIsland;
	}

	public T value() {
		return value;
	}
}
