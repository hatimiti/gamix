package com.github.hatimiti.gamix.app.game.field.network.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import com.github.hatimiti.gamix.base.gui.swing.ChatMessageContainer;
import com.github.hatimiti.gamix.base.network.handler.ChatMessageServerHandler;


public class ChatServer implements Runnable {

	private final int port;
	private final ChatMessageContainer chatMessageContainer;

	public ChatServer(final int port) {
		this.port = port;
		this.chatMessageContainer = new ChatMessageContainer();
	}

	@Override
	public void run() {
		System.out.println("ChatサーバーUDP開始");
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
				.channel(NioDatagramChannel.class)
				.option(ChannelOption.SO_BROADCAST, false)
				.handler(new ChatMessageServerHandler(this.chatMessageContainer));

			b.bind(this.port).sync().channel().closeFuture().await();

		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			group.shutdownGracefully();
		}
		System.out.println("ChatサーバーUDP終了");
	}
}
