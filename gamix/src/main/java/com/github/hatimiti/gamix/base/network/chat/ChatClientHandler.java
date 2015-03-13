package com.github.hatimiti.gamix.base.network.chat;

import io.netty.channel.ChannelHandlerContext;

import org.slf4j.Logger;

import com.github.hatimiti.gamix.base.network.JsonHandler;
import com.github.hatimiti.gamix.base.network.exchange.json.chat.ExchangeChatMessageJson;
import com.github.hatimiti.gamix.base.util._Util;

/**
 * @author hatimiti
 *
 */
class ChatClientHandler
		extends JsonHandler<ExchangeChatMessageJson, String> {

	private static final Logger LOG = _Util.getLogger();

	private ChatMessageSender sender;

	ChatClientHandler(ChatMessageSender sender) {
		this.sender = sender;
	}

	@Override
	protected void execute(
			final ExchangeChatMessageJson json,
			final ChannelHandlerContext ctx,
			final String packet) {

		ChatMessageContainer cc
			= ChatMessageContainer.getInstance(ChatMessageType.PUBLIC);

		LOG.debug("Client received message: {}", json.message);

		cc.addMessageTo("1", json.message);
		this.sender.receive(json.message);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected Class<ExchangeChatMessageJson> getExchangeClass() {
		return ExchangeChatMessageJson.class;
	}

	@Override
	protected String getContent(String packet) {
		return packet;
	}

}