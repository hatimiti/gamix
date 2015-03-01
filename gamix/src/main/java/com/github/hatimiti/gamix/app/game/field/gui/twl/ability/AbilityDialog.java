package com.github.hatimiti.gamix.app.game.field.gui.twl.ability;

import java.util.ArrayList;
import java.util.List;

import com.github.hatimiti.gamix.app.game.field.entity.support.status.AbilityDefineListener;
import com.github.hatimiti.gamix.app.game.field.entity.support.status.AbilityParameter;
import com.github.hatimiti.gamix.app.game.field.type.ability.AbilityParameterType;
import com.github.hatimiti.gamix.app.game.field.type.ability.AbilityPoint;
import com.github.hatimiti.gamix.app.game.field.type.ability.Agility;
import com.github.hatimiti.gamix.app.game.field.type.ability.Dexterity;
import com.github.hatimiti.gamix.app.game.field.type.ability.Intelligence;
import com.github.hatimiti.gamix.app.game.field.type.ability.MagicDefense;
import com.github.hatimiti.gamix.app.game.field.type.ability.PhysicalDefence;
import com.github.hatimiti.gamix.app.game.field.type.ability.Strength;
import com.github.hatimiti.gamix.app.game.field.type.ability.Vitality;
import com.github.hatimiti.gamix.base.gui.twl.FadeFrame;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.DialogLayout;
import de.matthiasmann.twl.DialogLayout.Group;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.Table;
import de.matthiasmann.twl.model.AbstractTableModel;
import de.matthiasmann.twl.model.SimpleIntegerModel;
import de.matthiasmann.twl.model.TableModel;

/**
 *
 * @author hatimiti
 */
public class AbilityDialog extends FadeFrame {

	static final String WITH_TITLE = "resizableframe-title";
	static final String WITHOUT_TITLE = "resizableframe";

	protected AbilityPointLabel abilityPointLabel;
	protected AbilityValueAdjuster strValueAdjuster;
	protected AbilityValueAdjuster pdfValueAdjuster;
	protected AbilityValueAdjuster mdfValueAdjuster;
	protected AbilityValueAdjuster dexValueAdjuster;
	protected AbilityValueAdjuster vitValueAdjuster;
	protected AbilityValueAdjuster agiValueAdjuster;
	protected AbilityValueAdjuster intValueAdjuster;

	protected Button cancelButton;
	protected Button okButton;

	protected AbilityParameter beforeSavedParameter;

	protected List<AbilityDefineListener> listeners;

	public AbilityDialog() {

		this.listeners = new ArrayList<>();

		this.abilityPointLabel = new AbilityPointLabel(new AbilityPoint(0));

		this.strValueAdjuster = new AbilityValueAdjuster(this.abilityPointLabel);
		Label strLabel = new Label("STR");
		strLabel.setLabelFor(this.strValueAdjuster);

		this.pdfValueAdjuster = new AbilityValueAdjuster(this.abilityPointLabel);
		Label pdfLabel = new Label("PDF");
		pdfLabel.setLabelFor(this.pdfValueAdjuster);

		this.mdfValueAdjuster = new AbilityValueAdjuster(this.abilityPointLabel);
		Label mdfLabel = new Label("MDF");
		mdfLabel.setLabelFor(this.mdfValueAdjuster);

		this.dexValueAdjuster = new AbilityValueAdjuster(this.abilityPointLabel);
		Label dexLabel = new Label("DEX");
		dexLabel.setLabelFor(this.dexValueAdjuster);

		this.vitValueAdjuster = new AbilityValueAdjuster(this.abilityPointLabel);
		Label vitLabel = new Label("VIT");
		vitLabel.setLabelFor(this.vitValueAdjuster);

		this.agiValueAdjuster = new AbilityValueAdjuster(this.abilityPointLabel);
		Label agiLabel = new Label("AGI");
		agiLabel.setLabelFor(this.agiValueAdjuster);

		this.intValueAdjuster = new AbilityValueAdjuster(this.abilityPointLabel);
		Label intLabel = new Label("INT");
		intLabel.setLabelFor(this.intValueAdjuster);

		this.cancelButton = new Button("CXL ");
		this.cancelButton.addCallback(new Runnable() {
			@Override
			public void run() {
				hide();
			}
		});

		this.okButton = new Button("OK  ");
		this.okButton.addCallback(new Runnable() {
			@Override
			public void run() {
				receipt();
				hide();
			}
		});

		DialogLayout box = new DialogLayout();
		box.setTheme("optionsdialog"); // the '/' causes this theme to start at the root again

		Label ap = new Label("AP");
		Label lv = new Label("レベル 10");
		Label nx = new Label("次のレベルまであと 500 EXP");

		TableModel m = new AbstractTableModel() {
			@Override
			public int getNumRows() {
				return 10;
			}

			@Override
			public int getNumColumns() {
				return 4;
			}

			@Override
			public String getColumnHeaderText(final int column) {
				switch (column) {
				case 0:
					return "-";
				case 1:
					return "基本";
				case 2:
					return "装備";
				case 3:
					return "合計";
				default:
				}

				return "";
			}

			@Override
			public Object getCell(final int row, final int column) {

				switch (column) {
				case 0:
					switch (row) {
					case 0:
						return "HP";
					case 1:
						return "MP";
					case 2:
						return "物攻";
					case 3:
						return "物防";
					case 4:
						return "魔攻";
					case 5:
						return "魔防";
					case 6:
						return "素早";
					default:
						return "";
					}
				case 1:
				case 2:
				default:
				}

				return "x";
			}

			@Override
			public Object getTooltipContent(final int row, final int column) {
				return "X:"+(column+1)+" Y:"+(row+1);
			}

		};
		Table tbl = new Table(m);

		Group hGroup = box.createSequentialGroup()
				.addGap(20)
				.addGroup(box.createParallelGroup()
						.addWidget(lv)
						.addWidget(nx)
						.addWidget(tbl)
						)
						.addGap(20)
						.addGroup(box.createParallelGroup()
								.addWidget(ap)
								.addWidget(strLabel)
								.addWidget(pdfLabel)
								.addWidget(mdfLabel)
								.addWidget(dexLabel)
								.addWidget(vitLabel)
								.addWidget(agiLabel)
								.addWidget(intLabel)
								)
								.addGroup(box.createParallelGroup()
										.addWidget(this.abilityPointLabel)
										.addWidget(this.strValueAdjuster)
										.addWidget(this.pdfValueAdjuster)
										.addWidget(this.mdfValueAdjuster)
										.addWidget(this.dexValueAdjuster)
										.addWidget(this.vitValueAdjuster)
										.addWidget(this.agiValueAdjuster)
										.addWidget(this.intValueAdjuster)
										.addGroup(box.createSequentialGroup()
												.addGap(50)
												.addWidget(this.cancelButton)
												.addWidget(this.okButton))
										)
										;

		Group vGroup = box.createSequentialGroup()
				.addGroup(box.createParallelGroup(lv, ap))
				.addGroup(box.createParallelGroup(nx, this.abilityPointLabel))
				.addGroup(box.createParallelGroup()
						.addWidget(tbl)
						.addGroup(box.createSequentialGroup()
								.addGroup(box.createParallelGroup(strLabel, this.strValueAdjuster))
								.addGroup(box.createParallelGroup(pdfLabel, this.pdfValueAdjuster))
								.addGroup(box.createParallelGroup(mdfLabel, this.mdfValueAdjuster))
								.addGroup(box.createParallelGroup(dexLabel, this.dexValueAdjuster))
								.addGroup(box.createParallelGroup(vitLabel, this.vitValueAdjuster))
								.addGroup(box.createParallelGroup(agiLabel, this.agiValueAdjuster))
								.addGroup(box.createParallelGroup(intLabel, this.intValueAdjuster))
								)
						)
						.addGap(10)
						.addGroup(box.createParallelGroup(this.cancelButton, this.okButton))
						;

		box.setHorizontalGroup(hGroup);
		box.setVerticalGroup(vGroup);

		setTheme(WITH_TITLE);
		add(box);
		setTitle("アビリティ");

		setSize(450, 280);

		// 閉じるボタン
		addCloseCallback();
		// リサイズ禁止
		setResizableAxis(ResizableAxis.NONE);

		hide();
	}

