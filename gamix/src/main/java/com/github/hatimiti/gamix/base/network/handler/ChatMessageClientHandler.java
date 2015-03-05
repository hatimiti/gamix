package com.github.hatimiti.gamix.base.network.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

import com.github.hatimiti.gamix.base.gui.swing.ChatDialog;
import com.github.hatimiti.gamix.base.network.JsonDatagramHandler;
import com.github.hatimiti.gamix.base.network.exchange.json.chat.ExchangeChatMessageJson;

/**
 * @author hatimiti
 *
 */
public class ChatMessageClientHandler
		extends JsonDatagramHandler<ExchangeChatMessageJson> {

	protected ChatDialog chatDialog;

	public ChatMessageClientHandler(final ChatDialog chatDialog) {
		this.chatDialog = chatDialog;
	}

	@Override
	protected void execute(
			final ExchangeChatMessageJson json,
			final ChannelHandlerContext ctx,
			final DatagramPacket packet) {

		this.chatDialog.setText(json.message);
	}

	@Override
	protected Class<ExchangeChatMessageJson> getExchangeClass() {
		return ExchangeChatMessageJson.class;
	}

}