package com.github.hatimiti.gamix.app.game.field.entity.character;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;
import com.github.hatimiti.gamix.app.game.field.entity.support.attack.AttackState;
import com.github.hatimiti.gamix.app.game.field.entity.support.status.AbilityParameter;
import com.github.hatimiti.gamix.app.game.field.type.ability.AbilityPoint;
import com.github.hatimiti.gamix.app.game.field.type.ability.Agility;
import com.github.hatimiti.gamix.app.game.field.type.ability.Dexterity;
import com.github.hatimiti.gamix.app.game.field.type.ability.Intelligence;
import com.github.hatimiti.gamix.app.game.field.type.ability.MagicDefense;
import com.github.hatimiti.gamix.app.game.field.type.ability.PhysicalDefence;
import com.github.hatimiti.gamix.app.game.field.type.ability.Strength;
import com.github.hatimiti.gamix.app.game.field.type.ability.Vitality;

/**
 * プレイヤー自身を表現
 * @author hatimiti
 *
 */
public class Player extends Character {

	protected AbilityParameter abilityParameter;

	public Player(
			final int characterNumber,
			final Point defaultPoint) throws SlickException {
		super(characterNumber, defaultPoint);
		this.abilityParameter = new AbilityParameter(
				new AbilityPoint(100),
				new Strength(10),
				new PhysicalDefence(10),
				new MagicDefense(10),
				new Dexterity(10),
				new Vitality(10),
				new Agility(10),
				new Intelligence(10));
	}

	@Override
	public void update(final EntityContainer ec) {

		this.liveStatus.update();
		this.weapon.update(ec);

		if (!this.weapon.isAttacking()) {
			this.attackState = AttackState.STOP;
		}

		if (isStopping()) {
			return;
		}

		move();
	}

	public AbilityParameter getAbilityParameter() {
		return this.abilityParameter;
	}

	public void setPoint(final Point point) {
		this.shape.setX(point.getX());
		this.shape.setY(point.getY());
	}

	@Override
	public void levelUp(final AbilityParameter ap) {
		super.levelUp(ap);
		this.abilityParameter = ap;
	}

}