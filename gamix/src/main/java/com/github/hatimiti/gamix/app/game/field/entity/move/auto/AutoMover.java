package com.github.hatimiti.gamix.app.game.field.entity.move.auto;

import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;
import com.github.hatimiti.gamix.app.game.field.entity.move.character.AutoCharacter;

/**
 * 自動移動の動作定義(Strategyパターン)
 * @author hatimiti
 *
 */
@FunctionalInterface
public interface AutoMover {

	public void update(AutoCharacter target, EntityContainer ec);

}
