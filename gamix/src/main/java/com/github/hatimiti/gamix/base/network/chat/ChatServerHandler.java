package com.github.hatimiti.gamix.base.network.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.val;

import com.github.hatimiti.gamix.base.network.JsonHandler;
import com.github.hatimiti.gamix.base.network.exchange.json.chat.ExchangeChatMessageJson;

class ChatServerHandler
		extends JsonHandler<ExchangeChatMessageJson, String> {

	/** 接続中のクライアント */
	private static final ChannelGroup channels
		= new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	@Override
	public void channelActive(final ChannelHandlerContext ctx) {
		channels.add(ctx.channel());
	}
	
	@Override
	protected void execute(ExchangeChatMessageJson json,
			ChannelHandlerContext ctx, String packet) {
		
		System.out.println("packet=" + packet);
		
		val container = ChatMessageContainer.getInstance(ChatMessageType.PUBLIC);
//		container.addMessageTo(key, message);
		
		for (Channel c: channels) {
			if (c != ctx.channel()) {
				// Sends message to other members
				c.writeAndFlush("[" + ctx.channel().remoteAddress() + "] " + packet + '\n');
			} else {
				// Sends message to myself
				c.writeAndFlush("[you] " + packet + '\n');
			}
		}
		
	}

	@Override
	protected Class<ExchangeChatMessageJson> getExchangeClass() {
		return ExchangeChatMessageJson.class;
	}

	@Override
	protected String getContent(String packet) {
		return packet;
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}

//@Override
//protected void execute(
//		final ExchangeChatMessageJson json,
//		final ChannelHandlerContext ctx,
//		final DatagramPacket packet) {
//
//	if (!Strings.isNullOrEmpty(json.message)) {
//		this.container.addMessageTo(1, json.message);
//	}
//
//	ExchangeChatMessageJson exJson = new ExchangeChatMessageJson();
//	exJson.message = this.container.getMessageOf(1);
//
//	ctx.write(new DatagramPacket(
//			Unpooled.copiedBuffer(JSON.encode(exJson), CharsetUtil.UTF_8),
//			packet.sender()));
//}