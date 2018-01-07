package com.github.hatimiti.gamix.base.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.Optional;

import org.slf4j.Logger;

import com.github.hatimiti.gamix.base.GamixRuntimeException;
import com.github.hatimiti.gamix.base.util._Util;

public abstract class TCPClient<R> implements Runnable {

	private static final Logger LOG = _Util.getLogger();

	private final InetSocketAddress serverAddress;
	private final int updateInterval;
	private final ChannelHandler channelHandler;

	protected boolean isStarted;

	protected TCPClient(
			final InetSocketAddress serverAddress,
			final int updateInterval,
			final ChannelHandler channelHandler) {

		this.serverAddress = serverAddress;
		this.updateInterval = updateInterval;
		this.channelHandler = channelHandler;
	}
	
	public void start() {
		this.isStarted = true;
		new Thread(this).start();
	}
	
	public synchronized void resume() {
		notify();
	}
	
	public void stop() {
		this.isStarted = false;
		resume();
	}
	
	@Override
	public void run() {

		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
				.channel(NioSocketChannel.class)
				.handler(this.channelHandler);

			Channel ch = null;
			try {
				ch = b.connect(this.serverAddress).sync().channel();
			} catch (Exception e) {
				LOG.warn("Can't connect to server: " + e.getMessage());
				return;
			}
			
			for (;;) {
				
				Thread.sleep(this.updateInterval);
			
				Optional<R> ret = execute(ch);
				
				if (!ret.isPresent()) {
					return;
				}
				
				ChannelFuture lastWriteFuture
					= ch.writeAndFlush(prepareWrite(ret.get()));
	
				if (lastWriteFuture != null) {
					lastWriteFuture.sync();
				}
			}
			
		} catch (InterruptedException e) {
			throw new GamixRuntimeException(e);
		} finally {
			group.shutdownGracefully();
		}
	}

	protected abstract Optional<R> execute(Channel ch);
	protected abstract Object prepareWrite(R value);
}
