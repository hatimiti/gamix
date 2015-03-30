package com.github.hatimiti.gamix.app.game.field.entity.map;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.github.hatimiti.gamix.app.game.field.entity.map.support.MapId;

public abstract class BaseMap {

	protected MapId mapId;
	protected Map<MapTilePoint, MapTile> tileMap;

	public BaseMap(MapId mapId, MapTile... tiles) {

		this.mapId = mapId;
		this.tileMap = new HashMap<>();

		Arrays.asList(tiles)
			.forEach(t -> this.tileMap.put(t.getPoint(), t));
	}

	public MapId getMapId() {
		return mapId;
	}

	public MapTile getTileIn(MapTilePoint point) {
		MapTile tile = this.tileMap.get(point);
		if (tile == null) {
			return null;
		}
		return tile;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof BaseMap)) {
			return false;
		}
		BaseMap target = (BaseMap) o;
		return new EqualsBuilder()
			.append(this.mapId, target.mapId)
			.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(this.mapId)
			.hashCode();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}