package com.github.hatimiti.gamix.app.game.field.type.ability;

import com.github.hatimiti.gamix.base.type.BaseType;

public class AbilityPoint extends BaseType<Integer> {

	private final int abilityPoint;

	public AbilityPoint(final int abilityPoint) {
		this.abilityPoint = abilityPoint;
	}

	@Override
	public Integer getVal() {
		return this.abilityPoint;
	}

}
