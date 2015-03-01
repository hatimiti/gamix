package com.github.hatimiti.gamix.app.game.field.entity.support.status;

import com.github.hatimiti.gamix.app.game.field.type.live.HP;

/**
 *
 * @author hatimiti
 *
 */
public interface Liveable {

	public HP getMaxHP();
	public HP getHP();

	public int getMaxST();
	public int getST();

	public int getNowMotion();
	public void damage(int damage);

	public boolean isNormal();
	public boolean isDamaging();
	public boolean isNearlyDead();
	public boolean isDead();

}
