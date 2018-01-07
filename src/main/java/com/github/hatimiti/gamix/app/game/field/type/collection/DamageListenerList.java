package com.github.hatimiti.gamix.app.game.field.type.collection;

import com.github.hatimiti.gamix.app.game.field.damage.DamageEvent;
import com.github.hatimiti.gamix.app.game.field.damage.DamageListener;
import com.github.hatimiti.gamix.base.type.ArrayListType;


public final class DamageListenerList
		extends ArrayListType<DamageListener> {

	public void notifyDamage(final DamageEvent event) {
		for (DamageListener observer : getVal()) {
			observer.notifyDamage(event);
		}
	}

}
