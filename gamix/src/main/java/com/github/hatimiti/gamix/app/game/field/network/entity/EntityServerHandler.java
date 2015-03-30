package com.github.hatimiti.gamix.app.game.field.network.entity;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;

import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;
import com.github.hatimiti.gamix.app.game.field.entity.map.MapTile;
import com.github.hatimiti.gamix.app.game.field.entity.map.MapTilePoint;
import com.github.hatimiti.gamix.app.game.field.entity.map.support.MapId;
import com.github.hatimiti.gamix.app.game.field.network.exchange.json.entity.ExchangeEntityClientJson;
import com.github.hatimiti.gamix.app.game.field.network.exchange.json.entity.ExchangeEntityServerJson;
import com.github.hatimiti.gamix.app.game.field.type.entity.EntityId;
import com.github.hatimiti.gamix.base.GamixRuntimeException;
import com.github.hatimiti.gamix.base.network.JsonHandler;
import com.github.hatimiti.gamix.base.util._Util;

/**
 * @author hatimiti
 *
 */
public class EntityServerHandler
		extends JsonHandler<ExchangeEntityClientJson, DatagramPacket> {

	private static final Logger LOG = _Util.getLogger();

	protected EntityContainer container;

	public EntityServerHandler(final EntityContainer container) {
		this.container = container;
	}

	@Override
	protected void execute(
			final ExchangeEntityClientJson clientJson,
			final ChannelHandlerContext ctx,
			final DatagramPacket packet) {

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

		this.container.update(tile, clientJson.p);

		ExchangeEntityServerJson serverJson
			= new ExchangeEntityServerJson(tile, clientJson, this.container);

		LOG.info("serverJson = {}" + serverJson);

		try {
			ctx.writeAndFlush(new DatagramPacket(
					Unpooled.copiedBuffer(JSON.encode(serverJson), CharsetUtil.UTF_8),
					packet.sender())).sync();
		} catch (JSONException | InterruptedException e) {
			throw new GamixRuntimeException(e);
		}
	}

	@Override
	protected Class<ExchangeEntityClientJson> getExchangeClass() {
		return ExchangeEntityClientJson.class;
	}

	@Override
	protected String getContent(DatagramPacket packet) {
		return packet.content().toString(CharsetUtil.UTF_8);
	}

}