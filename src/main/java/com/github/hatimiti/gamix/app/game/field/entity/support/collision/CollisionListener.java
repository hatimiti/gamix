package com.github.hatimiti.gamix.app.game.field.entity.support.collision;

import org.newdawn.slick.geom.Shape;


public interface CollisionListener {

	/** 衝突時イベント */
	public void onCollision(CollisionEvent event);
	/** 衝突開放時イベント */
	public void onCollisionFree();
	/** 衝突判定対象のシェイプリスト */
	public Shape[] getCollisionShapes();

}