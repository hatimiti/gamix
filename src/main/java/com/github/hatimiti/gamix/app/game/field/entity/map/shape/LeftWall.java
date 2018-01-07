package com.github.hatimiti.gamix.app.game.field.entity.map.shape;

import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

public class LeftWall extends Polygon {

	protected Polygon road;

	public LeftWall() {}
	public LeftWall(float[] points) {
		super(points);
	}
	public LeftWall(Polygon road) {
		super(getDefault().subtract(road)[0].getPoints());
		this.road = road;
	}

	public static LeftWall getDefault() {
		LeftWall leftWall = new LeftWall();
		leftWall.addPoint(0, 0);
		leftWall.addPoint(120, 0);
		leftWall.addPoint(120, 240);
		leftWall.addPoint(0, 600);
		return leftWall;
	}

	public static LeftWall getDefaultWithRoad() {
//		LeftWall leftWall = new LeftWall();
//		leftWall.addPoint(0, 0);
//		leftWall.addPoint(120, 0);
//		leftWall.addPoint(120, 240);
//		leftWall.addPoint(90, 340);
//		leftWall.addPoint(52, 340);
//		leftWall.addPoint(30, 400);
//		leftWall.addPoint(70, 400);
//		leftWall.addPoint(0, 600);

		Polygon road = new Polygon();
		road.addPoint(90, 340);
		road.addPoint(52, 340);
		road.addPoint(30, 400);
		road.addPoint(70, 400);

		return new LeftWall(road);
	}

	public boolean containsInLeftRoad(Shape shape) {
		if (this.road == null) {
			return false;
		}
		return this.road.intersects(shape);
	}

}