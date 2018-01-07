package com.github.hatimiti.gamix.app.game.field.entity.support.direction;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import com.github.hatimiti.gamix.base.util.Point;



public abstract class BaseDirectionAnimation {

	protected SpriteSheet spriteSheet;

	public BaseDirectionAnimation(
			Image image, int w, int h) {

		this.spriteSheet = createSpriteSheet(image, w, h);
	}

	public abstract void draw(Graphics g, FacingDirection direction, Point point);

	protected SpriteSheet createSpriteSheet(Image image, int w, int h) {
		return new SpriteSheet(image, w, h);
	}
}