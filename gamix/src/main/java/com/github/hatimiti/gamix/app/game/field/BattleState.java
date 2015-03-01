package com.github.hatimiti.gamix.app.game.field;

import java.net.InetSocketAddress;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.StateBasedGame;

import com.github.hatimiti.gamix.app.game.field.damage.DamageEvent;
import com.github.hatimiti.gamix.app.game.field.damage.DamageListener;
import com.github.hatimiti.gamix.app.game.field.entity.BaseEntity;
import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;
import com.github.hatimiti.gamix.app.game.field.entity.map.BaseMap;
import com.github.hatimiti.gamix.app.game.field.entity.map.MapTile;
import com.github.hatimiti.gamix.app.game.field.entity.map.MapTilePoint;
import com.github.hatimiti.gamix.app.game.field.entity.map.town.FirstTownMap;
import com.github.hatimiti.gamix.app.game.field.entity.move.auto.AutoApproachMover;
import com.github.hatimiti.gamix.app.game.field.entity.move.auto.AutoStopMover;
import com.github.hatimiti.gamix.app.game.field.entity.move.character.AutoCharacter;
import com.github.hatimiti.gamix.app.game.field.entity.move.character.Player;
import com.github.hatimiti.gamix.app.game.field.entity.move.label.DamageLabel;
import com.github.hatimiti.gamix.app.game.field.entity.support.collision.CollisionHandler;
import com.github.hatimiti.gamix.app.game.field.entity.support.status.AbilityDefineListener;
import com.github.hatimiti.gamix.app.game.field.entity.support.status.AbilityParameter;
import com.github.hatimiti.gamix.app.game.field.gui.twl.HPBar;
import com.github.hatimiti.gamix.app.game.field.gui.twl.StatusButton;
import com.github.hatimiti.gamix.app.game.field.gui.twl.TextFrame;
import com.github.hatimiti.gamix.app.game.field.gui.twl.ability.AbilityDialog;
import com.github.hatimiti.gamix.app.game.field.network.client.EntityClient;
import com.github.hatimiti.gamix.app.game.field.network.client.EntityClient.EntityUpdateListener;
import com.github.hatimiti.gamix.app.game.field.network.exchange.json.entity.ExchangeEntityClientJson;
import com.github.hatimiti.gamix.app.game.field.type.collection.EntityList;
import com.github.hatimiti.gamix.app.support.GameSceneState;
import com.github.hatimiti.gamix.app.util.ConstProperty;
import com.github.hatimiti.gamix.base.BaseGameState;
import com.github.hatimiti.gamix.base.gui.swing.ChatDialog;
import com.github.hatimiti.gamix.base.gui.twl.RootPane;

import de.matthiasmann.twl.Button;


