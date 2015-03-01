package com.github.hatimiti.gamix.app.game.field.entity.map.shape;

import org.newdawn.slick.geom.Polygon;

public class FrontWall extends Polygon {

	public static FrontWall getDefault() {
		FrontWall frontWall = new FrontWall();
		frontWall.addPoint(0, 580);
		frontWall.addPoint(800, 580);
		frontWall.addPoint(800, 600);
		frontWall.addPoint(0, 600);
		return frontWall;
	}

	public static FrontWall getDefaultWithRoad() {
		FrontWall frontWall = new FrontWall();
		frontWall.addPoint(0, 580);
		frontWall.addPoint(800, 580);
		frontWall.addPoint(800, 600);
		frontWall.addPoint(0, 600);
		return frontWall;
	}

}