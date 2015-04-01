package com.github.hatimiti.gamix.base.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import com.github.hatimiti.gamix.base.GamixRuntimeException;

public abstract class TCPServer implements Runnable {

	private final int port;
	private final ChannelHandler channelHandler;

	protected TCPServer(
			final int port,
			final ChannelHandler channelHandler) {

		this.port = port;
		this.channelHandler = channelHandler;
	}
	
	public void start() {
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(channelHandler);

			b.bind(this.port).sync().channel().closeFuture().sync();
			
		} catch (InterruptedException e) {
			throw new GamixRuntimeException(e);
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
