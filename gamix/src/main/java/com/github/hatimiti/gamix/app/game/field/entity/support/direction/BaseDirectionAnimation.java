package com.github.hatimiti.gamix.app.game.field.entity.support.direction;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;



public abstract class BaseDirectionAnimation {

	protected SpriteSheet spriteSheet;

	public BaseDirectionAnimation(
			Image image, int w, int h) {

		this.spriteSheet = createSpriteSheet(image, w, h);
	}

	public abstract void draw(Graphics g, FacingDirection direction, float x, float y);

	protected SpriteSheet createSpriteSheet(Image image, int w, int h) {
		return new SpriteSheet(image, w, h);
	}
}