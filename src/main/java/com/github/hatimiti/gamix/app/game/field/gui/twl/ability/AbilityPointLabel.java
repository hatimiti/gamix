package com.github.hatimiti.gamix.app.game.field.gui.twl.ability;

import com.github.hatimiti.gamix.app.game.field.type.ability.AbilityPoint;

import de.matthiasmann.twl.Label;

public class AbilityPointLabel extends Label {

	protected AbilityPoint abilityPoint;

	public AbilityPointLabel(final AbilityPoint abilityPoint) {
		setTheme("abilityPoint");
		setAbilityPoint(abilityPoint);
	}

	public AbilityPoint getAbilityPoint() {
		return this.abilityPoint;
	}

	public void setAbilityPoint(final AbilityPoint abilityPoint) {
		this.abilityPoint = abilityPoint;
		this.setText(String.valueOf(this.abilityPoint.getVal()));
	}

	public void doIncrement() {
		setAbilityPoint(new AbilityPoint(this.abilityPoint.getVal() + 1));
	}

	public void doDecrement() {
		setAbilityPoint(new AbilityPoint(this.abilityPoint.getVal() - 1));
	}

}
