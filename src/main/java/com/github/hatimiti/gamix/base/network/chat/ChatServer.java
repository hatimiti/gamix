package com.github.hatimiti.gamix.base.network.chat;

import com.github.hatimiti.gamix.base.network.TCPServer;

public class ChatServer extends TCPServer {

	public ChatServer(int port) {
		super(port, new ChatServerInitializer());
	}

}
