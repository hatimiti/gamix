package com.github.hatimiti.gamix.base.network.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

import net.arnx.jsonic.JSON;

import org.slf4j.Logger;

import com.github.hatimiti.gamix.base.network.exchange.json.chat.ExchangeChatMessageJson;
import com.github.hatimiti.gamix.base.util._Util;


public class ChatClient implements Runnable {

	private static final Logger LOG = _Util.getLogger();

	private InetSocketAddress serverAddress;
	private ChatMessageSender messageSender;
	private boolean isStarted;

	public ChatClient(
			final InetSocketAddress serverAddress,
			final ChatMessageSender messageSender) {

		this.serverAddress = serverAddress;
		this.messageSender = messageSender;
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
				.handler(new ChatClientInitializer(this.messageSender));

			Channel ch = null;
			try {
				ch = b.connect(this.serverAddress).sync().channel();
			} catch (Exception e) {
				LOG.warn("Can't connect to server: " + e.getMessage());
				return;
			}

			for (;;) {

				String message = null;
				synchronized (this) {
					if (this.isStarted) {
						wait();
					}
					message = this.messageSender.send();
					LOG.info("input message is {}.", message);
				}

				if (_Util.isNullOrEmpty(message) || !this.isStarted) {
					ch.closeFuture();
					LOG.info("Closed connection to server.");
					return;
				}

				ExchangeChatMessageJson json = new ExchangeChatMessageJson();
				json.message = message;

				// Sends the received message to the server.
				ChannelFuture lastWriteFuture = ch.writeAndFlush(JSON.encode(json) + "\r\n");

				if (lastWriteFuture != null) {
					lastWriteFuture.sync();
				}
			}

		} catch (InterruptedException e) {
			LOG.warn("Cause Unknown: ", e);
		} finally {
			group.shutdownGracefully();
		}
	}

}
