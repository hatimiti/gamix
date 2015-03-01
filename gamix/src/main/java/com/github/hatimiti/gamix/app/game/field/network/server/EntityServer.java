package com.github.hatimiti.gamix.app.game.field.network.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;
import com.github.hatimiti.gamix.app.game.field.network.handler.EntityServerHandler;

public class EntityServer implements Runnable {

	private final int port;
	private final EntityContainer entityContainer;

	public EntityServer(final int port, final EntityContainer entityContainer) {
		this.port = port;
		this.entityContainer = entityContainer;
	}

	@Override
	public void run() {
		System.out.println("EntityサーバーUDP開始");
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
				.channel(NioDatagramChannel.class)
				.option(ChannelOption.SO_BROADCAST, false)
				.handler(new EntityServerHandler(this.entityContainer));

			b.bind(this.port).sync().channel().closeFuture().await();

		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			group.shutdownGracefully();
		}
		System.out.println("EntityサーバーUDP終了");
	}

}
