package com.github.hatimiti.gamix.app.game.field.network.entity;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;
import com.github.hatimiti.gamix.base.GamixRuntimeException;

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
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new EntityServerInitializer(this.entityContainer));

			b.bind(this.port).sync().channel().closeFuture().sync();
			
		} catch (InterruptedException e) {
			throw new GamixRuntimeException(e);
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
		System.out.println("EntityサーバーUDP終了");
	}

}
