package com.github.hatimiti.gamix.app.game.field.type.ability;

import com.github.hatimiti.gamix.base.type.Type;

public class AbilityPoint extends Type<Integer> {

	private final int abilityPoint;

	public AbilityPoint(final int abilityPoint) {
		this.abilityPoint = abilityPoint;
	}

	@Override
	public Integer getVal() {
		return this.abilityPoint;
	}

}
