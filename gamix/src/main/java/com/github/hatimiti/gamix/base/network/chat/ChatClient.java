package com.github.hatimiti.gamix.base.network.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

import com.github.hatimiti.gamix.base.network.exchange.json.chat.ExchangeChatMessageJson;


public class ChatClient implements Runnable {

	private final InetSocketAddress serverAddress;

	public ChatClient(
			final InetSocketAddress serverAddress) {

		this.serverAddress = serverAddress;
	}

	public void start() {
		new Thread(this).start();
	}

	@Override
	public void run() {

		while (true) {

			EventLoopGroup group = new NioEventLoopGroup();
			try {
				Bootstrap b = new Bootstrap();
				b.group(group)
					.channel(NioSocketChannel.class)
					.handler(new ChatClientHandler());

				Channel ch = b.connect(this.serverAddress).sync().channel();
				
				ChannelFuture lastWriteFuture = null;
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				for (;;) {
					String line = in.readLine();
					if (line == null) {
						break;
					}

					// Sends the received line to the server.
					lastWriteFuture = ch.writeAndFlush(line + "\r\n");

					// If user typed the 'bye' command, wait until the server closes
					// the connection.
					if ("bye".equals(line.toLowerCase())) {
						ch.closeFuture().sync();
						break;
					}
				}
				ExchangeChatMessageJson json = new ExchangeChatMessageJson();
//				json.message = this.chatDialog.extractEstablishedText();

//				ch.write(new DatagramPacket(
//						Unpooled.copiedBuffer(JSON.encode(json), CharsetUtil.UTF_8),
//						this.serverAddress)).sync();

				ch.closeFuture().sync();

			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} finally {
				group.shutdownGracefully();
			}
		}
	}
}
