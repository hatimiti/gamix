package com.github.hatimiti.gamix.app.game.field.entity.map.town;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.github.hatimiti.gamix.app.game.field.entity.map.BaseMap;
import com.github.hatimiti.gamix.app.game.field.entity.map.MapTile;
import com.github.hatimiti.gamix.app.game.field.entity.map.MapTilePoint;
import com.github.hatimiti.gamix.app.game.field.entity.map.support.MapId;

/**
 *
 *   0 1 2 3 4
 * 0 □□■
 * 1 　　□□□
 * @author hatimiti
 */
public class FirstTownMap extends BaseMap {

	protected static final MapId ID = MapId.FIRST_TOWN;

	public FirstTownMap() throws SlickException {
		super(ID, new MapTile[] {
			new MapTile(ID, new MapTilePoint(0, 0), new Image("imageFiles/background.png")),
			new MapTile(ID, new MapTilePoint(0, 1), new Image("imageFiles/background2.png")),
			new MapTile(ID, new MapTilePoint(0, 2), new Image("imageFiles/background.png")),
			new MapTile(ID, new MapTilePoint(1, 2), new Image("imageFiles/background.png")),
			new MapTile(ID, new MapTilePoint(1, 3), new Image("imageFiles/background.png")),
			new MapTile(ID, new MapTilePoint(1, 4), new Image("imageFiles/background.png")),
		});
	}

}