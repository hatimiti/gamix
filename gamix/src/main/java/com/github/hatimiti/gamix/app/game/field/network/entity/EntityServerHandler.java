package com.github.hatimiti.gamix.app.game.field.network.entity;

import static com.github.hatimiti.gamix.app.game.field.entity.ServerEntityContainer.serverEntityContainer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import net.arnx.jsonic.JSON;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;

import com.github.hatimiti.gamix.app.game.field.entity.map.MapTile;
import com.github.hatimiti.gamix.app.game.field.entity.map.MapTilePoint;
import com.github.hatimiti.gamix.app.game.field.entity.map.support.MapId;
import com.github.hatimiti.gamix.app.game.field.network.exchange.json.entity.ExchangeEntityClientJson;
import com.github.hatimiti.gamix.app.game.field.network.exchange.json.entity.ExchangeEntityServerJson;
import com.github.hatimiti.gamix.app.game.field.type.entity.EntityId;
import com.github.hatimiti.gamix.base.network.JsonHandler;
import com.github.hatimiti.gamix.base.util._Util;

/**
 * @author hatimiti
 *
 */
public class EntityServerHandler
		extends JsonHandler<ExchangeEntityClientJson, String> {

	private static final Logger LOG = _Util.getLogger();

	/** 接続中のクライアント */
	private static final ChannelGroup channels
		= new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	@Override
	public void channelActive(final ChannelHandlerContext ctx) {
		channels.add(ctx.channel());
	}

	@Override
	protected void execute(
			final ExchangeEntityClientJson clientJson,
			final ChannelHandlerContext ctx,
			final String packet) {

		LOG.debug("clientJson = {}", clientJson);

		MapTile tile = new MapTile(
					MapId.getBy(clientJson.m.mid),
					new MapTilePoint(
							clientJson.m.tx,
							clientJson.m.ty),
					null);

		if (new EntityId(clientJson.p.eid).isNone()) {
			clientJson.p.eid
				= RandomStringUtils.randomAlphanumeric(10);
		}

		serverEntityContainer().update(tile, clientJson.p);

		ExchangeEntityServerJson serverJson
			= new ExchangeEntityServerJson(tile, clientJson);

		LOG.debug("serverJson = {}" + serverJson);

		for (Channel c: channels) {
			if (c != ctx.channel()) {
				continue;
			}
			// Sends message to myself
			c.writeAndFlush(JSON.encode(serverJson) + "\r\n");
			break;
		}
	}

	@Override
	protected Class<ExchangeEntityClientJson> getExchangeClass() {
		return ExchangeEntityClientJson.class;
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