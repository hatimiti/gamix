package com.github.hatimiti.gamix.app.game.field.network.exchange.entity;

import lombok.EqualsAndHashCode;

import com.github.hatimiti.gamix.base.network.exchange.entity.BaseExchangeEntity;


/**
 * @author hatimiti
 *
 */
@EqualsAndHashCode(callSuper = true)
public class ExchangeMap
		extends BaseExchangeEntity {

	/** mapId */
	public String mid;
	/** mapTilePointX */
	public int tx;
	/** mapTilePointY */
	public int ty;
}