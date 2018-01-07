package com.github.hatimiti.gamix.app.game.field.entity.map.shape;

import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

public class RightWall extends Polygon {

	protected Polygon road;

	public RightWall() {}
	public RightWall(float[] points) {
		super(points);
	}
	public RightWall(Polygon road) {
		super(getDefault().subtract(road)[0].getPoints());
		this.road = road;
	}

	public static RightWall getDefault() {
		RightWall rightWall = new RightWall();
		rightWall.addPoint(680, 0);
		rightWall.addPoint(800, 0);
		rightWall.addPoint(800, 600);
		rightWall.addPoint(680, 240);
		return rightWall;
	}

	public static RightWall getDefaultWithRoad() {

		Polygon road = new Polygon();
		road.addPoint(710, 340);
		road.addPoint(748, 340);
		road.addPoint(770, 400);
		road.addPoint(730, 400);

		return new RightWall(road);
	}

	public boolean containsInRightRoad(Shape shape) {
		if (this.road == null) {
			return false;
		}
		return this.road.intersects(shape);
	}

}