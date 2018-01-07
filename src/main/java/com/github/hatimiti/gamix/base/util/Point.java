package com.github.hatimiti.gamix.base.util;

import java.io.Serializable;

import org.newdawn.slick.geom.Shape;

public class Point implements Serializable {

	private static final long serialVersionUID = 1L;

	private org.newdawn.slick.geom.Point point;
	
	protected Point(int x, int y) {
		this.point = new org.newdawn.slick.geom.Point(x, y);
	}
	
	public static Point at(int x, int y) {
		return new Point(x, y);
	}
	
	public static Point at(float x, float y) {
		return at((int) x, (int) y);
	}

	public static Point at(Shape shape) {
		return at(shape.getX(), shape.getY());
	}

	public static Point atCenter(Shape shape) {
		return at(shape.getCenterX(), shape.getCenterY());
	}

	public Point plus(Point p) {
		return p == null
				? getCopy()
				: at(getX() + p.getX(), getY() + p.getY());
	}

	public Point minus(Point p) {
		return p == null
				? getCopy()
				: plus(Point.at(-p.getX(), -p.getY()));
	}
	
	public int getX() {
		return (int) point.getX();
	}
	
	public int getY() {
		return (int) point.getY();
	}
	
	public int getCenterX() {
		return (int) point.getCenterX();
	}
	
	public int getCenterY() {
		return (int) point.getCenterY();
	}
	
	private Point getCopy() {
		return at(this.getX(), this.getY());
	}
}
