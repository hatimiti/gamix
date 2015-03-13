package com.github.hatimiti.gamix.app.game.field.network.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import org.newdawn.slick.SlickException;
import org.slf4j.Logger;

import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;
import com.github.hatimiti.gamix.app.game.field.entity.map.MapTile;
import com.github.hatimiti.gamix.app.game.field.entity.map.MapTilePoint;
import com.github.hatimiti.gamix.app.game.field.entity.map.support.MapId;
import com.github.hatimiti.gamix.app.game.field.network.exchange.entity.ExchangePlayer;
import com.github.hatimiti.gamix.app.game.field.network.exchange.json.entity.ExchangeEntityServerJson;
import com.github.hatimiti.gamix.app.game.field.type.entity.EntityId;
import com.github.hatimiti.gamix.base.network.JsonHandler;
import com.github.hatimiti.gamix.base.util._Util;

/**
 * @author hatimiti
 *
 */
public class EntityClientHandler
		extends JsonHandler<ExchangeEntityServerJson, DatagramPacket> {

	private static final Logger LOG = _Util.getLogger();

	public EntityClientHandler() {
	}

	@Override
	protected void execute(
			final ExchangeEntityServerJson json,
			final ChannelHandlerContext ctx,
			final DatagramPacket packet) {

		LOG.debug("json = {}", json);

		MapTile tile;

		try {
			tile = new MapTile(
					MapId.getBy(json.m.mid),
					new MapTilePoint(
							json.m.tx,
							json.m.ty),
							null);
		} catch (SlickException e) {
			e.printStackTrace();
			return;
		}

		EntityContainer ec = EntityContainer.getInstance();
		ec.getPlayer().setEntityId(new EntityId(json.p.eid));

		try {
			for (ExchangePlayer op : json.ops) {
				ec.update(tile, op);
			}
		} catch (SlickException e) {
			e.printStackTrace();
			return;
		}

//		for (BaseEntity e : ec.getEntitiesIn(tile)) {
//			System.out.println("client-entities = " + e.getEntityId());
//		}
	}

	@Override
	protected Class<ExchangeEntityServerJson> getExchangeClass() {
		return ExchangeEntityServerJson.class;
	}

	@Override
	protected String getContent(DatagramPacket packet) {
		return packet.content().toString(CharsetUtil.UTF_8);
	}

}