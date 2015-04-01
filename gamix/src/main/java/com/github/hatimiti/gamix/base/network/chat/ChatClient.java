package com.github.hatimiti.gamix.base.network.chat;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.Optional;

import net.arnx.jsonic.JSON;

import org.slf4j.Logger;

import com.github.hatimiti.gamix.base.network.TCPClient;
import com.github.hatimiti.gamix.base.network.exchange.json.chat.ExchangeChatMessageJson;
import com.github.hatimiti.gamix.base.util._Util;


public class ChatClient extends TCPClient<ExchangeChatMessageJson> {

	private static final Logger LOG = _Util.getLogger();

	private ChatMessageSender messageSender;
	
	public ChatClient(
			final InetSocketAddress serverAddress,
			final ChatMessageSender messageSender) {

		super(serverAddress, 0, new ChatClientInitializer(messageSender));
		this.messageSender = messageSender;
	}

	@Override
	protected Optional<ExchangeChatMessageJson> execute(Channel ch) {
		String message = null;
		synchronized (this) {
			if (this.isStarted) {
				try {
					wait();
				} catch (InterruptedException e) {
					return Optional.empty();
				}
			}
			message = this.messageSender.send();
			LOG.info("input message is {}.", message);
		}

		if (_Util.isNullOrEmpty(message) || !this.isStarted) {
			ch.closeFuture();
			LOG.info("Closed connection to server.");
			return Optional.empty();
		}

		ExchangeChatMessageJson json = new ExchangeChatMessageJson();
		json.message = message;
		
		return Optional.of(json);
	}

	@Override
	protected Object prepareWrite(ExchangeChatMessageJson value) {
		return JSON.encode(value) + "\r\n";
	}

}
