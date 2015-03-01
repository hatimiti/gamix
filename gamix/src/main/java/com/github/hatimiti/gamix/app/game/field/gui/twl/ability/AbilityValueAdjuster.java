package com.github.hatimiti.gamix.app.game.field.gui.twl.ability;

import com.github.hatimiti.gamix.base.gui.twl.widget.LValueAdjusterInt;


public class AbilityValueAdjuster extends LValueAdjusterInt {

	protected AbilityPointLabel abilityPointLabel;

	AbilityValueAdjuster(final AbilityPointLabel abilityPointLabel) {
		this.abilityPointLabel = abilityPointLabel;
	}

	@Override
	protected void doDecrement() {
		if (getValue() - 1 < getMinValue()) {
			return;
		}
		super.doDecrement();
		this.abilityPointLabel.doIncrement();
	}

	@Override
	protected void doIncrement() {
		if (this.abilityPointLabel.getAbilityPoint().getVal() <= 0) {
			return;
		}
		super.doIncrement();
		this.abilityPointLabel.doDecrement();
	}

}
