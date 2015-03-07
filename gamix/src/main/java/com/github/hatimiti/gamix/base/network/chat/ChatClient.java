package com.github.hatimiti.gamix.base.network.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

import net.arnx.jsonic.JSON;

import com.github.hatimiti.gamix.base.network.exchange.json.chat.ExchangeChatMessageJson;
import com.github.hatimiti.gamix.base.util.Strings;


public class ChatClient
		implements Runnable {

	private InetSocketAddress serverAddress;
	private ChatMessageSender messageSender;

	public ChatClient(
			final InetSocketAddress serverAddress,
			final ChatMessageSender messageSender) {

		this.serverAddress = serverAddress;
		this.messageSender = messageSender;
	}

	public void start() {
		new Thread(this).start();
	}

	@Override
	public void run() {

		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
				.channel(NioSocketChannel.class)
				.handler(new ChatClientInitializer());

			Channel ch = b.connect(this.serverAddress).sync().channel();
			
			for (;;) {
			
				String message = null;
				synchronized (this) {
					wait();
					message = this.messageSender.notifyMessage();
				}
				
				if (Strings.isNullOrEmpty(message)) {
					ch.closeFuture().sync();
					return;
				}
					
				ExchangeChatMessageJson json = new ExchangeChatMessageJson();
				json.message = message;
				
				// Sends the received line to the server.
				ChannelFuture lastWriteFuture = ch.writeAndFlush(JSON.encode(json) + "\r\n");
	
				if (lastWriteFuture != null) {
					lastWriteFuture.sync();
				}
			}
			
		} catch (InterruptedException e) {
			System.out.println("tetetet");
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}

	public synchronized void startThread() {
		notify();
	}

}
