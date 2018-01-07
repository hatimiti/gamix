package com.github.hatimiti.gamix.app.game.field.entity.support.status;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import com.github.hatimiti.gamix.base.util.Point;

import com.github.hatimiti.gamix.app.game.field.entity.support.direction.BaseDirectionAnimation;
import com.github.hatimiti.gamix.app.game.field.entity.support.direction.FacingDirection;
import com.github.hatimiti.gamix.app.util.ImageFactory;


public class LiveDirectionAnimation
		extends BaseDirectionAnimation {

	protected Liveable liveable;

	protected Animation rightAnimation;
	protected Animation leftAnimation;

	protected SpriteSheet deadEffectSpriteSheet;
	protected Animation deadEffectAnimation;

	public LiveDirectionAnimation(
			Liveable liveable,
			int n, int w, int h) {

		super(ImageFactory.getInstance().getCharacterImage(n), w, h);

		this.liveable = liveable;

		final int DMG_NL = 3;
		final int DMG_NR = 6;
		final int DELAY = 1;

		this.leftAnimation = new Animation(
				this.spriteSheet,
				new int[]{ DMG_NL + 0, 2, DMG_NL + 1, 2, DMG_NL + 2, 2 },
				new int[]{ DELAY, DELAY, DELAY });
		this.leftAnimation.setLooping(false);

		this.rightAnimation = new Animation(
				this.spriteSheet,
				new int[]{ DMG_NR + 2, 2, DMG_NR + 1, 2, DMG_NR + 0, 2 },
				new int[]{ DELAY, DELAY, DELAY });
		this.rightAnimation.setLooping(false);


		this.deadEffectSpriteSheet = createSpriteSheet(
				ImageFactory.getInstance().getEffectImage(2), 82, 73);

		this.deadEffectAnimation = new Animation(
				this.deadEffectSpriteSheet,
				new int[]{ 2, 0, 1, 0, 0, 0 },
				new int[]{ DELAY, DELAY, DELAY });
		this.deadEffectAnimation.setLooping(false);

	}

	@Override
	public void draw(Graphics g, FacingDirection direction, Point point) {

		int motion = this.liveable.getNowMotion();

		if (this.liveable.isDamaging()) {
			drawDamaging(g, direction, point, motion);
		} else if (this.liveable.isNearlyDead()) {
			this.deadEffectAnimation.setCurrentFrame(motion);
			draw(g, this.deadEffectAnimation, point.minus(Point.at(30, 20)));
		}

	}

	protected void drawDamaging(Graphics g, FacingDirection direction, Point point, int motion) {
		switch (direction) {
		case LEFT:
			this.leftAnimation.setCurrentFrame(motion);
			draw(g, leftAnimation, point);
			return;
		case RIGHT:
			this.rightAnimation.setCurrentFrame(motion);
			draw(g, this.rightAnimation, point);
			return;
		default:
			return;
		}
	}

	private static void draw(Graphics g, Animation animation, Point point) {
		g.drawAnimation(animation, point.getX(), point.getY());
	}
}