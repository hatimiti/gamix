package com.github.hatimiti.gamix.base.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;

import com.github.hatimiti.gamix.base.network.exchange.BaseExchangeJson;
import com.github.hatimiti.gamix.base.util.Strings;

/**
 * @author hatimiti
 *
 */
public abstract class JsonDatagramHandler<J extends BaseExchangeJson>
		extends SimpleChannelInboundHandler<DatagramPacket> {

	public void messageReceived(
			final ChannelHandlerContext ctx,
			final DatagramPacket packet) {

		String content = packet.content().toString(CharsetUtil.UTF_8);

		if (Strings.isNullOrEmpty(content)) {
			return;
		}

		J jsonEntity = null;
		try {
			jsonEntity = JSON.decode(content, getExchangeClass());
		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}
		if (jsonEntity == null || !jsonEntity.valid()) {
			return;
		}

		execute(jsonEntity, ctx, packet);
	}

	protected abstract void execute(J json, ChannelHandlerContext ctx, DatagramPacket packet);
	protected abstract Class<J> getExchangeClass();
}