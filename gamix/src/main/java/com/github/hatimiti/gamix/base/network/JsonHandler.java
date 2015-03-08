package com.github.hatimiti.gamix.base.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;

import com.github.hatimiti.gamix.base.network.exchange.BaseExchangeJson;
import com.github.hatimiti.gamix.base.util._Util;

/**
 * @author hatimiti
 *
 */
public abstract class JsonHandler<J extends BaseExchangeJson, P>
		extends SimpleChannelInboundHandler<P> {

	@Override
	public final void messageReceived(
			final ChannelHandlerContext ctx,
			final P packet) {

		String content = getContent(packet);

		if (_Util.isNullOrEmpty(content)) {
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

	protected abstract void execute(J json, ChannelHandlerContext ctx, P packet);
	protected abstract Class<J> getExchangeClass();
	protected abstract String getContent(P packet);
}