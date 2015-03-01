package com.github.hatimiti.gamix.app.game.field.entity.support.job;

import com.github.hatimiti.gamix.app.game.field.entity.support.status.AbilityParameter;
import com.github.hatimiti.gamix.app.game.field.entity.support.status.AbilityStatus;

public abstract class BaseJob {

	public abstract String getJobName();
	public abstract AbilityStatus calc(AbilityParameter abilityParameter);

}
