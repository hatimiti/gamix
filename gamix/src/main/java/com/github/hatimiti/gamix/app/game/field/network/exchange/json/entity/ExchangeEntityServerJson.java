package com.github.hatimiti.gamix.app.game.field.network.exchange.json.entity;

import java.util.ArrayList;
import java.util.List;

import com.github.hatimiti.gamix.app.game.field.entity.BaseEntity;
import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;
import com.github.hatimiti.gamix.app.game.field.entity.character.OnlinePlayer;
import com.github.hatimiti.gamix.app.game.field.entity.map.MapTile;
import com.github.hatimiti.gamix.app.game.field.network.exchange.entity.ExchangeMap;
import com.github.hatimiti.gamix.app.game.field.network.exchange.entity.ExchangePlayer;
import com.github.hatimiti.gamix.app.game.field.type.entity.EntityId;
import com.github.hatimiti.gamix.base.network.exchange.BaseExchangeJson;


/**
 * @author hatimiti
 *
 */
public class ExchangeEntityServerJson
		extends BaseExchangeJson {

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

		for (BaseEntity entity : container.getEntityListIn(tile)) {

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
			System.out.println("snd:" + p.eid + ":x=" + p.x + ":y=" + p.y);
		}
	}

}