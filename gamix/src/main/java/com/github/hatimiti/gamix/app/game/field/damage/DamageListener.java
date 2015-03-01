package com.github.hatimiti.gamix.app.game.field.damage;

public interface DamageListener {

	/** 衝突時報告の受信 */
	public void notifyDamage(DamageEvent event);

}