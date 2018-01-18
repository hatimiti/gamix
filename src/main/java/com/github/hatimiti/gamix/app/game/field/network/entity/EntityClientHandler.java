package com.github.hatimiti.gamix.app.game.field.network.entity;

import static com.github.hatimiti.gamix.app.game.field.entity.ClientEntityContainer.clientEntityContainer;
import io.netty.channel.ChannelHandlerContext;

import org.slf4j.Logger;

import com.github.hatimiti.gamix.app.game.field.entity.ClientEntityContainer;
import com.github.hatimiti.gamix.app.game.field.entity.map.MapTile;
import com.github.hatimiti.gamix.app.game.field.entity.map.MapTilePoint;
import com.github.hatimiti.gamix.app.game.field.entity.map.support.MapId;
import com.github.hatimiti.gamix.app.game.field.network.exchange.json.entity.ExchangeEntityServerJson;
import com.github.hatimiti.gamix.app.game.field.type.entity.EntityId;
import com.github.hatimiti.gamix.base.network.JsonHandler;
import com.github.hatimiti.gamix.base.util._Util;

/**
 * @author hatimiti
 *
 */
public class EntityClientHandler
		extends JsonHandler<ExchangeEntityServerJson, String> {

	private static final Logger LOG = _Util.getLogger();

	public EntityClientHandler() {
	}

	@Override
	protected void execute(
			final ExchangeEntityServerJson json,
			final ChannelHandlerContext ctx,
			final String packet) {

		LOG.debug("json = {}", json);

		final MapTile tile = new MapTile(
				MapId.getBy(json.m.mid),
				new MapTilePoint(json.m.tx, json.m.ty),
				null);

		final ClientEntityContainer ec = clientEntityContainer();
		ec.getPlayer().setEntityId(new EntityId(json.p.eid));

		json.ops.forEach(op -> ec.update(tile, op));
	}

	@Override
	protected Class<ExchangeEntityServerJson> getExchangeClass() {
		return ExchangeEntityServerJson.class;
	}

	@Override
	protected String getContent(String packet) {
		return packet;
	}

}