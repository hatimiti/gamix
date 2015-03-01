package com.github.hatimiti.gamix.app.game.field.entity.support.attack;

import com.github.hatimiti.gamix.app.game.field.entity.BaseEntity;

/**
 *
 * @author hatimiti
 *
 */
public interface Attackable {

	public void attack(BaseEntity target);
	public AttackState getAttackState();
	public boolean isAttacking();

}
