package com.github.hatimiti.gamix.base.gui.twl.widget;

import org.apache.commons.lang3.math.NumberUtils;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.DraggableButton;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.Event;
import de.matthiasmann.twl.FocusGainedCause;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.ThemeInfo;
import de.matthiasmann.twl.Timer;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.AnimationState.StateKey;

public abstract class LValueAdjuster extends Widget {

	public static final StateKey STATE_EDIT_ACTIVE = StateKey.get("editActive");

	protected static final int INITIAL_DELAY = 300;
	protected static final int REPEAT_DELAY = 75;

	protected final DraggableButton label;
	protected final Label valueLabel;
	protected final Button decButton;
	protected final Button incButton;
	protected final Runnable timerCallback;
	protected final L listeners;
	protected Timer timer;

	protected String displayPrefix;
	protected String displayPrefixTheme = "";
	protected boolean useMouseWheel = true;
	protected boolean acceptValueOnFocusLoss = true;
	protected boolean wasInEditOnFocusLost;
	protected int width;

	public LValueAdjuster() {
		this.label = new DraggableButton(getAnimationState(), true);
		// EditField always inherits from the passed animation state
		this.valueLabel = new Label(getAnimationState());
		this.decButton = new Button(getAnimationState(), true);
		this.incButton = new Button(getAnimationState(), true);

		label.setClip(true);
		label.setTheme("valueDisplay");
		valueLabel.setTheme("valueEdit");
		decButton.setTheme("decButton");
		incButton.setTheme("incButton");

		Runnable cbUpdateTimer = new Runnable() {
			public void run() {
				updateTimer();
			}
		};

		timerCallback = new Runnable() {
			public void run() {
				onTimer(REPEAT_DELAY);
			}
		};

		decButton.getModel().addStateCallback(cbUpdateTimer);
		incButton.getModel().addStateCallback(cbUpdateTimer);

		listeners = new L();
		label.addCallback(listeners);
		label.setListener(listeners);

		valueLabel.setVisible(false);
//		valueLabel.addCallback(listeners);

		add(label);
		add(valueLabel);
		add(decButton);
		add(incButton);
		setCanAcceptKeyboardFocus(true);
		setDepthFocusTraversal(false);
	}

	public int getValue() {
		return NumberUtils.toInt(this.valueLabel.getText(), 0);
	}

	public String getDisplayPrefix() {
		return displayPrefix;
	}

	/**
	 * Sets the display prefix which is displayed before the value.
	 *
	 * If this is property is null then the value from the theme is used,
	 * otherwise this one.
	 *
	 * @param displayPrefix the prefix or null
	 */
	public void setDisplayPrefix(String displayPrefix) {
		this.displayPrefix = displayPrefix;
		setDisplayText();
	}

	public boolean isUseMouseWheel() {
		return useMouseWheel;
	}

	/**
	 * Controls the behavior on focus loss when editing the value.
	 * If true then the value is accepted (like pressing RETURN).
	 * If false then it is discard (like pressing ESCAPE).
	 *
	 * Default is true.
	 *
	 * @param acceptValueOnFocusLoss true if focus loss should accept the edited value.
	 */
	public void setAcceptValueOnFocusLoss(boolean acceptValueOnFocusLoss) {
		this.acceptValueOnFocusLoss = acceptValueOnFocusLoss;
	}

	public boolean isAcceptValueOnFocusLoss() {
		return acceptValueOnFocusLoss;
	}

	/**
	 * Controls if the ValueAdjuster should respond to the mouse wheel or not
	 *
	 * @param useMouseWheel true if the mouse wheel is used
	 */
	public void setUseMouseWheel(boolean useMouseWheel) {
		this.useMouseWheel = useMouseWheel;
	}

	@Override
	public void setTooltipContent(Object tooltipContent) {
		super.setTooltipContent(tooltipContent);
		label.setTooltipContent(tooltipContent);
	}

	@Override
	protected void applyTheme(ThemeInfo themeInfo) {
		super.applyTheme(themeInfo);
		applyThemeValueAdjuster(themeInfo);
	}

	protected void applyThemeValueAdjuster(ThemeInfo themeInfo) {
		width = themeInfo.getParameter("width", 100);
		displayPrefixTheme = themeInfo.getParameter("displayPrefix", "");
		useMouseWheel = themeInfo.getParameter("useMouseWheel", useMouseWheel);
	}

	@Override
	public int getMinWidth() {
		int minWidth = super.getMinWidth();
		minWidth = Math.max(minWidth,
				getBorderHorizontal() +
				decButton.getMinWidth() +
				Math.max(width, label.getMinWidth()) +
				incButton.getMinWidth());
		return minWidth;
	}

	@Override
	public int getMinHeight() {
		int minHeight = label.getMinHeight();
		minHeight = Math.max(minHeight, decButton.getMinHeight());
		minHeight = Math.max(minHeight, incButton.getMinHeight());
		minHeight += getBorderVertical();
		return Math.max(minHeight, super.getMinHeight());
	}

	@Override
	public int getPreferredInnerWidth() {
		return decButton.getPreferredWidth() +
				Math.max(width, label.getPreferredWidth()) +
				incButton.getPreferredWidth();
	}

	@Override
	public int getPreferredInnerHeight() {
		return Math.max(Math.max(
				decButton.getPreferredHeight(),
				incButton.getPreferredHeight()),
				label.getPreferredHeight());
	}

