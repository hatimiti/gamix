package com.github.hatimiti.gamix.app.game.field.entity.support.attack;

import com.github.hatimiti.gamix.app.game.field.entity.Entity;

/**
 *
 * @author hatimiti
 *
 */
public interface Attackable {

	public void attack(Entity target);
	public AttackState getAttackState();
	public boolean isAttacking();

}
