package com.github.hatimiti.gamix.app.game.field.type.live;

import com.github.hatimiti.gamix.base.type.Type;

public class HP
		extends Type<Integer>
		implements Comparable<HP> {

	public static final HP ZERO = new HP(0);

	private final int hp;

	public HP(final int hp) {
		this.hp = hp;
	}

	public boolean isMinus() {
		return this.hp < 0;
	}

	@Override
	public Integer getVal() {
		return this.hp;
	}

	@Override
	public int compareTo(final HP target) {
		return new Integer(this.hp).compareTo(target.hp);
	}

}
