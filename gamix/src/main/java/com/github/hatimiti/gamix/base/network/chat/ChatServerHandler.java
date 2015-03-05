package com.github.hatimiti.gamix.base.network.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

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
	
//	@Override
//	public void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
//		// Send the received message to all channels but the current one.
//		System.out.println("recieved message is [" + msg + "]");
//		for (Channel c: channels) {
//			if (c != ctx.channel()) {
//				c.writeAndFlush("[" + ctx.channel().remoteAddress() + "] " + msg + '\n');
//			} else {
//				c.writeAndFlush("[you] " + msg + '\n');
//			}
//		}
//
//		// Close the connection if the client has sent 'bye'.
//		if ("bye".equals(msg.toLowerCase())) {
//			ctx.close();
//		}
//	}

	@Override
	protected void execute(ExchangeChatMessageJson json,
			ChannelHandlerContext ctx, String packet) {
		// TODO 自動生成されたメソッド・スタブ
		
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