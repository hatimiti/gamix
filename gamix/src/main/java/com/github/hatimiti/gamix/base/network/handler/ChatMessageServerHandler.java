package com.github.hatimiti.gamix.base.network.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import net.arnx.jsonic.JSON;

import com.github.hatimiti.gamix.base.gui.swing.ChatMessageContainer;
import com.github.hatimiti.gamix.base.network.JsonDatagramHandler;
import com.github.hatimiti.gamix.base.network.exchange.json.chat.ExchangeChatMessageJson;
import com.github.hatimiti.gamix.base.util.Strings;

/**
 * @author hatimiti
 *
 */
public class ChatMessageServerHandler
		extends JsonDatagramHandler<ExchangeChatMessageJson> {

	protected ChatMessageContainer container;

	public ChatMessageServerHandler(final ChatMessageContainer container) {
		this.container = container;
	}

	@Override
	protected void execute(
			final ExchangeChatMessageJson json,
			final ChannelHandlerContext ctx,
			final DatagramPacket packet) {

		if (!Strings.isNullOrEmpty(json.message)) {
			this.container.addMessageTo(1, json.message);
		}

		ExchangeChatMessageJson exJson = new ExchangeChatMessageJson();
		exJson.message = this.container.getMessageOf(1);

		ctx.write(new DatagramPacket(
				Unpooled.copiedBuffer(JSON.encode(exJson), CharsetUtil.UTF_8),
				packet.sender()));
	}

	@Override
	protected Class<ExchangeChatMessageJson> getExchangeClass() {
		return ExchangeChatMessageJson.class;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg)
			throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}