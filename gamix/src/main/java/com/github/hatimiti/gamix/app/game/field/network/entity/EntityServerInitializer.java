package com.github.hatimiti.gamix.app.game.field.network.entity;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;

class EntityServerInitializer extends ChannelInitializer<SocketChannel> {

	protected EntityContainer container;

	public EntityServerInitializer(final EntityContainer container) {
		this.container = container;
	}
	
	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		// On top of the SSL handler, add the text line codec.
		pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		pipeline.addLast(new StringDecoder());
		pipeline.addLast(new StringEncoder());

		// and then business logic.
		pipeline.addLast(new EntityServerHandler(this.container));
	}
}