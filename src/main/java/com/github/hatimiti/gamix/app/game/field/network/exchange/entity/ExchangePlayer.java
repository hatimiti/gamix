package com.github.hatimiti.gamix.app.game.field.network.exchange.entity;

import lombok.EqualsAndHashCode;

import com.github.hatimiti.gamix.base.network.exchange.entity.BaseExchangeEntity;


/**
 * @author hatimiti
 *
 */
@EqualsAndHashCode(callSuper = true)
public class ExchangePlayer
		extends BaseExchangeEntity {

	/** x */
	public int x;
	/** y */
	public int y;
	/** direction */
	public int d;

}