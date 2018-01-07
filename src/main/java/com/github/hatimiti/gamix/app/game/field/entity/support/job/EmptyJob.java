package com.github.hatimiti.gamix.app.game.field.entity.support.job;

import com.github.hatimiti.gamix.app.game.field.entity.support.status.AbilityParameter;
import com.github.hatimiti.gamix.app.game.field.entity.support.status.AbilityStatus;


public class EmptyJob extends BaseJob {

	@Override
	public AbilityStatus calc(AbilityParameter abilityParameter) {
		return null;
	}

	@Override
	public String getJobName() {
		return "すっぴん";
	}

}
