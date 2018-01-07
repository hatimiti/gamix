package com.github.hatimiti.gamix.app.game.field.entity;


public final class ServerEntityContainer extends EntityContainer {

	private static ServerEntityContainer instance;

	public static synchronized ServerEntityContainer serverEntityContainer() {
		if (instance == null) {
			instance = new ServerEntityContainer();
		}
		return instance;
	}
}