	@Override
	protected void keyboardFocusLost() {
		wasInEditOnFocusLost = valueLabel.isVisible();
		label.getAnimationState().setAnimationState(STATE_KEYBOARD_FOCUS, false);
	}

	@Override
	protected void keyboardFocusGained() {
		// keep in this method to not change subclassing behavior
		label.getAnimationState().setAnimationState(STATE_KEYBOARD_FOCUS, true);
	}

	@Override
	protected void keyboardFocusGained(FocusGainedCause cause, Widget previousWidget) {
		keyboardFocusGained();
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
	}

	@Override
	protected void widgetDisabled() {
	}

	@Override
	protected void layout() {
		int height = getInnerHeight();
		int y = getInnerY();
		decButton.setPosition(getInnerX(), y);
		decButton.setSize(decButton.getPreferredWidth(), height);
		incButton.setPosition(getInnerRight() - incButton.getPreferredWidth(), y);
		incButton.setSize(incButton.getPreferredWidth(), height);
		int labelX = decButton.getRight();
		int labelWidth = Math.max(0, incButton.getX() - labelX);
		label.setSize(labelWidth, height);
		label.setPosition(labelX, y);
		valueLabel.setSize(labelWidth, height);
		valueLabel.setPosition(labelX, y);
	}

	protected void setDisplayText() {
		String prefix = (displayPrefix != null) ? displayPrefix : displayPrefixTheme;
		label.setText(prefix.concat(formatText()));
	}

	protected abstract String formatText();

	void onTimer(int nextDelay) {
		timer.setDelay(nextDelay);
		if(incButton.getModel().isArmed()) {
			doIncrement();
		} else if(decButton.getModel().isArmed()) {
			doDecrement();
		}
	}

	void updateTimer() {
		if(timer != null) {
			if(incButton.getModel().isArmed() || decButton.getModel().isArmed()) {
				if(!timer.isRunning()) {
					onTimer(INITIAL_DELAY);
					timer.start();
				}
			} else {
				timer.stop();
			}
		}
	}

	@Override
	protected void afterAddToGUI(GUI gui) {
		super.afterAddToGUI(gui);
		timer = gui.createTimer();
		timer.setCallback(timerCallback);
		timer.setContinuous(true);
	}

	@Override
	protected void beforeRemoveFromGUI(GUI gui) {
		super.beforeRemoveFromGUI(gui);
		if(timer != null) {
			timer.stop();
		}
		timer = null;
	}

	@Override
	protected boolean handleEvent(Event evt) {
		if(evt.isKeyEvent()) {
			if(evt.isKeyPressedEvent() && evt.getKeyCode() == Event.KEY_ESCAPE && listeners.dragActive) {
				listeners.dragActive = false;
				onDragCancelled();
				return true;
			}
			if(!valueLabel.isVisible()) {
				switch(evt.getType()) {
				case KEY_PRESSED:
					switch(evt.getKeyCode()) {
					case Event.KEY_RIGHT:
						doIncrement();
						return true;
					case Event.KEY_LEFT:
						doDecrement();
						return true;
					default:
						if(evt.hasKeyCharNoModifiers() && shouldStartEdit(evt.getKeyChar())) {
							return true;
						}
					}
				case CLEAR_KEYBOARD_STATE:
				case KEY_RELEASED:
				case MOUSE_BTNDOWN:
				case MOUSE_BTNUP:
				case MOUSE_CLICKED:
				case MOUSE_DRAGGED:
				case MOUSE_ENTERED:
				case MOUSE_EXITED:
				case MOUSE_MOVED:
				case MOUSE_WHEEL:
				case POPUP_CLOSED:
				case POPUP_OPENED:
				default:
					break;
				}
				return false;
			}
		} else if(!valueLabel.isVisible() && useMouseWheel && evt.getType() == Event.Type.MOUSE_WHEEL) {
			if(evt.getMouseWheelDelta() < 0) {
				doDecrement();
			} else if(evt.getMouseWheelDelta() > 0) {
				doIncrement();
			}
			return true;
		}
		return super.handleEvent(evt);
	}

	protected abstract String onEditStart();
	protected abstract boolean onEditEnd(String text);
	protected abstract String validateEdit(String text);
	protected abstract void onEditCanceled();
	protected abstract boolean shouldStartEdit(char ch);

	protected abstract void onDragStart();
	protected abstract void onDragUpdate(int dragDelta);
	protected abstract void onDragCancelled();
	protected void onDragEnd() {}

	protected abstract void doDecrement();
	protected abstract void doIncrement();

	void handleEditCallback(int key) {
		switch(key) {
		case Event.KEY_RETURN:
			if(onEditEnd(valueLabel.getText())) {
				label.setVisible(true);
				valueLabel.setVisible(false);
			}
			break;

		case Event.KEY_ESCAPE:
//			cancelEdit();
			break;

		default:
		}
	}

	protected abstract void syncWithModel();

	class ModelCallback implements Runnable {
		public void run() {
			syncWithModel();
		}
	}

	class L implements Runnable, DraggableButton.DragListener, EditField.Callback {
		boolean dragActive;
		public void run() {
		}
		public void dragStarted() {
			dragActive = true;
			onDragStart();
		}
		public void dragged(int deltaX, int deltaY) {
			if(dragActive) {
				onDragUpdate(deltaX);
			}
		}
		public void dragStopped() {
			dragActive = false;
			onDragEnd();
		}
		public void callback(int key) {
			handleEditCallback(key);
		}
	}

}
