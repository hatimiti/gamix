package com.github.hatimiti.gamix.app.game.field.type.ability;

import com.github.hatimiti.gamix.base.type.BaseType;

public abstract class AbilityParameterType extends BaseType<Integer> {

	private final int parameter;

	public AbilityParameterType(final int parameter) {
		this.parameter = parameter;
	}

	@Override
	public Integer getVal() {
		return this.parameter;
	}
}