public class BattleState
		extends BaseGameState
		implements DamageListener, AbilityDefineListener {

	protected EntityContainer entityContainer;

	protected Player player;
	protected BaseEntity target;

	protected BaseMap map;
	protected MapTilePoint nowPoint;

	protected ChatDialog chatDialog;

	protected Button statusButton;
	protected TextFrame textFrame;
	protected AbilityDialog abilityDialog;

	protected HPBar playerHPBar;
	protected HPBar targetHPBar;

	protected CollisionHandler collisionHandler;

	protected BattleInputHelper inputHelper;

	public BattleState() {
		super(GameSceneState.BATTLE);
	}

	@Override
	public void init(
			final GameContainer gc,
			final StateBasedGame game) throws SlickException {

		this.inputHelper = new BattleInputHelper(this);

		this.entityContainer = EntityContainer.getInstance();
		this.entityContainer.clearEntities();
		this.textFrame = new TextFrame();

		this.map = new FirstTownMap();
		this.nowPoint = new MapTilePoint(0, 0);

		this.player = new Player(101, new Point(300, 500));
		this.player.addDamageListener(this);

		AutoCharacter target = new AutoCharacter(33, new AutoStopMover(), new Point(300, 450));
		target.addDamageListener(this);

		this.playerHPBar = new HPBar();
		this.playerHPBar.update(this.player.getStatus());
		this.targetHPBar = new HPBar();

		this.entityContainer.addTo(getNowTile(), this.map.getTileIn(this.nowPoint));
		this.entityContainer.addTo(getNowTile(), this.player);
		this.entityContainer.addTo(getNowTile(), target);

		this.collisionHandler = new CollisionHandler();

		this.chatDialog = new ChatDialog();
		this.abilityDialog = new AbilityDialog();
		this.abilityDialog.addListener(this);

		this.statusButton = new StatusButton(() ->
				this.textFrame.setVisible(!this.textFrame.isVisible()));
		
	}

	@Override
	public void enter(
			final GameContainer gc,
			final StateBasedGame game) throws SlickException {

		super.enter(gc, game);

		this.chatDialog.start(new InetSocketAddress(
				ConstProperty.getInstance().getString("network.server.ip"),
				ConstProperty.getInstance().getInt("network.server.port.chat")),
				ConstProperty.getInstance().getInt("network.update.interval.chat"));

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
		this.playerHPBar.draw(g);
		this.targetHPBar.draw(g);
	}

	@Override
	public void update(
			final GameContainer gc,
			final StateBasedGame game,
			final int paramInt) throws SlickException {

		setChatDialogVisible(gc);
		this.inputHelper.input(gc);
		moveMapTile();

		EntityList entities = this.entityContainer.getEntityListIn(getNowTile());

		entities.removeNonExsitsEntites();
		entities.update(this.entityContainer);
		this.collisionHandler.judgeCollision(entities);
	}

	protected void moveMapTile() throws SlickException {
		if (getNowTile().containsInLeftRoad(this.player.getShape())
				|| getNowTile().containsInRightRoad(this.player.getShape())) {

			this.entityContainer.clearEntitiesIn(getNowTile());
			this.nowPoint = new MapTilePoint(0, 1);
			this.entityContainer.addTo(getNowTile(), this.map.getTileIn(this.nowPoint));
			this.entityContainer.addTo(getNowTile(), this.player);
			AutoCharacter ac = new AutoCharacter(111, new AutoApproachMover(), new Point(300, 450));
			ac.addDamageListener(this);
			this.entityContainer.addTo(getNowTile(), ac);
			this.collisionHandler.clear();
			this.player.setPoint(new Point(650, 400));
		}
	}

	protected MapTile getNowTile() {
		return this.map.getTileIn(this.nowPoint);
	}

	protected void setChatDialogVisible(final GameContainer gc) {
		this.chatDialog.setAlwaysOnTop(gc.hasFocus()
				? this.chatDialog.isVisible() : false);
	}

	@Override
	protected RootPane createRootPane() {
		RootPane rp = super.createRootPane();
		rp.setTheme("gameui");
		rp.add(this.textFrame);
		rp.add(this.playerHPBar);
		rp.add(this.targetHPBar);
		rp.add(this.statusButton);
		rp.add(this.abilityDialog);
		return rp;
	}

	@Override
	protected void layoutRootPane() {
		this.textFrame.setSize(200, 150);
		this.textFrame.setPosition(400, 20);
		this.textFrame.setVisible(false);

		this.playerHPBar.setPosition(30, 30);
		this.targetHPBar.setPosition(350, 30);
		this.statusButton.setPosition(670, 20);
	}

	@Override
	public void notifyDamage(final DamageEvent event) {

		if (event.getTo() instanceof Player) {
			this.playerHPBar.update(((Player) event.getTo()).getStatus());
		} else {
			if (!(event.getTo() instanceof AutoCharacter)) {
				return;
			}
			AutoCharacter c = (AutoCharacter) event.getTo();
			this.target = c;
			this.targetHPBar.update(c.getStatus());

			DamageLabel dl = new DamageLabel(event.getDamage(), event.getPoint());
			this.entityContainer.addTo(getNowTile(), dl);
		}
	}

	@Override
	public void notifyDefinedAbility(final AbilityParameter abilityParameter) {
		this.player.levelUp(abilityParameter);
	}

}
