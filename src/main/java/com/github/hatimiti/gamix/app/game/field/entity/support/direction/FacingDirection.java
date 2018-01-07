package com.github.hatimiti.gamix.app.game.field.entity.support.direction;

/**
 * オブジェクトが向いている方向
 * @author hatimiti
 *
 */
public enum FacingDirection {

	NONE(0b0000),
	
	UP(0b1000),
	RIGHT(0b0100),
	DOWN(0b0010),
	LEFT(0b0001),

	UP_RIGHT(0b1100),
	UP_LEFT(0b1001),
	DOWN_RIGHT(0b0110),
	DOWN_LEFT(0b0011),
	
	;

	private int value;

	private FacingDirection(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public static FacingDirection getBy(final int value) {

		int _value = value;
//		_value = (getImpossibleValueX() & _value) == getImpossibleValueX()
//				? _value ^ getImpossibleValueX() : _value;
//		_value = (getImpossibleValueY() & _value) == getImpossibleValueY()
//				? _value ^ getImpossibleValueY() : _value;

		for (FacingDirection d : FacingDirection.values()) {
			if (getImpossibleValueX() == _value) {
				return DOWN;
			} else if (getImpossibleValueY() == _value) {
				return LEFT;
			} else if (d.getValue() == _value) {
				return d;
			}
		}
		return NONE;
	}

	public FacingDirection reverse() {
		return getReversedBy(this);
	}

	public static FacingDirection getReversedBy(FacingDirection d) {
		switch (d) {
		case UP:
			return DOWN;
		case RIGHT:
			return LEFT;
		case LEFT:
			return RIGHT;
		case DOWN:
			return UP;
		case UP_RIGHT:
			return DOWN_LEFT;
		case UP_LEFT:
			return DOWN_RIGHT;
		case DOWN_RIGHT:
			return UP_LEFT;
		case DOWN_LEFT:
			return UP_RIGHT;
		default:
			return NONE;
		}
	}

	private static int getImpossibleValueX() {
		return UP.getValue() | DOWN.getValue();
	}

	private static int getImpossibleValueY() {
		return RIGHT.getValue() | LEFT.getValue();
	}

}
