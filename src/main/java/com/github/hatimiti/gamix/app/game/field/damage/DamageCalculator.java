package com.github.hatimiti.gamix.app.game.field.damage;

import java.util.Date;
import java.util.Random;

import com.github.hatimiti.gamix.app.game.field.entity.support.status.AbilityStatus;



public class DamageCalculator {

	protected int attack;
	protected int defense;
	protected AbilityStatus selfAbilityStatus = new AbilityStatus(1, 1, 1, 1, 1, 1);
	protected AbilityStatus targetAbilityStatus = new AbilityStatus(1, 1, 1, 1, 1, 1);

	public DamageCalculator attack(int attack) {
		this.attack = attack;
		return this;
	}

	public DamageCalculator defence(int defence) {
		this.defense = defence;
		return this;
	}

	public DamageCalculator self(AbilityStatus abilityStatus) {
		if (abilityStatus == null) {
			throw new IllegalArgumentException("abilityStatus は null を許容しません。");
		}
		this.selfAbilityStatus = abilityStatus;
		return this;
	}

	public DamageCalculator target(AbilityStatus abilityStatus) {
		if (targetAbilityStatus == null) {
			throw new IllegalArgumentException("targetAbilityStatus は null を許容しません。");
		}
		this.targetAbilityStatus = abilityStatus;
		return this;
	}

	public int calcDamage() {
		int damage = getAttack() / 2 - getDefense() / 4;
		damage = damage < 0 ? 0 : damage;
		Random rnd = new Random(new Date().getTime());
		int rndInt = rnd.nextInt(damage + 1);
		boolean isEven = rndInt % 2 == 0;
		int offset = rndInt / 8 + 1;
		return damage + (isEven ? offset : -offset);
	}

	protected int getAttack() {
		return this.attack + this.selfAbilityStatus.getPhysicalAttack();
	}

	protected int getDefense() {
		return this.defense + this.targetAbilityStatus.getPhysicalDefense();
	}

}
