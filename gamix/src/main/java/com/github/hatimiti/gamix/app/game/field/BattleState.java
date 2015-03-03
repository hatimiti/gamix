package com.github.hatimiti.gamix.app.game.field;

import java.net.InetSocketAddress;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.StateBasedGame;

import com.github.hatimiti.gamix.app.game.field.entity.EmptyEntity;
import com.github.hatimiti.gamix.app.game.field.entity.Entity;
import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;
import com.github.hatimiti.gamix.app.game.field.entity.character.AutoCharacter;
import com.github.hatimiti.gamix.app.game.field.entity.character.Player;
import com.github.hatimiti.gamix.app.game.field.entity.map.BaseMap;
import com.github.hatimiti.gamix.app.game.field.entity.map.MapTile;
import com.github.hatimiti.gamix.app.game.field.entity.map.MapTilePoint;
import com.github.hatimiti.gamix.app.game.field.entity.map.town.FirstTownMap;
import com.github.hatimiti.gamix.app.game.field.entity.support.move.mover.AutoApproachMover;
import com.github.hatimiti.gamix.app.game.field.entity.support.move.mover.AutoStopMover;
import com.github.hatimiti.gamix.app.game.field.entity.support.status.AbilityDefineListener;
import com.github.hatimiti.gamix.app.game.field.entity.support.status.AbilityParameter;
import com.github.hatimiti.gamix.app.game.field.network.client.EntityClient;
import com.github.hatimiti.gamix.app.game.field.network.client.EntityClient.EntityUpdateListener;
import com.github.hatimiti.gamix.app.game.field.network.exchange.json.entity.ExchangeEntityClientJson;
import com.github.hatimiti.gamix.app.game.field.type.collection.EntityList;
import com.github.hatimiti.gamix.app.support.GameSceneState;
import com.github.hatimiti.gamix.app.util.ConstProperty;
import com.github.hatimiti.gamix.base.BaseGameState;
import com.github.hatimiti.gamix.base.gui.twl.RootPane;


public class BattleState
		extends BaseGameState
		implements AbilityDefineListener {

	EntityContainer entityContainer;

	Player player;
	Entity target = new EmptyEntity();

	BaseMap map;
	MapTilePoint nowPoint;

	BattleInputHelper inputHelper;
	BattleGUIManager guiManager;

	public BattleState() {
		super(GameSceneState.BATTLE);
	}

	@Override
	public void init(
			final GameContainer gc,
			final StateBasedGame game) throws SlickException {

		this.inputHelper = new BattleInputHelper(this);
		this.guiManager = new BattleGUIManager(this);

		this.entityContainer = EntityContainer.getInstance();
		this.entityContainer.clearEntities();

		this.map = new FirstTownMap();
		this.nowPoint = new MapTilePoint(0, 0);

		this.player = new Player(101, new Point(300, 500));
		this.player.addDamageListener(guiManager);

		AutoCharacter target = new AutoCharacter(33, new AutoStopMover(), new Point(300, 450));
		target.addDamageListener(guiManager);

		this.entityContainer.addTo(getNowTile(), this.map.getTileIn(this.nowPoint));
		this.entityContainer.addTo(getNowTile(), this.player);
		this.entityContainer.addTo(getNowTile(), target);

		this.guiManager.init(gc, game);
	}

	@Override
	public void enter(
			final GameContainer gc,
			final StateBasedGame game) throws SlickException {

		super.enter(gc, game);

		this.guiManager.enter(gc, game);

		new Thread(new EntityClient(
			new InetSocketAddress(
				ConstProperty.getInstance().getString("network.server.ip"),
				ConstProperty.getInstance().getInt("network.server.port.entity")),
				ConstProperty.getInstance().getInt("network.update.interval.entity"),
			new EntityUpdateListener() {
				@Override
				public ExchangeEntityClientJson createLatestEntity() {
					ExchangeEntityClientJson json = new ExchangeEntityClientJson();
					json.m.mid = BattleState.this.map.getMapId().getValue();
					json.m.tx = getNowTile().getPoint().getX();
					json.m.ty = getNowTile().getPoint().getY();
					json.p.eid = BattleState.this.player.getEntityId().getVal();
					json.p.x = BattleState.this.player.getX();
					json.p.y = BattleState.this.player.getY();
					json.p.d = BattleState.this.player.getDirection().getValue();
					return json;
				}

				@Override
				public Player getLatestPlayer() {
					return BattleState.this.player;
				}
		})).start();

//		this.midiPlayer.play(new File(
//				ResourceLoader.getResource("musicFiles/sh_battle4.mid").getPath()));
	}

	@Override
	public void render(
			final GameContainer gc,
			final StateBasedGame game,
			final Graphics g) throws SlickException {

		this.entityContainer.getEntityListIn(getNowTile()).draw(g);
		this.guiManager.render(gc, game, g);
	}

	@Override
	public void update(
			final GameContainer gc,
			final StateBasedGame game,
			final int paramInt) throws SlickException {

		this.guiManager.update(gc, game, paramInt);
		this.inputHelper.input(gc);
		
		moveMapTile();

		EntityList entities
			= this.entityContainer.getEntityListIn(getNowTile());
		entities.update(this.entityContainer);
	}

	protected void moveMapTile() throws SlickException {
		if (getNowTile().containsInLeftRoad(this.player.getShape())
				|| getNowTile().containsInRightRoad(this.player.getShape())) {

			this.entityContainer.clearEntitiesIn(getNowTile());
			this.nowPoint = new MapTilePoint(0, 1);
			this.entityContainer.addTo(getNowTile(), this.map.getTileIn(this.nowPoint));
			this.entityContainer.addTo(getNowTile(), this.player);
			AutoCharacter ac = new AutoCharacter(111, new AutoApproachMover(), new Point(300, 450));
			ac.addDamageListener(guiManager);
			this.entityContainer.addTo(getNowTile(), ac);
			this.player.setPoint(new Point(650, 400));
		}
	}

	protected MapTile getNowTile() {
		return this.map.getTileIn(this.nowPoint);
	}

	@Override
	protected RootPane createRootPane() {
		return this.guiManager.createRootPane(super.createRootPane());
	}

	@Override
	protected void layoutRootPane() {
		this.guiManager.layoutRootPane();
	}

	@Override
	public void notifyDefinedAbility(final AbilityParameter abilityParameter) {
		this.player.levelUp(abilityParameter);
	}

}
