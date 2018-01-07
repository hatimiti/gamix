package com.github.hatimiti.gamix.app.game.field.entity.support.direction;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

import com.github.hatimiti.gamix.app.util.ImageFactory;
import com.github.hatimiti.gamix.base.util.Point;


public class MoveDirectionAnimation
		extends BaseDirectionAnimation {

	protected Animation upAnimation;
	protected Animation rightAnimation;
	protected Animation downAnimation;
	protected Animation leftAnimation;

	public MoveDirectionAnimation(
			int n, int w, int h) {

		super(ImageFactory.getInstance().getCharacterImage(n), w, h);

		final int N = 0;//n * 2;
		final int DELAY = 300;

		this.upAnimation = new Animation(
				this.spriteSheet,
				new int[]{ N, 3, N + 1, 3, N + 2, 3 },
				new int[]{ DELAY, DELAY, DELAY });
		this.upAnimation.setLooping(true);

		this.leftAnimation = new Animation(
				this.spriteSheet,
				new int[]{ N, 1, N + 1, 1, N + 2, 1 },
				new int[]{ DELAY, DELAY, DELAY });
		this.leftAnimation.setLooping(true);

		this.rightAnimation = new Animation(
				this.spriteSheet,
				new int[]{ N, 2, N + 1, 2, N + 2, 2 },
				new int[]{ DELAY, DELAY, DELAY });
		this.rightAnimation.setLooping(true);

		this.downAnimation = new Animation(
				this.spriteSheet,
				new int[]{ N, 0, N + 1, 0, N + 2, 0 },
				new int[]{ DELAY, DELAY, DELAY });
		this.downAnimation.setLooping(true);
	}

	@Override
	public void draw(Graphics g, FacingDirection direction, Point p) {
		
		if (direction == null) {
			return;
		}
		
		final int x = p.getX();
		final int y = p.getY();
		
		switch (direction) {
		case UP:
			g.drawAnimation(this.upAnimation, x, y);
			return;
		case DOWN:
			g.drawAnimation(this.downAnimation, x, y);
			return;
		case UP_RIGHT:
		case DOWN_RIGHT:
		case RIGHT:
			g.drawAnimation(this.rightAnimation, x, y);
			return;
		case UP_LEFT:
		case DOWN_LEFT:
		case LEFT:
			g.drawAnimation(this.leftAnimation, x, y);
			return;
		default:
			return;
		}
	}
}