	@Override
	public void setVisible(final boolean visible) {
		if (visible && this.beforeSavedParameter == null) {
			throw new IllegalAccessError(
					"表示は　show(AbilityParameter abilityParameter) を呼び出してください。");
		} else if (!visible) {
			this.beforeSavedParameter = null;
		}
		super.setVisible(visible);
	}

	@Override
	public void show() {
		if (this.beforeSavedParameter == null) {
			throw new IllegalAccessError(
					"表示は　show(AbilityParameter abilityParameter) を呼び出してください。");
		}
		super.show();
	}

	public void show(final AbilityParameter abilityParameter) {
		if (abilityParameter == null) {
			throw new IllegalArgumentException("'abilityParameter' is required parameter.");
		}
		this.beforeSavedParameter = abilityParameter;
		this.abilityPointLabel.setAbilityPoint(abilityParameter.getAbilityPoint());
		this.strValueAdjuster.setModel(	getAbilityModel(abilityParameter.getStrength()));
		this.pdfValueAdjuster.setModel(	getAbilityModel(abilityParameter.getPhysicalDefense()));
		this.mdfValueAdjuster.setModel(	getAbilityModel(abilityParameter.getMagicDefense()));
		this.dexValueAdjuster.setModel(	getAbilityModel(abilityParameter.getDexterity()));
		this.vitValueAdjuster.setModel(	getAbilityModel(abilityParameter.getVitality()));
		this.agiValueAdjuster.setModel(	getAbilityModel(abilityParameter.getAgility()));
		this.intValueAdjuster.setModel(	getAbilityModel(abilityParameter.getIntelligence()));
		show();
	}

	protected SimpleIntegerModel getAbilityModel(final AbilityParameterType parameter) {
		return new SimpleIntegerModel(parameter.getVal(), 1000, parameter.getVal());
	}

	@Override
	protected void paint(final GUI gui) {
		super.paint(gui);
	}

	public boolean addListener(final AbilityDefineListener listener) {
		return this.listeners.add(listener);
	}

	protected void receipt() {

		AbilityParameter ap = new AbilityParameter(
				new AbilityPoint(this.abilityPointLabel.getAbilityPoint().getVal()),
				new Strength(this.strValueAdjuster.getValue()),
				new PhysicalDefence(this.pdfValueAdjuster.getValue()),
				new MagicDefense(this.mdfValueAdjuster.getValue()),
				new Dexterity(this.dexValueAdjuster.getValue()),
				new Vitality(this.vitValueAdjuster.getValue()),
				new Agility(this.agiValueAdjuster.getValue()),
				new Intelligence(this.intValueAdjuster.getValue()));

		for (AbilityDefineListener listener : this.listeners) {
			listener.notifyDefinedAbility(ap);
		}
	}

}
