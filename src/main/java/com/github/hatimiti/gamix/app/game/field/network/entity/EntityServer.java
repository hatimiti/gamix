package com.github.hatimiti.gamix.app.game.field.network.entity;

import com.github.hatimiti.gamix.base.network.TCPServer;

public class EntityServer extends TCPServer {

	public EntityServer(final int port) {
		super(port, new EntityServerInitializer());
	}

}
