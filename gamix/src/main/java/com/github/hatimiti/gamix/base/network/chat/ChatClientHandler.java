package com.github.hatimiti.gamix.base.network.chat;

import io.netty.channel.ChannelHandlerContext;

import com.github.hatimiti.gamix.base.network.JsonHandler;
import com.github.hatimiti.gamix.base.network.exchange.json.chat.ExchangeChatMessageJson;

/**
 * @author hatimiti
 *
 */
class ChatClientHandler
		extends JsonHandler<ExchangeChatMessageJson, String> {

	private ChatMessageSender sender;

	ChatClientHandler(ChatMessageSender sender) {
		this.sender = sender;
	}

	@Override
	protected void execute(
			final ExchangeChatMessageJson json,
			final ChannelHandlerContext ctx) {

		ChatMessageContainer cc = ChatMessageContainer.getInstance(ChatMessageType.PUBLIC);
		cc.addMessageTo("1", json.message);

		this.sender.receiveMessage(json.message);
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