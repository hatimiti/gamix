package com.github.hatimiti.gamix.base.gui.twl.widget;

import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.model.IntegerModel;

public class LValueAdjusterInt extends LValueAdjuster {

	private int value;
	private int minValue;
	private int maxValue = 100;
	private int dragStartValue;
	private IntegerModel model;
	private Runnable modelCallback;

	public LValueAdjusterInt() {
		setTheme("valueadjuster");
        setDisplayText();
	}

	public LValueAdjusterInt(IntegerModel model) {
		setTheme("valueadjuster");
		setModel(model);
	}

	public int getMaxValue() {
		if(model != null) {
			maxValue = model.getMaxValue();
		}
		return maxValue;
	}

	public int getMinValue() {
		if(model != null) {
			minValue = model.getMinValue();
		}
		return minValue;
	}

	public void setMinMaxValue(int minValue, int maxValue) {
		if(maxValue < minValue) {
			throw new IllegalArgumentException("maxValue < minValue");
		}
		this.minValue = minValue;
		this.maxValue = maxValue;
		setValue(value);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		int _value = Math.max(getMinValue(), Math.min(getMaxValue(), value));
		if(this.value != _value) {
			this.value = _value;
			if(model != null) {
				model.setValue(_value);
			}
			setDisplayText();
		}
	}

	public IntegerModel getModel() {
		return model;
	}

	public void setModel(IntegerModel model) {
		if(this.model != model) {
			removeModelCallback();
			this.model = model;
			if(model != null) {
				this.minValue = model.getMinValue();
				this.maxValue = model.getMaxValue();
				addModelCallback();
			}
		}
	}


	@Override
	protected String onEditStart() {
		return formatText();
	}

	@Override
	protected boolean onEditEnd(String text) {
		try {
			setValue(Integer.parseInt(text));
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	@Override
	protected String validateEdit(String text) {
		try {
			Integer.parseInt(text);
			return null;
		} catch (NumberFormatException ex) {
			return ex.toString();
		}
	}

	@Override
	protected void onEditCanceled() {
	}

	@Override
	protected boolean shouldStartEdit(char ch) {
		return (ch >= '0' && ch <= '9') || (ch == '-');
	}

	@Override
	protected void onDragStart() {
		dragStartValue = value;
	}

	@Override
	protected void onDragUpdate(int dragDelta) {
		int range = Math.max(1, Math.abs(getMaxValue() - getMinValue()));
		setValue(dragStartValue + dragDelta/Math.max(3, getWidth()/range));
	}

	@Override
	protected void onDragCancelled() {
		setValue(dragStartValue);
	}

	@Override
	protected void doDecrement() {
		setValue(value - 1);
	}

	@Override
	protected void doIncrement() {
		setValue(value + 1);
	}

	@Override
	protected String formatText() {
		return Integer.toString(value);
	}

	protected void syncWithModel() {
		this.minValue = model.getMinValue();
		this.maxValue = model.getMaxValue();
		this.value = model.getValue();
		setDisplayText();
	}

	@Override
	protected void afterAddToGUI(GUI gui) {
		super.afterAddToGUI(gui);
		addModelCallback();
	}

	@Override
	protected void beforeRemoveFromGUI(GUI gui) {
		removeModelCallback();
		super.beforeRemoveFromGUI(gui);
	}

	protected void removeModelCallback() {
		if(model != null && modelCallback != null) {
			model.removeCallback(modelCallback);
		}
	}

	protected void addModelCallback() {
		if(model != null && getGUI() != null) {
			if(modelCallback == null) {
				modelCallback = new ModelCallback();
			}
			model.addCallback(modelCallback);
			syncWithModel();
		}
	}

}
