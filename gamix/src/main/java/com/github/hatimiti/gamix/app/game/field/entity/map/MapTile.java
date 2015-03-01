package com.github.hatimiti.gamix.app.game.field.entity.map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import com.github.hatimiti.gamix.app.game.field.entity.BaseEntity;
import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;
import com.github.hatimiti.gamix.app.game.field.entity.map.shape.BackWall;
import com.github.hatimiti.gamix.app.game.field.entity.map.shape.FrontWall;
import com.github.hatimiti.gamix.app.game.field.entity.map.shape.LeftWall;
import com.github.hatimiti.gamix.app.game.field.entity.map.shape.RightWall;
import com.github.hatimiti.gamix.app.game.field.entity.map.support.MapId;
import com.github.hatimiti.gamix.app.game.field.entity.support.collision.CollisionEvent;

public class MapTile
		extends BaseEntity {

	protected Image image;

	/** マップID */
	protected MapId mapId;
	/** マップ位置(座標ではない) */
	protected MapTilePoint point;

	protected BackWall backWall;
	protected RightWall rightWall;
	protected FrontWall frontWall;
	protected LeftWall leftWall;

	public MapTile(
			final MapId mapId,
			final MapTilePoint point,
			final Image background) throws SlickException {
		super(new Rectangle(0, 0, 800, 600));
		init(mapId, point,
				background,
				BackWall.getDefault(),
				RightWall.getDefaultWithRoad(),
				FrontWall.getDefault(),
				LeftWall.getDefaultWithRoad());
	}

	public MapTile(
			final MapId mapId,
			final MapTilePoint point,
			final Image background,
			final BackWall backWall,
			final RightWall rightWall,
			final FrontWall frontWall,
			final LeftWall leftWall) throws SlickException {

		super(new Rectangle(0, 0, 800, 600));
		init(mapId, point, background, backWall, rightWall, frontWall, leftWall);
	}

	protected void init(
			final MapId mapId,
			final MapTilePoint point,
			final Image background,
			final BackWall backWall,
			final RightWall rightWall,
			final FrontWall frontWall,
			final LeftWall leftWall) {

		this.mapId = mapId;
		this.point = point;
		this.image = background;
		this.backWall = backWall;
		this.rightWall = rightWall;
		this.frontWall = frontWall;
		this.leftWall = leftWall;
	}

	public MapTilePoint getPoint() {
		return this.point;
	}

	@Override
	public void draw(final Graphics g) {
//		this.image.draw();
		g.setColor(Color.red);
		g.draw(this.leftWall);
		g.draw(this.rightWall);
		g.draw(this.frontWall);
		g.draw(this.backWall);
	}

	@Override
	public Shape[] getCollisionShapes() {
		return new Shape[] {
				this.leftWall,
				this.rightWall,
				this.frontWall,
				this.backWall,
		};
	}

	public boolean containsInLeftRoad(final Shape shape) {
		return this.leftWall.containsInLeftRoad(shape);
	}

	public boolean containsInRightRoad(final Shape shape) {
		return this.rightWall.containsInRightRoad(shape);
	}

	@Override
	public void update(final EntityContainer ec) {
	}

	@Override
	public void onCollision(final CollisionEvent event) {
	}

	@Override
	public void onCollisionFree() {
	}

	@Override
	public boolean equals(final Object o) {
		if (!(o instanceof MapTile)) {
			return false;
		}
		MapTile target = (MapTile) o;
		return new EqualsBuilder()
			.append(this.mapId, target.mapId)
			.append(this.point, target.point)
			.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(this.mapId)
			.append(this.point)
			.hashCode();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public boolean existsInGame() {
		return true;
	}

}