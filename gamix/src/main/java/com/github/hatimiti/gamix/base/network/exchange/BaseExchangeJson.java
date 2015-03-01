package com.github.hatimiti.gamix.base.network.exchange;

import net.arnx.jsonic.JSON;



/**
 * @author hatimiti
 *
 */
public abstract class BaseExchangeJson {

	public boolean valid() {
		return true;
	}

	@Override
	public String toString() {
		return JSON.encode(this);
	}

}