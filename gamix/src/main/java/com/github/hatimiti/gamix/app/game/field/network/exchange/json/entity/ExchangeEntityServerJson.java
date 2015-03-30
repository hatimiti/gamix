package com.github.hatimiti.gamix.app.game.field.network.exchange.json.entity;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.github.hatimiti.gamix.app.game.field.entity.Entity;
import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;
import com.github.hatimiti.gamix.app.game.field.entity.character.OnlinePlayer;
import com.github.hatimiti.gamix.app.game.field.entity.map.MapTile;
import com.github.hatimiti.gamix.app.game.field.network.exchange.entity.ExchangeMap;
import com.github.hatimiti.gamix.app.game.field.network.exchange.entity.ExchangePlayer;
import com.github.hatimiti.gamix.app.game.field.type.entity.EntityId;
import com.github.hatimiti.gamix.base.network.exchange.BaseExchangeJson;
import com.github.hatimiti.gamix.base.util._Util;


/**
 * @author hatimiti
 *
 */
public class ExchangeEntityServerJson
		extends BaseExchangeJson {

	private static final Logger LOG = _Util.getLogger();
	
	/** map */
	public ExchangeMap m = new ExchangeMap();
	/** player */
	public ExchangePlayer p = new ExchangePlayer();
	/** online players */
	public List<ExchangePlayer> ops = new ArrayList<>();

	public ExchangeEntityServerJson() {
	}

	public ExchangeEntityServerJson(
			final MapTile tile,
			final ExchangeEntityClientJson client,
			final EntityContainer container) {

		convert(client, container, tile);
	}

	protected void convert(
			final ExchangeEntityClientJson client,
			final EntityContainer container,
			final MapTile tile) {

		this.p = client.p;
		this.m = client.m;

		for (Entity entity : container.getEntityListIn(tile)) {

			if (!(entity instanceof OnlinePlayer)) {
				continue;
			}

			OnlinePlayer c = (OnlinePlayer) entity;

			if (new EntityId(client.p.eid)
					.equals(c.getEntityId())) {
				continue;
			}

			ExchangePlayer p = new ExchangePlayer();
			p.eid = c.getEntityId().getVal();
			p.x = c.getX();
			p.y = c.getY();
			p.d = c.getDirection().getValue();
			this.ops.add(p);
			
			LOG.debug("send: {} :x= {} :y= {}", p.eid, p.x, p.y);
		}
	}

}