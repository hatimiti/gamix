package com.github.hatimiti.gamix.app.game.field.entity.map.support;


/**
 * マップが向いている方向
 * @author hatimiti
 *
 */
public enum MapId {

	FIRST_TOWN("M0001"),
	;

	private String value;

	private MapId(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public static MapId getBy(final String value) {
		for (MapId id : MapId.values()) {
			if (id.getValue().equals(value)) {
				return id;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.value;
	}
}
