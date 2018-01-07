package com.github.hatimiti.gamix.app.game.field.entity.map.shape;

import org.newdawn.slick.geom.Polygon;

public class BackWall extends Polygon {

	public static BackWall getDefault() {
		BackWall backWall = new BackWall();
		backWall.addPoint(120, 0);
		backWall.addPoint(680, 0);
		backWall.addPoint(680, 240);
		backWall.addPoint(120, 240);
		return backWall;
	}

	public static BackWall getDefaultWithRoad() {
		BackWall backWall = new BackWall();
		backWall.addPoint(120, 0);
		backWall.addPoint(680, 0);
		backWall.addPoint(680, 240);
		backWall.addPoint(120, 240);
		return backWall;
	}

}