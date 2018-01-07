package com.github.hatimiti.gamix.app.game.field.entity.support.attack;

/**
 *
 * @author hatimiti
 *
 */
public interface Attackable {

	public void attack();
	public AttackState getAttackState();
	public boolean isAttacking();

}
