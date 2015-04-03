package com.github.hatimiti.gamix.app.game.field.entity.support.move.mover;

import com.github.hatimiti.gamix.app.game.field.entity.character.AutoCharacter;
import com.github.hatimiti.gamix.app.game.field.entity.character.Player;

/**
 * 自動移動の動作定義(Strategyパターン)
 * @author hatimiti
 *
 */
@FunctionalInterface
public interface AutoMover {

	// TODO プレイヤー以外の情報の渡し方(EntityList?)
	public void update(AutoCharacter target, Player player);

}
