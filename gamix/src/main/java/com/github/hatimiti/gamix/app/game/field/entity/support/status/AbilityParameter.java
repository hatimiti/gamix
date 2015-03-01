package com.github.hatimiti.gamix.app.game.field.entity.support.status;

import com.github.hatimiti.gamix.app.game.field.type.ability.AbilityPoint;
import com.github.hatimiti.gamix.app.game.field.type.ability.Agility;
import com.github.hatimiti.gamix.app.game.field.type.ability.Dexterity;
import com.github.hatimiti.gamix.app.game.field.type.ability.Intelligence;
import com.github.hatimiti.gamix.app.game.field.type.ability.MagicDefense;
import com.github.hatimiti.gamix.app.game.field.type.ability.PhysicalDefence;
import com.github.hatimiti.gamix.app.game.field.type.ability.Strength;
import com.github.hatimiti.gamix.app.game.field.type.ability.Vitality;

/**
 * 腕力やすばやさなどのステータスを表す。
 * @author hatimiti
 */
public class AbilityParameter {

	protected AbilityPoint abilityPoint;

	/** 力(STR)：基本的な物理攻撃力に影響 */
	protected Strength strength;
	/** 物理防御力(PDF) */
	protected PhysicalDefence physicalDefense;
	/** 魔法防御(MDF) */
	protected MagicDefense magicDefense;
	/** 器用さ(DEX)：命中率に影響 */
	protected Dexterity dexterity;
	/** 生命力(VIT)：HPなどに影響 */
	protected Vitality vitality;
	/** 敏捷性(AGI)：すばやさなどに影響 */
	protected Agility agility;
	/** 知力(INT)：魔力に影響 */
	protected Intelligence intelligence;

	public AbilityParameter(
			final AbilityPoint abilityPoint,
			final Strength strength,
			final PhysicalDefence physicalDefense,
			final MagicDefense magicDefense,
			final Dexterity dexterity,
			final Vitality vitality,
			final Agility agility,
			final Intelligence intelligence) {

		this.abilityPoint = abilityPoint;
		this.strength = strength;
		this.physicalDefense = physicalDefense;
		this.magicDefense = magicDefense;
		this.dexterity = dexterity;
		this.vitality = vitality;
		this.agility = agility;
		this.intelligence = intelligence;
	}

	public int getPhysicalAttack() {
		return (int) Math.sqrt(this.strength.getVal() * 1000);
	}

	/*
	 * getter
	 */

	public AbilityPoint getAbilityPoint() {
		return this.abilityPoint;
	}

	public Strength getStrength() {
		return this.strength;
	}

	public PhysicalDefence getPhysicalDefense() {
		return this.physicalDefense;
	}

	public MagicDefense getMagicDefense() {
		return this.magicDefense;
	}

	public Dexterity getDexterity() {
		return this.dexterity;
	}

	public Vitality getVitality() {
		return this.vitality;
	}

	public Agility getAgility() {
		return this.agility;
	}

	public Intelligence getIntelligence() {
		return this.intelligence;
	}

}
