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

	@Override
	protected void execute(
			final ExchangeChatMessageJson json,
			final ChannelHandlerContext ctx,
			final String packet) {

		// TODO メッセージをコンテナに設定
	}

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