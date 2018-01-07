package com.github.hatimiti.gamix.app.game.field.network.exchange.json.entity;

import com.github.hatimiti.gamix.app.game.field.network.exchange.entity.ExchangeMap;
import com.github.hatimiti.gamix.app.game.field.network.exchange.entity.ExchangePlayer;
import com.github.hatimiti.gamix.base.network.exchange.BaseExchangeJson;


/**
 * @author hatimiti
 *
 */
public class ExchangeEntityClientJson
		extends BaseExchangeJson {

	/** map */
	public ExchangeMap m = new ExchangeMap();
	/** player */
	public ExchangePlayer p = new ExchangePlayer();

}